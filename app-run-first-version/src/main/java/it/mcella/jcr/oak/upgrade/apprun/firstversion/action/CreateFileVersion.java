package it.mcella.jcr.oak.upgrade.apprun.firstversion.action;

import it.mcella.jcr.oak.upgrade.repository.JcrNodeId;
import it.mcella.jcr.oak.upgrade.repository.OakNodeId;
import it.mcella.jcr.oak.upgrade.repository.firstversion.persistence.file.FileVersionPersistence;
import it.mcella.jcr.oak.upgrade.repository.firstversion.persistence.file.FileVersionPersistenceException;

import java.nio.file.Path;
import java.nio.file.Paths;

public class CreateFileVersion implements Action {

    private final FileVersionPersistence fileVersionPersistence;
    private final String fileNodeId;
    private final String filePath;
    private final String description;

    public CreateFileVersion(FileVersionPersistence fileVersionPersistence, String fileNodeId, String filePath, String description) {
        this.fileVersionPersistence = fileVersionPersistence;
        this.fileNodeId = fileNodeId;
        this.filePath = filePath;
        this.description = description;
    }

    @Override
    public ActionType getType() {
        return ActionType.CREATE_FILE_VERSION;
    }

    @Override
    public void execute() throws ActionException {
        try {
            JcrNodeId jcrFileNodeId = new OakNodeId(fileNodeId);
            Path file = Paths.get(filePath);
            fileVersionPersistence.createNewVersion(jcrFileNodeId, description, file);
        } catch (FileVersionPersistenceException e) {
            throw new ActionException("Cannot create node version", e);
        }
    }

    public String getFileNodeId() {
        return fileNodeId;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getDescription() {
        return description;
    }

}
