package it.mcella.jcr.oak.upgrade.repository.firstversion.node.file;

import it.mcella.jcr.oak.upgrade.repository.JcrNode;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class OakFileNodeFactoryTest {

    @Test
    public void shouldCreateOakFileNodeFromJcrNode() throws Exception {
        JcrNode jcrNode = null;
        OakFileNodeFactory oakFileNodeFactory = new OakFileNodeFactory();

        JcrFileNode jcrFileNode = oakFileNodeFactory.createFrom(jcrNode);

        assertNotNull(jcrFileNode);
    }

}
