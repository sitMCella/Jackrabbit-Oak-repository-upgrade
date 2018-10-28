package it.mcella.jcr.oak.upgrade.repository.firstversion.node;

import it.mcella.jcr.oak.upgrade.repository.JcrNodeId;
import it.mcella.jcr.oak.upgrade.repository.JcrNodePath;

public abstract class BaseNode implements Node {

    protected final JcrNodeId jcrNodeId;
    protected final JcrNodePath jcrNodePath;
    protected final String name;

    public BaseNode(JcrNodeId jcrNodeId, JcrNodePath jcrNodePath, String name) {
        this.jcrNodeId = jcrNodeId;
        this.jcrNodePath = jcrNodePath;
        this.name = name;
    }

    @Override
    public JcrNodeId getJcrNodeId() {
        return jcrNodeId;
    }

    public JcrNodePath getJcrNodePath() {
        return jcrNodePath;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "BaseNode{" +
                "jcrNodeId='" + jcrNodeId.getNodeId() + '\'' +
                "jcrNodePath='" + jcrNodePath.getPath() + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

}
