package it.mcella.jcr.oak.upgrade.repository.firstversion.node.file;

import it.mcella.jcr.oak.upgrade.repository.JcrNode;
import it.mcella.jcr.oak.upgrade.repository.JcrSession;

import javax.jcr.RepositoryException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

public interface JcrFileNode {

    public static final String NODE_TYPE = "app:file";
    public static final String HIDDEN_PROPERTY_NAME = "app:hidden";
    public static final String DELETABLE_PROPERTY_NAME = "app:deletable";

    void setHidden(boolean hidden) throws RepositoryException;

    void setDeletable(boolean deletable) throws RepositoryException;

    JcrNode createContent(JcrSession jcrSession, Path file, InputStream inputStream) throws RepositoryException, IOException;

    FileNode toNode() throws RepositoryException;

}
