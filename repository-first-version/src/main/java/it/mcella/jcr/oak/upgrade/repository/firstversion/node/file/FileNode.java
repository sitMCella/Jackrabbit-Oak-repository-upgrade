package it.mcella.jcr.oak.upgrade.repository.firstversion.node.file;

import it.mcella.jcr.oak.upgrade.repository.JcrNodeId;
import it.mcella.jcr.oak.upgrade.repository.JcrNodePath;
import it.mcella.jcr.oak.upgrade.repository.firstversion.node.BaseNode;

import java.util.Collections;
import java.util.List;

public class FileNode extends BaseNode {

    protected final boolean hidden;
    protected final boolean deletable;
    protected final String mimeType;

    public FileNode(JcrNodeId jcrNodeId, JcrNodePath jcrNodePath, String name, boolean hidden, boolean deletable, String mimeType) {
        super(jcrNodeId, jcrNodePath, name);
        this.hidden = hidden;
        this.deletable = deletable;
        this.mimeType = mimeType;
    }

    public boolean isHidden() {
        return hidden;
    }

    public boolean isDeletable() {
        return deletable;
    }

    public String getMimeType() {
        return mimeType;
    }

    @Override
    public List<JcrNodeId> getChildNodeIds() {
        return Collections.emptyList();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof FileNode)) return false;

        FileNode fileNode = (FileNode) o;

        if (jcrNodeId != null ? !jcrNodeId.equals(fileNode.jcrNodeId) : fileNode.jcrNodeId != null) return false;
        if (jcrNodePath != null ? !jcrNodePath.equals(fileNode.jcrNodePath) : fileNode.jcrNodePath != null)
            return false;
        if (name != null ? !name.equals(fileNode.name) : fileNode.name != null) return false;
        if (hidden != fileNode.hidden) return false;
        if (deletable != fileNode.deletable) return false;
        if (mimeType != null ? !mimeType.equals(fileNode.mimeType) : fileNode.mimeType != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = jcrNodeId != null ? jcrNodeId.hashCode() : 0;
        result = 31 * result + (jcrNodePath != null ? jcrNodePath.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (hidden ? 1 : 0);
        result = 31 * result + (deletable ? 1 : 0);
        result = 31 * result + (mimeType != null ? mimeType.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return "FileNode{" +
                "jcrNodeId='" + jcrNodeId.getNodeId() + '\'' +
                ", jcrNodePath='" + jcrNodePath.getPath() + '\'' +
                ", name='" + name + '\'' +
                ", hidden=" + hidden +
                ", deletable=" + deletable +
                ", mimeType='" + mimeType + '\'' +
                '}';
    }

}
