package it.mcella.jcr.oak.upgrade.repository.secondversion.update;

import it.mcella.jcr.oak.upgrade.repository.JcrNode;
import it.mcella.jcr.oak.upgrade.repository.nodetypes.migration.JcrNodeVersionMigratorRunner;

import javax.jcr.RepositoryException;

public class JcrDescriptionPropertyHistoryMigratorRunner implements JcrNodeVersionMigratorRunner {

    private final JcrNodeToDescription jcrNodeToDescription;

    public JcrDescriptionPropertyHistoryMigratorRunner(JcrNodeToDescription jcrNodeToDescription) {
        this.jcrNodeToDescription = jcrNodeToDescription;
    }

    @Override
    public void run(JcrNode jcrNode) throws RepositoryException {
        String description = jcrNodeToDescription.retrieveDescription(jcrNode.getNodeId());
        jcrNode.setProperty(JcrNodeDescriptionMigrator.DESCRIPTION_PROPERTY, description);
    }

}
