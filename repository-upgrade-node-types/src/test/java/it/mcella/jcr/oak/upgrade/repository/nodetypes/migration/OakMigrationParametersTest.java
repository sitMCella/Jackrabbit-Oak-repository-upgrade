package it.mcella.jcr.oak.upgrade.repository.nodetypes.migration;

import it.mcella.jcr.oak.upgrade.repository.JcrNamespace;
import it.mcella.jcr.oak.upgrade.repository.JcrNamespaceConfiguration;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class OakMigrationParametersTest {

    private final JcrNamespace jcrOldNamespace = mock(JcrNamespace.class);
    private final JcrNamespaceConfiguration jcrNamespaceOldConfiguration = mock(JcrNamespaceConfiguration.class);
    private final JcrNamespace jcrIntermediateNamespace = mock(JcrNamespace.class);
    private final JcrNamespaceConfiguration jcrNamespaceIntermediateConfiguration = mock(JcrNamespaceConfiguration.class);
    private final JcrNamespace jcrNewNamespace = mock(JcrNamespace.class);
    private final JcrNamespaceConfiguration jcrNamespaceNewConfiguration = mock(JcrNamespaceConfiguration.class);

    private OakMigrationParameters oakMigrationParameters;

    @Before
    public void setUp() throws Exception {
        oakMigrationParameters = new OakMigrationParameters(
                jcrOldNamespace,
                jcrNamespaceOldConfiguration,
                jcrIntermediateNamespace,
                jcrNamespaceIntermediateConfiguration,
                jcrNewNamespace,
                jcrNamespaceNewConfiguration);
    }

    @Test
    public void shouldGetJcrOldNamespace() throws Exception {
        assertThat(oakMigrationParameters.getJcrOldNamespace(), is(jcrOldNamespace));
    }

    @Test
    public void shouldGetJcrNamespaceOldConfiguration() throws Exception {
        assertThat(oakMigrationParameters.getJcrNamespaceOldConfiguration(), is(jcrNamespaceOldConfiguration));
    }

    @Test
    public void shouldGetJcrIntermediateNamespace() throws Exception {
        assertThat(oakMigrationParameters.getJcrIntermediateNamespace(), is(jcrIntermediateNamespace));
    }

    @Test
    public void shouldGetJcrNamespaceIntermediateConfiguration() throws Exception {
        assertThat(oakMigrationParameters.getJcrNamespaceIntermediateConfiguration(), is(jcrNamespaceIntermediateConfiguration));
    }

    @Test
    public void shouldGetJcrNewNamespace() throws Exception {
        assertThat(oakMigrationParameters.getJcrNewNamespace(), is(jcrNewNamespace));
    }

    @Test
    public void shouldGetJcrNamespaceNewConfiguration() throws Exception {
        assertThat(oakMigrationParameters.getJcrNamespaceNewConfiguration(), is(jcrNamespaceNewConfiguration));
    }

}