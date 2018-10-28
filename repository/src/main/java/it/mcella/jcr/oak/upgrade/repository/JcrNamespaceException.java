package it.mcella.jcr.oak.upgrade.repository;

public class JcrNamespaceException extends Exception {

    private static final long serialVersionUID = 2714027076506993729L;

    public JcrNamespaceException(String message) {
        super(message);
    }

    public JcrNamespaceException(String message, Throwable cause) {
        super(message, cause);
    }

}
