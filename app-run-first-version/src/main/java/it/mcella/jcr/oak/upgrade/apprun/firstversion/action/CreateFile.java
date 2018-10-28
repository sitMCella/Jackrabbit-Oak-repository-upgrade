package it.mcella.jcr.oak.upgrade.apprun.firstversion.action;

import it.mcella.jcr.oak.upgrade.repository.JcrNodeId;
import it.mcella.jcr.oak.upgrade.repository.OakNodeId;
import it.mcella.jcr.oak.upgrade.repository.firstversion.persistence.file.FilePersistence;
import it.mcella.jcr.oak.upgrade.repository.firstversion.persistence.file.FilePersistenceException;

import java.nio.file.Path;
import java.nio.file.Paths;

public class CreateFile implements Action {

    private final FilePersistence filePersistence;
    private final String parentNodeId;
    private final String fileName;
    private final boolean hidden;
    private final boolean deletable;
    private final String filePath;

    public CreateFile(FilePersistence filePersistence, String parentNodeId, String fileName, boolean hidden,
                      boolean deletable, String filePath) {
        this.filePersistence = filePersistence;
        this.parentNodeId = parentNodeId;
        this.fileName = fileName;
        this.hidden = hidden;
        this.deletable = deletable;
        this.filePath = filePath;
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
            filePersistence.createInto(jcrNodeId, fileName, hidden, deletable, file);
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

    public boolean isHidden() {
        return hidden;
    }

    public boolean isDeletable() {
        return deletable;
    }

    public String getFilePath() {
        return filePath;
    }

}
