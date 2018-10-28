package it.mcella.jcr.oak.upgrade.repository.nodetypes.migration;

import it.mcella.jcr.oak.upgrade.repository.JcrNode;

public interface JcrNodeMigrator {

    void migrate(JcrNode jcrNode) throws JcrNodeMigratorException;

    void migrateHistory(JcrNode jcrNode) throws JcrNodeMigratorException;

}
