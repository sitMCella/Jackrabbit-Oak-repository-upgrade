package it.mcella.jcr.oak.upgrade.repository.secondversion.retrieve;

import it.mcella.jcr.oak.upgrade.repository.JcrNode;
import it.mcella.jcr.oak.upgrade.repository.JcrNodeId;
import it.mcella.jcr.oak.upgrade.repository.JcrRepository;
import it.mcella.jcr.oak.upgrade.repository.JcrSession;
import it.mcella.jcr.oak.upgrade.repository.secondversion.node.Node;
import it.mcella.jcr.oak.upgrade.repository.secondversion.node.NodeVersion;
import it.mcella.jcr.oak.upgrade.repository.secondversion.node.file.JcrFileNode;
import org.apache.jackrabbit.JcrConstants;
import org.junit.Before;
import org.junit.Test;

import javax.jcr.RepositoryException;
import javax.jcr.SimpleCredentials;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class NodeVersionRetrieveTest {

    private final JcrRepository jcrRepository = mock(JcrRepository.class);
    private final JcrSession jcrSession = mock(JcrSession.class);
    private final Node node = mock(Node.class);
    private final JcrNodeId jcrNodeId = mock(JcrNodeId.class);
    private final JcrNode jcrNode = mock(JcrNode.class);

    private NodeVersionRetrieve nodeVersionRetrieve;

    @Before
    public void setUp() throws Exception {
        nodeVersionRetrieve = new NodeVersionRetrieve(jcrRepository);

        when(jcrRepository.login(any(SimpleCredentials.class))).thenReturn(jcrSession);
        when(node.getJcrNodeId()).thenReturn(jcrNodeId);
    }

    @Test
    public void shouldRetrieveFileNodeVersionsNode() throws Exception {
        when(jcrSession.getNodeById(jcrNodeId)).thenReturn(jcrNode);
        when(jcrNode.hasType(JcrFileNode.NODE_TYPE)).thenReturn(true);
        when(jcrNode.hasProperty(JcrConstants.JCR_MIXINTYPES)).thenReturn(false);

        nodeVersionRetrieve.retrieveNodeVersions(node);

        verify(jcrNode).hasProperty(JcrConstants.JCR_MIXINTYPES);
    }

    @Test
    public void shouldReturnEmptyVersionNodesIfFolderNode() throws Exception {
        when(jcrSession.getNodeById(jcrNodeId)).thenReturn(jcrNode);
        when(jcrNode.hasType(JcrFileNode.NODE_TYPE)).thenReturn(false);

        List<NodeVersion> nodeVersions = nodeVersionRetrieve.retrieveNodeVersions(node);

        assertTrue(nodeVersions.isEmpty());
    }

    @Test(expected = NodeVersionRetrieveException.class)
    public void shouldThrowNodeVersionRetrieveExceptionOnRepositoryException() throws Exception {
        when(jcrSession.getNodeById(jcrNodeId)).thenThrow(new RepositoryException());

        nodeVersionRetrieve.retrieveNodeVersions(node);
    }

}
