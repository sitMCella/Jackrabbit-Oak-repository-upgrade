package it.mcella.jcr.oak.upgrade.repository.secondversion.node.root;

import it.mcella.jcr.oak.upgrade.repository.JcrNode;

public interface JcrRootNodeFactory {

    JcrRootNode createFrom(JcrNode jcrNode);

}
