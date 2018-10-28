package it.mcella.jcr.oak.upgrade.repository.secondversion.update;

import it.mcella.jcr.oak.upgrade.repository.JcrNamespaceConfiguration;
import it.mcella.jcr.oak.upgrade.repository.JcrRepository;
import it.mcella.jcr.oak.upgrade.repository.nodetypes.migration.JcrMigrationException;
import it.mcella.jcr.oak.upgrade.repository.nodetypes.migration.JcrMigratorRunner;
import it.mcella.jcr.oak.upgrade.repository.nodetypes.migration.JcrNodeMigrator;
import it.mcella.jcr.oak.upgrade.repository.nodetypes.migration.JcrNodeVersionMigrator;
import it.mcella.jcr.oak.upgrade.repository.nodetypes.migration.OakMigrationParameters;
import it.mcella.jcr.oak.upgrade.repository.nodetypes.migration.OakMigrationRepository;
import it.mcella.jcr.oak.upgrade.repository.nodetypes.migration.OakNodeVersionMigrator;
import it.mcella.jcr.oak.upgrade.repository.nodetypes.migration.OakStepByStepMigratorRunner;

public class JcrNodeAttributesMixinMigrator {

    public static final String HIDDEN_PROPERTY_NAME = "app:hidden";

    private static final String CND_CONFIG_FILE_OLD_VERSION = "remove_attributes_mixin/cnd_old_version.config";
    private static final String CND_CONFIG_FILE_INTERMEDIATE_VERSION = "remove_attributes_mixin/cnd_intermediate_version.config";
    private static final String CND_CONFIG_FILE_NEW_VERSION = "remove_attributes_mixin/cnd_new_version.config";

    private final JcrMigratorRunner oakStepByStepMigratorRunner;

    public JcrNodeAttributesMixinMigrator(OakMigrationRepository oakMigrationRepository) {
        OakMigrationParameters oakMigrationParameters = new OakMigrationParameters(
                JcrUpgradeConstants.JCR_OLD_NAMESPACE,
                new JcrNamespaceConfiguration(CND_CONFIG_FILE_OLD_VERSION),
                JcrUpgradeConstants.JCR_INTERMEDIATE_NAMESPACE,
                new JcrNamespaceConfiguration(CND_CONFIG_FILE_INTERMEDIATE_VERSION),
                JcrUpgradeConstants.JCR_NEW_NAMESPACE,
                new JcrNamespaceConfiguration(CND_CONFIG_FILE_NEW_VERSION));
        JcrHiddenPropertyHistoryMigratorRunner jcrHiddenPropertyHistoryMigratorRunner = new JcrHiddenPropertyHistoryMigratorRunner();
        JcrNodeVersionMigrator jcrNodeVersionMigrator = new OakNodeVersionMigrator(
                jcrHiddenPropertyHistoryMigratorRunner,
                JcrUpgradeConstants.JCR_OLD_NAMESPACE,
                new JcrNamePropertyFilter(HIDDEN_PROPERTY_NAME));
        JcrNodeMigrator oakHiddenPropertyMigrator = new OakHiddenPropertyMigrator(jcrNodeVersionMigrator);
        this.oakStepByStepMigratorRunner = new OakStepByStepMigratorRunner(
                oakMigrationParameters,
                oakMigrationRepository,
                oakHiddenPropertyMigrator);
    }

    public void run(JcrRepository jcrRepository) throws JcrMigrationException {
        oakStepByStepMigratorRunner.run(jcrRepository);
    }

}
