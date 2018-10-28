package it.mcella.jcr.oak.upgrade.repository.secondversion.persistence.folder;

public class FolderPersistenceException extends Exception {

    private static final long serialVersionUID = 7762197199173091261L;

    public FolderPersistenceException(String folderName, Throwable cause) {
        super(String.format("Cannot create folder \"%s\"", folderName), cause);
    }

}
