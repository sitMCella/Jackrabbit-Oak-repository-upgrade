package it.mcella.jcr.oak.upgrade.apprun.firstversion.action;

import it.mcella.jcr.oak.upgrade.repository.JcrNodeId;
import it.mcella.jcr.oak.upgrade.repository.OakNodeId;
import it.mcella.jcr.oak.upgrade.repository.firstversion.retrieve.file.FileRetrieve;
import it.mcella.jcr.oak.upgrade.repository.firstversion.retrieve.file.FileRetrieveException;

import java.nio.file.Path;

public class RetrieveFile implements Action {

    private final FileRetrieve fileRetrieve;
    private final String nodeId;

    public RetrieveFile(FileRetrieve fileRetrieve, String nodeId) {
        this.fileRetrieve = fileRetrieve;
        this.nodeId = nodeId;
    }

    @Override
    public ActionType getType() {
        return ActionType.RETRIEVE_FILE;
    }

    @Override
    public void execute() throws ActionException {
        try {
            JcrNodeId jcrNodeId = new OakNodeId(nodeId);
            Path image = fileRetrieve.retrieveFrom(jcrNodeId);
            System.out.println(image);
        } catch (FileRetrieveException e) {
            throw new ActionException("Cannot retrieve file content", e);
        }
    }

    public String getNodeId() {
        return nodeId;
    }

}
