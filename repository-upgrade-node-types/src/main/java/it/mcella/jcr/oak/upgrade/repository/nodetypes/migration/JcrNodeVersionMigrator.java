package it.mcella.jcr.oak.upgrade.repository.nodetypes.migration;

import it.mcella.jcr.oak.upgrade.repository.JcrNode;

import javax.jcr.RepositoryException;

public interface JcrNodeVersionMigrator {

    void migrate(JcrNode jcrNode) throws RepositoryException;

}
