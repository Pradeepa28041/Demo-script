package common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevToolsException;
import org.openqa.selenium.devtools.v123.network.Network;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.MalformedURLException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.devtools.v123.network.model.RequestId;
import java.util.Optional;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.DevToolsException;
import org.openqa.selenium.devtools.v123.network.Network;
import org.openqa.selenium.devtools.v123.network.model.RequestId;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import org.openqa.selenium.Cookie;

import java.nio.file.Paths;

public class WebBrowser {
	//public static WebDriver driver;
	private static WebDriver driver;
	private static String path = System.getProperty("user.dir");
	static String parentWindowHandle;
	private static boolean isBrowserOpen = false;
	private static boolean launchNewBrowser = false;
	public static boolean boolHighLightElement = false;
	public static boolean boolEachstepScreenshot = false;
	public static boolean boolEachSoftAssersion = false;
	static List<WebDriver> webdriverList = new ArrayList<WebDriver>();
	public static String browserType = "";
	public static String PageLoadTimeout = "";
	public static String DirectoryPAth = "";
	public static String HighLightElement = "";
	public static String EachstepScreenshot = "";
	public static String softassertion = "";
	public static String profilePath = "";
	public static String lambdaTestBuild = "";
	public static String lambdaTestURL = "";
	public static String NetworkLog = "";
	public static String type = "";
	public static String hubURL = "";
	public static DevTools devTools;
	

	public static WebDriver getBrowser(boolean launchBrowser) {
		if ((driver == null || launchBrowser) && !isBrowserOpen) {

			if (CommonUtil.browserName != null) {
				browserType = CommonUtil.browserName;
				System.out.println("browserName-----------------" + browserType);
			} else {
				browserType = CommonUtil.GetXMLData(
						Paths.get(path.toString(), "src", "test", "java", "ApplicationSettings.xml").toString(),
						"BrowserType");
			}
			PageLoadTimeout = CommonUtil.GetXMLData(
					Paths.get(path.toString(), "src", "test", "java", "ApplicationSettings.xml").toString(),
					"MaximumTimeInSecondsToWaitForControl");
			DirectoryPAth = CommonUtil.GetXMLData(
					Paths.get(path.toString(), "src", "test", "java", "ApplicationSettings.xml").toString(),
					"DownloadInCurrentDirectory");
			HighLightElement = CommonUtil.GetXMLData(
					Paths.get(path.toString(), "src", "test", "java", "ApplicationSettings.xml").toString(),
					"HighLightWebElement");
			EachstepScreenshot = CommonUtil.GetXMLData(
					Paths.get(path.toString(), "src", "test", "java", "ApplicationSettings.xml").toString(),
					"EnableEachStepScreenshot");
			softassertion = CommonUtil.GetXMLData(
					Paths.get(path.toString(), "src", "test", "java", "ApplicationSettings.xml").toString(),
					"EnableSoftassertion");
			profilePath = CommonUtil.GetXMLData(
					Paths.get(path.toString(), "src", "test", "java", "ApplicationSettings.xml").toString(),
					"ProfilePath");
			String pathOfBrowser = CommonUtil.GetXMLData(
					Paths.get(path.toString(), "src", "test", "java", "ApplicationSettings.xml").toString(),
					"WebdriverPath");

			if(HighLightElement.toUpperCase().equals("TRUE")) {
				boolHighLightElement=true;
			}
			if(EachstepScreenshot.toUpperCase().equals("TRUE")) {
				boolEachstepScreenshot=true;
			}
			if(softassertion.toUpperCase().equals("TRUE")) {
				boolEachSoftAssersion=true;
			}
			if (browserType.equals("Firefox")) {
				System.out.print("Launching Firefox");
				if (pathOfBrowser.equals("Na")) {
					WebDriverManager.firefoxdriver().clearDriverCache().setup();
				}
				else {
					System.setProperty("webdriver.gecko.driver", pathOfBrowser + "/geckodriver.exe");
				}
				driver = new FirefoxDriver();
				driver.manage().window().maximize();
			} else if (browserType.equals("Edge")) {
				System.out.print("Launching Edge");
				if (pathOfBrowser.equals("Na")) {
					WebDriverManager.edgedriver().clearDriverCache().setup();
				}
				else {
					System.setProperty("webdriver.edge.driver", pathOfBrowser + "/msedgedriver.exe");
				}
				driver = new EdgeDriver();
				driver.manage().window().maximize();
			} else if (browserType.toUpperCase().equals("Lambda".toUpperCase())) {
				System.out.print("Attempting connection to LambdaTest");
				lambdaTestBuild = CommonUtil.GetXMLData(
						Paths.get(path.toString(), "src", "test", "java", "ApplicationSettings.xml").toString(),
						"LambdaTestBuild");
				lambdaTestURL = CommonUtil.GetXMLData(
						Paths.get(path.toString(), "src", "test", "java", "ApplicationSettings.xml").toString(),
						"LambdaTestURL");
				NetworkLog = CommonUtil.GetXMLData(
						Paths.get(path.toString(), "src", "test", "java", "ApplicationSettings.xml").toString(),
						"NetworkLog");

				RemoteWebDriver remotedriver = null;
				DesiredCapabilities caps = new DesiredCapabilities();
				caps.setCapability("build", lambdaTestBuild);

				//String testRailId=CommonUtil.getTestRailID();

				caps.setCapability("name", "");
				caps.setCapability("platform", "Windows 10");
				caps.setCapability("browserName", "Chrome");
				caps.setCapability("version", "97.0");
				caps.setCapability("network",NetworkLog);
				System.out.println("Desired caps made successfully");

				try {

					remotedriver = new RemoteWebDriver(new URL(lambdaTestURL),
							caps);
					SessionId sessionid = remotedriver.getSessionId();
					System.out.print("Driver session id is :"+sessionid.toString());

				} catch (MalformedURLException e) {

					System.out.println("Invalid grid URL");

				} catch (Exception e) {

					System.out.println(e.getMessage());

				}

				driver = remotedriver;
			} else {
				System.out.print("Launching Chrome");
				if (pathOfBrowser.equals("Na")) {
					WebDriverManager.chromedriver().clearDriverCache().setup();
				}
				else {
					System.setProperty("webdriver.chrome.driver", pathOfBrowser + "/chromedriver.exe");
				}
				//Create a map to store  preferences
				Map<String, Object> prefs = new HashMap<String, Object>();
				// add key and value to map as follow to switch off browser notification
				// Pass the argument 1 to allow and 2 to block
				prefs.put("profile.default_content_setting_values.notifications", 2);
				//DesiredCapabilities caps = DesiredCapabilities.chrome();
				DesiredCapabilities caps = new DesiredCapabilities();
				LoggingPreferences logPrefs = new LoggingPreferences();
				logPrefs.enable(LogType.BROWSER, Level.ALL);
				//caps.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
				logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
				caps.setCapability("goog:loggingPrefs", logPrefs);

				// Create an instance of ChromeOptions
				if (DirectoryPAth.toUpperCase().equals("TRUE")) {
					String downloadFilepath = System.getProperty("user.dir");
					prefs.put("download.default_directory", downloadFilepath);
				}
				ChromeOptions options = new ChromeOptions();
				if (browserType.toUpperCase().equals("HEADLESS CHROME")) {
					options.addArguments("--no-sandbox"); // Bypass OS security model
					options.addArguments("--headless"); // headless -> no browser window. needed for jenkins
					options.addArguments("disable-infobars"); // disabling infobars
					options.addArguments("--disable-extensions"); // disabling extensions
					options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
					options.addArguments("window-size=1920,1080");
					options.addArguments("--disable-gpu");
					options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36");
				}

				if (profilePath != null && !profilePath.isEmpty()) {
					// Here you set the path of the profile ending with User Data not the profile folder
					options.addArguments("user-data-dir="+profilePath);
				}
				options.addArguments("--ignore-ssl-errors=yes");
				options.addArguments("--ignore-certificate-errors");
				// set ExperimentalOption - prefs
				options.setExperimentalOption("prefs", prefs);
				options.setCapability("goog:loggingPrefs", java.util.Map.of(LogType.PERFORMANCE, Level.ALL));
				options.addArguments("use-fake-ui-for-media-stream");
				options.addArguments("use-fake-device-for-media-stream");
				options.addArguments("--remote-allow-origins=*");
				options.addArguments("--disable-blink-features=AutomationControlled");
				if (browserType.equals("Kiosk Chrome")) {
					options.addArguments("--kiosk");
				}

				type = CommonUtil.GetXMLData(
						Paths.get(path.toString(), "src", "test", "java", "ApplicationSettings.xml").toString(),
						"Type");

				if (type.toUpperCase().contains("GRID")) {
					hubURL = CommonUtil.GetXMLData(
							Paths.get(path.toString(), "src", "test", "java", "ApplicationSettings.xml").toString(),
							"HubURL");
					try {
						driver = new RemoteWebDriver(new URL(hubURL), options);
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					// Now Pass ChromeOptions instance to ChromeDriver Constructor to initialize
					// chrome driver which will switch off this browser notification on the chrome
					// browser
					options.merge(caps);
					driver = new ChromeDriver(options);
					driver.manage().window().maximize();
				}
			}
			webdriverList.add(driver);
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Integer.parseInt(PageLoadTimeout)));
			//driver.manage().timeouts().pageLoadTimeout(Integer.parseInt(PageLoadTimeout), TimeUnit.SECONDS);

			parentWindowHandle = driver.getWindowHandle();
			isBrowserOpen = true;
		} else if (launchBrowser) {
			WebDriver driver2 = multipleBrowserInstance();
			webdriverList.add(driver2);
			driver2.manage().window().maximize();
		}
		return driver;

	}

	public static WebDriver multipleBrowserInstance() {

		if (CommonUtil.browserName != null) {
			browserType = CommonUtil.browserName;
			System.out.println("browserName-----------------" + browserType);
		} else {
			browserType = CommonUtil.GetXMLData(
					Paths.get(path.toString(), "src", "test", "java", "ApplicationSettings.xml").toString(),
					"BrowserType");
		}
		PageLoadTimeout = CommonUtil.GetXMLData(
				Paths.get(path.toString(), "src", "test", "java", "ApplicationSettings.xml").toString(),
				"MaximumTimeInSecondsToWaitForControl");
		DirectoryPAth = CommonUtil.GetXMLData(
				Paths.get(path.toString(), "src", "test", "java", "ApplicationSettings.xml").toString(),
				"DownloadInCurrentDirectory");
		HighLightElement = CommonUtil.GetXMLData(
				Paths.get(path.toString(), "src", "test", "java", "ApplicationSettings.xml").toString(),
				"HighLightWebElement");
		EachstepScreenshot = CommonUtil.GetXMLData(
				Paths.get(path.toString(), "src", "test", "java", "ApplicationSettings.xml").toString(),
				"EnableEachStepScreenshot");
		softassertion = CommonUtil.GetXMLData(
				Paths.get(path.toString(), "src", "test", "java", "ApplicationSettings.xml").toString(),
				"EnableSoftassertion");

		profilePath = CommonUtil.GetXMLData(
				Paths.get(path.toString(), "src", "test", "java", "ApplicationSettings.xml").toString(),
				"ProfilePath");	

		if(HighLightElement.toUpperCase().equals("TRUE")) {
			boolHighLightElement=true;
		}
		if(EachstepScreenshot.toUpperCase().equals("TRUE")) {
			boolEachstepScreenshot=true;
		}
		if(softassertion.toUpperCase().equals("TRUE")) {
			boolEachSoftAssersion=true;
		}
		if (browserType.equals("Firefox")) {
			System.out.print("Launching Firefox");
			//System.setProperty("webdriver.gecko.driver",
			//		Paths.get(path.toString(), "src", "test", "resources", "geckodriver.exe").toString());
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
		} else if (browserType.equals("Edge")) {

			System.out.print("Launching Edge");
			//System.setProperty("webdriver.edge.driver",
			//		Paths.get(path.toString(), "src", "test", "resources", "msedgedriver.exe").toString());
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			driver.manage().window().maximize();
		} else if (browserType.toUpperCase().equals("Lambda".toUpperCase())) {
			System.out.print("Attempting connection to LambdaTest");
			lambdaTestBuild = CommonUtil.GetXMLData(
					Paths.get(path.toString(), "src", "test", "java", "ApplicationSettings.xml").toString(),
					"LambdaTestBuild");
			lambdaTestURL = CommonUtil.GetXMLData(
					Paths.get(path.toString(), "src", "test", "java", "ApplicationSettings.xml").toString(),
					"LambdaTestURL");
			NetworkLog = CommonUtil.GetXMLData(
					Paths.get(path.toString(), "src", "test", "java", "ApplicationSettings.xml").toString(),
					"NetworkLog");

			RemoteWebDriver remotedriver = null;
			DesiredCapabilities caps = new DesiredCapabilities();
			caps.setCapability("build", lambdaTestBuild);

			//String testRailId=CommonUtil.getTestRailID();

			caps.setCapability("name", "");
			caps.setCapability("platform", "Windows 10");
			caps.setCapability("browserName", "Chrome");
			caps.setCapability("version", "97.0");
			caps.setCapability("network",NetworkLog);
			System.out.println("Desired caps made successfully");

			try {

				remotedriver = new RemoteWebDriver(new URL(lambdaTestURL),
						caps);
				SessionId sessionid = remotedriver.getSessionId();
				System.out.print("Driver session id is :"+sessionid.toString());

			} catch (MalformedURLException e) {

				System.out.println("Invalid grid URL");

			} catch (Exception e) {

				System.out.println(e.getMessage());

			}

			driver = remotedriver;
		} else {
			System.out.print("Launching Chrome");
			//				if (isWindows()) {
			//				System.setProperty("webdriver.chrome.driver",
			//						Paths.get(path.toString(), "src", "test", "resources", "chromedriver.exe").toString());
			//			} else {
			//				System.setProperty("webdriver.chrome.driver",
			//					Paths.get(path.toString(), "src", "test", "resources", "chromedriver").toString());
			//			}
			WebDriverManager.chromedriver().setup();
			//Create a map to store  preferences
			Map<String, Object> prefs = new HashMap<String, Object>();

			//add key and value to map as follow to switch off browser notification
			//Pass the argument 1 to allow and 2 to block
			prefs.put("profile.default_content_setting_values.notifications", 2);
			//DesiredCapabilities caps = DesiredCapabilities.chrome();
			DesiredCapabilities caps = new DesiredCapabilities();
			LoggingPreferences logPrefs = new LoggingPreferences();
			logPrefs.enable(LogType.BROWSER, Level.ALL);
			//caps.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
			logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
			caps.setCapability("goog:loggingPrefs", logPrefs);

			//Create an instance of ChromeOptions
			if (DirectoryPAth.toUpperCase().equals("TRUE")) {
				String downloadFilepath = System.getProperty("user.dir");
				prefs.put("download.default_directory", downloadFilepath);
			}
			ChromeOptions options = new ChromeOptions();
			if (browserType.toUpperCase().equals("HEADLESS CHROME")) {
				options.addArguments("--no-sandbox"); // Bypass OS security model
				options.addArguments("--headless"); // headless -> no browser window. needed for jenkins
				options.addArguments("disable-infobars"); // disabling infobars
				options.addArguments("--disable-extensions"); // disabling extensions
				options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
				options.addArguments("window-size=1920,1080");
			}

			if (profilePath != null && !profilePath.isEmpty()) {
				// Here you set the path of the profile ending with User Data not the profile folder
				options.addArguments("user-data-dir="+profilePath);
			}

			options.addArguments("--ignore-ssl-errors=yes");
			options.addArguments("--ignore-certificate-errors");
			//set ExperimentalOption - prefs
			options.setExperimentalOption("prefs", prefs);
			options.addArguments("use-fake-ui-for-media-stream");
			options.addArguments("use-fake-device-for-media-stream");
			options.addArguments("--remote-allow-origins=*");
			if (browserType.equals("Kiosk Chrome")) {
				options.addArguments("--kiosk");
			}

			type = CommonUtil.GetXMLData(
					Paths.get(path.toString(), "src", "test", "java", "ApplicationSettings.xml").toString(),
					"Type");

			if (type.toUpperCase().contains("GRID")) {
				hubURL = CommonUtil.GetXMLData(
						Paths.get(path.toString(), "src", "test", "java", "ApplicationSettings.xml").toString(),
						"HubURL");
				try {
					driver = new RemoteWebDriver(new URL(hubURL), options);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				// Now Pass ChromeOptions instance to ChromeDriver Constructor to initialize
				// chrome driver which will switch off this browser notification on the chrome
				// browser
				options.merge(caps);
				driver = new ChromeDriver(options);
				driver.manage().window().maximize();
			}
		}
		//webdriverList.add(driver);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Integer.parseInt(PageLoadTimeout)));
		//driver.manage().timeouts().pageLoadTimeout(Integer.parseInt(PageLoadTimeout), TimeUnit.SECONDS);

		parentWindowHandle = driver.getWindowHandle();
		isBrowserOpen = true;

		return driver;
	}
	public static WebDriver getBrowser() {
		return getBrowser(launchNewBrowser);
	}

	public void setBrowser(WebDriver webDriver) {

		this.driver = webDriver;
	}

	public static void closetab(int tab) {
		try {
			ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(tabs2.get(tab));
			driver.close();
		} catch (Exception e) {

		}

	}

	public static void LaunchApplication(boolean openBrowser) {
							
			String autUrl = "";
			if (CommonUtil.appUrl != null) {
				autUrl = CommonUtil.appUrl;
				System.out.println("appurl-----------" + autUrl);
			} else {
				autUrl = CommonUtil.GetXMLData(
						Paths.get(path.toString(), "src", "test", "java", "ApplicationSettings.xml").toString(), "URL");
			}
			if(driver == null)
			{
				getBrowser(openBrowser);
			}
			
			driver.get(autUrl);
		
			if(Hooks.CookiesAdded) 
			{
			
			      for(Cookie cookie:Hooks.cookies)
			  {
			    	  driver.manage().addCookie(cookie);
			  }
			      driver.navigate().refresh();
			}
		
	}

	public static void LaunchApplication(boolean openBrowser, String autUrl) {

		getBrowser(openBrowser);
		((JavascriptExecutor) driver).executeScript("window.open()");
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		for (int i = 0; i < tabs.size(); i++) {
			driver.switchTo().window(tabs.get(i));
		}
		WebDriver newDriver = webdriverList.get(webdriverList.size() - 1);
		newDriver.get(autUrl);
	}

	public static void LaunchNewInstance(boolean openBrowser, String autUrl) {
		getBrowser(openBrowser);
		WebDriver newDriver = webdriverList.get(webdriverList.size() - 1);
		newDriver.get(autUrl);
	}

	public static void LaunchApplication1(String autUrl) {

		driver.navigate().to(autUrl);
	}

	public static void openNewTab(boolean openBrowser, String autUrl) {		
		((JavascriptExecutor) driver).executeScript("window.open()");
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		for (int i = 0; i < tabs.size(); i++) {
			driver.switchTo().window(tabs.get(i));
		}
		WebDriver newDriver = webdriverList.get(webdriverList.size() - 1);
		newDriver.get(autUrl);
	}

	public static void LaunchAPIApplication() {
		String autUrl = "";
		if (RestAssuredUtil.apiCmdUrl != null) {
			autUrl = RestAssuredUtil.apiCmdUrl;
			System.out.println("api url-----------" + autUrl);
		} else {
			autUrl = CommonUtil.GetXMLData(
					Paths.get(path.toString(), "src", "test", "java", "ApplicationSettings.xml").toString(), "APIURL");
		}
		// getBrowser();
		RestAssuredUtil.setAPIURL(autUrl);
		// driver.get(autUrl);

	}

	public static String getParentWindowHandle() {
		return parentWindowHandle;
	}

	public static void setCurrentBrowser(int index) {
		if (webdriverList.size() > index) {
			driver = webdriverList.get(index);
			isBrowserOpen = true;
		}
	}

	public static void closeBrowserInstance() {
		for (int counter = 0; counter < webdriverList.size(); counter++) {
			if (webdriverList.get(counter) != null) {
				webdriverList.get(counter).quit();
			}
		}

		driver = null;
		webdriverList = new ArrayList<WebDriver>();
		isBrowserOpen = false;
	}

	public static boolean isBrowserOpened() {
		return isBrowserOpen;
	}
	public static boolean DevTool(List<String> list, String URL) {
		// Cast WebDriver to DevTools to access DevTools methods
		devTools = ((ChromeDriver) driver).getDevTools();
 
		// Create a session to use the Network domain
		devTools.createSession();
 
		devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
 
		Boolean Flag = true;
		AtomicInteger count = new AtomicInteger(0);
		final RequestId[] id = new RequestId[3];
		// String ResponseBody = "";
		List<String> responseBodies = new ArrayList<>();
 
//		for (String item : list) 
//			System.out.println(item);
//		
 
		//
		for (int retry = 0; retry < 5; retry++) {
			driver.navigate().refresh();
			WebBrowserUtil.ScrollDown(String.valueOf(10));
			devTools.addListener(Network.responseReceived(), responseReceived -> {
 
				String responseUrl = responseReceived.getResponse().getUrl();
				System.out.println("Received response URL: " + responseUrl);
 
				if (responseUrl.equals(URL)) {
					count.getAndIncrement();
					if (count.get() <= 3) {
						id[count.get() - 1] = responseReceived.getRequestId();
						System.out.println("ID" + count.get() + ": " + id[count.get() - 1]);
						ExtentCucumberAdapter.addTestStepLog("ID" + count.get() + ": " + id[count.get() - 1]);
 
					}
				}
			});
 
			try {
				Thread.sleep(9000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			for (int i = 0; i < id.length; i++) {
	            if (id[i] != null) {
	                try {
	                    String responseBody = devTools.send(Network.getResponseBody(id[i])).getBody();
	                    responseBodies.add(responseBody);
	                   // System.out.println("ResponseBody" + (i + 1) + " = " + responseBody);
	                } catch (Exception e) {
	                    System.out.println("Not able to capture response body in id[" + i + "]: " + e.getMessage());
	                }
	            }
	        }
			if (!responseBodies.isEmpty()) {
				break; // Exit retry loop if response bodies are successfully captured
			} else {
				System.out.println("Retrying to fetch response bodies...");
			}
		}
		if (!responseBodies.isEmpty()) {
			for (String responseBody : responseBodies) {
				if (responseBody.contains(list.get(list.size() - 1))) {
				for (String item : list) {
					if (responseBody.contains(item)) {
						System.out.println("Yes! " + item + " it is present in the algonomy url");
						ExtentCucumberAdapter.addTestStepLog("Yes! " + item + " it is present in the algonomy url");
					} else {
						System.out.println(item + " No! it is not present in alognomy url");
						ExtentCucumberAdapter.addTestStepLog(item + " No! it is not present in alognomy url");
						Flag = false;
					}
					String[] reqStrings = responseBody.split("\\?");
					String reqString = "";
					for (String key : reqStrings) {
						if (key.contains(item)) {
							reqString = key;
							break;
						}
					}
					String[] reqStrings1 = reqString.split(",");
					String[] reqString2 = reqStrings1[0].split("&");
					for (String key : reqString2) {
						if (key.contains(item)) {
							System.out.println("The value is ### " + key);
							ExtentCucumberAdapter.addTestStepLog("The value is ### " + key);
							break;
						}
					}
				}
			}
				else {
					for (String item : list) {
						if (responseBody.contains(item)) {
							System.out.println("Yes! " + item + " it is present in the algonomy url");
							ExtentCucumberAdapter.addTestStepLog("Yes! " + item + " it is present in the algonomy url");
						} else {
							System.out.println(item + " No! it is not present in alognomy url");
							ExtentCucumberAdapter.addTestStepLog(item + " No! it is not present in alognomy url");
							Flag = false;
						}
						String[] reqStrings = responseBody.split("\\?");
						String reqString = "";
						for (String key : reqStrings) {
							if (key.contains(item)) {
								reqString = key;
								break;
							}
						}
						String[] reqStrings1 = reqString.split(",");
						String[] reqString2 = reqStrings1[0].split("&");
						for (String key : reqString2) {
							if (key.contains(item)) {
								System.out.println("The value is ### " + key);
								ExtentCucumberAdapter.addTestStepLog("The value is ### " + key);
								break;
							}
						}
					}
				}
			}
		} else {
			throw new CustomException("Response body is empty");
		}
		return Flag;
	}
	public static boolean isWindows() {
		String OS = System.getProperty("os.name").toLowerCase();
		return OS.contains("win");
	}

}
