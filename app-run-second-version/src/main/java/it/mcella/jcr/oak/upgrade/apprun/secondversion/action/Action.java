package it.mcella.jcr.oak.upgrade.apprun.secondversion.action;

public interface Action {

    ActionType getType();

    void execute() throws ActionException;

}
