package com.kushi.baseConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kushi.utility.TestResultsCucumberUtility;


public class ExecutionHelper {

    private static final Logger LOG = LoggerFactory.getLogger(ExecutionHelper.class);
    TestResultsCucumberUtility testResultUtils = new TestResultsCucumberUtility();

    public void loadResources(String featureName, String browserName) throws Exception, Throwable {
        try {
            // Use SLF4J logger instead of JDK internal Log
            LOG.info("*********Before Cucumber*******");
            LOG.info("Browser Name: {}", browserName);
            LOG.info("Feature Name: {}", featureName);
            
            // Initialize test results and report
            TestResultsCucumberUtility.outputFolder(browserName);
            TestResultsCucumberUtility.extentReportInitialize(featureName);
            
            LOG.info("In before step definition for scenario::{}", "test");
        } catch (Exception exception) {
            LOG.error("Scenario::{} - Error in execution of before test. Exception: {}", "test", exception.getMessage(), exception);
        }
    }

    /**
     * Close.
     *
     * @throws Exception the exception
     */
    public void closeConnections() throws Exception {
        try {
            System.out.println("Closing the Browser");
           // LOG.info("Current scenario status is: {}", CucumberUtilityManager.getScenarioStatus());
            // Flush the extent report if needed
            // testResultUtils.extentReportFlush();
        } catch (Exception exception) {
            LOG.error("Error in After Block execution: {}", exception.getMessage(), exception);
            throw new Exception(exception);
        }
    }
}
