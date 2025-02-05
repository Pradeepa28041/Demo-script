package common;

import java.nio.file.Paths;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import io.percy.selenium.Percy;

public class Constants {
	public static WebDriver browser;
	public static int Number_of_Iteration = 5;
	public static String Path = System.getProperty("user.dir");
	public static String Application_Settings = "ApplicationSettings.xml";
	public static String App_Settings_Path = Paths.get(Path, "src", "test", "java", Application_Settings).toString();
}
