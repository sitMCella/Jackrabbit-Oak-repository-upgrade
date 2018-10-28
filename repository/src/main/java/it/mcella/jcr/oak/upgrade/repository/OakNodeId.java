package it.mcella.jcr.oak.upgrade.repository;

public class OakNodeId implements JcrNodeId {

    private final String nodeId;

    public OakNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    @Override
    public String getNodeId() {
        return nodeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof OakNodeId)) return false;

        OakNodeId oakNodeId = (OakNodeId) o;

        if (nodeId != null ? !nodeId.equals(oakNodeId.nodeId) : oakNodeId.nodeId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return nodeId != null ? nodeId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "OakNodeId{" +
                "nodeId='" + nodeId + '\'' +
                '}';
    }

}
