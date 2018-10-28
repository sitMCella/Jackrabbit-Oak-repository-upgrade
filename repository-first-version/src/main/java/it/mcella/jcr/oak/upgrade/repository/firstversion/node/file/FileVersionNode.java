package it.mcella.jcr.oak.upgrade.repository.firstversion.node.file;

import it.mcella.jcr.oak.upgrade.repository.JcrNodeId;
import it.mcella.jcr.oak.upgrade.repository.firstversion.node.NodeVersion;

import java.util.Collections;
import java.util.List;

public class FileVersionNode extends FileNode implements NodeVersion {

    private final long versionNumber;
    private final String versionDescription;

    public FileVersionNode(FileNode fileNode, JcrNodeId jcrNodeId, long versionNumber, String versionDescription) {
        super(jcrNodeId, fileNode.getJcrNodePath(), fileNode.getName(), fileNode.hidden, fileNode.deletable, fileNode.getMimeType());
        this.versionNumber = versionNumber;
        this.versionDescription = versionDescription;
    }

    public FileVersionNode(FileNode fileNode, JcrNodeId jcrNodeId, long versionNumber) {
        this(fileNode, jcrNodeId, versionNumber, "");
    }

    public long getVersionNumber() {
        return versionNumber;
    }

    public String getVersionDescription() {
        return versionDescription;
    }

    @Override
    public List<JcrNodeId> getChildNodeIds() {
        return Collections.emptyList();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof FileVersionNode)) return false;

        FileVersionNode fileVersionNode = (FileVersionNode) o;

        if (jcrNodeId != null ? !jcrNodeId.equals(fileVersionNode.jcrNodeId) : fileVersionNode.jcrNodeId != null)
            return false;
        if (jcrNodePath != null ? !jcrNodePath.equals(fileVersionNode.jcrNodePath) : fileVersionNode.jcrNodePath != null)
            return false;
        if (name != null ? !name.equals(fileVersionNode.name) : fileVersionNode.name != null) return false;
        if (hidden != fileVersionNode.hidden) return false;
        if (deletable != fileVersionNode.deletable) return false;
        if (mimeType != null ? !mimeType.equals(fileVersionNode.mimeType) : fileVersionNode.mimeType != null)
            return false;
        if (versionDescription != null ? !versionDescription.equals(fileVersionNode.versionDescription) : fileVersionNode.versionDescription != null)
            return false;
        if (versionNumber != fileVersionNode.versionNumber) return false;

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
        result = 31 * result + (int) (versionNumber ^ (versionNumber >>> 32));
        result = 31 * result + (versionDescription != null ? versionDescription.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FileVersionNode{" +
                "jcrNodeId='" + jcrNodeId.getNodeId() + '\'' +
                ", jcrNodePath='" + jcrNodePath.getPath() + '\'' +
                ", name='" + name + '\'' +
                ", hidden=" + hidden +
                ", deletable=" + deletable +
                ", mimeType='" + mimeType + '\'' +
                ", versionNumber=" + versionNumber +
                ", versionDescription='" + versionDescription + '\'' +
                '}';
    }

}
