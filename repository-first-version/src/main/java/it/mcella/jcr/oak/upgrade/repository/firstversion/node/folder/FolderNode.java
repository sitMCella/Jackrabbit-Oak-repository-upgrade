package it.mcella.jcr.oak.upgrade.repository.firstversion.node.folder;

import it.mcella.jcr.oak.upgrade.repository.JcrNodeId;
import it.mcella.jcr.oak.upgrade.repository.JcrNodePath;
import it.mcella.jcr.oak.upgrade.repository.firstversion.node.BaseNode;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FolderNode extends BaseNode {

    private final boolean hidden;
    private final List<JcrNodeId> childNodeIds;

    public FolderNode(JcrNodeId jcrNodeId, JcrNodePath jcrNodePath, String name, boolean hidden) {
        this(jcrNodeId, jcrNodePath, name, hidden, Collections.emptyList());
    }

    public FolderNode(JcrNodeId jcrNodeId, JcrNodePath jcrNodePath, String name, boolean hidden, List<JcrNodeId> childNodeIds) {
        super(jcrNodeId, jcrNodePath, name);
        this.hidden = hidden;
        this.childNodeIds = childNodeIds;
    }

    public boolean isHidden() {
        return hidden;
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
        if (hidden != folderNode.hidden) return false;
        if (childNodeIds != null ? !childNodeIds.equals(folderNode.childNodeIds) : folderNode.childNodeIds != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = jcrNodeId != null ? jcrNodeId.hashCode() : 0;
        result = 31 * result + (jcrNodePath != null ? jcrNodePath.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (hidden ? 1 : 0);
        result = 31 * result + (childNodeIds != null ? childNodeIds.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FolderNode{" +
                "jcrNodeId='" + jcrNodeId.getNodeId() + '\'' +
                ", jcrNodePath='" + jcrNodePath.getPath() + '\'' +
                ", name='" + name + '\'' +
                ", hidden=" + hidden +
                ", childNodeIds='" + Arrays.toString(childNodeIds.toArray()) + '\'' +
                '}';
    }

}
