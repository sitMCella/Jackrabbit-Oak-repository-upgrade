package it.mcella.jcr.oak.upgrade.repository.secondversion.node.file;

import it.mcella.jcr.oak.upgrade.repository.JcrNode;
import it.mcella.jcr.oak.upgrade.repository.JcrSession;

import javax.jcr.RepositoryException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

public interface JcrFileNode {

    public static final String NODE_TYPE = "app:file";
    public static final String SYSTEM_PROPERTY_NAME = "app:system";
    public static final String DESCRIPTION_PROPERTY_NAME = "app:description";

    void setSystem(boolean system) throws RepositoryException;

    void setDescription(String description) throws RepositoryException;

    JcrNode createContent(JcrSession jcrSession, Path file, InputStream inputStream) throws RepositoryException, IOException;

    FileNode toNode() throws RepositoryException;

}
