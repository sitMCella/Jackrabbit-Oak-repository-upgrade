package it.mcella.jcr.oak.upgrade.repository;

import org.junit.Before;
import org.junit.Test;

import javax.jcr.Property;
import javax.jcr.Value;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OakPropertyTest {

    private static final String PREFIX = "prefix";

    private final Property property = mock(Property.class);
    private final Value value = mock(Value.class);
    private final JcrNamespace namespace = mock(JcrNamespace.class, "namespace");
    private final JcrNamespace newNamespace = mock(JcrNamespace.class, "newNamespace");
    private final JcrValueFactory jcrValueFactory = mock(JcrValueFactory.class);

    private OakProperty oakProperty;

    @Before
    public void setUp() throws Exception {
        oakProperty = new OakProperty(property);
    }

    @Test
    public void shouldReturnTrueIfPropertyValuesContainValue() throws Exception {
        Value[] values = new Value[]{value};
        when(property.getValues()).thenReturn(values);
        String searchValue = "search value";
        when(value.getString()).thenReturn(searchValue);

        assertTrue(oakProperty.contains(searchValue));
    }

    @Test
    public void shouldReturnFalseIfPropertyValuesNotContainValue() throws Exception {
        Value[] values = new Value[]{value};
        when(property.getValues()).thenReturn(values);
        String searchValue = "search value";
        when(value.getString()).thenReturn("another value");

        assertFalse(oakProperty.contains(searchValue));
    }

    @Test
    public void shouldGetStringPropertyValue() throws Exception {
        when(property.getValue()).thenReturn(value);
        String propertyValue = "property value";
        when(value.getString()).thenReturn(propertyValue);

        assertThat(oakProperty.getStringValue(), is(propertyValue));
    }

    @Test
    public void shouldGetBooleanPropertyValue() throws Exception {
        when(property.getValue()).thenReturn(value);
        boolean propertyValue = true;
        when(value.getBoolean()).thenReturn(propertyValue);

        assertThat(oakProperty.getBooleanValue(), is(propertyValue));
    }

    @Test
    public void shouldCheckIfPropertyWithMultipleValues() throws Exception {
        oakProperty.isMultiple();
        verify(property).isMultiple();
    }

    @Test
    public void shouldReturnPropertyValue() throws Exception {
        oakProperty.getValue();
        verify(property).getValue();
    }

    @Test
    public void shouldReturnPropertyValues() throws Exception {
        oakProperty.getValues();
        verify(property).getValues();
    }

    @Test
    public void shouldReturnPropertyName() throws Exception {
        String propertyName = PREFIX + "propertyName";
        when(property.getName()).thenReturn(propertyName);

        assertThat(oakProperty.getName(), is(propertyName));
    }

    @Test
    public void shouldCheckIfOakPropertyBelongsToGivenNamespace() throws Exception {
        JcrNamespace jcrNamespace = mock(JcrNamespace.class);

        oakProperty.belongsTo(jcrNamespace);

        verify(jcrNamespace).isAppliedTo(oakProperty);
    }

    @Test
    public void shouldRemoveProperty() throws Exception {
        oakProperty.remove();
        verify(property).remove();
    }

    @Test
    public void shouldRenamePropertyValueName() throws Exception {
        when(property.isMultiple()).thenReturn(false);
        when(property.getValue()).thenReturn(value);
        when(namespace.isAppliedTo(value)).thenReturn(true);
        String valueName = "valueName";
        when(newNamespace.getValueNameFrom(value)).thenReturn(valueName);
        Value newValue = mock(Value.class, "newValue");
        when(jcrValueFactory.createFrom(valueName, oakProperty)).thenReturn(newValue);

        oakProperty.renameValueNames(namespace, newNamespace, jcrValueFactory);

        verify(property).setValue(newValue);
    }

    @Test
    public void shouldNotRenamePropertyValueNameIfNamespaceIsNotAppliedToPropertyValueName() throws Exception {
        when(property.isMultiple()).thenReturn(false);
        when(property.getValue()).thenReturn(value);
        when(namespace.isAppliedTo(value)).thenReturn(false);

        oakProperty.renameValueNames(namespace, newNamespace, jcrValueFactory);

        verify(property, never()).setValue(any(Value.class));
    }

    @Test
    public void shouldRenamePropertyValueNames() throws Exception {
        when(property.isMultiple()).thenReturn(true);
        Value anotherValue = mock(Value.class, "anotherValue");
        Value[] values = new Value[]{value, anotherValue};
        when(property.getValues()).thenReturn(values);
        when(namespace.isAppliedTo(value)).thenReturn(true);
        when(namespace.isAppliedTo(anotherValue)).thenReturn(false);
        String valueName = "valueName";
        when(newNamespace.getValueNameFrom(value)).thenReturn(valueName);
        Value newValue = mock(Value.class, "newValue");
        when(jcrValueFactory.createFrom(valueName, oakProperty)).thenReturn(newValue);

        oakProperty.renameValueNames(namespace, newNamespace, jcrValueFactory);

        verify(property).setValue(new Value[]{newValue, anotherValue});
    }

    @Test
    public void shouldNotRenamePropertyValueNamesIfNamespaceIsNotAppliedToAnyPropertyValueName() throws Exception {
        when(property.isMultiple()).thenReturn(true);
        Value anotherValue = mock(Value.class, "anotherValue");
        Value[] values = new Value[]{value, anotherValue};
        when(property.getValues()).thenReturn(values);
        when(namespace.isAppliedTo(value)).thenReturn(false);
        when(namespace.isAppliedTo(anotherValue)).thenReturn(false);

        oakProperty.renameValueNames(namespace, newNamespace, jcrValueFactory);

        verify(property, never()).setValue(any(Value[].class));
    }

    @Test
    public void shouldGetJcrPropertyType() throws Exception {
        int type = 43;
        when(property.getType()).thenReturn(type);

        JcrPropertyType jcrPropertyType = oakProperty.getPropertyType();

        verify(property).getType();
        assertTrue(jcrPropertyType instanceof OakPropertyType);
        assertThat(jcrPropertyType.getType(), is(type));
    }

    @Test
    public void shouldReturnProperty() throws Exception {
        assertThat(oakProperty.getProperty(), is(property));
    }

}