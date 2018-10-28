package it.mcella.jcr.oak.upgrade.apprun.secondversion.action;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class QuitTest {

    @Test
    public void shouldReturnQuitActionType() throws Exception {
        Quit quit = new Quit();
        assertThat(quit.getType(), is(ActionType.QUIT));
    }

}