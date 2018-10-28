package it.mcella.jcr.oak.upgrade.repository.nodetypes.migration;

import it.mcella.jcr.oak.upgrade.repository.nodetypes.jackrabbit.OakNodeTypeDefinitionsReader;
import it.mcella.jcr.oak.upgrade.repository.nodetypes.jackrabbit.OakNodeTypesRegister;
import it.mcella.jcr.oak.upgrade.repository.nodetypes.jackrabbit.OakNodeTypesUnregister;

public class OakMigrationRepositoryFactory {

    public OakMigrationRepository build() {
        OakNodesMigrator oakNodesMigrator = new OakNodesMigrator();
        OakNamespaceRegister oakNamespaceRegister = new OakNamespaceRegister();
        JcrNodeTypeDefinitionsReader jcrNodeTypeDefinitionsReader = new OakNodeTypeDefinitionsReader();
        JcrNodeTypesRegister jcrNodeTypesRegister = new OakNodeTypesRegister(jcrNodeTypeDefinitionsReader);
        JcrNodeTypesUnregister jcrNodeTypesUnregister = new OakNodeTypesUnregister(jcrNodeTypeDefinitionsReader);
        return new OakMigrationRepository(oakNodesMigrator, oakNamespaceRegister, jcrNodeTypesRegister, jcrNodeTypesUnregister);
    }

}
