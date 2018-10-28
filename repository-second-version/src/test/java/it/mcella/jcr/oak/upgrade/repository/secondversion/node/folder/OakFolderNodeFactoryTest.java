package it.mcella.jcr.oak.upgrade.repository.secondversion.node.folder;

import it.mcella.jcr.oak.upgrade.repository.JcrNode;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class OakFolderNodeFactoryTest {

    @Test
    public void shouldCreateOakFolderNodeFromJcrNode() throws Exception {
        JcrNode jcrNode = null;
        OakFolderNodeFactory oakFolderNodeFactory = new OakFolderNodeFactory();

        JcrFolderNode jcrFolderNode = oakFolderNodeFactory.createFrom(jcrNode);

        assertNotNull(jcrFolderNode);
    }

}
