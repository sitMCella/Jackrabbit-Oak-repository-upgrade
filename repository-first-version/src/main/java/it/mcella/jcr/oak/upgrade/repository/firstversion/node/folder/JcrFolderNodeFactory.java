package it.mcella.jcr.oak.upgrade.repository.firstversion.node.folder;

import it.mcella.jcr.oak.upgrade.repository.JcrNode;

public interface JcrFolderNodeFactory {

    JcrFolderNode createFrom(JcrNode jcrNode);

}
