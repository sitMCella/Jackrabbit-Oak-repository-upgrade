package it.mcella.jcr.oak.upgrade.repository.nodetypes.jackrabbit;

import it.mcella.jcr.oak.upgrade.repository.nodetypes.migration.JcrNodeTypeDefinitionsReader;
import it.mcella.jcr.oak.upgrade.repository.nodetypes.migration.OakNodeTypeDefinition;
import org.apache.jackrabbit.commons.cnd.CompactNodeTypeDefReader;
import org.apache.jackrabbit.commons.cnd.CompactNodeTypeDefWriter.NamespaceMapping;
import org.apache.jackrabbit.spi.QNodeTypeDefinition;
import org.apache.jackrabbit.spi.commons.nodetype.QDefinitionBuilderFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class OakNodeTypeDefinitionsReader implements JcrNodeTypeDefinitionsReader {

    private static final String SYSTEM_ID = "<test>";
    private static final NamespaceMapping NAMESPACE_MAPPING = null;

    @Override
    public List<OakNodeTypeDefinition> readFrom(InputStream inputStream) throws JcrNodeTypeDefinitionsException {
        try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream)) {
            QDefinitionBuilderFactory qDefinitionBuilderFactory = new QDefinitionBuilderFactory();
            @SuppressWarnings({"rawtypes", "unchecked"})
            CompactNodeTypeDefReader<QNodeTypeDefinition, NamespaceMapping> cndReader =
                    new CompactNodeTypeDefReader(inputStreamReader, SYSTEM_ID, NAMESPACE_MAPPING, qDefinitionBuilderFactory);
            List<QNodeTypeDefinition> qNodeTypeDefinitions = cndReader.getNodeTypeDefinitions();
            return OakNodeTypeDefinitionFactory.from(qNodeTypeDefinitions);
        } catch (Exception e) {
            throw new JcrNodeTypeDefinitionsException("Cannot retrieve JCR node type definitions", e);
        }
    }

}
