package it.mcella.jcr.oak.upgrade.repository;

import org.junit.Test;

import javax.jcr.SimpleCredentials;

import static org.junit.Assert.assertNotNull;

public class AdministratorCredentialsTest {

    @Test
    public void shouldCreateAdministratorCredentials() throws Exception {
        SimpleCredentials simpleCredentials = AdministratorCredentials.create();

        assertNotNull(simpleCredentials);
    }

}