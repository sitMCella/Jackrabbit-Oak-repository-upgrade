package it.mcella.jcr.oak.upgrade.repository;

import javax.jcr.Credentials;
import javax.jcr.RepositoryException;

public interface JcrRepository {

    JcrSession login(Credentials credentials) throws RepositoryException;

}
