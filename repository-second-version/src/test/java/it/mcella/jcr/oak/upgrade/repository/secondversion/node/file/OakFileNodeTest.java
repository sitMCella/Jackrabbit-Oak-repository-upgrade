package it.mcella.jcr.oak.upgrade.repository.secondversion.node.file;

import it.mcella.jcr.oak.upgrade.repository.JcrBinary;
import it.mcella.jcr.oak.upgrade.repository.JcrNode;
import it.mcella.jcr.oak.upgrade.repository.JcrNodeId;
import it.mcella.jcr.oak.upgrade.repository.JcrNodePath;
import it.mcella.jcr.oak.upgrade.repository.JcrProperty;
import it.mcella.jcr.oak.upgrade.repository.JcrSession;
import it.mcella.jcr.oak.upgrade.repository.secondversion.node.Node;
import org.apache.jackrabbit.JcrConstants;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OakFileNodeTest {

    private final JcrNode jcrNode = mock(JcrNode.class, "file");
    private final JcrSession jcrSession = mock(JcrSession.class);
    private final InputStream inputStream = mock(InputStream.class);
    private final JcrNode content = mock(JcrNode.class, "content");
    private final JcrBinary binary = mock(JcrBinary.class);

    private OakFileNode oakFileNode;

    @Before
    public void setUp() throws Exception {
        oakFileNode = new OakFileNode(jcrNode);
    }

    @Test
    public void shouldAddSystemPropertyToJcrFileNode() throws Exception {
        boolean system = false;

        oakFileNode.setSystem(system);

        verify(jcrNode).setProperty(JcrFileNode.SYSTEM_PROPERTY_NAME, system);
    }

    @Test
    public void shouldAddDescriptionPropertyToJcrFileNode() throws Exception {
        String description = "file description";

        oakFileNode.setDescription(description);

        verify(jcrNode).setProperty(JcrFileNode.DESCRIPTION_PROPERTY_NAME, description);
    }

    @Test
    public void shouldCreateContentNodeIfDoesNotExist() throws Exception {
        when(jcrNode.hasNode(JcrConstants.JCR_CONTENT)).thenReturn(false);
        when(jcrNode.addNodeWithNameNotEscaped(JcrConstants.JCR_CONTENT, JcrConstants.NT_RESOURCE)).thenReturn(content);
        when(jcrSession.createBinary(any(InputStream.class))).thenReturn(binary);
        Path image = Paths.get(ClassLoader.getSystemResource("fox.jpg").toURI());

        oakFileNode.createContent(jcrSession, image, inputStream);

        verify(jcrNode).addNodeWithNameNotEscaped(JcrConstants.JCR_CONTENT, JcrConstants.NT_RESOURCE);
    }

    @Test
    public void shouldRetrieveContentNodeIfExists() throws Exception {
        when(jcrNode.hasNode(JcrConstants.JCR_CONTENT)).thenReturn(true);
        when(jcrNode.getNode(JcrConstants.JCR_CONTENT)).thenReturn(content);
        when(jcrSession.createBinary(any(InputStream.class))).thenReturn(binary);
        Path image = Paths.get(ClassLoader.getSystemResource("fox.jpg").toURI());

        oakFileNode.createContent(jcrSession, image, inputStream);

        verify(jcrNode).getNode(JcrConstants.JCR_CONTENT);
    }

    @Test
    public void shouldSetContentNodeProperties() throws Exception {
        when(jcrNode.hasNode(JcrConstants.JCR_CONTENT)).thenReturn(false);
        when(jcrNode.addNodeWithNameNotEscaped(JcrConstants.JCR_CONTENT, JcrConstants.NT_RESOURCE)).thenReturn(content);
        when(jcrSession.createBinary(any(InputStream.class))).thenReturn(binary);
        Path image = Paths.get(ClassLoader.getSystemResource("fox.jpg").toURI());

        oakFileNode.createContent(jcrSession, image, inputStream);

        verify(content).setProperty(JcrConstants.JCR_MIMETYPE, "image/jpeg");
        verify(content).setProperty(JcrConstants.JCR_ENCODING, "");
        verify(content).setProperty(JcrConstants.JCR_DATA, binary);
    }

    @Test
    public void shouldReturnFileNode() throws Exception {
        JcrNodeId jcrNodeId = mock(JcrNodeId.class);
        when(jcrNode.getNodeId()).thenReturn(jcrNodeId);
        JcrNodePath jcrNodePath = mock(JcrNodePath.class);
        when(jcrNode.getNodePath()).thenReturn(jcrNodePath);
        String name = "node name";
        when(jcrNode.getName()).thenReturn(name);
        JcrProperty jcrSystemProperty = mock(JcrProperty.class, "system property");
        when(jcrNode.getProperty(JcrFileNode.SYSTEM_PROPERTY_NAME)).thenReturn(jcrSystemProperty);
        boolean system = false;
        when(jcrSystemProperty.getBooleanValue()).thenReturn(system);
        JcrProperty jcrDescriptionProperty = mock(JcrProperty.class, "description property");
        when(jcrNode.getProperty(JcrFileNode.DESCRIPTION_PROPERTY_NAME)).thenReturn(jcrDescriptionProperty);
        String description = "file description";
        when(jcrDescriptionProperty.getStringValue()).thenReturn(description);
        when(jcrNode.getNode(JcrConstants.JCR_CONTENT)).thenReturn(content);
        JcrProperty jcrMimeTypeProperty = mock(JcrProperty.class, "mime type property");
        when(content.getProperty(JcrConstants.JCR_MIMETYPE)).thenReturn(jcrMimeTypeProperty);
        String mimeType = "mime type";
        when(jcrMimeTypeProperty.getStringValue()).thenReturn(mimeType);

        Node fileNode = oakFileNode.toNode();

        FileNode expectedFileNode = new FileNode(jcrNodeId, jcrNodePath, name, system, description, mimeType);
        assertThat(fileNode, is(expectedFileNode));
    }

}
