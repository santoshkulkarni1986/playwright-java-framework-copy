package com.kushi.baseConfig;

import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.*;
import com.microsoft.playwright.Browser.NewContextOptions;

import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PageConfig {

    private static final ThreadLocal<Playwright> playwrightThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<Browser> browserThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> contextThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<Page> pageThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<String> browserName = new ThreadLocal<>();
    private static final ThreadLocal<String> baseURL = new ThreadLocal<>();
    private static final ThreadLocal<String> scenarioName = new ThreadLocal<>();
    private static final ThreadLocal<ExtentTest> logger = new ThreadLocal<>();

    private static final Logger LOG = LoggerFactory.getLogger(PageConfig.class);

    public void setUpPageDetails(String browser, String url) {
        setBrowserName(browser);
        setBaseURL(url);
        LOG.info("Setting up Playwright for browser: {}", browser);
        LOG.info("Navigating to URL: {}", url);

        try {
            Playwright playwright = Playwright.create();
            setPlaywright(playwright);

            BrowserType browserType = getBrowserType(playwright, browser);
            Browser browserInstance = browserType.launch(new BrowserType.LaunchOptions().setHeadless(true)); // set to true for headless mode
            setBrowser(browserInstance);

            NewContextOptions contextOptions = new Browser.NewContextOptions()
            		.setViewportSize(1280, 1024) // Sets the viewport size, effectively maximizing the window
                    .setRecordVideoDir(Paths.get("videos/")) // Sets the video recording directory
                    .setAcceptDownloads(true); // Accepts downloads automatically
            BrowserContext context = browserInstance.newContext(contextOptions);
            
            // Start tracing before the page is opened
            context.tracing().start(new Tracing.StartOptions()
                    .setScreenshots(true)
                    .setSnapshots(true)
                    .setSources(true));

            setContext(context);

            Page page = context.newPage();
            setPage(page);
            page.navigate(url);
            LOG.info("Page navigated to URL: {}", url);
        } catch (Exception e) {
            LOG.error("Exception during Playwright setup for browser: {}", browser, e);
            throw new RuntimeException("Playwright initialization failed for browser: " + browser, e);
        }
    }

    private BrowserType getBrowserType(Playwright playwright, String browser) {
        switch (browser.toLowerCase()) {
            case "chrome":
                return playwright.chromium();
            case "firefox":
                return playwright.firefox();
            case "safari":
                return playwright.webkit();
            case "edge":
                return playwright.chromium();
            default:
                throw new IllegalArgumentException("Browser not supported: " + browser);
        }
    }

    public static Playwright getPlaywright() {
        return playwrightThreadLocal.get();
    }

    private static void setPlaywright(Playwright playwright) {
        playwrightThreadLocal.set(playwright);
    }

    public static Browser getBrowser() {
        return browserThreadLocal.get();
    }

    private static void setBrowser(Browser browser) {
        browserThreadLocal.set(browser);
    }

    public static BrowserContext getContext() {
        return contextThreadLocal.get();
    }

    private static void setContext(BrowserContext context) {
        contextThreadLocal.set(context);
    }

    public static Page getPage() {
        return pageThreadLocal.get();
    }

    private static void setPage(Page page) {
        pageThreadLocal.set(page);
    }

    private static void setBrowserName(String browser) {
        browserName.set(browser);
    }

    private static void setBaseURL(String url) {
        baseURL.set(url);
    }

    public static String getBrowserName() {
        return browserName.get();
    }

    public static String getBaseURL() {
        return baseURL.get();
    }

    public static String getScenarioName() {
        return scenarioName.get();
    }

    public static void setScenarioName(String scenario) {
        scenarioName.set(scenario);
    }
    
    public static ExtentTest getLogger() {
        return logger.get();
    }

    public static void setLogger(ExtentTest test) {
        logger.set(test);
    }

    public void closeConnection() {
        try {
            if (getPage() != null) {
                getPage().close();
            }
            if (getContext() != null) {
                // Stop tracing and save it to a file before closing the context
                getContext().tracing().stop(new Tracing.StopOptions()
                    .setPath(Paths.get("trace-" + getBrowserName() + ".zip")));
                
                getContext().close();
            }
            if (getBrowser() != null) {
                getBrowser().close();
            }
            if (getPlaywright() != null) {
                getPlaywright().close();
            }
        } catch (Exception e) {
            LOG.error("Error during Playwright teardown: {}", e.getMessage());
        }
    }
}
