package it.mcella.jcr.oak.upgrade.repository.nodetypes.migration;

import it.mcella.jcr.oak.upgrade.repository.JcrNamespace;
import it.mcella.jcr.oak.upgrade.repository.JcrNode;
import it.mcella.jcr.oak.upgrade.repository.JcrNodeVersion;
import it.mcella.jcr.oak.upgrade.repository.JcrProperty;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OakNodeMigrationTest {

    private final JcrNode jcrNode = mock(JcrNode.class);
    private final JcrNamespace jcrNamespace = mock(JcrNamespace.class);
    private final JcrPropertyFilter jcrPropertyFilter = mock(JcrPropertyFilter.class);

    private OakNodeMigration oakNodeMigration;

    @Before
    public void setUp() throws Exception {
        oakNodeMigration = new OakNodeMigration(jcrNode, jcrNamespace, jcrPropertyFilter);
    }

    @Test
    public void shouldAddNodePropertiesFromNodeVersion() throws Exception {
        JcrNodeVersion jcrNodeVersion = mock(JcrNodeVersion.class);
        List<JcrProperty> jcrProperties = new ArrayList<>();
        JcrProperty jcrNamespaceProperty = mock(JcrProperty.class);
        jcrProperties.add(jcrNamespaceProperty);
        when(jcrNodeVersion.getProperties()).thenReturn(jcrProperties);
        when(jcrNamespace.isAppliedTo(jcrNamespaceProperty)).thenReturn(true);
        when(jcrPropertyFilter.match(jcrNamespaceProperty)).thenReturn(false);

        oakNodeMigration.addNodePropertiesFrom(jcrNodeVersion);

        verify(jcrNode).setProperty(jcrNamespaceProperty);
    }

    @Test
    public void shouldNotAddAnotherNamespaceNodePropertyFromNodeVersion() throws Exception {
        JcrNodeVersion jcrNodeVersion = mock(JcrNodeVersion.class);
        List<JcrProperty> jcrProperties = new ArrayList<>();
        JcrProperty jcrAnotherNamespaceProperty = mock(JcrProperty.class);
        jcrProperties.add(jcrAnotherNamespaceProperty);
        when(jcrNodeVersion.getProperties()).thenReturn(jcrProperties);
        when(jcrNamespace.isAppliedTo(jcrAnotherNamespaceProperty)).thenReturn(false);
        when(jcrPropertyFilter.match(jcrAnotherNamespaceProperty)).thenReturn(false);

        oakNodeMigration.addNodePropertiesFrom(jcrNodeVersion);

        verify(jcrNode, never()).setProperty(jcrAnotherNamespaceProperty);
    }

    @Test
    public void shouldNotAddFilteredNodePropertyFromNodeVersion() throws Exception {
        JcrNodeVersion jcrNodeVersion = mock(JcrNodeVersion.class);
        List<JcrProperty> jcrProperties = new ArrayList<>();
        JcrProperty jcrNamespaceProperty = mock(JcrProperty.class);
        jcrProperties.add(jcrNamespaceProperty);
        when(jcrNodeVersion.getProperties()).thenReturn(jcrProperties);
        when(jcrNamespace.isAppliedTo(jcrNamespaceProperty)).thenReturn(true);
        when(jcrPropertyFilter.match(jcrNamespaceProperty)).thenReturn(true);

        oakNodeMigration.addNodePropertiesFrom(jcrNodeVersion);

        verify(jcrNode, never()).setProperty(jcrNamespaceProperty);
    }

}