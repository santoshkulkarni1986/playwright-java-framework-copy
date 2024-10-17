package com.kushi.steps;

import com.kushi.baseConfig.*;
import com.kushi.utility.Constants;
import com.kushi.utility.PlaywrightUtils;
import com.kushi.utility.TestResultsCucumberUtility;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import com.aventstack.extentreports.ExtentTest;

public class loginStepDef {

	TestResultsCucumberUtility testResultsStepDefinition = new TestResultsCucumberUtility();
	ExecutionHelper helper = new ExecutionHelper();

	/** The logger. */
	private static final Logger LOG = LoggerFactory.getLogger(loginStepDef.class);
	public static String baseProjectPath = System.getProperty(Constants.USER_DIR);

	// Extent Test variable

	@When("I enter username {string}")
	public void i_enter_username(String userName) throws InterruptedException {
		long startTime = System.currentTimeMillis(); // Start timer
		try {
			TestResultsCucumberUtility.createStepNode("When", "User Entert the Username");
			PlaywrightUtils.enterText("//input[@name='username']", userName);
			LOG.info("User is able to enter username ::PASS");
			TestResultsCucumberUtility.logPass("User is able to enter username");
			Assert.assertTrue(true, "User is able to enter username");
			long elapsedTime = System.currentTimeMillis() - startTime; // End timer
			LOG.info("Time taken to enter username: in ms", elapsedTime); // Log the time
			TestResultsCucumberUtility.logInfo("Time taken to enter username: in ms", elapsedTime); // Log the time

		} catch (final Exception exception) {
			LOG.error("User is unable to enter username::FAIL::{}", exception.getMessage());
			TestResultsCucumberUtility.logFail("User is unable to enter username: " + exception.getMessage());
			// TestResultsCucumberUtility.attachScreenshotToReport("enter_username");
			Assert.fail("User is unable to enter username");
		}
	}

	@When("I enter password {string}")
	public void i_enter_password(String passWord) {
		long startTime = System.currentTimeMillis(); // Start timer

		try {
			TestResultsCucumberUtility.createStepNode("When", "User Entert the Password");
			PlaywrightUtils.enterText("//input[@name='password']", passWord);
			LOG.info("User is able to enter password ::PASS");
			TestResultsCucumberUtility.logPass("User is able to enter password");
			long elapsedTime = System.currentTimeMillis() - startTime; // End timer
			LOG.info("Time taken to enter password: in ms", elapsedTime); // Log the time
			TestResultsCucumberUtility.logInfo("Time taken to enter password: in ms", elapsedTime); // Log the time
			Assert.assertTrue(true, "User is able to enter password");
		} catch (final Exception exception) {
			LOG.error("User is unable to enter password::FAIL::{}", exception.getMessage());
			TestResultsCucumberUtility.logFail("User is unable to enter password: " + exception.getMessage());
			// TestResultsCucumberUtility.attachScreenshotToReport("enter_password");
			Assert.fail("User is unable to enter password");
		}
	}

	@When("I click on the submit button")
	public void i_click_on_the_submit_button() {
		long startTime = System.currentTimeMillis(); // Start timer

		try {
			TestResultsCucumberUtility.createStepNode("When", "User Clicks on Submit Button");
			PlaywrightUtils.playwrightClick("//button[@id='submit']");
			LOG.info("User is able to click the submit button ::PASS");
			TestResultsCucumberUtility.logPass("User is able to click the submit button");
			long elapsedTime = System.currentTimeMillis() - startTime; // End timer
			LOG.info("Time taken to click the submit button: in ms", elapsedTime); // Log the time
			TestResultsCucumberUtility.logInfo("Time taken to click the submit button: in ms", elapsedTime);

		} catch (final Exception exception) {
			LOG.error("User is unable to click the submit button::FAIL::{}", exception.getMessage());
			TestResultsCucumberUtility.logFail("User is unable to click the submit button: " + exception.getMessage());
			// TestResultsCucumberUtility.attachScreenshotToReport("click_submit_button");
			Assert.fail("User is unable to click the submit button");
		}
	}

	@Then("I should see the welcome message")
	public void i_should_see_the_welcome_message() {
		long startTime = System.currentTimeMillis(); // Start timer

		try {
			TestResultsCucumberUtility.createStepNode("Given", "User Should see Welcome Message");
			PlaywrightUtils.playwrightClick("//a[contains(text(),'Log out')]");
			LOG.info("User is able to click the logout button ::PASS");
			// TestResultsCucumberUtility.logPass("User is able to click the Logout
			// button");
			long elapsedTime = System.currentTimeMillis() - startTime; // End timer
			LOG.info("Time taken to see the welcome message in ms : ", elapsedTime); // Log the time
			TestResultsCucumberUtility.logInfo("Time taken to see the welcome message in ms ", elapsedTime);
		} catch (final Exception exception) {
			LOG.error("Welcome message is not displayed::FAIL::{}", exception.getMessage());
			TestResultsCucumberUtility.logFail("Welcome message is not displayed: " + exception.getMessage());
			// TestResultsCucumberUtility.attachScreenshotToReport("welcome_message");
			Assert.fail("Welcome message is not displayed");
		}
	}

}
