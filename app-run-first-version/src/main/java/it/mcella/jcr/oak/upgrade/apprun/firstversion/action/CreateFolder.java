package it.mcella.jcr.oak.upgrade.apprun.firstversion.action;

import it.mcella.jcr.oak.upgrade.repository.JcrNodeId;
import it.mcella.jcr.oak.upgrade.repository.OakNodeId;
import it.mcella.jcr.oak.upgrade.repository.firstversion.persistence.folder.FolderPersistence;
import it.mcella.jcr.oak.upgrade.repository.firstversion.persistence.folder.FolderPersistenceException;

public class CreateFolder implements Action {

    private final FolderPersistence folderPersistence;
    private final String parentNodeId;
    private final String folderName;
    private final boolean hidden;

    public CreateFolder(FolderPersistence folderPersistence, String parentNodeId, String folderName, boolean hidden) {
        this.folderPersistence = folderPersistence;
        this.parentNodeId = parentNodeId;
        this.folderName = folderName;
        this.hidden = hidden;
    }

    @Override
    public ActionType getType() {
        return ActionType.CREATE_FOLDER;
    }

    @Override
    public void execute() throws ActionException {
        try {
            JcrNodeId jcrNodeId = new OakNodeId(parentNodeId);
            folderPersistence.createInto(jcrNodeId, folderName, hidden);
        } catch (FolderPersistenceException e) {
            throw new ActionException("Cannot create node", e);
        }
    }

    public String getParentNodeId() {
        return parentNodeId;
    }

    public String getFolderName() {
        return folderName;
    }

    public boolean isHidden() {
        return hidden;
    }

}
