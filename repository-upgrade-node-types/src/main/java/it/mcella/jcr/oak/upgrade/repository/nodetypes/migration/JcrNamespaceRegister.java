package it.mcella.jcr.oak.upgrade.repository.nodetypes.migration;

import it.mcella.jcr.oak.upgrade.repository.JcrNamespace;
import it.mcella.jcr.oak.upgrade.repository.JcrNamespaceException;
import it.mcella.jcr.oak.upgrade.repository.JcrSession;

public interface JcrNamespaceRegister {

    void register(JcrNamespace jcrNamespace, JcrSession jcrSession) throws JcrNamespaceException;

}
