package it.mcella.jcr.oak.upgrade.repository.nodetypes.migration;

import it.mcella.jcr.oak.upgrade.repository.JcrNamespace;
import it.mcella.jcr.oak.upgrade.repository.JcrNamespaceConfiguration;
import it.mcella.jcr.oak.upgrade.repository.JcrNamespaceException;
import it.mcella.jcr.oak.upgrade.repository.JcrSession;
import it.mcella.jcr.oak.upgrade.repository.nodetypes.jackrabbit.JcrNodeTypeDefinitionsException;
import it.mcella.jcr.oak.upgrade.repository.nodetypes.jackrabbit.JcrNodeTypeUnregisterException;
import it.mcella.jcr.oak.upgrade.repository.nodetypes.jackrabbit.JcrNodeTypesRegisterException;

import java.io.IOException;
import java.io.InputStream;

public class OakMigrationRepository {

    private final OakNodesMigrator oakNodesMigrator;
    private final OakNamespaceRegister oakNamespaceRegister;
    private final JcrNodeTypesRegister jcrNodeTypesRegister;
    private final JcrNodeTypesUnregister jcrNodeTypesUnregister;

    public OakMigrationRepository(OakNodesMigrator oakNodesMigrator,
                                  OakNamespaceRegister oakNamespaceRegister,
                                  JcrNodeTypesRegister jcrNodeTypesRegister,
                                  JcrNodeTypesUnregister jcrNodeTypesUnregister) {
        this.oakNodesMigrator = oakNodesMigrator;
        this.oakNamespaceRegister = oakNamespaceRegister;
        this.jcrNodeTypesRegister = jcrNodeTypesRegister;
        this.jcrNodeTypesUnregister = jcrNodeTypesUnregister;
    }

    public void registerNamespace(JcrNamespace jcrNamespace, JcrSession jcrSession) throws JcrMigrationRepositoryException {
        try {
            oakNamespaceRegister.register(jcrNamespace, jcrSession);
        } catch (JcrNamespaceException e) {
            throw new JcrMigrationRepositoryException(e);
        }
    }

    public void registerNodeTypesFrom(JcrNamespaceConfiguration jcrNamespaceConfiguration, JcrSession jcrSession, JcrNamespace jcrNamespace) throws JcrMigrationRepositoryException {
        String namespaceUri = retrieveNamespaceUriFrom(jcrNamespaceConfiguration);
        if (!jcrNamespace.hasUri(namespaceUri)) {
            throw new JcrMigrationRepositoryException(String.format("Wrong node type definition file \"%s\". Expected namespace URI: \"%s\", found namespace URI: \"%s\"", jcrNamespaceConfiguration, jcrNamespace.getUri(), namespaceUri));
        }
        registerNodeTypes(jcrNamespaceConfiguration, jcrSession);
    }

    public void migrate(JcrNodeMigrator jcrNodeMigrator, JcrNamespace jcrOldNamespace, JcrNamespace jcrNewNamespace, JcrSession jcrSession) throws JcrMigrationRepositoryException {
        try {
            oakNodesMigrator.migrate(jcrNodeMigrator, jcrOldNamespace, jcrNewNamespace, jcrSession);
        } catch (JcrNodesMigratorException e) {
            throw new JcrMigrationRepositoryException(e);
        }
    }

    public void unregisterNodeTypesFrom(JcrNamespaceConfiguration jcrNamespaceConfiguration, JcrSession jcrSession) throws JcrMigrationRepositoryException {
        try (InputStream inputStream = jcrNamespaceConfiguration.getInputStream()) {
            jcrNodeTypesUnregister.unregister(inputStream, jcrSession);
        } catch (IOException | JcrNodeTypeUnregisterException e) {
            throw new JcrMigrationRepositoryException(e);
        }
    }

    private String retrieveNamespaceUriFrom(JcrNamespaceConfiguration jcrNamespaceConfiguration) throws JcrMigrationRepositoryException {
        try (InputStream inputStream = jcrNamespaceConfiguration.getInputStream()) {
            return jcrNodeTypesRegister.retrieveNamespaceUriFrom(inputStream);
        } catch (IOException | JcrNodeTypeDefinitionsException e) {
            throw new JcrMigrationRepositoryException(e);
        }
    }

    private void registerNodeTypes(JcrNamespaceConfiguration jcrNamespaceConfiguration, JcrSession jcrSession) throws JcrMigrationRepositoryException {
        try (InputStream inputStream = jcrNamespaceConfiguration.getInputStream()) {
            jcrNodeTypesRegister.registerNodeTypes(inputStream, jcrSession);
        } catch (IOException | JcrNodeTypesRegisterException e) {
            throw new JcrMigrationRepositoryException(e);
        }
    }

}
