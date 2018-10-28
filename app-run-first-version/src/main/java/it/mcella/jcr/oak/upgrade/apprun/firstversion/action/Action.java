package it.mcella.jcr.oak.upgrade.apprun.firstversion.action;

public interface Action {

    ActionType getType();

    void execute() throws ActionException;

}
