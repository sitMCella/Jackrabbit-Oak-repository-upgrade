package it.mcella.jcr.oak.upgrade.repository.firstversion.node.root;

import it.mcella.jcr.oak.upgrade.repository.JcrNode;

public interface JcrRootNodeFactory {

    JcrRootNode createFrom(JcrNode jcrNode);

}
