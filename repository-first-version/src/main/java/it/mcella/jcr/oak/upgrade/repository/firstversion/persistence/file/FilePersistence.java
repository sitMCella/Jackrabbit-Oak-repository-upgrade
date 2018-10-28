package it.mcella.jcr.oak.upgrade.repository.firstversion.persistence.file;

import it.mcella.jcr.oak.upgrade.repository.AdministratorCredentials;
import it.mcella.jcr.oak.upgrade.repository.JcrNode;
import it.mcella.jcr.oak.upgrade.repository.JcrNodeId;
import it.mcella.jcr.oak.upgrade.repository.JcrRepository;
import it.mcella.jcr.oak.upgrade.repository.JcrSession;
import it.mcella.jcr.oak.upgrade.repository.firstversion.node.file.FileNode;
import it.mcella.jcr.oak.upgrade.repository.firstversion.node.file.JcrFileNode;
import it.mcella.jcr.oak.upgrade.repository.firstversion.node.file.JcrFileNodeFactory;
import it.mcella.jcr.oak.upgrade.repository.firstversion.node.folder.FolderNode;
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

    public FileNode createInto(FolderNode parentFolderNode, String fileName, boolean hidden, boolean deletable, Path file) throws FilePersistenceException {
        return createInto(parentFolderNode.getJcrNodeId(), fileName, hidden, deletable, file);
    }

    public FileNode createInto(JcrNodeId jcrParentNodeId, String fileName, boolean hidden, boolean deletable, Path file) throws FilePersistenceException {
        try (JcrSession jcrSession = jcrRepository.login(AdministratorCredentials.create());
             InputStream inputStream = Files.newInputStream(file)) {
            JcrNode parentNode = jcrSession.getNodeById(jcrParentNodeId);
            JcrFileNode jcrFileNode = createFileNode(parentNode, fileName, hidden, deletable, file, jcrSession, inputStream);
            jcrSession.save();
            FileNode fileNode = jcrFileNode.toNode();
            LOGGER.info("Created file: " + fileNode);
            return fileNode;
        } catch (Exception e) {
            throw new FilePersistenceException(fileName, e);
        }
    }

    private JcrFileNode createFileNode(JcrNode parentNode, String fileName, boolean hidden, boolean deletable, Path file,
                                       JcrSession jcrSession, InputStream inputStream) throws RepositoryException, IOException {
        JcrNode fileNode = parentNode.addNode(fileName, JcrFileNode.NODE_TYPE);
        JcrFileNode jcrFileNode = jcrFileNodeFactory.createFrom(fileNode);
        jcrFileNode.setHidden(hidden);
        jcrFileNode.setDeletable(deletable);
        jcrFileNode.createContent(jcrSession, file, inputStream);
        return jcrFileNode;
    }

}
