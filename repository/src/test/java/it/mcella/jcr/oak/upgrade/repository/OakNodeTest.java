package it.mcella.jcr.oak.upgrade.repository;

import org.apache.jackrabbit.JcrConstants;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import javax.jcr.Binary;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.ValueFactory;
import javax.jcr.Workspace;
import javax.jcr.nodetype.NodeType;
import javax.jcr.version.Version;
import javax.jcr.version.VersionHistory;
import javax.jcr.version.VersionIterator;
import javax.jcr.version.VersionManager;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OakNodeTest {

    private static final String IDENTIFIER = "identifier";

    private final Node node = mock(Node.class);

    private OakNode oakNode;

    @Before
    public void setUp() throws Exception {
        oakNode = new OakNode(node);

        when(node.getIdentifier()).thenReturn(IDENTIFIER);
    }

    @Test
    public void shouldGetNodeId() throws Exception {
        JcrNodeId jcrNodeId = oakNode.getNodeId();

        verify(node).getIdentifier();
        assertTrue(jcrNodeId instanceof OakNodeId);
    }

    @Test
    public void shouldGetNodePath() throws Exception {
        JcrNodePath jcrNodePath = oakNode.getNodePath();

        verify(node).getPath();
        assertTrue(jcrNodePath instanceof OakNodePath);
    }

    @Test
    public void shouldGetNodeName() throws Exception {
        oakNode.getName();

        verify(node).getName();
    }

    @Test
    public void shouldGetPrimaryNodeType() throws Exception {
        NodeType nodeType = mock(NodeType.class);
        when(node.getPrimaryNodeType()).thenReturn(nodeType);

        JcrNodeType jcrNodeType = oakNode.getNodeType();

        verify(node).getPrimaryNodeType();
        assertTrue(jcrNodeType instanceof OakNodeType);
        assertThat(((OakNodeType) jcrNodeType).getNodeType(), is(nodeType));
    }

    @Test
    public void shouldAddNode() throws Exception {
        String name = "node name";
        String type = "node type";

        JcrNode jcrNode = oakNode.addNode(name, type);

        verify(node).addNode(name, type);
        assertTrue(jcrNode instanceof OakNode);
    }

    @Test
    public void shouldAddNodeWithNameNotEscape() throws Exception {
        String name = "node:name";
        String type = "node type";

        JcrNode jcrNode = oakNode.addNodeWithNameNotEscaped(name, type);

        verify(node).addNode(name, type);
        assertTrue(jcrNode instanceof OakNode);
    }

    @Test
    public void shouldCheckIfNodeContainsChildNodeGivenRelativePath() throws Exception {
        String relativePath = "path/to/node";

        oakNode.hasNode(relativePath);

        verify(node).hasNode(relativePath);
    }

    @Test
    public void shouldGetChildNodeFromRelativePath() throws Exception {
        String relativePath = "path/to/node";

        JcrNode jcrChildNode = oakNode.getNode(relativePath);

        verify(node).getNode(relativePath);
        assertTrue(jcrChildNode instanceof OakNode);
    }

    @Test
    public void shouldSetStringNodeProperty() throws Exception {
        String propertyName = "property";
        String propertyValue = "value";

        oakNode.setProperty(propertyName, propertyValue);

        verify(node).setProperty(propertyName, propertyValue);
    }

    @Test
    public void shouldSetBooleanNodeProperty() throws Exception {
        String propertyName = "property";
        boolean propertyValue = true;

        oakNode.setProperty(propertyName, propertyValue);

        verify(node).setProperty(propertyName, propertyValue);
    }

    @Test
    public void shouldSetBinaryNodeProperty() throws Exception {
        String propertyName = "property";
        JcrBinary jcrBinary = mock(JcrBinary.class);
        Binary binary = mock(Binary.class);
        when(jcrBinary.getBinary()).thenReturn(binary);

        oakNode.setProperty(propertyName, jcrBinary);

        verify(node).setProperty(propertyName, binary);
    }

    @Test
    public void shouldCopyMultipleValuesNodeProperty() throws Exception {
        JcrProperty jcrProperty = mock(JcrProperty.class);
        when(jcrProperty.isMultiple()).thenReturn(true);
        String propertyName = "property";
        when(jcrProperty.getName()).thenReturn(propertyName);
        Value[] values = new Value[0];
        when(jcrProperty.getValues()).thenReturn(values);

        oakNode.setProperty(jcrProperty);

        verify(node).setProperty(propertyName, values);
    }

    @Test
    public void shouldCopyNodeProperty() throws Exception {
        JcrProperty jcrProperty = mock(JcrProperty.class);
        when(jcrProperty.isMultiple()).thenReturn(false);
        String propertyName = "property";
        when(jcrProperty.getName()).thenReturn(propertyName);
        Value value = mock(Value.class);
        when(jcrProperty.getValue()).thenReturn(value);

        oakNode.setProperty(jcrProperty);

        verify(node).setProperty(propertyName, value);
    }

    @Test
    public void shouldCheckIfNodeHasProperty() throws Exception {
        String propertyName = "property";

        oakNode.hasProperty(propertyName);

        verify(node).hasProperty(propertyName);
    }

    @Test
    public void shouldGetNodeProperties() throws Exception {
        PropertyIterator propertyIterator = mock(PropertyIterator.class);
        when(node.getProperties()).thenReturn(propertyIterator);
        when(propertyIterator.hasNext()).thenReturn(true, false);
        Property property = mock(Property.class);
        when(propertyIterator.nextProperty()).thenReturn(property);

        List<JcrProperty> jcrProperties = oakNode.getProperties();

        assertThat(jcrProperties.size(), is(1));
        assertTrue(jcrProperties.get(0) instanceof OakProperty);
        assertThat(((OakProperty) jcrProperties.get(0)).getProperty(), is(property));
    }

    @Test
    public void shouldGetNodeProperty() throws Exception {
        String propertyName = "property";

        JcrProperty jcrProperty = oakNode.getProperty(propertyName);

        verify(node).getProperty(propertyName);
        assertTrue(jcrProperty instanceof OakProperty);
    }

    @Test
    public void shouldGetBinary() throws Exception {
        Node contentNode = mock(Node.class, "content");
        when(node.getNode(JcrConstants.JCR_CONTENT)).thenReturn(contentNode);
        Property property = mock(Property.class);
        when(contentNode.getProperty(JcrConstants.JCR_DATA)).thenReturn(property);

        oakNode.getBinary();

        verify(property).getBinary();
    }

    @Test
    public void shouldGetMixinNodeTypes() throws Exception {
        NodeType nodeType = mock(NodeType.class);
        NodeType[] mixinNodeTypes = new NodeType[]{nodeType};
        when(node.getMixinNodeTypes()).thenReturn(mixinNodeTypes);

        List<JcrNodeType> jcrMixinNodeTypes = oakNode.getMixinNodeTypes();

        assertThat(jcrMixinNodeTypes.size(), is(1));
        assertThat(((OakNodeType) jcrMixinNodeTypes.get(0)).getNodeType(), is(nodeType));
    }

    @Test
    public void shouldAddMixin() throws Exception {
        String mixin = "mixin";

        oakNode.addMixin(mixin);

        verify(node).addMixin(mixin);
    }

    @Test
    public void shouldReturnFalseIfNodeIsNotRoot() throws Exception {
        when(node.getPath()).thenReturn("/nodePath");

        assertFalse(oakNode.isRoot());
    }

    @Test
    public void shouldReturnTrueIfNodeIsRoot() throws Exception {
        when(node.getPath()).thenReturn("/");

        assertTrue(oakNode.isRoot());
    }

    @Test
    public void shouldCheckIfNodeHasGivenType() throws Exception {
        NodeType nodeType = mock(NodeType.class);
        when(node.getPrimaryNodeType()).thenReturn(nodeType);
        String type = "node type";

        oakNode.hasType(type);

        verify(nodeType).isNodeType(type);
    }

    @Test
    public void shouldGetNodeParent() throws Exception {
        Node parent = mock(Node.class, "parent node");
        when(node.getParent()).thenReturn(parent);

        JcrNode jcrParentNode = oakNode.getParent();

        verify(node).getParent();
        assertTrue(jcrParentNode instanceof OakNode);
        assertThat(((OakNode) jcrParentNode).getNode(), is(parent));
    }

    @Test
    public void shouldSaveSession() throws Exception {
        Session session = mock(Session.class);
        when(node.getSession()).thenReturn(session);

        oakNode.save();

        verify(session).save();
    }

    @Test
    public void shouldCheckIfNodeHasTypeMixVersionable() throws Exception {
        oakNode.isVersionable();

        verify(node).isNodeType(JcrConstants.MIX_VERSIONABLE);
    }

    @Test
    public void shouldCheckIfNodeIsCheckedOut() throws Exception {
        oakNode.isCheckedOut();

        verify(node).isCheckedOut();
    }

    @Test
    public void shouldCheckoutNode() throws Exception {
        Session session = mock(Session.class);
        when(node.getSession()).thenReturn(session);
        Workspace workspace = mock(Workspace.class);
        when(session.getWorkspace()).thenReturn(workspace);
        VersionManager versionManager = mock(VersionManager.class);
        when(workspace.getVersionManager()).thenReturn(versionManager);
        String path = "/path/to/node";
        when(node.getPath()).thenReturn(path);

        oakNode.checkout();

        verify(versionManager).checkout(path);
    }

    @Test
    public void shouldCheckinNode() throws Exception {
        Session session = mock(Session.class);
        when(node.getSession()).thenReturn(session);
        Workspace workspace = mock(Workspace.class);
        when(session.getWorkspace()).thenReturn(workspace);
        VersionManager versionManager = mock(VersionManager.class);
        when(workspace.getVersionManager()).thenReturn(versionManager);
        String path = "/path/to/node";
        when(node.getPath()).thenReturn(path);

        JcrNodeVersion jcrNodeVersion = oakNode.checkin();

        verify(versionManager).checkin(path);
        assertTrue(jcrNodeVersion instanceof OakNodeVersion);
    }

    @Test
    public void shouldGetNodeVersions() throws Exception {
        Session session = mock(Session.class);
        when(node.getSession()).thenReturn(session);
        Workspace workspace = mock(Workspace.class);
        when(session.getWorkspace()).thenReturn(workspace);
        VersionManager versionManager = mock(VersionManager.class);
        when(workspace.getVersionManager()).thenReturn(versionManager);
        String path = "/path/to/node";
        when(node.getPath()).thenReturn(path);
        VersionHistory versionHistory = mock(VersionHistory.class);
        when(versionManager.getVersionHistory(path)).thenReturn(versionHistory);
        VersionIterator versionIterator = mock(VersionIterator.class);
        when(versionHistory.getAllVersions()).thenReturn(versionIterator);
        when(versionIterator.hasNext()).thenReturn(true, true, false);
        Version version = mock(Version.class);
        Version rootVersion = mock(Version.class, "rootVersion");
        when(versionIterator.next()).thenReturn(version, rootVersion);
        when(version.getName()).thenReturn("version-name");
        when(rootVersion.getName()).thenReturn(JcrConstants.JCR_ROOTVERSION);

        List<JcrNodeVersion> versions = oakNode.getVersions();

        assertThat(versions.size(), is(1));
        assertThat(((OakNodeVersion) versions.get(0)).getVersion(), is(version));
    }

    @Test
    public void shouldGetLastNodeVersion() throws Exception {
        Session session = mock(Session.class);
        when(node.getSession()).thenReturn(session);
        Workspace workspace = mock(Workspace.class);
        when(session.getWorkspace()).thenReturn(workspace);
        VersionManager versionManager = mock(VersionManager.class);
        when(workspace.getVersionManager()).thenReturn(versionManager);
        String path = "/path/to/node";
        when(node.getPath()).thenReturn(path);
        VersionHistory versionHistory = mock(VersionHistory.class);
        when(versionManager.getVersionHistory(path)).thenReturn(versionHistory);
        Version lastVersion = mock(Version.class, "lastVersion");
        when(versionHistory.getVersionByLabel(OakNodeVersion.LAST_VERSION_LABEL)).thenReturn(lastVersion);

        JcrNodeVersion jcrNodeVersion = oakNode.getLastVersion();

        verify(versionHistory).getVersionByLabel(OakNodeVersion.LAST_VERSION_LABEL);
        assertThat(((OakNodeVersion) jcrNodeVersion).getVersion(), is(lastVersion));
    }

    @Test
    public void shouldRemoveJcrNodeVersion() throws Exception {
        Session session = mock(Session.class);
        when(node.getSession()).thenReturn(session);
        Workspace workspace = mock(Workspace.class);
        when(session.getWorkspace()).thenReturn(workspace);
        VersionManager versionManager = mock(VersionManager.class);
        when(workspace.getVersionManager()).thenReturn(versionManager);
        String path = "/path/to/node";
        when(node.getPath()).thenReturn(path);
        VersionHistory versionHistory = mock(VersionHistory.class);
        when(versionManager.getVersionHistory(path)).thenReturn(versionHistory);
        JcrNodeVersion jcrNodeVersion = mock(JcrNodeVersion.class);
        String versionName = "versionName";
        when(jcrNodeVersion.getName()).thenReturn(versionName);

        oakNode.removeVersion(jcrNodeVersion);

        verify(versionHistory).removeVersion(versionName);
    }

    @Test
    public void shouldGetChildNodes() throws Exception {
        NodeIterator nodeIterator = mock(NodeIterator.class);
        when(node.getNodes()).thenReturn(nodeIterator);
        when(nodeIterator.hasNext()).thenReturn(true, false);
        Node childNode = mock(Node.class, "child");
        when(nodeIterator.nextNode()).thenReturn(childNode);

        List<JcrNode> childNodes = oakNode.getChildNodes();

        assertThat(childNodes.size(), is(1));
        assertThat(((OakNode) childNodes.get(0)).getNode(), is(childNode));
    }

    @Test
    public void shouldCheckIfNodeNameBelongsToGivenNamespace() throws Exception {
        JcrNamespace jcrNamespace = mock(JcrNamespace.class);

        oakNode.checkNodeNameBelongsTo(jcrNamespace);

        verify(jcrNamespace).isAppliedTo(oakNode);
    }

    @Test
    public void shouldChangeNodePrimaryTypeFromGivenNamespace() throws Exception {
        NodeType nodeType = mock(NodeType.class);
        when(node.getPrimaryNodeType()).thenReturn(nodeType);
        JcrNamespace jcrNamespace = mock(JcrNamespace.class);
        String nodeTypeName = "nodeTypeName";
        when(jcrNamespace.getNodeTypeNameFrom(any(JcrNodeType.class))).thenReturn(nodeTypeName);
        when(nodeType.getName()).thenReturn(nodeTypeName);

        oakNode.changeNodeType(jcrNamespace);

        ArgumentCaptor<JcrNodeType> jcrNodeTypeCaptor = ArgumentCaptor.forClass(JcrNodeType.class);
        verify(jcrNamespace).getNodeTypeNameFrom(jcrNodeTypeCaptor.capture());
        assertThat(jcrNodeTypeCaptor.getValue().getName(), is(nodeTypeName));
        verify(node).setPrimaryType(nodeTypeName);
    }

    @Test
    public void shouldRenameNodeMixinName() throws Exception {
        JcrNodeType jcrMixinNodeType = mock(JcrNodeType.class);
        String mixinName = "mixinName";
        when(jcrMixinNodeType.getName()).thenReturn(mixinName);
        JcrNamespace jcrNamespace = mock(JcrNamespace.class);
        String newMixinName = "newMixinName";
        when(jcrNamespace.getNodeTypeNameFrom(any(JcrNodeType.class))).thenReturn(newMixinName);

        oakNode.renameMixin(jcrMixinNodeType, jcrNamespace);

        verify(node).removeMixin(mixinName);
        ArgumentCaptor<JcrNodeType> jcrMixinNodeTypeCaptor = ArgumentCaptor.forClass(JcrNodeType.class);
        verify(jcrNamespace).getNodeTypeNameFrom(jcrMixinNodeTypeCaptor.capture());
        assertThat(jcrMixinNodeTypeCaptor.getValue().getName(), is(mixinName));
        verify(node).addMixin(newMixinName);
    }

    @Test
    public void shouldChangeNodePathWithNewNodeNameFromGivenNamespace() throws Exception {
        JcrNode jcrParentNode = mock(JcrNode.class, "parentNode");
        oakNode = new OakNode(node) {
            @Override
            public JcrNode getParent() throws RepositoryException {
                return jcrParentNode;
            }
        };
        String nodePath = "/path/to/node";
        when(node.getPath()).thenReturn(nodePath);
        JcrNamespace jcrNamespace = mock(JcrNamespace.class);
        String newNodePath = "/new/path/to/node";
        when(jcrNamespace.getNodePath(oakNode)).thenReturn(newNodePath);
        Session session = mock(Session.class);
        when(node.getSession()).thenReturn(session);
        JcrNodeType jcrParentNodeType = mock(JcrNodeType.class);
        when(jcrParentNode.getNodeType()).thenReturn(jcrParentNodeType);
        when(jcrParentNodeType.hasOrderableChildNodes()).thenReturn(false);

        oakNode.rename(jcrNamespace);

        verify(session).move(nodePath, newNodePath);
    }

    @Test
    public void shouldOrderNodeBeforeNextSiblingNode() throws Exception {
        JcrNode jcrParentNode = mock(JcrNode.class, "parentNode");
        oakNode = new OakNode(node) {
            @Override
            public JcrNode getParent() throws RepositoryException {
                return jcrParentNode;
            }
        };
        JcrNodeType jcrParentNodeType = mock(JcrNodeType.class);
        when(jcrParentNode.getNodeType()).thenReturn(jcrParentNodeType);
        when(jcrParentNodeType.hasOrderableChildNodes()).thenReturn(true);
        OakNode jcrNextSiblingNode = mock(OakNode.class, "nextSiblingNode");
        Node nextSiblingNode = mock(Node.class);
        when(jcrNextSiblingNode.getNode()).thenReturn(nextSiblingNode);
        when(nextSiblingNode.getIdentifier()).thenReturn("nextSiblingNodeIdentifier");
        List<JcrNode> jcrChildNodes = Collections.unmodifiableList(Arrays.asList(oakNode, jcrNextSiblingNode));
        when(jcrParentNode.getChildNodes()).thenReturn(jcrChildNodes);
        JcrNamespace jcrNamespace = mock(JcrNamespace.class);
        Session session = mock(Session.class);
        when(node.getSession()).thenReturn(session);

        oakNode.rename(jcrNamespace);

        verify(jcrParentNode).orderBefore(oakNode, jcrNextSiblingNode);
    }

    @Test
    public void shouldOrderFirstChildNodeBeforeSecondChildNode() throws Exception {
        JcrNode jcrFirstChildNode = mock(JcrNode.class, "firstChildNode");
        String firstNodeRelativePath = "firstNodeRelativePath";
        when(jcrFirstChildNode.getRelativePath()).thenReturn("firstNodeRelativePath");
        JcrNode jcrSecondChildNode = mock(JcrNode.class, "secondChildNode");
        String secondNodeRelativePath = "secondNodeRelativePath";
        when(jcrSecondChildNode.getRelativePath()).thenReturn("secondNodeRelativePath");

        oakNode.orderBefore(jcrFirstChildNode, jcrSecondChildNode);

        verify(node).orderBefore(firstNodeRelativePath, secondNodeRelativePath);
    }

    @Test
    public void shouldGetNodeRelativePath() throws Exception {
        String nodeName = "nodeName";
        when(node.getName()).thenReturn(nodeName);
        int index = 4;
        when(node.getIndex()).thenReturn(index);

        String nodeRelativePath = oakNode.getRelativePath();

        assertThat(nodeRelativePath, is(nodeName + "[" + Integer.toString(index) + "]"));
    }

    @Test
    public void shouldGetChildNodePathFromNodePathAndGivenNodeName() throws Exception {
        JcrNodePath jcrNodePath = mock(JcrNodePath.class);
        oakNode = new OakNode(node) {
            @Override
            public JcrNodePath getNodePath() throws RepositoryException {
                return jcrNodePath;
            }
        };
        String nodeName = "nodeName";

        oakNode.getChildNodePath(nodeName);

        verify(jcrNodePath).getChildNodePath(nodeName);
    }

    @Test
    public void shouldRenameNodePropertyWithSingleValueFromGivenNamespace() throws Exception {
        JcrProperty jcrProperty = mock(JcrProperty.class);
        JcrNamespace jcrNamespace = mock(JcrNamespace.class);
        String propertyName = "propertyName";
        when(jcrNamespace.getPropertyNameFrom(jcrProperty)).thenReturn(propertyName);
        when(jcrProperty.isMultiple()).thenReturn(false);
        Value value = mock(Value.class);
        when(jcrProperty.getValue()).thenReturn(value);

        oakNode.renameProperty(jcrProperty, jcrNamespace);

        verify(node).setProperty(propertyName, value);
        verify(jcrProperty).remove();
    }

    @Test
    public void shouldRenameNodePropertyWithMultipleValuesFromGivenNamespace() throws Exception {
        JcrProperty jcrProperty = mock(JcrProperty.class);
        JcrNamespace jcrNamespace = mock(JcrNamespace.class);
        String propertyName = "propertyName";
        when(jcrNamespace.getPropertyNameFrom(jcrProperty)).thenReturn(propertyName);
        when(jcrProperty.isMultiple()).thenReturn(true);
        Value firstValue = mock(Value.class, "firstValue");
        Value secondValue = mock(Value.class, "secondValue");
        Value[] values = new Value[]{firstValue, secondValue};
        when(jcrProperty.getValues()).thenReturn(values);

        oakNode.renameProperty(jcrProperty, jcrNamespace);

        verify(node).setProperty(propertyName, values);
        verify(jcrProperty).remove();
    }

    @Test
    public void shouldRemoveNodeProperty() throws Exception {
        JcrProperty jcrProperty = mock(JcrProperty.class);

        oakNode.removeProperty(jcrProperty);

        verify(jcrProperty).remove();
    }

    @Test
    public void shouldGetJcrValueFactory() throws Exception {
        Session session = mock(Session.class);
        when(node.getSession()).thenReturn(session);
        ValueFactory valueFactory = mock(ValueFactory.class);
        when(session.getValueFactory()).thenReturn(valueFactory);

        JcrValueFactory jcrValueFactory = oakNode.getJcrValueFactory();

        verify(session).getValueFactory();
        assertTrue(jcrValueFactory instanceof OakValueFactory);
        assertThat(((OakValueFactory) jcrValueFactory).getValueFactory(), is(valueFactory));
    }

    @Test
    public void shouldGetNode() throws Exception {
        assertThat(oakNode.getNode(), is(node));
    }

    @Test
    public void shouldGetNodeIndex() throws Exception {
        oakNode.getIndex();
        verify(node).getIndex();
    }

}