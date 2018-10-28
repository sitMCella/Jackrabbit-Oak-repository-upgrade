package it.mcella.jcr.oak.upgrade.repository.secondversion.persistence.file;

import it.mcella.jcr.oak.upgrade.repository.JcrNodeId;

public class FileVersionPersistenceException extends Exception {

    private static final long serialVersionUID = 6400404094088095264L;

    public FileVersionPersistenceException(JcrNodeId jcrFileNodeId, Throwable cause) {
        super(String.format("Cannot create new version of file node with id \"%s\"", jcrFileNodeId.getNodeId()), cause);
    }

}
