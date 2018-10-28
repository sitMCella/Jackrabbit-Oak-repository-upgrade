package it.mcella.jcr.oak.upgrade.repository.secondversion.update;

import it.mcella.jcr.oak.upgrade.repository.JcrNamespaceConfiguration;
import it.mcella.jcr.oak.upgrade.repository.JcrRepository;
import it.mcella.jcr.oak.upgrade.repository.nodetypes.migration.JcrMigrationException;
import it.mcella.jcr.oak.upgrade.repository.nodetypes.migration.JcrMigratorRunner;
import it.mcella.jcr.oak.upgrade.repository.nodetypes.migration.JcrNodeMigrator;
import it.mcella.jcr.oak.upgrade.repository.nodetypes.migration.JcrNodeVersionMigrator;
import it.mcella.jcr.oak.upgrade.repository.nodetypes.migration.JcrNodeVersionMigratorRunner;
import it.mcella.jcr.oak.upgrade.repository.nodetypes.migration.OakMigrationParameters;
import it.mcella.jcr.oak.upgrade.repository.nodetypes.migration.OakMigrationRepository;
import it.mcella.jcr.oak.upgrade.repository.nodetypes.migration.OakNodeVersionMigrator;
import it.mcella.jcr.oak.upgrade.repository.nodetypes.migration.OakStepByStepMigratorRunner;

public class JcrDeletablePropertyMigrator {

    public static final String DELETABLE_PROPERTY_NAME = "app:deletable";

    private static final String CND_CONFIG_FILE_OLD_VERSION = "remove_deletable_property/cnd_old_version.config";
    private static final String CND_CONFIG_FILE_INTERMEDIATE_VERSION = "remove_deletable_property/cnd_intermediate_version.config";
    private static final String CND_CONFIG_FILE_NEW_VERSION = "remove_deletable_property/cnd_new_version.config";

    private final JcrMigratorRunner oakStepByStepMigratorRunner;

    public JcrDeletablePropertyMigrator(OakMigrationRepository oakMigrationRepository) {
        OakMigrationParameters oakMigrationParameters = new OakMigrationParameters(
                JcrUpgradeConstants.JCR_OLD_NAMESPACE,
                new JcrNamespaceConfiguration(CND_CONFIG_FILE_OLD_VERSION),
                JcrUpgradeConstants.JCR_INTERMEDIATE_NAMESPACE,
                new JcrNamespaceConfiguration(CND_CONFIG_FILE_INTERMEDIATE_VERSION),
                JcrUpgradeConstants.JCR_NEW_NAMESPACE,
                new JcrNamespaceConfiguration(CND_CONFIG_FILE_NEW_VERSION));
        JcrNodeVersionMigratorRunner jcrDeletablePropertyHistoryMigratorRunner = new JcrDeletablePropertyHistoryMigratorRunner();
        JcrNodeVersionMigrator jcrNodeVersionMigrator = new OakNodeVersionMigrator(
                jcrDeletablePropertyHistoryMigratorRunner,
                JcrUpgradeConstants.JCR_OLD_NAMESPACE,
                new JcrNamePropertyFilter(DELETABLE_PROPERTY_NAME));
        JcrNodeMigrator oakDeletablePropertyMigrator = new OakDeletablePropertyMigrator(jcrNodeVersionMigrator);
        this.oakStepByStepMigratorRunner = new OakStepByStepMigratorRunner(
                oakMigrationParameters,
                oakMigrationRepository,
                oakDeletablePropertyMigrator);
    }

    public void run(JcrRepository jcrRepository) throws JcrMigrationException {
        oakStepByStepMigratorRunner.run(jcrRepository);
    }

}
