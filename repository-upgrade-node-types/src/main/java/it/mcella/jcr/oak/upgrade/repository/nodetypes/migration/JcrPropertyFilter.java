package it.mcella.jcr.oak.upgrade.repository.nodetypes.migration;

import it.mcella.jcr.oak.upgrade.repository.JcrProperty;

import javax.jcr.RepositoryException;

public interface JcrPropertyFilter {

    boolean match(JcrProperty jcrProperty) throws RepositoryException;

}
