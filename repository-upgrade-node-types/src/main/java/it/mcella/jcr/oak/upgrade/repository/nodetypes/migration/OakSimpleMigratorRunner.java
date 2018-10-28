package it.mcella.jcr.oak.upgrade.repository.nodetypes.migration;

import it.mcella.jcr.oak.upgrade.repository.AdministratorCredentials;
import it.mcella.jcr.oak.upgrade.repository.JcrRepository;
import it.mcella.jcr.oak.upgrade.repository.JcrSession;

public class OakSimpleMigratorRunner implements JcrMigratorRunner {

    private final JcrMigrator jcrMigrator;

    public OakSimpleMigratorRunner(JcrMigrator jcrMigrator) {
        this.jcrMigrator = jcrMigrator;
    }

    @Override
    public void run(JcrRepository jcrRepository) throws JcrMigrationException {
        try (JcrSession jcrSession = jcrRepository.login(AdministratorCredentials.create())) {
            jcrMigrator.upgrade(jcrSession);
        } catch (Exception e) {
            throw new JcrMigrationException(e);
        }
    }

}
