package com.kushi.utility;

import java.util.logging.Logger;

public class EnvironmentUtil {
	private static final Logger LOGGER = Logger.getLogger(EnvironmentUtil.class.getName());
	private static final String BASE_URL_ENV_VAR = "BASE_URL";
	private static final String BROWSER_NAME_ENV_VAR = "BROWSER_NAME";
	private static final String TAGS_ENV_VAR = "CUCUMBER_TAGS";

	private static final String DEFAULT_BASE_URL = "https://practicetestautomation.com/practice-test-login/";
	private static final String DEFAULT_BROWSER_NAME = "chrome";
	private static final String DEFAULT_TAGS = "@Smoke";

	public static final String BASE_URL;
	public static final String BROWSER_NAME;
	public static final String TAGS;

	static {
		BASE_URL = System.getenv(BASE_URL_ENV_VAR) != null ? System.getenv(BASE_URL_ENV_VAR) : DEFAULT_BASE_URL;
		BROWSER_NAME = System.getenv(BROWSER_NAME_ENV_VAR) != null ? System.getenv(BROWSER_NAME_ENV_VAR)
				: DEFAULT_BROWSER_NAME;
		TAGS = System.getenv(TAGS_ENV_VAR) != null && !System.getenv(TAGS_ENV_VAR).isEmpty()
				? System.getenv(TAGS_ENV_VAR)
				: DEFAULT_TAGS;

		LOGGER.info("Using BASE_URL: " + BASE_URL);
		LOGGER.info("Using BROWSER_NAME: " + BROWSER_NAME);
		LOGGER.info("Using TAGS: " + TAGS);
	}

	public static String getBaseUrl() {
		return BASE_URL;
	}

	public static String getBrowserName() {
		return BROWSER_NAME;
	}

	public static String getTags() {
		return TAGS;
	}
}
