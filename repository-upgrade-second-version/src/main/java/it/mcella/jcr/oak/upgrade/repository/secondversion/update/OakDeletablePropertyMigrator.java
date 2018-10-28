package it.mcella.jcr.oak.upgrade.repository.secondversion.update;

import it.mcella.jcr.oak.upgrade.repository.JcrNode;
import it.mcella.jcr.oak.upgrade.repository.nodetypes.migration.JcrNodeMigrator;
import it.mcella.jcr.oak.upgrade.repository.nodetypes.migration.JcrNodeMigratorException;
import it.mcella.jcr.oak.upgrade.repository.nodetypes.migration.JcrNodeVersionMigrator;
import org.apache.jackrabbit.JcrConstants;

import javax.jcr.RepositoryException;

public class OakDeletablePropertyMigrator implements JcrNodeMigrator {

    static final String INTERMEDIATE_DELETABLE_PROPERTY_NAME = "intermediate:deletable";

    private final JcrNodeVersionMigrator jcrNodeVersionMigrator;

    public OakDeletablePropertyMigrator(JcrNodeVersionMigrator jcrNodeVersionMigrator) {
        this.jcrNodeVersionMigrator = jcrNodeVersionMigrator;
    }

    @Override
    public void migrate(JcrNode jcrNode) throws JcrNodeMigratorException {
        try {
            if (jcrNode.hasProperty(INTERMEDIATE_DELETABLE_PROPERTY_NAME)) {
                jcrNode.removeProperty(jcrNode.getProperty(INTERMEDIATE_DELETABLE_PROPERTY_NAME));
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
