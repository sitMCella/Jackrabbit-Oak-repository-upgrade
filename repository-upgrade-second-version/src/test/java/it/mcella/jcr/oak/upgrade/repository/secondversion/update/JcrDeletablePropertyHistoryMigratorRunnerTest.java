package it.mcella.jcr.oak.upgrade.repository.secondversion.update;

import it.mcella.jcr.oak.upgrade.repository.JcrNode;
import it.mcella.jcr.oak.upgrade.repository.JcrProperty;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class JcrDeletablePropertyHistoryMigratorRunnerTest {

    private final JcrNode jcrNode = mock(JcrNode.class);

    private JcrDeletablePropertyHistoryMigratorRunner jcrDeletablePropertyHistoryMigratorRunner;

    @Before
    public void setUp() throws Exception {
        jcrDeletablePropertyHistoryMigratorRunner = new JcrDeletablePropertyHistoryMigratorRunner();
    }

    @Test
    public void shouldRemoveDeletablePropertyFromJcrNodeIfExists() throws Exception {
        when(jcrNode.hasProperty(JcrDeletablePropertyMigrator.DELETABLE_PROPERTY_NAME)).thenReturn(true);
        JcrProperty jcrProperty = mock(JcrProperty.class);
        when(jcrNode.getProperty(JcrDeletablePropertyMigrator.DELETABLE_PROPERTY_NAME)).thenReturn(jcrProperty);

        jcrDeletablePropertyHistoryMigratorRunner.run(jcrNode);

        verify(jcrNode).removeProperty(jcrProperty);
    }

    @Test
    public void shouldNotRemoveDeletablePropertyFromJcrNodeIfDoesNotExist() throws Exception {
        when(jcrNode.hasProperty(JcrDeletablePropertyMigrator.DELETABLE_PROPERTY_NAME)).thenReturn(false);

        jcrDeletablePropertyHistoryMigratorRunner.run(jcrNode);

        verify(jcrNode, never()).removeProperty(any(JcrProperty.class));
    }

}