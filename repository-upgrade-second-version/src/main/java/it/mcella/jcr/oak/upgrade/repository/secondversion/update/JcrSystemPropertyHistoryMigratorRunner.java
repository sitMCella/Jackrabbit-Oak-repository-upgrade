package it.mcella.jcr.oak.upgrade.repository.secondversion.update;

import it.mcella.jcr.oak.upgrade.repository.JcrNode;
import it.mcella.jcr.oak.upgrade.repository.nodetypes.migration.JcrNodeVersionMigratorRunner;

import javax.jcr.RepositoryException;

public class JcrSystemPropertyHistoryMigratorRunner implements JcrNodeVersionMigratorRunner {

    @Override
    public void run(JcrNode jcrNode) throws RepositoryException {
        jcrNode.setProperty(JcrSystemPropertyMigrator.SYSTEM_PROPERTY, false);
    }

}
