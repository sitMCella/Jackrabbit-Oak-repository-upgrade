package it.mcella.jcr.oak.upgrade.repository.firstversion.node.folder;

import it.mcella.jcr.oak.upgrade.repository.JcrNode;
import it.mcella.jcr.oak.upgrade.repository.JcrNodeId;
import it.mcella.jcr.oak.upgrade.repository.JcrNodePath;

import javax.jcr.RepositoryException;
import java.util.ArrayList;
import java.util.List;

public class OakFolderNode implements JcrFolderNode {

    private final JcrNode jcrNode;

    public OakFolderNode(JcrNode jcrNode) {
        this.jcrNode = jcrNode;
    }

    @Override
    public void setHidden(boolean hidden) throws RepositoryException {
        jcrNode.setProperty(JcrFolderNode.HIDDEN_PROPERTY_NAME, hidden);
    }

    @Override
    public FolderNode toNode() throws RepositoryException {
        JcrNodeId jcrNodeId = jcrNode.getNodeId();
        JcrNodePath jcrNodePath = jcrNode.getNodePath();
        String name = jcrNode.getName();
        boolean hidden = jcrNode.getProperty(JcrFolderNode.HIDDEN_PROPERTY_NAME).getBooleanValue();
        List<JcrNodeId> jcrChildNodeIds = new ArrayList<>();
        for (JcrNode jcrChildNode : jcrNode.getChildNodes()) {
            jcrChildNodeIds.add(jcrChildNode.getNodeId());
        }
        return new FolderNode(jcrNodeId, jcrNodePath, name, hidden, jcrChildNodeIds);
    }

}
