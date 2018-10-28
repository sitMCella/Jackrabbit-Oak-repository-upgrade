package it.mcella.jcr.oak.upgrade.repository.nodetypes.migration;

import it.mcella.jcr.oak.upgrade.repository.JcrNamespace;
import it.mcella.jcr.oak.upgrade.repository.JcrSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OakStepByStepMigrator implements JcrMigrator {

    private static final Logger LOGGER = LoggerFactory.getLogger(OakStepByStepMigrator.class.getName());

    private final OakMigrationRepository oakMigrationRepository;
    private final OakMigrationParameters oakMigrationParameters;
    private final JcrNodeMigrator jcrNodeIntermediateMigrator;
    private final JcrNodeMigrator jcrNodeMigrator;

    public OakStepByStepMigrator(OakMigrationRepository oakMigrationRepository, OakMigrationParameters oakMigrationParameters,
                                 JcrNodeMigrator jcrNodeIntermediateMigrator, JcrNodeMigrator jcrNodeMigrator) {
        this.oakMigrationRepository = oakMigrationRepository;
        this.oakMigrationParameters = oakMigrationParameters;
        this.jcrNodeIntermediateMigrator = jcrNodeIntermediateMigrator;
        this.jcrNodeMigrator = jcrNodeMigrator;
    }

    @Override
    public void upgrade(JcrSession jcrSession) throws JcrNodesMigratorException {
        try {
            migrateToIntermediateNamespace(jcrSession);
            jcrSession.save();
            migrateToNewNamespace(jcrSession);
            jcrSession.save();
        } catch (Exception e) {
            throw new JcrNodesMigratorException(String.format("Cannot migrate nodes from namespace %s to namespace %s", oakMigrationParameters.getJcrOldNamespace(), oakMigrationParameters.getJcrNewNamespace()), e);
        }
    }

    private void migrateToIntermediateNamespace(JcrSession jcrSession) throws JcrMigrationRepositoryException {
        JcrNamespace jcrIntermediateNamespace = oakMigrationParameters.getJcrIntermediateNamespace();
        JcrNamespace jcrOldNamespace = oakMigrationParameters.getJcrOldNamespace();
        LOGGER.info(String.format("Register namespace %s", jcrIntermediateNamespace));
        oakMigrationRepository.registerNamespace(jcrIntermediateNamespace, jcrSession);
        LOGGER.info(String.format("Register node types from namespace %s", jcrIntermediateNamespace));
        oakMigrationRepository.registerNodeTypesFrom(oakMigrationParameters.getJcrNamespaceIntermediateConfiguration(), jcrSession, jcrIntermediateNamespace);
        LOGGER.info(String.format("Migrate node types to namespace %s", jcrIntermediateNamespace));
        oakMigrationRepository.migrate(jcrNodeIntermediateMigrator, jcrOldNamespace, jcrIntermediateNamespace, jcrSession);
        LOGGER.info(String.format("Unregister node types from namespace %s", jcrOldNamespace));
        oakMigrationRepository.unregisterNodeTypesFrom(oakMigrationParameters.getJcrNamespaceOldConfiguration(), jcrSession);
    }

    private void migrateToNewNamespace(JcrSession jcrSession) throws JcrMigrationRepositoryException {
        JcrNamespace jcrNewNamespace = oakMigrationParameters.getJcrNewNamespace();
        JcrNamespace jcrIntermediateNamespace = oakMigrationParameters.getJcrIntermediateNamespace();
        LOGGER.info(String.format("Register node types from namespace %s", jcrNewNamespace));
        oakMigrationRepository.registerNodeTypesFrom(oakMigrationParameters.getJcrNamespaceNewConfiguration(), jcrSession, jcrNewNamespace);
        LOGGER.info(String.format("Migrate node types to namespace %s", jcrNewNamespace));
        oakMigrationRepository.migrate(jcrNodeMigrator, jcrIntermediateNamespace, jcrNewNamespace, jcrSession);
        LOGGER.info(String.format("Unregister node types from namespace %s", jcrIntermediateNamespace));
        oakMigrationRepository.unregisterNodeTypesFrom(oakMigrationParameters.getJcrNamespaceIntermediateConfiguration(), jcrSession);
    }

}
