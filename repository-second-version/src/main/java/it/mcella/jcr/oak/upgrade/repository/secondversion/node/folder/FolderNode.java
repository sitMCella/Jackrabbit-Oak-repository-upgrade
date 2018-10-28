package it.mcella.jcr.oak.upgrade.repository.secondversion.node.folder;

import it.mcella.jcr.oak.upgrade.repository.JcrNodeId;
import it.mcella.jcr.oak.upgrade.repository.JcrNodePath;
import it.mcella.jcr.oak.upgrade.repository.secondversion.node.BaseNode;

import java.util.Arrays;
import java.util.List;

public class FolderNode extends BaseNode {

    private final String description;
    private final List<JcrNodeId> childNodeIds;

    public FolderNode(JcrNodeId jcrNodeId, JcrNodePath jcrNodePath, String name, String description, List<JcrNodeId> childNodeIds) {
        super(jcrNodeId, jcrNodePath, name);
        this.description = description;
        this.childNodeIds = childNodeIds;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public List<JcrNodeId> getChildNodeIds() {
        return childNodeIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof FolderNode)) return false;

        FolderNode folderNode = (FolderNode) o;

        if (jcrNodeId != null ? !jcrNodeId.equals(folderNode.jcrNodeId) : folderNode.jcrNodeId != null) return false;
        if (jcrNodePath != null ? !jcrNodePath.equals(folderNode.jcrNodePath) : folderNode.jcrNodePath != null)
            return false;
        if (name != null ? !name.equals(folderNode.name) : folderNode.name != null) return false;
        if (description != null ? !description.equals(folderNode.description) : folderNode.description != null)
            return false;
        if (childNodeIds != null ? !childNodeIds.equals(folderNode.childNodeIds) : folderNode.childNodeIds != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = jcrNodeId != null ? jcrNodeId.hashCode() : 0;
        result = 31 * result + (jcrNodePath != null ? jcrNodePath.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (childNodeIds != null ? childNodeIds.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FolderNode{" +
                "jcrNodeId='" + jcrNodeId.getNodeId() + '\'' +
                ", jcrNodePath='" + jcrNodePath.getPath() + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", childNodeIds='" + Arrays.toString(childNodeIds.toArray()) + '\'' +
                '}';
    }

}
