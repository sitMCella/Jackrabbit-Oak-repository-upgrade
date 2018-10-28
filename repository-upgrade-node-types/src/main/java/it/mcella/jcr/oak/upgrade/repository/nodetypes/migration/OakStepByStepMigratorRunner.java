package it.mcella.jcr.oak.upgrade.repository.nodetypes.migration;

import it.mcella.jcr.oak.upgrade.repository.AdministratorCredentials;
import it.mcella.jcr.oak.upgrade.repository.JcrNamespace;
import it.mcella.jcr.oak.upgrade.repository.JcrRepository;
import it.mcella.jcr.oak.upgrade.repository.JcrSession;

public class OakStepByStepMigratorRunner implements JcrMigratorRunner {

    private final JcrMigrator oakStepByStepMigrator;

    public OakStepByStepMigratorRunner(OakMigrationParameters oakMigrationParameters, OakMigrationRepository oakMigrationRepository, JcrNodeMigrator jcrNodeMigrator) {
        JcrNamespace jcrOldNamespace = oakMigrationParameters.getJcrOldNamespace();
        JcrNamespace jcrIntermediateNamespace = oakMigrationParameters.getJcrIntermediateNamespace();
        OakNamespaceSwitch oakNamespaceSwitchIntermediate = new OakNamespaceSwitch(jcrOldNamespace, jcrIntermediateNamespace);
        JcrNodeMigrator oakNodeIntermediateMigrator = new OakNodeIntermediateMigrator(oakNamespaceSwitchIntermediate);
        this.oakStepByStepMigrator = new OakStepByStepMigrator(oakMigrationRepository, oakMigrationParameters, oakNodeIntermediateMigrator, jcrNodeMigrator);
    }

    @Override
    public void run(JcrRepository jcrRepository) throws JcrMigrationException {
        try (JcrSession jcrSession = jcrRepository.login(AdministratorCredentials.create())) {
            oakStepByStepMigrator.upgrade(jcrSession);
        } catch (Exception e) {
            throw new JcrMigrationException(e);
        }
    }

}
