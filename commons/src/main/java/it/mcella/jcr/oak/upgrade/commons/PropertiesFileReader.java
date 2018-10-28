package it.mcella.jcr.oak.upgrade.commons;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesFileReader {

    private final Properties properties;

    public PropertiesFileReader(String propertiesFileName) throws Exception {
        this.properties = new Properties();
        fillProperties(properties, propertiesFileName);
    }

    public String readProperty(String key) throws PropertiesFileReaderException {
        if (properties == null) {
            throw new PropertiesFileReaderException("Cannot read properties file");
        }
        String property = properties.getProperty(key);
        if (property == null) {
            throw new PropertiesFileReaderException(String.format("Cannot retrieve property \"%s\"", key));
        }
        return property;
    }

    private Properties fillProperties(Properties properties, String propertiesFileName) throws Exception {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propertiesFileName)) {
            if (inputStream != null) {
                properties.load(inputStream);
                return properties;
            }
            String errorMessage = String.format("Property file \"%s\" not found in the classpath", propertiesFileName);
            throw new FileNotFoundException(errorMessage);
        }
    }

}
