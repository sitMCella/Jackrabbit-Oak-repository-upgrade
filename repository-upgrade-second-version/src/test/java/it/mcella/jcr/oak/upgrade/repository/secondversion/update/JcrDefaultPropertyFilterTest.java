package it.mcella.jcr.oak.upgrade.repository.secondversion.update;

import it.mcella.jcr.oak.upgrade.repository.JcrProperty;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;

public class JcrDefaultPropertyFilterTest {

    private JcrDefaultPropertyFilter jcrDefaultPropertyFilter;

    @Before
    public void setUp() throws Exception {
        jcrDefaultPropertyFilter = new JcrDefaultPropertyFilter();
    }

    @Test
    public void shouldNotMatchAnyJcrProperty() throws Exception {
        JcrProperty jcrProperty = mock(JcrProperty.class);

        boolean match = jcrDefaultPropertyFilter.match(jcrProperty);

        assertFalse(match);
    }

}