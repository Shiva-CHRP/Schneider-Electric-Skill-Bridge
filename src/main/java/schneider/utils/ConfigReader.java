package schneider.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

	private static Properties prop = new Properties();

	static {

		String path = System.getProperty("user.dir") + "/src/main/resources/GlobalData.properties";

		try (FileInputStream fis = new FileInputStream(path)) {

			prop.load(fis);

		} catch (IOException e) {

			throw new RuntimeException("Failed to load config file: " + path, e);
		}
	}

	// Get property by key
	public static String get(String key) {

		return prop.getProperty(key);
	}

	// Get browser
	public static String getBrowser() {

		return prop.getProperty("browser");
	}

	// Get URL
	public static String getUrl() {

		return prop.getProperty("url");
	}

	public static String getUsername() {
		return prop.getProperty("username");
	}

	public static String getPassword() {
		return prop.getProperty("password");
	}
}
