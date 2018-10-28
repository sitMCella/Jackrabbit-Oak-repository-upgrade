package it.mcella.jcr.oak.upgrade.repository.firstversion.node.root;

import it.mcella.jcr.oak.upgrade.repository.JcrNode;

public class OakRootNodeFactory implements JcrRootNodeFactory {

    @Override
    public JcrRootNode createFrom(JcrNode jcrNode) {
        return new OakRootNode(jcrNode);
    }

}
