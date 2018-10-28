package it.mcella.jcr.oak.upgrade.repository.secondversion.node.root;

import it.mcella.jcr.oak.upgrade.repository.JcrNode;
import it.mcella.jcr.oak.upgrade.repository.JcrNodeId;
import it.mcella.jcr.oak.upgrade.repository.JcrNodePath;

import javax.jcr.RepositoryException;
import java.util.ArrayList;
import java.util.List;

public class OakRootNode implements JcrRootNode {

    private final JcrNode jcrNode;

    public OakRootNode(JcrNode jcrNode) {
        this.jcrNode = jcrNode;
    }

    @Override
    public RootNode toNode() throws RepositoryException {
        JcrNodeId jcrNodeId = jcrNode.getNodeId();
        JcrNodePath jcrNodePath = jcrNode.getNodePath();
        String name = jcrNode.getName();
        List<JcrNodeId> jcrChildNodeIds = new ArrayList<>();
        for (JcrNode jcrChildNode : jcrNode.getChildNodes()) {
            jcrChildNodeIds.add(jcrChildNode.getNodeId());
        }
        return new RootNode(jcrNodeId, jcrNodePath, name, jcrChildNodeIds);
    }

}
