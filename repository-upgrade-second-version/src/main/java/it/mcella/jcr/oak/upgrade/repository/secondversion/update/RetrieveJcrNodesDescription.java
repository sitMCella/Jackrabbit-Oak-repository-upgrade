package it.mcella.jcr.oak.upgrade.repository.secondversion.update;

import it.mcella.jcr.oak.upgrade.repository.AdministratorCredentials;
import it.mcella.jcr.oak.upgrade.repository.JcrNamespaceConfiguration;
import it.mcella.jcr.oak.upgrade.repository.JcrNode;
import it.mcella.jcr.oak.upgrade.repository.JcrNodeId;
import it.mcella.jcr.oak.upgrade.repository.JcrNodeVersion;
import it.mcella.jcr.oak.upgrade.repository.JcrRepository;
import it.mcella.jcr.oak.upgrade.repository.JcrSession;
import it.mcella.jcr.oak.upgrade.repository.OakCustomNodeTypeDefinition;
import it.mcella.jcr.oak.upgrade.repository.OakRepository;
import it.mcella.jcr.oak.upgrade.repository.nodetypes.migration.JcrMigrationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.RepositoryException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Simple implementation of a mapping between JcrNodeId and description field.
 * Each JcrNodeId versions description is mapped to the original node JcrNodeId.
 */
public class RetrieveJcrNodesDescription {

    public static final String DESCRIPTION_SEPARATOR = ",";

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    private static final String CND_CONFIG_FILE_FIRST_VERSION = "remove_attributes_mixin/cnd_old_version.config";

    private final Path oakFileSystemFolder;

    public RetrieveJcrNodesDescription(Path oakFileSystemFolder) {
        this.oakFileSystemFolder = oakFileSystemFolder;
    }

    public JcrNodeToDescription evaluate() throws Exception {
        JcrNamespaceConfiguration jcrNamespaceConfiguration = new JcrNamespaceConfiguration(CND_CONFIG_FILE_FIRST_VERSION);
        OakCustomNodeTypeDefinition oakCustomNodeTypeDefinition = new OakCustomNodeTypeDefinition(jcrNamespaceConfiguration);
        try (OakRepository oakRepository = new OakRepository(oakFileSystemFolder, oakCustomNodeTypeDefinition)) {
            oakRepository.init();
            return new JcrNodeToDescription(evaluate(oakRepository));
        }
    }

    private Map<JcrNodeId, String> evaluate(JcrRepository jcrRepository) throws JcrMigrationException {
        Map<JcrNodeId, String> jcrNodeIdToDescription = new HashMap<>();
        try (JcrSession jcrSession = jcrRepository.login(AdministratorCredentials.create())) {
            JcrNode jcrRootNode = jcrSession.getRootNode();
            jcrRootNode.getChildNodes().stream()
                    .filter(jcrChildNode -> hasName("awesome folder", jcrChildNode))
                    .findFirst()
                    .ifPresent(awesomeFolder -> evaluateAwesomeFolder(jcrNodeIdToDescription, awesomeFolder));
        } catch (Exception e) {
            throw new JcrMigrationException(e);
        }
        return jcrNodeIdToDescription;
    }

    private void evaluateAwesomeFolder(Map<JcrNodeId, String> jcrNodeIdToDescription, JcrNode awesomeFolder) {
        try {
            jcrNodeIdToDescription.put(awesomeFolder.getNodeId(), "awesome folder description");
            awesomeFolder.getChildNodes().stream()
                    .filter(jcrChildNode -> hasName("subFolder", jcrChildNode))
                    .findFirst()
                    .ifPresent(subFolder -> evaluateSubFolder(jcrNodeIdToDescription, subFolder));
        } catch (RepositoryException e) {
            LOGGER.warn(String.format("Cannot evaluate JCR node %s", awesomeFolder));
        }
    }

    private void evaluateSubFolder(Map<JcrNodeId, String> jcrNodeIdToDescription, JcrNode subFolder) {
        try {
            subFolder.getChildNodes().stream()
                    .filter(jcrChildNode -> hasName("image", jcrChildNode))
                    .findFirst()
                    .ifPresent(image -> evaluateImage(jcrNodeIdToDescription, image));
        } catch (RepositoryException e) {
            LOGGER.warn(String.format("Cannot evaluate JCR node %s", subFolder));
        }
    }

    private boolean hasName(String name, JcrNode jcrNode) {
        try {
            return jcrNode.getName().equals(name);
        } catch (RepositoryException e) {
            LOGGER.warn(String.format("Cannot retrieve JCR node with name \"%s\"", name));
        }
        return false;
    }

    private void evaluateImage(Map<JcrNodeId, String> jcrNodeIdToDescription, JcrNode image) {
        try {
            StringBuilder descriptions = new StringBuilder();
            String versionsDescription = image.getVersions().stream()
                    .map(jcrNodeVersion -> evaluateImageVersion(jcrNodeVersion))
                    .collect(Collectors.joining(DESCRIPTION_SEPARATOR));
            descriptions.append(versionsDescription);
            descriptions.append(",image description");
            jcrNodeIdToDescription.put(image.getNodeId(), descriptions.toString());
        } catch (RepositoryException e) {
            LOGGER.warn(String.format("Cannot evaluate JCR node %s", image));
        }
    }

    private String evaluateImageVersion(JcrNodeVersion jcrNodeVersion) {
        try {
            return "image description " + jcrNodeVersion.getVersionNumber();
        } catch (RepositoryException e) {
            LOGGER.warn(String.format("Cannot evaluate JCR node version %s", jcrNodeVersion));
        }
        return "";
    }

}
