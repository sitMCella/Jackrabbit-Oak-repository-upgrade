package it.mcella.jcr.oak.upgrade.repository.nodetypes.migration;

import it.mcella.jcr.oak.upgrade.repository.JcrNamespace;
import it.mcella.jcr.oak.upgrade.repository.JcrNamespaceConfiguration;
import it.mcella.jcr.oak.upgrade.repository.JcrSession;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OakMigrationRepositoryTest {

    private static final String NAMESPACE_CONFIGURATION_FILE_NAME = "cnd.config";

    private final OakNodesMigrator oakNodesMigrator = mock(OakNodesMigrator.class);
    private final OakNamespaceRegister oakNamespaceRegister = mock(OakNamespaceRegister.class);
    private final JcrNodeTypesRegister jcrNodeTypesRegister = mock(JcrNodeTypesRegister.class);
    private final JcrNodeTypesUnregister jcrNodeTypesUnregister = mock(JcrNodeTypesUnregister.class);
    private final JcrNamespace jcrNamespace = mock(JcrNamespace.class);
    private final JcrSession jcrSession = mock(JcrSession.class);

    private OakMigrationRepository oakMigrationRepository;

    @Before
    public void setUp() throws Exception {
        oakMigrationRepository = new OakMigrationRepository(oakNodesMigrator, oakNamespaceRegister, jcrNodeTypesRegister, jcrNodeTypesUnregister);
    }

    @Test
    public void shouldRegisterOakNamespace() throws Exception {
        oakMigrationRepository.registerNamespace(jcrNamespace, jcrSession);

        verify(oakNamespaceRegister).register(jcrNamespace, jcrSession);
    }

    @Test
    public void shouldRetrieveNamespaceUriFromCndConfigurationFile() throws Exception {
        when(jcrNamespace.hasUri(any(String.class))).thenReturn(true);
        JcrNamespaceConfiguration jcrNamespaceConfiguration = new JcrNamespaceConfiguration(NAMESPACE_CONFIGURATION_FILE_NAME);

        oakMigrationRepository.registerNodeTypesFrom(jcrNamespaceConfiguration, jcrSession, jcrNamespace);

        verify(jcrNodeTypesRegister).retrieveNamespaceUriFrom(any(InputStream.class));
    }

    @Test
    public void shouldRegisterNodeTypesFromCndConfigurationFile() throws Exception {
        String namespaceUri = "namespaceUri";
        when(jcrNodeTypesRegister.retrieveNamespaceUriFrom(any(InputStream.class))).thenReturn(namespaceUri);
        when(jcrNamespace.hasUri(namespaceUri)).thenReturn(true);
        JcrNamespaceConfiguration jcrNamespaceConfiguration = new JcrNamespaceConfiguration(NAMESPACE_CONFIGURATION_FILE_NAME);

        oakMigrationRepository.registerNodeTypesFrom(jcrNamespaceConfiguration, jcrSession, jcrNamespace);

        verify(jcrNodeTypesRegister).registerNodeTypes(any(InputStream.class), eq(jcrSession));
    }

    @Test(expected = JcrMigrationRepositoryException.class)
    public void shouldThrowJcrMigrationRepositoryExceptionIfRetrievedNamespaceUriIsNotEqualToJcnNamespaceUri() throws Exception {
        String namespaceUri = "namespaceUri";
        when(jcrNodeTypesRegister.retrieveNamespaceUriFrom(any(InputStream.class))).thenReturn(namespaceUri);
        when(jcrNamespace.hasUri(namespaceUri)).thenReturn(false);
        JcrNamespaceConfiguration jcrNamespaceConfiguration = new JcrNamespaceConfiguration(NAMESPACE_CONFIGURATION_FILE_NAME);

        oakMigrationRepository.registerNodeTypesFrom(jcrNamespaceConfiguration, jcrSession, jcrNamespace);
    }

    @Test
    public void shouldMigrateOakNodes() throws Exception {
        JcrNodeMigrator jcrNodeMigrator = mock(JcrNodeMigrator.class);
        JcrNamespace jcrOldNamespace = mock(JcrNamespace.class, "oldNamespace");
        JcrNamespace jcrNewNamespace = mock(JcrNamespace.class, "newNamespace");

        oakMigrationRepository.migrate(jcrNodeMigrator, jcrOldNamespace, jcrNewNamespace, jcrSession);

        verify(oakNodesMigrator).migrate(jcrNodeMigrator, jcrOldNamespace, jcrNewNamespace, jcrSession);
    }

    @Test
    public void shouldUnregisterNodeTypesFromCndConfigurationFile() throws Exception {
        JcrNamespaceConfiguration jcrNamespaceConfiguration = new JcrNamespaceConfiguration(NAMESPACE_CONFIGURATION_FILE_NAME);

        oakMigrationRepository.unregisterNodeTypesFrom(jcrNamespaceConfiguration, jcrSession);

        verify(jcrNodeTypesUnregister).unregister(any(InputStream.class), eq(jcrSession));
    }

}