package it.mcella.jcr.oak.upgrade.repository.nodetypes.migration;

import it.mcella.jcr.oak.upgrade.repository.JcrSession;
import it.mcella.jcr.oak.upgrade.repository.nodetypes.jackrabbit.JcrNodeTypeDefinitionsException;
import it.mcella.jcr.oak.upgrade.repository.nodetypes.jackrabbit.JcrNodeTypesRegisterException;

import java.io.InputStream;

public interface JcrNodeTypesRegister {

    String retrieveNamespaceUriFrom(InputStream namespaceConfiguration) throws JcrNodeTypeDefinitionsException;

    void registerNodeTypes(InputStream namespaceConfiguration, JcrSession jcrSession) throws JcrNodeTypesRegisterException;

}
