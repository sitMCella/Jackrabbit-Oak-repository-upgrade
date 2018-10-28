package it.mcella.jcr.oak.upgrade.apprun.firstversion.action;

import it.mcella.jcr.oak.upgrade.commons.ConsoleWriter;

public class Help implements Action {

    private final ConsoleWriter consoleWriter;

    public Help(ConsoleWriter consoleWriter) {
        this.consoleWriter = consoleWriter;
    }

    @Override
    public ActionType getType() {
        return ActionType.HELP;
    }

    @Override
    public void execute() throws ActionException {
        consoleWriter.println("\nActions:");
        for (ActionType actionType : ActionType.values()) {
            if (actionType == ActionType.NONE) {
                continue;
            }
            consoleWriter.println(actionType.getName() + actionType.getDescription());
        }
    }

}
