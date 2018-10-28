package it.mcella.jcr.oak.upgrade.repository;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.io.InputStream;
import java.util.List;

public interface JcrSession extends AutoCloseable {

    JcrNode getNodeById(JcrNodeId jcrNodeId) throws RepositoryException;

    List<JcrNode> getNodesById(List<JcrNodeId> jcrNodeIds) throws RepositoryException;

    JcrNode getRootNode() throws RepositoryException;

    void save() throws RepositoryException;

    JcrBinary createBinary(InputStream inputStream) throws RepositoryException;

    Session getSession();

    JcrValue createUriValue(JcrNamespace jcrNamespace) throws RepositoryException;

    JcrValue createNameValue(String name) throws RepositoryException;

}
