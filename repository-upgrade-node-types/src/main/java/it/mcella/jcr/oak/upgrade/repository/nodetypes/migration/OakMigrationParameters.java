package it.mcella.jcr.oak.upgrade.repository.nodetypes.migration;

import it.mcella.jcr.oak.upgrade.repository.JcrNamespace;
import it.mcella.jcr.oak.upgrade.repository.JcrNamespaceConfiguration;

public class OakMigrationParameters {

    private final JcrNamespace jcrOldNamespace;
    private final JcrNamespaceConfiguration jcrNamespaceOldConfiguration;
    private final JcrNamespace jcrIntermediateNamespace;
    private final JcrNamespaceConfiguration jcrNamespaceIntermediateConfiguration;
    private final JcrNamespace jcrNewNamespace;
    private final JcrNamespaceConfiguration jcrNamespaceNewConfiguration;

    public OakMigrationParameters(JcrNamespace jcrOldNamespace, JcrNamespaceConfiguration jcrNamespaceOldConfiguration,
                                  JcrNamespace jcrIntermediateNamespace, JcrNamespaceConfiguration jcrNamespaceIntermediateConfiguration,
                                  JcrNamespace jcrNewNamespace, JcrNamespaceConfiguration jcrNamespaceNewConfiguration) {
        this.jcrOldNamespace = jcrOldNamespace;
        this.jcrNamespaceOldConfiguration = jcrNamespaceOldConfiguration;
        this.jcrIntermediateNamespace = jcrIntermediateNamespace;
        this.jcrNamespaceIntermediateConfiguration = jcrNamespaceIntermediateConfiguration;
        this.jcrNewNamespace = jcrNewNamespace;
        this.jcrNamespaceNewConfiguration = jcrNamespaceNewConfiguration;
    }

    public JcrNamespace getJcrOldNamespace() {
        return jcrOldNamespace;
    }

    public JcrNamespaceConfiguration getJcrNamespaceOldConfiguration() {
        return jcrNamespaceOldConfiguration;
    }

    public JcrNamespace getJcrIntermediateNamespace() {
        return jcrIntermediateNamespace;
    }

    public JcrNamespaceConfiguration getJcrNamespaceIntermediateConfiguration() {
        return jcrNamespaceIntermediateConfiguration;
    }

    public JcrNamespace getJcrNewNamespace() {
        return jcrNewNamespace;
    }

    public JcrNamespaceConfiguration getJcrNamespaceNewConfiguration() {
        return jcrNamespaceNewConfiguration;
    }

}
