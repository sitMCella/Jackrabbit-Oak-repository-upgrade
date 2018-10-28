package it.mcella.jcr.oak.upgrade.repository.firstversion.node.file;

import it.mcella.jcr.oak.upgrade.repository.JcrNodeId;
import it.mcella.jcr.oak.upgrade.repository.JcrNodePath;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class FileVersionNodeTest {

    private static final String NAME = "file name";
    private static final boolean HIDDEN = false;
    private static final boolean DELETABLE = true;
    private static final String MIME_TYPE = "mime type";
    private static final long VERSION_NUMBER = 1L;
    private static final String VERSION_DESCRIPTION = "version description";

    private final JcrNodeId jcrNodeVersionId = mock(JcrNodeId.class, "jcrNodeVersionId");
    private final JcrNodeId jcrNodeId = mock(JcrNodeId.class, "jcrNodeId");
    private final JcrNodePath jcrNodePath = mock(JcrNodePath.class);

    private FileVersionNode fileVersionNode;

    @Before
    public void setUp() throws Exception {
        FileNode fileNode = new FileNode(jcrNodeId, jcrNodePath, NAME, HIDDEN, DELETABLE, MIME_TYPE);
        fileVersionNode = new FileVersionNode(fileNode, jcrNodeVersionId, VERSION_NUMBER, VERSION_DESCRIPTION);
    }

    @Test
    public void shouldGetJcrNodeVersionId() throws Exception {
        assertThat(fileVersionNode.getJcrNodeId(), is(jcrNodeVersionId));
    }

    @Test
    public void shouldGetJcrNodePath() throws Exception {
        assertThat(fileVersionNode.getJcrNodePath(), is(jcrNodePath));
    }

    @Test
    public void shouldGetNodeName() throws Exception {
        assertThat(fileVersionNode.getName(), is(NAME));
    }

    @Test
    public void shouldGetHidden() throws Exception {
        assertThat(fileVersionNode.isHidden(), is(HIDDEN));
    }

    @Test
    public void shouldGetDeletable() throws Exception {
        assertThat(fileVersionNode.isDeletable(), is(DELETABLE));
    }

    @Test
    public void shouldGetMimeType() throws Exception {
        assertThat(fileVersionNode.getMimeType(), is(MIME_TYPE));
    }

    @Test
    public void shouldGetVersionLabel() throws Exception {
        assertThat(fileVersionNode.getVersionNumber(), is(VERSION_NUMBER));
    }

    @Test
    public void shouldGetVersionDescription() throws Exception {
        assertThat(fileVersionNode.getVersionDescription(), is(VERSION_DESCRIPTION));
    }

    @Test
    public void shouldBeEqualIfAllFieldsAreEqual() throws Exception {
        FileNode anotherFileNode = new FileNode(jcrNodeId, jcrNodePath, NAME, HIDDEN, DELETABLE, MIME_TYPE);
        FileVersionNode anotherFileVersionNode = new FileVersionNode(anotherFileNode, jcrNodeVersionId, VERSION_NUMBER, VERSION_DESCRIPTION);

        assertThat(fileVersionNode, is(anotherFileVersionNode));
    }

    @Test
    public void shouldNotBeEqualIfJcrNodeVersionIdIsDifferent() throws Exception {
        JcrNodeId anotherJcrNodeId = mock(JcrNodeId.class);
        FileNode anotherFileNode = new FileNode(jcrNodeId, jcrNodePath, NAME, HIDDEN, DELETABLE, MIME_TYPE);
        FileVersionNode anotherFileVersionNode = new FileVersionNode(anotherFileNode, anotherJcrNodeId, VERSION_NUMBER, VERSION_DESCRIPTION);

        assertThat(fileVersionNode, is(not(anotherFileVersionNode)));
    }

    @Test
    public void shouldNotBeEqualIfJcrNodePathIsDifferent() throws Exception {
        JcrNodePath anotherJcrNodePath = mock(JcrNodePath.class);
        FileNode anotherFileNode = new FileNode(jcrNodeId, anotherJcrNodePath, NAME, HIDDEN, DELETABLE, MIME_TYPE);
        FileVersionNode anotherFileVersionNode = new FileVersionNode(anotherFileNode, jcrNodeVersionId, VERSION_NUMBER, VERSION_DESCRIPTION);

        assertThat(fileVersionNode, is(not(anotherFileVersionNode)));
    }

    @Test
    public void shouldNotBeEqualIfNodeNameIsDifferent() throws Exception {
        String anotherName = "another file name";
        FileNode anotherFileNode = new FileNode(jcrNodeId, jcrNodePath, anotherName, HIDDEN, DELETABLE, MIME_TYPE);
        FileVersionNode anotherFileVersionNode = new FileVersionNode(anotherFileNode, jcrNodeVersionId, VERSION_NUMBER, VERSION_DESCRIPTION);

        assertThat(fileVersionNode, is(not(anotherFileVersionNode)));
    }

    @Test
    public void shouldNotBeEqualIfHiddenIsDifferent() throws Exception {
        boolean anotherHidden = true;
        FileNode anotherFileNode = new FileNode(jcrNodeId, jcrNodePath, NAME, anotherHidden, DELETABLE, MIME_TYPE);
        FileVersionNode anotherFileVersionNode = new FileVersionNode(anotherFileNode, jcrNodeVersionId, VERSION_NUMBER, VERSION_DESCRIPTION);

        assertThat(fileVersionNode, is(not(anotherFileVersionNode)));
    }

    @Test
    public void shouldNotBeEqualIfDeletableIsDifferent() throws Exception {
        boolean anotherDeletable = false;
        FileNode anotherFileNode = new FileNode(jcrNodeId, jcrNodePath, NAME, HIDDEN, anotherDeletable, MIME_TYPE);
        FileVersionNode anotherFileVersionNode = new FileVersionNode(anotherFileNode, jcrNodeVersionId, VERSION_NUMBER, VERSION_DESCRIPTION);

        assertThat(fileVersionNode, is(not(anotherFileVersionNode)));
    }

    @Test
    public void shouldNotBeEqualIfMimeTypeIsDifferent() throws Exception {
        String anotherMimeType = "another mime type";
        FileNode anotherFileNode = new FileNode(jcrNodeId, jcrNodePath, NAME, HIDDEN, DELETABLE, anotherMimeType);
        FileVersionNode anotherFileVersionNode = new FileVersionNode(anotherFileNode, jcrNodeVersionId, VERSION_NUMBER, VERSION_DESCRIPTION);

        assertThat(fileVersionNode, is(not(anotherFileVersionNode)));
    }

    @Test
    public void shouldNotBeEqualIfVersionNumberIsDifferent() throws Exception {
        FileNode anotherFileNode = new FileNode(jcrNodeId, jcrNodePath, NAME, HIDDEN, DELETABLE, MIME_TYPE);
        long anotherVersionNumber = 2L;
        FileVersionNode anotherFileVersionNode = new FileVersionNode(anotherFileNode, jcrNodeVersionId, anotherVersionNumber, VERSION_DESCRIPTION);

        assertThat(fileVersionNode, is(not(anotherFileVersionNode)));
    }

    @Test
    public void shouldNotBeEqualIfVersionDescriptionIsDifferent() throws Exception {
        FileNode anotherFileNode = new FileNode(jcrNodeId, jcrNodePath, NAME, HIDDEN, DELETABLE, MIME_TYPE);
        String anotherVersionDescription = "another version description";
        FileVersionNode anotherFileVersionNode = new FileVersionNode(anotherFileNode, jcrNodeVersionId, VERSION_NUMBER, anotherVersionDescription);

        assertThat(fileVersionNode, is(not(anotherFileVersionNode)));
    }

}
