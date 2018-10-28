package it.mcella.jcr.oak.upgrade.repository.nodetypes.migration;

import it.mcella.jcr.oak.upgrade.repository.nodetypes.jackrabbit.JcrNodeTypeDefinitionsException;

import java.io.InputStream;
import java.util.List;

public interface JcrNodeTypeDefinitionsReader {

    List<OakNodeTypeDefinition> readFrom(InputStream inputStream) throws JcrNodeTypeDefinitionsException;

}
