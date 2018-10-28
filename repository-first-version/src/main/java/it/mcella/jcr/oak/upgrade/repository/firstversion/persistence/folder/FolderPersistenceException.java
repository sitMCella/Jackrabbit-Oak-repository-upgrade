package it.mcella.jcr.oak.upgrade.repository.firstversion.persistence.folder;

public class FolderPersistenceException extends Exception {

    private static final long serialVersionUID = -8410507862787973923L;

    public FolderPersistenceException(String folderName, Throwable cause) {
        super(String.format("Cannot create folder \"%s\"", folderName), cause);
    }

}
