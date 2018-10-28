package it.mcella.jcr.oak.upgrade.repository.nodetypes.migration;

import it.mcella.jcr.oak.upgrade.repository.JcrNamespace;
import it.mcella.jcr.oak.upgrade.repository.JcrSession;

public interface JcrNodesMigrator {

    void migrate(JcrNodeMigrator jcrNodeMigrator, JcrNamespace oldNamespace, JcrNamespace newNamespace, JcrSession jcrSession) throws JcrNodesMigratorException;

}
