package it.mcella.jcr.oak.upgrade.repository.secondversion.update;

import it.mcella.jcr.oak.upgrade.repository.JcrNodeId;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class JcrNodeToDescriptionTest {

    private static final String FIST_NODE_DESCRIPTION = "first node description";
    private static final String SECOND_NODE_DESCRIPTION_FIRST_FIELD = "original node description";
    private static final String SECOND_NODE_DESCRIPTION_SECOND_FIELD = "first version node description";
    private static final String SECOND_NODE_DESCRIPTION = SECOND_NODE_DESCRIPTION_FIRST_FIELD + "," + SECOND_NODE_DESCRIPTION_SECOND_FIELD;

    private final JcrNodeId FIRST_JCR_NODE_ID = mock(JcrNodeId.class);
    private final JcrNodeId SECOND_JCR_NODE_ID = mock(JcrNodeId.class);

    private Map<JcrNodeId, String> jcrNodeIdToDescription;
    private JcrNodeToDescription jcrNodeToDescription;

    @Before
    public void setUp() throws Exception {
        jcrNodeIdToDescription = new HashMap<>();
        jcrNodeIdToDescription.put(FIRST_JCR_NODE_ID, FIST_NODE_DESCRIPTION);
        jcrNodeIdToDescription.put(SECOND_JCR_NODE_ID, SECOND_NODE_DESCRIPTION);
        jcrNodeToDescription = new JcrNodeToDescription(jcrNodeIdToDescription);
    }

    @Test
    public void shouldRetrieveDefaultDescriptionFromJcrNodeIdIfMappingIsNull() throws Exception {
        jcrNodeToDescription = new JcrNodeToDescription(null);

        String description = jcrNodeToDescription.retrieveDescription(FIRST_JCR_NODE_ID);

        assertThat(description, is(JcrNodeToDescription.DEFAULT_DESCRIPTION));
    }

    @Test
    public void shouldRetrieveDefaultDescriptionFromJcrNodeIdIfMappingDoesNotExist() throws Exception {
        JcrNodeId anotherJcrNodeId = mock(JcrNodeId.class);

        String description = jcrNodeToDescription.retrieveDescription(anotherJcrNodeId);

        assertThat(description, is(JcrNodeToDescription.DEFAULT_DESCRIPTION));
    }

    @Test
    public void shouldRetrieveDescriptionFromJcrNodeId() throws Exception {
        String description = jcrNodeToDescription.retrieveDescription(FIRST_JCR_NODE_ID);

        assertThat(description, is(FIST_NODE_DESCRIPTION));
    }

    @Test
    public void shouldRetrieveFirstDescriptionFieldFromJcrNodeId() throws Exception {
        String description = jcrNodeToDescription.retrieveDescription(SECOND_JCR_NODE_ID);

        assertThat(description, is(SECOND_NODE_DESCRIPTION_FIRST_FIELD));
    }

    @Test
    public void shouldRetrieveSecondDescriptionFieldFromJcrNodeId() throws Exception {
        jcrNodeToDescription.retrieveDescription(SECOND_JCR_NODE_ID);

        String description = jcrNodeToDescription.retrieveDescription(SECOND_JCR_NODE_ID);

        assertThat(description, is(SECOND_NODE_DESCRIPTION_SECOND_FIELD));
    }

    @Test
    public void shouldRetrieveDefaultDescriptionFromJcrNodeIdIfThereAreNoMoreFields() throws Exception {
        jcrNodeToDescription.retrieveDescription(SECOND_JCR_NODE_ID);
        jcrNodeToDescription.retrieveDescription(SECOND_JCR_NODE_ID);

        String description = jcrNodeToDescription.retrieveDescription(SECOND_JCR_NODE_ID);

        assertThat(description, is(JcrNodeToDescription.DEFAULT_DESCRIPTION));
    }

}