package it.mcella.jcr.oak.upgrade.repository.secondversion.update;

import it.mcella.jcr.oak.upgrade.repository.JcrNode;
import it.mcella.jcr.oak.upgrade.repository.JcrNodeId;
import it.mcella.jcr.oak.upgrade.repository.JcrSession;
import it.mcella.jcr.oak.upgrade.repository.nodetypes.migration.JcrMigrator;
import it.mcella.jcr.oak.upgrade.repository.nodetypes.migration.JcrNodeVersionMigrator;
import it.mcella.jcr.oak.upgrade.repository.nodetypes.migration.JcrNodeVersionMigratorRunner;
import it.mcella.jcr.oak.upgrade.repository.nodetypes.migration.JcrNodesMigratorException;
import it.mcella.jcr.oak.upgrade.repository.nodetypes.migration.OakNodeVersionMigrator;
import it.mcella.jcr.oak.upgrade.repository.secondversion.node.file.JcrFileNode;
import it.mcella.jcr.oak.upgrade.repository.secondversion.node.folder.JcrFolderNode;
import org.apache.jackrabbit.JcrConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.RepositoryException;

public class JcrNodeDescriptionMigrator implements JcrMigrator {

    private static final Logger LOGGER = LoggerFactory.getLogger(JcrNodeDescriptionMigrator.class);

    public static final String DESCRIPTION_PROPERTY = "app:description";

    private final JcrNodeToDescription jcrNodeIdToDescription;
    private final JcrNodeVersionMigrator jcrNodeVersionMigrator;

    public JcrNodeDescriptionMigrator(JcrNodeToDescription jcrNodeIdToDescription, JcrNodeVersionMigratorRunner jcrNodeVersionMigratorRunner) {
        this.jcrNodeIdToDescription = jcrNodeIdToDescription;
        this.jcrNodeVersionMigrator = new OakNodeVersionMigrator(
                jcrNodeVersionMigratorRunner,
                JcrUpgradeConstants.JCR_OLD_NAMESPACE,
                new JcrDefaultPropertyFilter());
    }

    @Override
    public void upgrade(JcrSession jcrSession) throws JcrNodesMigratorException {
        try {
            JcrNode jcrRootNode = jcrSession.getRootNode();
            upgradeChildNodes(jcrRootNode);
        } catch (RepositoryException e) {
            throw new JcrNodesMigratorException("Cannot migrate nodes", e);
        }
    }

    private void upgradeChildNodes(JcrNode jcrNode) throws RepositoryException {
        jcrNode.getChildNodes().stream()
                .filter(jcrChildNode -> checkAppNodeType(jcrChildNode, JcrFileNode.NODE_TYPE)
                        || checkAppNodeType(jcrChildNode, JcrFolderNode.NODE_TYPE))
                .forEach(this::upgradeNode);
    }

    private boolean checkAppNodeType(JcrNode jcrNode, String nodeType) {
        JcrNodeId jcrNodeId = null;
        try {
            jcrNodeId = jcrNode.getNodeId();
            return jcrNode.hasType(nodeType);
        } catch (RepositoryException e) {
            LOGGER.warn(String.format("Cannot check type of node with id \"%s\"", jcrNodeId), e);
        }
        return false;
    }

    private void upgradeNode(JcrNode jcrNode) {
        JcrNodeId jcrNodeId = null;
        try {
            jcrNodeId = jcrNode.getNodeId();
            if (jcrNode.hasProperty(JcrConstants.JCR_MIXINTYPES)) {
                jcrNodeVersionMigrator.migrate(jcrNode);
            }
            String description = jcrNodeIdToDescription.retrieveDescription(jcrNodeId);
            jcrNode.setProperty(DESCRIPTION_PROPERTY, description);
            jcrNode.save();
            LOGGER.info(String.format("Updated description of node with id \"%s\"", jcrNodeId));
            upgradeFolderNodeChildrenNodes(jcrNode);
        } catch (RepositoryException e) {
            LOGGER.warn(String.format("Cannot update description of node with id \"%s\"", jcrNodeId), e);
        }
    }

    private void upgradeFolderNodeChildrenNodes(JcrNode jcrNode) {
        JcrNodeId jcrNodeId = null;
        try {
            jcrNodeId = jcrNode.getNodeId();
            if (jcrNode.hasType(JcrFolderNode.NODE_TYPE)) {
                upgradeChildNodes(jcrNode);
            }
        } catch (RepositoryException e) {
            LOGGER.warn(String.format("Cannot update description of children nodes of node with id \"%s\"", jcrNodeId), e);
        }
    }

}
