package it.mcella.jcr.oak.upgrade.repository.secondversion.update;

import it.mcella.jcr.oak.upgrade.repository.JcrNode;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class JcrSystemPropertyHistoryMigratorRunnerTest {

    private JcrSystemPropertyHistoryMigratorRunner jcrSystemPropertyHistoryMigratorRunner;

    @Before
    public void setUp() throws Exception {
        jcrSystemPropertyHistoryMigratorRunner = new JcrSystemPropertyHistoryMigratorRunner();
    }

    @Test
    public void shouldSetSystemPropertyAsFalseToJcrNode() throws Exception {
        JcrNode jcrNode = mock(JcrNode.class);

        jcrSystemPropertyHistoryMigratorRunner.run(jcrNode);

        verify(jcrNode).setProperty(JcrSystemPropertyMigrator.SYSTEM_PROPERTY, false);
    }

}