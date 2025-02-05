package stepdefinitions;
    import io.cucumber.java.en.*;
    import workflows.SeleniumWorkFlow;
    import org.junit.Assert;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;

import common.Assertion;
    import common.WebBrowserUtil;
    public class IncomeDetailsStepDefinition
	{
        SeleniumWorkFlow workFlow = new SeleniumWorkFlow();
        static long startTime;
        public static long endTime;
        public static double timeTakenInSeconds;
        

            @When("^I selected Other Bank check box in income details$")			
            public void WhenISelectedOtherBankCheckBoxInIncomeDetails()
            {
                workFlow.clickedElement(0,"Income Details","Income Details.OtherBankcheckboxButtonXPATH","XPATH");
                
            }

            @When("^I selected Next Button in income details$")			
            public void WhenISelectedNextButtonInIncomeDetails()
            {
                workFlow.clickedElement(0,"Income Details","Income Details.NextButtonButtonXPATH","XPATH");
                
            }

            @When("^I selected Net Banking Login in income details$")			
            public void WhenISelectedNetBankingLoginInIncomeDetails()
            {
                workFlow.clickedElement(0,"Income Details","Income Details.NetBankingLoginButtonXPATH","XPATH");
                
            }

            @When("^I scroll and click ListBox in income details$")			
            public void WhenIScrollClickListboxInIncomeDetails()
            {
                workFlow.scrollAndClick(0,"Income Details","Income Details.ListBoxButtonXPATH","XPATH");
                
            }

            @When("^I selected Acme Bank Ltd India in income details$")			
            public void WhenISelectedAcmeBankLtdIndiaInIncomeDetails()
            {
                workFlow.clickedElement(0,"Income Details","Income Details.AcmeBankLtdIndiaButtonXPATH","XPATH");
                
            }

            @When("^I clicked Next Button in Select Bank in income details$")			
            public void WhenIClickedNextButtonInSelectBankInIncomeDetails()
            {
            	startTime = System.currentTimeMillis();
                workFlow.clickedElement(0,"Income Details","Income Details.NextButtoninSelectBankButtonXPATH","XPATH");
                WebBrowserUtil.waitForElementToBeVisible("//span[contains(.,'Please enter your Acme Bank Ltd., details')]");
                endTime = System.currentTimeMillis();
                timeTakenInSeconds = (endTime - startTime) / 1000.00;
               ExtentCucumberAdapter.addTestStepLog("Time taken to launch Customer ACME bank Login page: "+timeTakenInSeconds + " Sec");
            
            }
    }