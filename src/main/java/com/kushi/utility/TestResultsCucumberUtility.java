package com.kushi.utility;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.gherkin.model.Feature;
import com.aventstack.extentreports.gherkin.model.IGherkinFormatterModel;
import com.aventstack.extentreports.gherkin.model.Scenario;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.reporter.configuration.ViewName;
import com.kushi.baseConfig.*;
import com.microsoft.playwright.Page;

public class TestResultsCucumberUtility {

	private static final Logger LOG = LoggerFactory.getLogger(TestResultsCucumberUtility.class);
	public static String logDirectory;
	/** The zapi directory. */
	public static String logFilePath;
	public static String baseProjectPath = System.getProperty(Constants.USER_DIR);
	private static ThreadLocal<String> extentReportDirectory = new ThreadLocal<>();
	private static ThreadLocal<ExtentReports> extent = new ThreadLocal<>();
	private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
	private static ThreadLocal<ExtentTest> featureTest = new ThreadLocal<>();
	private static ThreadLocal<ExtentTest> scenarioTest = new ThreadLocal<>();
	private static ThreadLocal<ExtentTest> stepTest = new ThreadLocal<>();
	private static ThreadLocal<ExtentTest> stepNode = new ThreadLocal<>();


	public static void outputFolder(String browserName) throws Exception {
		try {
			// Define the ExtentReports folder in the root directory
			String extentReportsRoot = baseProjectPath.concat(File.separator).concat("ExtentReports");

			// Delete the folder if it already exists
			File extentReportsFolder = new File(extentReportsRoot);
			if (extentReportsFolder.exists()) {
				FileUtils.deleteDirectory(extentReportsFolder);
				LOG.info("Existing ExtentReports folder deleted: {}", extentReportsRoot);
			}

			// Recreate the folder structure
			DirFileUtils.createDirTree(extentReportsRoot);

			Date curDate = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String dateToStr = format.format(curDate);
			format = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
			dateToStr = format.format(curDate);

			String executionStartDate = dateToStr.substring(0, 10);
			String executionStartTime = dateToStr.substring(11, 19).replace(' ', '-');

			String browserDirectory = extentReportsRoot.concat(File.separator).concat(browserName.toLowerCase());
			DirFileUtils.createDirTree(browserDirectory);

			String dateDirectory = browserDirectory.concat(File.separator).concat(executionStartDate);
			DirFileUtils.createDirTree(dateDirectory);

			String timestampDirectory = dateDirectory.concat(File.separator).concat(executionStartTime);
			DirFileUtils.createDirTree(timestampDirectory);

			String reportDirectory = timestampDirectory.concat(File.separator).concat("ExtentReport");
			DirFileUtils.createDirTree(reportDirectory);
			extentReportDirectory.set(reportDirectory);

		} catch (Exception exception) {
			LOG.error("Error occurred while creating files and directories", exception);
			throw new Exception(exception);
		}
	}

	public static synchronized void extentReportInitialize(String featureName) {
		try {
			String extentReportLocation = extentReportDirectory.get().concat(File.separator).concat(featureName)
					.concat(" - ExtentReport.html");
			ExtentSparkReporter sparkReporter = new ExtentSparkReporter(extentReportLocation);
			sparkReporter.config().setTheme(Theme.DARK);
			sparkReporter.config().setDocumentTitle("Automation Report");
			sparkReporter.config().setReportName("Test Report Using Browser: " + PageConfig.getBrowserName());
			sparkReporter
			.viewConfigurer().viewOrder().as(new ViewName[] { ViewName.DASHBOARD, ViewName.TEST,
					ViewName.CATEGORY, ViewName.EXCEPTION, ViewName.AUTHOR, ViewName.DEVICE, ViewName.LOG })
			.apply();

			ExtentReports reports = new ExtentReports();
			reports.setSystemInfo("Operating System", System.getProperty("os.name"));
			reports.setSystemInfo("Java Version", System.getProperty("java.version"));
			reports.setSystemInfo("Browser", PageConfig.getBrowserName());
			reports.setSystemInfo("Author Name", "Santosh Kulkarni");
			reports.setSystemInfo("Project Name", "MyProject");
			reports.setSystemInfo("Project Version", "1.0.0");
			reports.setSystemInfo("Build Number", "1234");
			reports.setSystemInfo("Environment", "Staging");
			reports.attachReporter(sparkReporter);

			extent.set(reports);
			// featureTest.set(reports.createTest((Class<? extends IGherkinFormatterModel>) Feature.class, featureName));

			System.out.println("Santosh");
		} catch (Exception e) {
			LOG.error("Error initializing extent report", e);
		}
	}

	public static synchronized void startFeature(String featureName,String scenarioTags) {
		try {
			ExtentTest feature = extent.get().createTest(Feature.class, featureName).assignCategory(scenarioTags).assignAuthor("Santosh Kulkarni");
			featureTest.set(feature);
			// Set loggers and extent test
	            LOG.info("Starting test: " + featureName);
	            LOG.info("Starting test: " + featureName + " in " + PageConfig.getBrowserName());

	            // Pass the @Given node to WrapperUtility and DriverConfig
	            PlaywrightUtils.setExtentTest(feature);
	            PageConfig.setLogger(feature);
			LOG.info("Feature started: {}", featureName);
		} catch (Exception e) {
			LOG.error("Error starting feature", e);
		}
	}
	public static void startScenario(String scenarioName) {
		try {
			ExtentTest feature = featureTest.get();
			if (feature != null) {
				// Create Scenario node
				scenarioTest.set(feature.createNode((Class<? extends IGherkinFormatterModel>) Scenario.class, scenarioName));
				LOG.info("Started Scenario: {}", scenarioName);
			}
		} catch (Exception e) {
			LOG.error("Error starting scenario: {}", scenarioName, e);
		}
	}

	 // End the scenario
    public static synchronized void endScenario() {
        try {
            LOG.info("Scenario ended.");
            stepNode.remove();
        } catch (Exception e) {
            LOG.error("Error ending scenario", e);
        }
    }


    /**
     * Logs a new step node within the scenario with a description.
     * @param stepName Name of the step to be logged as a node
     * @param stepDescription Detailed description of the step being logged
     */
    public static void createStepNode(String stepName, String stepDescription) {
        try {
            ExtentTest scenario = scenarioTest.get();
            if (scenario != null) {
                // Create a node for each step with a description and store it in ThreadLocal
                stepNode.set(scenario.createNode(stepName).info(stepDescription));
                LOG.info("Started Step Node: {} - {}", stepName, stepDescription);
            }
        } catch (Exception e) {
            LOG.error("Error creating step node: {} - {}", stepName, stepDescription, e);
        }
    }


	// Mark step as Passed
	public static synchronized void markStepPassed(String stepDescription) {
		try {
			stepNode.get().log(Status.PASS, "Step Passed: " + stepDescription);
			LOG.info("Step Passed: {}", stepDescription);
		} catch (Exception e) {
			LOG.error("Error marking step as passed", e);
		}
	}

	public static synchronized void markStepFailed(String stepDescription, io.cucumber.java.Scenario scenario) throws IOException {
        try {
            if (stepNode.get() == null) {
                LOG.error("stepNode is null, cannot attach screenshot.");
                return;
            }

            // Get the Playwright page object
            Page page = PageConfig.getPage();

            // Define the screenshot path
            String screenshotPath = extentReportDirectory + File.separator + scenario.getName() + ".png";

            // Capture screenshot using Playwright and save it to the specified path
            page.screenshot(new Page.ScreenshotOptions()
                    .setPath(Paths.get(screenshotPath))
                    .setFullPage(true));

            // Create File object for the saved screenshot
            File screenshotFile = new File(screenshotPath);

            // Ensure screenshot file exists and is not empty
            if (!screenshotFile.exists() || screenshotFile.length() == 0) {
                LOG.error("Screenshot file does not exist or is empty at path: {}", screenshotPath);
                return;
            }

            // Attach screenshot to the extent report
            stepNode.get().log(Status.FAIL, "Step Failed: " + stepDescription)
                    .addScreenCaptureFromPath(screenshotPath);
            LOG.error("Step Failed: {}", stepDescription);

        } catch (Exception e) {
            LOG.error("Unexpected error occurred while capturing screenshot", e);
        }
    }
	// Mark step as Skipped
	public static synchronized void markStepSkipped(String stepDescription) {
		try {
			stepNode.get().log(Status.SKIP, "Step Skipped: " + stepDescription);
			LOG.warn("Step Skipped: {}", stepDescription);
		} catch (Exception e) {
			LOG.error("Error marking step as skipped", e);
		}
	}

	
	public static void flushReports() {
		try {
			ExtentReports reports = extent.get();
			if (reports != null) {
				reports.flush();
				LOG.info("Extent report flushed.");
			}
		} catch (Exception e) {
			LOG.error("Error flushing extent report", e);
		}
	}

	public static synchronized void logInfo(String message, Object... args) {
		try {
			ExtentTest extentTest = test.get();
			ExtentTest extentStepNode = stepNode.get();

			// Log to step node if present
			if (extentStepNode != null) {
				extentStepNode.log(Status.INFO, String.format(message, args));
			}
			// Log to main test node if present
			if (extentTest != null) {
				extentTest.log(Status.INFO, String.format(message, args));
			}
			LOG.info(message, args); // SLF4J log
		} catch (Exception e) {
			LOG.error("Error logging info: {}", message, e);
		}
	}

	public static synchronized void logInfo(String message, long elapsedTime) {
		try {
			ExtentTest extentTest = test.get();
			ExtentTest extentStepNode = stepNode.get();
			String logMessage = message + " (Time taken: " + elapsedTime + " ms)";

			// Log to step node if present
			if (extentStepNode != null) {
				extentStepNode.log(Status.INFO, logMessage);
			}
			// Log to main test node if present
			if (extentTest != null) {
				extentTest.log(Status.INFO, logMessage);
			}
			LOG.info(logMessage); // SLF4J log
		} catch (Exception e) {
			LOG.error("Error logging info", e);
		}
	}

	public static synchronized void logWarning(String message) {
		try {
			ExtentTest extentTest = test.get();
			ExtentTest extentStepNode = stepNode.get();

			// Log to step node if present
			if (extentStepNode != null) {
				extentStepNode.log(Status.WARNING, message);
			}
			// Log to main test node if present
			if (extentTest != null) {
				extentTest.log(Status.WARNING, message);
			}
			LOG.warn(message); // SLF4J log
		} catch (Exception e) {
			LOG.error("Error logging warning", e);
		}
	}

	public static synchronized void logSkip(String message) {
		try {
			ExtentTest extentTest = test.get();
			ExtentTest extentStepNode = stepNode.get();

			// Log to step node if present
			if (extentStepNode != null) {
				extentStepNode.log(Status.SKIP, message);
			}
			// Log to main test node if present
			if (extentTest != null) {
				extentTest.log(Status.SKIP, message);
			}
			LOG.warn(message); // SLF4J log
		} catch (Exception e) {
			LOG.error("Error logging skip", e);
		}
	}

	public static synchronized void logPass(String message) {
		try {
			ExtentTest extentTest = test.get();
			ExtentTest extentStepNode = stepNode.get();

			// Log to step node if present
			if (extentStepNode != null) {
				extentStepNode.log(Status.PASS, message);
			}
			// Log to main test node if present
			if (extentTest != null) {
				extentTest.log(Status.PASS, message);
			}
			LOG.info(message); // SLF4J log
		} catch (Exception e) {
			LOG.error("Error logging pass", e);
		}
	}
	
	public static synchronized void logFail(String message) {
		try {
			ExtentTest extentTest = test.get();
			ExtentTest extentStepNode = stepNode.get();

			// Log to step node if present
			if (extentStepNode != null) {
				extentStepNode.log(Status.PASS, message);
			}
			// Log to main test node if present
			if (extentTest != null) {
				extentTest.log(Status.PASS, message);
			}
			LOG.info(message); // SLF4J log
		} catch (Exception e) {
			LOG.error("Error logging pass", e);
		}
	}

	
}



