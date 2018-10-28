package it.mcella.jcr.oak.upgrade.repository.firstversion.persistence.folder;

import it.mcella.jcr.oak.upgrade.repository.AdministratorCredentials;
import it.mcella.jcr.oak.upgrade.repository.JcrNode;
import it.mcella.jcr.oak.upgrade.repository.JcrNodeId;
import it.mcella.jcr.oak.upgrade.repository.JcrRepository;
import it.mcella.jcr.oak.upgrade.repository.JcrSession;
import it.mcella.jcr.oak.upgrade.repository.firstversion.node.folder.FolderNode;
import it.mcella.jcr.oak.upgrade.repository.firstversion.node.folder.JcrFolderNode;
import it.mcella.jcr.oak.upgrade.repository.firstversion.node.folder.JcrFolderNodeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.RepositoryException;

public class FolderPersistence {

    private static final Logger LOGGER = LoggerFactory.getLogger(FolderPersistence.class);

    private final JcrRepository jcrRepository;
    private final JcrFolderNodeFactory jcrFolderNodeFactory;

    public FolderPersistence(JcrRepository jcrRepository, JcrFolderNodeFactory jcrFolderNodeFactory) {
        this.jcrRepository = jcrRepository;
        this.jcrFolderNodeFactory = jcrFolderNodeFactory;
    }

    public FolderNode createIntoRoot(String folderName, boolean hidden) throws FolderPersistenceException {
        try (JcrSession jcrSession = jcrRepository.login(AdministratorCredentials.create())) {
            JcrNode jcrRootNode = jcrSession.getRootNode();
            JcrFolderNode jcrFolderNode = createFolderNode(jcrRootNode, folderName, hidden);
            jcrSession.save();
            FolderNode folderNode = jcrFolderNode.toNode();
            LOGGER.info("Created folder: " + folderNode);
            return folderNode;
        } catch (Exception e) {
            throw new FolderPersistenceException(folderName, e);
        }
    }

    public FolderNode createInto(FolderNode parentFolderNode, String folderName, boolean hidden) throws FolderPersistenceException {
        return createInto(parentFolderNode.getJcrNodeId(), folderName, hidden);
    }

    public FolderNode createInto(JcrNodeId jcrParentNodeId, String folderName, boolean hidden) throws FolderPersistenceException {
        try (JcrSession jcrSession = jcrRepository.login(AdministratorCredentials.create())) {
            JcrNode jcrParentFolderNode = jcrSession.getNodeById(jcrParentNodeId);
            JcrFolderNode jcrFolderNode = createFolderNode(jcrParentFolderNode, folderName, hidden);
            jcrSession.save();
            FolderNode folderNode = jcrFolderNode.toNode();
            LOGGER.info("Created folder: " + folderNode);
            return folderNode;
        } catch (Exception e) {
            throw new FolderPersistenceException(folderName, e);
        }
    }

    private JcrFolderNode createFolderNode(JcrNode jcrParentFolderNode, String folderName, boolean hidden) throws RepositoryException {
        JcrNode jcrNode = jcrParentFolderNode.addNode(folderName, JcrFolderNode.NODE_TYPE);
        JcrFolderNode jcrFolderNode = jcrFolderNodeFactory.createFrom(jcrNode);
        jcrFolderNode.setHidden(hidden);
        return jcrFolderNode;
    }

}
