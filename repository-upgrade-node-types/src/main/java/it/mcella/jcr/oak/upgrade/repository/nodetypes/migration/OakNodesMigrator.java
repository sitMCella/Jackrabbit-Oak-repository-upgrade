package it.mcella.jcr.oak.upgrade.repository.nodetypes.migration;

import it.mcella.jcr.oak.upgrade.repository.JcrNamespace;
import it.mcella.jcr.oak.upgrade.repository.JcrNode;
import it.mcella.jcr.oak.upgrade.repository.JcrSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.RepositoryException;
import java.util.List;

public class OakNodesMigrator implements JcrNodesMigrator {

    private static final Logger LOGGER = LoggerFactory.getLogger(OakNodesMigrator.class.getName());

    private final OakNodesToMigrate oakNodesToMigrate;
    private final OakNodeMigrationChecker oakNodeMigrationChecker;

    public OakNodesMigrator() {
        this.oakNodesToMigrate = new OakNodesToMigrate();
        this.oakNodeMigrationChecker = new OakNodeMigrationChecker();
    }

    @Override
    public void migrate(JcrNodeMigrator jcrNodeMigrator, JcrNamespace oldNamespace,
                        JcrNamespace newNamespace, JcrSession jcrSession) throws JcrNodesMigratorException {
        try {
            LOGGER.info(String.format("Migrate nodes from namespace %s to namespace %s", oldNamespace, newNamespace));
            List<JcrNode> jcrNodesToMigrate = oakNodesToMigrate.retrieve(jcrSession);
            OakNamespaceSwitch oakNamespaceSwitch = new OakNamespaceSwitch(oldNamespace, newNamespace);
            for (JcrNode jcrNode : jcrNodesToMigrate) {
                migrate(jcrNode, jcrNodeMigrator, oldNamespace, oakNamespaceSwitch);
            }
            LOGGER.info("Migration successful");
        } catch (Exception e) {
            throw new JcrNodesMigratorException(String.format("Cannot migrate nodes from namespace %s to namespace %s", oldNamespace, newNamespace), e);
        }
    }

    private void migrate(JcrNode jcrNode, JcrNodeMigrator jcrNodeMigrator, JcrNamespace oldNamespace, OakNamespaceSwitch oakNamespaceSwitch) throws Exception {
        try {
            checkout(jcrNode);
            jcrNodeMigrator.migrate(jcrNode);
            oakNamespaceSwitch.update(jcrNode);
            jcrNodeMigrator.migrateHistory(jcrNode);
            oakNodeMigrationChecker.check(jcrNode, oldNamespace);
            jcrNode.save();
        } catch (RepositoryException e) {
            throw new Exception(String.format("Cannot migrate node %s", jcrNode), e);
        }
    }

    private void checkout(JcrNode jcrNode) throws RepositoryException {
        if (!jcrNode.isCheckedOut()) {
            if (jcrNode.isVersionable()) {
                jcrNode.checkout();
            }
            checkout(jcrNode.getParent());
        }
    }

}
