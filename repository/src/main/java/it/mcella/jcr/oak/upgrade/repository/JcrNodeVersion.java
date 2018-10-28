package it.mcella.jcr.oak.upgrade.repository;

import javax.jcr.RepositoryException;
import java.util.List;

public interface JcrNodeVersion {

    JcrNodeId getJcrNodeId() throws RepositoryException;

    long getVersionNumber() throws RepositoryException;

    String getName() throws RepositoryException;

    String addVersionLabel() throws RepositoryException;

    JcrProperty getProperty(String propertyName) throws RepositoryException;

    List<JcrProperty> getProperties() throws RepositoryException;

    List<String> getVersionLabels() throws RepositoryException;

    void addVersionLabelsFrom(JcrNodeVersion jcrNodeVersion) throws RepositoryException;

}
