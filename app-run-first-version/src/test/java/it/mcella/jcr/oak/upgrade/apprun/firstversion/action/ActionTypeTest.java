package it.mcella.jcr.oak.upgrade.apprun.firstversion.action;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ActionTypeTest {

    @Test
    public void shouldGetQuitActionTypeFromName() throws Exception {
        ActionType actionType = ActionType.from(ActionType.QUIT.getName());

        assertThat(actionType, is(ActionType.QUIT));
    }

    @Test
    public void shouldGetHelpActionTypeFromName() throws Exception {
        ActionType actionType = ActionType.from(ActionType.HELP.getName());

        assertThat(actionType, is(ActionType.HELP));
    }

    @Test
    public void shouldGetSetupActionTypeFromName() throws Exception {
        ActionType actionType = ActionType.from(ActionType.SETUP.getName());

        assertThat(actionType, is(ActionType.SETUP));
    }

    @Test
    public void shouldGetListActionTypeFromName() throws Exception {
        ActionType actionType = ActionType.from(ActionType.LIST.getName());

        assertThat(actionType, is(ActionType.LIST));
    }

    @Test
    public void shouldGetCreateFolderActionTypeFromName() throws Exception {
        ActionType actionType = ActionType.from(ActionType.CREATE_FOLDER.getName());

        assertThat(actionType, is(ActionType.CREATE_FOLDER));
    }

    @Test
    public void shouldGetCreateFileActionTypeFromName() throws Exception {
        ActionType actionType = ActionType.from(ActionType.CREATE_FILE.getName());

        assertThat(actionType, is(ActionType.CREATE_FILE));
    }

    @Test
    public void shouldGetCreateFileVersionActionTypeFromName() throws Exception {
        ActionType actionType = ActionType.from(ActionType.CREATE_FILE_VERSION.getName());

        assertThat(actionType, is(ActionType.CREATE_FILE_VERSION));
    }

    @Test
    public void shouldGetRetrieveFileActionTypeFromName() throws Exception {
        ActionType actionType = ActionType.from(ActionType.RETRIEVE_FILE.getName());

        assertThat(actionType, is(ActionType.RETRIEVE_FILE));
    }

}