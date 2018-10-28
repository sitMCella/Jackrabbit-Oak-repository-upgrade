package it.mcella.jcr.oak.upgrade.repository.firstversion.node.folder;

import it.mcella.jcr.oak.upgrade.repository.JcrNode;
import it.mcella.jcr.oak.upgrade.repository.JcrNodeId;
import it.mcella.jcr.oak.upgrade.repository.JcrNodePath;
import it.mcella.jcr.oak.upgrade.repository.JcrProperty;
import it.mcella.jcr.oak.upgrade.repository.firstversion.node.Node;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OakFolderNodeTest {

    private static final boolean HIDDEN = true;

    private final JcrNode jcrNode = mock(JcrNode.class);

    private OakFolderNode oakFolderNode;

    @Before
    public void setUp() throws Exception {
        oakFolderNode = new OakFolderNode(jcrNode);
    }

    @Test
    public void shouldSetFolderNodeHiddenProperty() throws Exception {
        oakFolderNode.setHidden(HIDDEN);

        verify(jcrNode).setProperty(JcrFolderNode.HIDDEN_PROPERTY_NAME, HIDDEN);
    }

    @Test
    public void shouldReturnFolderNode() throws Exception {
        JcrNodeId jcrNodeId = mock(JcrNodeId.class);
        when(jcrNode.getNodeId()).thenReturn(jcrNodeId);
        JcrNodePath jcrNodePath = mock(JcrNodePath.class);
        when(jcrNode.getNodePath()).thenReturn(jcrNodePath);
        String name = "folder name";
        when(jcrNode.getName()).thenReturn(name);
        JcrProperty jcrHiddenProperty = mock(JcrProperty.class);
        when(jcrNode.getProperty(JcrFolderNode.HIDDEN_PROPERTY_NAME)).thenReturn(jcrHiddenProperty);
        when(jcrHiddenProperty.getBooleanValue()).thenReturn(HIDDEN);

        Node folderNode = oakFolderNode.toNode();

        FolderNode expectedFolderNode = new FolderNode(jcrNodeId, jcrNodePath, name, HIDDEN);
        assertThat(folderNode, is(expectedFolderNode));
    }

}
