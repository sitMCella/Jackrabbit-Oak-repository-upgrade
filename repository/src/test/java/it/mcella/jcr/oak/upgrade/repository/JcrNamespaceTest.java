package it.mcella.jcr.oak.upgrade.repository;

import org.junit.Before;
import org.junit.Test;

import javax.jcr.Value;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class JcrNamespaceTest {

    private static final String PREFIX = "prefix";
    private static final String URI = "uri";

    private JcrNamespace jcrNamespace;

    @Before
    public void setUp() throws Exception {
        jcrNamespace = new JcrNamespace(PREFIX, URI);
    }

    @Test
    public void shouldReturnTrueIfNamespaceIsAppliedToGivenJcrNodeTypeName() throws Exception {
        JcrNodeType jcrNodeType = mock(JcrNodeType.class);
        String nodeTypeName = PREFIX + ":" + "typeName";
        when(jcrNodeType.getName()).thenReturn(nodeTypeName);

        assertTrue(jcrNamespace.isAppliedTo(jcrNodeType));
    }

    @Test
    public void shouldReturnFalseIfAnotherNamespaceIsAppliedToGivenJcrNodeTypeName() throws Exception {
        JcrNodeType jcrNodeType = mock(JcrNodeType.class);
        String nodeTypeName = "anotherPrefix" + ":" + "typeName";
        when(jcrNodeType.getName()).thenReturn(nodeTypeName);

        assertFalse(jcrNamespace.isAppliedTo(jcrNodeType));
    }

    @Test
    public void shouldReturnTrueIfNamespaceIsAppliedToGivenJcrNodeName() throws Exception {
        JcrNode jcrNode = mock(JcrNode.class);
        String nodeName = PREFIX + ":" + "name";
        when(jcrNode.getName()).thenReturn(nodeName);

        assertTrue(jcrNamespace.isAppliedTo(jcrNode));
    }

    @Test
    public void shouldReturnFalseIfAnotherNamespaceIsAppliedToGivenJcrNodeName() throws Exception {
        JcrNode jcrNode = mock(JcrNode.class);
        String nodeName = "anotherPrefix" + ":" + "name";
        when(jcrNode.getName()).thenReturn(nodeName);

        assertFalse(jcrNamespace.isAppliedTo(jcrNode));
    }

    @Test
    public void shouldReturnFalseIfGivenJcrNodeNameHasNoNamespacePrefix() throws Exception {
        JcrNode jcrNode = mock(JcrNode.class);
        String nodeName = "name";
        when(jcrNode.getName()).thenReturn(nodeName);

        assertFalse(jcrNamespace.isAppliedTo(jcrNode));
    }

    @Test
    public void shouldReturnTrueIfNamespaceIsAppliedToGivenJcrPropertyName() throws Exception {
        JcrProperty jcrProperty = mock(JcrProperty.class);
        String propertyName = PREFIX + ":" + "propertyName";
        when(jcrProperty.getName()).thenReturn(propertyName);

        assertTrue(jcrNamespace.isAppliedTo(jcrProperty));
    }

    @Test
    public void shouldReturnFalseIfAnotherNamespaceIsAppliedToGivenJcrPropertyName() throws Exception {
        JcrProperty jcrProperty = mock(JcrProperty.class);
        String propertyName = "anotherPrefix" + ":" + "propertyName";
        when(jcrProperty.getName()).thenReturn(propertyName);

        assertFalse(jcrNamespace.isAppliedTo(jcrProperty));
    }

    @Test
    public void shouldReturnTrueIfNamespaceIsAppliedToGivenValue() throws Exception {
        Value value = mock(Value.class);
        String valueText = PREFIX + ":" + "value";
        when(value.getString()).thenReturn(valueText);

        assertTrue(jcrNamespace.isAppliedTo(value));
    }

    @Test
    public void shouldReturnFalseIfAnotherNamespaceIsAppliedToGivenValue() throws Exception {
        Value value = mock(Value.class);
        String valueText = "anotherPrefix" + ":" + "value";
        when(value.getString()).thenReturn(valueText);

        assertFalse(jcrNamespace.isAppliedTo(value));
    }

    @Test
    public void shouldGetJcrNodeTypeNameWithNewNamespacePrefix() throws Exception {
        JcrNodeType jcrNodeType = mock(JcrNodeType.class);
        String nodeTypeNameWithoutPrefix = "nodeTypeName";
        String nodeTypeName = "anotherPrefix" + ":" + nodeTypeNameWithoutPrefix;
        when(jcrNodeType.getName()).thenReturn(nodeTypeName);

        String newNodeTypeName = jcrNamespace.getNodeTypeNameFrom(jcrNodeType);

        assertThat(newNodeTypeName, is(PREFIX + ":" + nodeTypeNameWithoutPrefix));
    }

    @Test
    public void shouldGetJcrNodeNameWithNewNamespacePrefix() throws Exception {
        JcrNode jcrNode = mock(JcrNode.class);
        String nodeNameWithoutPrefix = "nodeName";
        String nodeName = "anotherPrefix" + ":" + nodeNameWithoutPrefix;
        when(jcrNode.getName()).thenReturn(nodeName);

        String newNodeName = jcrNamespace.getNodeNameFrom(jcrNode);

        assertThat(newNodeName, is(PREFIX + ":" + nodeNameWithoutPrefix));
    }

    @Test
    public void shouldGetJcrPropertyNodeNameWithNewNamespacePrefix() throws Exception {
        JcrProperty jcrProperty = mock(JcrProperty.class);
        String propertyNameWithoutPrefix = "propertyName";
        String propertyName = "anotherPrefix" + ":" + propertyNameWithoutPrefix;
        when(jcrProperty.getName()).thenReturn(propertyName);

        String newPropertyName = jcrNamespace.getPropertyNameFrom(jcrProperty);

        assertThat(newPropertyName, is(PREFIX + ":" + propertyNameWithoutPrefix));
    }

    @Test
    public void shouldGetValueNameWithNewNamespacePrefix() throws Exception {
        Value value = mock(Value.class);
        String valueNameWithoutPrefix = "valueName";
        String valueName = "anotherPrefix" + ":" + valueNameWithoutPrefix;
        when(value.getString()).thenReturn(valueName);

        String newValueName = jcrNamespace.getValueNameFrom(value);

        assertThat(newValueName, is(PREFIX + ":" + valueNameWithoutPrefix));
    }

    @Test
    public void shouldGetNodePathFromParentNodeWithNewNamespacePrefix() throws Exception {
        JcrNode jcrNode = mock(JcrNode.class);
        String nodeNameWithoutPrefix = "nodeName";
        String nodeName = "anotherPrefix" + ":" + nodeNameWithoutPrefix;
        when(jcrNode.getName()).thenReturn(nodeName);
        JcrNode jcrParentNode = mock(JcrNode.class, "parentNode");
        when(jcrNode.getParent()).thenReturn(jcrParentNode);

        jcrNamespace.getNodePath(jcrNode);

        verify(jcrParentNode).getChildNodePath(PREFIX + ":" + nodeNameWithoutPrefix);
    }

    @Test
    public void shouldReturnTrueIfNamespaceUriIsSameAsGivenUri() throws Exception {
        assertTrue(jcrNamespace.hasUri(URI));
    }

    @Test
    public void shouldReturnFalseIfNamespaceUriIsDifferentThanGivenUri() throws Exception {
        assertFalse(jcrNamespace.hasUri("anotherUri"));
    }

    @Test
    public void shouldGetPrefix() throws Exception {
        String prefix = jcrNamespace.getPrefix();

        assertThat(prefix, is(PREFIX));
    }

    @Test
    public void shouldGetUri() throws Exception {
        String uri = jcrNamespace.getUri();

        assertThat(uri, is(URI));
    }

    @Test
    public void shouldBeEqualsToAnotherNamespaceIfPrefixAndUriAreEquals() throws Exception {
        JcrNamespace anotherJcrNamespace = new JcrNamespace(PREFIX, URI);

        assertTrue(jcrNamespace.equals(anotherJcrNamespace));
    }

    @Test
    public void shouldNotBeEqualsToAnotherNamespaceIfPrefixAreNotEquals() throws Exception {
        JcrNamespace anotherJcrNamespace = new JcrNamespace("anotherPrefix", URI);

        assertFalse(jcrNamespace.equals(anotherJcrNamespace));
    }

    @Test
    public void shouldNotBeEqualsToAnotherNamespaceIfUriAreNotEquals() throws Exception {
        JcrNamespace anotherJcrNamespace = new JcrNamespace(PREFIX, "anotherUri");

        assertFalse(jcrNamespace.equals(anotherJcrNamespace));
    }

}