package it.mcella.jcr.oak.upgrade.repository.nodetypes.migration;

import org.apache.jackrabbit.spi.QNodeTypeDefinition;

public class OakNodeTypeDefinition {

    private final QNodeTypeDefinition qNodeTypeDefinition;

    public OakNodeTypeDefinition(QNodeTypeDefinition qNodeTypeDefinition) {
        this.qNodeTypeDefinition = qNodeTypeDefinition;
    }

    public QNodeTypeDefinition getQNodeTypeDefinition() {
        return qNodeTypeDefinition;
    }

    public String getNamespaceURI() {
        return qNodeTypeDefinition.getName().getNamespaceURI();
    }

}
