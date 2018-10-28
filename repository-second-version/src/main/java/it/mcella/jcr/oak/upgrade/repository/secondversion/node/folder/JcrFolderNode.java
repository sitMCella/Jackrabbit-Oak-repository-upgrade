package it.mcella.jcr.oak.upgrade.repository.secondversion.node.folder;

import javax.jcr.RepositoryException;

public interface JcrFolderNode {

    public static final String NODE_TYPE = "app:folder";
    public static final String DESCRIPTION_PROPERTY_NAME = "app:description";

    FolderNode toNode() throws RepositoryException;

    void setDescription(String description) throws RepositoryException;

}
