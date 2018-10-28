package it.mcella.jcr.oak.upgrade.apprun.firstversion.action;

import it.mcella.jcr.oak.upgrade.commons.ConsoleWriter;
import it.mcella.jcr.oak.upgrade.repository.JcrNodeId;
import it.mcella.jcr.oak.upgrade.repository.firstversion.node.Node;
import it.mcella.jcr.oak.upgrade.repository.firstversion.node.NodeVersion;
import it.mcella.jcr.oak.upgrade.repository.firstversion.node.file.FileVersionNode;
import it.mcella.jcr.oak.upgrade.repository.firstversion.retrieve.NodeRetrieve;
import it.mcella.jcr.oak.upgrade.repository.firstversion.retrieve.NodeVersionRetrieve;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ListNodesTest {

    private final NodeRetrieve nodeRetrieve = mock(NodeRetrieve.class);
    private final NodeVersionRetrieve nodeVersionRetrieve = mock(NodeVersionRetrieve.class);
    private final ConsoleWriter consoleWriter = mock(ConsoleWriter.class);
    private final Node rootNode = mock(Node.class, "rootNode");
    private final JcrNodeId firstRootChildJcrNodeId = mock(JcrNodeId.class, "firstRootChildJcrNodeId");
    private final JcrNodeId secondRootChildJcrNodeId = mock(JcrNodeId.class, "secondRootChildJcrNodeId");
    private final Node firstRootChildNode = mock(Node.class, "firstRootChildNode");
    private final Node secondRootChildNode = mock(Node.class, "secondRootChildNode");
    private final JcrNodeId jcrNodeId = mock(JcrNodeId.class);
    private final Node childNode = mock(Node.class, "childNode");

    private ListNodes listNodes;

    @Before
    public void setUp() throws Exception {
        listNodes = new ListNodes(nodeRetrieve, nodeVersionRetrieve, consoleWriter);

        when(nodeRetrieve.retrieveRootNode()).thenReturn(rootNode);
        when(nodeVersionRetrieve.retrieveNodeVersions(rootNode)).thenReturn(Collections.emptyList());
        List<Node> rootChildNodes = Collections.unmodifiableList(Arrays.asList(firstRootChildNode, secondRootChildNode));
        List<JcrNodeId> jcrRootChildNodeIds = Collections.unmodifiableList(Arrays.asList(firstRootChildJcrNodeId, secondRootChildJcrNodeId));
        when(rootNode.getChildNodeIds()).thenReturn(jcrRootChildNodeIds);
        when(nodeRetrieve.retrieveNodesFrom(jcrRootChildNodeIds)).thenReturn(rootChildNodes);
    }

    @Test
    public void shouldReturnListNodesActionType() throws Exception {
        assertThat(listNodes.getType(), is(ActionType.LIST));
    }

    @Test
    public void shouldPrintRootChildNodes() throws Exception {
        listNodes.execute();

        verify(consoleWriter).println(firstRootChildNode);
        verify(consoleWriter).println(secondRootChildNode);
    }

    @Test
    public void shouldPrintRootChildNodeVersions() throws Exception {
        FileVersionNode fileVersionNode = mock(FileVersionNode.class);
        List<NodeVersion> versionNodes = Collections.singletonList(fileVersionNode);
        when(nodeVersionRetrieve.retrieveNodeVersions(firstRootChildNode)).thenReturn(versionNodes);

        listNodes.execute();

        verify(consoleWriter).println(fileVersionNode);
    }

    @Test
    public void shouldPrintChildNode() throws Exception {
        List<JcrNodeId> jcrChildNodeIds = Collections.singletonList(jcrNodeId);
        when(firstRootChildNode.getChildNodeIds()).thenReturn(jcrChildNodeIds);
        List<Node> childNodes = Collections.singletonList(childNode);
        when(nodeRetrieve.retrieveNodesFrom(jcrChildNodeIds)).thenReturn(childNodes);

        listNodes.execute();

        verify(consoleWriter).println(childNode);
    }

    @Test
    public void shouldPrintChildNodeVersions() throws Exception {
        List<JcrNodeId> jcrChildNodeIds = Collections.singletonList(jcrNodeId);
        when(firstRootChildNode.getChildNodeIds()).thenReturn(jcrChildNodeIds);
        List<Node> childNodes = Collections.singletonList(childNode);
        when(nodeRetrieve.retrieveNodesFrom(jcrChildNodeIds)).thenReturn(childNodes);
        FileVersionNode fileVersionNode = mock(FileVersionNode.class);
        List<NodeVersion> versionNodes = Collections.singletonList(fileVersionNode);
        when(nodeVersionRetrieve.retrieveNodeVersions(childNode)).thenReturn(versionNodes);

        listNodes.execute();

        verify(consoleWriter).println(fileVersionNode);
    }

}