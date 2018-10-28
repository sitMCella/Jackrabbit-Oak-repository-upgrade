package it.mcella.jcr.oak.upgrade.apprun.firstversion.action;

import it.mcella.jcr.oak.upgrade.commons.ConsoleReader;
import it.mcella.jcr.oak.upgrade.commons.ConsoleWriter;
import it.mcella.jcr.oak.upgrade.repository.JcrRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ActionFactoryTest {

    private final ConsoleReader consoleReader = mock(ConsoleReader.class);
    private final ConsoleWriter consoleWriter = mock(ConsoleWriter.class);
    private final InteractiveActionConsole interactiveActionConsole = mock(InteractiveActionConsole.class);

    private ActionFactory actionFactory;
    private JcrRepository jcrRepository;

    @Before
    public void setUp() throws Exception {
        actionFactory = new ActionFactory(consoleReader, consoleWriter, interactiveActionConsole);
        jcrRepository = null;
    }

    @Test
    public void shouldReturnQuitActionInstance() throws Exception {
        when(consoleReader.readLine()).thenReturn(ActionType.QUIT.getName());

        Action action = actionFactory.createFrom(jcrRepository);

        assertTrue(action instanceof Quit);
    }

    @Test
    public void shouldReturnHelpActionInstance() throws Exception {
        when(consoleReader.readLine()).thenReturn(ActionType.HELP.getName());

        Action action = actionFactory.createFrom(jcrRepository);

        assertTrue(action instanceof Help);
    }

    @Test
    public void shouldReturnNodesSetupActionInstance() throws Exception {
        when(consoleReader.readLine()).thenReturn(ActionType.SETUP.getName());

        Action action = actionFactory.createFrom(jcrRepository);

        assertTrue(action instanceof NodesSetup);
    }

    @Test
    public void shouldReturnListNodesActionInstance() throws Exception {
        when(consoleReader.readLine()).thenReturn(ActionType.LIST.getName());

        Action action = actionFactory.createFrom(jcrRepository);

        assertTrue(action instanceof ListNodes);
    }

    @Test
    public void shouldReturnCreateFolderActionInstance() throws Exception {
        when(consoleReader.readLine()).thenReturn(ActionType.CREATE_FOLDER.getName());
        String parentNodeId = "parent-node-id";
        String folderName = "folder name";
        boolean hidden = true;
        List<String> responses = Collections.unmodifiableList(Arrays.asList(parentNodeId, folderName, String.valueOf(hidden)));
        when(interactiveActionConsole.retrieveResponses()).thenReturn(responses);

        Action action = actionFactory.createFrom(jcrRepository);

        verify(interactiveActionConsole).reset();
        assertTrue(action instanceof CreateFolder);
        assertThat(((CreateFolder) action).getParentNodeId(), is(parentNodeId));
        assertThat(((CreateFolder) action).getFolderName(), is(folderName));
        assertThat(((CreateFolder) action).isHidden(), is(hidden));
    }

    @Test
    public void shouldReturnCreateFileActionInstance() throws Exception {
        when(consoleReader.readLine()).thenReturn(ActionType.CREATE_FILE.getName());
        String parentNodeId = "parent-node-id";
        String fileName = "file name";
        boolean hidden = false;
        boolean deletable = true;
        String filePath = "/path/to/file";
        List<String> responses = Collections.unmodifiableList(Arrays.asList(parentNodeId, fileName, String.valueOf(hidden), String.valueOf(deletable), filePath));
        when(interactiveActionConsole.retrieveResponses()).thenReturn(responses);

        Action action = actionFactory.createFrom(jcrRepository);

        verify(interactiveActionConsole).reset();
        assertTrue(action instanceof CreateFile);
        assertThat(((CreateFile) action).getParentNodeId(), is(parentNodeId));
        assertThat(((CreateFile) action).getFileName(), is(fileName));
        assertThat(((CreateFile) action).isHidden(), is(hidden));
        assertThat(((CreateFile) action).isDeletable(), is(deletable));
        assertThat(((CreateFile) action).getFilePath(), is(filePath));
    }

    @Test
    public void shouldReturnCreateFileVersionActionInstance() throws Exception {
        when(consoleReader.readLine()).thenReturn(ActionType.CREATE_FILE_VERSION.getName());
        String fileNodeId = "file-node-id";
        String filePath = "/path/to/file";
        String description = "version description";
        List<String> responses = Collections.unmodifiableList(Arrays.asList(fileNodeId, filePath, description));
        when(interactiveActionConsole.retrieveResponses()).thenReturn(responses);

        Action action = actionFactory.createFrom(jcrRepository);

        verify(interactiveActionConsole).reset();
        assertTrue(action instanceof CreateFileVersion);
        assertThat(((CreateFileVersion) action).getFileNodeId(), is(fileNodeId));
        assertThat(((CreateFileVersion) action).getFilePath(), is(filePath));
        assertThat(((CreateFileVersion) action).getDescription(), is(description));
    }

    @Test
    public void shouldReturnRetrieveFileActionInstance() throws Exception {
        when(consoleReader.readLine()).thenReturn(ActionType.RETRIEVE_FILE.getName());
        String fileNodeId = "file-node-id";
        List<String> responses = Collections.singletonList(fileNodeId);
        when(interactiveActionConsole.retrieveResponses()).thenReturn(responses);

        Action action = actionFactory.createFrom(jcrRepository);

        verify(interactiveActionConsole).reset();
        assertTrue(action instanceof RetrieveFile);
        assertThat(((RetrieveFile) action).getNodeId(), is(fileNodeId));
    }

    @Test
    public void shouldReturnHelpActionInstanceOnNoneActionType() throws Exception {
        when(consoleReader.readLine()).thenReturn(ActionType.NONE.getName());

        Action action = actionFactory.createFrom(jcrRepository);

        assertTrue(action instanceof Help);
    }

}