package it.mcella.jcr.oak.upgrade.repository;

import javax.jcr.Repository;

public interface JcrCustomNodeTypeDefinition {

    void load(Repository repository) throws RepositoryInitializationException;

}
