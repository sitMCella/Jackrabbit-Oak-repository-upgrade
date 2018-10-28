package it.mcella.jcr.oak.upgrade.repository.secondversion.update;

import it.mcella.jcr.oak.upgrade.commons.PropertiesFileReader;
import it.mcella.jcr.oak.upgrade.repository.nodetypes.migration.JcrNodeVersionMigratorRunner;
import it.mcella.jcr.oak.upgrade.repository.nodetypes.migration.OakMigrationRepository;
import it.mcella.jcr.oak.upgrade.repository.nodetypes.migration.OakMigrationRepositoryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String... args) {
        try {
            OakMigrationRepository oakMigrationRepository = new OakMigrationRepositoryFactory().build();
            JcrNodeVersionMigratorRunner jcrSystemPropertyHistoryMigratorRunner = new JcrSystemPropertyHistoryMigratorRunner();
            Path oakFileSystemFolder = retrieveOakFileSystemFolder();
            RetrieveJcrNodesDescription retrieveJcrNodesDescription = new RetrieveJcrNodesDescription(oakFileSystemFolder);
            JcrNodeToDescription jcrNodeIdToDescription = retrieveJcrNodesDescription.evaluate();
            JcrNodeVersionMigratorRunner jcrDescriptionPropertyHistoryMigratorRunner = new JcrDescriptionPropertyHistoryMigratorRunner(jcrNodeIdToDescription);
            RepositorySecondVersion repositorySecondVersion = new RepositorySecondVersion(
                    new JcrNodeAttributesMixinMigrator(oakMigrationRepository),
                    new JcrSystemPropertyMigrator(jcrSystemPropertyHistoryMigratorRunner),
                    new JcrDeletablePropertyMigrator(oakMigrationRepository),
                    new JcrNodeDescriptionMigrator(jcrNodeIdToDescription, jcrDescriptionPropertyHistoryMigratorRunner));
            repositorySecondVersion.upgrade(oakFileSystemFolder);
        } catch (Exception e) {
            LOGGER.error("Error while running upgrade to second repository version", e);
            System.exit(1);
        }
    }

    private static Path retrieveOakFileSystemFolder() throws Exception {
        PropertiesFileReader propertiesFileReader = new PropertiesFileReader("settings.properties");
        String folderAbsolutePath = propertiesFileReader.readProperty("file-system");
        Path oakFileSystemFolder = FileSystems.getDefault().getPath(folderAbsolutePath);
        if (Files.notExists(oakFileSystemFolder) || !Files.isDirectory(oakFileSystemFolder)
                || !Files.isReadable(oakFileSystemFolder) || !Files.isWritable(oakFileSystemFolder)) {
            String errorMessage = String.format("Cannot find or wrong permissions on folder \"%s\"", folderAbsolutePath);
            throw new Exception(errorMessage);
        }
        return oakFileSystemFolder;
    }

}
