package it.mcella.jcr.oak.upgrade.repository.nodetypes.jackrabbit;

public class JcrNodeTypesRegisterException extends Exception {

    private static final long serialVersionUID = 5982088769982381424L;

    public JcrNodeTypesRegisterException(Throwable cause) {
        super("Cannot register node types", cause);
    }

}
