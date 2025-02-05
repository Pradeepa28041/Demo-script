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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import org.unix4j.Unix4j;
import org.unix4j.line.Line;
import org.unix4j.unix.Grep;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import io.restassured.response.Response;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;

//import com.cucumber.listener.Reporter;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.errorprone.annotations.Var;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.openqa.selenium.UnhandledAlertException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CommonUtil {
	private static String labelNumber;
	private static int randomlabelNumber;
	private static String alphaNumber32;
	private static String alphaNumber64Epoch;
	private static String labelText;
	private static String applicationType;
	private static String testRailID;
	private static String randomlabelText;
	private static int columnIndex;
	private static String APIURL;
	private static String MethodType;
	private static String RequestParameters;
	private static String APIHeaders;
	private static String APIendpoint;
	private static String Maxhour;
	private static String BasicAuth;
	private static String APIparameter;
	private static String responseHeader;
	private static int copiedCount;
	private static int randomcopiedCount;
	private static int alpha32Count;
	private static int alpha64Count;
	private static String maxDuration;
	private static String minDuration;
	private static String dbValue;
	private static Response apiResponse;
	public static String appUrl;
	public static String browserName;
	public static String apiCmdUrl;
	private static List<String> copiedValues = new ArrayList<String>();
	private static List<String> globalValues = new ArrayList<String>();
	private static List<String> globalRandomValues = new ArrayList<String>();
	private static Map<String, String> apiResponseDictionary = new HashMap<String, String>();
	public static Map<String, String> apiPayloadDictionary = new HashMap<String, String>();
	private static List<String> randomcopiedValues=new ArrayList<String>();
	private static List<String> randomcopiednumberValues=new ArrayList<String>();
	private static List<String> randomAlpha32List=new ArrayList<String>();
	private static List<String> randomAlpha64List=new ArrayList<String>();
	private static List<String> randomcopiedtextValues=new ArrayList<String>();
	private static List<String> copiedList=new ArrayList<String>();
	private static String testData;
	public static String error ="";

	public static Map<String, String> dict = new HashMap<String, String>();
	private static final Gson gson = new Gson();
	//Get vale from xml file by passing xml file path and key name
	public static String GetXMLData(String filePath, String keyName) {
		String value = "";
		try {
			File file = new File(filePath);
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(file);
			value = document.getElementsByTagName(keyName).item(0).getTextContent();
		} catch (Exception ex) {
			// throw new Exception("XML Key with Name not found.");
		}
		return value;
	}

	//To generate random number of 5 digits
	public static String getRandomNumberString() {
		// It will generate 5 digit random Number.
		// from 0 to 99999
		Random rnd = new Random();
		int number = rnd.nextInt(99999);

		// this will convert any number sequence into 6 character.
		return String.format("%05d", number);
	}
	//To set value of LabelNamuber, copied from element
	public static void setCopiedNumber(String number) {
		labelNumber = number;
	}
	//	public static void setCopiedRandomNumber(int number) {
	//		randomlabelNumber = number;
	//	}
	//	
	//To get value of LabelNamuber, copied from element
	public static String getCopiedNumber() {
		return labelNumber;
	}

	// To set value of applicationType
	public static void setApplicationType(String type) {
		applicationType = type;
	}

	// To get value of applicationType
	public static String getApplicationType() {
		return applicationType;
	}

	// To set value of TestRailID
	public static void setTestRailID(String type) {
		testRailID = type;
	}

	// To get value of TestRailID
	public static String getTestRailID() {
		return testRailID;
	}

	public static int getCopiedRandomNumber() {
		return randomlabelNumber;
	}


	public static String getMaxhour() {

		return Maxhour;
	}
	public static void setMaxhour(String text) {
		Maxhour=text;
	}
	//To set value of copiedCount variable
	public static void setCopiedCount(int number) {
		copiedCount = number;
	}
	public static void setRandomCopiedCount(int number) {
		randomcopiedCount = number;
	}

	//To get value of copiedCount, copied from element's size
	public static int getCopiedCount() {
		return copiedCount;
	}
	public static int getRandomCopiedCount() {
		return randomcopiedCount;
	}
	public static void setAlpha32Count(int number) {
		alpha32Count = number;
	}
	public static int getAlpha32Count() {
		return alpha32Count;
	}
	public static void setAlpha64Count(int number) {
		alpha64Count = number;
	}
	public static int getAlpha64Count() {
		return alpha64Count;
	}
	public static void setMaximumDuration(String text) {
		maxDuration = text;
	}
	public static String getMaximumDuration() {
		return maxDuration;
	}

	public static void setMinimumDuration(String text) {
		minDuration = text;
	}
	public static String getMinimumDuration() {
		return minDuration;
	}
	//To set value of LabelText variable if copied action is used once in a test case, else to set values in copiedValues list
	public static void setCopiedText(String text) {

		if(getCopiedCount()==0)
		{
			labelText = text;
			copiedValues.add(text);
		}
		else
		{
			copiedValues.add(text);
		}
		setCopiedCount(getCopiedCount()+1);

	}
	
	//To set value of LabelText variable if copied action is used once in a test case, else to set values in copiedValues list
		public static void setCopiedTextKey(String Key,String text) {

			dict.put(Key, text);  

		}
		
		public static String getCopiedTextKey(String Key) {
			if(dict.containsKey(Key))
			    return dict.get(Key); 
			else
				return Key;

	}
	public static void setGlobalValues(String text) {

		
			globalValues.add(text);
		

	}
	public static void setAPIResponse(Response text) {
		apiResponse = text;
	}
	public static Response getAPIResponse() {
		return apiResponse;
	}
	public static void setDBValue(String text) {
		dbValue = text;
	}
	public static String getDBValue() {
		return dbValue;
	}
	public static void setApiResponseDict(String key,String text) {
		if(text.contains("--")) {
		
			String[] verifystr = text.split("and");
			if(verifystr[0].contains("check --") && verifystr[0].contains("[]")) {
				
				String[] Str = verifystr[0].replaceAll("\\{", "").replaceAll("check --", "").trim().split("\\.");
				int size = getAPIResponse().jsonPath().getList(Str[0].replaceAll("\\[\\]", "")).size();
				
				String[] sanitizeStr = verifystr[0].replaceAll("\\{", "").replaceAll("check --", "").trim().split("=");
				String checkStrbool;
				String[] checkStrkey = new String[2];
				
				checkStrbool = sanitizeStr[1];
				if(sanitizeStr[0].contains("[]")) {
					checkStrkey = sanitizeStr[0].split("\\[\\]");
				}
							
				String sanitizegetStr = verifystr[1].replaceAll("get --", "").replaceAll("}", "").trim();
				String[] getstrkey = sanitizegetStr.split("\\[\\]");
				
				for(int i = 0; i < size-1; i++) {
//					String str = filterkey[0].replaceAll("[]", replacement);
					
					if(getAPIResponse().jsonPath().getString(checkStrkey[0]+"["+i+"]"+checkStrkey[1])
							.equalsIgnoreCase(checkStrbool)) {
						apiResponseDictionary.put("sku"+i, getAPIResponse().jsonPath()
								.getString(getstrkey[0]+"["+i+"]"+getstrkey[1]));
					}
				}
			}
			
//			String[] str = product_list[].try_at_home--1--productlist.listingview.sku;
		}else {
			String[] splitText=text.split(",");
			int count=0;
		/*	if(apiResponseDictionary.containsKey("index"))
		{
			String s=apiResponseDictionary.get("index");
			count=Integer.parseInt(s);
			apiResponseDictionary.put("index", ++count+"");
		}
		else
		{
			apiResponseDictionary.put("index", ++count+"");	
		}*/
			for(int i=0;i<splitText.length;i++)
			{
				/*if(splitText[i].contains("time")) {
		llong literal = 1622711928000l;
		ZonedDateTime dateTime = Instant.ofEpochMilli(literal)
	            .atZone(ZoneId.of("Australia/Sydney"));
	}*/
				String value=getAPIResponse().jsonPath().getString(splitText[i]).toString();
				if(value.contains(",")) {
					value = value.replace(",", "#");
				}
				//apiResponseDictionary.put(splitText[i]+count, value);
				apiResponseDictionary.put(key+"."+splitText[i], value);
			}
		}
	}
	public static String getApipayloadDict(String text) {

		for ( String key : apiPayloadDictionary.keySet() ) {
			if(text.contains("$"+key))
			{
				text=text.replace("$"+key, apiPayloadDictionary.get(key));
				//System.out.println("test value"+text);
			}
		}
		ExtentCucumberAdapter.addTestStepLog("Actual value :"+ text);
		return text;
	}
	public static String getValueFromAPiResponse(String text) {

		for ( String key : apiResponseDictionary.keySet() ) {
			if(text.contains("@"+key))
			{
				text=text.replace("@"+key, apiResponseDictionary.get(key));
			}
		}
		return text;
	}
	public static String getApiResponseDict(String text) {

		return apiResponseDictionary.get(text);
	}

	public static void setCopiedRandomText(String text) {

		if(getRandomCopiedCount()==0)
		{
			randomlabelText = text;
			randomcopiedtextValues.add(text);
		}
		else
		{
			randomcopiedtextValues.add(text);
		}
		globalRandomValues.add(text);
		int size=globalRandomValues.size();
		if(size-1==0)
		{
		ExtentCucumberAdapter.addTestStepLog("@globalRanodmCopiedText --> "+globalRandomValues.get(size-1));
		}
		else
		{
			ExtentCucumberAdapter.addTestStepLog("@globalRanodmCopiedText"+(size)+" --> "+globalRandomValues.get(size-1));
		}
		setRandomCopiedCount(getRandomCopiedCount()+1);

	}
	public static void setCopiedRandomNumber(String number) {

		if(getRandomCopiedCount()==0)
		{
			randomlabelNumber = Integer.parseInt(number);
			randomcopiednumberValues.add(number);
		}
		else
		{
			randomcopiednumberValues.add(number);
		}
		globalRandomValues.add(number);
		int size=globalRandomValues.size();
		if(size-1==0)
		{
		ExtentCucumberAdapter.addTestStepLog("@globalRanodmCopiedText --> "+globalRandomValues.get(size-1));
		}
		else
		{
			ExtentCucumberAdapter.addTestStepLog("@globalRanodmCopiedText"+(size)+" --> "+globalRandomValues.get(size-1));
		}
		setRandomCopiedCount(getRandomCopiedCount()+1);

	}
	public static void setCopiedAlphaNumber32(String number) {

		if(getAlpha32Count()==0)
		{
			alphaNumber32 = number;
			randomAlpha32List.add(number);
		}
		else
		{
			randomAlpha32List.add(number);
		}
		setAlpha32Count(getAlpha32Count()+1);

	}
	public static void setCopiedAlphaNumber64(String number) {

		if(getAlpha64Count()==0)
		{
			alphaNumber64Epoch = number;
			randomAlpha64List.add(number);
		}
		else
		{
			randomAlpha64List.add(number);
		}
		setAlpha64Count(getAlpha64Count()+1);

	}
	public static void setCopiedList(List text) {

		copiedList=text;

	}

	public static List getCopiedList() {

		return copiedList;

	}


	//To get value by index of copiedValues list by passing index number
	public static String getCopiedCountText(String text) {
		
		return copiedValues.get(Integer.parseInt(text)-1);
		//return copiedValues.get(Integer.parseInt(text)-1);

	}
	
	public static String getGlobalText(String text) {

		return globalValues.get(Integer.parseInt(text)-1);

	}
	public static String getRandomCopiedCountText(String text) {

		return randomcopiedtextValues.get(Integer.parseInt(text)-1);

	}
	public static String getAlphaNum32CopiedCountText(String text) {

		return randomAlpha32List.get(Integer.parseInt(text)-1);

	}
	public static String getAlphaNum64CopiedCountText(String text) {

		return randomAlpha64List.get(Integer.parseInt(text)-1);

	}
	
	public static String getAlphaNum32CopiedText() {

		return alphaNumber32;

	}
	public static String getAlphaNum64CopiedText() {

		return alphaNumber64Epoch;

	}
	public static String getRandomCopiedCountNumber(String text) {

		return randomcopiednumberValues.get(Integer.parseInt(text)-1);

	}

	//To empty the copiedValues list
	public static void setCopiedCountTextNull() {

		copiedValues.clear();
		randomcopiednumberValues.clear();
		randomcopiedtextValues.clear();

	}
	public static List<String> getCopiedTextList() {
	    return copiedValues;
	}

	//To get value of labelText, copied from element
	public static String getCopiedText() {
		return labelText;
	}
	public static String getCopiedRandomText() {
		return randomlabelText;
	}

	//To get value of columnIndex, copied from element
	public static int getColumnIndex() {
		return columnIndex;
	}

	//To set value of columnIndex, copied from element
	public static void setColumnIndex(int number) {
		columnIndex = number;
	}

	//To set vale of APIURL for API execution
	public static void setAPIURL(String text) {
		APIURL = text.replaceAll("'", "");
	}

	//To get value of APIURL for API execution
	public static String getAPIURL() {
		return APIURL;
	}

	//To set value of MethodType for API execution
	public static void setMethodType(String text) {
		MethodType = text;
	}

	//To get value of MethodType for API execution
	public static String getMethodType() {
		return MethodType;
	}

	//To set value of RequestParameters for API execution
	public static void setRequestParameters(String text) {
		RequestParameters = text;
	}

	//To get value of RequestParameters for API execution
	public static String getRequestParameters() {
		return RequestParameters;
	}

	//To set value of APIHeaders for API execution
	public static void setAPIHeaders(String text) {
		APIHeaders = text;
	}

	//To get value of APIHeaders for API execution
	public static String getAPIResponseHeaders() {
		return responseHeader;
	}

	public static void setAPIResponseHeaders(String text) {
		responseHeader = text;
	}

	//To get value of APIHeaders for API execution
	public static String getAPIHeaders() {
		return APIHeaders;
	}

	//To set value of APIendpoint for API execution
	public static void setAPIEndpoint(String text) {
		APIendpoint = text;
	}

	//To get value of APIendpoint for API execution
	public static String getAPIEndpoint() {
		return APIendpoint;
	}

	//To set value of APIparameter for API execution
	public static void setAPIParameter(String text) {
		APIparameter = text;
	}

	public static String getGlobalRandomText(String text) {

		return globalRandomValues.get(Integer.parseInt(text) - 1);

	}

	// To set value of Basic auth for API execution
	public static String getBasicAuth() {
		return BasicAuth;
	}

	//To set value of Basic auth for API execution
	public static void setBasicAuth(String text) {
		BasicAuth = text;
	}

	//To get value of APIparameter for API execution
	public static String getAPIParameter() {
		return APIparameter;
	}
	
	 public static Map<String, String> readValuesFromJson(String filePath) {
	        ObjectMapper objectMapper = new ObjectMapper();
	        Map<String, String> dynamicValues = new HashMap<>();
	        
	        try {
	            List<Map<String, String>> list = objectMapper.readValue(new File(filePath), new TypeReference<List<Map<String, String>>>() {});
	            for (Map<String, String> item : list) {
	                dynamicValues.put(item.get("variable"), item.get("value"));
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        return dynamicValues;
	    }
	 public static String replacePlaceholders(String input, Map<String, String> dynamicValues) {
	        Pattern pattern = Pattern.compile("\\{\\{(.*?)\\}\\}");
	        Matcher matcher = pattern.matcher(input);

	        StringBuffer result = new StringBuffer();

	        while (matcher.find()) {
	            String placeholder = matcher.group(1);
	            String replacement = dynamicValues.getOrDefault(placeholder, "");
	            matcher.appendReplacement(result, replacement);
	        }
	        matcher.appendTail(result);

	        return result.toString();
	    }
	public static String GetDataFromJson(String variableName) {
		try {
			if(variableName.contains("{{") && variableName.contains("}}"))
			{
//				String[] splitValue=variableName.split("\\{\\{");
//				String[] splitValue2=splitValue[1].split("\\}\\}");
//			
		JSONParser parser = new JSONParser(); 
		String path = System.getProperty("user.dir");
		String envJSON =Paths.get(path.toString(), "src", "test", "java", "TestDataEnv.json").toString();
		 Map<String, String> dynamicValues = readValuesFromJson(envJSON);
		 variableName = replacePlaceholders(variableName, dynamicValues);
//		JSONArray jsonArray = (JSONArray)parser.parse(new FileReader(envJSON));
//
//         // Extract values
//         for (Object obj : jsonArray) {
//             JSONObject jsonObject = (JSONObject) obj;
//             String key=splitValue2[0];
//             if (jsonObject.get("variable").equals(key)) {
//            	 variableName=variableName.replaceAll("\\{\\{"+key+"\\}\\}", (String) jsonObject.get("value"));
//                 return variableName;
//             }
//
//             
//         }
         return variableName;
			}
		return variableName;
		}catch(Exception e)
		{
			return variableName;
		}
	}
	//To get data from yml file by passing key, to return random number or random text or random emailid or dateformat or time format if parameter contains concerned flag 
	public static String GetData(String variableName) {
		String value = null;
		variableName = YMLUtil.getYMLData(variableName);
	//	variableName = variableName.replace("_space_", "").replace("_empty_", "");	
        variableName = variableName.replaceAll("_space_", " ").replaceAll("_empty_", "");	
		variableName = DbHelper.queryCopiedText(variableName);
		variableName = DbHelper.alphaNumeric32CopiedText(variableName);
		variableName = DbHelper.alphaNumeric64CopiedText(variableName);
		variableName = DbHelper.replaceGlobalText(variableName);
		variableName = DbHelper.textRandomCopiedText(variableName);
		variableName = DbHelper.textRandomCopiedNumber(variableName);
		variableName = DbHelper.globalRandomText(variableName);
		variableName = GetDataFromJson(variableName);
		
		variableName = getCopiedTextKey(variableName);
		
		try {
			if (variableName.contains("randomnumber_")) {
				String[] numberlist = variableName.split("_");
				if(numberlist.length==2)
				{
					int length = Integer.parseInt(variableName.replace("randomnumber_", ""));
					Random random = new Random();
					String numbers = "0123456789";
					StringBuilder sb = new StringBuilder();
					while (sb.length() < length) { // length of the random string.
						int index = (int) (random.nextFloat() * numbers.length());
						sb.append(numbers.charAt(index));
					}
					value = sb.toString();
					setCopiedRandomNumber(value);
				}
				if(numberlist.length==3)
				{
					variableName=variableName.replace("randomnumber_","");
					int length = Integer.parseInt(numberlist[2]);
					Random random = new Random();
					String chars = "123456789";
					StringBuilder sb = new StringBuilder();
					while (sb.length() < length) { // length of the random string.
						int index = (int) (random.nextFloat() * chars.length());
						sb.append(chars.charAt(index));
					}
					value = numberlist[0]+sb.toString();
					//setCopiedRandomNumber(value);

				}

			} 
			if(variableName.contains("@randomnumber")) {
				Random random = new Random();
				String numbers = "0123456789";
				StringBuilder sb = new StringBuilder();
				while (sb.length() < 5) { // length of the random string.
					int index = (int) (random.nextFloat() * numbers.length());
					sb.append(numbers.charAt(index));
				}
				value = sb.toString();
				setCopiedRandomNumber(value);
				value=variableName.replace("@randomnumber", value);
				variableName=value;
			}
			if(variableName.contains("@randomtext")) {
				Random randomString = new Random();
				String chars = "abcdefghijklmnopqrstuvwxyz";
				StringBuilder sbString = new StringBuilder();
				while (sbString.length() < 7) { // length of the random string.
					int index = (int) (randomString.nextFloat() * chars.length());
					sbString.append(chars.charAt(index));
				}
				value = sbString.toString();
				setCopiedRandomText(value);
				value=variableName.replace("@randomtext", value);
				variableName=value;
			}
			  if(variableName.contains("@32DigitAplphaNum"))
			    {
			    	String digitToken32=RandomStringUtils.randomAlphanumeric(32);
			    	setCopiedAlphaNumber32(digitToken32);
			    	variableName=variableName.replace("@32DigitAplphaNum", digitToken32);
			    }
			    if(variableName.contains("@64DigitAplphaNumEpoch"))
			    {
			    	String digitToken64=RandomStringUtils.randomAlphanumeric(64);
			    	long epochTime=System.currentTimeMillis()/1000;
			    	digitToken64=digitToken64+"."+epochTime;
			    	setCopiedAlphaNumber64(digitToken64);
			    	variableName=variableName.replace("@64DigitAplphaNumEpoch", digitToken64);
			    }
			    if(variableName.contains("@md5Hash")){
					String xyz=variableName.replaceAll(" ", "").replaceAll(",\"hash\":\"@md5Hash\"", "");
				xyz=xyz.replaceAll(",\"validateHash\":true", "");
				  MessageDigest md = MessageDigest.getInstance("MD5");
				   byte[] messageDigest = md.digest(xyz.getBytes());
				   BigInteger no = new BigInteger(1, messageDigest);
				   String hashtext = no.toString(16);
			       while (hashtext.length() < 32) {
			           hashtext = "0" + hashtext;
			       }
			       variableName=variableName.replace("@md5Hash", hashtext);
			    }
			if(variableName.contains("@copiedtext"))
			{
				String message = CommonUtil.getCopiedText();
				value=variableName.replace("@copiedtext",message);
				variableName=value;

			}
			if(variableName.contains("@copiednumber"))
			{
				String message = String.valueOf(CommonUtil.getCopiedNumber());
				value=variableName.replace("@copiednumber",message);
				variableName=value;

			}
			if(variableName.contains("@datetime_utc")) {
				Instant instant = Instant.now();
				String[] sb=instant.toString().split(":");
				value=variableName.replace("@datetime_utc", sb[0]);
				variableName=value;
			}
			if(variableName.contains("@currentDirectory"))
			{
				value=variableName.replace("@currentDirectory", System.getProperty("user.dir"));
			}
			if(variableName.contains("@convertDate_"))
			{
				//@convertDate_dateToBeconverted_actualDateFormat_toBeConvertedDateFormat 
				//eg: @convertDate_10 Jan 2020_dd MMM yyyy_dd/MM/yyyy
				String[] splitText=variableName.split("_");
				 SimpleDateFormat format1 = new SimpleDateFormat(splitText[2]);
				    SimpleDateFormat format2 = new SimpleDateFormat(splitText[3]);
				    Date date = format1.parse(splitText[1]);
				    value=format2.format(date);
				    //System.out.println(value);
			}
			if(variableName.contains("@currentdate")) {
				String sb="";
				if(variableName.contains("@currentdate_"))
				{
					String[] vnames=variableName.split(",");
					for(int i=0;i<vnames.length;i++)
					{
						if(vnames[i].contains("@currentdate_"))
						{
							String[] dates=vnames[i].split("_");
							Date today = new Date();
							Date date = new Date(today.getTime() + (1000 * 60 * 60 * 24 * Integer.parseInt(dates[1])));
							sb = new SimpleDateFormat("yyyy-MM-dd").format(date);
							value=variableName.replace(vnames[i], sb);
							variableName=value;
						}

					}




				}
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date currentDate = new Date();
				// convert date to calendar
				//					Calendar c = Calendar.getInstance();
				//					c.setTime(currentDate);
				//					int randomNumb=(int) (Math.random()*(10 - 1)) + 1;
				//					c.add(Calendar.DATE, randomNumb); 
				//					Date currentDatePlusOne = c.getTime();
				sb = dateFormat.format(currentDate);
				value=variableName.replace("@currentdate", sb);
				variableName=value;

			}
			if(variableName.contains("@epochtime")) {
				String sb="";
				if(variableName.contains("@epochtime_"))
				{
					String[] vnames=variableName.split(",");
					for(int i=0;i<vnames.length;i++)
					{
						if(vnames[i].contains("@epochtime_"))
						{
							String[] dates=vnames[i].split("_");
							Date today = new Date();
							Date date;
							if(dates[1].contains("'")) {
								String[] minutes = dates[1].split("'");
								date = new Date(today.getTime() + ((1000 * 60 * 60 * 24 * Integer.parseInt(minutes[0])) 
										+ (1000 * 60 * Integer.parseInt(minutes[1]))));
							}else
								date = new Date(today.getTime() + (1000 * 60 * 60 * 24 * Integer.parseInt(dates[1])));

							Long epochTime=date.getTime();
							sb=Long.toString(epochTime);
							value=variableName.replace("@epochtime_"+dates[1]+"_", sb);
							variableName=value;
						}

					}			 

				}
				Date today = new Date();
				Date date = new Date(today.getTime());
				long epoch = date.getTime();



				value=variableName.replace("@epochtime",Long.toString(epoch));
				variableName=value;

			}

			else if (variableName.contains("randomdate_yyyy/dd/mm")) {
				DateFormat dateFormat = new SimpleDateFormat("yyyy/dd/MM");
				Date currentDate = new Date();
				// convert date to calendar
				Calendar c = Calendar.getInstance();
				c.setTime(currentDate);
				int randomNumb=(int) (Math.random()*(10 - 1)) + 1;
				c.add(Calendar.DATE, randomNumb); 
				Date currentDatePlusOne = c.getTime();
				value = dateFormat.format(currentDatePlusOne);
			}
			else if (variableName.contains("randomtext_")) {
				Random randomString = new Random();
				String chars = "abcdefghijklmnopqrstuvwxyz";
				StringBuilder sbString = new StringBuilder();
				while (sbString.length() < 7) { // length of the random string.
					int index = (int) (randomString.nextFloat() * chars.length());
					sbString.append(chars.charAt(index));
				}
				value = sbString.toString();
			} else if (variableName.contains("randomemail_")) {
				Random generator = new Random();
				variableName = variableName.replace("_space_", " ").replace("_empty_", "");
				String[] emailList = variableName.split("@");
				if (emailList.length > 1) {
					value = emailList[0].replace("randomemail_", "") + String.format("%05d", generator.nextInt(99999)) + "@" + emailList[1];
				} else {
					value = emailList[0].replace("randomemail_", "") + String.format("%05d", generator.nextInt(99999)) + "@gmail.com";
				}
			}			
			else if (variableName.contains("dateformat_")) {	

				String[] dateFormatKey=variableName.split("_");
				if(dateFormatKey.length>2)
				{
					int daysNumber = Integer.parseInt(dateFormatKey[2]);
					variableName = variableName.replace("dateformat_", " ").replace("_empty_", "");
					Date currentDate = new Date();
					Calendar c = Calendar.getInstance();
					c.setTime(currentDate);					
					c.add(Calendar.DATE, daysNumber); 

					value=(new SimpleDateFormat(dateFormatKey[1]).format(c.getTime())).trim();

				}
				else {
					variableName = variableName.replace("dateformat_", " ").replace("_empty_", "");
					value=(new SimpleDateFormat(variableName).format(Calendar.getInstance().getTime())).trim();
				}
			}
			else if (variableName.contains("timeformat_")) {
				variableName = variableName.replace("timeformat_", " ").replace("_empty_", "");
				value=(new SimpleDateFormat(variableName).format(Calendar.getInstance().getTime())).trim();
			}
			else {
				if(value==null)
				{
					value = variableName;
				}
			}
		} catch (Exception ex) {
			value = variableName;
		}
		ExtentCucumberAdapter.addTestStepLog("Value : "+value);
		return value;
	}

	//To verify, if parameter passed is in json format or not
	public static boolean IsValidJson(String jsonInString) {
		try {
			gson.fromJson(jsonInString, Object.class);
			return true;
		} catch (com.google.gson.JsonSyntaxException ex) {
			return false;
		}
	}

	//To convert result data into list
	public static List resultSetToArrayList(ResultSet rs) throws SQLException {
		ResultSetMetaData md = rs.getMetaData();
		int columns = md.getColumnCount();
		ArrayList list = new ArrayList(50);
		while (rs.next()) {
			HashMap row = new HashMap(columns);
			for (int i = 1; i <= columns; ++i) {
				row.put(md.getColumnName(i), rs.getObject(i));
			}
			list.add(row);
		}
		return list;
	}

	/* To verify the downloaded files */
	/*public boolean VerifyTheDownloadedFile(String filePath) {
		try {
			Thread.sleep(2000);
			}
			catch (Exception e) {
			}
	    File theNewestFile = null;
	    File dir = new File(filePath);
	    FileFilter fileFilter = new WildcardFileFilter("*.*");
	    File[] files = dir.listFiles(fileFilter);
		if (filePath.contains("Extention_")) {
			String path = null;
			String[] arr = filePath.split("_");
			String fileType = arr[1] ;
			 path = System.getProperty("user.dir");
			 //System.out.println("user directory :"+path);

			 dir = new File(path);
			// System.out.println("File Name is :"+dir);
			 fileFilter = new WildcardFileFilter("*." + fileType);
			 files = dir.listFiles(fileFilter);

			try {
				Thread.sleep(1000);
			} catch (Exception e) {
			}
			//System.out.println("File absolute path : "+fileName);
			 //fileName.split("\\.")[1].equalsIgnoreCase(fileType);

		}

	    if (files.length > 0) {
	        // The newest file comes first 
	        Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
	        theNewestFile = files[0];

	        //System.out.println("Going to validate file name :"+theNewestFile.getAbsolutePath());
	    }
	    long date=theNewestFile.lastModified();
	    Date fileDate = new Date(date);
	    Date currentDate = new Date();
	    currentDate.setTime(currentDate.getTime() - 30000);
	    if(fileDate.after(currentDate))
	    {

	    	return true;
	    }
	    return false;
	}*/

	public boolean VerifyTheDownloadedFile(String filePath) {
		try {
			Thread.sleep(5000);
		} catch (Exception e) {
		}


		if (filePath.contains("Extention_")) {
			System.out.println("filePath is :--------"+filePath);

			System.out.println("file path contains :" + filePath.contains("Extention_"));
			String path = null;
			String[] arr = filePath.split("_");
			String fileType = arr[1];
			path = System.getProperty("user.dir");
			System.out.println("user directory :" + path);

			// filePath=System.getProperty("user.dir");

			// String fileType = splits[1];

			// File theNewestFile = null;

			File theNewestFile = null;
			File dir = new File(path);
			FileFilter fileFilter = new WildcardFileFilter("*.*");
			File[] files = dir.listFiles(fileFilter);



			String fileExtension = null;

			if (files.length > 0) {
				/** The newest file comes first **/
				Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
				theNewestFile = files[0];
				System.out.println("theNewestFile is :------"+theNewestFile);
				fileExtension = CommonUtil.getFileExtension(theNewestFile);
				System.out.println("file extension : "+fileExtension);
				if (fileExtension.equals(fileType)) {
					return true;
				} else {
					return false;
				}

			}
		}

		else {
			File theNewestFile = null;
			File dir = new File(filePath);
			FileFilter fileFilter = new WildcardFileFilter("*.*");
			File[] files = dir.listFiles(fileFilter);

			if (files.length > 0) {

				/** The newest file comes first **/
				Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
				theNewestFile = files[0];

				System.out.println("Going to validate file name :" + theNewestFile.getAbsolutePath());
			}

			long date = theNewestFile.lastModified();
			Date fileDate = new Date(date);
			Date currentDate = new Date();
			currentDate.setTime(currentDate.getTime() - 30000);
			if (fileDate.after(currentDate)) {
				System.out.println("Modify current date");
				return true;
			}
		}
		return false;

	}




	public static String findLatestFileFromPathBasedOnType(String filePath, String fileType) {
		try {
			Thread.sleep(2000);
		} catch (Exception e) {
		}
		String path = null;
		File theNewestFile = null;
		File dir = new File(filePath);
		FileFilter fileFilter = new WildcardFileFilter("*." + fileType);
		System.out.println("filePath : "+filePath);
		File[] files = dir.listFiles(fileFilter);

		if (files.length > 0) {
			/** The newest file comes first **/
			Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
			theNewestFile = files[0];
		}
		path = theNewestFile.getAbsolutePath();
		return path;
	}

	public static boolean validateContent(String content, String logFilePath) {

		String logFile = findLatestFileFromPathBasedOnType(logFilePath, "txt");
		System.out.println("Unzip log file path "+logFile);
		File file = new File(logFile);
		List<Line> lines = Unix4j.grep(Grep.Options.lineNumber, content, file).toLineList();

		System.out.println("Content " + content + " repeated " + lines.size() + " times");
		if (lines.size() > 0) {
			return true;
		}

		return false;
	}

	public static boolean unZipFile(String source, String destination) {
		try {
			ZipFile zipFile = new ZipFile(source);
			zipFile.extractAll(destination);
			return true;

		} catch (ZipException e) {
			e.printStackTrace();
		}
		return false;
	}
	public static boolean verifyFileTypeInDownloadPath(String fileNames) {

		try {
			try {
				Thread.sleep(5000);
			}
			catch (Exception e) {
			}
			String[] splits = fileNames.split("_");

			String filePath = splits[0];
			String fileType = splits[1];

			//System.out.println("download path : "+filePath);
			//System.out.println("file type : "+fileType);
			File theNewestFile = null;
			File dir = new File(filePath);

			FileFilter fileFilter = new WildcardFileFilter("*.*");
			File[] files = dir.listFiles(fileFilter);
			String fileExtension = null;

			if (files.length > 0) {
				/** The newest file comes first **/
				Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
				theNewestFile = files[0];
				fileExtension = getFileExtension(theNewestFile);
				//System.out.println("file extension : "+fileExtension);

			}
			long date = theNewestFile.lastModified();
			Date fileDate = new Date(date);
			Date currentDate = new Date();
			currentDate.setTime(currentDate.getTime() - 30000);
			if (fileDate.after(currentDate)) {
				if (fileExtension != null && fileExtension.equalsIgnoreCase(fileType)) {
					return true;
				}

			}

		} catch (Exception e) {

		}

		return false;
	}


	public static boolean verifyJsonWithApiResponse(String userInput) 
		{
			Response res=getAPIResponse();
//			userInput = "{\"zipcode\": \"75034\",\"country\":\"US\"}";
			HashMap<String, Object> responseMap = new HashMap<String,Object>();
			HashMap<String, Object> userResponseMap = new HashMap<String,Object>();
			ObjectMapper mapper = new ObjectMapper();
			boolean flag = false;
			
			if(!IsValidJson(userInput)) {
				ExtentCucumberAdapter.addTestStepLog("Input json is invalid");
				return flag;
			}
			
			try {
				responseMap = mapper.readValue(res.asString(), 
						new TypeReference<HashMap<String,Object>>(){});
				userResponseMap = mapper.readValue(userInput, 
						new TypeReference<HashMap<String,Object>>(){});
				

				
				responseMap.remove("_id");
				userResponseMap.remove("_id");
		
				if(!responseMap.keySet().equals(userResponseMap.keySet())) {
					System.out.println("Warning: Keys set are not equal");
					//log.info("Warning: Keys set are not equal");
				}
		
				if(responseMap.equals(userResponseMap))
					return true;
		
				for (String k : userResponseMap.keySet())
				{
					if(responseMap.get(k)==null && userResponseMap.get(k)==null) {	
						continue;
					}
					else if(!(responseMap.containsValue(userResponseMap.get(k)))) {
						for(String d : responseMap.keySet()) {
							

							if(responseMap.get(d)!=null && responseMap.get(d).getClass().equals(java.util.LinkedHashMap.class)) {
								
								System.out.println("object"+ responseMap.get(d));
								if(d.contains(k))
									flag = processing(responseMap.get(d), userResponseMap.get(k));
							}
						
							/*
							
							
							if(responseMap.get(d)!=null && responseMap.get(d).getClass().equals(java.util.LinkedHashMap.class)) {
								System.out.println("object"+ responseMap.get(d));
								flag = processing(responseMap.get(d), userResponseMap);
							}*/
						}if(!flag) {
							System.out.println("**********************Unable to verify "+"\""+k+"\"");
							ExtentCucumberAdapter.addTestStepLog("Unable to verify: "+ "\"" + k + "\" with value:"+userResponseMap.get(k));
							flag = false;
						}
					} 
					else if(responseMap.get(k)==null || userResponseMap.get(k)==null) { 
		            	System.out.println("---------------------------------------------Unable to verify "+"\""+k+"\"");
		            	ExtentCucumberAdapter.addTestStepLog("Unable to verify: "+ "\"" + k + "\"");
		            	flag = false;
		            }    
					else
					{
						ExtentCucumberAdapter.addTestStepLog("JSON key: "+ "\"" + k + "\" with value: "+userResponseMap.get(k)+" is verified");
		            	System.out.println(k+"                                 checked                " );
		            	flag = true;
					}
				} 
			} catch (NullPointerException np) {
				//System.out.println("exception");
			} catch (JsonMappingException e) {
				//e.printStackTrace();
			} catch (JsonProcessingException e) {
				//e.printStackTrace();
			} catch (JsonSyntaxException e) {
				ExtentCucumberAdapter.addTestStepLog("Input json is invalid");

		}
		
			return flag;
		
		
			//return (responseMap.values().toString() ).equals(/*new ArrayList<>( */userResponseMap.values().toString());
	}
	
	private static boolean processing(Object mapper, Object usermapper/*HashMap<String, Object> userResponseMap*/) {

		// TODO Auto-generated method stub
		HashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map = (HashMap<String, Object>) mapper;
		
//		HashMap<String, Object> map = new LinkedHashMap<String, Object>();
		Set<String> check = new HashSet<String>();
//		map = (HashMap<String, Object>) mapper;
		HashMap<String, Object> userResponseMap = new LinkedHashMap<String, Object>();
		userResponseMap = (HashMap<String, Object>) usermapper;
		boolean flag = false;
		for(String k : userResponseMap.keySet()) {
			if(!(userResponseMap.get(k)!=null && userResponseMap.get(k).getClass().equals(java.util.LinkedHashMap.class))) {
				for(String k1 : map.keySet()) {
					if(k/*map*/.contains(k1)) {
						if(userResponseMap.get(k).equals(map.get(k1))) {
							flag = true;
							if(check.add(userResponseMap.get(k1).toString())){
								System.out.println(k1+"                                 checked                " );
//								Reporter.addStepLog("JSON key: "+ "\"" + k + "\" with value: "+userResponseMap.get(k1)+" is verified");
							break;
							}
							
						}else 
							flag = false;
					}
				}
			} else {
				flag = processing(map.get(k), userResponseMap);		
			}
		}
		check.clear();
		return flag;
	
		
		
		
		/*
		// TODO Auto-generated method stub
		HashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map = (HashMap<String, Object>) mapper;
		boolean flag = false;
		for(String k : map.keySet()) {
			if(!(map.get(k)!=null && map.get(k).getClass().equals(java.util.LinkedHashMap.class))) {
				for(String k1 : userResponseMap.keySet()) {
					if(map.containsKey(k1)) {
						if(map.containsValue(userResponseMap.get(k1))) {
							flag = true;
							Reporter.addStepLog("JSON key: "+ "\"" + k + "\" with value: "+userResponseMap.get(k1)+" is verified");
							System.out.println(k1+"                                 checked                " );
						}else 
							flag = false;
					}
				}
			} else {
				flag = processing(map.get(k), userResponseMap);		
			}
		}
		return flag;
	*/}        
	public static String Randomnumber(String number ) {	
		if(number.contains("randomnumber_"))
		{
		    String [] numberOnly = number.split("_");
			int length = Integer.parseInt(numberOnly[1]);
			Random random = new Random();
			String numbers = "0123456789";
			StringBuilder sb = new StringBuilder();
			while (sb.length() < length) { // length of the random string.
				int index = (int) (random.nextFloat() * numbers.length());
				sb.append(numbers.charAt(index));
			}
			return sb.toString();	
		}
		else
			return number;
	}
	
		private static String getFileExtension(File file) {
		    String name = file.getName();
		    int lastIndexOf = name.lastIndexOf(".");
		    if (lastIndexOf == -1) {
		        return ""; // empty extension
		    }
		    return name.substring(lastIndexOf+1);
		}
		
		public static void setTestData(String text) {
		testData = text;
	}
	public static String getTestData() {
		return testData;
	}
	public static String cleanMessage(String message) {
		if (message.contains("<") || message.contains(">")) {
			message = message.replace("<", "").replace(">", "");
		}
		return message;
	}
                        
}