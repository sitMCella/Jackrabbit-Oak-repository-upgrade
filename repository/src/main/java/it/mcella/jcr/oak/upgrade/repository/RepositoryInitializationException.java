package it.mcella.jcr.oak.upgrade.repository;

public class RepositoryInitializationException extends Exception {

    private static final long serialVersionUID = -4482762335304416954L;

    public RepositoryInitializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public RepositoryInitializationException(Throwable cause) {
        super("Cannot initialize oak repository", cause);
    }

}
