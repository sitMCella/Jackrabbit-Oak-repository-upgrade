package it.mcella.jcr.oak.upgrade.repository.secondversion.node.root;

import it.mcella.jcr.oak.upgrade.repository.JcrNode;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class OakRootNodeFactoryTest {

    @Test
    public void shouldCreateOakFolderNodeFromJcrNode() throws Exception {
        JcrNode jcrNode = null;
        JcrRootNodeFactory oakRootNodeFactory = new OakRootNodeFactory();

        JcrRootNode jcrRootNode = oakRootNodeFactory.createFrom(jcrNode);

        assertNotNull(jcrRootNode);
    }

}