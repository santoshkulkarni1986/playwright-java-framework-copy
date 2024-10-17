package com.kushi.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class CucumberUtilityManager {

    private static final Logger LOG = LoggerFactory.getLogger(CucumberUtilityManager.class);

    /** The testdatahashmap. */
    private static ThreadLocal<HashMap<String, String>> testdatahashmap = new ThreadLocal<>();
    /** The scenarioname. */
    private static ThreadLocal<String> scenarioname = new ThreadLocal<>();
    /** The ExtentReportPath. */
    private static ThreadLocal<String> extentReportPath = new ThreadLocal<>();
    /** The scenarioDescription. */
    private static ThreadLocal<String> scenariodescription = new ThreadLocal<>();
    private static ThreadLocal<HashMap<String, String>> resultSetOrderID = new ThreadLocal<>();
    private static ThreadLocal<String> endToEndScenario = new ThreadLocal<>();
    
    // New additions
    private static ThreadLocal<String> featureName = new ThreadLocal<>();
    private static ThreadLocal<String> scenarioStatus = new ThreadLocal<>();
    private static ThreadLocal<String> stepStatus = new ThreadLocal<>();
    
    // New additions for author and tags
    private static ThreadLocal<String> author = new ThreadLocal<>();
    private static ThreadLocal<String[]> tags = new ThreadLocal<>();

    // Additional methods for scenario and feature names
    public static synchronized void setFeatureName(String feature) {
        featureName.set(feature);
        LOG.info("Feature name set: {}", feature);
    }

    public static synchronized String getFeatureName() {
        String feature = featureName.get();
        LOG.info("Retrieved feature name: {}", feature);
        return feature;
    }

    public static synchronized void setScenarioStatus(String status) {
        scenarioStatus.set(status);
        LOG.info("Scenario status set: {}", status);
    }

    public static synchronized String getScenarioStatus() {
        String status = scenarioStatus.get();
        LOG.info("Retrieved scenario status: {}", status);
        return status;
    }

    public static synchronized void setStepStatus(String status) {
        stepStatus.set(status);
        LOG.info("Step status set: {}", status);
    }

    public static synchronized String getStepStatus() {
        String status = stepStatus.get();
        LOG.info("Retrieved step status: {}", status);
        return status;
    }

    public static HashMap<String, String> getResultSetOrderID() {
        LOG.info("Retrieved result set order ID.");
        return resultSetOrderID.get();
    }

    public static void setResultSetOrderID(HashMap<String, String> resultSet) {
        resultSetOrderID.set(resultSet);
        LOG.info("Result set order ID set.");
    }

    public static String getEndToEndScenario() {
        String scenario = endToEndScenario.get();
        LOG.info("Retrieved end-to-end scenario: {}", scenario);
        return scenario;
    }

    public static void setEndToEndScenario(String scenario) {
        endToEndScenario.set(scenario);
        LOG.info("End-to-end scenario set: {}", scenario);
    }

    public static void setScenarioName(String scenario) {
        scenarioname.set(scenario);
        LOG.info("Scenario name set: {}", scenario);
    }

    public static String getScenarioName() {
        String scenario = scenarioname.get();
        LOG.info("Retrieved scenario name: {}", scenario);
        return scenario;
    }

    public static void setScenarioDescription(String description) {
        scenariodescription.set(description);
        LOG.info("Scenario description set: {}", description);
    }

    public static String getScenarioDescription() {
        String description = scenariodescription.get();
        LOG.info("Retrieved scenario description: {}", description);
        return description;
    }

    public static void setExtentReportPath(String path) {
        extentReportPath.set(path);
        LOG.info("Extent report path set: {}", path);
    }

    public static String getExtentReportPath() {
        String path = extentReportPath.get();
        LOG.info("Retrieved extent report path: {}", path);
        return path;
    }

    public static synchronized HashMap<String, String> getTestDataHashMap() {
        LOG.info("Retrieved test data hash map.");
        return testdatahashmap.get();
    }

    public static synchronized void setTestDataHashMap(HashMap<String, String> hashMap) {
        testdatahashmap.set(hashMap);
        LOG.info("Test data hash map set.");
    }

    public static synchronized void cleanTestDataHashMap() {
        LOG.info("Cleaning up test data and scenario-related information.");
        testdatahashmap.remove();
        scenarioname.remove();
        scenariodescription.remove();
        extentReportPath.remove();
        featureName.remove();
        scenarioStatus.remove();
        stepStatus.remove();
        author.remove();
        tags.remove();
        LOG.info("Test data and scenario-related information cleaned up.");
    }

    // Methods for author and tags
    public static synchronized void setAuthor(String testAuthor) {
        author.set(testAuthor);
        LOG.info("Author set: {}", testAuthor);
    }

    public static synchronized String getAuthor() {
        String testAuthor = author.get();
        LOG.info("Retrieved author: {}", testAuthor);
        return testAuthor;
    }

    public static synchronized void setTags(String... testTags) {
        tags.set(testTags);
        LOG.info("Tags set: {}", (Object) testTags);
    }

    public static synchronized String[] getTags() {
        String[] testTags = tags.get();
        LOG.info("Retrieved tags: {}", (Object) testTags);
        return testTags;
    }
}
