package it.mcella.jcr.oak.upgrade.apprun.secondversion;

import it.mcella.jcr.oak.upgrade.apprun.secondversion.action.Action;
import it.mcella.jcr.oak.upgrade.apprun.secondversion.action.ActionFactory;
import it.mcella.jcr.oak.upgrade.apprun.secondversion.action.ActionType;
import it.mcella.jcr.oak.upgrade.apprun.secondversion.action.InteractiveActionConsole;
import it.mcella.jcr.oak.upgrade.commons.ConsoleReader;
import it.mcella.jcr.oak.upgrade.commons.ConsoleWriter;
import it.mcella.jcr.oak.upgrade.commons.PropertiesFileReader;
import it.mcella.jcr.oak.upgrade.repository.OakCustomNodeTypeDefinition;
import it.mcella.jcr.oak.upgrade.repository.OakRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static final void main(String... args) {
        try (OakRepository oakRepository = new OakRepository(retrieveOakFileSystemFolder(), new OakCustomNodeTypeDefinition())) {
            oakRepository.init();
            InteractiveActionConsole interactiveActionConsole = new InteractiveActionConsole(new ConsoleReader(), new ConsoleWriter());
            ActionFactory actionFactory = new ActionFactory(new ConsoleReader(), new ConsoleWriter(), interactiveActionConsole);
            Action action = actionFactory.createFrom(oakRepository);
            while (action.getType() != ActionType.QUIT) {
                action.execute();
                action = actionFactory.createFrom(oakRepository);
            }
        } catch (Exception e) {
            LOGGER.error("Error while running application", e);
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
