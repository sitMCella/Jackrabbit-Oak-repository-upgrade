package it.mcella.jcr.oak.upgrade.repository.nodetypes.jackrabbit;

import it.mcella.jcr.oak.upgrade.repository.nodetypes.migration.OakNodeTypeDefinition;
import org.apache.jackrabbit.spi.QNodeTypeDefinition;

import java.util.List;
import java.util.stream.Collectors;

public class OakNodeTypeDefinitionFactory {

    public static List<OakNodeTypeDefinition> from(List<QNodeTypeDefinition> qNodeTypeDefinitions) {
        return qNodeTypeDefinitions.stream()
                .map(nodeTypeDefinition -> new OakNodeTypeDefinition(nodeTypeDefinition))
                .collect(Collectors.toList());
    }

}
