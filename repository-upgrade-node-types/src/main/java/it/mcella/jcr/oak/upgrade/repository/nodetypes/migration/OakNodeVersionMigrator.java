package it.mcella.jcr.oak.upgrade.repository.nodetypes.migration;

import it.mcella.jcr.oak.upgrade.repository.JcrNamespace;
import it.mcella.jcr.oak.upgrade.repository.JcrNode;
import it.mcella.jcr.oak.upgrade.repository.JcrNodeVersion;

import javax.jcr.RepositoryException;

public class OakNodeVersionMigrator implements JcrNodeVersionMigrator {

    private final JcrNodeVersionMigratorRunner jcrNodeVersionMigratorRunner;
    private final JcrNamespace jcrNamespace;
    private final JcrPropertyFilter jcrPropertyFilter;

    public OakNodeVersionMigrator(JcrNodeVersionMigratorRunner jcrNodeVersionMigratorRunner,
                                  JcrNamespace jcrNamespace,
                                  JcrPropertyFilter jcrPropertyFilter) {
        this.jcrNodeVersionMigratorRunner = jcrNodeVersionMigratorRunner;
        this.jcrNamespace = jcrNamespace;
        this.jcrPropertyFilter = jcrPropertyFilter;
    }

    @Override
    public void migrate(JcrNode jcrNode) throws RepositoryException {
        OakNodeMigration oakNodeMigration = new OakNodeMigration(jcrNode, jcrNamespace, jcrPropertyFilter);
        for (JcrNodeVersion jcrNodeVersion : jcrNode.getVersions()) {
            jcrNode.checkout();
            oakNodeMigration.addNodePropertiesFrom(jcrNodeVersion);
            jcrNodeVersionMigratorRunner.run(jcrNode);
            jcrNode.save();
            JcrNodeVersion jcrNodeNewVersion = jcrNode.checkin();
            jcrNodeNewVersion.addVersionLabelsFrom(jcrNodeVersion);
            jcrNode.removeVersion(jcrNodeVersion);
        }
        jcrNode.checkout();
    }

}
