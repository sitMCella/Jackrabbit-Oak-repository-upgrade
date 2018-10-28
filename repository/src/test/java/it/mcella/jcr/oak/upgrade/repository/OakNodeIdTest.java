package it.mcella.jcr.oak.upgrade.repository;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

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

        assertThat(oakNodeId, is(anotherOakNodeId));
    }

    @Test
    public void shouldNotBeEqualIfNodeIdIsDifferent() throws Exception {
        OakNodeId anotherOakNodeId = new OakNodeId("another node id");

        assertThat(oakNodeId, is(not(anotherOakNodeId)));
    }

}