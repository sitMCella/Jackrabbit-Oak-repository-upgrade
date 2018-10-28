package it.mcella.jcr.oak.upgrade.repository.nodetypes.migration;

import it.mcella.jcr.oak.upgrade.repository.JcrNode;

import javax.jcr.RepositoryException;

public interface JcrNodeVersionMigratorRunner {

    void run(JcrNode jcrNode) throws RepositoryException;

}
