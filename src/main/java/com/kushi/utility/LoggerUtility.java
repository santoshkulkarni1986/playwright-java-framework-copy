package com.kushi.utility;

import java.io.File;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LoggerUtility {

    private static boolean isConfigured = false;

    public static void configureLogger(String configFilePath) {
        if (!isConfigured) {
            File configFile = new File(configFilePath);
            if (configFile.exists()) {
                PropertyConfigurator.configure(configFilePath);
                isConfigured = true;
            } else {
                System.err.println("Log4j configuration file not found: " + configFilePath);
            }
        }
    }

    public static Logger getLogger(Class<?> clazz) {
        return Logger.getLogger(clazz);
    }
}
