package it.mcella.jcr.oak.upgrade.repository;

import javax.jcr.RepositoryException;
import javax.jcr.Value;

public interface JcrProperty {

    boolean contains(String value) throws RepositoryException;

    String getStringValue() throws RepositoryException;

    boolean getBooleanValue() throws RepositoryException;

    boolean isMultiple() throws RepositoryException;

    Value getValue() throws RepositoryException;

    Value[] getValues() throws RepositoryException;

    String getName() throws RepositoryException;

    boolean belongsTo(JcrNamespace jcrNamespace) throws RepositoryException;

    void remove() throws RepositoryException;

    void renameValueNames(JcrNamespace namespace, JcrNamespace newNamespace, JcrValueFactory jcrValueFactory) throws RepositoryException;

    JcrPropertyType getPropertyType() throws RepositoryException;

}
