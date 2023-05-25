package com.dododo.ariadne.test.rule;

public final class RuleSet {

    private final Class<?> type;

    private final NodeRule nodeRule;

    private final EdgeRule edgeRule;

    private RuleSet(Builder builder) {
        this.type = builder.type;
        this.nodeRule = builder.nodeRule;
        this.edgeRule = builder.edgeRule;
    }

    public Class<?> getType() {
        return type;
    }

    public NodeRule getNodeRule() {
        return nodeRule;
    }

    public EdgeRule getEdgeRule() {
        return edgeRule;
    }

    public static class Builder {

        private final Class<?> type;

        private NodeRule nodeRule;

        private EdgeRule edgeRule;

        public Builder(Class<?> type) {
            this.type = type;
        }

        public Builder setNodeRule(NodeRule nodeRule) {
            this.nodeRule = nodeRule;
            return this;
        }

        public Builder setEdgeRule(EdgeRule edgeRule) {
            this.edgeRule = edgeRule;
            return this;
        }

        public RuleSet build() {
            return new RuleSet(this);
        }
    }
}
