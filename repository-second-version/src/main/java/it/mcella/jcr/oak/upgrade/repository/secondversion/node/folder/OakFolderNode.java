package it.mcella.jcr.oak.upgrade.repository.secondversion.node.folder;

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
    public void setDescription(String description) throws RepositoryException {
        jcrNode.setProperty(JcrFolderNode.DESCRIPTION_PROPERTY_NAME, description);
    }

    @Override
    public FolderNode toNode() throws RepositoryException {
        JcrNodeId jcrNodeId = jcrNode.getNodeId();
        JcrNodePath jcrNodePath = jcrNode.getNodePath();
        String name = jcrNode.getName();
        String description = jcrNode.getProperty(JcrFolderNode.DESCRIPTION_PROPERTY_NAME).getStringValue();
        List<JcrNodeId> jcrChildNodeIds = new ArrayList<>();
        for (JcrNode jcrChildNode : jcrNode.getChildNodes()) {
            jcrChildNodeIds.add(jcrChildNode.getNodeId());
        }
        return new FolderNode(jcrNodeId, jcrNodePath, name, description, jcrChildNodeIds);
    }

}
