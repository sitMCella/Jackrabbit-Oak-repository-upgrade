package it.mcella.jcr.oak.upgrade.repository.secondversion.update;

import it.mcella.jcr.oak.upgrade.repository.JcrNamespaceConfiguration;
import it.mcella.jcr.oak.upgrade.repository.OakCustomNodeTypeDefinition;
import it.mcella.jcr.oak.upgrade.repository.OakRepository;
import it.mcella.jcr.oak.upgrade.repository.nodetypes.migration.OakSimpleMigratorRunner;

import java.nio.file.Path;

public class RepositorySecondVersion {

    private static final String CND_CONFIG_FILE_FIRST_VERSION = "remove_attributes_mixin/cnd_old_version.config";
    private static final String CND_CONFIG_FILE_WITHOUT_ATTRIBUTES_MIXIN_WITH_SYSTEM_PROPERTY = "add_system_property/cnd_new_version.config";
    private static final String CND_CONFIG_FILE_SECOND_VERSION = "add_describable_mixin/cnd_second_version.config";

    private final JcrNodeAttributesMixinMigrator jcrNodeAttributesMixinMigrator;
    private final JcrSystemPropertyMigrator jcrSystemPropertyMigrator;
    private final JcrDeletablePropertyMigrator jcrDeletablePropertyMigrator;
    private final JcrNodeDescriptionMigrator jcrNodeDescriptionMigrator;

    public RepositorySecondVersion(JcrNodeAttributesMixinMigrator jcrNodeAttributesMixinMigrator,
                                   JcrSystemPropertyMigrator jcrSystemPropertyMigrator,
                                   JcrDeletablePropertyMigrator jcrDeletablePropertyMigrator,
                                   JcrNodeDescriptionMigrator jcrNodeDescriptionMigrator) {
        this.jcrNodeAttributesMixinMigrator = jcrNodeAttributesMixinMigrator;
        this.jcrSystemPropertyMigrator = jcrSystemPropertyMigrator;
        this.jcrDeletablePropertyMigrator = jcrDeletablePropertyMigrator;
        this.jcrNodeDescriptionMigrator = jcrNodeDescriptionMigrator;
    }

    public void upgrade(Path oakFileSystemFolder) throws Exception {
        try {
            runRemoveAttributesMixinUpgrade(oakFileSystemFolder);
            runAddSystemPropertyIntoPropertiesMixinUpgrade(oakFileSystemFolder);
            runRemoveDeletablePropertyFromPropertiesMixinUpgrade(oakFileSystemFolder);
            runAddDescribableMixinUpgrade(oakFileSystemFolder);
        } catch (Exception ex) {
            throw new RepositorySecondVersionException("Cannot upgrade JCR repository", ex);
        }
    }

    private void runRemoveAttributesMixinUpgrade(Path oakFileSystemFolder) throws Exception {
        JcrNamespaceConfiguration jcrNamespaceConfiguration = new JcrNamespaceConfiguration(CND_CONFIG_FILE_FIRST_VERSION);
        OakCustomNodeTypeDefinition oakCustomNodeTypeDefinition = new OakCustomNodeTypeDefinition(jcrNamespaceConfiguration);
        try (OakRepository oakRepository = new OakRepository(oakFileSystemFolder, oakCustomNodeTypeDefinition)) {
            oakRepository.init();
            jcrNodeAttributesMixinMigrator.run(oakRepository);
        }
    }

    private void runAddSystemPropertyIntoPropertiesMixinUpgrade(Path oakFileSystemFolder) throws Exception {
        JcrNamespaceConfiguration jcrNamespaceConfiguration = new JcrNamespaceConfiguration(CND_CONFIG_FILE_WITHOUT_ATTRIBUTES_MIXIN_WITH_SYSTEM_PROPERTY);
        OakCustomNodeTypeDefinition oakCustomNodeTypeDefinition = new OakCustomNodeTypeDefinition(jcrNamespaceConfiguration);
        try (OakRepository oakRepository = new OakRepository(oakFileSystemFolder, oakCustomNodeTypeDefinition)) {
            oakRepository.init();
            OakSimpleMigratorRunner oakSimpleMigratorRunner = new OakSimpleMigratorRunner(jcrSystemPropertyMigrator);
            oakSimpleMigratorRunner.run(oakRepository);
        }
    }

    private void runRemoveDeletablePropertyFromPropertiesMixinUpgrade(Path oakFileSystemFolder) throws Exception {
        JcrNamespaceConfiguration jcrNamespaceConfiguration = new JcrNamespaceConfiguration(CND_CONFIG_FILE_WITHOUT_ATTRIBUTES_MIXIN_WITH_SYSTEM_PROPERTY);
        OakCustomNodeTypeDefinition oakCustomNodeTypeDefinition = new OakCustomNodeTypeDefinition(jcrNamespaceConfiguration);
        try (OakRepository oakRepository = new OakRepository(oakFileSystemFolder, oakCustomNodeTypeDefinition)) {
            oakRepository.init();
            jcrDeletablePropertyMigrator.run(oakRepository);
        }
    }

    private void runAddDescribableMixinUpgrade(Path oakFileSystemFolder) throws Exception {
        JcrNamespaceConfiguration jcrNamespaceConfiguration = new JcrNamespaceConfiguration(CND_CONFIG_FILE_SECOND_VERSION);
        OakCustomNodeTypeDefinition oakCustomNodeTypeDefinition = new OakCustomNodeTypeDefinition(jcrNamespaceConfiguration);
        try (OakRepository oakRepository = new OakRepository(oakFileSystemFolder, oakCustomNodeTypeDefinition)) {
            oakRepository.init();
            OakSimpleMigratorRunner oakSimpleMigratorRunner = new OakSimpleMigratorRunner(jcrNodeDescriptionMigrator);
            oakSimpleMigratorRunner.run(oakRepository);
        }
    }

}
