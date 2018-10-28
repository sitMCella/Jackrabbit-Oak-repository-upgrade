package it.mcella.jcr.oak.upgrade.repository.secondversion.update;

import it.mcella.jcr.oak.upgrade.repository.JcrProperty;
import it.mcella.jcr.oak.upgrade.repository.nodetypes.migration.JcrPropertyFilter;

import javax.jcr.RepositoryException;

public class JcrDefaultPropertyFilter implements JcrPropertyFilter {

    @Override
    public boolean match(JcrProperty jcrProperty) throws RepositoryException {
        return false;
    }

}
