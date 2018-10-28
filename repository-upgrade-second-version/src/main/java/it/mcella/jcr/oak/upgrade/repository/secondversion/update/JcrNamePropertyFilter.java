package it.mcella.jcr.oak.upgrade.repository.secondversion.update;

import it.mcella.jcr.oak.upgrade.repository.JcrProperty;
import it.mcella.jcr.oak.upgrade.repository.nodetypes.migration.JcrPropertyFilter;

import javax.jcr.RepositoryException;

public class JcrNamePropertyFilter implements JcrPropertyFilter {

    private final String jcrPropertyName;

    public JcrNamePropertyFilter(String jcrPropertyName) {
        this.jcrPropertyName = jcrPropertyName;
    }

    @Override
    public boolean match(JcrProperty jcrProperty) throws RepositoryException {
        return jcrPropertyName.equals(jcrProperty.getName());
    }

}
