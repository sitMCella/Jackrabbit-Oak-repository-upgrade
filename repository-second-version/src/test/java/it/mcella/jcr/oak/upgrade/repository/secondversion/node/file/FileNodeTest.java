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

public class FileNodeTest {

    private static final String NAME = "file name";
    private static final boolean SYSTEM = false;
    private static final String DESCRIPTION = "file description";
    private static final String MIME_TYPE = "mime type";

    private final JcrNodeId jcrNodeId = mock(JcrNodeId.class);
    private final JcrNodePath jcrNodePath = mock(JcrNodePath.class);

    private FileNode fileNode;

    @Before
    public void setUp() throws Exception {
        fileNode = new FileNode(jcrNodeId, jcrNodePath, NAME, SYSTEM, DESCRIPTION, MIME_TYPE);
    }

    @Test
    public void shouldGetJcrNodeId() throws Exception {
        assertThat(fileNode.getJcrNodeId(), is(jcrNodeId));
    }

    @Test
    public void shouldGetJcrNodePath() throws Exception {
        assertThat(fileNode.getJcrNodePath(), is(jcrNodePath));
    }

    @Test
    public void shouldGetName() throws Exception {
        assertThat(fileNode.getName(), is(NAME));
    }

    @Test
    public void shouldGetSystem() throws Exception {
        assertThat(fileNode.isSystem(), is(SYSTEM));
    }

    @Test
    public void shouldGetDescription() throws Exception {
        assertThat(fileNode.getDescription(), is(DESCRIPTION));
    }

    @Test
    public void shouldGetMimeType() throws Exception {
        assertThat(fileNode.getMimeType(), is(MIME_TYPE));
    }

    @Test
    public void shouldBeEqualIfAllFieldsAreEqual() throws Exception {
        FileNode anotherFileNode = new FileNode(jcrNodeId, jcrNodePath, NAME, SYSTEM, DESCRIPTION, MIME_TYPE);

        assertTrue(fileNode.equals(anotherFileNode) && anotherFileNode.equals(fileNode));
        assertThat(fileNode.hashCode(), is(anotherFileNode.hashCode()));
    }

    @Test
    public void shouldNotBeEqualIfJcrNodeIdIsDifferent() throws Exception {
        JcrNodeId anotherJcrNodeId = mock(JcrNodeId.class);
        FileNode anotherFileNode = new FileNode(anotherJcrNodeId, jcrNodePath, NAME, SYSTEM, DESCRIPTION, MIME_TYPE);

        assertFalse(fileNode.equals(anotherFileNode));
    }

    @Test
    public void shouldNotBeEqualIfJcrNodePathIsDifferent() throws Exception {
        JcrNodePath anotherJcrNodePath = mock(JcrNodePath.class);
        FileNode anotherFileNode = new FileNode(jcrNodeId, anotherJcrNodePath, NAME, SYSTEM, DESCRIPTION, MIME_TYPE);

        assertFalse(fileNode.equals(anotherFileNode));
    }

    @Test
    public void shouldNotBeEqualIfNameIsDifferent() throws Exception {
        String anotherName = "another file name";
        FileNode anotherFileNode = new FileNode(jcrNodeId, jcrNodePath, anotherName, SYSTEM, DESCRIPTION, MIME_TYPE);

        assertFalse(fileNode.equals(anotherFileNode));
    }

    @Test
    public void shouldNotBeEqualIfSystemIsDifferent() throws Exception {
        boolean anotherSystem = true;
        FileNode anotherFileNode = new FileNode(jcrNodeId, jcrNodePath, NAME, anotherSystem, DESCRIPTION, MIME_TYPE);

        assertFalse(fileNode.equals(anotherFileNode));
    }

    @Test
    public void shouldNotBeEqualIfDescriptionIsDifferent() throws Exception {
        String anotherDescription = "another file description";
        FileNode anotherFileNode = new FileNode(jcrNodeId, jcrNodePath, NAME, SYSTEM, anotherDescription, MIME_TYPE);

        assertFalse(fileNode.equals(anotherFileNode));
    }

    @Test
    public void shouldNotBeEqualIfMimeTypeIsDifferent() throws Exception {
        String anotherMimeType = "another mime type";
        FileNode anotherFileNode = new FileNode(jcrNodeId, jcrNodePath, NAME, SYSTEM, DESCRIPTION, anotherMimeType);

        assertFalse(fileNode.equals(anotherFileNode));
    }

    @Test
    public void shouldGetStringRepresentation() throws Exception {
        String nodeId = "node id";
        when(jcrNodeId.getNodeId()).thenReturn(nodeId);
        String path = "/path/to/node";
        when(jcrNodePath.getPath()).thenReturn(path);
        String expected = String.format("FileNode{jcrNodeId='%s', jcrNodePath='%s', name='%s', system=%s, description='%s', mimeType='%s'}",
                nodeId, path, NAME, SYSTEM, DESCRIPTION, MIME_TYPE);

        assertThat(fileNode.toString(), is(expected));
    }

}
