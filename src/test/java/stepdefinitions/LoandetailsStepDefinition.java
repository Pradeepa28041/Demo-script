package stepdefinitions;
    import io.cucumber.java.en.*;
    import workflows.SeleniumWorkFlow;
    import org.junit.Assert;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;

import common.Assertion;
    import common.WebBrowserUtil;
    public class LoandetailsStepDefinition
	{
        SeleniumWorkFlow workFlow = new SeleniumWorkFlow();
        static long startTime;
        public static long endTime;
        public static double timeTakenInSeconds;
        

            @When("^I selected drag loan amount number in loan details$")			
            public void WhenISelectedDragLoanAmountNumberInLoanDetails()
            {
                workFlow.clickedElement(0,"Loan details","Loan details.dragloanamountnumberButtonXPATH","XPATH");
                
            }

            @When("^I selected PURPOSE in loan details$")			
            public void WhenISelectedPurposeInLoanDetails()
            {
                workFlow.clickedElement(0,"Loan details","Loan details.PURPOSEButtonXPATH","XPATH");
                
            }

            @When("^I scroll and click Repair of own house in loan details$")			
            public void WhenIScrollClickRepairOfOwnHouseInLoanDetails()
            {
                workFlow.scrollAndClick(0,"Loan details","Loan details.RepairofownhouseButtonXPATH","XPATH");
                
            }

            @When("^I selected NEXT BUTTON in loan details$")			
            public void WhenISelectedNextButtonInLoanDetails()
            {
            	startTime = System.currentTimeMillis();
                workFlow.clickedElement(0,"Loan details","Loan details.NEXTBUTTONButtonXPATH","XPATH");
                WebBrowserUtil.waitForElementToBeVisible("//h1[contains(.,'Help us with your Personal Details')]");
                endTime = System.currentTimeMillis();
                timeTakenInSeconds = (endTime - startTime) / 1000.00;
               ExtentCucumberAdapter.addTestStepLog("Time taken to launch Customer Personal Details page: "+timeTakenInSeconds + " Sec");
            }
    }