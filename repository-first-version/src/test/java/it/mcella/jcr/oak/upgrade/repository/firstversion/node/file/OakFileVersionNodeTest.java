package it.mcella.jcr.oak.upgrade.repository.firstversion.node.file;

import it.mcella.jcr.oak.upgrade.repository.JcrBinary;
import it.mcella.jcr.oak.upgrade.repository.JcrNode;
import it.mcella.jcr.oak.upgrade.repository.JcrNodeId;
import it.mcella.jcr.oak.upgrade.repository.JcrNodePath;
import it.mcella.jcr.oak.upgrade.repository.JcrNodeVersion;
import it.mcella.jcr.oak.upgrade.repository.JcrProperty;
import it.mcella.jcr.oak.upgrade.repository.JcrSession;
import org.apache.jackrabbit.JcrConstants;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OakFileVersionNodeTest {

    private static final String NAME = "node name";
    private static final boolean HIDDEN = false;
    private static final boolean DELETABLE = true;
    private static final String MIME_TYPE = "mime type";

    private final JcrNode jcrNode = mock(JcrNode.class, "file");
    private final JcrNode content = mock(JcrNode.class, "content");
    private final JcrSession jcrSession = mock(JcrSession.class);
    private final JcrBinary binary = mock(JcrBinary.class);
    private final JcrNodeId jcrNodeId = mock(JcrNodeId.class);
    private final JcrNodePath jcrNodePath = mock(JcrNodePath.class);
    private final JcrNodeVersion jcrNodeVersion = mock(JcrNodeVersion.class);

    private OakFileVersionNode oakFileVersionNode;

    @Before
    public void setUp() throws Exception {
        oakFileVersionNode = new OakFileVersionNode(jcrNode);

        when(jcrNode.getNodeId()).thenReturn(jcrNodeId);
        when(jcrNode.getNodePath()).thenReturn(jcrNodePath);
        when(jcrNode.getName()).thenReturn(NAME);
        JcrProperty jcrHiddenProperty = mock(JcrProperty.class, "hiddenProperty");
        when(jcrNode.getProperty(JcrFileNode.HIDDEN_PROPERTY_NAME)).thenReturn(jcrHiddenProperty);
        when(jcrHiddenProperty.getBooleanValue()).thenReturn(HIDDEN);
        JcrProperty jcrDeletableProperty = mock(JcrProperty.class, "deletableProperty");
        when(jcrNode.getProperty(JcrFileNode.DELETABLE_PROPERTY_NAME)).thenReturn(jcrDeletableProperty);
        when(jcrDeletableProperty.getBooleanValue()).thenReturn(DELETABLE);
        when(jcrNode.getNode(JcrConstants.JCR_CONTENT)).thenReturn(content);
        JcrProperty jcrMimeTypeProperty = mock(JcrProperty.class, "mimeTypeProperty");
        when(content.getProperty(JcrConstants.JCR_MIMETYPE)).thenReturn(jcrMimeTypeProperty);
        when(jcrMimeTypeProperty.getStringValue()).thenReturn(MIME_TYPE);
        when(jcrNode.checkin()).thenReturn(jcrNodeVersion);
    }

    @Test
    public void shouldCheckFileNodeVersionedIfFileNodeHasMixinTypes() throws Exception {
        when(jcrNode.hasProperty(JcrConstants.JCR_MIXINTYPES)).thenReturn(true);
        JcrProperty jcrProperty = mock(JcrProperty.class);
        when(jcrNode.getProperty(JcrConstants.JCR_MIXINTYPES)).thenReturn(jcrProperty);

        oakFileVersionNode.isVersioned();

        verify(jcrProperty).contains(JcrConstants.MIX_VERSIONABLE);
    }

    @Test
    public void shouldReturnFalseIfFileNodeHasNotMixinTypes() throws Exception {
        when(jcrNode.hasProperty(JcrConstants.JCR_MIXINTYPES)).thenReturn(false);

        assertFalse(oakFileVersionNode.isVersioned());
    }

    @Test
    public void shouldCreateFirstFileVersionNode() throws Exception {
        oakFileVersionNode.firstVersion();

        verify(jcrNode).addMixin(JcrConstants.MIX_VERSIONABLE);
        verify(jcrNode).save();
        verify(jcrNode).checkout();
        verify(jcrNode).checkin();
        verify(jcrNodeVersion).addVersionLabel();
    }

    @Test
    public void shouldReturnFileFirstVersionNode() throws Exception {
        JcrNodeId jcrNodeId = mock(JcrNodeId.class);
        when(jcrNodeVersion.getJcrNodeId()).thenReturn(jcrNodeId);
        long versionNumber = 1L;
        when(jcrNodeVersion.getVersionNumber()).thenReturn(versionNumber);

        FileVersionNode fileVersionNode = oakFileVersionNode.firstVersion();

        FileNode expectedFileNode = new FileNode(jcrNodeId, jcrNodePath, NAME, HIDDEN, DELETABLE, MIME_TYPE);
        FileVersionNode expectedFileVersionNode = new FileVersionNode(expectedFileNode, jcrNodeId, versionNumber);
        assertThat(fileVersionNode, is(expectedFileVersionNode));
    }

    @Test
    public void shouldCreateNewFileVersionNode() throws Exception {
        when(jcrNode.hasNode(JcrConstants.JCR_CONTENT)).thenReturn(false);
        when(jcrNode.addNodeWithNameNotEscaped(JcrConstants.JCR_CONTENT, JcrConstants.NT_RESOURCE)).thenReturn(content);
        when(jcrSession.createBinary(any(InputStream.class))).thenReturn(binary);
        String versionDescription = "version description";
        Path image = Paths.get(ClassLoader.getSystemResource("fox.jpg").toURI());

        oakFileVersionNode.newVersion(versionDescription, jcrSession, image);

        verify(jcrNode).checkout();
        verify(jcrNode).setProperty(OakFileVersionNode.VERSION_DESCRIPTION_PROPERTY_NAME, versionDescription);
        verify(content).setProperty(JcrConstants.JCR_MIMETYPE, "image/jpeg");
        verify(content).setProperty(JcrConstants.JCR_ENCODING, "");
        verify(content).setProperty(JcrConstants.JCR_DATA, binary);
        verify(jcrNode).save();
        verify(jcrNode).checkin();
        verify(jcrNodeVersion).addVersionLabel();
    }

    @Test
    public void shouldReturnFileVersionNode() throws Exception {
        when(jcrNode.hasNode(JcrConstants.JCR_CONTENT)).thenReturn(false);
        when(jcrNode.addNodeWithNameNotEscaped(JcrConstants.JCR_CONTENT, JcrConstants.NT_RESOURCE)).thenReturn(content);
        when(jcrSession.createBinary(any(InputStream.class))).thenReturn(binary);
        JcrNodeId jcrNodeId = mock(JcrNodeId.class);
        when(jcrNodeVersion.getJcrNodeId()).thenReturn(jcrNodeId);
        long versionNumber = 1L;
        when(jcrNodeVersion.getVersionNumber()).thenReturn(versionNumber);
        String versionDescription = "version description";
        Path image = Paths.get(ClassLoader.getSystemResource("fox.jpg").toURI());

        FileVersionNode fileVersionNode = oakFileVersionNode.newVersion(versionDescription, jcrSession, image);

        FileNode expectedFileNode = new FileNode(jcrNodeId, jcrNodePath, NAME, HIDDEN, DELETABLE, MIME_TYPE);
        FileVersionNode expectedFileVersionNode = new FileVersionNode(expectedFileNode, jcrNodeId, versionNumber, versionDescription);
        assertThat(fileVersionNode, is(expectedFileVersionNode));
    }

}
