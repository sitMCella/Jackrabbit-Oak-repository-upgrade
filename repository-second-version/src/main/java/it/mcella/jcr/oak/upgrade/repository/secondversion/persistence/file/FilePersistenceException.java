package it.mcella.jcr.oak.upgrade.repository.secondversion.persistence.file;

public class FilePersistenceException extends Exception {

    private static final long serialVersionUID = -8011161199833778215L;

    public FilePersistenceException(String fileName, Throwable cause) {
        super(String.format("Cannot create file \"%s\"", fileName), cause);
    }

}
