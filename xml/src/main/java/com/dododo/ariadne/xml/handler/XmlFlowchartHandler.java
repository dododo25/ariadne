package com.dododo.ariadne.xml.handler;

import com.dododo.ariadne.core.model.Reply;
import com.dododo.ariadne.core.model.Text;
import com.dododo.ariadne.extended.model.ComplexMenu;
import com.dododo.ariadne.extended.model.ComplexOption;
import com.dododo.ariadne.extended.model.ComplexState;
import com.dododo.ariadne.extended.model.ComplexSwitch;
import com.dododo.ariadne.extended.model.ComplexSwitchBranch;
import com.dododo.ariadne.extended.model.GoToPoint;
import com.dododo.ariadne.extended.model.Marker;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XmlFlowchartHandler extends DefaultHandler {

    private final ComplexState rootState;

    private ComplexState current;

    public XmlFlowchartHandler(ComplexState rootState) {
        this.rootState = rootState;
    }

    @Override
    public void startDocument() {
        current = rootState;
    }

    @Override
    @SuppressWarnings("java:S1192")
    public void startElement(String uri, String localName, String qName, Attributes attrs) throws SAXException {
        switch (qName.toLowerCase()) {
            case "marker":
                current.addChild(new Marker(attrs.getValue("value")));
                break;
            case "text":
                current.addChild(new Text(attrs.getValue("value")));
                break;
            case "reply":
                current.addChild(new Reply(attrs.getValue("character"), attrs.getValue("line")));
                break;
            case "menu":
                ComplexMenu menu = new ComplexMenu();
                current.addChild(menu);
                current = menu;
                break;
            case "option":
                ComplexOption option = new ComplexOption(attrs.getValue("value"), attrs.getValue("condition"));
                current.addChild(option);
                current = option;
                break;
            case "switch":
                ComplexSwitch complexSwitch = new ComplexSwitch();
                current.addChild(complexSwitch);
                current = complexSwitch;
                break;
            case "if":
                ComplexSwitchBranch ifSwitchBranch = new ComplexSwitchBranch(attrs.getValue("condition"), false);
                current.addChild(ifSwitchBranch);
                current = ifSwitchBranch;
                break;
            case "else-if":
                ComplexSwitchBranch elseIfSwitchBranch = new ComplexSwitchBranch(attrs.getValue("condition"), true);
                current.addChild(elseIfSwitchBranch);
                current = elseIfSwitchBranch;
                break;
            case "else":
                ComplexSwitchBranch elseSwitchBranch = new ComplexSwitchBranch(true);
                current.addChild(elseSwitchBranch);
                current = elseSwitchBranch;
                break;
            case "goto":
                current.addChild(new GoToPoint(attrs.getValue("value")));
                break;
            case "flowchart":
                break;
            default:
                throw new SAXException(String.format("unknown tag %s", qName));
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        switch (qName.toLowerCase()) {
            case "menu":
            case "option":
            case "switch":
            case "if":
            case "else-if":
            case "else":
                current = (ComplexState) current.getRoots()[0];
                break;
            default:
                break;
        }
    }
}
