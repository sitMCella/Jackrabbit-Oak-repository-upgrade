package it.mcella.jcr.oak.upgrade.repository;

import org.junit.Before;
import org.junit.Test;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.version.Version;
import javax.jcr.version.VersionHistory;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OakNodeVersionTest {

    private static final String VERSION_NAME = "version name";

    private final Version version = mock(Version.class);

    private OakNodeVersion oakNodeVersion;

    @Before
    public void setUp() throws Exception {
        oakNodeVersion = new OakNodeVersion(version);

        when(version.getName()).thenReturn(VERSION_NAME);
    }

    @Test
    public void shouldGetJcrNodeVersionId() throws Exception {
        Node frozenNode = mock(Node.class);
        when(version.getFrozenNode()).thenReturn(frozenNode);
        String identifier = "node-identifier";
        when(frozenNode.getIdentifier()).thenReturn(identifier);

        JcrNodeId jcrNodeId = oakNodeVersion.getJcrNodeId();

        JcrNodeId expectedJcrNodeId = new OakNodeId(identifier);
        assertThat(jcrNodeId, is(expectedJcrNodeId));
    }

    @Test
    public void shouldGetVersionNumber() throws Exception {
        VersionHistory versionHistory = mock(VersionHistory.class);
        when(version.getContainingHistory()).thenReturn(versionHistory);
        String[] versionLabels = new String[]{OakNodeVersion.LAST_VERSION_LABEL, OakNodeVersion.VERSION_LABEL_PATTERN + "1"};
        when(versionHistory.getVersionLabels(version)).thenReturn(versionLabels);

        long versionNumber = oakNodeVersion.getVersionNumber();

        assertThat(versionNumber, is(1L));
    }

    @Test
    public void shouldGetMinusOneAsVersionNumberIfCannotFindVersionLabel() throws Exception {
        VersionHistory versionHistory = mock(VersionHistory.class);
        when(version.getContainingHistory()).thenReturn(versionHistory);
        String[] versionLabels = new String[]{OakNodeVersion.LAST_VERSION_LABEL, "another_label"};
        when(versionHistory.getVersionLabels(version)).thenReturn(versionLabels);

        long versionNumber = oakNodeVersion.getVersionNumber();

        assertThat(versionNumber, is(-1L));
    }

    @Test
    public void shouldGetVersionName() throws Exception {
        String versionName = oakNodeVersion.getName();

        assertThat(versionName, is(VERSION_NAME));
    }

    @Test
    public void shouldAddFirstVersionLabelIfNoVersionsExist() throws Exception {
        VersionHistory versionHistory = mock(VersionHistory.class);
        when(version.getContainingHistory()).thenReturn(versionHistory);
        when(versionHistory.hasVersionLabel(OakNodeVersion.LAST_VERSION_LABEL)).thenReturn(false);

        oakNodeVersion.addVersionLabel();

        verify(versionHistory).addVersionLabel(VERSION_NAME, OakNodeVersion.FIRST_VERSION_LABEL, false);
    }

    @Test
    public void shouldAddLastVersionLabelIfNoVersionsExist() throws Exception {
        VersionHistory versionHistory = mock(VersionHistory.class);
        when(version.getContainingHistory()).thenReturn(versionHistory);
        when(versionHistory.hasVersionLabel(OakNodeVersion.LAST_VERSION_LABEL)).thenReturn(false);

        oakNodeVersion.addVersionLabel();

        verify(versionHistory).addVersionLabel(VERSION_NAME, OakNodeVersion.LAST_VERSION_LABEL, true);
    }

    @Test
    public void shouldAddSecondVersionLabelIfFirstVersionExists() throws Exception {
        VersionHistory versionHistory = mock(VersionHistory.class);
        when(version.getContainingHistory()).thenReturn(versionHistory);
        when(versionHistory.hasVersionLabel(OakNodeVersion.LAST_VERSION_LABEL)).thenReturn(true);
        Version lastVersion = mock(Version.class, "lastVersion");
        when(versionHistory.getVersionByLabel(OakNodeVersion.LAST_VERSION_LABEL)).thenReturn(lastVersion);
        String[] versionLabels = new String[]{OakNodeVersion.LAST_VERSION_LABEL, OakNodeVersion.VERSION_LABEL_PATTERN + "1"};
        when(versionHistory.getVersionLabels(lastVersion)).thenReturn(versionLabels);

        oakNodeVersion.addVersionLabel();

        verify(versionHistory).addVersionLabel(VERSION_NAME, OakNodeVersion.VERSION_LABEL_PATTERN + "2", false);
    }

    @Test
    public void shouldAddLastVersionLabelToSecondVersionIfFirstVersionExists() throws Exception {
        VersionHistory versionHistory = mock(VersionHistory.class);
        when(version.getContainingHistory()).thenReturn(versionHistory);
        when(versionHistory.hasVersionLabel(OakNodeVersion.LAST_VERSION_LABEL)).thenReturn(true);
        Version lastVersion = mock(Version.class, "lastVersion");
        when(versionHistory.getVersionByLabel(OakNodeVersion.LAST_VERSION_LABEL)).thenReturn(lastVersion);
        String[] versionLabels = new String[]{OakNodeVersion.LAST_VERSION_LABEL, OakNodeVersion.VERSION_LABEL_PATTERN + "1"};
        when(versionHistory.getVersionLabels(lastVersion)).thenReturn(versionLabels);

        oakNodeVersion.addVersionLabel();

        verify(versionHistory).addVersionLabel(VERSION_NAME, OakNodeVersion.LAST_VERSION_LABEL, true);
    }

    @Test
    public void shouldGetNodeVersionJcrProperty() throws Exception {
        String propertyName = "propertyName";
        Node frozenNode = mock(Node.class);
        when(version.getFrozenNode()).thenReturn(frozenNode);

        JcrProperty jcrProperty = oakNodeVersion.getProperty(propertyName);

        verify(frozenNode).getProperty(propertyName);
        assertNotNull(jcrProperty);
    }

    @Test
    public void shouldGetNodeVersionJcrProperties() throws Exception {
        Node frozenNode = mock(Node.class);
        when(version.getFrozenNode()).thenReturn(frozenNode);
        PropertyIterator propertyIterator = mock(PropertyIterator.class);
        when(frozenNode.getProperties()).thenReturn(propertyIterator);
        Property property = mock(Property.class);
        when(propertyIterator.hasNext()).thenReturn(true, false);
        when(propertyIterator.next()).thenReturn(property);

        List<JcrProperty> jcrProperties = oakNodeVersion.getProperties();

        assertFalse(jcrProperties.isEmpty());
        assertThat(jcrProperties.size(), is(1));
    }

    @Test
    public void shouldGetNodeVersionLabels() throws Exception {
        VersionHistory versionHistory = mock(VersionHistory.class);
        when(version.getContainingHistory()).thenReturn(versionHistory);
        String[] jcrNodeVersionLabels = new String[]{OakNodeVersion.LAST_VERSION_LABEL, OakNodeVersion.VERSION_LABEL_PATTERN + "1"};
        when(versionHistory.getVersionLabels(version)).thenReturn(jcrNodeVersionLabels);

        List<String> versionLabels = oakNodeVersion.getVersionLabels();

        assertFalse(versionLabels.isEmpty());
        assertThat(versionLabels.size(), is(2));
        assertTrue(versionLabels.contains(OakNodeVersion.LAST_VERSION_LABEL));
        assertTrue(versionLabels.contains(OakNodeVersion.VERSION_LABEL_PATTERN + "1"));
    }

    @Test
    public void shouldAddVersionLabelsFromAnotherJcrNodeVersion() throws Exception {
        VersionHistory versionHistory = mock(VersionHistory.class);
        when(version.getContainingHistory()).thenReturn(versionHistory);
        JcrNodeVersion anotherJcrNodeVersion = mock(JcrNodeVersion.class);
        List<String> versionLabels = new ArrayList<>();
        versionLabels.add(OakNodeVersion.VERSION_LABEL_PATTERN + "1");
        versionLabels.add(OakNodeVersion.LAST_VERSION_LABEL);
        when(anotherJcrNodeVersion.getVersionLabels()).thenReturn(versionLabels);

        oakNodeVersion.addVersionLabelsFrom(anotherJcrNodeVersion);

        verify(versionHistory).addVersionLabel(VERSION_NAME, OakNodeVersion.VERSION_LABEL_PATTERN + "1", true);
        verify(versionHistory).addVersionLabel(VERSION_NAME, OakNodeVersion.LAST_VERSION_LABEL, true);
    }

}