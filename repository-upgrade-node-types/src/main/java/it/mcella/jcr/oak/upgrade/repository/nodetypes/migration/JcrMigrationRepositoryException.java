package it.mcella.jcr.oak.upgrade.repository.nodetypes.migration;

public class JcrMigrationRepositoryException extends Exception {

    public JcrMigrationRepositoryException(String message) {
        super(message);
    }

    public JcrMigrationRepositoryException(Throwable cause) {
        super(cause);
    }

}
