package it.mcella.jcr.oak.upgrade.repository.secondversion.node.file;

import it.mcella.jcr.oak.upgrade.repository.JcrNode;

public interface JcrFileNodeFactory {

    JcrFileNode createFrom(JcrNode jcrNode);

}
