package com.automationexercise.utils.dataReader;

import com.automationexercise.utils.logs.LogsManager;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Collection;
import java.util.Properties;

public class PropertyReader {
    public static Properties loadProperties() {

        try {
            // Stores all key-value pairs loaded from properties files
            Properties properties = new Properties();

            // Find all files with ".properties" extension recursively
            // under src/main/resources
            Collection<File> propertyFiles =
                    FileUtils.listFiles(
                            new File("src/main/resources"),
                            new String[]{"properties"},
                            true);

            // Iterate through each properties file found
            propertyFiles.forEach(file -> {

                try {

                    // Load key-value pairs from the current file
                    // into the Properties object
                    properties.load(FileUtils.openInputStream(file));

                } catch (Exception e) {
                    LogsManager.error("Error loading properties from file: ", file.getName(), " - ", e.getMessage());
                }

                /*
                 * Merge all JVM System Properties into the local properties object.
                 *
                 * If the same key exists in both places, the System Property value
                 * overrides the value loaded from the .properties file.
                 *
                 * Example:
                 * File property:     browser=chrome
                 * JVM property:      browser=firefox
                 *
                 * Result in properties:
                 * browser=firefox
                 */
                properties.putAll(System.getProperties());

                /*
                 * Copies all JVM System Properties into the properties object.
                 * Copy all loaded properties into JVM System Properties.
                 *
                 * This allows retrieving values later using:
                 * System.getProperty("key")
                 *
                 * Example:
                 * browser=chrome
                 *
                 * becomes available as:
                 * System.getProperty("browser")
                 */
                System.getProperties().putAll(properties);
            });

            // Return all loaded properties
            return properties;

        } catch (Exception e) {
            LogsManager.error("Error loading properties: ", e.getMessage());
            return null;
        }
    }

    /**
     * Retrieves a property value from JVM System Properties.
     *
     * Example:
     * String browser = PropertyReader.getProperty("browser");
     *
     * @param key Property name
     * @return Property value, or null if not found or an error occurs
     */
    public static String getProperty(String key) {

        try {
            // Read property from JVM System Properties
            return System.getProperty(key);
        } catch (Exception e) {
            // Log retrieval errors
            LogsManager.error("Error retrieving property: ", key, " - ", e.getMessage());
            return "";
        }
    }
}

