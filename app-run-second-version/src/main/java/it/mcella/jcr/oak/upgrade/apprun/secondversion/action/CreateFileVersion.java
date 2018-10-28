package it.mcella.jcr.oak.upgrade.apprun.secondversion.action;

import it.mcella.jcr.oak.upgrade.repository.JcrNodeId;
import it.mcella.jcr.oak.upgrade.repository.OakNodeId;
import it.mcella.jcr.oak.upgrade.repository.secondversion.persistence.file.FileVersionPersistence;
import it.mcella.jcr.oak.upgrade.repository.secondversion.persistence.file.FileVersionPersistenceException;

import java.nio.file.Path;
import java.nio.file.Paths;

public class CreateFileVersion implements Action {

    private final FileVersionPersistence fileVersionPersistence;
    private final String fileNodeId;
    private final String filePath;
    private final boolean system;
    private final String description;

    public CreateFileVersion(FileVersionPersistence fileVersionPersistence, String fileNodeId, String filePath, boolean system, String description) {
        this.fileVersionPersistence = fileVersionPersistence;
        this.fileNodeId = fileNodeId;
        this.filePath = filePath;
        this.system = system;
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
            fileVersionPersistence.createNewVersion(jcrFileNodeId, system, description, file);
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

    public boolean isSystem() {
        return system;
    }

    public String getDescription() {
        return description;
    }

}
