package com.qa.testrailmanager;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;

import com.gurock.testrail.APIClient;
import com.gurock.testrail.APIException;


import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import java.io.File;
import org.apache.commons.io.FileUtils;



public class TestRailManager {
	
	//private static String path = System.getProperty("user.dir");
//	static String TestRail_RunID = CommonUtil.GetXMLData(
//			Paths.get(path.toString(), "src", "test", "java", "ApplicationSettings.xml").toString(),
//			"TestRailRunID");
	
	public static String TEST_RUN_ID = "1";
//	public static String TEST_RUN_ID = TestRail_RunID;
	
	public static String TEST_RAIL_USERNAME = "pradeepa.b@algoshack.com";
	public static String TEST_RAIL_PASSWORD = "Algotest@123";
	
	
	public static String TEST_RAIL_ENGINE_URL = "https://algotest.testrail.io/";
	
	public static int TEST_CASE_PASS_STATUS = 1;
	public static int TEST_CASE_FAIL_STATUS = 5;
	
	
	public static void addResultsForTestCase(WebDriver driver, String testCaseId, int status, String error, List<Map> customStepResults) {
		
		String testRunID = TEST_RUN_ID;
		APIClient client = new APIClient(TEST_RAIL_ENGINE_URL);
		client.setUser(TEST_RAIL_USERNAME);
		client.setPassword(TEST_RAIL_PASSWORD);
		int result_id= 27;
		String url = String.format("add_attachment_to_result/%d", result_id);
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("status_id", status);
		data.put("custom_step_results", customStepResults);
		data.put("comment", "The Test case is executed through test automation code: " + error);
	
//		List<Map> customStepResults = new ArrayList<>();
//	    Map step1 = new HashMap();
//	    step1.put("content", "I entered Search data S in search details as");
//	    step1.put("status_id", 1); // 1 for Passed, 5 for Failed
//	    customStepResults.add(step1);
	    
		try {
			//to attach the text to the result
			client.sendPost("add_result_for_case/"+testRunID+"/"+testCaseId, data);
			Object response = client.sendPost("add_result_for_case/"+testRunID+"/"+testCaseId, data);
			
			Map<String, Object> responseMap = (Map<String, Object>) response;
			
			//to get the result_id
			String result= responseMap.get("id").toString();
			int result_id_1= Integer.parseInt(result);
			String url1 = String.format("add_attachment_to_result/%d", result_id_1);
			System.out.print("code executed");
			String screenshotName = "Test.png";
			 File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		        // Create the destination file
			 String userHome = System.getProperty("user.home");
		        String screenshotPath = userHome + File.separator + screenshotName;
		        File destinationFile = new File(screenshotPath);
		        
		        // Ensure the parent directory exists
		        destinationFile.getParentFile().mkdirs();
		        
		        try {
		            // Copy the screenshot to the destination file
		            FileUtils.copyFile(screenshotFile, destinationFile);
		            System.out.println("Screenshot saved at: " + screenshotPath);
		        } catch (IOException e) {
		            System.err.println("Failed to save screenshot: " + e.getMessage());
		        }
            //to attach the Screenshot to the test result
            client.sendPost(url1, new File(screenshotPath));
            System.out.println("Screenshot uploaded successfully!");
            
            
            
            
            
            
		} catch (MalformedURLException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
		
			e.printStackTrace();
		} catch (APIException e) {
			
			e.printStackTrace();
		}
		
		
		
	}
	
	
	
	
	
	
}
