package it.mcella.jcr.oak.upgrade.repository.nodetypes.migration;

import it.mcella.jcr.oak.upgrade.repository.JcrRepository;

public interface JcrMigratorRunner {

    void run(JcrRepository jcrRepository) throws JcrMigrationException;

}
