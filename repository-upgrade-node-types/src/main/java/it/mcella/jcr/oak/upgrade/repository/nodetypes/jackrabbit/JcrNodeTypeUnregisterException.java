package it.mcella.jcr.oak.upgrade.repository.nodetypes.jackrabbit;

public class JcrNodeTypeUnregisterException extends Exception {

    private static final long serialVersionUID = 2290321910026241523L;

    public JcrNodeTypeUnregisterException(Throwable cause) {
        super("Cannot unregister node types", cause);
    }

}
