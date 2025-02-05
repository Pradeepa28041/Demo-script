package stepdefinitions;
    import io.cucumber.java.en.*;
    import workflows.SeleniumWorkFlow;
    import org.junit.Assert;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;

import common.Assertion;
    import common.WebBrowserUtil;
    public class PersonaldetailsStepDefinition
	{
        SeleniumWorkFlow workFlow = new SeleniumWorkFlow();
        static long startTime;
        public static long endTime;
        public static double timeTakenInSeconds;
        

             @Then("^verify text DOB personal detail in personal details$")			
            public void ThenVerifyTextDobPersonalDetailInPersonalDetails()
            {
                Assertion.IsTrue(workFlow.verifyTextInLink(0,"Personal details","Personal details.DOBpersonaldetailLabelXPATH","XPATH"), "Then verify text DOB personal detail in personal details");
                WebBrowserUtil.captureScreenshot();
                
            }

             @Then("^verify text GENDER in personal details$")			
            public void ThenVerifyTextGenderInPersonalDetails()
            {
                Assertion.IsTrue(workFlow.verifyTextInLink(0,"Personal details","Personal details.GENDERLabelXPATH","XPATH"), "Then verify text GENDER in personal details");
                WebBrowserUtil.captureScreenshot();
                
            }

             @Then("^verify text MARITAL STATUS in personal details$")			
            public void ThenVerifyTextMaritalStatusInPersonalDetails()
            {
                Assertion.IsTrue(workFlow.verifyTextInLink(0,"Personal details","Personal details.MARITALSTATUSLabelXPATH","XPATH"), "Then verify text MARITAL STATUS in personal details");
                WebBrowserUtil.captureScreenshot();
                
            }

             @Then("^verify text Resident Indian in personal details$")			
            public void ThenVerifyTextResidentIndianInPersonalDetails()
            {
                Assertion.IsTrue(workFlow.verifyTextInLink(0,"Personal details","Personal details.ResidentIndianLabelXPATH","XPATH"), "Then verify text Resident Indian in personal details");
                WebBrowserUtil.captureScreenshot();
                
            }

             @Then("^verify text CURRENT ADDRESS in personal details$")			
            public void ThenVerifyTextCurrentAddressInPersonalDetails()
            {
                Assertion.IsTrue(workFlow.verifyTextInLink(0,"Personal details","Personal details.CURRENTADDRESSLabelXPATH","XPATH"), "Then verify text CURRENT ADDRESS in personal details");
                WebBrowserUtil.captureScreenshot();
                
            }

             @Then("^verify text PINCODE in personal details$")			
            public void ThenVerifyTextPincodeInPersonalDetails()
            {
                Assertion.IsTrue(workFlow.verifyTextInLink(0,"Personal details","Personal details.PINCODELabelXPATH","XPATH"), "Then verify text PINCODE in personal details");
                WebBrowserUtil.captureScreenshot();
                
            }

             @Then("^verify text CITY in personal details$")			
            public void ThenVerifyTextCityInPersonalDetails()
            {
                Assertion.IsTrue(workFlow.verifyTextInLink(0,"Personal details","Personal details.CITYLabelXPATH","XPATH"), "Then verify text CITY in personal details");
                WebBrowserUtil.captureScreenshot();
                
            }

            @When("^I entered EMAIL ADDRESS in personal details as '(.*)'$")			
            public void WhenIEnteredEmailAddressInPersonalDetailsAsemailAddress(String  _emailAddress)
            {
                workFlow.enterText(_emailAddress,0,"Personal details","Personal details.EMAILADDRESSTextBoxXPATH","XPATH");
                
            }

             @Then("^verify text GraduateQUALIFICATION in personal details$")			
            public void ThenVerifyTextGraduatequalificationInPersonalDetails()
            {
                Assertion.IsTrue(workFlow.verifyTextInLink(0,"Personal details","Personal details.GraduateQUALIFICATIONLabelXPATH","XPATH"), "Then verify text GraduateQUALIFICATION in personal details");
                WebBrowserUtil.captureScreenshot();
                
            }

            @When("^I selected RESIDENCE TYPE in personal details$")			
            public void WhenISelectedResidenceTypeInPersonalDetails()
            {
                workFlow.clickedElement(0,"Personal details","Personal details.RESIDENCETYPEButtonXPATH","XPATH");
                
            }

            @When("^I selected FamilyOwned in personal details$")			
            public void WhenISelectedFamilyownedInPersonalDetails()
            {
                workFlow.clickedElement(0,"Personal details","Personal details.FamilyOwnedButtonXPATH","XPATH");
                
            }

            @When("^I selected NUMBER OF DEPENDENTS in personal details$")			
            public void WhenISelectedNumberOfDependentsInPersonalDetails()
            {
                workFlow.clickedElement(0,"Personal details","Personal details.NUMBEROFDEPENDENTSButtonXPATH","XPATH");
                
            }

            @When("^I selected Num1 dependents in personal details$")			
            public void WhenISelectedNum1DependentsInPersonalDetails()
            {
                workFlow.clickedElement(0,"Personal details","Personal details.Num1dependentsButtonXPATH","XPATH");
                
            }

            @When("^I selected SELECT ID PROOF in personal details$")			
            public void WhenISelectedSelectIdProofInPersonalDetails()
            {
                workFlow.clickedElement(0,"Personal details","Personal details.SELECTIDPROOFButtonXPATH","XPATH");
                
            }

            @When("^I selected aadhaar as proof in personal details$")			
            public void WhenISelectedAadhaarAsProofInPersonalDetails()
            {
                workFlow.clickedElement(0,"Personal details","Personal details.aadhaarasproofButtonXPATH","XPATH");
                
            }

            @When("^I entered ENTER AADHAAR NUMBER in personal details as '(.*)'$")			
            public void WhenIEnteredEnterAadhaarNumberInPersonalDetailsAsenterAadhaarNumber(String  _enterAadhaarNumber)
            {
                workFlow.enterText(_enterAadhaarNumber,0,"Personal details","Personal details.ENTERAADHAARNUMBERTextBoxXPATH","XPATH");
                
            }

            @When("^I entered REENTER ID PROOF NUMBER in personal details as '(.*)'$")			
            public void WhenIEnteredReenterIdProofNumberInPersonalDetailsAsreenterIdProofNumber(String  _reenterIdProofNumber)
            {
                workFlow.enterText(_reenterIdProofNumber,0,"Personal details","Personal details.REENTERIDPROOFNUMBERTextBoxXPATH","XPATH");
                
            }

            @When("^I checked NO FACILITY NEEDED in personal details$")			
            public void WhenICheckedNoFacilityNeededInPersonalDetails()
            {
                workFlow.checkCheckbox(0,"Personal details","Personal details.NOFACILITYNEEDEDCheckBoxXPATH","XPATH");
                
            }

            @When("^I selected NEXTbutton in personal details$")			
            public void WhenISelectedNextbuttonInPersonalDetails()
            {
            	startTime = System.currentTimeMillis();
                workFlow.clickedElement(0,"Personal details","Personal details.NEXTbuttonButtonXPATH","XPATH");
                WebBrowserUtil.waitForElementToBeVisible("//input[@formcontrolname='empName']");
                endTime = System.currentTimeMillis();
                timeTakenInSeconds = (endTime - startTime) / 1000.00;
               ExtentCucumberAdapter.addTestStepLog("Time taken to launch Customer Employment Details page: "+timeTakenInSeconds + " Sec");
            
            }
    }