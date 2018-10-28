package it.mcella.jcr.oak.upgrade.apprun.firstversion.action;

import it.mcella.jcr.oak.upgrade.commons.ConsoleReader;
import it.mcella.jcr.oak.upgrade.commons.ConsoleWriter;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class InteractiveActionConsoleTest {

    private final ConsoleReader consoleReader = mock(ConsoleReader.class);
    private final ConsoleWriter consoleWriter = mock(ConsoleWriter.class);

    private InteractiveActionConsole interactiveActionConsole;

    @Before
    public void setUp() throws Exception {
        this.interactiveActionConsole = new InteractiveActionConsole(consoleReader, consoleWriter);
    }

    @Test
    public void shouldInitiallyHaveNoQuestions() throws Exception {
        assertNotNull(interactiveActionConsole.getQuestions());
        assertTrue(interactiveActionConsole.getQuestions().isEmpty());
    }

    @Test
    public void shouldResetQuestions() throws Exception {
        interactiveActionConsole.addQuestion("question");

        interactiveActionConsole.reset();

        assertNotNull(interactiveActionConsole.getQuestions());
        assertTrue(interactiveActionConsole.getQuestions().isEmpty());
    }

    @Test
    public void shouldAddQuestion() throws Exception {
        String question = "question";

        interactiveActionConsole.addQuestion(question);

        List<String> questions = interactiveActionConsole.getQuestions();
        assertNotNull(questions);
        assertThat(questions.size(), is(1));
        assertThat(questions.get(0), is(question));
    }

    @Test
    public void shouldRetrieveResponses() throws Exception {
        interactiveActionConsole.addQuestion("question");
        String response = "response";
        when(consoleReader.readLine()).thenReturn(response);

        List<String> responses = interactiveActionConsole.retrieveResponses();

        assertThat(responses, contains(response));
    }

}