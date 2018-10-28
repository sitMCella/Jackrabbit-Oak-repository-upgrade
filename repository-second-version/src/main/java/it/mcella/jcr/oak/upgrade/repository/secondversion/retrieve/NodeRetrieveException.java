package it.mcella.jcr.oak.upgrade.repository.secondversion.retrieve;

public class NodeRetrieveException extends Exception {

    private static final long serialVersionUID = -4101493960952363145L;

    public NodeRetrieveException(String message) {
        super(message);
    }

    public NodeRetrieveException(String message, Throwable cause) {
        super(message, cause);
    }

}
