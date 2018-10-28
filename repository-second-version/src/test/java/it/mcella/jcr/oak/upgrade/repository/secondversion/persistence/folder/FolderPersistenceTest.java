package it.mcella.jcr.oak.upgrade.repository.secondversion.persistence.folder;

import it.mcella.jcr.oak.upgrade.repository.JcrNode;
import it.mcella.jcr.oak.upgrade.repository.JcrNodeId;
import it.mcella.jcr.oak.upgrade.repository.JcrRepository;
import it.mcella.jcr.oak.upgrade.repository.JcrSession;
import it.mcella.jcr.oak.upgrade.repository.secondversion.node.folder.FolderNode;
import it.mcella.jcr.oak.upgrade.repository.secondversion.node.folder.JcrFolderNode;
import it.mcella.jcr.oak.upgrade.repository.secondversion.node.folder.JcrFolderNodeFactory;
import it.mcella.jcr.oak.upgrade.repository.secondversion.node.folder.OakFolderNode;
import org.junit.Before;
import org.junit.Test;

import javax.jcr.RepositoryException;
import javax.jcr.SimpleCredentials;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FolderPersistenceTest {

    private static final String FOLDER_NAME = "folder name";
    private static final String FOLDER_DESCRIPTION = "folder description";

    private final JcrRepository jcrRepository = mock(JcrRepository.class);
    private final JcrFolderNodeFactory jcrFolderNodeFactory = mock(JcrFolderNodeFactory.class);
    private final JcrSession jcrSession = mock(JcrSession.class);
    private final JcrNode jcrParentNode = mock(JcrNode.class, "parent");

    private FolderPersistence folderPersistence;

    @Before
    public void setUp() throws Exception {
        folderPersistence = new FolderPersistence(jcrRepository, jcrFolderNodeFactory);

        when(jcrRepository.login(any(SimpleCredentials.class))).thenReturn(jcrSession);
    }

    @Test
    public void shouldCreateFolderIntoRootNode() throws Exception {
        JcrNode jcrRootNode = mock(JcrNode.class, "root");
        when(jcrSession.getRootNode()).thenReturn(jcrRootNode);
        JcrNode jcrNode = mock(JcrNode.class, "folder");
        when(jcrRootNode.addNode(any(String.class), eq(OakFolderNode.NODE_TYPE))).thenReturn(jcrNode);
        JcrFolderNode jcrFolderNode = mock(JcrFolderNode.class);
        when(jcrFolderNodeFactory.createFrom(jcrNode)).thenReturn(jcrFolderNode);

        folderPersistence.createIntoRoot(FOLDER_NAME, FOLDER_DESCRIPTION);

        verify(jcrRootNode).addNode(FOLDER_NAME, OakFolderNode.NODE_TYPE);
        verify(jcrFolderNode).setDescription(FOLDER_DESCRIPTION);
        verify(jcrSession).save();
    }

    @Test
    public void shouldCreateFolderIntoParentFolderNode() throws Exception {
        when(jcrSession.getNodeById(any(JcrNodeId.class))).thenReturn(jcrParentNode);
        JcrNode jcrNode = mock(JcrNode.class, "folder");
        when(jcrParentNode.addNode(any(String.class), eq(JcrFolderNode.NODE_TYPE))).thenReturn(jcrNode);
        JcrFolderNode jcrFolderNode = mock(JcrFolderNode.class);
        when(jcrFolderNodeFactory.createFrom(jcrNode)).thenReturn(jcrFolderNode);
        FolderNode parentFolderNode = mock(FolderNode.class);

        folderPersistence.createInto(parentFolderNode, FOLDER_NAME, FOLDER_DESCRIPTION);

        verify(jcrParentNode).addNode(FOLDER_NAME, JcrFolderNode.NODE_TYPE);
        verify(jcrFolderNode).setDescription(FOLDER_DESCRIPTION);
        verify(jcrSession).save();
    }

    @Test
    public void shouldCreateFolderIntoParentFolderNodeGivenParentFolderNodeId() throws Exception {
        JcrNodeId jcrParentNodeId = mock(JcrNodeId.class);
        when(jcrSession.getNodeById(jcrParentNodeId)).thenReturn(jcrParentNode);
        JcrNode jcrNode = mock(JcrNode.class, "folder");
        when(jcrParentNode.addNode(any(String.class), eq(JcrFolderNode.NODE_TYPE))).thenReturn(jcrNode);
        JcrFolderNode jcrFolderNode = mock(JcrFolderNode.class);
        when(jcrFolderNodeFactory.createFrom(jcrNode)).thenReturn(jcrFolderNode);

        folderPersistence.createInto(jcrParentNodeId, FOLDER_NAME, FOLDER_DESCRIPTION);

        verify(jcrParentNode).addNode(FOLDER_NAME, JcrFolderNode.NODE_TYPE);
        verify(jcrFolderNode).setDescription(FOLDER_DESCRIPTION);
        verify(jcrSession).save();
    }

    @Test(expected = FolderPersistenceException.class)
    public void shouldThrowFolderPersistenceExceptionOnAddNodeRepositoryException() throws Exception {
        when(jcrSession.getNodeById(any(JcrNodeId.class))).thenReturn(jcrParentNode);
        when(jcrParentNode.addNode(any(String.class), eq(JcrFolderNode.NODE_TYPE)))
                .thenThrow(new RepositoryException());
        JcrNodeId jcrParentNodeId = mock(JcrNodeId.class);

        folderPersistence.createInto(jcrParentNodeId, FOLDER_NAME, FOLDER_DESCRIPTION);
    }

}
