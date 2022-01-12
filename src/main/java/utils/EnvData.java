package utils;

import java.io.IOException;
import java.util.Properties;

public class EnvData {

	private Properties properties = new Properties();

	public EnvData(){
		String propertiesFileName = System.getProperty("propertiesFile");
		try {
			properties.load(this.getClass().getClassLoader().getResourceAsStream(propertiesFileName));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public String getURL() {
		return properties.getProperty("URL");
	}

	public String getUser() {
		return properties.getProperty("username");
	}
	public String getPass() {
		return properties.getProperty("password");
	}
}
