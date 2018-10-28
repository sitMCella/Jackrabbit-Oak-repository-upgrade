package it.mcella.jcr.oak.upgrade.repository.secondversion.persistence.file;

import it.mcella.jcr.oak.upgrade.repository.JcrNode;
import it.mcella.jcr.oak.upgrade.repository.JcrNodeId;
import it.mcella.jcr.oak.upgrade.repository.JcrRepository;
import it.mcella.jcr.oak.upgrade.repository.JcrSession;
import it.mcella.jcr.oak.upgrade.repository.secondversion.node.file.JcrFileNode;
import it.mcella.jcr.oak.upgrade.repository.secondversion.node.file.JcrFileNodeFactory;
import it.mcella.jcr.oak.upgrade.repository.secondversion.node.file.OakFileNode;
import it.mcella.jcr.oak.upgrade.repository.secondversion.node.folder.FolderNode;
import org.junit.Before;
import org.junit.Test;

import javax.jcr.RepositoryException;
import javax.jcr.SimpleCredentials;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FilePersistenceTest {

    private static final String FILE_NAME = "file name";
    private static final boolean SYSTEM = true;
    private static final String FILE_DESCRIPTION = "file description";

    private final JcrRepository jcrRepository = mock(JcrRepository.class);
    private final JcrFileNodeFactory jcrFileNodeFactory = mock(JcrFileNodeFactory.class);
    private final JcrSession jcrSession = mock(JcrSession.class);
    private final JcrNode jcrNode = mock(JcrNode.class, "node");
    private final JcrFileNode jcrFileNode = mock(JcrFileNode.class);
    private final JcrNode jcrRootNode = mock(JcrNode.class, "root");
    private final JcrNode jcrParentNode = mock(JcrNode.class, "parent");

    private FilePersistence filePersistence;

    @Before
    public void setUp() throws Exception {
        filePersistence = new FilePersistence(jcrRepository, jcrFileNodeFactory);

        when(jcrRepository.login(any(SimpleCredentials.class))).thenReturn(jcrSession);
        when(jcrSession.getRootNode()).thenReturn(jcrRootNode);
    }

    @Test
    public void shouldCreateFileIntoRootNode() throws Exception {
        when(jcrRootNode.addNode(any(String.class), eq(JcrFileNode.NODE_TYPE))).thenReturn(jcrNode);
        when(jcrFileNodeFactory.createFrom(jcrNode)).thenReturn(jcrFileNode);
        Path image = Paths.get(ClassLoader.getSystemResource("fox.jpg").toURI());

        filePersistence.createIntoRoot(FILE_NAME, image, SYSTEM, FILE_DESCRIPTION);

        verify(jcrRootNode).addNode(FILE_NAME, JcrFileNode.NODE_TYPE);
        verify(jcrFileNode).setSystem(SYSTEM);
        verify(jcrFileNode).setDescription(FILE_DESCRIPTION);
        verify(jcrFileNode).createContent(eq(jcrSession), eq(image), any(InputStream.class));
        verify(jcrSession).save();
    }

    @Test
    public void shouldCreateFileIntoParentFolderNode() throws Exception {
        when(jcrSession.getNodeById(any(JcrNodeId.class))).thenReturn(jcrParentNode);
        when(jcrParentNode.addNode(any(String.class), eq(OakFileNode.NODE_TYPE))).thenReturn(jcrNode);
        when(jcrFileNodeFactory.createFrom(jcrNode)).thenReturn(jcrFileNode);
        FolderNode parentFolderNode = mock(FolderNode.class);
        Path image = Paths.get(ClassLoader.getSystemResource("fox.jpg").toURI());

        filePersistence.createInto(parentFolderNode, FILE_NAME, image, SYSTEM, FILE_DESCRIPTION);

        verify(jcrParentNode).addNode(FILE_NAME, OakFileNode.NODE_TYPE);
        verify(jcrFileNode).setSystem(SYSTEM);
        verify(jcrFileNode).setDescription(FILE_DESCRIPTION);
        verify(jcrFileNode).createContent(eq(jcrSession), eq(image), any(InputStream.class));
        verify(jcrSession).save();
    }

    @Test
    public void shouldCreateFileIntoParentFolderNodeGivenParentFolderNodeId() throws Exception {
        JcrNodeId jcrParentNodeId = mock(JcrNodeId.class);
        when(jcrSession.getNodeById(jcrParentNodeId)).thenReturn(jcrParentNode);
        when(jcrParentNode.addNode(any(String.class), eq(OakFileNode.NODE_TYPE))).thenReturn(jcrNode);
        when(jcrFileNodeFactory.createFrom(jcrNode)).thenReturn(jcrFileNode);
        Path image = Paths.get(ClassLoader.getSystemResource("fox.jpg").toURI());

        filePersistence.createInto(jcrParentNodeId, FILE_NAME, image, SYSTEM, FILE_DESCRIPTION);

        verify(jcrParentNode).addNode(FILE_NAME, OakFileNode.NODE_TYPE);
        verify(jcrFileNode).setSystem(SYSTEM);
        verify(jcrFileNode).setDescription(FILE_DESCRIPTION);
        verify(jcrFileNode).createContent(eq(jcrSession), eq(image), any(InputStream.class));
        verify(jcrSession).save();
    }

    @Test(expected = FilePersistenceException.class)
    public void shouldThrowFilePersistenceExceptionOnAddNodeRepositoryException() throws Exception {
        when(jcrSession.getNodeById(any(JcrNodeId.class))).thenReturn(jcrParentNode);
        when(jcrParentNode.addNode(any(String.class), eq(OakFileNode.NODE_TYPE))).thenThrow(new RepositoryException());
        JcrNodeId jcrParentNodeId = mock(JcrNodeId.class);
        Path image = Paths.get(ClassLoader.getSystemResource("fox.jpg").toURI());

        filePersistence.createInto(jcrParentNodeId, FILE_NAME, image, SYSTEM, FILE_DESCRIPTION);
    }

}
