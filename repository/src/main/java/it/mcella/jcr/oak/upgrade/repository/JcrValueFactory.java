package it.mcella.jcr.oak.upgrade.repository;

import javax.jcr.RepositoryException;
import javax.jcr.Value;

public interface JcrValueFactory {

    Value createFrom(String valueName, JcrProperty jcrProperty) throws RepositoryException;

}
