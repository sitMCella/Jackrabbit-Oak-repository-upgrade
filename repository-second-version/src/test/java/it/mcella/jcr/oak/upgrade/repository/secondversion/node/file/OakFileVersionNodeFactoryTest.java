package it.mcella.jcr.oak.upgrade.repository.secondversion.node.file;

import it.mcella.jcr.oak.upgrade.repository.JcrNode;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class OakFileVersionNodeFactoryTest {

    @Test
    public void shouldCreateOakFileVersionNodeFromJcrNode() throws Exception {
        JcrNode jcrNode = null;
        OakFileVersionNodeFactory oakFileVersionNodeFactory = new OakFileVersionNodeFactory();

        JcrFileVersionNode jcrFileVersionNode = oakFileVersionNodeFactory.createFrom(jcrNode);

        assertNotNull(jcrFileVersionNode);
    }

}
