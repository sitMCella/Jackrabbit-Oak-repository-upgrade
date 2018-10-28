package it.mcella.jcr.oak.upgrade.repository.nodetypes.migration;

import it.mcella.jcr.oak.upgrade.repository.JcrNamespace;
import it.mcella.jcr.oak.upgrade.repository.JcrNamespaceException;
import it.mcella.jcr.oak.upgrade.repository.JcrSession;

import javax.jcr.NamespaceRegistry;
import javax.jcr.RepositoryException;

public class OakNamespaceRegister implements JcrNamespaceRegister {

    @Override
    public void register(JcrNamespace jcrNamespace, JcrSession jcrSession) throws JcrNamespaceException {
        try {
            NamespaceRegistry namespaceRegistry = jcrSession.getSession().getWorkspace().getNamespaceRegistry();
            namespaceRegistry.registerNamespace(jcrNamespace.getPrefix(), jcrNamespace.getUri());
        } catch (RepositoryException e) {
            String message = String.format("Cannot register namespace %s", jcrNamespace);
            throw new JcrNamespaceException(message, e);
        }
    }

}
