package it.mcella.jcr.oak.upgrade.apprun.secondversion.action;

import it.mcella.jcr.oak.upgrade.repository.JcrNodeId;
import it.mcella.jcr.oak.upgrade.repository.OakNodeId;
import it.mcella.jcr.oak.upgrade.repository.secondversion.persistence.file.FilePersistence;
import it.mcella.jcr.oak.upgrade.repository.secondversion.persistence.file.FilePersistenceException;

import java.nio.file.Path;
import java.nio.file.Paths;

public class CreateFile implements Action {

    private final FilePersistence filePersistence;
    private final String parentNodeId;
    private final String fileName;
    private final String filePath;
    private final boolean system;
    private final String description;

    public CreateFile(FilePersistence filePersistence, String parentNodeId, String fileName,
                      String filePath, boolean system, String description) {
        this.filePersistence = filePersistence;
        this.parentNodeId = parentNodeId;
        this.fileName = fileName;
        this.filePath = filePath;
        this.system = system;
        this.description = description;
    }

    @Override
    public ActionType getType() {
        return ActionType.CREATE_FILE;
    }

    @Override
    public void execute() throws ActionException {
        try {
            JcrNodeId jcrNodeId = new OakNodeId(parentNodeId);
            Path file = Paths.get(filePath);
            filePersistence.createInto(jcrNodeId, fileName, file, system, description);
        } catch (FilePersistenceException e) {
            throw new ActionException("Cannot create node", e);
        }
    }

    public String getParentNodeId() {
        return parentNodeId;
    }

    public String getFileName() {
        return fileName;
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
