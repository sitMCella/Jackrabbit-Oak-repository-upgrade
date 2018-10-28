package it.mcella.jcr.oak.upgrade.apprun.firstversion.action;

import it.mcella.jcr.oak.upgrade.repository.OakNodeId;
import it.mcella.jcr.oak.upgrade.repository.firstversion.retrieve.file.FileRetrieve;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class RetrieveFileTest {

    private static final String NODE_ID = "node-id";

    private final FileRetrieve fileRetrieve = mock(FileRetrieve.class);

    private RetrieveFile retrieveFile;

    @Before
    public void setUp() throws Exception {
        this.retrieveFile = new RetrieveFile(fileRetrieve, NODE_ID);
    }

    @Test
    public void shouldReturnListNodesActionType() throws Exception {
        assertThat(retrieveFile.getType(), is(ActionType.RETRIEVE_FILE));
    }

    @Test
    public void shouldRetrieveFileFromNodeId() throws Exception {
        retrieveFile.execute();

        verify(fileRetrieve).retrieveFrom(new OakNodeId(NODE_ID));
    }

}