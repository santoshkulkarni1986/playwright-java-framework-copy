package com.kushi.utility;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
//import java.net.InetAddress;
//import java.net.UnknownHostException;
import java.util.Properties;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertyUtils {

	private static final Logger LOG = LoggerFactory.getLogger(PropertyUtils.class);
	/** The property file. */
	Properties propertyFile = new Properties();
	/** The file name. */
	String fileName;
	/** The value. */
	String value;
	/**
	 * Instantiates a new property utils.
	 *
	 * @param fileName
	 *            the file name
	 */
	public PropertyUtils(String fileName) {
		this.fileName = fileName;
		LOG.info("Property File Name is ::" + fileName);
		File propertyfile = new File(fileName);
		if (propertyfile.exists()) {
			FileInputStream fileInputStream = null;
			try {
				fileInputStream = new FileInputStream(propertyfile);
				if (fileInputStream != null) {
					propertyFile.load(fileInputStream);
					fileInputStream.close();
				}
			} catch (FileNotFoundException fileNotFoundException) {
				LOG.error("Error while finding Property file ::" + fileNotFoundException.getMessage());
			} catch (IOException iOException) {
				System.out
				.println("Error while Loading file in loading propetry file is ::" + iOException.getMessage());
			} catch (Exception exception) {
				LOG.error("Error propetry file is ::" + exception.getMessage());
			} finally {
				IOUtils.closeQuietly(fileInputStream);
			}
		} else {
			LOG.error("File not found::" + fileName);
		}
	}
	
	public String getProperty(String property) {
		try {
			value = propertyFile.getProperty(property);
		} catch (Exception exception) {
			LOG.error("Error while fetching the property ::" + exception);
		}
		return value;
	}
	
	public void setProperty(String strKey, String strValue) throws Throwable {
		try {
			propertyFile.setProperty(strKey, strValue);
			propertyFile.store(new FileOutputStream(fileName), null);
		} catch (Exception exception) {
			LOG.error("Error while adding the property ::" + exception);
		}
	}
}
