package it.mcella.jcr.oak.upgrade.repository;

import org.junit.Before;
import org.junit.Test;

import javax.jcr.nodetype.NodeType;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OakNodeTypeTest {

    private final NodeType nodeType = mock(NodeType.class);

    private OakNodeType oakNodeType;

    @Before
    public void setUp() throws Exception {
        oakNodeType = new OakNodeType(nodeType);
    }

    @Test
    public void shouldGetNodeTypeName() throws Exception {
        String nodeTypeName = "nodeTypeName";
        when(nodeType.getName()).thenReturn(nodeTypeName);

        String oakNodeTypeName = oakNodeType.getName();

        assertThat(oakNodeTypeName, is(nodeTypeName));
    }

    @Test
    public void shouldCheckIfNodeTypeBelongsToGivenNamespace() throws Exception {
        JcrNamespace jcrNamespace = mock(JcrNamespace.class);

        oakNodeType.belongsTo(jcrNamespace);

        verify(jcrNamespace).isAppliedTo(oakNodeType);
    }

    @Test
    public void shouldCheckIfNodeTypeHasOrderableChildNodes() throws Exception {
        oakNodeType.hasOrderableChildNodes();

        verify(nodeType).hasOrderableChildNodes();
    }

    @Test
    public void shouldReturnNodeType() throws Exception {
        assertThat(oakNodeType.getNodeType(), is(nodeType));
    }

}