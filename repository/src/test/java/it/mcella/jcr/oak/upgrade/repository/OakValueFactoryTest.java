package it.mcella.jcr.oak.upgrade.repository;

import org.junit.Before;
import org.junit.Test;

import javax.jcr.ValueFactory;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OakValueFactoryTest {

    private final ValueFactory valueFactory = mock(ValueFactory.class);

    private OakValueFactory oakValueFactory;

    @Before
    public void setUp() throws Exception {
        oakValueFactory = new OakValueFactory(valueFactory);
    }

    @Test
    public void shouldCreateValueFromValueNameAndProperty() throws Exception {
        JcrProperty jcrProperty = mock(JcrProperty.class);
        JcrPropertyType jcrPropertyType = mock(JcrPropertyType.class);
        when(jcrProperty.getPropertyType()).thenReturn(jcrPropertyType);
        int type = 5;
        when(jcrPropertyType.getType()).thenReturn(type);
        String valueName = "valueName";

        oakValueFactory.createFrom(valueName, jcrProperty);

        verify(valueFactory).createValue(valueName, type);
    }

    @Test
    public void shouldGetValueFactory() throws Exception {
        assertThat(oakValueFactory.getValueFactory(), is(valueFactory));
    }

}