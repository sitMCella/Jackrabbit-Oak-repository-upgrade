package it.mcella.jcr.oak.upgrade.apprun.firstversion.action;

import it.mcella.jcr.oak.upgrade.commons.ConsoleWriter;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class HelpTest {

    private final ConsoleWriter consoleWriter = mock(ConsoleWriter.class);

    private Help help;

    @Before
    public void setUp() throws Exception {
        help = new Help(consoleWriter);
    }

    @Test
    public void shouldReturnHelpActionType() throws Exception {
        assertThat(help.getType(), is(ActionType.HELP));
    }

    @Test
    public void shouldExecuteHelp() throws Exception {
        help.execute();

        verify(consoleWriter).println("\nActions:");
        verify(consoleWriter).println("quit\t\t\tapplication exit");
        verify(consoleWriter).println("help\t\t\tprint this help");
        verify(consoleWriter).println("setup\t\t\tinitialize the repository with a predefined node structure");
        verify(consoleWriter).println("list\t\t\tlist all repository nodes and versions");
        verify(consoleWriter).println("create-folder\t\tcreate folder");
        verify(consoleWriter).println("create-file\t\tcreate file");
        verify(consoleWriter).println("create-file-version\tcreate file version");
        verify(consoleWriter).println("retrieve-file\t\tretrieve file or file version content");
    }

}