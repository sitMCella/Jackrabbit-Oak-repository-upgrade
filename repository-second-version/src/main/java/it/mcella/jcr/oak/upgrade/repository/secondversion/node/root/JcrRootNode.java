package it.mcella.jcr.oak.upgrade.repository.secondversion.node.root;

import javax.jcr.RepositoryException;

public interface JcrRootNode {

    public static final String NODE_TYPE = "rep:root";

    RootNode toNode() throws RepositoryException;

}
