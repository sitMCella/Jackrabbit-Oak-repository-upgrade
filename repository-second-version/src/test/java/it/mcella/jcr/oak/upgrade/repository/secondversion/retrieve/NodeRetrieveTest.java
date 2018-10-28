package it.mcella.jcr.oak.upgrade.repository.secondversion.retrieve;

import it.mcella.jcr.oak.upgrade.repository.JcrNode;
import it.mcella.jcr.oak.upgrade.repository.JcrNodeId;
import it.mcella.jcr.oak.upgrade.repository.JcrProperty;
import it.mcella.jcr.oak.upgrade.repository.JcrRepository;
import it.mcella.jcr.oak.upgrade.repository.JcrSession;
import it.mcella.jcr.oak.upgrade.repository.secondversion.node.Node;
import it.mcella.jcr.oak.upgrade.repository.secondversion.node.file.FileNode;
import it.mcella.jcr.oak.upgrade.repository.secondversion.node.file.JcrFileNode;
import it.mcella.jcr.oak.upgrade.repository.secondversion.node.folder.FolderNode;
import it.mcella.jcr.oak.upgrade.repository.secondversion.node.folder.JcrFolderNode;
import it.mcella.jcr.oak.upgrade.repository.secondversion.node.root.JcrRootNode;
import it.mcella.jcr.oak.upgrade.repository.secondversion.node.root.RootNode;
import org.apache.jackrabbit.JcrConstants;
import org.junit.Before;
import org.junit.Test;

import javax.jcr.RepositoryException;
import javax.jcr.SimpleCredentials;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NodeRetrieveTest {

    private final JcrRepository jcrRepository = mock(JcrRepository.class);
    private final JcrSession jcrSession = mock(JcrSession.class);
    private final JcrNode jcrRootNode = mock(JcrNode.class, "root node");
    private final JcrNodeId jcrNodeId = mock(JcrNodeId.class, "node id");
    private final JcrProperty jcrProperty = mock(JcrProperty.class);
    private final JcrNode folderJcrNode = mock(JcrNode.class, "folder node");

    private NodeRetrieve nodeRetrieve;

    @Before
    public void setUp() throws Exception {
        nodeRetrieve = new NodeRetrieve(jcrRepository);

        when(jcrRepository.login(any(SimpleCredentials.class))).thenReturn(jcrSession);
        when(jcrRootNode.hasType(JcrRootNode.NODE_TYPE)).thenReturn(true);
        when(folderJcrNode.hasType(JcrFolderNode.NODE_TYPE)).thenReturn(true);
    }

    @Test
    public void shouldRetrieveRootNode() throws Exception {
        when(jcrSession.getRootNode()).thenReturn(jcrRootNode);

        Node rootNode = nodeRetrieve.retrieveRootNode();

        assertNotNull(rootNode);
        assertTrue(rootNode instanceof RootNode);
    }

    @Test(expected = NodeRetrieveException.class)
    public void shouldThrowNodeRetrieveExceptionOnRetrieveRootNodeRepositoryException() throws Exception {
        when(jcrSession.getRootNode()).thenThrow(new RepositoryException());

        nodeRetrieve.retrieveRootNode();
    }

    @Test
    public void shouldRetrieveNodesFromId() throws Exception {
        List<JcrNodeId> jcrNodeIds = Collections.unmodifiableList(Arrays.asList(jcrNodeId, jcrNodeId, jcrNodeId));
        JcrNode fileJcrNode = mock(JcrNode.class, "file node");
        JcrNode fileContentJcrNode = mock(JcrNode.class, "file content node");
        List<JcrNode> jcrNodes = Collections.unmodifiableList(Arrays.asList(jcrRootNode, fileJcrNode, folderJcrNode));
        when(jcrSession.getNodesById(jcrNodeIds)).thenReturn(jcrNodes);
        when(fileJcrNode.hasType(JcrFileNode.NODE_TYPE)).thenReturn(true);
        when(fileJcrNode.getProperty(JcrFileNode.SYSTEM_PROPERTY_NAME)).thenReturn(jcrProperty);
        when(fileJcrNode.getProperty(JcrFileNode.DESCRIPTION_PROPERTY_NAME)).thenReturn(jcrProperty);
        when(fileJcrNode.getNode(JcrConstants.JCR_CONTENT)).thenReturn(fileContentJcrNode);
        when(fileContentJcrNode.getProperty(JcrConstants.JCR_MIMETYPE)).thenReturn(jcrProperty);
        when(folderJcrNode.getProperty(JcrFileNode.DESCRIPTION_PROPERTY_NAME)).thenReturn(jcrProperty);

        List<Node> nodes = nodeRetrieve.retrieveNodesFrom(jcrNodeIds);

        assertThat(nodes.size(), is(3));
        assertTrue(nodes.get(0) instanceof RootNode);
        assertTrue(nodes.get(1) instanceof FileNode);
        assertTrue(nodes.get(2) instanceof FolderNode);
    }

    @Test(expected = NodeRetrieveException.class)
    public void shouldThrowNodeRetrieveExceptionOnRetrieveNodesRepositoryException() throws Exception {
        List<JcrNodeId> jcrNodeIds = Collections.singletonList(jcrNodeId);
        when(jcrSession.getNodesById(jcrNodeIds)).thenThrow(new RepositoryException());

        nodeRetrieve.retrieveNodesFrom(jcrNodeIds);
    }

    @Test
    public void shouldFilterUnackwledgedNode() throws Exception {
        List<JcrNodeId> jcrNodeIds = Collections.unmodifiableList(Arrays.asList(jcrNodeId));
        JcrNode jcrNode = mock(JcrNode.class, "node");
        List<JcrNode> jcrNodes = Collections.unmodifiableList(Arrays.asList(jcrNode));
        when(jcrSession.getNodesById(jcrNodeIds)).thenReturn(jcrNodes);

        List<Node> nodes = nodeRetrieve.retrieveNodesFrom(jcrNodeIds);

        assertThat(nodes.size(), is(0));
    }

    @Test
    public void shouldFilterNodeOnRepositoryException() throws Exception {
        List<JcrNodeId> jcrNodeIds = Collections.unmodifiableList(Arrays.asList(jcrNodeId));
        List<JcrNode> jcrNodes = Collections.unmodifiableList(Arrays.asList(folderJcrNode));
        when(jcrSession.getNodesById(jcrNodeIds)).thenReturn(jcrNodes);
        when(folderJcrNode.getNodeId()).thenThrow(new RepositoryException());

        List<Node> nodes = nodeRetrieve.retrieveNodesFrom(jcrNodeIds);

        assertThat(nodes.size(), is(0));
    }

}
