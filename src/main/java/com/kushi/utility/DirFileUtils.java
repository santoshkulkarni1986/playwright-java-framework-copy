package com.kushi.utility;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class DirFileUtils {

	/** The logger. */
	private static final Logger LOG = LoggerFactory.getLogger(DirFileUtils.class);
	/**
	 * Date stamp.
	 *
	 * @return the string
	 */
	// return date
	public static String dateStamp() {
		DateFormat dateFormat = new SimpleDateFormat();
		Date date = new Date();
		return dateFormat.format(date).substring(0, 7);
	}
	/**
	 * Date formate.
	 *
	 * @return the string
	 */
	public static String dateFormat() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.E_MMM_d_y);
		Date date = new Date();
		return dateFormat.format(date);
	}
	/**
	 * Time stamp.
	 *
	 * @return the string
	 */
	// return time and date
	public static String timeStamp() {
		java.util.Date today = new java.util.Date();
		return new java.sql.Timestamp(today.getTime()).toString();
	}
	/**
	 * Os environment.
	 *
	 * @return the string
	 */
	// return environmental details
	public static String osEnvironment() {
		return "Current suit exicuted on : " + System.getProperty("os.name") + "/version : "
				+ System.getProperty("os.version") + "/Architecture : " + System.getProperty("os.arch");
	}
	/**
	 * Creates the dir tree.
	 *
	 * @param directoryTreeName
	 *            the directory tree name
	 */
	public static void createDirTree(String directoryTreeName) {
		try {
			File dir = new File(directoryTreeName);
			if (!dir.exists()) {
				if (dir.mkdirs()) {
				} else {
				}
			}
		} catch (Exception exception) {
			LOG.info("Error  Exception in file::" + directoryTreeName);
			LOG.info("Error is ::" + exception.getMessage());
			exception.printStackTrace();
		}
	}
	/**
	 * Creates the new file.
	 *
	 * @param filePath
	 *            the file path
	 */
	public static void createNewFile(String filePath) {
		try {
			LOG.info("file path is " + filePath);
			File file = new File(filePath);
			if (file.getParentFile().exists()) {
				LOG.info("Directory is present!" + file.getParent());
				file.createNewFile();
			} else {
				LOG.info("creating directory!" + file.getParent());
				createDirTree(file.getParent());
				file.createNewFile();
			}
		} catch (
				Exception exception) {
			LOG.info("Error  Exception in file::" + filePath);
			LOG.info("Error is ::" + exception.getMessage());
			exception.printStackTrace();
		}
	}
}

