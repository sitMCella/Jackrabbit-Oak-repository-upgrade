package it.mcella.jcr.oak.upgrade.repository.secondversion.node.root;

import it.mcella.jcr.oak.upgrade.repository.JcrNodeId;
import it.mcella.jcr.oak.upgrade.repository.JcrNodePath;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class RootNodeTest {

    private static final String NAME = "folder name";

    private final JcrNodeId jcrNodeId = mock(JcrNodeId.class);
    private final JcrNodePath jcrNodePath = mock(JcrNodePath.class);
    private final JcrNodeId childNodeId = mock(JcrNodeId.class);

    private List<JcrNodeId> childNodeIds;
    private RootNode rootNode;

    @Before
    public void setUp() throws Exception {
        childNodeIds = new ArrayList<>();
        childNodeIds.add(childNodeId);
        rootNode = new RootNode(jcrNodeId, jcrNodePath, NAME, childNodeIds);
    }

    @Test
    public void shouldGetJcrNodeId() throws Exception {
        assertThat(rootNode.getJcrNodeId(), is(jcrNodeId));
    }

    @Test
    public void shouldGetJcrNodePath() throws Exception {
        assertThat(rootNode.getJcrNodePath(), is(jcrNodePath));
    }

    @Test
    public void shouldGetNodeName() throws Exception {
        assertThat(rootNode.getName(), is(NAME));
    }

    @Test
    public void shouldGetChildNodeIds() throws Exception {
        assertThat(rootNode.getChildNodeIds(), is(childNodeIds));
    }

    @Test
    public void shouldBeEqualIfAllFieldsAreEqual() throws Exception {
        RootNode anotherRootNode = new RootNode(jcrNodeId, jcrNodePath, NAME, childNodeIds);

        assertTrue(rootNode.equals(anotherRootNode));
    }

    @Test
    public void shouldNotBeEqualIfJcrNodeIdIsDifferent() throws Exception {
        JcrNodeId anotherJcrNodeId = mock(JcrNodeId.class);
        RootNode anotherRootNode = new RootNode(anotherJcrNodeId, jcrNodePath, NAME, childNodeIds);

        assertFalse(rootNode.equals(anotherRootNode));
    }

    @Test
    public void shouldNotBeEqualIfJcrNodePathIsDifferent() throws Exception {
        JcrNodePath anotherJcrNodePath = mock(JcrNodePath.class);
        RootNode anotherRootNode = new RootNode(jcrNodeId, anotherJcrNodePath, NAME, childNodeIds);

        assertFalse(rootNode.equals(anotherRootNode));
    }

    @Test
    public void shouldNotBeEqualIfNodeNameIsDifferent() throws Exception {
        String anotherName = "another file name";
        RootNode anotherRootNode = new RootNode(jcrNodeId, jcrNodePath, anotherName, childNodeIds);

        assertFalse(rootNode.equals(anotherRootNode));
    }

    @Test
    public void shouldNotBeEqualIfChildNodesAreDifferent() throws Exception {
        List<JcrNodeId> otherChildNodeIds = new ArrayList<>();
        JcrNodeId anotherChildNodeId = mock(JcrNodeId.class);
        otherChildNodeIds.add(anotherChildNodeId);
        RootNode anotherRootNode = new RootNode(jcrNodeId, jcrNodePath, NAME, otherChildNodeIds);

        assertFalse(rootNode.equals(anotherRootNode));
    }

}