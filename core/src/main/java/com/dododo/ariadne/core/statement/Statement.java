package com.dododo.ariadne.core.statement;

import com.dododo.ariadne.core.statement.operand.LogicOperand;
import com.dododo.ariadne.core.statement.operand.MathOperand;
import com.dododo.ariadne.core.statement.operand.Operand;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface Statement {

    Object process(Map<String, ?> params);

    class Builder {

        protected final Builder root;

        protected final Builder parent;

        protected final List<Object> parts;

        public Builder() {
            this.root = this;
            this.parent = new InvalidBuilder();
            this.parts = new ArrayList<>();
        }

        protected Builder(Builder root, Builder parent) {
            this.root = root;
            this.parent = parent;
            this.parts = new ArrayList<>();
        }

        public Builder addParam(Parameter param) {
            this.parts.add(param);
            return this;
        }

        public Builder addOperation(Operand operand) {
            this.parts.add(operand);
            return this;
        }

        public Builder openBracket() {
            Builder builder = new Builder(root, this);
            this.parts.add(builder);
            return builder;
        }

        public Builder closeBracket() {
            return parent;
        }

        public Statement build() {
            if (root != this) {
                throw new IllegalArgumentException();
            }

            return root.buildFromRoot();
        }

        private Statement buildFromRoot() {
            if (parts.isEmpty()) {
                throw new IllegalArgumentException();
            }

            prepareSpecialParts();
            validateParts();

            processBracketStatements();
            processOperands(3);
            processOperands(2);
            processOperands(1);
            processOperands(0);

            return (Statement) parts.get(0);
        }

        private void prepareSpecialParts() {
            Object part = parts.get(0);

            if (part instanceof MathOperand) {
                MathOperand operand = (MathOperand) part;

                if (operand.isSpecial()) {
                    parts.add(0, Parameter.constant(0));
                }
            } else if (part instanceof LogicOperand) {
                LogicOperand operand = (LogicOperand) part;

                if (operand.isSpecial()) {
                    parts.add(0, Parameter.constant(true));
                    parts.set(1, LogicOperand.XOR);
                }
            }
        }

        private void validateParts() {
            for (int i = 0; i < parts.size(); i++) {
                Object part = parts.get(i);

                if (part instanceof Operand) {
                    if (i == 0 || i == parts.size() - 1) {
                        throw new IllegalArgumentException();
                    }

                    Object right = parts.get(i + 1);

                    if (right instanceof Operand) {
                        throw new IllegalArgumentException();
                    }
                } else if (part instanceof Parameter && i < parts.size() - 1) {
                    Object right = parts.get(i + 1);

                    if (right instanceof Parameter) {
                        throw new IllegalArgumentException();
                    }
                }
            }
        }

        private void processBracketStatements() {
            for (int i = 0; i < parts.size(); i++) {
                Object part = parts.get(i);

                if (part instanceof Builder) {
                    parts.set(i, ((Builder) part).buildFromRoot());
                }
            }
        }

        private void processOperands(int level) {
            int i = 0;

            while (i < parts.size()) {
                Object part = parts.get(i);

                if (part instanceof Operand) {
                    Operand operand = (Operand) part;

                    if (operand.getLevel() == level) {
                        Statement left = (Statement) parts.get(i - 1);
                        Statement right = (Statement) parts.get(i + 1);

                        Operation operation = new Operation(left, right, operand);

                        parts.set(i, operation);

                        parts.remove(left);
                        parts.remove(right);

                        i--;
                    }
                }

                i++;
            }
        }
    }

    class InvalidBuilder extends Builder {

        private InvalidBuilder() {
            super(null, null);
        }

        @Override
        public Builder addParam(Parameter param) {
            return this;
        }

        @Override
        public Builder addOperation(Operand operand) {
            return this;
        }

        @Override
        public Builder openBracket() {
            return this;
        }

        @Override
        public Builder closeBracket() {
            return this;
        }

        @Override
        public Statement build() {
            throw new IllegalArgumentException();
        }
    }
}