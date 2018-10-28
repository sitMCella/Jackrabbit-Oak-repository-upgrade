package it.mcella.jcr.oak.upgrade.repository.secondversion.node.folder;

import it.mcella.jcr.oak.upgrade.repository.JcrNodeId;
import it.mcella.jcr.oak.upgrade.repository.JcrNodePath;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class FolderNodeTest {

    private static final String NAME = "folder name";
    private static final String DESCRIPTION = "folder description";

    private final JcrNodeId jcrNodeId = mock(JcrNodeId.class);
    private final JcrNodePath jcrNodePath = mock(JcrNodePath.class);
    private final JcrNodeId childNodeId = mock(JcrNodeId.class);

    private List<JcrNodeId> childNodeIds;
    private FolderNode folderNode;

    @Before
    public void setUp() throws Exception {
        childNodeIds = new ArrayList<>();
        childNodeIds.add(childNodeId);
        folderNode = new FolderNode(jcrNodeId, jcrNodePath, NAME, DESCRIPTION, childNodeIds);
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
    public void shouldGetName() throws Exception {
        assertThat(folderNode.getName(), is(NAME));
    }

    @Test
    public void shouldGetDescription() throws Exception {
        assertThat(folderNode.getDescription(), is(DESCRIPTION));
    }

    @Test
    public void shouldGetChildNodeIds() throws Exception {
        assertThat(folderNode.getChildNodeIds(), is(childNodeIds));
    }

    @Test
    public void shouldBeEqualIfAllFieldsAreEqual() throws Exception {
        FolderNode anotherFolderNode = new FolderNode(jcrNodeId, jcrNodePath, NAME, DESCRIPTION, childNodeIds);

        assertThat(folderNode, is(anotherFolderNode));
    }

    @Test
    public void shouldNotBeEqualIfJcrNodeIdIsDifferent() throws Exception {
        JcrNodeId anotherJcrNodeId = mock(JcrNodeId.class);
        FolderNode anotherFolderNode = new FolderNode(anotherJcrNodeId, jcrNodePath, NAME, DESCRIPTION, childNodeIds);

        assertThat(folderNode, is(not(anotherFolderNode)));
    }

    @Test
    public void shouldNotBeEqualIfJcrNodePathIsDifferent() throws Exception {
        JcrNodePath anotherJcrNodePath = mock(JcrNodePath.class);
        FolderNode anotherFolderNode = new FolderNode(jcrNodeId, anotherJcrNodePath, NAME, DESCRIPTION, childNodeIds);

        assertThat(folderNode, is(not(anotherFolderNode)));
    }

    @Test
    public void shouldNotBeEqualIfNameIsDifferent() throws Exception {
        String anotherName = "another file name";
        FolderNode anotherFolderNode = new FolderNode(jcrNodeId, jcrNodePath, anotherName, DESCRIPTION, childNodeIds);

        assertThat(folderNode, is(not(anotherFolderNode)));
    }

    @Test
    public void shouldNotBeEqualIfDescriptionIsDifferent() throws Exception {
        String anotherDescription = "another file description";
        FolderNode anotherFolderNode = new FolderNode(jcrNodeId, jcrNodePath, NAME, anotherDescription, childNodeIds);

        assertThat(folderNode, is(not(anotherFolderNode)));
    }

    @Test
    public void shouldNotBeEqualIfChildNodesAreDifferent() throws Exception {
        List<JcrNodeId> otherChildNodeIds = new ArrayList<>();
        JcrNodeId anotherChildNodeId = mock(JcrNodeId.class);
        otherChildNodeIds.add(anotherChildNodeId);
        FolderNode anotherFolderNode = new FolderNode(jcrNodeId, jcrNodePath, NAME, DESCRIPTION, otherChildNodeIds);

        assertThat(folderNode, is(not(anotherFolderNode)));
    }

}
