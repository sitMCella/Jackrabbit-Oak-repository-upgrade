package it.mcella.jcr.oak.upgrade.repository.secondversion.update;

import it.mcella.jcr.oak.upgrade.repository.JcrNode;
import it.mcella.jcr.oak.upgrade.repository.nodetypes.migration.JcrNodeVersionMigratorRunner;

import javax.jcr.RepositoryException;

public class JcrDeletablePropertyHistoryMigratorRunner implements JcrNodeVersionMigratorRunner {

    @Override
    public void run(JcrNode jcrNode) throws RepositoryException {
        if (jcrNode.hasProperty(JcrDeletablePropertyMigrator.DELETABLE_PROPERTY_NAME)) {
            jcrNode.removeProperty(jcrNode.getProperty(JcrDeletablePropertyMigrator.DELETABLE_PROPERTY_NAME));
        }
    }

}
