package it.mcella.jcr.oak.upgrade.repository.secondversion.update;

import it.mcella.jcr.oak.upgrade.repository.JcrNode;
import it.mcella.jcr.oak.upgrade.repository.JcrProperty;
import it.mcella.jcr.oak.upgrade.repository.nodetypes.migration.JcrNodeMigratorException;
import it.mcella.jcr.oak.upgrade.repository.nodetypes.migration.JcrNodeVersionMigrator;
import org.apache.jackrabbit.JcrConstants;
import org.junit.Before;
import org.junit.Test;

import javax.jcr.RepositoryException;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OakHiddenPropertyMigratorTest {

    private final JcrNodeVersionMigrator jcrNodeVersionMigrator = mock(JcrNodeVersionMigrator.class);
    private final JcrNode jcrNode = mock(JcrNode.class);

    private OakHiddenPropertyMigrator oakHiddenPropertyMigrator;

    @Before
    public void setUp() throws Exception {
        oakHiddenPropertyMigrator = new OakHiddenPropertyMigrator(jcrNodeVersionMigrator);
    }

    @Test
    public void shouldRemoveIntermediateHiddenJcrPropertyFromJcrNodeIfExists() throws Exception {
        when(jcrNode.hasProperty(OakHiddenPropertyMigrator.INTERMEDIATE_HIDDEN_PROPERTY_NAME)).thenReturn(true);
        JcrProperty jcrProperty = mock(JcrProperty.class);
        when(jcrNode.getProperty(OakHiddenPropertyMigrator.INTERMEDIATE_HIDDEN_PROPERTY_NAME)).thenReturn(jcrProperty);

        oakHiddenPropertyMigrator.migrate(jcrNode);

        verify(jcrNode).removeProperty(jcrProperty);
    }

    @Test
    public void shouldNotRemoveIntermediateHiddenJcrPropertyFromJcrNodeIfDoesNotExist() throws Exception {
        when(jcrNode.hasProperty(OakHiddenPropertyMigrator.INTERMEDIATE_HIDDEN_PROPERTY_NAME)).thenReturn(false);

        oakHiddenPropertyMigrator.migrate(jcrNode);

        verify(jcrNode, never()).removeProperty(any(JcrProperty.class));
    }

    @Test(expected = JcrNodeMigratorException.class)
    public void shouldThrowJcrNodeMigratorExceptionOnRepositoryException() throws Exception {
        when(jcrNode.hasProperty(OakHiddenPropertyMigrator.INTERMEDIATE_HIDDEN_PROPERTY_NAME)).thenReturn(true);
        when(jcrNode.getProperty(OakHiddenPropertyMigrator.INTERMEDIATE_HIDDEN_PROPERTY_NAME)).thenThrow(new RepositoryException());

        oakHiddenPropertyMigrator.migrate(jcrNode);
    }

    @Test
    public void shouldRunNodeVersionMigratorIfNodeIsVersionable() throws Exception {
        when(jcrNode.hasProperty(JcrConstants.JCR_MIXINTYPES)).thenReturn(true);

        oakHiddenPropertyMigrator.migrateHistory(jcrNode);

        verify(jcrNodeVersionMigrator).migrate(jcrNode);
    }

    @Test
    public void shouldNotRunNodeVersionMigratorIfNodeIsNotVersionable() throws Exception {
        when(jcrNode.hasProperty(JcrConstants.JCR_MIXINTYPES)).thenReturn(false);

        oakHiddenPropertyMigrator.migrateHistory(jcrNode);

        verify(jcrNodeVersionMigrator, never()).migrate(jcrNode);
    }

    @Test(expected = JcrNodeMigratorException.class)
    public void shouldThrowJcrNodeMigratorExceptionOnMigrateHistoryRepositoryException() throws Exception {
        when(jcrNode.hasProperty(JcrConstants.JCR_MIXINTYPES)).thenReturn(true);
        doThrow(new RepositoryException()).when(jcrNodeVersionMigrator).migrate(jcrNode);

        oakHiddenPropertyMigrator.migrateHistory(jcrNode);
    }

}