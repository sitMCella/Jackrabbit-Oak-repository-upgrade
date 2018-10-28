package it.mcella.jcr.oak.upgrade.apprun.firstversion.action;

public class Quit implements Action {

    @Override
    public ActionType getType() {
        return ActionType.QUIT;
    }

    @Override
    public void execute() throws ActionException {
        // do nothing
    }

}
