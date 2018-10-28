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

public class JcrHiddenPropertyHistoryMigratorRunnerTest {

    private final JcrNode jcrNode = mock(JcrNode.class);

    private JcrHiddenPropertyHistoryMigratorRunner jcrHiddenPropertyHistoryMigratorRunner;

    @Before
    public void setUp() throws Exception {
        jcrHiddenPropertyHistoryMigratorRunner = new JcrHiddenPropertyHistoryMigratorRunner();
    }

    @Test
    public void shouldRemoveJcrHiddenPropertyFromJcrNodeIfExists() throws Exception {
        when(jcrNode.hasProperty(JcrNodeAttributesMixinMigrator.HIDDEN_PROPERTY_NAME)).thenReturn(true);
        JcrProperty jcrProperty = mock(JcrProperty.class);
        when(jcrNode.getProperty(JcrNodeAttributesMixinMigrator.HIDDEN_PROPERTY_NAME)).thenReturn(jcrProperty);

        jcrHiddenPropertyHistoryMigratorRunner.run(jcrNode);

        verify(jcrNode).removeProperty(jcrProperty);
    }

    @Test
    public void shouldNotRemoveJcrHiddenPropertyFromJcrNodeIfDoesNotExist() throws Exception {
        when(jcrNode.hasProperty(JcrNodeAttributesMixinMigrator.HIDDEN_PROPERTY_NAME)).thenReturn(false);

        jcrHiddenPropertyHistoryMigratorRunner.run(jcrNode);

        verify(jcrNode, never()).removeProperty(any(JcrProperty.class));
    }

}