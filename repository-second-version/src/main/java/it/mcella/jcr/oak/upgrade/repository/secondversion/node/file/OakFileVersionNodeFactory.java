package it.mcella.jcr.oak.upgrade.repository.secondversion.node.file;

import it.mcella.jcr.oak.upgrade.repository.JcrNode;

public class OakFileVersionNodeFactory implements JcrFileVersionNodeFactory {

    @Override
    public JcrFileVersionNode createFrom(JcrNode jcrNode) {
        return new OakFileVersionNode(jcrNode);
    }

}
