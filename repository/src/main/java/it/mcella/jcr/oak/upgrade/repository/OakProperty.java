package it.mcella.jcr.oak.upgrade.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Value;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OakProperty implements JcrProperty {

    private final Logger LOGGER = LoggerFactory.getLogger(OakProperty.class);

    private final Property property;

    public OakProperty(Property property) {
        this.property = property;
    }

    @Override
    public boolean contains(String searchValue) throws RepositoryException {
        return Arrays.stream(property.getValues())
                .filter((propertyValue) -> isValue(propertyValue, searchValue))
                .anyMatch(s -> true);
    }

    @Override
    public String getStringValue() throws RepositoryException {
        return property.getValue().getString();
    }

    @Override
    public boolean getBooleanValue() throws RepositoryException {
        return property.getValue().getBoolean();
    }

    @Override
    public Value getValue() throws RepositoryException {
        return property.getValue();
    }

    @Override
    public boolean isMultiple() throws RepositoryException {
        return property.isMultiple();
    }

    @Override
    public Value[] getValues() throws RepositoryException {
        return property.getValues();
    }

    @Override
    public String getName() throws RepositoryException {
        return property.getName();
    }

    @Override
    public boolean belongsTo(JcrNamespace jcrNamespace) throws RepositoryException {
        return jcrNamespace.isAppliedTo(this);
    }

    @Override
    public void remove() throws RepositoryException {
        property.remove();
    }

    @Override
    public void renameValueNames(JcrNamespace namespace, JcrNamespace newNamespace, JcrValueFactory jcrValueFactory) throws RepositoryException {
        if (isMultiple()) {
            renameMultipleValueNames(namespace, newNamespace, jcrValueFactory);
        } else {
            Value value = getValue();
            if (namespace.isAppliedTo(value)) {
                String valueName = newNamespace.getValueNameFrom(value);
                property.setValue(jcrValueFactory.createFrom(valueName, this));
            }
        }
    }

    @Override
    public JcrPropertyType getPropertyType() throws RepositoryException {
        return new OakPropertyType(property.getType());
    }

    @Override
    public String toString() {
        return "OakProperty{" +
                "name=" + getNameQuietly() +
                '}';
    }

    Property getProperty() {
        return property;
    }

    private boolean isValue(Value propertyValue, String searchValue) {
        try {
            return searchValue.equals(propertyValue.getString());
        } catch (RepositoryException e) {
            LOGGER.warn("Cannot check JCR node properties", e);
            return false;
        }
    }

    private void renameMultipleValueNames(JcrNamespace namespace, JcrNamespace newNamespace, JcrValueFactory jcrValueFactory) throws RepositoryException {
        boolean changed = false;
        List<Value> values = new ArrayList<>();
        for (Value value : getValues()) {
            if (namespace.isAppliedTo(value)) {
                String valueName = newNamespace.getValueNameFrom(value);
                values.add(jcrValueFactory.createFrom(valueName, this));
                changed = true;
            } else {
                values.add(value);
            }
        }
        if (changed) {
            property.setValue(values.toArray(new Value[values.size()]));
        }
    }

    private String getNameQuietly() {
        try {
            return getName();
        } catch (RepositoryException e) {
            LOGGER.warn("Cannot retrieve property name", e);
        }
        return "";
    }

}
