package it.mcella.jcr.oak.upgrade.repository.secondversion.node.file;

import it.mcella.jcr.oak.upgrade.repository.JcrNode;

public class OakFileNodeFactory implements JcrFileNodeFactory {

    @Override
    public JcrFileNode createFrom(JcrNode jcrNode) {
        return new OakFileNode(jcrNode);
    }

}
