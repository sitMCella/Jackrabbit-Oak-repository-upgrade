package it.mcella.jcr.oak.upgrade.repository.nodetypes.migration;

import it.mcella.jcr.oak.upgrade.repository.JcrNamespace;
import it.mcella.jcr.oak.upgrade.repository.JcrNode;
import it.mcella.jcr.oak.upgrade.repository.JcrNodeType;
import it.mcella.jcr.oak.upgrade.repository.JcrProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.RepositoryException;

@SuppressWarnings("deprecation")
public class OakNodeMigrationChecker {

    private static final Logger LOGGER = LoggerFactory.getLogger(OakNodeMigrationChecker.class.getName());

    public boolean check(JcrNode jcrNode, JcrNamespace jcrOldNamespace) throws RepositoryException {
        boolean migrationCompleted = true;
        migrationCompleted &= checkNodePrimaryType(jcrNode, jcrOldNamespace);
        for (JcrNodeType jcrNodeType : jcrNode.getMixinNodeTypes()) {
            migrationCompleted &= checkMixinNodeType(jcrNodeType, jcrNode, jcrOldNamespace);
        }
        migrationCompleted &= checkNodeName(jcrNode, jcrOldNamespace);
        for (JcrProperty jcrProperty : jcrNode.getProperties()) {
            migrationCompleted &= checkPropertyName(jcrProperty, jcrNode, jcrOldNamespace);
        }
        return migrationCompleted;
    }

    boolean checkNodePrimaryType(JcrNode jcrNode, JcrNamespace jcrNamespace) throws RepositoryException {
        JcrNodeType jcrNodeType = jcrNode.getNodeType();
        if (jcrNodeType.belongsTo(jcrNamespace)) {
            LOGGER.warn(String.format("Node %s not migrated: primary type has old namespace prefix", jcrNode));
            return false;
        }
        return true;
    }

    boolean checkMixinNodeType(JcrNodeType jcrNodeType, JcrNode jcrNode, JcrNamespace jcrNamespace) throws RepositoryException {
        if (jcrNodeType.belongsTo(jcrNamespace)) {
            LOGGER.warn(String.format("Node %s not migrated: mixin type %s has old namespace prefix", jcrNode, jcrNodeType));
            return false;
        }
        return true;
    }

    boolean checkNodeName(JcrNode jcrNode, JcrNamespace jcrNamespace) throws RepositoryException {
        if (jcrNode.checkNodeNameBelongsTo(jcrNamespace)) {
            LOGGER.warn(String.format("Node %s not migrated: node name has old namespace prefix", jcrNode));
            return false;
        }
        return true;
    }

    boolean checkPropertyName(JcrProperty jcrProperty, JcrNode jcrNode, JcrNamespace jcrNamespace) throws RepositoryException {
        if (jcrProperty.belongsTo(jcrNamespace)) {
            LOGGER.warn(String.format("Node %s not migrated: property %s has old namespace prefix", jcrNode, jcrProperty));
            return false;
        }
        return true;
    }

}
