package it.mcella.jcr.oak.upgrade.repository.secondversion.update;

import it.mcella.jcr.oak.upgrade.repository.JcrNode;
import it.mcella.jcr.oak.upgrade.repository.JcrNodeId;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class JcrDescriptionPropertyHistoryMigratorRunnerTest {

    private final JcrNodeToDescription jcrNodeToDescription = mock(JcrNodeToDescription.class);
    private final JcrNode jcrNode = mock(JcrNode.class);
    private final JcrNodeId jcrNodeId = mock(JcrNodeId.class);

    private JcrDescriptionPropertyHistoryMigratorRunner jcrDescriptionPropertyHistoryMigratorRunner;

    @Before
    public void setUp() throws Exception {
        jcrDescriptionPropertyHistoryMigratorRunner = new JcrDescriptionPropertyHistoryMigratorRunner(jcrNodeToDescription);
    }

    @Test
    public void shouldRetrieveJcrNodeVersionDescriptionFromMapping() throws Exception {
        when(jcrNode.getNodeId()).thenReturn(jcrNodeId);

        jcrDescriptionPropertyHistoryMigratorRunner.run(jcrNode);

        verify(jcrNodeToDescription).retrieveDescription(jcrNodeId);
    }

    @Test
    public void shouldSetJcrNodeVersionDescriptionProperty() throws Exception {
        when(jcrNode.getNodeId()).thenReturn(jcrNodeId);
        String description = "description";
        when(jcrNodeToDescription.retrieveDescription(jcrNodeId)).thenReturn(description);

        jcrDescriptionPropertyHistoryMigratorRunner.run(jcrNode);

        verify(jcrNode).setProperty(JcrNodeDescriptionMigrator.DESCRIPTION_PROPERTY, description);
    }

}