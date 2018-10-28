package it.mcella.jcr.oak.upgrade.repository.nodetypes.migration;

import it.mcella.jcr.oak.upgrade.repository.JcrNamespace;
import it.mcella.jcr.oak.upgrade.repository.JcrNode;
import it.mcella.jcr.oak.upgrade.repository.JcrNodeVersion;
import it.mcella.jcr.oak.upgrade.repository.JcrProperty;

import javax.jcr.RepositoryException;
import java.util.List;

public class OakNodeMigration {

    private final JcrNode jcrNode;
    private final JcrNamespace jcrNamespace;
    private final JcrPropertyFilter jcrPropertyFilter;

    public OakNodeMigration(JcrNode jcrNode, JcrNamespace jcrNamespace, JcrPropertyFilter jcrPropertyFilter) {
        this.jcrNode = jcrNode;
        this.jcrNamespace = jcrNamespace;
        this.jcrPropertyFilter = jcrPropertyFilter;
    }

    public void addNodePropertiesFrom(JcrNodeVersion jcrNodeVersion) throws RepositoryException {
        List<JcrProperty> jcrProperties = jcrNodeVersion.getProperties();
        for (JcrProperty jcrProperty : jcrProperties) {
            if (jcrNamespace.isAppliedTo(jcrProperty) && !jcrPropertyFilter.match(jcrProperty)) {
                jcrNode.setProperty(jcrProperty);
            }
        }
    }
}
