package it.mcella.jcr.oak.upgrade.repository.secondversion.node.file;

import it.mcella.jcr.oak.upgrade.repository.JcrNodeId;
import it.mcella.jcr.oak.upgrade.repository.JcrNodePath;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FileVersionNodeTest {

    private static final String NAME = "file name";
    private static final boolean SYSTEM = false;
    private static final String DESCRIPTION = "file description";
    private static final String MIME_TYPE = "mime type";
    private static final long VERSION_NUMBER = 1L;
    private static final String VERSION_DESCRIPTION = "version description";

    private final JcrNodeId jcrNodeVersionId = mock(JcrNodeId.class, "jcrNodeVersionId");
    private final JcrNodeId jcrNodeId = mock(JcrNodeId.class, "jcrNodeId");
    private final JcrNodePath jcrNodePath = mock(JcrNodePath.class);

    private FileVersionNode fileVersionNode;

    @Before
    public void setUp() throws Exception {
        FileNode fileNode = new FileNode(jcrNodeId, jcrNodePath, NAME, SYSTEM, DESCRIPTION, MIME_TYPE);
        fileVersionNode = new FileVersionNode(fileNode, jcrNodeVersionId, SYSTEM, VERSION_NUMBER, VERSION_DESCRIPTION);
    }

    @Test
    public void shouldGetJcrNodeVersionId() throws Exception {
        assertThat(fileVersionNode.getJcrNodeId(), is(jcrNodeVersionId));
    }

    @Test
    public void shouldGetJcrNodePath() throws Exception {
        assertThat(fileVersionNode.getJcrNodePath(), is(jcrNodePath));
    }

    @Test
    public void shouldGetName() throws Exception {
        assertThat(fileVersionNode.getName(), is(NAME));
    }

    @Test
    public void shouldGetSystem() throws Exception {
        assertThat(fileVersionNode.isSystem(), is(SYSTEM));
    }

    @Test
    public void shouldGetDescription() throws Exception {
        assertThat(fileVersionNode.getDescription(), is(DESCRIPTION));
    }

    @Test
    public void shouldGetMimeType() throws Exception {
        assertThat(fileVersionNode.getMimeType(), is(MIME_TYPE));
    }

    @Test
    public void shouldGetVersionLabel() throws Exception {
        assertThat(fileVersionNode.getVersionNumber(), is(VERSION_NUMBER));
    }

    @Test
    public void shouldGetVersionDescription() throws Exception {
        assertThat(fileVersionNode.getVersionDescription(), is(VERSION_DESCRIPTION));
    }

    @Test
    public void shouldBeEqualIfAllFieldsAreEqual() throws Exception {
        FileNode anotherFileNode = new FileNode(jcrNodeId, jcrNodePath, NAME, SYSTEM, DESCRIPTION, MIME_TYPE);
        FileVersionNode anotherFileVersionNode = new FileVersionNode(anotherFileNode, jcrNodeVersionId, SYSTEM, VERSION_NUMBER, VERSION_DESCRIPTION);

        assertTrue(fileVersionNode.equals(anotherFileVersionNode) && anotherFileVersionNode.equals(fileVersionNode));
        assertThat(fileVersionNode.hashCode(), is(anotherFileVersionNode.hashCode()));
    }

    @Test
    public void shouldNotBeEqualIfJcrNodeVersionIdIsDifferent() throws Exception {
        JcrNodeId anotherJcrNodeId = mock(JcrNodeId.class);
        FileNode anotherFileNode = new FileNode(jcrNodeId, jcrNodePath, NAME, SYSTEM, DESCRIPTION, MIME_TYPE);
        FileVersionNode anotherFileVersionNode = new FileVersionNode(anotherFileNode, anotherJcrNodeId, SYSTEM, VERSION_NUMBER, VERSION_DESCRIPTION);

        assertFalse(fileVersionNode.equals(anotherFileVersionNode));
    }

    @Test
    public void shouldNotBeEqualIfJcrNodePathIsDifferent() throws Exception {
        JcrNodePath anotherJcrNodePath = mock(JcrNodePath.class);
        FileNode anotherFileNode = new FileNode(jcrNodeId, anotherJcrNodePath, NAME, SYSTEM, DESCRIPTION, MIME_TYPE);
        FileVersionNode anotherFileVersionNode = new FileVersionNode(anotherFileNode, jcrNodeVersionId, SYSTEM, VERSION_NUMBER, VERSION_DESCRIPTION);

        assertFalse(fileVersionNode.equals(anotherFileVersionNode));
    }

    @Test
    public void shouldNotBeEqualIfNameIsDifferent() throws Exception {
        String anotherName = "another file name";
        FileNode anotherFileNode = new FileNode(jcrNodeId, jcrNodePath, anotherName, SYSTEM, DESCRIPTION, MIME_TYPE);
        FileVersionNode anotherFileVersionNode = new FileVersionNode(anotherFileNode, jcrNodeVersionId, SYSTEM, VERSION_NUMBER, VERSION_DESCRIPTION);

        assertFalse(fileVersionNode.equals(anotherFileVersionNode));
    }

    @Test
    public void shouldNotBeEqualIfSystemIsDifferent() throws Exception {
        FileNode anotherFileNode = new FileNode(jcrNodeId, jcrNodePath, NAME, SYSTEM, DESCRIPTION, MIME_TYPE);
        boolean anotherSystem = true;
        FileVersionNode anotherFileVersionNode = new FileVersionNode(anotherFileNode, jcrNodeVersionId, anotherSystem, VERSION_NUMBER, VERSION_DESCRIPTION);

        assertFalse(fileVersionNode.equals(anotherFileVersionNode));
    }

    @Test
    public void shouldNotBeEqualIfDescriptionIsDifferent() throws Exception {
        String anotherDescription = "another file description";
        FileNode anotherFileNode = new FileNode(jcrNodeId, jcrNodePath, NAME, SYSTEM, anotherDescription, MIME_TYPE);
        FileVersionNode anotherFileVersionNode = new FileVersionNode(anotherFileNode, jcrNodeVersionId, SYSTEM, VERSION_NUMBER, VERSION_DESCRIPTION);

        assertFalse(fileVersionNode.equals(anotherFileVersionNode));
    }

    @Test
    public void shouldNotBeEqualIfMimeTypeIsDifferent() throws Exception {
        String anotherMimeType = "another mime type";
        FileNode anotherFileNode = new FileNode(jcrNodeId, jcrNodePath, NAME, SYSTEM, DESCRIPTION, anotherMimeType);
        FileVersionNode anotherFileVersionNode = new FileVersionNode(anotherFileNode, jcrNodeVersionId, SYSTEM, VERSION_NUMBER, VERSION_DESCRIPTION);

        assertFalse(fileVersionNode.equals(anotherFileVersionNode));
    }

    @Test
    public void shouldNotBeEqualIfVersionNumberIsDifferent() throws Exception {
        FileNode anotherFileNode = new FileNode(jcrNodeId, jcrNodePath, NAME, SYSTEM, DESCRIPTION, MIME_TYPE);
        long anotherVersionNumber = 2L;
        FileVersionNode anotherFileVersionNode = new FileVersionNode(anotherFileNode, jcrNodeVersionId, SYSTEM, anotherVersionNumber, VERSION_DESCRIPTION);

        assertFalse(fileVersionNode.equals(anotherFileVersionNode));
    }

    @Test
    public void shouldNotBeEqualIfVersionDescriptionIsDifferent() throws Exception {
        FileNode anotherFileNode = new FileNode(jcrNodeId, jcrNodePath, NAME, SYSTEM, DESCRIPTION, MIME_TYPE);
        String anotherVersionDescription = "another version description";
        FileVersionNode anotherFileVersionNode = new FileVersionNode(anotherFileNode, jcrNodeVersionId, SYSTEM, VERSION_NUMBER, anotherVersionDescription);

        assertFalse(fileVersionNode.equals(anotherFileVersionNode));
    }

    @Test
    public void shouldGetStringRepresentation() throws Exception {
        String nodeId = "node id";
        when(jcrNodeVersionId.getNodeId()).thenReturn(nodeId);
        String path = "/path/to/node";
        when(jcrNodePath.getPath()).thenReturn(path);
        String expected = String.format("FileVersionNode{jcrNodeId='%s', jcrNodePath='%s', name='%s', system=%s, description='%s', mimeType='%s', versionNumber=%s, versionDescription='%s'}",
                nodeId, path, NAME, SYSTEM, DESCRIPTION, MIME_TYPE, VERSION_NUMBER, VERSION_DESCRIPTION);

        assertThat(fileVersionNode.toString(), is(expected));
    }

}
