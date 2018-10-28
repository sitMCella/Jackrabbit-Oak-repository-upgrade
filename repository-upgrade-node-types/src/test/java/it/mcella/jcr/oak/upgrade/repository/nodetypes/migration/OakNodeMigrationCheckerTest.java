package it.mcella.jcr.oak.upgrade.repository.nodetypes.migration;

import it.mcella.jcr.oak.upgrade.repository.JcrNamespace;
import it.mcella.jcr.oak.upgrade.repository.JcrNode;
import it.mcella.jcr.oak.upgrade.repository.JcrNodeType;
import it.mcella.jcr.oak.upgrade.repository.JcrProperty;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OakNodeMigrationCheckerTest {

    private final JcrNamespace oldNamespace = mock(JcrNamespace.class);
    private final JcrNode jcrNode = mock(JcrNode.class);
    private final JcrNodeType jcrPrimaryNodeType = mock(JcrNodeType.class, "primaryNodeType");
    private final JcrNodeType jcrMixinNodeType = mock(JcrNodeType.class, "mixinNodeType");
    private final JcrProperty jcrProperty = mock(JcrProperty.class);

    private OakNodeMigrationChecker oakNodeMigrationChecker;
    private List<JcrNodeType> jcrMixinNodeTypes;
    private List<JcrProperty> jcrProperties;

    @Before
    public void setUp() throws Exception {
        oakNodeMigrationChecker = new OakNodeMigrationChecker();
        when(jcrNode.getNodeType()).thenReturn(jcrPrimaryNodeType);
        jcrMixinNodeTypes = Collections.singletonList(jcrMixinNodeType);
        when(jcrNode.getMixinNodeTypes()).thenReturn(jcrMixinNodeTypes);
        jcrProperties = Collections.singletonList(jcrProperty);
        when(jcrNode.getProperties()).thenReturn(jcrProperties);
    }

    @Test
    public void shouldMigrationNotBeCompletedIfPrimaryNodeTypeBelongsToOldNamespace() throws Exception {
        when(jcrPrimaryNodeType.belongsTo(oldNamespace)).thenReturn(true);
        when(jcrMixinNodeType.belongsTo(oldNamespace)).thenReturn(false);
        when(jcrNode.checkNodeNameBelongsTo(oldNamespace)).thenReturn(false);
        when(jcrProperty.belongsTo(oldNamespace)).thenReturn(false);

        boolean isMigrationCompleted = oakNodeMigrationChecker.check(jcrNode, oldNamespace);

        assertFalse(isMigrationCompleted);
    }

    @Test
    public void shouldMigrationNotBeCompletedIfMixinNodeTypeBelongsToOldNamespace() throws Exception {
        when(jcrPrimaryNodeType.belongsTo(oldNamespace)).thenReturn(false);
        when(jcrMixinNodeType.belongsTo(oldNamespace)).thenReturn(true);
        when(jcrNode.checkNodeNameBelongsTo(oldNamespace)).thenReturn(false);
        when(jcrProperty.belongsTo(oldNamespace)).thenReturn(false);

        boolean isMigrationCompleted = oakNodeMigrationChecker.check(jcrNode, oldNamespace);

        assertFalse(isMigrationCompleted);
    }

    @Test
    public void shouldMigrationNotBeCompletedIfNodeNameBelongsToOldNamespace() throws Exception {
        when(jcrPrimaryNodeType.belongsTo(oldNamespace)).thenReturn(false);
        when(jcrMixinNodeType.belongsTo(oldNamespace)).thenReturn(false);
        when(jcrNode.checkNodeNameBelongsTo(oldNamespace)).thenReturn(true);
        when(jcrProperty.belongsTo(oldNamespace)).thenReturn(false);

        boolean isMigrationCompleted = oakNodeMigrationChecker.check(jcrNode, oldNamespace);

        assertFalse(isMigrationCompleted);
    }

    @Test
    public void shouldMigrationNotBeCompletedIfPropertyBelongsToOldNamespace() throws Exception {
        when(jcrPrimaryNodeType.belongsTo(oldNamespace)).thenReturn(false);
        when(jcrMixinNodeType.belongsTo(oldNamespace)).thenReturn(false);
        when(jcrNode.checkNodeNameBelongsTo(oldNamespace)).thenReturn(false);
        when(jcrProperty.belongsTo(oldNamespace)).thenReturn(true);

        boolean isMigrationCompleted = oakNodeMigrationChecker.check(jcrNode, oldNamespace);

        assertFalse(isMigrationCompleted);
    }

    @Test
    public void shouldMigrationBeCompletedIfNoNamesBelongToOldNamespace() throws Exception {
        when(jcrPrimaryNodeType.belongsTo(oldNamespace)).thenReturn(false);
        when(jcrMixinNodeType.belongsTo(oldNamespace)).thenReturn(false);
        when(jcrNode.checkNodeNameBelongsTo(oldNamespace)).thenReturn(false);
        when(jcrProperty.belongsTo(oldNamespace)).thenReturn(false);

        boolean isMigrationCompleted = oakNodeMigrationChecker.check(jcrNode, oldNamespace);

        assertTrue(isMigrationCompleted);
    }

    @Test
    public void shouldReturnTrueIfJcrPrimaryNodeTypeDoesNotBelongToOldNamespace() throws Exception {
        when(jcrPrimaryNodeType.belongsTo(oldNamespace)).thenReturn(false);

        boolean isPrimaryNodeNameMigrated = oakNodeMigrationChecker.checkNodePrimaryType(jcrNode, oldNamespace);

        assertTrue(isPrimaryNodeNameMigrated);
    }

    @Test
    public void shouldReturnFalseIfJcrPrimaryNodeTypeBelongsToOldNamespace() throws Exception {
        when(jcrPrimaryNodeType.belongsTo(oldNamespace)).thenReturn(true);

        boolean isPrimaryNodeNameMigrated = oakNodeMigrationChecker.checkNodePrimaryType(jcrNode, oldNamespace);

        assertFalse(isPrimaryNodeNameMigrated);
    }

    @Test
    public void shouldReturnTrueIfJcrMixinNodeTypeDoesNotBelongToOldNamespace() throws Exception {
        when(jcrMixinNodeType.belongsTo(oldNamespace)).thenReturn(false);

        boolean isMixinNodeNameMigrated = oakNodeMigrationChecker.checkMixinNodeType(jcrMixinNodeType, jcrNode, oldNamespace);

        assertTrue(isMixinNodeNameMigrated);
    }

    @Test
    public void shouldReturnFalseIfJcrMixinNodeTypeBelongsToOldNamespace() throws Exception {
        when(jcrMixinNodeType.belongsTo(oldNamespace)).thenReturn(true);

        boolean isMixinNodeNameMigrated = oakNodeMigrationChecker.checkMixinNodeType(jcrMixinNodeType, jcrNode, oldNamespace);

        assertFalse(isMixinNodeNameMigrated);
    }

    @Test
    public void shouldReturnTrueIfJcrNodeNameDoesNotBelongToOldNamespace() throws Exception {
        when(jcrNode.checkNodeNameBelongsTo(oldNamespace)).thenReturn(false);

        boolean isNodeNameMigrated = oakNodeMigrationChecker.checkNodeName(jcrNode, oldNamespace);

        assertTrue(isNodeNameMigrated);
    }

    @Test
    public void shouldReturnFalseIfJcrNodeNameBelongsToOldNamespace() throws Exception {
        when(jcrNode.checkNodeNameBelongsTo(oldNamespace)).thenReturn(true);

        boolean isNodeNameMigrated = oakNodeMigrationChecker.checkNodeName(jcrNode, oldNamespace);

        assertFalse(isNodeNameMigrated);
    }

    @Test
    public void shouldReturnTrueIfJcrPropertyDoesNotBelongToOldNamespace() throws Exception {
        when(jcrProperty.belongsTo(oldNamespace)).thenReturn(false);

        boolean isPropertyNameMigrated = oakNodeMigrationChecker.checkPropertyName(jcrProperty, jcrNode, oldNamespace);

        assertTrue(isPropertyNameMigrated);
    }

    @Test
    public void shouldReturnFalseIfJcrPropertyBelongsToOldNamespace() throws Exception {
        when(jcrProperty.belongsTo(oldNamespace)).thenReturn(true);

        boolean isPropertyNameMigrated = oakNodeMigrationChecker.checkPropertyName(jcrProperty, jcrNode, oldNamespace);

        assertFalse(isPropertyNameMigrated);
    }

}
