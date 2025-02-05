package common;

import java.awt.Rectangle;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.Collator;
import java.text.RuleBasedCollator;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.AWTException;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.stream.Collectors;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.OutputType;
import org.apache.pdfbox.io.RandomAccessBufferedFileInputStream;
import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.text.PDFTextStripper;
import java.net.URL;
import java.util.stream.IntStream;

import javax.imageio.ImageIO;

import org.openqa.selenium.NoSuchWindowException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.codehaus.plexus.util.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.chrome.ChromeDriver;

//import com.cucumber.listener.Reporter;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import java.io.FileFilter;

import io.cucumber.java.Scenario;
import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.cucumber.java.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.openqa.selenium.Dimension;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebBrowserUtil {
	static WebDriver driver;
	static String path = System.getProperty("user.dir");
	static int timeInterval = Integer.parseInt(CommonUtil.GetXMLData(
			Paths.get(path.toString(), "src", "test", "java", "ApplicationSettings.xml").toString(),
			"TimeIntervalInMilliSecondsToWaitForPage"));
	static int maxDelay = Integer.parseInt(CommonUtil.GetXMLData(
			Paths.get(path.toString(), "src", "test", "java", "ApplicationSettings.xml").toString(),
			"MaximumTimeInMilliSecondsToWaitForPage"));
	static int controlLoadTimeout = Integer.parseInt(CommonUtil.GetXMLData(
			Paths.get(path.toString(), "src", "test", "java", "ApplicationSettings.xml").toString(),
			"MaximumTimeInSecondsToWaitForControl"));
	static Scenario sce;
	public static String ScrenshotForSucess = "FALSE";

	public static void turnOffImplicitWaits() {
		// driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
	}

	public static void turnOnImplicitWaits() {
		String PageLoadTimeout = CommonUtil.GetXMLData(
				Paths.get(path.toString(), "src", "test", "java", "ApplicationSettings.xml").toString(),
				"MaximumTimeInSecondsToWaitForControl");
		// driver.manage().timeouts().implicitlyWait(Integer.parseInt(PageLoadTimeout),
		// TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Integer.parseInt(PageLoadTimeout)));
	}

	public static boolean IsElementPresent(String message) {
		driver = WebBrowser.getBrowser();
		List<WebElement> webElements = driver.findElements(By.xpath("//*[contains(text(), '" + message + "')]"));
		for (int counter = 0; counter < webElements.size(); counter++) {
			if (webElements.get(counter) != null && webElements.get(counter).isDisplayed()) {
				return true;
			}
		}
		return false;
	}

	public static boolean IsElementPresent1(String message) {
		driver = WebBrowser.getBrowser();
		List<WebElement> webElements = driver.findElements(By.xpath("//*[contains(text(), \"" + message + "\")]"));
		for (int counter = 0; counter < webElements.size(); counter++) {
			if (webElements.get(counter) != null && webElements.get(counter).isDisplayed()) {
				return true;
			}
		}
		return false;
	}

	public static String GetContent(String message) {
		String value = "";
		driver = WebBrowser.getBrowser();
		List<WebElement> webElements = driver.findElements(By.xpath("//*[contains(text(), '" + message + "')]"));
		for (int counter = 0; counter < webElements.size(); counter++) {
			if (webElements.get(counter) != null && webElements.get(counter).isDisplayed()) {
				return GetText(webElements.get(counter));
			} else {
				value = GetText(webElements.get(counter));
			}
		}
		return value;
	}

	/// <summary>
	/// To verify URL
	/// </summary>
	/// <param name="url">The URL</param>
	/// <returns>Verification result</returns>
	public static boolean VerifyURL(String url) {
		boolean isVerified = false;
		try {
			driver = WebBrowser.getBrowser();
			isVerified = driver.getCurrentUrl().toUpperCase().contains(url.toUpperCase());
			if (isVerified) {
				return isVerified;
			}
			Thread.sleep(500);
			Set<String> windowHandles = driver.getWindowHandles();
			for (int i = 0; i < windowHandles.size(); i++) {
				driver.switchTo().window(windowHandles.toArray()[i].toString());
				isVerified = driver.getCurrentUrl().toUpperCase().contains(url.toUpperCase());

				if (isVerified) {
					break;
				}
			}
		} catch (Exception ex) {
		}
		return isVerified;
	}
	public static void ClickIfVisible(WebElement element) {
		int i = 0;
		while (i < 2) {
			try {
				i++;
				Click(element);
				// browser.Wait();
				break;
			} catch (Exception ex) {
			}
		}
	}
	public static boolean verifyLabelDisplayed(WebElement element) {
		boolean isVerified = false;		
		try {
			//List<WebElement> links = getElementByXpath(value);
			if (element!= null) {
				isVerified = true;
			}				
		} catch (Exception ex) {
			isVerified = false;		
		}
		return isVerified;
	}
	public static void ScrollAndEnterText(WebElement element, String text) {
		Scroll(element);
		try {
			element.click();
			element.clear();
			element.sendKeys(text);
			for (int i = 0; i < 1; i++) {
				if (element.getAttribute("value").equals(text)) {
					break;
					}
				else {
					element.sendKeys(Keys.CONTROL + "a");
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
					}
					element.sendKeys(text);
				}
			}
		} catch (Exception ex) {
			throw new CustomException("Scroll And EnterText unsuccessful" + ex.getMessage());
		}
	}

	public static WebElement findElement(String xpath, String identificationType) {
		driver = WebBrowser.getBrowser();
		if(xpath.contains("||"))
		{
			String[] xpathSplit= xpath.split("\\|\\|");
			xpath=xpathSplit[0];
			driver=GetFrame(xpathSplit[1]);
		}
		WebElement element = null;
		if (WebBrowser.boolHighLightElement) {

			int i = 0;
			while (i < 2)
				try {

					if (identificationType.toUpperCase().equals("xpath".toUpperCase())) {
						element = driver.findElement(By.xpath(xpath));
					} else if (identificationType.toUpperCase().equals("id".toUpperCase())) {
						element = driver.findElement(By.id(xpath));
					}
					JavascriptExecutor jse = (JavascriptExecutor) driver;
					jse.executeScript("arguments[0].style.border='2px solid red'", element);
					break;
				} catch (Exception e) {
					try {
						Thread.sleep(2000);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						//e1.printStackTrace();
					}
					i++;
					if (i == 2) {
						throw new CustomException(e.getMessage(), e);
					}

				}
		} else {

			if (xpath.contains("shadowRoot")) {
				xpath = "return "+xpath;
				element = (WebElement)((JavascriptExecutor) driver).executeScript(xpath);
			} else if (identificationType.toUpperCase().equals("xpath".toUpperCase())) {
				element = driver.findElement(By.xpath(xpath));
			} else if (identificationType.toUpperCase().equals("id".toUpperCase())) {
				element = driver.findElement(By.id(xpath));
			}
		}
		return element;
	}

	public static WebElement findElement(String xpath, String identificationType, WebDriver frame) {
		WebElement element = null;
		if (identificationType.toUpperCase().equals("xpath".toUpperCase())) {
			element = frame.findElement(By.xpath(xpath));
		} else if (identificationType.toUpperCase().equals("id".toUpperCase())) {
			element = frame.findElement(By.id(xpath));
		}
		return element;
	}

	public static List<WebElement> findElements(String xpath, String identificationType) {
		driver = WebBrowser.getBrowser();
		if(xpath.contains("||"))
		{
			String[] xpathSplit= xpath.split("\\|\\|");
			xpath=xpathSplit[0];
			driver=GetFrame(xpathSplit[1]);
		}
		List<WebElement> element = null;
		if (identificationType.toUpperCase().equals("xpath".toUpperCase())) {
			element = driver.findElements(By.xpath(xpath));
		} else if (identificationType.toUpperCase().equals("id".toUpperCase())) {
			element = driver.findElements(By.id(xpath));
		}
		return element;
	}

	public static List<WebElement> findElements(String xpath, String identificationType, WebDriver frame) {
		List<WebElement> element = null;
		if (identificationType.toUpperCase().equals("xpath".toUpperCase())) {
			element = frame.findElements(By.xpath(xpath));
		} else if (identificationType.toUpperCase().equals("id".toUpperCase())) {
			element = frame.findElements(By.id(xpath));
		}
		return element;
	}

	public static void EnterText(WebElement element, String text) {
		try {
			element.click();
			element.sendKeys(text);
			for (int i = 0; i <= 1; i++) {		
			 try {
					if (element.getAttribute("value").equals(text)) {
						break;
					} else {
						element.sendKeys(Keys.CONTROL + "a");
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {

						}
						element.sendKeys(text);
					}
				}catch (Exception ex) {}
			}
		} catch (Exception ex) {
			throw new CustomException(ex.getMessage(), ex);				
		}
	}
	public static boolean verifyEnabledDisabledOptions(String verificationType,List<WebElement> eleList,String text)
	{
		boolean isVerified=false;
		boolean disabled=false;
		boolean verficationStarted=false;
		if(verificationType.toUpperCase().equals("Disabled".toUpperCase()))
		{
			disabled=true;
		}
		//System.out.println("eleList size--------------"+eleList.size());
		//String text= "Brand Logo,Brand Name,Business Logo,Customer Apartment Number";
		String[] textSplit=text.split(",");
		boolean breakLoop=false;
		int textSplitCount=0;

		for(int k=0;k<eleList.size();k++)
		{
			String eleText=eleList.get(k).getText();
			for(int j=textSplitCount;j<textSplit.length;j++)
			{

				//System.out.println("ele text-------------"+eleText+" textSplit.length : "+textSplit[j]+" j value : "+j+" textSplitCount : "+textSplitCount);
				if(textSplitCount==textSplit.length-1)
				{
					breakLoop=true;
				}
				if(eleText.equals(textSplit[j]))
				{
					verficationStarted=true;
					textSplitCount++;
					String classname=eleList.get(k).getAttribute("class");
					//System.out.println("ele class name---------------"+classname);
					ExtentCucumberAdapter.addTestStepLog("Element text : "+eleText+" , Element Class : "+classname);
					if(classname.contains("disabled"))
					{
						//System.out.println("element is disabled");
						if(disabled)
						{
							isVerified=true;
							break;
						}
						else
						{
							isVerified=false;
							break;
						}

					}
					else
					{
						//System.out.println("element is enabled");
						if(disabled)
						{
							isVerified=false;
							break;
						}
						else
						{
							isVerified=true;
							break;
						}
					}
				}

			}
			if(breakLoop)
			{
				break;
			}
			if(verficationStarted)
			{
				if(isVerified==false)
				{
					if(disabled)
					{
						//System.out.println(eleList.get(k).getText()+" is enabled, Expected to be disabled");
						ExtentCucumberAdapter.addTestStepLog(eleText+" is enabled, Expected to be disabled");
						break;
					}
					else
					{
						//System.out.println(eleList.get(k).getText()+" is disabled, Expected to be enabled");
						ExtentCucumberAdapter.addTestStepLog(eleText+" is disabled, Expected to be enabled");
						break;
					}
				}
			}

		}
		//System.out.println("isVerified---------------------"+isVerified);
		return isVerified;

	}

	public static void takeScrenshot (Scenario scenario) {
		String FullScreenshot = CommonUtil.GetXMLData(
				Paths.get(path.toString(), "src", "test", "java", "ApplicationSettings.xml").toString(),
				"EnableFullScreenshot");

			
		driver = WebBrowser.getBrowser();
		if(FullScreenshot.toLowerCase().equals("true"))
		{
		try {
		    JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		    Long height = (Long) jsExecutor.executeScript("return Math.max(document.body.scrollHeight, document.body.offsetHeight, document.documentElement.clientHeight, document.documentElement.scrollHeight, document.documentElement.offsetHeight);");

		    if (height < 1000) {
		        height = Long.valueOf(1000);
		    }

		    Actions actions = new Actions(driver);
		    int xCoordinate = 100; 
	        int yCoordinate = 350;
	        actions.moveByOffset(xCoordinate, yCoordinate).click().perform();

		    for (int i = 0; i <= height; i += driver.manage().window().getSize().height) {
		        jsExecutor.executeScript("window.scrollTo(0," + i + ");");
		        
		        try {
		            Thread.sleep(500);
		        } catch (InterruptedException e) {
		            e.printStackTrace();
		        }

		        File screenshotBase64 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		        String pathh = Paths.get(path.toString(), "src", "test", "java", "image_" + i + ".png").toString();
		        File destFile = new File(pathh);
		        FileUtils.copyFile(screenshotBase64, destFile);
		        
		        
		     //   actions.sendKeys(Keys.chord(Keys.END)).perform();
		        actions.sendKeys(Keys.PAGE_DOWN).perform();
//		        jsExecutor.executeScript("window.scrollBy(0, window.innerHeight);");
		        
		    }

		    String pathhh = Paths.get(path.toString(), "src", "test", "java").toString();
		    File dir = new File(pathhh);
		    FileFilter fileFilter = new WildcardFileFilter("image_*.png");
		    File[] files = dir.listFiles(fileFilter);

		    if (files.length > 0) {
		        Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_COMPARATOR);
		        int width = 0;
		        int heightt = 0;
		        for (int i = 0; i < files.length; i++) {
		            BufferedImage image1 = ImageIO.read(files[i]);
		            if (width < image1.getWidth()) {
		                width = image1.getWidth();
		            }
		            heightt = heightt + image1.getHeight();
		        }

		        BufferedImage result = new BufferedImage(width, heightt, BufferedImage.TYPE_INT_ARGB);
		        Graphics2D g = result.createGraphics();
		        for (int i = 0; i < files.length; i++) {
		            BufferedImage image1 = ImageIO.read(files[i]);
		            g.drawImage(image1, 0, i * image1.getHeight(), null);
		        }
		        g.dispose();

		        File outputFile = new File(Paths.get(path.toString(), "src", "test", "java", "resulttt.png").toString());
		        ImageIO.write(result, "png", outputFile);

		        Path filepath = Paths.get(path.toString(), "src", "test", "java", "image.png");
		        ImageIO.write(result, "PNG", new File(filepath.toString()));
		        BufferedImage bImage = ImageIO.read(new File(filepath.toString()));
		        ByteArrayOutputStream bos = new ByteArrayOutputStream();
		        ImageIO.write(bImage, "png", bos);
		        byte[] data = bos.toByteArray();

		        scenario.attach(data, "image/png", "image");
		    }
		} catch (Exception e) {
		    e.printStackTrace();
		}
		}
		else
		{
			final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
			scenario.attach(screenshot, "image/png", "image"); 	
		}
	}

		
	public static void captureScreenshot() {
		ScrenshotForSucess = CommonUtil.GetXMLData(
				Paths.get(path.toString(), "src", "test", "java", "ApplicationSettings.xml").toString(),
				"EnableScrenshotForSucess");
		if(ScrenshotForSucess.toUpperCase().equals("TRUE"))
		{
			if(!Hooks.apiScenario)
			{
				driver = WebBrowser.getBrowser();
				final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
				sce.attach(screenshot, "image/png", "image");
			}
		}
	}
	public static void takeEachStepScrenshot(Scenario scenario) {
		if(WebBrowser.boolEachstepScreenshot) {
			driver = WebBrowser.getBrowser();
			final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
			scenario.attach(screenshot, "image/png", "image"); 
		}  
	}
	public static WebDriver SwitchToBrowserWindow(String windowHandle) {
		driver = WebBrowser.getBrowser();
		return driver.switchTo().window(windowHandle);

	}

	public static void Scroll(WebElement element) {
		try {
			//Thread.sleep(2 * timeInterval);
			driver = WebBrowser.getBrowser();
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
			Thread.sleep(800);
			Actions actions = new Actions(driver);
			actions.moveToElement(element).build().perform();
			// actions.MoveToElement(element).Click().Build().Perform();
			//Thread.sleep(timeInterval);
			// Log.Info("Scroll is Successfull");
		} catch (Exception ex) {
			/*	try {
					Thread.sleep(2 * timeInterval);
				} catch (InterruptedException e) {

				}*/
			// Log.Info("Error in Scroll" + ex);
			throw new CustomException("Scroll and click unsuccessful" + ex.getMessage());
		}
	}

	public static void RefreshPage() {

		driver.navigate().refresh();
	}

	public static List<WebElement> getElementByXpath(String xpath) {
		if(xpath.contains("||"))
		{
			String[] xpathSplit= xpath.split("\\|\\|");
			xpath=xpathSplit[0];
			driver=GetFrame(xpathSplit[1]);
		}
		return driver.findElements(By.xpath(xpath));

	}
	public static WebElement getSingleElementByXpath(String xpath) {

		if(xpath.contains("||"))
		{
			String[] xpathSplit= xpath.split("\\|\\|");
			xpath=xpathSplit[0];
			driver=GetFrame(xpathSplit[1]);
		}
		return driver.findElement(By.xpath(xpath));

	}


	public static void SelectByIndex(WebElement DropDownList, int index) {
		Select drpList = new Select(DropDownList);
		if (DropDownList.isDisplayed()) {
			drpList.selectByIndex(index);
		} else {

			throw new CustomException("Select List not displayed");
		}
	}

	public static void SelectByRandomIndex(WebElement DropDownList) {
		Select drpList = new Select(DropDownList);
		if (DropDownList.isDisplayed()) {
			int totalCount=drpList.getOptions().size();
			Random random = new Random();
			int randomNumber =random.nextInt(totalCount - 1);
			drpList.selectByIndex(randomNumber);
		} else {

			throw new CustomException("Select List not displayed");
		}
	}

	public static void SelectByLastIndex(WebElement DropDownList) {
		Select drpList = new Select(DropDownList);
		if (DropDownList.isDisplayed()) {
			drpList.selectByIndex(drpList.getOptions().size() - 1);
		} else {

			throw new CustomException("Select List not displayed");
		}
	}
	public static int GetListCount(WebElement DropDownList) {
		Select drpList = new Select(DropDownList);
		if (DropDownList.isDisplayed()) {
			return drpList.getOptions().size();
		} else {

			throw new CustomException("Select List not displayed");
		}
	}
	public static void SelectByFirstIndex(WebElement DropDownList) {
		Select drpList = new Select(DropDownList);
		if (DropDownList.isDisplayed()) {
			drpList.selectByIndex(1);
		} else {

			throw new CustomException("Select List not displayed");
		}
	}


	public static void CloseBrowserWindow() {
		driver = WebBrowser.getBrowser();
		driver.close();
	}

	public static void SelectByValue(WebElement DropDownList, String text) {
		Select drpList = new Select(DropDownList);
		if (DropDownList.isDisplayed()) {
			drpList.selectByValue(text);
		} else {

			throw new CustomException("Select List not displayed");
		}
	}
	public static String GetSelectedValue(WebElement DropDownList) {
		Select drpList = new Select(DropDownList);
		if (DropDownList.isDisplayed()) {
			return drpList.getFirstSelectedOption().getText();
		} else {

			throw new CustomException("Select List not displayed");
		}
	}
	public static void ScrollAndSelectByText(WebElement DropDownList, String text) throws InterruptedException {
		Scroll(DropDownList);
		Select drpList = new Select(DropDownList);

		if (DropDownList.isDisplayed()) {
			drpList.selectByVisibleText(text);
		} else {

			throw new CustomException("Select List not displayed");
		}

	}

	public static WebDriver AttachPage(String pageTitle) {
		driver = WebBrowser.getBrowser();
		try {
			String title=driver.getTitle();

		}catch(UnhandledAlertException e){
			// driver.switchTo().alert().accept();
		}
		catch (NoSuchWindowException ex) {
			driver.switchTo().window((driver.getWindowHandles().toArray()[0]).toString());


		}
		try {

			// Log.Info("page Title of Attached Page = " + pageTitle);
			if (!driver.getTitle().toUpperCase().contains(pageTitle.toUpperCase())) {
				driver.switchTo().defaultContent();
				try {
					// TimeSpan timeOut = new TimeSpan(0, 0, 0, 0, maxDelay);
					//vish changed
					//WebDriverWait wait = new WebDriverWait(driver, 3);
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
					wait.until(ExpectedConditions.numberOfWindowsToBe(2));
				} catch (Exception ex) {
					// Log.Error("Timeout Delay");
					if (!(driver.getWindowHandles().size() > 2)) {
						return driver;
					}
				}
				int i = 0;
				int index = maxDelay / timeInterval;
				while (i < index) {
					for (String winHandle : driver.getWindowHandles()) {
						// driver.switchTo().window(winHandle);
						try {
							String title = pageTitle.replace("Page", "");
							// int n;
							boolean isNumeric = isStringInt(title);
							// bool isNumeric = int.TryParse(title, out n);
							if (isNumeric) {
								int windowIndex = Integer.parseInt(title);
								return driver.switchTo()
										.window((driver.getWindowHandles().toArray()[windowIndex - 1]).toString());
							}
						} catch (Exception ex) {

						}

						if (driver.switchTo().window(winHandle).getTitle().toUpperCase()
								.contains(pageTitle.toUpperCase())) {
							return driver.switchTo().window(winHandle);
						}

					}
					i++;
					Thread.sleep(timeInterval);
				}
			} else {
				// Wait();
				Thread.sleep(timeInterval);
			}
			return driver;
			// throw new Exception("Not able to attach browser window with title :" +
			// pageTitle);
		} catch(UnhandledAlertException e) {
			return driver;
		}
		catch (Exception ex) {
			return driver.switchTo().window((driver.getWindowHandles().toArray()[0]).toString());
			// Log.Error("Error in Attach Page Method");
			// throw;
		}
	}

	public static void ScrollAndClick(WebElement element) throws InterruptedException {
		try {
			driver = WebBrowser.getBrowser();
			Thread.sleep(2 * timeInterval);

			Actions actions = new Actions(driver);
			// actions.MoveToElement(element).Perform();
			// actions.MoveToElement(element).Click().Build().Perform();
			Thread.sleep(timeInterval);
			try {
				element.click();
				// Log.Info("Click Successful");

			} catch (Exception ex) {
				// Log.Info("Scroll and click Successful");
				((JavascriptExecutor) driver).executeScript("arguments[0].click()", element);
			}
		} catch (Exception ex) {
			Thread.sleep(2 * timeInterval);
			// Log.Error("Scroll and click unsuccessful" + ex.Message);
			throw new CustomException("Scroll and click unsuccessful" + ex.getMessage());
		}
	}

	public static WebDriver GetFrame(String propertyValue) {
		try {
			driver = WebBrowser.getBrowser();
			driver.switchTo().defaultContent();
			int i = 0;
			while (i < maxDelay / timeInterval) {
				try {
					String[] frameIndex = propertyValue.split("_");

					boolean isNumeric = isStringInt(frameIndex[0]);
					// bool isNumeric = int.TryParse(title, out n);
					if (isNumeric) {
						int index = Integer.parseInt(frameIndex[0]);
						driver.switchTo().frame(index);
						if (frameIndex.length > 1) {
							isNumeric = isStringInt(frameIndex[1]);
							Thread.sleep(2*timeInterval);
							// bool isNumeric = int.TryParse(title, out n);
							if (isNumeric) {
								index = Integer.parseInt(frameIndex[1]);
								return driver.switchTo().frame(index);

							} else {
								return driver.switchTo().frame(frameIndex[1]);
							}
						}
						return driver;
					}
					else
					{
						for (int j = 0; j < frameIndex.length; j++)
						{
							driver.switchTo().frame(frameIndex[j]);
							Thread.sleep(2*timeInterval);
						}
						return driver;						
					}					
				} catch (Exception e) {
					Thread.sleep(timeInterval);
				}
				i++;
			}
			return driver;
		} catch (Exception ex) {
			throw new CustomException("Web Frame with :" + propertyValue + " not found");
		}

	}

	public static boolean isStringInt(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}

	public static boolean IsSorted(List<Integer> sortedList) {
		boolean isSorted = IntStream.range(1, sortedList.size())
				.map(index -> sortedList.get(index - 1).compareTo(sortedList.get(index))).allMatch(order -> order <= 0);
		return isSorted;
	}

	public static boolean IsReverseSorted(List<Integer> sortedList) {
		boolean isSorted = IntStream.range(1, sortedList.size())
				.map(index -> sortedList.get(index - 1).compareTo(sortedList.get(index))).allMatch(order -> order >= 0);
		return isSorted;
	}

	public static String Text(WebElement element) {

		return element.getAttribute("value");

	}

	public static boolean Checked(WebElement _checkBox) {
		return _checkBox.isSelected();
	}

	public static void SelectByText(WebElement element, String value) {

		Select drpDownLst = new Select(element);
		drpDownLst.selectByVisibleText(value);

	}

	public static WebElement GetLinkByPartialLinkText(String text) {
		return driver.findElement(By.partialLinkText(text));
	}

	public static boolean validationOfSortedDropdownAscending(WebElement element) {

		boolean orderStatus = false;
		Select dropdown = new Select(element);

		List<WebElement> allOptionsElement = dropdown.getOptions();
		List<String> options = new ArrayList<String>();

		for (WebElement optionElement : allOptionsElement) {
			options.add(optionElement.getText().trim());
		}

		System.out.println("Options in dropdown with Default order :" + options);

		List<String> tempList = options.stream().sorted().collect(Collectors.toList());

		String englishRules1 = ("< '\u0021' < '\u0040' < '\u0023' < '\u0024' < '\u0025' < '\u005E' "
				+ "< '\u0026' < '\u002A' < '\u0028' < '\u0029'< '\u002D' < '\u002B' < '\u0020'"
				+ " < 0 < 1 < 2 < 3 < 4 < 5 < 6 < 7 < 8 < 9 < a < b < c < d < e < f < g < h < i < j < k < l "
				+ " < m < n < o < p < q < r < s < t < u < v < w < x < y < z < A < B < C < D < E < F "
				+ " < G < H < I < J < K < L < M < N < O < P < Q < R < S < T < U < V < W < X < Y < Z");

		try {

			RuleBasedCollator rbc = new RuleBasedCollator(englishRules1);
			rbc.setStrength(Collator.PRIMARY);

			Collections.sort(tempList, rbc);
			System.out.println("After sorting tempList :"+tempList);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.fillInStackTrace();
		}

		orderStatus = options.equals(tempList);

		System.out.println(" orderStatus : " + orderStatus);

		return orderStatus;

	}

	public static Comparator<String> stringAlphabeticalComparator = new Comparator<String>() {
		public int compare(String str1, String str2) {
			return ComparisonChain.start().compare(str1, str2, String.CASE_INSENSITIVE_ORDER).compare(str1, str2)
					.result();
		}
	};
	public static Comparator<String> stringReverseAlphabeticalComparator = new Comparator<String>() {
		public int compare(String str1, String str2) {
			return -1 * ComparisonChain.start().compare(str1, str2, String.CASE_INSENSITIVE_ORDER).compare(str1, str2)
					.result();
		}
	};

	public static void AcceptAlert() {
		int i = 0;
		driver = WebBrowser.getBrowser();
		while (i < maxDelay / timeInterval) {
			try {
				Thread.sleep(timeInterval);
				Alert alert = driver.switchTo().alert();				
				alert.accept();
				i++;
				break;
			} catch (Exception e) {
				i++;
				try {
					Thread.sleep(timeInterval);					
				} catch (InterruptedException e1) {

				}
			}
		}
	}
	public static void DismissAlert() {
		int i = 0;
		driver = WebBrowser.getBrowser();
		while (i < maxDelay / timeInterval) {
			try {
				Thread.sleep(timeInterval);
				Alert alert = driver.switchTo().alert();				
				alert.dismiss();
				i++;
				break;
			} catch (Exception e) {
				i++;
				try {
					Thread.sleep(timeInterval);					
				} catch (InterruptedException e1) {

				}
			}
		}
	}
	public static void EnterAlertText(String text) {
		int i = 0;
		driver = WebBrowser.getBrowser();
		while (i < maxDelay / timeInterval) {
			try {
				Thread.sleep(timeInterval);
				Alert alert = driver.switchTo().alert();				
				alert.sendKeys(text);
				i++;
				break;
			} catch (Exception e) {
				i++;
				try {
					Thread.sleep(timeInterval);					
				} catch (InterruptedException e1) {

				}
			}
		}
	}
	public static boolean verifyAlertText(String text) {
		int i = 0;
		boolean isVerified=false;
		driver = WebBrowser.getBrowser();
		while (i < maxDelay / timeInterval) {
			try {
				Thread.sleep(timeInterval);
				Alert alert = driver.switchTo().alert();				
				isVerified=alert.getText().contains(text);
				//alert.accept();
				i++;
				break;
			} catch (Exception e) {
				i++;
				try {
					Thread.sleep(timeInterval);
				} catch (InterruptedException e1) {

				}
			}
		}
		return isVerified;

	}

	public static void Check(WebElement element) throws InterruptedException {
		// Log.Info("Check is Successful");
		if (!Checked(element)) {
			// Log.Info("Check is Successful");
			Click(element);
		}
	}

	public static void UnCheck(WebElement element) throws InterruptedException{
		// Log.Info("Uncheck is Successful");
		if (Checked(element)) {
			// Log.Info("Uncheck is Successful");
			//element.click();
			Click(element);
		}
	}

	public static boolean IsEnabled(WebElement element) {

		return element.isEnabled();

	}

	public static String GetText(WebElement element) {
		String text = "";
		text = element.getText();
		if (text == null || text.trim().isEmpty()) {
			text = element.getAttribute("value");
		}
		if (text == null || text.trim().isEmpty()) {
			text = element.getAttribute("innerHTML");
		}

		return text;
	}

	public static boolean IsDisplayed(WebElement element) {

		return element.isDisplayed();

	}

	public static boolean ReadOnly(WebElement element) {

		return element.isSelected();

	}

	public static void ClearText(WebElement element) {
		element.clear();
	}

	public static void SelectEnterKey(WebElement element) {
		try {
			element.click();
			element.sendKeys(Keys.ENTER);
		} catch (Exception e) {
			throw new CustomException(e.getMessage(), e);
		}
	}


	/*public static String GetToolTip(WebElement element) {
		return element.getAttribute("alt");
	}*/

	public static String GetToolTip(WebElement element) {
		String toolTipText=element.getAttribute("alt");
		if(toolTipText == null || toolTipText.isEmpty())
		{
			toolTipText = element.getAttribute("title");
		}
		if(toolTipText == null || toolTipText.isEmpty())
		{
			toolTipText = element.getAttribute("data-original-title");
		}
		if(toolTipText == null || toolTipText.isEmpty())
		{
			toolTipText =element.getAttribute("id");
		}
		if(toolTipText == null || toolTipText.isEmpty())
		{
			toolTipText = element.getAttribute("uib-tooltip");
		}
		if(toolTipText == null || toolTipText.isEmpty())
		{
			toolTipText = element.getAttribute("mattooltip");
		}
		if(toolTipText == null || toolTipText.isEmpty())
		{
			toolTipText =GetText(element);
		}
		return toolTipText;
	}

	public static List<WebElement> GetOptions(WebElement element) {
		Select drpDwnLst = new Select(element);
		return drpDwnLst.getOptions();

	}

	public static boolean IsMultiple(WebElement element) {
		Select drpDwnLst = new Select(element);
		return drpDwnLst.isMultiple();

	}

	public static List<WebElement> GetAllSelectedOptions(WebElement element) {
		Select drpDwnLst = new Select(element);
		return drpDwnLst.getAllSelectedOptions();

	}

	public static boolean HasSelectedItems(WebElement element) {
		Select drpDwnLst = new Select(element);
		List<WebElement> elementsList = drpDwnLst.getAllSelectedOptions();
		//		if (elementsList.size() > 1) {
		if (!elementsList.get(0).getText().isEmpty()) {
			return true;
		} else {
			return false;
		}

	}
	public static String GetFirstOption(WebElement element) {
		Select drpDwnLst = new Select(element);
		WebElement option=drpDwnLst.getFirstSelectedOption();
		return option.getText();
	}

	public static String SelectedItem(WebElement element) {
		Select drpDwnLst = new Select(element);
		if (element.isDisplayed()) {
			return drpDwnLst.getFirstSelectedOption().getText();
		} else {
			throw new CustomException("Select List not displayed");
		}

	}

	public static void Selected(WebElement element) {
		try {
			boolean staleElement = true;
			int i = 0;
			while (staleElement && i < 5) {
				try {
					Click(element);
					staleElement = false;
				} catch (Exception ex) {
					i++;
					staleElement = true;
					try {
						ScrollAndClickUsingJS(element);
						staleElement = false;
					} catch (Exception exc) {
					}
					if (i == 4) {
						throw new CustomException(ex.getMessage(), ex);
					}
				}
			}

		} catch (Exception e) {
			throw new CustomException(e.getMessage(), e);
		}
	}

	public static void PressCTRLAndClick(WebElement element) {
		try {
			//Thread.sleep(2 * timeInterval);
			driver = WebBrowser.getBrowser();
			Actions actions = new Actions(driver);
			actions.moveToElement(element).keyDown(Keys.CONTROL).click().keyUp(Keys.CONTROL).build().perform();
			Thread.sleep(timeInterval);

		} catch (Exception ex) {
			/*	try {
				Thread.sleep(2 * timeInterval);
			} catch (InterruptedException e) {

			}*/
			throw new CustomException("Scroll and click unsuccessful" + ex.getMessage());
		}
	}

	public static int GetColumnIndex(WebElement _table, String colName) {
		Collection<WebElement> rows = null;
		Collection<WebElement> cells = null;
		try {

			if (_table.isDisplayed()) {
				rows = _table.findElements(By.tagName("tr"));
				for (WebElement row : rows) {
					if (!row.getText().isEmpty()) {
						cells = row.findElements(By.xpath("td"));
						if (cells.size() == 0) {
							cells = row.findElements(By.xpath("th"));
						}
						int columnNumber = 0;
						for (WebElement cell : cells) {
							if (cell.getText() != null && !cell.getText().isEmpty()) {
								if (cell.getText().toUpperCase().contains(colName.toUpperCase().trim())) {
									cells = null;
									return columnNumber;
								}
							}
							columnNumber++;
						}
					}
				}

				throw new CustomException("Table column with text '" + colName + "' does not exists");
			} else {

				throw new CustomException("Table does not exists");
			}
		} catch (Exception ex) {

			try {
				throw ex;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
			return 0;
		}
	}

	public static String IsReadOnly(WebElement element) {

		return element.getAttribute("readonly");

	}

	public static void ScrollAndCheck(WebElement element) throws InterruptedException, CustomException {
		// Log.Info("Scroll And Check is Successful");
		ScrollAndClickUsingJS(element);
		Thread.sleep(1000);
		if (!Checked(element)) {
			// Log.Info("Scroll And Check is Successful");
			element.click();
		}
	}

	public static void RightClick(WebElement element) {
		driver = WebBrowser.getBrowser();
		Actions actions = new Actions(driver);
		actions.moveToElement(element);
		actions.contextClick(element).build().perform();

	}

	public static void DoubleClick(WebElement element) {
		driver = WebBrowser.getBrowser();
		Actions actions = new Actions(driver);
		actions.moveToElement(element);
		actions.doubleClick(element).build().perform();

	}

	public static void ScrollAndClearEnterText(WebElement element, String text) throws InterruptedException {
		Scroll(element);
		try {
			element.click();
			element.clear();
			element.sendKeys(text);
			for (int i = 0; i < 1; i++) {
				if (element.getAttribute("value").equals(text)) {
					break;
					}
				else {
					element.sendKeys(Keys.CONTROL + "a");
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
					}
					element.sendKeys(text);
				}
			}
		} catch (Exception ex) {
			throw new CustomException("Scroll And Clear EnterText unsuccessful" + ex.getMessage());
		}
	}

	public static void ScrollAndUncheck(WebElement element) throws InterruptedException {

		ScrollAndClickUsingJS(element);
		Thread.sleep(1000);
		if (Checked(element)) {
			WebBrowserUtil.Click(element);
			//element.click();
		}
	}

	public static void ScrollAndWait() {
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 800)");
		try {
			Thread.sleep(timeInterval);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 1500)");
		try {
			Thread.sleep(3 * timeInterval);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
	}

	public static void DragAndDropHorizontally(WebElement sourceElement, int distance) {
		try {
			//Point startLocation = sourceElement.getLocation();
			driver = WebBrowser.getBrowser();
			// create object 'action' of Actions class
			Actions action = new Actions(driver);
			action.dragAndDropBy(sourceElement, distance, 0);
			action.build().perform();
			Thread.sleep(2 * timeInterval);

		} catch (Exception ex) {

			// Thread.Sleep(2 * timeInterval);
			throw new CustomException("Drag And Drop Horizontally unsuccessful" + ex.getMessage());
		}
	}

	public static void DragAndDropHorizontally(WebElement sourceElement, WebElement targetElement) {
		try {
			//Point startLocation = sourceElement.getLocation();
			driver = WebBrowser.getBrowser();
			// create object 'action' of Actions class
			Actions action = new Actions(driver);
			action.dragAndDrop(sourceElement, targetElement);
			action.build().perform();
			Thread.sleep(2 * timeInterval);

		} catch (Exception ex) {
			throw new CustomException("Drag And Drop Horizontally unsuccessful" + ex.getMessage());
		}
	}


	public static void ScrollDown(String param) {
		Actions actions = new Actions(driver);
		param = param.toLowerCase().replace(" ", "");
		if (param.contains("pagedown")) {
			Pattern pattern = Pattern.compile("\\d+");
			Matcher matcher = pattern.matcher(param);
			if (matcher.find()) {
				String numberString = matcher.group();
				int number = Integer.parseInt(numberString);
				for (int i = 0; i < number; i++) {
					try {
						actions.moveToElement(driver.findElement(By.tagName("body"))).sendKeys(Keys.PAGE_DOWN)
								.perform();
						Thread.sleep(3 * timeInterval);
					} catch (Exception e) {
					}
				}
			}
		} else if (param.contains("pageup")) {
			Pattern pattern = Pattern.compile("\\d+");
			Matcher matcher = pattern.matcher(param);
			if (matcher.find()) {
				String numberString = matcher.group();
				int number = Integer.parseInt(numberString);
				for (int i = 0; i < number; i++) {
					try {
						actions.moveToElement(driver.findElement(By.tagName("body"))).sendKeys(Keys.PAGE_UP).perform();
						Thread.sleep(3 * timeInterval);
					} catch (Exception e) {
					}
				}
			}
		} else if (param.contains("downarrow")) {
			Pattern pattern = Pattern.compile("\\d+");
			Matcher matcher = pattern.matcher(param);
			if (matcher.find()) {
				String numberString = matcher.group();
				int number = Integer.parseInt(numberString);
				for (int i = 0; i < number; i++) {
					try {
						actions.moveToElement(driver.findElement(By.tagName("body"))).sendKeys(Keys.ARROW_DOWN)
								.perform();
						Thread.sleep(3 * timeInterval);
					} catch (Exception e) {
					}
				}
			}
		} else if (param.contains("uparrow")) {
			Pattern pattern = Pattern.compile("\\d+");
			Matcher matcher = pattern.matcher(param);
			if (matcher.find()) {
				String numberString = matcher.group();
				int number = Integer.parseInt(numberString);
				for (int i = 0; i < number; i++) {
					try {
						actions.moveToElement(driver.findElement(By.tagName("body"))).sendKeys(Keys.ARROW_UP).perform();
						Thread.sleep(3 * timeInterval);
					} catch (Exception e) {
						// Handle exception or add logging here
					}
				}
			}
		} else {
			int scrollValue = Integer.parseInt(param);
			for (int i = 0; i < Math.abs(scrollValue); i++) {
				try {
					if (scrollValue > 0) {
						((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 2000)"); // Scroll down
					} else {
						((JavascriptExecutor) driver).executeScript("window.scrollBy(0, -2000)"); // Scroll up
					}
					Thread.sleep(3 * timeInterval);
				} catch (Exception e) {
				}
			}
		}
	}

	public static void ScrollVerifyTooltip(String info,String mousehover) {
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0)");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {		
			// e1.printStackTrace();
		}
		for (int i = 0; i < 10; i++)
		{	 
			if(i==0)
			{
				((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 100)");
			}
			else if(i==1)
			{
				((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 200)");
			}
			else if(i==2)
			{
				((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 300)");
			}
			else if(i==3)
			{
				((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 400)");
			}
			else if(i==4)
			{
				((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 500)");
			}
			else if(i==5)
			{
				((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 600)");
			}
			else if(i==6)
			{
				((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 700)");
			}
			else if(i==7)
			{
				((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 800)");
			}
			else if(i==8)
			{
				((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 900)");
			}
			else if(i==9)
			{
				((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 1000)");
			}
			try {
				Thread.sleep(timeInterval);
				WebElement hoverEle =WebBrowserUtil.getSingleElementByXpath(info);
				if(hoverEle.isDisplayed())
				{
					WebBrowserUtil.MouseHover(hoverEle);
					Thread.sleep(2000);
					WebElement eleTooltip=WebBrowserUtil.getSingleElementByXpath(mousehover);
					if(eleTooltip.isDisplayed()) {
						break;
					}
				}
			} catch (Exception e) {		 
				// e.printStackTrace();
			}		 
		}
	}
	public static void ScrollToTheElement(int numberOfTimes,WebElement element) {
		for (int i = 0; i < numberOfTimes; i++) {		
			try {				 
				Thread.sleep(2*timeInterval);
				((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", element);
				Thread.sleep(2*timeInterval);
				System.out.println(" Scrolling "+i+" time(s)");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				throw new CustomException(e.getMessage());
			}	

		}
	}

	public static void ScrollVertically(int numberOfTimes) {

		try {

			long endHeight=400;
			long startHeight=0;
			String scrollCommand= "window.scrollTo(startHeight, endHeight)";
			long lastHeight=0;
			long newHeight=0;

			while (true) {

				lastHeight = (long) ((JavascriptExecutor) driver).executeScript("return document.body.scrollHeight");  

				System.out.println(" Last hight "+lastHeight);
				//((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
				if (endHeight<=lastHeight) {
					System.out.println("Scroll start value "+startHeight +" and end value "+endHeight);
					String cmd = scrollCommand.replace("startHeight", String.valueOf(startHeight)).replace("endHeight", String.valueOf(endHeight));
					((JavascriptExecutor) driver).executeScript(cmd);
				}

				else {
					String cmd = scrollCommand.replace("startHeight", String.valueOf(startHeight)).replace("endHeight", String.valueOf(lastHeight));
					((JavascriptExecutor) driver).executeScript(cmd);
					Thread.sleep(3000);
					break;
				}
				Thread.sleep(3000);

				newHeight = (long) ((JavascriptExecutor) driver).executeScript("return document.body.scrollHeight");
				System.out.println(" new height "+newHeight);
				startHeight=endHeight;
				endHeight=startHeight+endHeight;

				Thread.sleep(3000);
				lastHeight = newHeight;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}


	}


	public static void MouseHover(WebElement element) {
		driver = WebBrowser.getBrowser();
		new Actions(driver).moveToElement(element).perform();
	}

	public static void ClickBrowserBackButton() {
		driver = WebBrowser.getBrowser();
		driver.navigate().back();

	}

	public static void Click(WebElement element) throws InterruptedException {
		try {
			driver = WebBrowser.getBrowser();

			try {
				element.click();
				// Log.Info("Click Successful");

			} catch (Exception ex) {
				// Log.Info("Scroll and click Successful");
				((JavascriptExecutor) driver).executeScript("arguments[0].click()", element);
			}
		} catch (Exception ex) {

			throw new CustomException("Click unsuccessful" + ex.getMessage());
		}
	}
	public static void ScrollAndClickUsingJS(WebElement element) {
		driver = WebBrowser.getBrowser();
		Actions actions = new Actions(driver);
		try {
			//actions.moveToElement(element).perform();
			if (element != null) {

				if (element.isDisplayed()) {
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
					wait.until(ExpectedConditions.elementToBeClickable(element));
					//((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
					//element = driver.findElement(By.xpath(element.getAttribute("xpath")));
					((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
				} else {
					System.out.println("In first else");
					actions.moveToElement(element).perform();
					actions.moveToElement(element).click().build().perform(); }
			}
			else {
				System.out.println("Element is not present!!!");
			}
		} catch (Exception ex) {
			throw new CustomException("Scroll and click unsuccessful" + ex.getMessage());
		}
	}
public static void ScrollAndClickUsingJS(String xpath,String identifier) {
		int i=0;
		boolean staleElement=true;
		try {

			while(i<4 && staleElement)
			{
			WebElement element=WebBrowserUtil.findElement(xpath,identifier);
			try {
				Actions actions = new Actions(driver);
				actions.moveToElement(element).perform();
				((JavascriptExecutor) driver).executeScript("arguments[0].click()", element);
				// Log.Info("Scroll and click Successful");
				staleElement=false;
			} catch (Exception e) {
				// Log.Info("Click Successful");
				i++;
				if(i==3)
				{
					throw new CustomException("Scroll and click unsuccessful" + e.getMessage());
				}
			}
		}
		} catch (Exception ex) {
			throw new CustomException("Scroll and click unsuccessful" + ex.getMessage());
		}
	}

	public static void ClearAndEnterText(WebElement element, String text) {
		try {
			element.click();
			element.clear();
			element.sendKeys(text);
			for (int i = 0; i < 1; i++) {
				if (element.getAttribute("value").equals(text)) {
					break;
					}
				else {
					element.sendKeys(Keys.CONTROL + "a");
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
					}
					element.sendKeys(text);
				}
			}
		} catch (Exception ex) {
			throw new CustomException(" Clear And EnterText unsuccessful" + ex.getMessage());
		}
	}
	public static void OpenNewTabAndNavigate(String url) {
		try {
			driver = WebBrowser.getBrowser();
			// To open a new tab
			Robot r = new Robot();
			r.keyPress(KeyEvent.VK_CONTROL);
			r.keyPress(KeyEvent.VK_T);
			r.keyRelease(KeyEvent.VK_CONTROL);
			r.keyRelease(KeyEvent.VK_T);
			// ((JavascriptExecutor)driver).executeScript("window.open();");
			Thread.sleep(4 * timeInterval);
			driver.switchTo().window(
					driver.getWindowHandles().toArray()[driver.getWindowHandles().toArray().length - 1].toString());
			driver.navigate().to(url);
			Thread.sleep(2 * timeInterval);
			// Log.Info("New tab opened with URL: " + url);
		} catch (Exception ex) {
			// Log.Error("Error while opening new tab " + ex);
			throw new CustomException(ex.getMessage());
		}
	}

	public static void waitForElementToBeHidden(String xPath)
	{
		try {
			driver = WebBrowser.getBrowser();
			int i=0;
			while (i < maxDelay / timeInterval) {
				try {
					//driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
					Thread.sleep(timeInterval);
					WebElement element=driver.findElement(By.xpath(xPath));
					if(element==null)
					{
						//	driver.manage().timeouts().implicitlyWait(controlLoadTimeout, TimeUnit.SECONDS);
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(controlLoadTimeout));
						break;
					}
					i++;					
				} catch (Exception e) {
					i++;
					Thread.sleep(timeInterval);	
					//	driver.manage().timeouts().implicitlyWait(controlLoadTimeout, TimeUnit.SECONDS);
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(controlLoadTimeout));
					break;
				}
			}
			//new WebDriverWait(driver, controlLoadTimeout).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xPath)));
		} catch (Exception ex) {
			// Log.Error("Error while opening new tab " + ex);
			throw new CustomException(ex.getMessage());
		}
	}

	public static void waitForElementToBeVisible(String xPath)
	{
		try {
			driver = WebBrowser.getBrowser();
			int i=0;
			while (i < maxDelay / timeInterval) {
				try {
					//driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
					Thread.sleep(timeInterval);
					WebElement element=driver.findElement(By.xpath(xPath));
					if(element!=null)
					{
						//	driver.manage().timeouts().implicitlyWait(controlLoadTimeout, TimeUnit.SECONDS);
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(controlLoadTimeout));
						break;
					}
					i++;					
				} catch (Exception e) {
					i++;
					Thread.sleep(timeInterval);	
					//	driver.manage().timeouts().implicitlyWait(controlLoadTimeout, TimeUnit.SECONDS);
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(controlLoadTimeout));
					break;
				}
			}
			//new WebDriverWait(driver, controlLoadTimeout).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xPath)));
		} catch (Exception ex) {
			// Log.Error("Error while opening new tab " + ex);
			throw new CustomException(ex.getMessage());
		}
	}


	public static void uploadFile(String fileName)
	{
		try
		{
			//put path to your image in a clipboard
			StringSelection ss = new StringSelection(fileName);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);

			//imitate mouse events like ENTER, CTRL+C, CTRL+V
			Robot robot = new Robot();
			robot.delay(4000);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.delay(50);
			robot.keyRelease(KeyEvent.VK_ENTER);
		}
		catch (Exception ex) {
			// Log.Error("Error while opening new tab " + ex);
			throw new CustomException(ex.getMessage());
		}
	}

	public static boolean validateDropdownDataSize(WebElement element) {

		Select dropdown = new Select(element);

		List<WebElement> allOptionsElement = dropdown.getOptions();
		int k=allOptionsElement.size();
		if(k==1)

		{return true;
		}
		else
		{
			return false;
		}


	}

	public static List<WebElement> getElementByXpathFromIframe(String xpath) {

		List<WebElement> list=null;
		int size = driver.findElements(By.tagName("iframe")).size();

		if(size >0) {
			for(int i=0; i<=size; i++){
				driver.switchTo().frame(i);
				list=driver.findElements(By.xpath(xpath));
				if(list.size()>0) {
					driver.switchTo().defaultContent();
					return list;
				}

				driver.switchTo().defaultContent();
			}
		}else {
			return getElementByXpath(xpath);
		}

		return null;

	}

	public static void getElementByXpathFromIframeAndClick(String xpath) {

		List<WebElement> list = null;
		int size = driver.findElements(By.tagName("iframe")).size();

		if (size > 0) {
			for (int i = 0; i <= size; i++) {
				driver.switchTo().frame(i);
				list = driver.findElements(By.xpath(xpath));
				if (list.size() > 0) {
					try {
						Click(list.get(0));
						driver.switchTo().defaultContent();
						return;
					} catch (InterruptedException e) {
						driver.switchTo().defaultContent();
					}catch (Exception e) {
						driver.switchTo().defaultContent();
					}

				}
				driver.switchTo().defaultContent();
			}

		}else {
			try {
				Click(getElementByXpath(xpath).get(0));
			} catch (Exception e) {

			}
		}
	}


	public static void openCmd(String cmd) {
		try {
			Runtime.getRuntime().exec(new String[] { "cmd.exe","/K", "Start",cmd});
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	public static boolean validateDateFormat(String date,String format) {
		/*String regex  = "^(?:(?:31(\\/|-|\\.| )(?:0?[13578]|1[02]|(?:Jan|Mar|May|Jul|Aug|Oct|Dec)))\\1|(?:(?:29|30)(\\/|-|\\.| )(?:0?[1,3-9]|1[0-2]|(?:Jan|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec))\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.| )(?:0?2|(?:Feb))\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.| )(?:(?:0?[1-9]|(?:Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep))|(?:1[0-2]|(?:Oct|Nov|Dec)))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";
		String regex1 = "^(?:(?:31(\\/|-|\\.| )(?:0?[13578]|1[02]|(?:Jan|Mar|May|Jul|Aug|Oct|Dec)))\\1|(?:(?:29|30)(\\/|-|\\.| )(?:0?[1,3-9]|1[0-2]|(?:Jan|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec))\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.| )(?:0?2|(?:Feb))\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.| )(?:(?:0?[1-9]|(?:Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep))|(?:1[0-2]|(?:Oct|Nov|Dec)))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2}) \\d{1,2}:\\d{1,2}:\\d{1,2}$";
		String regex2 = "^(?:(?:31(\\/|-|\\.| )(?:0?[13578]|1[02]|(?:Jan|Mar|May|Jul|Aug|Oct|Dec)))\\1|(?:(?:29|30)(\\/|-|\\.| )(?:0?[1,3-9]|1[0-2]|(?:Jan|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec))\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.| )(?:0?2|(?:Feb))\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.| )(?:(?:0?[1-9]|(?:Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep))|(?:1[0-2]|(?:Oct|Nov|Dec)))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2}) \\d{1,2}:\\d{1,2}$";
	//	String regex="^\\d{1,2}\\/\\d{1,2}\\/\\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2}";
 Pattern pattern = Pattern.compile(regex);
 Pattern pattern1 = Pattern.compile(regex1);
 Pattern pattern2 = Pattern.compile(regex2);

 Matcher matcher = pattern.matcher((CharSequence)date);
 Matcher matcher1 = pattern1.matcher((CharSequence)date);
 Matcher matcher2 = pattern2.matcher((CharSequence)date);
 if( matcher.matches() || matcher1.matches() || matcher2.matches())
 {
	return true;
 }
 else
 {
	return false;
 }*/
		List<String> formatStrings = Arrays.asList(format);
		for (String formatString : formatStrings)
		{
			try
			{
				new SimpleDateFormat(formatString).parse(date);
				return true;
			}
			catch (Exception e) {
				return false;
			}
		}

		return false;
	}

	public static boolean verifyDateFormat(String xpath,String text) {
		boolean result=false;
		String[] splitText=text.split("--");
		if(text.contains("verify_list"))
		{
			List<WebElement> links =getElementByXpath(xpath);
			String printData="";
			for(int i=0;i<links.size();i++)
			{
				String eleText=WebBrowserUtil.GetText(links.get(i));
				if(eleText=="" || eleText==null || eleText.isEmpty())
				{
					printData=printData+eleText+",";
					result=true;
				}
				else if(WebBrowserUtil.validateDateFormat(eleText,splitText[1])==false)
				{
					ExtentCucumberAdapter.addTestStepLog(eleText+" is not correct date format");
					return false;
				}
				else
				{
					printData=printData+eleText+",";
					result=true;
				}

			}
			ExtentCucumberAdapter.addTestStepLog(printData+" are in correct date format");
			return result;
		}
		else
		{
			WebElement links =getSingleElementByXpath(xpath);
			String eleText=WebBrowserUtil.GetText(links);
			if(eleText=="" || eleText==null || eleText.isEmpty())
			{
				ExtentCucumberAdapter.addTestStepLog("Element is null");
				result=true;
			}
			else if(WebBrowserUtil.validateDateFormat(eleText,splitText[1])==false)
			{
				ExtentCucumberAdapter.addTestStepLog(eleText+" is not correct date format");
				return false;
			}
			else
			{
				ExtentCucumberAdapter.addTestStepLog(eleText+" is correct date format");
				result=true;
			}
			return result;
		}
	}
	public static void enterDataInExcel(String path) {
		//path="path_enter-row"
		//path="path_update-cell_idToFindRowNumber_columnNumber_valueToBeUpdated"
		String[] splitText=path.split("--");
		String excelFilePath = splitText[0];
		try {

			File file=new File(excelFilePath);
			FileInputStream input_document = new FileInputStream(file);
			// convert it into a POI object
			XSSFWorkbook my_xlsx_workbook = new XSSFWorkbook(input_document); 
			// Read excel sheet that needs to be updated
			XSSFSheet my_worksheet = my_xlsx_workbook.getSheetAt(0);
			if(path.contains("enter-row"))
			{
				int lastRowIndex = 0;
				int lastCellIndex = 0;
				if( my_worksheet.getPhysicalNumberOfRows() > 0 )
				{

					lastRowIndex = my_worksheet.getLastRowNum();
					//Object [] objArr = new Object[] {};
					List<String> lastRowData=new ArrayList<>();
					Row row = my_worksheet.getRow( lastRowIndex );
					int colNum=row.getLastCellNum();
					System.out.println("total cell : "+colNum);
					for (int j = 0; j < colNum; j++) {
						final Cell cell = row.getCell(j);
						System.out.println("j : "+j+" value : "+cell);
						if(cell!=null)
						{
							String regex = "[0-9]+";
							Pattern p = Pattern.compile(regex);
							Matcher m = p.matcher(cell.toString());
							if(m.matches())
							{
								lastRowData.add(String.valueOf(Integer.parseInt(cell.toString())+1));
							}
							else
							{
								lastRowData.add(cell.toString());
							}

						}
						else
						{
							lastRowData.add("");
						}

					}
					input_document.close();

					Row rowNew = my_worksheet.createRow(lastRowIndex++);
					int cellnum = 0;
					for(String c : lastRowData)
					{
						Cell cell = rowNew.createCell(cellnum++);
						cell.setCellValue(c);
					}


				}}
			else if(path.contains("update-cell"))
			{
				String[][] excelData = null;

				int rows = my_worksheet.getPhysicalNumberOfRows();

				// get number of cell from row
				int cells = my_worksheet.getRow(0).getPhysicalNumberOfCells();

				excelData = new String[rows][cells];
				for(int p=0;p<rows;p++)
				{
					Row row2 = my_worksheet.getRow(p);
					for(int n=0;n<cells;n++)
					{

						Cell cell = row2.getCell(n);
						if(cell!=null)
						{
							excelData[p][n]=cell.toString();
						}
						else
						{
							excelData[p][n]="";   
						}
					} 
				}
				int rowCount=0;
				for(int l=0;l<excelData.length;l++)
				{
					if(excelData[l][0]!=null)
					{
						if(excelData[l][0].contains(splitText[2]))
						{
							rowCount=l;
							break;
						}
					}

				}
				input_document.close();
				Cell cellEdit = my_worksheet.getRow(rowCount).getCell(Integer.parseInt(splitText[3])); 
				if(cellEdit == null){
					Row rowNew = my_worksheet.getRow(rowCount);
					cellEdit = rowNew.createCell(Integer.parseInt(splitText[3]));
				}
				cellEdit.setCellValue(splitText[4]);
			}
			if(file!=null) {
				FileOutputStream fileOut = new FileOutputStream(file);

				//write this workbook to an Outputstream.
				if(my_xlsx_workbook!=null)
					my_xlsx_workbook.write(fileOut);
				fileOut.flush();
				fileOut.close();
			}
			if(my_xlsx_workbook!=null)
				my_xlsx_workbook.close();

		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage(),e);
		}
	}
	public static void enterDataInRequiredCell(String filePath) {
		//	path : path of the file,sheetname,celldata-rownumber-colnumber,celldata2-rownumber2-colnumber2
	        String[] splitPath = filePath.split(",");
	        String path = splitPath[0];
	        String sheetName = splitPath[1];

	        if (splitPath.length >= 3) {
	            try {
	                FileInputStream fis = new FileInputStream(new File(path));
	                XSSFWorkbook workbook = new XSSFWorkbook(fis);

	                if (workbook.getSheetIndex(sheetName) != -1) {
	                    XSSFSheet sheet = workbook.getSheet(sheetName);
	                    for (String data : splitPath) {
	                        if (data.contains("-")) {
	                        	 String[] entries = data.split("-");
	                                String cellValue = entries[0]; 
	                                int row = Integer.parseInt(entries[1]);
	                                int col = Integer.parseInt(entries[2]);
	                                XSSFRow sheetRow = sheet.getRow(row);
	                                if (sheetRow == null) {
	                                    sheetRow = sheet.createRow(row);
	                                }
	                                XSSFCell cell = sheetRow.getCell(col);
	                                if (cell == null) {
	                                    cell = sheetRow.createCell(col);
	                                }
	                                cell.setCellValue(cellValue);
//	                              sheet.getRow(row).getCell(col).setCellValue(cellValue);
	                        }
	                    }
	                    fis.close();
	                    FileOutputStream fos = new FileOutputStream(new File(path));
	                    workbook.write(fos);
	                    fos.close();
	                    System.out.println("Data is entered into excel sheet");
	                }
	            } catch (IOException e) {
	                System.out.println("Unable to enter and save to " + path);
	                e.printStackTrace();
	            }
	        } else {
	            try {
	                FileInputStream fis = new FileInputStream(new File(filePath));
	                XSSFWorkbook workbook = new XSSFWorkbook(fis);
	                XSSFSheet sheet = workbook.getSheetAt(0);

	                for (String data : splitPath) {
	                    if (data.contains("-")) {
	                    	String[] entries = data.split("-");
	                        String cellValue = entries[0]; 
	                        int row = Integer.parseInt(entries[1]);
	                        int col = Integer.parseInt(entries[2]);
	                            XSSFRow sheetRow = sheet.getRow(row);
	                            if (sheetRow == null) {
	                                sheetRow = sheet.createRow(row);
	                            }
	                            XSSFCell cell = sheetRow.getCell(col);
	                            if (cell == null) {
	                                cell = sheetRow.createCell(col);
	                            }
	                            cell.setCellValue(cellValue);
	                            sheet.getRow(row).getCell(col).setCellValue(cellValue);
	                        }
	                    }
	                
	                fis.close();
	                FileOutputStream fos = new FileOutputStream(new File(filePath));
	                workbook.write(fos);
	                fos.close();
	                System.out.println("Data is entered into excel sheet");
	            } catch (IOException e) {
	                System.out.println("Unable to enter and save to " + filePath);
	                e.printStackTrace();
	            }
	        }
	        }

	public static boolean verifyValueinJson(String path) {
		try {

			String[] splitedText=path.split("--");
			String[] splitKey=splitedText[1].split("\\.");
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader(splitedText[0]));
			//String value = (String) jsonObject.get("Items");

			JSONArray jsonArray = (JSONArray) jsonObject.get(splitKey[0]);
			String value=jsonArray.get(0).toString();
			HashMap<String, Object> userResponseMap = new HashMap<String,Object>();
			ObjectMapper mapper = new ObjectMapper();
			userResponseMap = mapper.readValue(value, new TypeReference<HashMap<String,Object>>(){});
			String finalString=userResponseMap.get(splitKey[1]).toString();
			ExtentCucumberAdapter.addTestStepLog(finalString+" length is : "+finalString.length());
			if(finalString.length()==Integer.parseInt(splitedText[2]))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		catch(Exception e)
		{
			ExtentCucumberAdapter.addTestStepLog("Error : "+e);
			return false;
		}
	}
	public static boolean ReadPDF(String url,String pdf)  {
		try {
			URL TestURL = new URL(url);

			RandomAccessBufferedFileInputStream TestFile = new RandomAccessBufferedFileInputStream(TestURL.openStream());
			PDFParser TestPDF = new PDFParser(TestFile);
			TestPDF.parse();
			String TestText = new PDFTextStripper().getText(TestPDF.getPDDocument());
			if(TestText.contains(pdf)) {
				System.out.println("TestText is present");
				ExtentCucumberAdapter.addTestStepLog("PDF content : "+TestText+" , Expected content : "+ pdf);
				TestFile.close();
				return true;
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			ExtentCucumberAdapter.addTestStepLog("Error : "+e);
		}
		return false;		
	}
	public static void takescrenshot() {
		if(WebBrowser.boolEachstepScreenshot) {
			driver = WebBrowser.getBrowser();
			String dest = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
			try {
				ExtentCucumberAdapter.addTestStepScreenCaptureFromPath("data:image/jpg;base64, " + dest);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static boolean AsecndingNumber(List<String> options) {
		boolean orderStatus = false;		
		List<String> tempList = (List<String>) options.stream().sorted().collect(Collectors.toList());
		String englishRules1 = (" < 0 < 1 < 2 < 3 < 4 < 5 < 6 < 7 < 8 < 9");
		RuleBasedCollator rbc;		
		try {
			rbc = new RuleBasedCollator(englishRules1);			
			rbc.setStrength(Collator.PRIMARY);
			Collections.sort(tempList, rbc);
			System.out.println("After sorting tempList :" + tempList);
			orderStatus = options.equals(tempList);
			System.out.println(" orderStatus : " + orderStatus);
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return orderStatus;
	}
	public static boolean  GetdatafromDBandverifyosofdevice(String query, String graphValue)
	{
		boolean isverified=false;
		try
		{
			RedShiftUtil  redShiftUtil=new RedShiftUtil();
			List resultList = redShiftUtil.getExcelData(query);
			for (int i = 0; i < resultList.size(); i++)
			{
				HashMap row = (HashMap)resultList.get(i);
				for (Object mapVal : row.values())
				{
					System.out.println(mapVal.toString());
					if ((graphValue).contains(","))
					{
						graphValue=((String) graphValue).replaceAll("[^a-zA-Z0-9]","");
					}
					else if((graphValue).contains("%")) 
					{
						graphValue = graphValue.replace("%", "");
					}
					ExtentCucumberAdapter.addTestStepLog("UI Value: "+graphValue+", DB Value: "+mapVal.toString());

					if(graphValue.equals(mapVal.toString()))
					{
						isverified = true;
					}
					else
					{
						isverified= false;
					}
				}
			}
		}
		catch (Exception ex)
		{
			System.out.println("No data found");
			isverified= false;
		}
		if(!isverified) {
			ExtentCucumberAdapter.addTestStepLog("Assertion Failed");
		}
		return isverified;
	}
	public static boolean verifyexceldatafromdb(String sheetName) 
	{
		try {
			sheetName=CommonUtil.GetData(sheetName);//0049 List of all possible combinations - Lists method Complete.xlsx--1M1S
			String[] splitSheet= sheetName.split("--");
			String filepath = System.getProperty("user.dir")+"\\src\\test\\java\\attachments\\"+splitSheet[0];//filename


			FileInputStream input = new FileInputStream(filepath);			
			XSSFWorkbook workbook = new XSSFWorkbook(filepath);			
			XSSFSheet sheet = workbook.getSheet(splitSheet[1]);//name of excel sheet

			WebElement elem= WebBrowserUtil.getSingleElementByXpath("//span[text()='Reset']");
			elem.click();	

			int rows = sheet.getLastRowNum();
			String month= "";
			String src= "";
			String dst="";
			String toverify="";

			List<Boolean> myList = new ArrayList<>();				
			for(int r=1; r<=rows;r++) 
			{
				XSSFRow row =sheet.getRow(r);
				try 
				{
					month= row.getCell(0).getStringCellValue();
				}
				catch(Exception e1) 
				{
					month= null;
				}
				try 
				{
					src= row.getCell(1).getStringCellValue();
				}
				catch(Exception e2)
				{
					src= null;
				}
				try
				{
					dst= row.getCell(2).getStringCellValue();
				}
				catch(Exception e3) 
				{
					dst=null;
				}

				try
				{
					toverify= row.getCell(3).getStringCellValue();

				}
				catch(Exception e4)
				{
					toverify= null;
				}	

				String queryExtension= "";

				if((month!=null))
				{
					queryExtension= queryExtension+ "and (" ;
					String[] mult_mnth=month.split(",");
					for(int i=0; i<mult_mnth.length;i++) 
					{
						WebElement nametxt= driver.findElement(By.xpath("(//div[@class='ant-select-selector']//input)[1]"));
						nametxt.sendKeys(mult_mnth[i]);
						nametxt.sendKeys(Keys.ENTER);
						queryExtension= queryExtension+ "Month= '" + mult_mnth[i] + "'" ;
						if(mult_mnth.length>1 && i<(mult_mnth.length-1))
						{
							queryExtension= queryExtension+ " OR ";
						}
					}
					ExtentCucumberAdapter.addTestStepLog("Month: "+month);
					queryExtension= queryExtension+ ")" ;
				}

				if((src!=null))
				{
					queryExtension= queryExtension+ "and (" ;
					String[] mult_src=src.split(",");
					for(int i=0; i<mult_src.length;i++) {
						WebElement srctxt= driver.findElement(By.xpath("(//div[@class='ant-select-selector']//input)[2]"));
						srctxt.sendKeys(mult_src[i]);
						srctxt.sendKeys(Keys.ENTER);
						queryExtension= queryExtension+ "Source= '" + mult_src[i] + "'" ;
						if(mult_src.length>1 && i<(mult_src.length-1))
						{
							queryExtension= queryExtension+ " OR ";
						}
					}
					ExtentCucumberAdapter.addTestStepLog("Source: "+src);
					queryExtension= queryExtension+ ")" ;
				}
				if((dst!=null))
				{
					queryExtension= queryExtension+ "and (" ;
					String[] mult_dst=dst.split(",");
					for(int i=0; i<mult_dst.length;i++) {
						WebElement dsttxt= driver.findElement(By.xpath("(//div[@class='ant-select-selector']//input)[3]"));
						dsttxt.sendKeys(mult_dst[i]);
						dsttxt.sendKeys(Keys.ENTER);
						queryExtension= queryExtension+ "Destination= '" + mult_dst[i] + "'" ;
						if(mult_dst.length>1 && i<(mult_dst.length-1))
						{
							queryExtension= queryExtension+ " OR ";
						}
					}
					ExtentCucumberAdapter.addTestStepLog("Destination: "+dst);
					queryExtension= queryExtension+ ")" ;
				}

				//	driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
				WebElement elems= WebBrowserUtil.getSingleElementByXpath("//span[text()='Submit']");
				elems.click();
				//	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
				if(toverify.contains("totalTrips")) {
					String totaltripsdetails= WebBrowserUtil.getSingleElementByXpath(YMLUtil.getYMLObjectRepositoryData("MILE.TotalTripslLabelXpath")).getText();
					String query= CommonUtil.GetData("TotalTripsQuery");
					query =  CommonUtil.GetXMLData(
							Paths.get(path.toString(), "src", "test", "java", "DBSettings.xml").toString(), query);
					String dbquery=query.replace("@Dynamic_data", queryExtension);
					myList.add(WebBrowserUtil.GetdatafromDBandverifyosofdevice(dbquery,totaltripsdetails));
				}

				if(toverify.contains("validTrips")) {
					String validtripsdetails= WebBrowserUtil.getSingleElementByXpath(YMLUtil.getYMLObjectRepositoryData("MILE.ValidTripsLabelXpath")).getText();				
					String query= CommonUtil.GetData("ValidTripsQuery");
					query =  CommonUtil.GetXMLData(
							Paths.get(path.toString(), "src", "test", "java", "DBSettings.xml").toString(), query);
					String dbquery=query.replace("@Dynamic_data", queryExtension);
					myList.add(WebBrowserUtil.GetdatafromDBandverifyosofdevice(dbquery,validtripsdetails));
				}
				if(toverify.contains("openTrips")) {
					String opentripsdetails= WebBrowserUtil.getSingleElementByXpath(YMLUtil.getYMLObjectRepositoryData("MILE.OpenTripsLabelXpath")).getText();		
					String query= CommonUtil.GetData("OpenTripsQuery");
					query =  CommonUtil.GetXMLData(
							Paths.get(path.toString(), "src", "test", "java", "DBSettings.xml").toString(), query);
					String dbquery=query.replace("@Dynamic_data", queryExtension);
					myList.add(WebBrowserUtil.GetdatafromDBandverifyosofdevice(dbquery,opentripsdetails));

				}
				if(toverify.contains("closedTrips")) {
					String closedtripsdetails= WebBrowserUtil.getSingleElementByXpath(YMLUtil.getYMLObjectRepositoryData("MILE.ClosedTripsLabelXpath")).getText();			
					String query= CommonUtil.GetData("ClosedTripsQuery");
					query =  CommonUtil.GetXMLData(
							Paths.get(path.toString(), "src", "test", "java", "DBSettings.xml").toString(), query);
					String dbquery=query.replace("@Dynamic_data", queryExtension);
					myList.add(WebBrowserUtil.GetdatafromDBandverifyosofdevice(dbquery,closedtripsdetails));
				}
				if(toverify.contains("geofenceClosures")) {
					String geofenceclosuresdetails= WebBrowserUtil.getSingleElementByXpath(YMLUtil.getYMLObjectRepositoryData("MILE.GeofenceClosuresLabelXpath")).getText();			
					String query= CommonUtil.GetData("GeofenceClosuresQuery");
					query =  CommonUtil.GetXMLData(
							Paths.get(path.toString(), "src", "test", "java", "DBSettings.xml").toString(), query);
					String dbquery=query.replace("@Dynamic_data", queryExtension);
					myList.add(WebBrowserUtil.GetdatafromDBandverifyosofdevice(dbquery,geofenceclosuresdetails));
				}
				if(toverify.contains("forcefulClosures")) {
					String forcefulclosuresdetails= WebBrowserUtil.getSingleElementByXpath(YMLUtil.getYMLObjectRepositoryData("MILE.ForcefulClosuresLabelXpath")).getText();

					String query= CommonUtil.GetData("ForcefulClosuresQuery");
					query =  CommonUtil.GetXMLData(
							Paths.get(path.toString(), "src", "test", "java", "DBSettings.xml").toString(), query);
					String dbquery=query.replace("@Dynamic_data", queryExtension);
					myList.add(WebBrowserUtil.GetdatafromDBandverifyosofdevice(dbquery,forcefulclosuresdetails));
				}
				if(toverify.contains("delayTrips")) {
					String delaytripsdetails= WebBrowserUtil.getSingleElementByXpath(YMLUtil.getYMLObjectRepositoryData("MILE.DelayTripsLabelXpath")).getText();

					String query= CommonUtil.GetData("DelayTripsQuery");
					query =  CommonUtil.GetXMLData(
							Paths.get(path.toString(), "src", "test", "java", "DBSettings.xml").toString(), query);
					String dbquery=query.replace("@Dynamic_data", queryExtension);
					myList.add(WebBrowserUtil.GetdatafromDBandverifyosofdevice(dbquery,delaytripsdetails));
				}
				if(toverify.contains("averagedailydistance")) {
					String averagedailydistancedetails= WebBrowserUtil.getSingleElementByXpath(YMLUtil.getYMLObjectRepositoryData("MILE.AverageDailyDistanceLabelXpath")).getText();			
					String query= CommonUtil.GetData("AverageDailyDistanceQuery");
					query =  CommonUtil.GetXMLData(
							Paths.get(path.toString(), "src", "test", "java", "DBSettings.xml").toString(), query);
					String dbquery=query.replace("@Dynamic_data", queryExtension);
					myList.add(WebBrowserUtil.GetdatafromDBandverifyosofdevice(dbquery,averagedailydistancedetails));
				}
				if(toverify.contains("averagedaystodeliver")) {
					String averagedaystodeliverdetails= WebBrowserUtil.getSingleElementByXpath(YMLUtil.getYMLObjectRepositoryData("MILE.AverageDaysToDeliverLabelXpath")).getText();			
					String query= CommonUtil.GetData("AverageDaystoDeliverQuery");
					query =  CommonUtil.GetXMLData(
							Paths.get(path.toString(), "src", "test", "java", "DBSettings.xml").toString(), query);
					String dbquery=query.replace("@Dynamic_data", queryExtension);
					myList.add(WebBrowserUtil.GetdatafromDBandverifyosofdevice(dbquery,averagedaystodeliverdetails));
				}
				if(toverify.contains("otif")) {
					String otifdetails= WebBrowserUtil.getSingleElementByXpath(YMLUtil.getYMLObjectRepositoryData("MILE.OtifLabelXpath")).getText();				
					String query= CommonUtil.GetData("OtifQuery");
					query =  CommonUtil.GetXMLData(
							Paths.get(path.toString(), "src", "test", "java", "DBSettings.xml").toString(), query);
					String dbquery=query.replace("@Dynamic_data", queryExtension);
					myList.add(WebBrowserUtil.GetdatafromDBandverifyosofdevice(dbquery,otifdetails));
				}
				if(toverify.contains("carbonemission")) {
					String carbonemissiondetails= WebBrowserUtil.getSingleElementByXpath(YMLUtil.getYMLObjectRepositoryData("MILE.CarbonEmissionLabelXpath")).getText();			
					String query= CommonUtil.GetData("CarbonEmissionQuery");
					query =  CommonUtil.GetXMLData(
							Paths.get(path.toString(), "src", "test", "java", "DBSettings.xml").toString(), query);
					String dbquery=query.replace("@Dynamic_data", queryExtension);
					myList.add(WebBrowserUtil.GetdatafromDBandverifyosofdevice(dbquery,carbonemissiondetails));
				}
				WebElement elex= WebBrowserUtil.getSingleElementByXpath("//span[text()='Reset']");
				elex.click();
				//	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			}

			for(int i=0; i<myList.size();i++) 
			{
				if(!myList.get(i)) 
				{
					return false;
				}	
			}

			workbook.close();
			input.close();
		}
		catch(Exception e) 
		{
			ExtentCucumberAdapter.addTestStepLog("Error: "+e);
			return false;
		}
		return true;
	}

	public static boolean CompareAndVerifyImage(String imageName){     
		boolean verified = false;
		driver = WebBrowser.getBrowser();
		File source = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			boolean EnableCompareImage =Boolean.parseBoolean( CommonUtil.GetXMLData(
					Paths.get(path.toString(), "src", "test", "java", "ApplicationSettings.xml").toString(),
					"EnableCompareImage"));

			String CompareImages = "src/test/java/Images/Compare/";
			File file = new File(CompareImages);
			file.mkdirs();

			String BaseImages = "src/test/java/Images/Baseline/";
			File file1 = new File(BaseImages);
			file1.mkdirs();

			if(EnableCompareImage==false){ 
				File Dest = new File(BaseImages+imageName+".jpeg");				 
				FileUtils.copyFile(source,Dest);
				return verified; 
			}
			else  
			{
				File Dest = new File(CompareImages+imageName+".jpeg");                  
				FileUtils.copyFile(source,Dest);
				BufferedImage imgA = ImageIO.read(new File(CompareImages+imageName+".jpeg"));
				 
				DataBuffer dbA = imgA.getData().getDataBuffer();
				int sizeA = dbA.getSize(); 

				BufferedImage imgB = ImageIO.read(new File(BaseImages+imageName+".jpeg"));				
				DataBuffer dbB = imgB.getData().getDataBuffer();
				int sizeB = dbB.getSize();
				//				 if (imgA.getWidth() == imgB.getWidth() && imgA.getHeight() == imgB.getHeight()) {
				//					 for (int x = 0; x < imgA.getWidth(); x++) {
				//						 for (int y = 0; y < imgA.getHeight(); y++) {
				//							 if (imgA.getRGB(x, y) != imgB.getRGB(x, y)) {
				//								 System.out.println("Images A resolution width: "+imgA.getWidth()+" and height: "+imgA.getHeight());
				//								 System.out.println("Images B resolution width: "+imgB.getWidth()+" and height: "+imgB.getHeight());
				//								 ExtentCucumberAdapter.addTestStepLog("Images are not same");
				//								 return false;}
				//						 }
				//					 }
				//				 } 
				if(sizeA == sizeB) {
					for(int i=0; i<sizeA; i++) { 
						if(dbA.getElem(i) != dbB.getElem(i)) {	
							ExtentCucumberAdapter.addTestStepLog("Images are not same");
							return verified;
						}
					}
					ExtentCucumberAdapter.addTestStepLog("Images are same");
					verified = true;
				}
				else {                                     
					ExtentCucumberAdapter.addTestStepLog("Images are not same");
					verified = false;
				}
			}
		}catch(Exception ex) {
			verified = false;
			System.err.println("Error: "+ex.getMessage());			
		}
		//ExtentCucumberAdapter.addTestStepLog("Images are same");
		return verified;              

	}     //End of compare image


	public static String getlatestfile(String ext) {
		File theNewestFile = null;
		try {
			String path = System.getProperty("user.dir");

			File dir = new File(path);
			FileFilter fileFilter = new WildcardFileFilter("*." +ext);
			//FileFilter fileFilter = new WildcardFileFilter("*.pdf*");
			File[] files = dir.listFiles(fileFilter);

			String fileExtension = null;

			if (files.length > 0) {
				/** The newest file comes first **/
				Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
				theNewestFile = files[0];
				System.out.println("theNewestFile is :------" + theNewestFile);

			}


		}
		catch(Exception e) 
		{
			ExtentCucumberAdapter.addTestStepLog("Error: "+e);
			throw new CustomException(e.getMessage(), e);
		}
		return theNewestFile.toString();
	}
	
	
	public static void ScrollRandomlyByNumber(String xpath, String identifier, String text) {
		int i = 0;
		boolean staleElement = true;
		driver = WebBrowser.getBrowser();
		int num = Integer.parseInt(text);
		try {
			while (i < 4 && staleElement) {
				WebElement element = WebBrowserUtil.findElement(xpath, identifier);
				try {

					JavascriptExecutor js = (JavascriptExecutor) driver;
					for (int j = 0; j < num; j++) {
						js.executeScript("arguments[0].scrollTop += 350;", element); 
						// Wait for a short while for the scroll to take effect
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					staleElement = false;
				} catch (Exception e) {
					i++;
					if (i == 3) {
						throw new CustomException("Scroll unsuccessful" + e.getMessage());
					}
				}
			}
		} catch (Exception ex) {
			throw new CustomException("Scroll  unsuccessful" + ex.getMessage());
		}
	}

	public static void deleteFile(String filePath) {
		try {
			filePath = CommonUtil.GetData(filePath);
			File fileToBeDeleted = new File(filePath);
			if (fileToBeDeleted.exists()) {
				if (fileToBeDeleted.delete()) {
					ExtentCucumberAdapter.addTestStepLog("Deleted the file: " + fileToBeDeleted.getName());
				} else {
					ExtentCucumberAdapter.addTestStepLog("Failed to delete the file.");
				}
			} else {
				ExtentCucumberAdapter.addTestStepLog("File does not exists.");
			}
		} catch (Exception e) {
			throw new CustomException("Failed to delete the file." + e.getMessage());
		}
	}

	public static boolean verifyPDFData(String filePath) {
		boolean isVerified = false;
		String actualText = "";
		try {
			filePath = CommonUtil.GetData(filePath);
			String[] data = filePath.split(",");
			filePath = data[0];
			if (!(data[0].contains(":"))) {
				filePath = WebBrowserUtil.getlatestfile("pdf");
			}
			actualText = common.PDFUtil.getText(filePath);
			System.out.println("PDF data: " + actualText);
			ExtentCucumberAdapter.addTestStepLog("PDF data: " + actualText);
			String[] expectedText = data[1].split("--");
			String verifyText = "";
			if (actualText != null && !actualText.trim().isEmpty()) {
				for (int i = 0; i < expectedText.length; i++) {
					if (actualText.contains(expectedText[i])) {
						verifyText = verifyText + "true,";
						System.out.println(expectedText[i] + " is present in pdf data");
						ExtentCucumberAdapter.addTestStepLog(expectedText[i] + " is present in pdf data");
					} else {
						ExtentCucumberAdapter.addTestStepLog(expectedText[i] + " is NOT present in pdf data");
						verifyText = verifyText + "false,";
					}
				}
			}
			if (verifyText.contains("false")) {
				isVerified = false;
			} else {
				isVerified = true;
			}
			return isVerified;
		} catch (Exception ex) {
			return isVerified;
		}
	}

}