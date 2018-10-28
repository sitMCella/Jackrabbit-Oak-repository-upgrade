package it.mcella.jcr.oak.upgrade.apprun.secondversion.action;

public enum ActionType {
    NONE("none", ""),
    QUIT("quit", "\t\t\tapplication exit"),
    HELP("help", "\t\t\tprint this help"),
    SETUP("setup", "\t\t\tinitialize the repository with a predefined node structure"),
    LIST("list", "\t\t\tlist all repository nodes and versions"),
    CREATE_FOLDER("create-folder", "\t\tcreate folder"),
    CREATE_FILE("create-file", "\t\tcreate file"),
    CREATE_FILE_VERSION("create-file-version", "\tcreate file version"),
    RETRIEVE_FILE("retrieve-file", "\t\tretrieve file or file version content");

    private final String name;
    private final String description;

    private ActionType(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public static ActionType from(String actionName) {
        for (ActionType actionType : values()) {
            if (actionType.getName().equals(actionName)) {
                return actionType;
            }
        }
        return ActionType.NONE;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

}
