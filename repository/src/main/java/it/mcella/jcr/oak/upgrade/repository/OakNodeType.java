package it.mcella.jcr.oak.upgrade.repository;

import javax.jcr.nodetype.NodeType;

public class OakNodeType implements JcrNodeType {

    private final NodeType nodeType;

    public OakNodeType(NodeType nodeType) {
        this.nodeType = nodeType;
    }

    @Override
    public String getName() {
        return nodeType.getName();
    }

    @Override
    public boolean belongsTo(JcrNamespace jcrNamespace) {
        return jcrNamespace.isAppliedTo(this);
    }

    @Override
    public boolean hasOrderableChildNodes() {
        return nodeType.hasOrderableChildNodes();
    }

    public NodeType getNodeType() {
        return nodeType;
    }

    @Override
    public String toString() {
        return "OakNodeType{" +
                "name=" + getName() +
                '}';
    }

}
