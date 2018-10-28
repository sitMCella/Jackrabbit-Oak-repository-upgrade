package it.mcella.jcr.oak.upgrade.repository;

import org.apache.jackrabbit.commons.cnd.CndImporter;
import org.apache.jackrabbit.commons.cnd.ParseException;

import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class OakCustomNodeTypeDefinition implements JcrCustomNodeTypeDefinition {

    private static final String DEFAULT_CND_CONFIG_FILE_NAME = "cnd.config";

    private final JcrNamespaceConfiguration jcrNamespaceConfiguration;

    public OakCustomNodeTypeDefinition() {
        this.jcrNamespaceConfiguration = new JcrNamespaceConfiguration(DEFAULT_CND_CONFIG_FILE_NAME);
    }

    public OakCustomNodeTypeDefinition(JcrNamespaceConfiguration jcrNamespaceConfiguration) {
        this.jcrNamespaceConfiguration = jcrNamespaceConfiguration;
    }

    @Override
    public void load(Repository repository) throws RepositoryInitializationException {
        try (InputStream inputStream = jcrNamespaceConfiguration.getInputStream()) {
            Session session = repository.login(AdministratorCredentials.create());
            CndImporter.registerNodeTypes(new InputStreamReader(inputStream), session, true);
        } catch (IOException | RepositoryException | ParseException e) {
            throw new RepositoryInitializationException("Cannot load repository node types", e);
        }
    }

}
