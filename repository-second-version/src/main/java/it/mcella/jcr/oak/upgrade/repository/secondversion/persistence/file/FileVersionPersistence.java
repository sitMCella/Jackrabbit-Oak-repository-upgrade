package it.mcella.jcr.oak.upgrade.repository.secondversion.persistence.file;

import it.mcella.jcr.oak.upgrade.repository.AdministratorCredentials;
import it.mcella.jcr.oak.upgrade.repository.JcrNode;
import it.mcella.jcr.oak.upgrade.repository.JcrNodeId;
import it.mcella.jcr.oak.upgrade.repository.JcrRepository;
import it.mcella.jcr.oak.upgrade.repository.JcrSession;
import it.mcella.jcr.oak.upgrade.repository.secondversion.node.file.FileNode;
import it.mcella.jcr.oak.upgrade.repository.secondversion.node.file.FileVersionNode;
import it.mcella.jcr.oak.upgrade.repository.secondversion.node.file.JcrFileNode;
import it.mcella.jcr.oak.upgrade.repository.secondversion.node.file.JcrFileVersionNode;
import it.mcella.jcr.oak.upgrade.repository.secondversion.node.file.JcrFileVersionNodeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

public class FileVersionPersistence {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileVersionPersistence.class);

    private final JcrRepository jcrRepository;
    private final JcrFileVersionNodeFactory jcrFileVersionNodeFactory;

    public FileVersionPersistence(JcrRepository jcrRepository, JcrFileVersionNodeFactory jcrFileVersionNodeFactory) {
        this.jcrRepository = jcrRepository;
        this.jcrFileVersionNodeFactory = jcrFileVersionNodeFactory;
    }

    public FileVersionNode createNewVersion(FileNode fileNode, boolean system, String description, Path file) throws FileVersionPersistenceException {
        return this.createNewVersion(fileNode.getJcrNodeId(), system, description, file);
    }

    public FileVersionNode createNewVersion(JcrNodeId jcrFileNodeId, boolean system, String description, Path file) throws FileVersionPersistenceException {
        try (JcrSession jcrSession = jcrRepository.login(AdministratorCredentials.create())) {
            JcrNode jcrNode = jcrSession.getNodeById(jcrFileNodeId);
            if (!jcrNode.hasType(JcrFileNode.NODE_TYPE)) {
                throw new Exception(String.format("Jcr node with id \"s\" is not a file node", jcrFileNodeId));
            }
            JcrFileVersionNode jcrFileVersionNode = jcrFileVersionNodeFactory.createFrom(jcrNode);
            if (!jcrFileVersionNode.isVersioned()) {
                FileVersionNode fileFirstVersionNode = jcrFileVersionNode.firstVersion();
                LOGGER.info("Created file version: " + fileFirstVersionNode);
            }
            FileVersionNode fileVersionNode = jcrFileVersionNode.newVersion(system, description, jcrSession, file);
            LOGGER.info("Created file version:" + fileVersionNode);
            return fileVersionNode;
        } catch (Exception e) {
            throw new FileVersionPersistenceException(jcrFileNodeId, e);
        }
    }

}
