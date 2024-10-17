package com.kushi.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aventstack.extentreports.ExtentTest;
import com.kushi.baseConfig.*;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.SelectOption;
import com.microsoft.playwright.options.WaitForSelectorState;

public class PlaywrightUtils {

    private static final Logger LOG = LoggerFactory.getLogger(PlaywrightUtils.class);
    private static ExtentTest test;
    private static Page page;  // Class-level Page instance

    static {
        // Initialize the Page instance when the utility class is loaded
        page = PageConfig.getPage();
    }

    public static void setExtentTest(ExtentTest extentTest) {
        test = extentTest;
        PageConfig.setLogger(extentTest);
    }

    public static void enterText(String locator, String text) {
        try {
            LOG.info("Attempting to enter text in element with selector: {}", locator);
            page.setDefaultTimeout(60000); // Set timeout to 60 seconds

            if (page.isVisible(locator)) {
                page.fill(locator, text);
                test.pass("Entered text in element with selector: " + locator);
                LOG.info("Successfully entered text in element with selector: {}", locator);
            } else {
                test.fail("Element with selector: " + locator + " is not visible.");
                LOG.warn("Element with selector: {} is not visible.", locator);
            }
        } catch (Exception e) {
            test.fail("Failed to enter text in element with selector: " + locator + ". Exception: " + e.getMessage());
            LOG.error("Error while entering text in element with selector: {}. Exception: {}", locator, e.getMessage());
            throw e;
        }
    }

    public static void playwrightClick(String locator) {
        try {
            LOG.info("Attempting to click element with selector: {}", locator);

            if (page.isVisible(locator)) {
                page.click(locator);
                test.pass("Clicked on element with selector: " + locator);
                LOG.info("Clicked on element with selector: {}", locator);
            } else {
                test.fail("Element with selector: " + locator + " is not visible.");
                LOG.warn("Element with selector: {} is not visible.", locator);
            }
        } catch (Exception e) {
            test.fail("Failed to click on element with selector: " + locator + ". Exception: " + e.getMessage());
            LOG.error("Failed to click on element with selector: {}. Exception: {}", locator, e.getMessage());
        }
    }

    public static void selectDropdownByValue(String locator, String value) {
        try {
            if (page.isVisible(locator)) {
                page.selectOption(locator, value);
                test.pass("Selected value: " + value + " from dropdown with locator: " + locator);
                LOG.info("Selected value: {} from dropdown with locator: {}", value, locator);
            } else {
                test.fail("Dropdown with locator: " + locator + " is not visible.");
                LOG.warn("Dropdown with locator: {} is not visible.", locator);
            }
        } catch (Exception e) {
            test.fail("Failed to select value from dropdown with locator: " + locator + ". Exception: " + e.getMessage());
            LOG.error("Failed to select value from dropdown with locator: {}. Exception: {}", locator, e.getMessage());
        }
    }

    public static void selectDropdownByIndex(String locator, int index) {
        try {
            if (page.isVisible(locator)) {
                page.selectOption(locator, new SelectOption().setIndex(index));
                test.pass("Selected index: " + index + " from dropdown with locator: " + locator);
                LOG.info("Selected index: {} from dropdown with locator: {}", index, locator);
            } else {
                test.fail("Dropdown with locator: " + locator + " is not visible.");
                LOG.warn("Dropdown with locator: {} is not visible.", locator);
            }
        } catch (Exception e) {
            test.fail("Failed to select index from dropdown with locator: " + locator + ". Exception: " + e.getMessage());
            LOG.error("Failed to select index from dropdown with locator: {}. Exception: {}", locator, e.getMessage());
        }
    }

    public static void selectDropdownByText(String locator, String text) {
        try {
            if (page.isVisible(locator)) {
                page.selectOption(locator, new SelectOption().setLabel(text));
                test.pass("Selected text: " + text + " from dropdown with selector: " + locator);
                LOG.info("Selected text: {} from dropdown with selector: {}", text, locator);
            } else {
                test.fail("Dropdown with selector: " + locator + " is not visible.");
                LOG.warn("Dropdown with selector: {} is not visible.", locator);
            }
        } catch (Exception e) {
            test.fail("Failed to select text from dropdown with selector: " + locator + ". Exception: " + e.getMessage());
            LOG.error("Failed to select text from dropdown with selector: {}. Exception: {}", locator, e.getMessage());
        }
    }

    public static void waitForElementToBeVisible(String locator) {
        try {
            LOG.info("Waiting for element with selector: {} to be visible.", locator);
            page.waitForSelector(locator, new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE));
            test.pass("Element with selector: " + locator + " is now visible.");
            LOG.info("Element with selector: {} is now visible.", locator);
        } catch (Exception e) {
            test.fail("Element with selector: " + locator + " is not visible. Exception: " + e.getMessage());
            LOG.error("Failed to wait for element with selector: {}. Exception: {}", locator, e.getMessage());
        }
    }

    public static void scrollToElement(String locator) {
        try {
            LOG.info("Scrolling to element with selector: {}", locator);

            if (page.isVisible(locator)) {
                page.locator(locator).scrollIntoViewIfNeeded();
                test.pass("Scrolled to element with selector: " + locator);
                LOG.info("Scrolled to element with selector: {}", locator);
            } else {
                test.fail("Element with selector: " + locator + " is not visible.");
                LOG.warn("Element with selector: {} is not visible.", locator);
            }
        } catch (Exception e) {
            test.fail("Failed to scroll to element with selector: " + locator + ". Exception: " + e.getMessage());
            LOG.error("Failed to scroll to element with selector: {}. Exception: {}", locator, e.getMessage());
        }
    }
}
