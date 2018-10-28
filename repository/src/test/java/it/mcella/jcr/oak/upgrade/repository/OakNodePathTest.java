package it.mcella.jcr.oak.upgrade.repository;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class OakNodePathTest {

    private static final String NODE_PATH = "/path/to/node";

    private OakNodePath oakNodePath;

    @Before
    public void setUp() throws Exception {
        oakNodePath = new OakNodePath(NODE_PATH);
    }

    @Test
    public void shouldGetNodePath() throws Exception {
        assertThat(oakNodePath.getPath(), is(NODE_PATH));
    }

    @Test
    public void shouldGetChildNodePathFromChildNodeName() throws Exception {
        String childNodeName = "childNodeName";

        String childNodePath = oakNodePath.getChildNodePath(childNodeName);

        assertThat(childNodePath, is(NODE_PATH + "/" + childNodeName));
    }

    @Test
    public void shouldBeEqualIfNodePathIsEqual() throws Exception {
        OakNodePath anotherOakNodePath = new OakNodePath(NODE_PATH);

        assertTrue(oakNodePath.equals(anotherOakNodePath) && anotherOakNodePath.equals(oakNodePath));
        assertThat(oakNodePath.hashCode(), is(anotherOakNodePath.hashCode()));
    }

    @Test
    public void shouldBeEqualIfNodePathIsDifferent() throws Exception {
        OakNodePath anotherOakNodePath = new OakNodePath("/another/path/to/node");

        assertFalse(oakNodePath.equals(anotherOakNodePath));
    }

}