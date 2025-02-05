package stepdefinitions;
    import io.cucumber.java.en.*;
    import workflows.SeleniumWorkFlow;
    import org.junit.Assert;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;

import common.Assertion;
    import common.WebBrowserUtil;
    public class AcmeBankDetailsStepDefinition
	{
        SeleniumWorkFlow workFlow = new SeleniumWorkFlow();
        static long startTime;
        public static long endTime;
        public static double timeTakenInSeconds;
        

            @When("^I entered Login Id in acme bank details as '(.*)'$")			
            public void WhenIEnteredLoginIdInAcmeBankDetailsAsloginId(String  _loginId)
            {
                workFlow.enterText(_loginId,0,"Acme Bank Details","Acme Bank Details.LoginIdTextBoxXPATH","XPATH");
                
            }

            @When("^I entered PassWord in acme bank details as '(.*)'$")			
            public void WhenIEnteredPasswordInAcmeBankDetailsAspassword(String  _password)
            {
                workFlow.enterText(_password,0,"Acme Bank Details","Acme Bank Details.PassWordTextBoxXPATH","XPATH");
                
            }

            @When("^I selected Next Button in acme bank details$")			
            public void WhenISelectedNextButtonInAcmeBankDetails()
            {
            	startTime = System.currentTimeMillis();
                workFlow.clickedElement(0,"Acme Bank Details","Acme Bank Details.NextButtonButtonXPATH","XPATH");
                WebBrowserUtil.waitForElementToBeVisible("//h1[contains(.,'Congratulations')]");
                endTime = System.currentTimeMillis();
                timeTakenInSeconds = (endTime - startTime) / 1000.00;
               ExtentCucumberAdapter.addTestStepLog("Time taken to launch Customer Loan Amount Confirmation  page: "+timeTakenInSeconds + " Sec");
            
            }

            @When("^I wait in seconds Wait in acme bank details as '(.*)'$")			
            public void WhenIWaitInSecondsWaitInAcmeBankDetailsAswait(String  _wait)
            {
                workFlow.waitInSeconds(_wait,0,"Acme Bank Details","Acme Bank Details.WaitTextBoxXPATH","XPATH");
                
            }

            @When("^I scroll and click Accept in acme bank details$")			
            public void WhenIScrollClickAcceptInAcmeBankDetails()
            {
            	startTime = System.currentTimeMillis();
                workFlow.scrollAndClick(0,"Acme Bank Details","Acme Bank Details.AcceptButtonXPATH","XPATH");
                WebBrowserUtil.waitForElementToBeVisible("(//h1[contains(.,'Help us with a few more details')])[2]");
                endTime = System.currentTimeMillis();
                timeTakenInSeconds = (endTime - startTime) / 1000.00;
               ExtentCucumberAdapter.addTestStepLog("Time taken to launch Customer Details Verification page: "+timeTakenInSeconds + " Sec");
            
            }
    }