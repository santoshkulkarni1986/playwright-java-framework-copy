package com.kushi.runners;

import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.kushi.baseConfig.*;
import com.kushi.utility.Constants;
import com.kushi.utility.PropertyUtils;
import com.kushi.utility.TestResultsCucumberUtility;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;


public class BaseLocalRunner {
    public static PageConfig pageConfig = new PageConfig();
    final ExecutionHelper helper = new ExecutionHelper();
	final TestResultsCucumberUtility results = new TestResultsCucumberUtility();
	public static String baseProjectPath = System.getProperty(Constants.USER_DIR);
	public static PropertyUtils logFilePath = new PropertyUtils(baseProjectPath.concat(Constants.LOG4J_FILE_PATH));

	@Parameters({ "browser", "url" ,"featureName"})
    @BeforeTest
    public void setUp(String browser, String url,String featureName ) throws Exception, Throwable {
        System.out.println("Inside Setup function");
        System.out.println(browser);
        System.out.println(url);
        Properties properties = new Properties();
		InputStream configStream = new FileInputStream("log4j.properties");
		System.out.println(Constants.LOG4J_FILE_PATH);
		properties.load(configStream);
		//configStream.close();
		//properties.setProperty("log4j.appender.R.File", logFilePath);
		LogManager.resetConfiguration();
		PropertyConfigurator.configure(properties);
		pageConfig.setUpPageDetails(browser, url);
		helper.loadResources(featureName,browser);

    }

  

    @AfterTest
    public void tearDown() throws Throwable {
    	System.out.println("Inside Tear Down function");
        /** EXTENT REPORT */
        results.flushReports();; // Flush the extent report
      //  results.; // End the current test
        /** CLOSE DRIVER */
        pageConfig.closeConnection();
        }
}
