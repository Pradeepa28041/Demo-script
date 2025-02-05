package stepdefinitions;
    import io.cucumber.java.en.*;
    import workflows.SeleniumWorkFlow;
    import org.junit.Assert;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;

import common.Assertion;
    import common.WebBrowserUtil;
    public class HomepageStepDefinition
	{
        SeleniumWorkFlow workFlow = new SeleniumWorkFlow();
        public static long startTime;
        public static long endTime;
        public static double timeTakenInSeconds;
        

             @Given("^I have access to application$")			
            public void GivenIHaveAccessToApplication()
            {
            	startTime = System.currentTimeMillis();
                workFlow.accessToPage();
                WebBrowserUtil.waitForElementToBeVisible("//h1[contains(.,'Apply for Personal Loan')]");
                endTime = System.currentTimeMillis();
                timeTakenInSeconds = (endTime - startTime) / 1000.00;
               ExtentCucumberAdapter.addTestStepLog("Time taken to launch application: "+timeTakenInSeconds + " Sec");
                
            }

            @When("^I selected Existing bank Customer login in home page$")			
            public void WhenISelectedExistingBankCustomerLoginInHomePage()
            {
           	    startTime = System.currentTimeMillis();
                workFlow.clickedElement(0,"Home page","Home page.ExistingbankCustomerloginButtonXPATH","XPATH");
                WebBrowserUtil.waitForElementToBeVisible("//p[contains(.,'Kindly keep the following documents/ details ready before starting the application.')]");
                endTime = System.currentTimeMillis();
                timeTakenInSeconds = (endTime - startTime) / 1000.00;
               ExtentCucumberAdapter.addTestStepLog("Time taken to launch Pre-requisite for Existing Customer Login: "+timeTakenInSeconds + " Sec");
                
            }

            @When("^I checked requirements in home page$")			
            public void WhenICheckedRequirementsInHomePage()
            {
                workFlow.checkCheckbox(0,"Home page","Home page.requirementsCheckBoxXPATH","XPATH");
                
            }

            @When("^I selected Proceed to create application in home page$")			
            public void WhenISelectedProceedToCreateApplicationInHomePage()
            {
            	startTime = System.currentTimeMillis();
                workFlow.clickedElement(0,"Home page","Home page.ProceedtocreateapplicationButtonXPATH","XPATH");
                WebBrowserUtil.waitForElementToBeVisible("//h1[contains(.,'Login for a faster Experience')]");
                endTime = System.currentTimeMillis();
                timeTakenInSeconds = (endTime - startTime) / 1000.00;
               ExtentCucumberAdapter.addTestStepLog("Time taken to launch Existing Customer Login page: "+timeTakenInSeconds + " Sec");
                
            }
    }