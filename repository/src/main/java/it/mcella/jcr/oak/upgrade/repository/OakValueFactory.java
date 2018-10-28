package it.mcella.jcr.oak.upgrade.repository;

import javax.jcr.RepositoryException;
import javax.jcr.Value;
import javax.jcr.ValueFactory;

public class OakValueFactory implements JcrValueFactory {

    private final ValueFactory valueFactory;

    public OakValueFactory(ValueFactory valueFactory) {
        this.valueFactory = valueFactory;
    }

    @Override
    public Value createFrom(String valueName, JcrProperty jcrProperty) throws RepositoryException {
        JcrPropertyType jcrPropertyType = jcrProperty.getPropertyType();
        return valueFactory.createValue(valueName, jcrPropertyType.getType());
    }

    public ValueFactory getValueFactory() {
        return valueFactory;
    }

}
