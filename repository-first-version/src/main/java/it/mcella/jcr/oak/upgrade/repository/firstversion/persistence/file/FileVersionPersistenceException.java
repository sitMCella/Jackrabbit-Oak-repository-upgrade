package it.mcella.jcr.oak.upgrade.repository.firstversion.persistence.file;

import it.mcella.jcr.oak.upgrade.repository.JcrNodeId;

public class FileVersionPersistenceException extends Exception {

    private static final long serialVersionUID = 4446184963569524744L;

    public FileVersionPersistenceException(JcrNodeId jcrFileNodeId, Throwable cause) {
        super(String.format("Cannot create new version of file node with id \"%s\"", jcrFileNodeId.getNodeId()), cause);
    }

}
