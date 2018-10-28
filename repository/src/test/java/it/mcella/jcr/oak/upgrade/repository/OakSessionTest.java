package it.mcella.jcr.oak.upgrade.repository;

import org.junit.Before;
import org.junit.Test;

import javax.jcr.PropertyType;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.ValueFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OakSessionTest {

    private final Session session = mock(Session.class);

    private OakSession oakSession;

    @Before
    public void setUp() throws Exception {
        oakSession = new OakSession(session);
    }

    @Test
    public void shouldGetNodeById() throws Exception {
        JcrNodeId jcrNodeId = mock(JcrNodeId.class);
        String nodeId = "node id";
        when(jcrNodeId.getNodeId()).thenReturn(nodeId);

        JcrNode jcrNode = oakSession.getNodeById(jcrNodeId);

        verify(session).getNodeByIdentifier(nodeId);
        assertTrue(jcrNode instanceof OakNode);
    }

    @Test
    public void shouldGetNodesById() throws Exception {
        JcrNodeId jcrNodeId = mock(JcrNodeId.class);
        String nodeId = "node id";
        when(jcrNodeId.getNodeId()).thenReturn(nodeId);
        List<JcrNodeId> jcrNodeIds = new ArrayList<>();
        jcrNodeIds.add(jcrNodeId);

        List<JcrNode> jcrNodes = oakSession.getNodesById(jcrNodeIds);

        verify(session).getNodeByIdentifier(nodeId);
        assertFalse(jcrNodes.isEmpty());
        assertThat(jcrNodes.size(), is(1));
    }

    @Test
    public void shouldGetRootNode() throws Exception {
        JcrNode rootNode = oakSession.getRootNode();

        verify(session).getRootNode();
        assertTrue(rootNode instanceof OakNode);
    }

    @Test
    public void shouldSaveSession() throws Exception {
        oakSession.save();

        verify(session).save();
    }

    @Test
    public void shouldCreateBinary() throws Exception {
        ValueFactory valueFactory = mock(ValueFactory.class);
        when(session.getValueFactory()).thenReturn(valueFactory);
        InputStream inputStream = mock(InputStream.class);

        JcrBinary jcrBinary = oakSession.createBinary(inputStream);

        verify(valueFactory).createBinary(inputStream);
        assertTrue(jcrBinary instanceof OakBinary);
    }

    @Test
    public void shouldCloseSession() throws Exception {
        oakSession.close();

        verify(session).logout();
    }

    @Test
    public void shouldGetSession() throws Exception {
        assertThat(oakSession.getSession(), is(session));
    }

    @Test
    public void shouldCreateUriValue() throws Exception {
        ValueFactory valueFactory = mock(ValueFactory.class);
        when(session.getValueFactory()).thenReturn(valueFactory);
        Value value = mock(Value.class);
        when(valueFactory.createValue(any(String.class), any(Integer.class))).thenReturn(value);
        JcrNamespace jcrNamespace = mock(JcrNamespace.class);
        String namespaceUri = "namespaceUri";
        when(jcrNamespace.getUri()).thenReturn(namespaceUri);

        JcrValue jcrValue = oakSession.createUriValue(jcrNamespace);

        verify(valueFactory).createValue(namespaceUri, PropertyType.URI);
        assertTrue(jcrValue instanceof OakValue);
        assertThat(((OakValue) jcrValue).getValue(), is(value));
    }

    @Test
    public void shouldCreateNameValue() throws Exception {
        ValueFactory valueFactory = mock(ValueFactory.class);
        when(session.getValueFactory()).thenReturn(valueFactory);
        Value value = mock(Value.class);
        when(valueFactory.createValue(any(String.class), any(Integer.class))).thenReturn(value);
        String name = "valueName";

        JcrValue jcrValue = oakSession.createNameValue(name);

        verify(valueFactory).createValue(name, PropertyType.NAME);
        assertTrue(jcrValue instanceof OakValue);
        assertThat(((OakValue) jcrValue).getValue(), is(value));
    }

}