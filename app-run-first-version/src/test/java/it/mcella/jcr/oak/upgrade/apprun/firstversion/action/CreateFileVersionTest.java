package it.mcella.jcr.oak.upgrade.apprun.firstversion.action;

import it.mcella.jcr.oak.upgrade.repository.JcrNodeId;
import it.mcella.jcr.oak.upgrade.repository.OakNodeId;
import it.mcella.jcr.oak.upgrade.repository.firstversion.persistence.file.FileVersionPersistence;
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

public class CreateFileVersionTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    private static final String FILE_NODE_ID = "file-node-id";
    private static final String DESCRIPTION = "version description";

    private final FileVersionPersistence fileVersionPersistence = mock(FileVersionPersistence.class);

    private CreateFileVersion createFileVersion;
    private String filePath;

    @Before
    public void setUp() throws Exception {
        File file = temporaryFolder.newFile("file_name.dat");
        filePath = file.getAbsolutePath();
        createFileVersion = new CreateFileVersion(fileVersionPersistence, FILE_NODE_ID, filePath, DESCRIPTION);
    }

    @Test
    public void shouldReturnCreateFileVersionActionType() throws Exception {
        assertThat(createFileVersion.getType(), is(ActionType.CREATE_FILE_VERSION));
    }

    @Test
    public void shouldCreateFileVersionNode() throws Exception {
        JcrNodeId jcrFileNodeId = new OakNodeId(FILE_NODE_ID);
        Path file = Paths.get(filePath);

        createFileVersion.execute();

        verify(fileVersionPersistence).createNewVersion(jcrFileNodeId, DESCRIPTION, file);
    }

}