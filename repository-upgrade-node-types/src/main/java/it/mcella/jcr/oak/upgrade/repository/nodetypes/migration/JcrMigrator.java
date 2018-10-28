package it.mcella.jcr.oak.upgrade.repository.nodetypes.migration;

import it.mcella.jcr.oak.upgrade.repository.JcrSession;

public interface JcrMigrator {

    void upgrade(JcrSession jcrSession) throws JcrNodesMigratorException;

}
