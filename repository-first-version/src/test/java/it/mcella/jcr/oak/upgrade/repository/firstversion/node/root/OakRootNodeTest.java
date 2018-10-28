package it.mcella.jcr.oak.upgrade.repository.firstversion.node.root;

import it.mcella.jcr.oak.upgrade.repository.JcrNode;
import it.mcella.jcr.oak.upgrade.repository.JcrNodeId;
import it.mcella.jcr.oak.upgrade.repository.JcrNodePath;
import it.mcella.jcr.oak.upgrade.repository.firstversion.node.Node;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OakRootNodeTest {

    private final JcrNode jcrNode = mock(JcrNode.class);

    @Test
    public void shouldReturnRootNode() throws Exception {
        OakRootNode oakRootNode = new OakRootNode(jcrNode);
        JcrNodeId jcrNodeId = mock(JcrNodeId.class);
        when(jcrNode.getNodeId()).thenReturn(jcrNodeId);
        JcrNodePath jcrNodePath = mock(JcrNodePath.class);
        when(jcrNode.getNodePath()).thenReturn(jcrNodePath);
        String name = "root";
        when(jcrNode.getName()).thenReturn(name);

        Node folderNode = oakRootNode.toNode();

        RootNode expectedFolderNode = new RootNode(jcrNodeId, jcrNodePath, name);
        assertThat(folderNode, is(expectedFolderNode));
    }

}