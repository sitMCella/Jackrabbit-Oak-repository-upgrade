package it.mcella.jcr.oak.upgrade.repository.secondversion.persistence.file;

import it.mcella.jcr.oak.upgrade.repository.AdministratorCredentials;
import it.mcella.jcr.oak.upgrade.repository.JcrNode;
import it.mcella.jcr.oak.upgrade.repository.JcrNodeId;
import it.mcella.jcr.oak.upgrade.repository.JcrRepository;
import it.mcella.jcr.oak.upgrade.repository.JcrSession;
import it.mcella.jcr.oak.upgrade.repository.secondversion.node.file.FileNode;
import it.mcella.jcr.oak.upgrade.repository.secondversion.node.file.JcrFileNode;
import it.mcella.jcr.oak.upgrade.repository.secondversion.node.file.JcrFileNodeFactory;
import it.mcella.jcr.oak.upgrade.repository.secondversion.node.folder.FolderNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.RepositoryException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class FilePersistence {

    private static final Logger LOGGER = LoggerFactory.getLogger(FilePersistence.class);

    private final JcrRepository jcrRepository;
    private final JcrFileNodeFactory jcrFileNodeFactory;

    public FilePersistence(JcrRepository jcrRepository, JcrFileNodeFactory jcrFileNodeFactory) {
        this.jcrRepository = jcrRepository;
        this.jcrFileNodeFactory = jcrFileNodeFactory;
    }

    public FileNode createIntoRoot(String fileName, Path file, boolean system, String description) throws FilePersistenceException {
        try (JcrSession jcrSession = jcrRepository.login(AdministratorCredentials.create());
             InputStream inputStream = Files.newInputStream(file)) {
            JcrNode rootNode = jcrSession.getRootNode();
            JcrFileNode jcrFileNode = createFileNode(rootNode, fileName, file, system, description, jcrSession, inputStream);
            jcrSession.save();
            FileNode fileNode = jcrFileNode.toNode();
            LOGGER.info("Created file:" + fileNode);
            return fileNode;
        } catch (Exception e) {
            throw new FilePersistenceException(fileName, e);
        }
    }

    public FileNode createInto(FolderNode parentFolderNode, String fileName, Path file, boolean system, String description) throws FilePersistenceException {
        return createInto(parentFolderNode.getJcrNodeId(), fileName, file, system, description);
    }

    public FileNode createInto(JcrNodeId jcrParentNodeId, String fileName, Path file, boolean system, String description) throws FilePersistenceException {
        try (JcrSession jcrSession = jcrRepository.login(AdministratorCredentials.create());
             InputStream inputStream = Files.newInputStream(file)) {
            JcrNode parentNode = jcrSession.getNodeById(jcrParentNodeId);
            JcrFileNode jcrFileNode = createFileNode(parentNode, fileName, file, system, description, jcrSession, inputStream);
            jcrSession.save();
            FileNode fileNode = jcrFileNode.toNode();
            LOGGER.info("Created file:" + fileNode);
            return fileNode;
        } catch (Exception e) {
            throw new FilePersistenceException(fileName, e);
        }
    }

    private JcrFileNode createFileNode(JcrNode parentNode, String fileName, Path file, boolean system, String description,
                                       JcrSession jcrSession, InputStream inputStream) throws RepositoryException, IOException {
        JcrNode fileNode = parentNode.addNode(fileName, JcrFileNode.NODE_TYPE);
        JcrFileNode jcrFileNode = jcrFileNodeFactory.createFrom(fileNode);
        jcrFileNode.setSystem(system);
        jcrFileNode.setDescription(description);
        jcrFileNode.createContent(jcrSession, file, inputStream);
        return jcrFileNode;
    }

}
