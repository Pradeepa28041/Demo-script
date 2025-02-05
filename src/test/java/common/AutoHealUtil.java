package common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import org.unix4j.Unix4j;
import org.unix4j.line.Line;
import org.unix4j.unix.Grep;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import io.restassured.response.Response;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.errorprone.annotations.Var;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AutoHealUtil {
	public static String XPath;
	public static String XPathKey;

	public static void SaveConfigDeatils() {
		try {
			String path = System.getProperty("user.dir");
			String autoHealingString = CommonUtil.GetXMLData(
					Paths.get(path.toString(), "src", "test", "java", "ApplicationSettings.xml").toString(),
					"AutoHealing");
			Boolean autoHealing = Boolean.valueOf(autoHealingString);
			if (autoHealing) {
				updtaeXML();
			}
		} catch (Exception ex) {

		}
	}

	public static void updtaeXML() {
		try {
			String workingDirectory;
			// here, we assign the name of the OS, according to Java, to a variable...
			String OS = (System.getProperty("os.name")).toUpperCase();
			// to determine what the workingDirectory is.
			// if it is some version of Windows
			if (OS.contains("WIN")) {
				// it is simply the location of the "AppData" folder
				workingDirectory = System.getenv("LOCALAPPDATA");
			}
			// Otherwise, we assume Linux or Mac
			else {
				// in either case, we would start in the user's home directory
				workingDirectory = System.getProperty("user.home");
				// if we are on a Mac, we are not done, we look for "Application Support"
				workingDirectory += "/Library/Application Support";
			}
			String autoHealPath = Paths.get(workingDirectory, "AlgoAF", "AutoHeal").toString();
			try {
				Files.createDirectories(Paths.get(autoHealPath));
			} catch (Exception ex) {

			}
			String xmlFile = Paths.get(autoHealPath, "AFConfig.xml").toString();
			String htmlFile = Paths.get(autoHealPath, "WebPage.html").toString();

			String path = System.getProperty("user.dir");
			String objectRepositoryPath = Paths.get(path.toString(), "src", "test", "java", "ObjectRepository.yml")
					.toString();

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("Configuration");
			doc.appendChild(rootElement);
			
			Element autType = doc.createElement("AutomationType");
			autType.appendChild(doc.createTextNode("Web"));
			rootElement.appendChild(autType);
			
			Element autoHeal = doc.createElement("AutoHeal");
			autoHeal.appendChild(doc.createTextNode("True"));
			rootElement.appendChild(autoHeal);

			Element projectPath = doc.createElement("ObjectRepositoryFile");
			projectPath.appendChild(doc.createTextNode(objectRepositoryPath));
			rootElement.appendChild(projectPath);

			Element xPathKey = doc.createElement("XPathKey");
			xPathKey.appendChild(doc.createTextNode(XPathKey));
			rootElement.appendChild(xPathKey);

			Element xPath = doc.createElement("XPath");
			xPath.appendChild(doc.createTextNode(XPath));
			rootElement.appendChild(xPath);

			Element xPathUpdatedStatus = doc.createElement("XPathUpdatedStatus");
			xPathUpdatedStatus.appendChild(doc.createTextNode("False"));
			rootElement.appendChild(xPathUpdatedStatus);

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(xmlFile));
			transformer.transform(source, result);
			System.out.println("XML File saved!");

			try {
				WebDriver driver = WebBrowser.getBrowser();
				// Create file
				FileWriter fstream = new FileWriter(htmlFile);
				BufferedWriter out = new BufferedWriter(fstream);
				out.write(driver.getPageSource());
				// Close the output stream
				out.close();
				System.out.println("HTML data saved!");
			} catch (Exception e) {// Catch exception if any
				System.err.println("Error: " + e.getMessage());
			}
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}
}