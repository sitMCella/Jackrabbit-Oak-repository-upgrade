package it.mcella.jcr.oak.upgrade.repository.secondversion.node.file;

import it.mcella.jcr.oak.upgrade.repository.JcrNode;

public interface JcrFileVersionNodeFactory {

    JcrFileVersionNode createFrom(JcrNode jcrNode);

}
