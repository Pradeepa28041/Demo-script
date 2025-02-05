package stepdefinitions;
    import io.cucumber.java.en.*;
    import workflows.SeleniumWorkFlow;
    import org.junit.Assert;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;

import common.Assertion;
    import common.WebBrowserUtil;
    public class DetailsVerificationStepDefinition
	{
        SeleniumWorkFlow workFlow = new SeleniumWorkFlow();
        static long startTime;
        public static long endTime;
        public static double timeTakenInSeconds;
        

            @When("^I selected RELIGION dropdown icon in details verification$")			
            public void WhenISelectedReligionDropdownIconInDetailsVerification()
            {
                workFlow.clickedElement(0,"Details Verification","Details Verification.RELIGIONdropdowniconButtonXPATH","XPATH");
                
            }

            @When("^I selected Hindu Religion in details verification$")			
            public void WhenISelectedHinduReligionInDetailsVerification()
            {
                workFlow.clickedElement(0,"Details Verification","Details Verification.HinduReligionButtonXPATH","XPATH");
                
            }

            @When("^I selected Category dropdown Icon in details verification$")			
            public void WhenISelectedCategoryDropdownIconInDetailsVerification()
            {
                workFlow.clickedElement(0,"Details Verification","Details Verification.CategorydropdownIconButtonXPATH","XPATH");
                
            }

            @When("^I selected General Category in details verification$")			
            public void WhenISelectedGeneralCategoryInDetailsVerification()
            {
                workFlow.clickedElement(0,"Details Verification","Details Verification.GeneralCategoryButtonXPATH","XPATH");
                
            }

            @When("^I selected Differently Abled NO in details verification$")			
            public void WhenISelectedDifferentlyAbledNoInDetailsVerification()
            {
                workFlow.clickedElement(0,"Details Verification","Details Verification.DifferentlyAbledNOButtonXPATH","XPATH");
                
            }

            @When("^I selected Visually challenged No in details verification$")			
            public void WhenISelectedVisuallyChallengedNoInDetailsVerification()
            {
                workFlow.clickedElement(0,"Details Verification","Details Verification.VisuallychallengedNoButtonXPATH","XPATH");
                
            }

            @When("^I selected I am not Related Check bBOx in details verification$")			
            public void WhenISelectedIAmNotRelatedCheckBboxInDetailsVerification()
            {
                workFlow.clickedElement(0,"Details Verification","Details Verification.IamnotRelatedCheckbBOxButtonXPATH","XPATH");
                
            }

            @When("^I selected Next Button in details verification$")			
            public void WhenISelectedNextButtonInDetailsVerification()
            {
            	startTime = System.currentTimeMillis();
                workFlow.clickedElement(0,"Details Verification","Details Verification.NextButtonButtonXPATH","XPATH");
                WebBrowserUtil.waitForElementToBeVisible("//p[contains(.,'Your loan has been approved in principle by Karnataka Bank.')]");
                endTime = System.currentTimeMillis();
                timeTakenInSeconds = (endTime - startTime) / 1000.00;
               ExtentCucumberAdapter.addTestStepLog("Time taken to launch Loan Offer Confirmation page: "+timeTakenInSeconds + " Sec");
            
            }

            @When("^I wait in seconds Wait in details verification as '(.*)'$")			
            public void WhenIWaitInSecondsWaitInDetailsVerificationAswait1(String  _wait1)
            {
                workFlow.waitInSeconds(_wait1,0,"Details Verification","Details Verification.WaitTextBoxXPATH","XPATH");
                
            }

            @When("^I selected Proceed to next in details verification$")			
            public void WhenISelectedProceedToNextInDetailsVerification()
            {
                workFlow.clickedElement(0,"Details Verification","Details Verification.ProceedtonextButtonXPATH","XPATH");
                
            }

             @Then("^'(.*)' is displayed with '(.*)'$")			
            public void ThenpageIsDisplayedWithcontent(String  _page, String _content)
            {
                Assertion.IsTrue(workFlow.VerifyDefaultpageIsdisplayed(_page), "Then '<page>' is displayed with '<content>'");
                Assertion.IsTrue(workFlow.VerifymessageIsDisplayed(_content), "");;
                //Assertion.assertAll();
            }
    }