package it.mcella.jcr.oak.upgrade.repository;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class OakNodeIdTest {

    private static final String NODE_ID = "node id";

    private OakNodeId oakNodeId;

    @Before
    public void setUp() throws Exception {
        oakNodeId = new OakNodeId(NODE_ID);
    }

    @Test
    public void shouldGetNodeId() throws Exception {
        assertThat(oakNodeId.getNodeId(), is(NODE_ID));
    }

    @Test
    public void shouldBeEqualIfNodeIdIsEqual() throws Exception {
        OakNodeId anotherOakNodeId = new OakNodeId(NODE_ID);

        assertTrue(oakNodeId.equals(anotherOakNodeId) && anotherOakNodeId.equals(oakNodeId));
        assertThat(oakNodeId.hashCode(), is(anotherOakNodeId.hashCode()));
    }

    @Test
    public void shouldBeEqualIfNodeIdsAreNull() throws Exception {
        OakNodeId nullOakNodeId = new OakNodeId(null);
        OakNodeId anotherNullOakNodeId = new OakNodeId(null);

        assertTrue(nullOakNodeId.equals(anotherNullOakNodeId) && anotherNullOakNodeId.equals(nullOakNodeId));
    }

    @Test
    public void shouldNotBeEqualIfNodeIdIsDifferent() throws Exception {
        OakNodeId anotherOakNodeId = new OakNodeId("another node id");

        assertFalse(oakNodeId.equals(anotherOakNodeId));
    }

    @Test
    public void shouldGetStringRepresentation() throws Exception {
        String expected = String.format("OakNodeId{nodeId='%s'}", NODE_ID);

        assertThat(oakNodeId.toString(), is(expected));
    }

}