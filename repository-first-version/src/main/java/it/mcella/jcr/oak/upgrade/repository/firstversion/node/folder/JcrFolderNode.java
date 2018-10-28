package it.mcella.jcr.oak.upgrade.repository.firstversion.node.folder;

import javax.jcr.RepositoryException;

public interface JcrFolderNode {

    public static final String NODE_TYPE = "app:folder";
    public static final String HIDDEN_PROPERTY_NAME = "app:hidden";

    void setHidden(boolean hidden) throws RepositoryException;

    FolderNode toNode() throws RepositoryException;

}
