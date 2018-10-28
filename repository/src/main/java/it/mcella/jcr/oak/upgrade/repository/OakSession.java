package it.mcella.jcr.oak.upgrade.repository;

import javax.jcr.PropertyType;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class OakSession implements JcrSession {

    private final Session session;

    public OakSession(Session session) {
        this.session = session;
    }

    @Override
    public JcrNode getNodeById(JcrNodeId jcrNodeId) throws RepositoryException {
        return new OakNode(session.getNodeByIdentifier(jcrNodeId.getNodeId()));
    }

    @Override
    public List<JcrNode> getNodesById(List<JcrNodeId> jcrNodeIds) throws RepositoryException {
        List<JcrNode> jcrNodes = new ArrayList<>();
        for (JcrNodeId jcrNodeId : jcrNodeIds) {
            jcrNodes.add(getNodeById(jcrNodeId));
        }
        return jcrNodes;
    }

    @Override
    public JcrNode getRootNode() throws RepositoryException {
        return new OakNode(session.getRootNode());
    }

    @Override
    public void save() throws RepositoryException {
        session.save();
    }

    @Override
    public JcrBinary createBinary(InputStream inputStream) throws RepositoryException {
        return new OakBinary(session.getValueFactory().createBinary(inputStream));
    }

    @Override
    public void close() throws Exception {
        if (session != null) {
            session.logout();
        }
    }

    @Override
    public Session getSession() {
        return session;
    }

    @Override
    public JcrValue createUriValue(JcrNamespace jcrNamespace) throws RepositoryException {
        return new OakValue(session.getValueFactory().createValue(jcrNamespace.getUri(), PropertyType.URI));
    }

    @Override
    public JcrValue createNameValue(String name) throws RepositoryException {
        return new OakValue(session.getValueFactory().createValue(name, PropertyType.NAME));
    }

}
