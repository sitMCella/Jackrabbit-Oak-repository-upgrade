package it.mcella.jcr.oak.upgrade.repository.firstversion.node.folder;

import it.mcella.jcr.oak.upgrade.repository.JcrNodeId;
import it.mcella.jcr.oak.upgrade.repository.JcrNodePath;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FolderNodeTest {

    private static final String NAME = "folder name";
    private static final boolean HIDDEN = true;

    private final JcrNodeId jcrNodeId = mock(JcrNodeId.class);
    private final JcrNodePath jcrNodePath = mock(JcrNodePath.class);
    private final JcrNodeId childNodeId = mock(JcrNodeId.class);

    private List<JcrNodeId> childNodeIds;
    private FolderNode folderNode;

    @Before
    public void setUp() throws Exception {
        childNodeIds = new ArrayList<>();
        childNodeIds.add(childNodeId);
        folderNode = new FolderNode(jcrNodeId, jcrNodePath, NAME, HIDDEN, childNodeIds);
    }

    @Test
    public void shouldGetJcrNodeId() throws Exception {
        assertThat(folderNode.getJcrNodeId(), is(jcrNodeId));
    }

    @Test
    public void shouldGetJcrNodePath() throws Exception {
        assertThat(folderNode.getJcrNodePath(), is(jcrNodePath));
    }

    @Test
    public void shouldGetNodeName() throws Exception {
        assertThat(folderNode.getName(), is(NAME));
    }

    @Test
    public void shouldGetNodeHiddenAttribute() throws Exception {
        assertThat(folderNode.isHidden(), is(HIDDEN));
    }

    @Test
    public void shouldGetChildNodeIds() throws Exception {
        assertThat(folderNode.getChildNodeIds(), is(childNodeIds));
    }

    @Test
    public void shouldBeEqualIfAllFieldsAreEqual() throws Exception {
        FolderNode anotherFolderNode = new FolderNode(jcrNodeId, jcrNodePath, NAME, HIDDEN, childNodeIds);

        assertTrue(folderNode.equals(anotherFolderNode) && anotherFolderNode.equals(folderNode));
        assertThat(folderNode.hashCode(), is(anotherFolderNode.hashCode()));
    }

    @Test
    public void shouldNotBeEqualIfJcrNodeIdIsDifferent() throws Exception {
        JcrNodeId anotherJcrNodeId = mock(JcrNodeId.class);
        FolderNode anotherFolderNode = new FolderNode(anotherJcrNodeId, jcrNodePath, NAME, HIDDEN, childNodeIds);

        assertFalse(folderNode.equals(anotherFolderNode));
    }

    @Test
    public void shouldNotBeEqualIfJcrNodePathIsDifferent() throws Exception {
        JcrNodePath anotherJcrNodePath = mock(JcrNodePath.class);
        FolderNode anotherFolderNode = new FolderNode(jcrNodeId, anotherJcrNodePath, NAME, HIDDEN, childNodeIds);

        assertFalse(folderNode.equals(anotherFolderNode));
    }

    @Test
    public void shouldNotBeEqualIfNodeNameIsDifferent() throws Exception {
        String anotherName = "another file name";
        FolderNode anotherFolderNode = new FolderNode(jcrNodeId, jcrNodePath, anotherName, HIDDEN, childNodeIds);

        assertFalse(folderNode.equals(anotherFolderNode));
    }

    @Test
    public void shouldNotBeEqualIfNodeHiddenAttributeIsDifferent() throws Exception {
        boolean anotherHidden = false;
        FolderNode anotherFolderNode = new FolderNode(jcrNodeId, jcrNodePath, NAME, anotherHidden, childNodeIds);

        assertFalse(folderNode.equals(anotherFolderNode));
    }

    @Test
    public void shouldNotBeEqualIfChildNodesAreDifferent() throws Exception {
        List<JcrNodeId> otherChildNodeIds = new ArrayList<>();
        JcrNodeId anotherChildNodeId = mock(JcrNodeId.class);
        otherChildNodeIds.add(anotherChildNodeId);
        FolderNode anotherFolderNode = new FolderNode(jcrNodeId, jcrNodePath, NAME, HIDDEN, otherChildNodeIds);

        assertFalse(folderNode.equals(anotherFolderNode));
    }

    @Test
    public void shouldGetStringRepresentation() throws Exception {
        String nodeId = "node id";
        when(jcrNodeId.getNodeId()).thenReturn(nodeId);
        String path = "/path/to/node";
        when(jcrNodePath.getPath()).thenReturn(path);
        childNodeIds.clear();
        JcrNodeId childNodeId = mock(JcrNodeId.class, "child node id");
        String childNodeIdentifier = "child node id";
        when(childNodeId.getNodeId()).thenReturn(childNodeIdentifier);
        childNodeIds.add(childNodeId);
        String expected = String.format("FolderNode{jcrNodeId='%s', jcrNodePath='%s', name='%s', hidden=%s, childNodeIds='%s'}",
                nodeId, path, NAME, HIDDEN, Arrays.toString(childNodeIds.toArray()));

        assertThat(folderNode.toString(), is(expected));
    }

}
