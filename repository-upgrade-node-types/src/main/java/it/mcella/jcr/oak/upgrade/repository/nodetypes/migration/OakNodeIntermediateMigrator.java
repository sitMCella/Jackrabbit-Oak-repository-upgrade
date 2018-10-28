package it.mcella.jcr.oak.upgrade.repository.nodetypes.migration;

import it.mcella.jcr.oak.upgrade.repository.JcrNode;

public class OakNodeIntermediateMigrator implements JcrNodeMigrator {

    private final OakNamespaceSwitch oakNamespaceSwitch;

    public OakNodeIntermediateMigrator(OakNamespaceSwitch oakNamespaceSwitch) {
        this.oakNamespaceSwitch = oakNamespaceSwitch;
    }

    @Override
    public void migrate(JcrNode jcrNode) throws JcrNodeMigratorException {
        try {
            oakNamespaceSwitch.update(jcrNode);
        } catch (JcrNamespaceSwitchException e) {
            throw new JcrNodeMigratorException(e);
        }
    }

    @Override
    public void migrateHistory(JcrNode jcrNode) throws JcrNodeMigratorException {
        // do nothing
    }

}
