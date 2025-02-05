package stepdefinitions;
    import io.cucumber.java.en.*;
    import workflows.SeleniumWorkFlow;
    import org.junit.Assert;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;

import common.Assertion;
    import common.WebBrowserUtil;
    public class BankaccountStepDefinition
	{
        SeleniumWorkFlow workFlow = new SeleniumWorkFlow();
        public static long startTime;
        public static long endTime;
        public static double timeTakenInSeconds;
        

            @When("^I selected Saving account radio in bank account$")			
            public void WhenISelectedSavingAccountRadioInBankAccount()
            {
                workFlow.clickedElement(0,"Bank account","Bank account.SavingaccountradioRadioButtonXPATH","XPATH");
                
            }

             @Then("^verify text Customer name in bank account$")			
            public void ThenVerifyTextCustomerNameInBankAccount()
            {
                Assertion.IsTrue(workFlow.verifyTextInLink(0,"Bank account","Bank account.CustomernameLabelXPATH","XPATH"), "Then verify text Customer name in bank account");
                WebBrowserUtil.captureScreenshot();
                
            }

            @When("^I selected Next btn in bank account$")			
            public void WhenISelectedNextBtnInBankAccount()
            {
            	startTime = System.currentTimeMillis();
                workFlow.clickedElement(0,"Bank account","Bank account.NextbtnButtonXPATH","XPATH");
                WebBrowserUtil.waitForElementToBeVisible("//h1[contains(.,'Select your Loan requirement')]");
                endTime = System.currentTimeMillis();
                timeTakenInSeconds = (endTime - startTime) / 1000.00;
               ExtentCucumberAdapter.addTestStepLog("Time taken to launch Customer Loan Details page: "+timeTakenInSeconds + " Sec");
            }
    }