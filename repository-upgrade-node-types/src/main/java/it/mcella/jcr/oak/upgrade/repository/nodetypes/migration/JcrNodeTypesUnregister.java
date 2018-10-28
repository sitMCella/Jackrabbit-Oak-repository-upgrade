package it.mcella.jcr.oak.upgrade.repository.nodetypes.migration;

import it.mcella.jcr.oak.upgrade.repository.JcrSession;
import it.mcella.jcr.oak.upgrade.repository.nodetypes.jackrabbit.JcrNodeTypeUnregisterException;

import java.io.InputStream;

public interface JcrNodeTypesUnregister {

    void unregister(InputStream namespaceConfiguration, JcrSession jcrSession) throws JcrNodeTypeUnregisterException;

}
