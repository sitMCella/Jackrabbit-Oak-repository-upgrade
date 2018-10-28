package it.mcella.jcr.oak.upgrade.apprun.secondversion.action;

import it.mcella.jcr.oak.upgrade.repository.JcrRepository;
import it.mcella.jcr.oak.upgrade.repository.secondversion.node.file.FileNode;
import it.mcella.jcr.oak.upgrade.repository.secondversion.node.file.OakFileNodeFactory;
import it.mcella.jcr.oak.upgrade.repository.secondversion.node.file.OakFileVersionNodeFactory;
import it.mcella.jcr.oak.upgrade.repository.secondversion.node.folder.FolderNode;
import it.mcella.jcr.oak.upgrade.repository.secondversion.node.folder.OakFolderNodeFactory;
import it.mcella.jcr.oak.upgrade.repository.secondversion.persistence.file.FilePersistence;
import it.mcella.jcr.oak.upgrade.repository.secondversion.persistence.file.FilePersistenceException;
import it.mcella.jcr.oak.upgrade.repository.secondversion.persistence.file.FileVersionPersistence;
import it.mcella.jcr.oak.upgrade.repository.secondversion.persistence.file.FileVersionPersistenceException;
import it.mcella.jcr.oak.upgrade.repository.secondversion.persistence.folder.FolderPersistence;
import it.mcella.jcr.oak.upgrade.repository.secondversion.persistence.folder.FolderPersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class NodesSetup implements Action {

    private static final Logger LOGGER = LoggerFactory.getLogger(NodesSetup.class);

    private final JcrRepository jcrRepository;

    public NodesSetup(JcrRepository jcrRepository) {
        this.jcrRepository = jcrRepository;
    }

    @Override
    public ActionType getType() {
        return ActionType.SETUP;
    }

    @Override
    public void execute() throws ActionException {
        Path firstImage = null;
        Path secondImage = null;
        try (InputStream foxImageStream = ClassLoader.getSystemResourceAsStream("fox.jpg");
             InputStream crocusImageStream = ClassLoader.getSystemResourceAsStream("crocus.jpg")) {
            FolderPersistence folderPersistence = new FolderPersistence(jcrRepository, new OakFolderNodeFactory());
            FolderNode folderNode = folderPersistence.createIntoRoot("creative folder", "test folder");

            FilePersistence filePersistence = new FilePersistence(jcrRepository, new OakFileNodeFactory());
            firstImage = Files.createTempFile("fox", ".jpg");
            Files.copy(foxImageStream, firstImage, StandardCopyOption.REPLACE_EXISTING);
            boolean system = false;
            FileNode imageNode = filePersistence.createInto(folderNode, "creative image", firstImage, system, "fox image");

            FileVersionPersistence fileVersionPersistence = new FileVersionPersistence(jcrRepository, new OakFileVersionNodeFactory());
            secondImage = Files.createTempFile("crocus", ".jpg");
            Files.copy(crocusImageStream, secondImage, StandardCopyOption.REPLACE_EXISTING);
            system = true;
            fileVersionPersistence.createNewVersion(imageNode, system, "crocus image", secondImage);
        } catch (FolderPersistenceException | FilePersistenceException | FileVersionPersistenceException | IOException e) {
            throw new ActionException("Cannot setup nodes", e);
        } finally {
            if (firstImage != null && Files.exists(firstImage)) {
                try {
                    Files.delete(firstImage);
                } catch (IOException e) {
                    LOGGER.warn(String.format("Cannot remove file \"s\"", firstImage.toString()));
                }
            }
            if (secondImage != null && Files.exists(secondImage)) {
                try {
                    Files.delete(secondImage);
                } catch (IOException e) {
                    LOGGER.warn(String.format("Cannot remove file \"s\"", secondImage.toString()));
                }
            }
        }
    }

}
