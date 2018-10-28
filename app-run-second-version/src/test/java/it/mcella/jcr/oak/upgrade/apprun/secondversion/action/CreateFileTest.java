package it.mcella.jcr.oak.upgrade.apprun.secondversion.action;

import it.mcella.jcr.oak.upgrade.repository.JcrNodeId;
import it.mcella.jcr.oak.upgrade.repository.OakNodeId;
import it.mcella.jcr.oak.upgrade.repository.secondversion.persistence.file.FilePersistence;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CreateFileTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    private static final String PARENT_NODE_ID = "parentNodeId";
    private static final String FILE_NODE_NAME = "file_name";
    private static final boolean SYSTEM = false;
    private static final String FILE_NODE_DESCRIPTION = "file description";

    private final FilePersistence filePersistence = mock(FilePersistence.class);

    private CreateFile createFile;
    private String filePath;

    @Before
    public void setUp() throws Exception {
        File file = temporaryFolder.newFile("file_name.dat");
        filePath = file.getAbsolutePath();
        createFile = new CreateFile(filePersistence, PARENT_NODE_ID, FILE_NODE_NAME, filePath, SYSTEM, FILE_NODE_DESCRIPTION);
    }

    @Test
    public void shouldReturnCreateFileActionType() throws Exception {
        assertThat(createFile.getType(), is(ActionType.CREATE_FILE));
    }

    @Test
    public void shouldCreateFileNode() throws Exception {
        JcrNodeId jcrNodeId = new OakNodeId(PARENT_NODE_ID);
        Path file = Paths.get(filePath);

        createFile.execute();

        verify(filePersistence).createInto(jcrNodeId, FILE_NODE_NAME, file, SYSTEM, FILE_NODE_DESCRIPTION);
    }

    @Test
    public void shouldGetParentNodeId() throws Exception {
        assertThat(createFile.getParentNodeId(), is(PARENT_NODE_ID));
    }

    @Test
    public void shouldGetFileName() throws Exception {
        assertThat(createFile.getFileName(), is(FILE_NODE_NAME));
    }

    @Test
    public void shouldGetFilePath() throws Exception {
        assertThat(createFile.getFilePath(), is(filePath));
    }

    @Test
    public void shouldGetSystem() throws Exception {
        assertThat(createFile.isSystem(), is(SYSTEM));
    }

    @Test
    public void shouldGetDescription() throws Exception {
        assertThat(createFile.getDescription(), is(FILE_NODE_DESCRIPTION));
    }

}