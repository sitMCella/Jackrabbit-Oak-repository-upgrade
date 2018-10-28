package it.mcella.jcr.oak.upgrade.repository.firstversion.node.file;

import it.mcella.jcr.oak.upgrade.repository.JcrNode;

public interface JcrFileNodeFactory {

    JcrFileNode createFrom(JcrNode jcrNode);

}
