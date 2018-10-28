package it.mcella.jcr.oak.upgrade.repository;

import org.apache.jackrabbit.JcrConstants;
import org.apache.jackrabbit.util.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.RepositoryException;
import javax.jcr.nodetype.NodeType;
import javax.jcr.version.Version;
import javax.jcr.version.VersionHistory;
import javax.jcr.version.VersionIterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class OakNode implements JcrNode {

    private static final String NODE_RELATIVE_PATH_PATTERN = "%s[%s]";

    private final Logger LOGGER = LoggerFactory.getLogger(OakNode.class);

    private final Node node;

    public OakNode(Node node) {
        this.node = node;
    }

    @Override
    public JcrNodeId getNodeId() throws RepositoryException {
        return new OakNodeId(node.getIdentifier());
    }

    @Override
    public JcrNodePath getNodePath() throws RepositoryException {
        return new OakNodePath(node.getPath());
    }

    @Override
    public String getName() throws RepositoryException {
        return node.getName();
    }

    @Override
    public JcrNodeType getNodeType() throws RepositoryException {
        return new OakNodeType(node.getPrimaryNodeType());
    }

    @Override
    public JcrNode addNode(String name, String type) throws RepositoryException {
        String encodedFolderName = Text.escapeIllegalJcrChars(name);
        return new OakNode(node.addNode(encodedFolderName, type));
    }

    @Override
    public JcrNode addNodeWithNameNotEscaped(String name, String type) throws RepositoryException {
        return new OakNode(node.addNode(name, type));
    }

    @Override
    public boolean hasNode(String relativePath) throws RepositoryException {
        return node.hasNode(relativePath);
    }

    @Override
    public JcrNode getNode(String relativePath) throws RepositoryException {
        return new OakNode(node.getNode(relativePath));
    }

    @Override
    public void setProperty(String name, String value) throws RepositoryException {
        node.setProperty(name, value);
    }

    @Override
    public void setProperty(String name, boolean value) throws RepositoryException {
        node.setProperty(name, value);
    }

    @Override
    public void setProperty(String name, JcrBinary binary) throws RepositoryException {
        node.setProperty(name, binary.getBinary());
    }

    @Override
    public void setProperty(JcrProperty jcrProperty) throws RepositoryException {
        if (jcrProperty.isMultiple()) {
            node.setProperty(jcrProperty.getName(), jcrProperty.getValues());
        } else {
            node.setProperty(jcrProperty.getName(), jcrProperty.getValue());
        }
    }

    @Override
    public boolean hasProperty(String name) throws RepositoryException {
        return node.hasProperty(name);
    }

    @Override
    public List<JcrProperty> getProperties() throws RepositoryException {
        List<JcrProperty> jcrProperties = new ArrayList<>();
        PropertyIterator propertyIterator = node.getProperties();
        while (propertyIterator.hasNext()) {
            Property property = propertyIterator.nextProperty();
            jcrProperties.add(new OakProperty(property));
        }
        return Collections.unmodifiableList(jcrProperties);
    }

    @Override
    public JcrProperty getProperty(String name) throws RepositoryException {
        return new OakProperty(node.getProperty(name));
    }

    @Override
    public JcrBinary getBinary() throws RepositoryException {
        return new OakBinary(node.getNode(JcrConstants.JCR_CONTENT).getProperty(JcrConstants.JCR_DATA).getBinary());
    }

    @Override
    public List<JcrNodeType> getMixinNodeTypes() throws RepositoryException {
        List<JcrNodeType> jcrNodeTypes = new ArrayList<>();
        for (NodeType mixinNodeType : node.getMixinNodeTypes()) {
            jcrNodeTypes.add(new OakNodeType(mixinNodeType));
        }
        return jcrNodeTypes;
    }

    @Override
    public void addMixin(String name) throws RepositoryException {
        node.addMixin(name);
    }

    @Override
    public boolean isRoot() throws RepositoryException {
        return node.getPath().equals("/");
    }

    @Override
    public boolean hasType(String type) throws RepositoryException {
        return node.getPrimaryNodeType().isNodeType(type);
    }

    @Override
    public JcrNode getParent() throws RepositoryException {
        return new OakNode(node.getParent());
    }

    @Override
    public void save() throws RepositoryException {
        node.getSession().save();
    }

    @Override
    public boolean isVersionable() throws RepositoryException {
        return node.isNodeType(JcrConstants.MIX_VERSIONABLE);
    }

    @Override
    public boolean isCheckedOut() throws RepositoryException {
        return node.isCheckedOut();
    }

    @Override
    public void checkout() throws RepositoryException {
        node.getSession().getWorkspace().getVersionManager().checkout(node.getPath());
    }

    @Override
    public JcrNodeVersion checkin() throws RepositoryException {
        return new OakNodeVersion(node.getSession().getWorkspace().getVersionManager().checkin(node.getPath()));
    }

    @Override
    public List<JcrNodeVersion> getVersions() throws RepositoryException {
        List<JcrNodeVersion> nodeVersions = new ArrayList<>();
        VersionIterator versionIterator = node.getSession().getWorkspace().getVersionManager().getVersionHistory(node.getPath()).getAllVersions();
        while (versionIterator.hasNext()) {
            Version version = (Version) versionIterator.next();
            if (!JcrConstants.JCR_ROOTVERSION.equals(version.getName())) {
                nodeVersions.add(new OakNodeVersion(version));
            }
        }
        return nodeVersions;
    }

    @Override
    public JcrNodeVersion getLastVersion() throws RepositoryException {
        VersionHistory versionHistory = node.getSession().getWorkspace().getVersionManager().getVersionHistory(node.getPath());
        Version lastVersion = versionHistory.getVersionByLabel(OakNodeVersion.LAST_VERSION_LABEL);
        return new OakNodeVersion(lastVersion);
    }

    @Override
    public void removeVersion(JcrNodeVersion jcrNodeVersion) throws RepositoryException {
        VersionHistory versionHistory = node.getSession().getWorkspace().getVersionManager().getVersionHistory(node.getPath());
        versionHistory.removeVersion(jcrNodeVersion.getName());
    }

    @Override
    public List<JcrNode> getChildNodes() throws RepositoryException {
        NodeIterator nodeIterator = node.getNodes();
        List<JcrNode> childNodes = new ArrayList<>();
        while (nodeIterator.hasNext()) {
            childNodes.add(new OakNode(nodeIterator.nextNode()));
        }
        return childNodes;
    }

    @Override
    public boolean checkNodeNameBelongsTo(JcrNamespace jcrNamespace) throws RepositoryException {
        return jcrNamespace.isAppliedTo(this);
    }

    @Override
    public void changeNodeType(JcrNamespace jcrNamespace) throws RepositoryException {
        node.setPrimaryType(jcrNamespace.getNodeTypeNameFrom(getNodeType()));
    }

    public void removeMixin(JcrNodeType jcrMixinNodeType) throws RepositoryException {
        node.removeMixin(jcrMixinNodeType.getName());
    }

    @Override
    public void renameMixin(JcrNodeType jcrMixinNodeType, JcrNamespace jcrNamespace) throws RepositoryException {
        node.removeMixin(jcrMixinNodeType.getName());
        node.addMixin(jcrNamespace.getNodeTypeNameFrom(jcrMixinNodeType));
    }

    @Override
    public void rename(JcrNamespace jcrNamespace) throws RepositoryException {
        node.getSession().move(getNodePath().getPath(), jcrNamespace.getNodePath(this));
        Optional<JcrNode> nextSibling = getNextSibling();
        if (nextSibling.isPresent()) {
            getParent().orderBefore(this, nextSibling.get());
        }
    }

    @Override
    public void orderBefore(JcrNode jcrFirstNode, JcrNode jcrSecondNode) throws RepositoryException {
        node.orderBefore(jcrFirstNode.getRelativePath(), jcrSecondNode.getRelativePath());
    }

    @Override
    public String getRelativePath() throws RepositoryException {
        return String.format(NODE_RELATIVE_PATH_PATTERN, getName(), getIndex());
    }

    @Override
    public String getChildNodePath(String nodeName) throws RepositoryException {
        return getNodePath().getChildNodePath(nodeName);
    }

    @Override
    public void renameProperty(JcrProperty jcrProperty, JcrNamespace jcrNamespace) throws RepositoryException {
        String propertyName = jcrNamespace.getPropertyNameFrom(jcrProperty);
        if (jcrProperty.isMultiple()) {
            node.setProperty(propertyName, jcrProperty.getValues());
        } else {
            node.setProperty(propertyName, jcrProperty.getValue());
        }
        jcrProperty.remove();
    }

    @Override
    public void removeProperty(JcrProperty jcrProperty) throws RepositoryException {
        jcrProperty.remove();
    }

    @Override
    public JcrValueFactory getJcrValueFactory() throws RepositoryException {
        return new OakValueFactory(node.getSession().getValueFactory());
    }

    public Node getNode() {
        return node;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OakNode oakNode = (OakNode) o;

        try {
            if (node != null ? !node.getIdentifier().equals(oakNode.node.getIdentifier()) : oakNode.node != null)
                return false;
        } catch (RepositoryException e) {
            LOGGER.error(e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return node != null ? node.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "OakNode{" +
                "name=" + getNodeNameQuietly() +
                ", path=" + getNodePathQuietly() +
                '}';
    }

    int getIndex() throws RepositoryException {
        return node.getIndex();
    }

    private Optional<JcrNode> getNextSibling() throws RepositoryException {
        if (!getParent().getNodeType().hasOrderableChildNodes()) {
            return Optional.empty();
        }
        List<JcrNode> jcrChildNodes = getParent().getChildNodes();
        int nodeIndex = jcrChildNodes.indexOf(this);
        int nextNodeIndex = nodeIndex + 1;
        if (nextNodeIndex >= jcrChildNodes.size()) {
            return Optional.empty();
        }
        return Optional.of(jcrChildNodes.get(nextNodeIndex));
    }

    private String getNodeNameQuietly() {
        try {
            return getName();
        } catch (RepositoryException e) {
            LOGGER.warn("Cannot retrieve node name" + e.getMessage());
        }
        return "";
    }

    private JcrNodePath getNodePathQuietly() {
        try {
            return getNodePath();
        } catch (RepositoryException e) {
            LOGGER.warn("Cannot retrieve node path" + e.getMessage());
        }
        return new OakNodePath("");
    }

}
