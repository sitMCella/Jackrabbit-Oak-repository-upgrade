package it.mcella.jcr.oak.upgrade.repository.firstversion.node.file;

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
    private static final boolean HIDDEN = false;
    private static final boolean DELETABLE = true;
    private static final String MIME_TYPE = "mime type";

    private final JcrNodeId jcrNodeId = mock(JcrNodeId.class);
    private final JcrNodePath jcrNodePath = mock(JcrNodePath.class);

    private FileNode fileNode;

    @Before
    public void setUp() throws Exception {
        fileNode = new FileNode(jcrNodeId, jcrNodePath, NAME, HIDDEN, DELETABLE, MIME_TYPE);
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
    public void shouldGetNodeName() throws Exception {
        assertThat(fileNode.getName(), is(NAME));
    }

    @Test
    public void shouldGetHidden() throws Exception {
        assertThat(fileNode.isHidden(), is(HIDDEN));
    }

    @Test
    public void shouldGetDeletable() throws Exception {
        assertThat(fileNode.isDeletable(), is(DELETABLE));
    }

    @Test
    public void shouldGetMimeType() throws Exception {
        assertThat(fileNode.getMimeType(), is(MIME_TYPE));
    }

    @Test
    public void shouldBeEqualIfAllFieldsAreEqual() throws Exception {
        FileNode anotherFileNode = new FileNode(jcrNodeId, jcrNodePath, NAME, HIDDEN, DELETABLE, MIME_TYPE);

        assertTrue(fileNode.equals(anotherFileNode) && anotherFileNode.equals(fileNode));
        assertThat(fileNode.hashCode(), is(anotherFileNode.hashCode()));
    }

    @Test
    public void shouldNotBeEqualIfJcrNodeIdIsDifferent() throws Exception {
        JcrNodeId anotherJcrNodeId = mock(JcrNodeId.class);
        FileNode anotherFileNode = new FileNode(anotherJcrNodeId, jcrNodePath, NAME, HIDDEN, DELETABLE, MIME_TYPE);

        assertFalse(fileNode.equals(anotherFileNode));
    }

    @Test
    public void shouldNotBeEqualIfJcrNodePathIsDifferent() throws Exception {
        JcrNodePath anotherJcrNodePath = mock(JcrNodePath.class);
        FileNode anotherFileNode = new FileNode(jcrNodeId, anotherJcrNodePath, NAME, HIDDEN, DELETABLE, MIME_TYPE);

        assertFalse(fileNode.equals(anotherFileNode));
    }

    @Test
    public void shouldNotBeEqualIfNodeNameIsDifferent() throws Exception {
        String anotherName = "another file name";
        FileNode anotherFileNode = new FileNode(jcrNodeId, jcrNodePath, anotherName, HIDDEN, DELETABLE, MIME_TYPE);

        assertFalse(fileNode.equals(anotherFileNode));
    }

    @Test
    public void shouldNotBeEqualIfHiddenIsDifferent() throws Exception {
        boolean anotherHidden = true;
        FileNode anotherFileNode = new FileNode(jcrNodeId, jcrNodePath, NAME, anotherHidden, DELETABLE, MIME_TYPE);

        assertFalse(fileNode.equals(anotherFileNode));
    }

    @Test
    public void shouldNotBeEqualIfDeletableIsDifferent() throws Exception {
        boolean anotherDeletable = false;
        FileNode anotherFileNode = new FileNode(jcrNodeId, jcrNodePath, NAME, HIDDEN, anotherDeletable, MIME_TYPE);

        assertFalse(fileNode.equals(anotherFileNode));
    }

    @Test
    public void shouldNotBeEqualIfMimeTypeIsDifferent() throws Exception {
        String anotherMimeType = "another mime type";
        FileNode anotherFileNode = new FileNode(jcrNodeId, jcrNodePath, NAME, HIDDEN, DELETABLE, anotherMimeType);

        assertFalse(fileNode.equals(anotherFileNode));
    }

    @Test
    public void shouldGetStringRepresentation() throws Exception {
        String nodeId = "node id";
        when(jcrNodeId.getNodeId()).thenReturn(nodeId);
        String path = "/path/to/node";
        when(jcrNodePath.getPath()).thenReturn(path);
        String expected = String.format("FileNode{jcrNodeId='%s', jcrNodePath='%s', name='%s', hidden=%s, deletable=%s, mimeType='%s'}",
                nodeId, path, NAME, HIDDEN, DELETABLE, MIME_TYPE);

        assertThat(fileNode.toString(), is(expected));
    }

}
