package it.mcella.jcr.oak.upgrade.repository.firstversion.persistence.file;

public class FilePersistenceException extends Exception {

    private static final long serialVersionUID = -3244817662809782746L;

    public FilePersistenceException(String fileName, Throwable cause) {
        super(String.format("Cannot create file \"%s\"", fileName), cause);
    }

}
