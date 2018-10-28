package it.mcella.jcr.oak.upgrade.repository.secondversion.update;

import it.mcella.jcr.oak.upgrade.repository.JcrProperty;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JcrNamePropertyFilterTest {

    private static final String PROPERTY_NAME = "propertyName";

    private final JcrProperty jcrProperty = mock(JcrProperty.class);

    private JcrNamePropertyFilter jcrNamePropertyFilter;

    @Before
    public void setUp() throws Exception {
        jcrNamePropertyFilter = new JcrNamePropertyFilter(PROPERTY_NAME);
    }

    @Test
    public void shouldMatchJcrPropertyIfNameIsPropertyName() throws Exception {
        when(jcrProperty.getName()).thenReturn(PROPERTY_NAME);

        boolean match = jcrNamePropertyFilter.match(jcrProperty);

        assertTrue(match);
    }

    @Test
    public void shouldNotMatchJcrPropertyIfNameIsAnotherPropertyName() throws Exception {
        String anotherPropertyName = "another property name";
        when(jcrProperty.getName()).thenReturn(anotherPropertyName);

        boolean match = jcrNamePropertyFilter.match(jcrProperty);

        assertFalse(match);
    }

}