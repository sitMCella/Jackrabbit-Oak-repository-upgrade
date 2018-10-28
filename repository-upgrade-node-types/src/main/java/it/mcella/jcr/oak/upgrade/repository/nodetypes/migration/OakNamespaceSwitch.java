package it.mcella.jcr.oak.upgrade.repository.nodetypes.migration;

import it.mcella.jcr.oak.upgrade.repository.JcrNamespace;
import it.mcella.jcr.oak.upgrade.repository.JcrNode;
import it.mcella.jcr.oak.upgrade.repository.JcrNodeType;
import it.mcella.jcr.oak.upgrade.repository.JcrProperty;
import it.mcella.jcr.oak.upgrade.repository.JcrPropertyType;
import it.mcella.jcr.oak.upgrade.repository.OakPropertyType;

import javax.jcr.PropertyType;
import javax.jcr.RepositoryException;
import java.util.List;
import java.util.stream.Collectors;

public class OakNamespaceSwitch {

    private static final JcrPropertyType JCR_NAME_PROPERTY_TYPE = new OakPropertyType(PropertyType.NAME);
    private static final JcrPropertyType JCR_URI_PROPERTY_TYPE = new OakPropertyType(PropertyType.URI);

    private final JcrNamespace jcrOldNamespace;
    private final JcrNamespace jcrNewNamespace;

    public OakNamespaceSwitch(JcrNamespace jcrOldNamespace, JcrNamespace jcrNewNamespace) {
        this.jcrOldNamespace = jcrOldNamespace;
        this.jcrNewNamespace = jcrNewNamespace;
    }

    public void update(JcrNode jcrNode) throws JcrNamespaceSwitchException {
        try {
            renamePrimaryTypeOf(jcrNode);
            renameMixinTypesOf(jcrNode);
            rename(jcrNode);
            renamePropertiesOf(jcrNode);
            renamePropertyValuesOf(jcrNode);
        } catch (RepositoryException e) {
            throw new JcrNamespaceSwitchException(e);
        }
    }

    void renamePrimaryTypeOf(JcrNode jcrNode) throws RepositoryException {
        if (jcrNode.getNodeType().belongsTo(jcrOldNamespace)) {
            jcrNode.changeNodeType(jcrNewNamespace);
        }
    }

    void renameMixinTypesOf(JcrNode jcrNode) throws RepositoryException {
        List<JcrNodeType> jcrMixinNodeTypes = jcrNode.getMixinNodeTypes().stream()
                .filter(jcrMixinNodeType -> jcrMixinNodeType.belongsTo(jcrOldNamespace))
                .collect(Collectors.toList());
        for (JcrNodeType jcrMixinNodeType : jcrMixinNodeTypes) {
            jcrNode.renameMixin(jcrMixinNodeType, jcrNewNamespace);
        }
    }

    void rename(JcrNode jcrNode) throws RepositoryException {
        if (jcrNode.checkNodeNameBelongsTo(jcrOldNamespace)) {
            jcrNode.rename(jcrNewNamespace);
        }
    }

    void renamePropertiesOf(JcrNode jcrNode) throws RepositoryException {
        for (JcrProperty jcrProperty : jcrNode.getProperties()) {
            if (jcrProperty.belongsTo(jcrOldNamespace)) {
                jcrNode.renameProperty(jcrProperty, jcrNewNamespace);
            }
        }
    }

    void renamePropertyValuesOf(JcrNode jcrNode) throws RepositoryException {
        for (JcrProperty jcrProperty : jcrNode.getProperties()) {
            JcrPropertyType jcrPropertyType = jcrProperty.getPropertyType();
            if (jcrPropertyType.equals(JCR_NAME_PROPERTY_TYPE) || jcrPropertyType.equals(JCR_URI_PROPERTY_TYPE)) {
                jcrProperty.renameValueNames(jcrOldNamespace, jcrNewNamespace, jcrNode.getJcrValueFactory());
            }
        }
    }

}
