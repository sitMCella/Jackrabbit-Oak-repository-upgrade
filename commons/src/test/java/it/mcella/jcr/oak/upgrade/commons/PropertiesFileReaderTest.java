package it.mcella.jcr.oak.upgrade.commons;

import org.junit.Test;

import java.io.FileNotFoundException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PropertiesFileReaderTest {

    @Test
    public void shouldReadPropertyFromFile() throws Exception {
        String propertiesFileName = "test.properties";
        PropertiesFileReader propertiesFileReader = new PropertiesFileReader(propertiesFileName);
        String propertyName = "property";

        String value = propertiesFileReader.readProperty(propertyName);

        assertThat(value, is("value"));
    }

    @Test(expected = FileNotFoundException.class)
    public void shouldThrowFileNotFoundExceptionIfPropertiesFileDoesNotExist() throws Exception {
        String propertiesFileName = "settings.properties";
        PropertiesFileReader propertiesFileReader = new PropertiesFileReader(propertiesFileName);
        String propertyName = "property";

        propertiesFileReader.readProperty(propertyName);
    }

    @Test(expected = PropertiesFileReaderException.class)
    public void shouldThrowPropertiesFileReaderExceptionIfPropertyDoesNotExist() throws Exception {
        String propertiesFileName = "test.properties";
        PropertiesFileReader propertiesFileReader = new PropertiesFileReader(propertiesFileName);
        String propertyName = "nonExistentProperty";

        propertiesFileReader.readProperty(propertyName);
    }

}