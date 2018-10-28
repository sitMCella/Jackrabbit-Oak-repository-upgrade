package it.mcella.jcr.oak.upgrade.repository.nodetypes.migration;

import it.mcella.jcr.oak.upgrade.repository.JcrNamespace;
import it.mcella.jcr.oak.upgrade.repository.JcrSession;
import org.junit.Test;
import org.mockito.Mockito;

import javax.jcr.NamespaceRegistry;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OakNamespaceRegisterTest {

    @Test
    public void shouldRegisterJcrNamespace() throws Exception {
        OakNamespaceRegister oakNamespaceRegister = new OakNamespaceRegister();
        JcrNamespace jcrNamespace = mock(JcrNamespace.class);
        JcrSession jcrSession = mock(JcrSession.class, Mockito.RETURNS_DEEP_STUBS);
        NamespaceRegistry namespaceRegistry = mock(NamespaceRegistry.class);
        when(jcrSession.getSession().getWorkspace().getNamespaceRegistry()).thenReturn(namespaceRegistry);
        String namespacePrefix = "namespacePrefix";
        when(jcrNamespace.getPrefix()).thenReturn(namespacePrefix);
        String jcrNamespaceUri = "jcrNamespaceUri";
        when(jcrNamespace.getUri()).thenReturn(jcrNamespaceUri);

        oakNamespaceRegister.register(jcrNamespace, jcrSession);

        verify(namespaceRegistry).registerNamespace(namespacePrefix, jcrNamespaceUri);
    }

}