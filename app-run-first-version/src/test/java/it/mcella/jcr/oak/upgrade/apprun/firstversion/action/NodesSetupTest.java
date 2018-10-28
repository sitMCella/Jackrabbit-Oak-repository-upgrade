package it.mcella.jcr.oak.upgrade.apprun.firstversion.action;

import it.mcella.jcr.oak.upgrade.repository.JcrRepository;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class NodesSetupTest {

    @Test
    public void shouldReturnNodesSetupActionType() throws Exception {
        JcrRepository jcrRepository = null;
        NodesSetup nodesSetup = new NodesSetup(jcrRepository);
        assertThat(nodesSetup.getType(), is(ActionType.SETUP));
    }

}