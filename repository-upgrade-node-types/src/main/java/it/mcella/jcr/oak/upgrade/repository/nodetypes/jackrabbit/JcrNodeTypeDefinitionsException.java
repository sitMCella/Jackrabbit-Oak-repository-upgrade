package it.mcella.jcr.oak.upgrade.repository.nodetypes.jackrabbit;

public class JcrNodeTypeDefinitionsException extends Exception {

    private static final long serialVersionUID = -441117343925579409L;

    public JcrNodeTypeDefinitionsException(String message) {
        super(message);
    }

    public JcrNodeTypeDefinitionsException(String message, Throwable cause) {
        super(message, cause);
    }

}
