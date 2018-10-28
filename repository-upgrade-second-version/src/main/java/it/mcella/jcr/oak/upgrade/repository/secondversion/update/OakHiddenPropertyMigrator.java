package it.mcella.jcr.oak.upgrade.repository.secondversion.update;

import it.mcella.jcr.oak.upgrade.repository.JcrNode;
import it.mcella.jcr.oak.upgrade.repository.nodetypes.migration.JcrNodeMigrator;
import it.mcella.jcr.oak.upgrade.repository.nodetypes.migration.JcrNodeMigratorException;
import it.mcella.jcr.oak.upgrade.repository.nodetypes.migration.JcrNodeVersionMigrator;
import org.apache.jackrabbit.JcrConstants;

import javax.jcr.RepositoryException;

public class OakHiddenPropertyMigrator implements JcrNodeMigrator {

    static final String INTERMEDIATE_HIDDEN_PROPERTY_NAME = "intermediate:hidden";

    private final JcrNodeVersionMigrator jcrNodeVersionMigrator;

    public OakHiddenPropertyMigrator(JcrNodeVersionMigrator jcrNodeVersionMigrator) {
        this.jcrNodeVersionMigrator = jcrNodeVersionMigrator;
    }

    @Override
    public void migrate(JcrNode jcrNode) throws JcrNodeMigratorException {
        try {
            if (jcrNode.hasProperty(INTERMEDIATE_HIDDEN_PROPERTY_NAME)) {
                jcrNode.removeProperty(jcrNode.getProperty(INTERMEDIATE_HIDDEN_PROPERTY_NAME));
            }
        } catch (RepositoryException e) {
            throw new JcrNodeMigratorException(e);
        }
    }

    @Override
    public void migrateHistory(JcrNode jcrNode) throws JcrNodeMigratorException {
        try {
            if (jcrNode.hasProperty(JcrConstants.JCR_MIXINTYPES)) {
                jcrNodeVersionMigrator.migrate(jcrNode);
            }
        } catch (RepositoryException e) {
            throw new JcrNodeMigratorException(e);
        }
    }

}
