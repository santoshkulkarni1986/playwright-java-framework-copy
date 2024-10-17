package com.kushi.steps;

import io.cucumber.java.Before;

import com.kushi.utility.EnvironmentUtil;
import com.kushi.utility.TestResultsCucumberUtility;

import io.cucumber.java.After;
import io.cucumber.java.Scenario;

public class CucumberHooks {

    private static String environment;

    @Before
    public void setup(Scenario scenario) {
    	 
    	        // Get the scenario name
    	        String scenarioName = scenario.getName();

    	        // Get the tags from the scenario
    	        String scenarioTags = String.join(",", scenario.getSourceTagNames());

    	        // Get the tags filter from EnvironmentUtil
    	        String tagsFilter = EnvironmentUtil.getTags();
    	        String featureName = scenario.getName();

    	        // Check if any of the scenario tags match the tags filter
    	        boolean shouldRun = scenarioTags.contains(tagsFilter);

    	        if (shouldRun) {
    	            // Start the test with tags
    	            TestResultsCucumberUtility.startFeature(featureName,tagsFilter);

    	            TestResultsCucumberUtility.startScenario(scenario.getName());
    	        } else {
    	            // Optionally, you can skip starting the test if it doesn't match the tags filter
    	            System.out.println("Skipping test: " + scenarioName + " as it does not match the tag filter: " + tagsFilter);
    	        }
    	    }

    @After
    public void afterScenario(Scenario scenario) {
        // Check if the scenario passed or failed
        if (scenario.isFailed()) {
            System.out.println("Scenario failed: " + scenario.getName());
        } else {
            System.out.println("Scenario passed: " + scenario.getName());
        }

        // Extract scenario steps from the scenario object
        System.out.println("Scenario steps:");
        scenario.getSourceTagNames().forEach(tag -> {
            System.out.println(tag);
        });
    }

   
}
