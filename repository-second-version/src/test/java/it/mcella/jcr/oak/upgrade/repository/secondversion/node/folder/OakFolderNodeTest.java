package it.mcella.jcr.oak.upgrade.repository.secondversion.node.folder;

import it.mcella.jcr.oak.upgrade.repository.JcrNode;
import it.mcella.jcr.oak.upgrade.repository.JcrNodeId;
import it.mcella.jcr.oak.upgrade.repository.JcrNodePath;
import it.mcella.jcr.oak.upgrade.repository.JcrProperty;
import it.mcella.jcr.oak.upgrade.repository.secondversion.node.Node;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OakFolderNodeTest {

    private final JcrNode jcrNode = mock(JcrNode.class);

    private OakFolderNode oakFolderNode;

    @Before
    public void setUp() throws Exception {
        oakFolderNode = new OakFolderNode(jcrNode);
    }

    @Test
    public void shouldAddDescriptionToFolderNode() throws Exception {
        String description = "folder description";

        oakFolderNode.setDescription(description);

        verify(jcrNode).setProperty(JcrFolderNode.DESCRIPTION_PROPERTY_NAME, description);
    }

    @Test
    public void shouldReturnFolderNode() throws Exception {
        JcrNodeId jcrNodeId = mock(JcrNodeId.class);
        when(jcrNode.getNodeId()).thenReturn(jcrNodeId);
        JcrNodePath jcrNodePath = mock(JcrNodePath.class);
        when(jcrNode.getNodePath()).thenReturn(jcrNodePath);
        String name = "folder name";
        when(jcrNode.getName()).thenReturn(name);
        JcrProperty jcrDescriptionProperty = mock(JcrProperty.class, "description property");
        when(jcrNode.getProperty(JcrFolderNode.DESCRIPTION_PROPERTY_NAME)).thenReturn(jcrDescriptionProperty);
        String description = "folder description";
        when(jcrDescriptionProperty.getStringValue()).thenReturn(description);

        Node folderNode = oakFolderNode.toNode();

        FolderNode expectedFolderNode = new FolderNode(jcrNodeId, jcrNodePath, name, description, Collections.emptyList());
        assertThat(folderNode, is(expectedFolderNode));
    }

}
