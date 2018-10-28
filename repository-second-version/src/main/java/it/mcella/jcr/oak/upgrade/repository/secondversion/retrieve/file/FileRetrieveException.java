package it.mcella.jcr.oak.upgrade.repository.secondversion.retrieve.file;

import it.mcella.jcr.oak.upgrade.repository.JcrNodeId;

public class FileRetrieveException extends Exception {

    private static final long serialVersionUID = -8913344308896875569L;

    public FileRetrieveException(JcrNodeId jcrNodeId, Throwable e) {
        super(String.format("Cannot retrieve node with id \"%s\"", jcrNodeId.getNodeId()), e);
    }

}
