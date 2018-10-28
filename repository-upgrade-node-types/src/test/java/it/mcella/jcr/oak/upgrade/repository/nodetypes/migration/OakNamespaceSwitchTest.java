package it.mcella.jcr.oak.upgrade.repository.nodetypes.migration;

import it.mcella.jcr.oak.upgrade.repository.JcrNamespace;
import it.mcella.jcr.oak.upgrade.repository.JcrNode;
import it.mcella.jcr.oak.upgrade.repository.JcrNodeType;
import it.mcella.jcr.oak.upgrade.repository.JcrProperty;
import it.mcella.jcr.oak.upgrade.repository.JcrPropertyType;
import it.mcella.jcr.oak.upgrade.repository.OakPropertyType;
import org.junit.Before;
import org.junit.Test;

import javax.jcr.PropertyType;
import javax.jcr.RepositoryException;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OakNamespaceSwitchTest {

    private final JcrNamespace jcrOldNamespace = mock(JcrNamespace.class, "oldNamespace");
    private final JcrNamespace jcrNewNamespace = mock(JcrNamespace.class, "newNamespace");
    private final JcrNode jcrNode = mock(JcrNode.class);
    private final JcrNodeType jcrNodePrimaryType = mock(JcrNodeType.class, "nodePrimaryType");
    private final JcrProperty jcrProperty = mock(JcrProperty.class);

    private OakNamespaceSwitch oakNamespaceSwitch;

    @Before
    public void setUp() throws Exception {
        oakNamespaceSwitch = new OakNamespaceSwitch(jcrOldNamespace, jcrNewNamespace);

        when(jcrNode.getNodeType()).thenReturn(jcrNodePrimaryType);
    }

    @Test
    public void shouldChangeJcrNodePrimaryTypeIfDoesBelongToJcrOldNamespace() throws Exception {
        when(jcrNodePrimaryType.belongsTo(jcrOldNamespace)).thenReturn(true);

        oakNamespaceSwitch.update(jcrNode);

        verify(jcrNode).changeNodeType(jcrNewNamespace);
    }

    @Test
    public void shouldNotChangeJcrNodePrimaryTypeIfDoesNotBelongToJcrOldNamespace() throws Exception {
        when(jcrNodePrimaryType.belongsTo(jcrOldNamespace)).thenReturn(false);

        oakNamespaceSwitch.update(jcrNode);

        verify(jcrNode, never()).changeNodeType(jcrNewNamespace);
    }

    @Test
    public void shouldRenameJcrNodeMixinTypeIfDoesBelongToJcrOldNamespace() throws Exception {
        JcrNodeType jcrMixinNodeType = mock(JcrNodeType.class, "mixinType");
        List<JcrNodeType> jcrMixinNodeTypes = Collections.singletonList(jcrMixinNodeType);
        when(jcrNode.getMixinNodeTypes()).thenReturn(jcrMixinNodeTypes);
        when(jcrMixinNodeType.belongsTo(jcrOldNamespace)).thenReturn(true);

        oakNamespaceSwitch.update(jcrNode);

        verify(jcrNode).renameMixin(jcrMixinNodeType, jcrNewNamespace);
    }

    @Test
    public void shouldNotRenameJcrNodeMixinTypeIfDoesNotBelongToJcrOldNamespace() throws Exception {
        JcrNodeType jcrMixinNodeType = mock(JcrNodeType.class, "mixinType");
        List<JcrNodeType> jcrMixinNodeTypes = Collections.singletonList(jcrMixinNodeType);
        when(jcrNode.getMixinNodeTypes()).thenReturn(jcrMixinNodeTypes);
        when(jcrMixinNodeType.belongsTo(jcrOldNamespace)).thenReturn(false);

        oakNamespaceSwitch.update(jcrNode);

        verify(jcrNode, never()).renameMixin(jcrMixinNodeType, jcrNewNamespace);
    }

    @Test
    public void shouldRenameNodeNameIfDoesBelongToJcrOldNamespace() throws Exception {
        when(jcrNode.checkNodeNameBelongsTo(jcrOldNamespace)).thenReturn(true);

        oakNamespaceSwitch.update(jcrNode);

        verify(jcrNode).rename(jcrNewNamespace);
    }

    @Test
    public void shouldNotRenameNodeNameIfDoesNotBelongToJcrOldNamespace() throws Exception {
        when(jcrNode.checkNodeNameBelongsTo(jcrOldNamespace)).thenReturn(false);

        oakNamespaceSwitch.update(jcrNode);

        verify(jcrNode, never()).rename(jcrNewNamespace);
    }

    @Test
    public void shouldRenameJcrNodePropertyNameIfDoesBelongToJcrOldNamespace() throws Exception {
        List<JcrProperty> jcrProperties = Collections.singletonList(jcrProperty);
        when(jcrNode.getProperties()).thenReturn(jcrProperties);
        JcrPropertyType jcrPropertyType = mock(JcrPropertyType.class);
        when(jcrProperty.getPropertyType()).thenReturn(jcrPropertyType);
        when(jcrProperty.belongsTo(jcrOldNamespace)).thenReturn(true);

        oakNamespaceSwitch.update(jcrNode);

        verify(jcrNode).renameProperty(jcrProperty, jcrNewNamespace);
    }

    @Test
    public void shouldNotRenameJcrNodePropertyNameIfDoesNotBelongToJcrOldNamespace() throws Exception {
        List<JcrProperty> jcrProperties = Collections.singletonList(jcrProperty);
        when(jcrNode.getProperties()).thenReturn(jcrProperties);
        JcrPropertyType jcrPropertyType = mock(JcrPropertyType.class);
        when(jcrProperty.getPropertyType()).thenReturn(jcrPropertyType);
        when(jcrProperty.belongsTo(jcrOldNamespace)).thenReturn(false);

        oakNamespaceSwitch.update(jcrNode);

        verify(jcrNode, never()).renameProperty(jcrProperty, jcrNewNamespace);
    }

    @Test
    public void shouldRenameJcrNodePropertyValueNamesIfPropertyTypeIsName() throws Exception {
        List<JcrProperty> jcrProperties = Collections.singletonList(jcrProperty);
        when(jcrNode.getProperties()).thenReturn(jcrProperties);
        JcrPropertyType jcrPropertyType = new OakPropertyType(PropertyType.NAME);
        when(jcrProperty.getPropertyType()).thenReturn(jcrPropertyType);

        oakNamespaceSwitch.update(jcrNode);

        verify(jcrProperty).renameValueNames(jcrOldNamespace, jcrNewNamespace, jcrNode.getJcrValueFactory());
    }

    @Test
    public void shouldRenameJcrNodePropertyValueNamesIfPropertyTypeIsUri() throws Exception {
        List<JcrProperty> jcrProperties = Collections.singletonList(jcrProperty);
        when(jcrNode.getProperties()).thenReturn(jcrProperties);
        JcrPropertyType jcrPropertyType = new OakPropertyType(PropertyType.URI);
        when(jcrProperty.getPropertyType()).thenReturn(jcrPropertyType);

        oakNamespaceSwitch.update(jcrNode);

        verify(jcrProperty).renameValueNames(jcrOldNamespace, jcrNewNamespace, jcrNode.getJcrValueFactory());
    }

    @Test
    public void shouldNotRenameJcrNodePropertyValueNamesIfPropertyTypeIsUndefined() throws Exception {
        List<JcrProperty> jcrProperties = Collections.singletonList(jcrProperty);
        when(jcrNode.getProperties()).thenReturn(jcrProperties);
        JcrPropertyType jcrPropertyType = new OakPropertyType(PropertyType.UNDEFINED);
        when(jcrProperty.getPropertyType()).thenReturn(jcrPropertyType);

        oakNamespaceSwitch.update(jcrNode);

        verify(jcrProperty, never()).renameValueNames(jcrOldNamespace, jcrNewNamespace, jcrNode.getJcrValueFactory());
    }

    @Test(expected = JcrNamespaceSwitchException.class)
    public void shouldThrowJcrNamespaceSwitchExceptionOnRepositoryException() throws Exception {
        when(jcrNode.getMixinNodeTypes()).thenThrow(new RepositoryException());

        oakNamespaceSwitch.update(jcrNode);
    }

}