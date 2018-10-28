package it.mcella.jcr.oak.upgrade.apprun.secondversion.action;

import it.mcella.jcr.oak.upgrade.commons.ConsoleReader;
import it.mcella.jcr.oak.upgrade.commons.ConsoleWriter;
import it.mcella.jcr.oak.upgrade.repository.JcrRepository;
import it.mcella.jcr.oak.upgrade.repository.secondversion.node.file.JcrFileNodeFactory;
import it.mcella.jcr.oak.upgrade.repository.secondversion.node.file.JcrFileVersionNodeFactory;
import it.mcella.jcr.oak.upgrade.repository.secondversion.node.file.OakFileNodeFactory;
import it.mcella.jcr.oak.upgrade.repository.secondversion.node.file.OakFileVersionNodeFactory;
import it.mcella.jcr.oak.upgrade.repository.secondversion.node.folder.JcrFolderNodeFactory;
import it.mcella.jcr.oak.upgrade.repository.secondversion.node.folder.OakFolderNodeFactory;
import it.mcella.jcr.oak.upgrade.repository.secondversion.persistence.file.FilePersistence;
import it.mcella.jcr.oak.upgrade.repository.secondversion.persistence.file.FileVersionPersistence;
import it.mcella.jcr.oak.upgrade.repository.secondversion.persistence.folder.FolderPersistence;
import it.mcella.jcr.oak.upgrade.repository.secondversion.retrieve.NodeRetrieve;
import it.mcella.jcr.oak.upgrade.repository.secondversion.retrieve.NodeVersionRetrieve;
import it.mcella.jcr.oak.upgrade.repository.secondversion.retrieve.file.FileRetrieve;

import java.util.List;

public class ActionFactory {

    private final ConsoleReader consoleReader;
    private final ConsoleWriter consoleWriter;
    private final InteractiveActionConsole interactiveActionConsole;

    public ActionFactory(ConsoleReader consoleReader, ConsoleWriter consoleWriter, InteractiveActionConsole interactiveActionConsole) {
        this.consoleReader = consoleReader;
        this.consoleWriter = consoleWriter;
        this.interactiveActionConsole = interactiveActionConsole;
    }

    public Action createFrom(JcrRepository jcrRepository) {
        consoleWriter.print(String.format("\nAction [%s]: ", ActionType.HELP.getName()));
        String actionName = consoleReader.readLine();
        ActionType actionType = ActionType.from(actionName);
        Action action;
        List<String> responses;
        switch (actionType) {
            case QUIT:
                action = new Quit();
                break;
            case HELP:
                action = new Help(new ConsoleWriter());
                break;
            case SETUP:
                action = new NodesSetup(jcrRepository);
                break;
            case LIST:
                action = listNodesAction(jcrRepository);
                break;
            case CREATE_FOLDER:
                responses = getCreateFolderResponses();
                action = createFolderAction(responses, jcrRepository);
                break;
            case CREATE_FILE:
                responses = getCreateFileResponses();
                action = createFileAction(responses, jcrRepository);
                break;
            case CREATE_FILE_VERSION:
                responses = getCreateFileVersionResponses();
                action = createFileVersionAction(responses, jcrRepository);
                break;
            case RETRIEVE_FILE:
                responses = getRetrieveFileResponses();
                action = retrieveFileAction(responses, jcrRepository);
                break;
            default:
                action = new Help(new ConsoleWriter());
                break;
        }
        return action;
    }

    private Action listNodesAction(JcrRepository jcrRepository) {
        NodeRetrieve nodeRetrieve = new NodeRetrieve(jcrRepository);
        NodeVersionRetrieve nodeVersionRetrieve = new NodeVersionRetrieve(jcrRepository);
        return new ListNodes(nodeRetrieve, nodeVersionRetrieve, new ConsoleWriter());
    }

    private List<String> getCreateFolderResponses() {
        interactiveActionConsole.reset();
        interactiveActionConsole.addQuestion("Parent Node ID");
        interactiveActionConsole.addQuestion("Folder Name");
        interactiveActionConsole.addQuestion("Description");
        return interactiveActionConsole.retrieveResponses();
    }

    private Action createFolderAction(List<String> responses, JcrRepository jcrRepository) {
        String parentNodeId = responses.get(0);
        String folderName = responses.get(1);
        String description = responses.get(2);
        JcrFolderNodeFactory jcrFolderNodeFactory = new OakFolderNodeFactory();
        FolderPersistence folderPersistence = new FolderPersistence(jcrRepository, jcrFolderNodeFactory);
        return new CreateFolder(folderPersistence, parentNodeId, folderName, description);
    }

    private List<String> getCreateFileResponses() {
        interactiveActionConsole.reset();
        interactiveActionConsole.addQuestion("Parent Node ID");
        interactiveActionConsole.addQuestion("File Name");
        interactiveActionConsole.addQuestion("File Path");
        interactiveActionConsole.addQuestion("Is system");
        interactiveActionConsole.addQuestion("Description");
        return interactiveActionConsole.retrieveResponses();
    }

    private Action createFileAction(List<String> responses, JcrRepository jcrRepository) {
        String parentNodeId = responses.get(0);
        String fileName = responses.get(1);
        String filePath = responses.get(2);
        boolean system = Boolean.parseBoolean(responses.get(3));
        String description = responses.get(4);
        JcrFileNodeFactory jcrFileNodeFactory = new OakFileNodeFactory();
        FilePersistence filePersistence = new FilePersistence(jcrRepository, jcrFileNodeFactory);
        return new CreateFile(filePersistence, parentNodeId, fileName, filePath, system, description);
    }

    private List<String> getCreateFileVersionResponses() {
        interactiveActionConsole.reset();
        interactiveActionConsole.addQuestion("File Node ID");
        interactiveActionConsole.addQuestion("File Path");
        interactiveActionConsole.addQuestion("Is system");
        interactiveActionConsole.addQuestion("Description");
        return interactiveActionConsole.retrieveResponses();
    }

    private Action createFileVersionAction(List<String> responses, JcrRepository jcrRepository) {
        String fileNodeId = responses.get(0);
        String filePath = responses.get(1);
        boolean system = Boolean.parseBoolean(responses.get(2));
        String description = responses.get(3);
        JcrFileVersionNodeFactory jcrFileVersionNodeFactory = new OakFileVersionNodeFactory();
        FileVersionPersistence fileVersionPersistence = new FileVersionPersistence(jcrRepository, jcrFileVersionNodeFactory);
        return new CreateFileVersion(fileVersionPersistence, fileNodeId, filePath, system, description);
    }

    private List<String> getRetrieveFileResponses() {
        interactiveActionConsole.reset();
        interactiveActionConsole.addQuestion("Node ID");
        return interactiveActionConsole.retrieveResponses();
    }

    private Action retrieveFileAction(List<String> responses, JcrRepository jcrRepository) {
        String nodeId = responses.get(0);
        FileRetrieve fileRetrieve = new FileRetrieve(jcrRepository);
        return new RetrieveFile(fileRetrieve, nodeId);
    }

}
