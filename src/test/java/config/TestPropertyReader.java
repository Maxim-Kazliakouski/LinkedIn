package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestPropertyReader {
    private static String configPropertiesPath = "/config.properties";
    private static volatile Properties properties;
    private static InputStream inputStream;

    private TestPropertyReader() {
    }

    private static String getCorrectPath() {
        if (configPropertiesPath.charAt(0) != '/')
            configPropertiesPath = "/" + configPropertiesPath;
        return configPropertiesPath;
    }

    public static Properties readProperties() {
        properties = new Properties();
        try {
            inputStream = TestPropertyReader.class.getResourceAsStream(getCorrectPath());
            if (inputStream != null)
                properties.load(inputStream);
        } catch (Exception ex) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (properties.getProperty("config_file") != null) {
            Properties additionalProperties = getProperties(properties.getProperty("config_file"));
            properties.putAll(additionalProperties);
        }
        return properties;
    }

    private static Properties loadProperties() {
        return properties != null ? properties : readProperties();
    }

    public static Properties getProperties(String path) {
        configPropertiesPath = path;
        return readProperties();
    }

    public static String getProperty(String propertyName) {
        return loadProperties().getProperty(propertyName);
    }
}