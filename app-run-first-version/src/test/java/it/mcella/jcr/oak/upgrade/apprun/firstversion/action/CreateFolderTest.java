package it.mcella.jcr.oak.upgrade.apprun.firstversion.action;

import it.mcella.jcr.oak.upgrade.repository.JcrNodeId;
import it.mcella.jcr.oak.upgrade.repository.OakNodeId;
import it.mcella.jcr.oak.upgrade.repository.firstversion.persistence.folder.FolderPersistence;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CreateFolderTest {

    private static final String PARENT_NODE_ID = "parentNodeId";
    private static final String FOLDER_NODE_NAME = "folder_name";
    private static final boolean HIDDEN = true;

    private final FolderPersistence folderPersistence = mock(FolderPersistence.class);

    private CreateFolder createFolder;

    @Before
    public void setUp() throws Exception {
        createFolder = new CreateFolder(folderPersistence, PARENT_NODE_ID, FOLDER_NODE_NAME, HIDDEN);
    }

    @Test
    public void shouldReturnCreateFolderActionType() throws Exception {
        assertThat(createFolder.getType(), is(ActionType.CREATE_FOLDER));
    }

    @Test
    public void shouldCreateFileNode() throws Exception {
        JcrNodeId jcrNodeId = new OakNodeId(PARENT_NODE_ID);

        createFolder.execute();

        verify(folderPersistence).createInto(jcrNodeId, FOLDER_NODE_NAME, HIDDEN);
    }

}