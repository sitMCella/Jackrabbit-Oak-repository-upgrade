package it.mcella.jcr.oak.upgrade.repository.firstversion.persistence.file;

import it.mcella.jcr.oak.upgrade.repository.JcrNode;
import it.mcella.jcr.oak.upgrade.repository.JcrNodeId;
import it.mcella.jcr.oak.upgrade.repository.JcrNodePath;
import it.mcella.jcr.oak.upgrade.repository.JcrRepository;
import it.mcella.jcr.oak.upgrade.repository.JcrSession;
import it.mcella.jcr.oak.upgrade.repository.firstversion.node.file.FileNode;
import it.mcella.jcr.oak.upgrade.repository.firstversion.node.file.JcrFileNode;
import it.mcella.jcr.oak.upgrade.repository.firstversion.node.file.JcrFileVersionNode;
import it.mcella.jcr.oak.upgrade.repository.firstversion.node.file.JcrFileVersionNodeFactory;
import org.junit.Before;
import org.junit.Test;

import javax.jcr.RepositoryException;
import javax.jcr.SimpleCredentials;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FileVersionPersistenceTest {

    private static final String VERSION_DESCRIPTION = "version description";

    private final JcrRepository jcrRepository = mock(JcrRepository.class);
    private final JcrFileVersionNodeFactory jcrFileVersionNodeFactory = mock(JcrFileVersionNodeFactory.class);
    private final JcrSession jcrSession = mock(JcrSession.class);
    private final JcrNode jcrNode = mock(JcrNode.class, "file");
    private final JcrFileVersionNode jcrFileVersionNode = mock(JcrFileVersionNode.class);
    private final JcrNodeId jcrFileNodeId = mock(JcrNodeId.class);

    private FileVersionPersistence fileVersionPersistence;
    private Path image;

    @Before
    public void setUp() throws Exception {
        fileVersionPersistence = new FileVersionPersistence(jcrRepository, jcrFileVersionNodeFactory);
        image = Paths.get(ClassLoader.getSystemResource("fox.jpg").toURI());

        when(jcrRepository.login(any(SimpleCredentials.class))).thenReturn(jcrSession);
    }

    @Test
    public void shouldCreateNewFileVersionNode() throws Exception {
        when(jcrSession.getNodeById(any(JcrNodeId.class))).thenReturn(jcrNode);
        when(jcrNode.hasType(JcrFileNode.NODE_TYPE)).thenReturn(true);
        when(jcrFileVersionNodeFactory.createFrom(jcrNode)).thenReturn(jcrFileVersionNode);
        when(jcrFileVersionNode.isVersioned()).thenReturn(false);
        JcrNodePath jcrNodePath = mock(JcrNodePath.class);
        boolean hidden = false;
        boolean deletable = false;
        FileNode fileNode = new FileNode(jcrFileNodeId, jcrNodePath, "file name", hidden, deletable, "mime type");

        fileVersionPersistence.createNewVersion(fileNode, VERSION_DESCRIPTION, image);

        verify(jcrFileVersionNode).firstVersion();
        verify(jcrFileVersionNode).newVersion(VERSION_DESCRIPTION, jcrSession, image);
    }

    @Test
    public void shouldCreateNewFileVersionNodeFromFileNodeId() throws Exception {
        when(jcrSession.getNodeById(jcrFileNodeId)).thenReturn(jcrNode);
        when(jcrNode.hasType(JcrFileNode.NODE_TYPE)).thenReturn(true);
        when(jcrFileVersionNodeFactory.createFrom(jcrNode)).thenReturn(jcrFileVersionNode);
        when(jcrFileVersionNode.isVersioned()).thenReturn(false);

        fileVersionPersistence.createNewVersion(jcrFileNodeId, VERSION_DESCRIPTION, image);

        verify(jcrFileVersionNode).firstVersion();
        verify(jcrFileVersionNode).newVersion(VERSION_DESCRIPTION, jcrSession, image);
    }

    @Test(expected = FileVersionPersistenceException.class)
    public void shouldThrowFileVersionPersistenceExceptionIfNotFileNode() throws Exception {
        when(jcrSession.getNodeById(any(JcrNodeId.class))).thenReturn(jcrNode);
        when(jcrNode.hasType(JcrFileNode.NODE_TYPE)).thenReturn(false);

        fileVersionPersistence.createNewVersion(jcrFileNodeId, VERSION_DESCRIPTION, image);
    }

    @Test(expected = FileVersionPersistenceException.class)
    public void shouldThrowFileVersionPersistenceExceptionOnAddNodeVersionRepositoryException() throws Exception {
        when(jcrSession.getNodeById(any(JcrNodeId.class))).thenReturn(jcrNode);
        when(jcrNode.hasType(JcrFileNode.NODE_TYPE)).thenReturn(true);
        when(jcrFileVersionNodeFactory.createFrom(jcrNode)).thenReturn(jcrFileVersionNode);
        when(jcrFileVersionNode.isVersioned()).thenReturn(false);
        when(jcrFileVersionNode.newVersion(VERSION_DESCRIPTION, jcrSession, image)).thenThrow(new RepositoryException());

        fileVersionPersistence.createNewVersion(jcrFileNodeId, VERSION_DESCRIPTION, image);
    }

}
