package common;

import java.nio.file.Paths;

import io.percy.selenium.Percy;

public class PercyUtil {
	public static String Path = System.getProperty("user.dir");
	public static Percy percy;
	public static String Application_Settings = "ApplicationSettings.xml";
	public static String App_Settings_Path = Paths.get(Path, "src", "test", "java", Application_Settings).toString();
	public static String percyFlag =  CommonUtil.GetXMLData(App_Settings_Path, "EnablePercy");
	public static String language = CommonUtil.GetXMLData(App_Settings_Path, "Language");
	public static String yes = "Yes";
}
