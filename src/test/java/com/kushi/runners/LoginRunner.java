package com.kushi.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

public class LoginRunner extends BaseLocalRunner {

	@CucumberOptions(features = "src/test/java/features/", plugin = { "html:target/cucumber-html-report",
			"json:target/cucumber-json-report.json" }, glue = "com.kushi.steps")

	public class runner extends AbstractTestNGCucumberTests {

	}
}