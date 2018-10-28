package it.mcella.jcr.oak.upgrade.repository.firstversion.node.root;

import javax.jcr.RepositoryException;

public interface JcrRootNode {

    public static final String NODE_TYPE = "rep:root";

    RootNode toNode() throws RepositoryException;

}
