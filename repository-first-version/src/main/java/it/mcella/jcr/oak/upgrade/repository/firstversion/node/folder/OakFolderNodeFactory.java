package it.mcella.jcr.oak.upgrade.repository.firstversion.node.folder;

import it.mcella.jcr.oak.upgrade.repository.JcrNode;

public class OakFolderNodeFactory implements JcrFolderNodeFactory {

    @Override
    public JcrFolderNode createFrom(JcrNode jcrNode) {
        return new OakFolderNode(jcrNode);
    }

}
