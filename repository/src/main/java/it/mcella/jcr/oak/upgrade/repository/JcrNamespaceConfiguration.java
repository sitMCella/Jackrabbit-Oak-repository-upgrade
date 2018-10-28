package it.mcella.jcr.oak.upgrade.repository;

import java.io.InputStream;

public class JcrNamespaceConfiguration {

    private final String namespaceConfigurationFileName;

    public JcrNamespaceConfiguration(String namespaceConfigurationFileName) {
        this.namespaceConfigurationFileName = namespaceConfigurationFileName;
    }

    public InputStream getInputStream() {
        return getClass().getClassLoader().getResourceAsStream(namespaceConfigurationFileName);
    }

    @Override
    public String toString() {
        return "JcrNamespaceConfiguration{" +
                "namespaceConfigurationFileName='" + namespaceConfigurationFileName + '\'' +
                '}';
    }

}
