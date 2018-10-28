package it.mcella.jcr.oak.upgrade.repository;

import javax.jcr.SimpleCredentials;

public class AdministratorCredentials {

    private static final String USER_ID = "admin";
    private static final String PASSWORD = "admin";

    public static SimpleCredentials create() {
        return new SimpleCredentials(USER_ID, PASSWORD.toCharArray());
    }

}
