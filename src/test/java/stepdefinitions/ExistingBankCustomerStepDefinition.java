package stepdefinitions;
    import io.cucumber.java.en.*;
    import workflows.SeleniumWorkFlow;
    import org.junit.Assert;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;

import common.Assertion;
    import common.WebBrowserUtil;
    public class ExistingBankCustomerStepDefinition
	{
        SeleniumWorkFlow workFlow = new SeleniumWorkFlow();
        public static long startTime;
        public static long endTime;
        public static double timeTakenInSeconds;
        

            @When("^I entered Customer ID in existing bank customer as '(.*)'$")			
            public void WhenIEnteredCustomerIdInExistingBankCustomerAscustomerId(String  _customerId)
            {
                workFlow.enterTextCustomerID(_customerId,0,"Existing Bank Customer","Existing Bank Customer.CustomerIDTextBoxXPATH","XPATH");
                
            }

            @When("^I entered Captchaa1 in existing bank customer as '(.*)'$")			
            public void WhenIEnteredCaptchaInExistingBankCustomerAscaptcha(String  _captcha)
            {
                workFlow.enterTextCaptcha(_captcha,0,"Existing Bank Customer","Existing Bank Customer.CaptchaTextBoxXPATH","XPATH");
                
            }

            @When("^I checked Terms and conditions in existing bank customer$")			
            public void WhenICheckedTermsConditionsInExistingBankCustomer()
            {
                workFlow.checkCheckbox(0,"Existing Bank Customer","Existing Bank Customer.TermsandconditionsCheckBoxXPATH","XPATH");
                
            }

            @When("^I selected Generate OTP1 in existing bank customer$")			
            public void WhenISelectedGenerateOtpInExistingBankCustomer()
            {
            	startTime = System.currentTimeMillis();
                workFlow.clickedElement(0,"Existing Bank Customer","Existing Bank Customer.GenerateOTPButtonXPATH","XPATH");
                WebBrowserUtil.waitForElementToBeVisible("//h1[contains(.,'Login for a faster Experience')]");
                endTime = System.currentTimeMillis();
                timeTakenInSeconds = (endTime - startTime) / 1000.00;
               ExtentCucumberAdapter.addTestStepLog("Time taken to launch Customer Login OTP Verification page: "+timeTakenInSeconds + " Sec");
                
                
            }

            @When("^I entered ENTER OTP1 in existing bank customer as '(.*)'$")			
            public void WhenIEnteredEnterOtpInExistingBankCustomerAsenterOtp(String  _enterOtp)
            {
                workFlow.enterText(_enterOtp,0,"Existing Bank Customer","Existing Bank Customer.ENTEROTPTextBoxXPATH","XPATH");
                
            }
            @When("^I entered ENTER OTP2 in existing bank customer as '(.*)'$")			
            public void WhenIEnteredEnterOtp2InExistingBankCustomerAsenterOtp(String  _enterOtp)
            {
                workFlow.enterText(_enterOtp,0,"Existing Bank Customer","Existing Bank Customer.ENTEROTP1TextBoxXPATH","XPATH");
                
            }

            @When("^I selected Verify OTP1 in existing bank customer$")			
            public void WhenISelectedVerifyOtpInExistingBankCustomer()
            {
            	Assertion.IsTrue(workFlow.verifyTextInLink(0,"Existing Bank Customer","Existing Bank Customer.VerifyOTPButtonXPATH","XPATH"), "Then verify text Verify OTP Button in existing bank customer");
                WebBrowserUtil.captureScreenshot();
//            	startTime = System.currentTimeMillis();
//                workFlow.clickedElement(0,"Existing Bank Customer","Existing Bank Customer.VerifyOTPButtonXPATH","XPATH");
//                WebBrowserUtil.waitForElementToBeVisible("//h1[contains(.,'PAN VALIDATION')]");
//                endTime = System.currentTimeMillis();
//                timeTakenInSeconds = (endTime - startTime) / 1000.00;
//               ExtentCucumberAdapter.addTestStepLog("Time taken to launch Customer PAN Validation page: "+timeTakenInSeconds + " Sec");
                
            }
            @When("^I selected Verify OTP2 in existing bank customer$")			
            public void WhenISelectedVerifyOtp2InExistingBankCustomer()
            {
            	Assertion.IsTrue(workFlow.verifyTextInLink(0,"Existing Bank Customer","Existing Bank Customer.VerifyOTP1ButtonXPATH","XPATH"), "Then verify text Verify OTP Button in existing bank customer");
                WebBrowserUtil.captureScreenshot();
            }

             @Then("^verify text Pan Validation label in existing bank customer$")			
            public void ThenVerifyTextPanValidationLabelInExistingBankCustomer()
            {
                Assertion.IsTrue(workFlow.verifyTextInLink(0,"Existing Bank Customer","Existing Bank Customer.PanValidationlabelLabelXPATH","XPATH"), "Then verify text Pan Validation label in existing bank customer");
                WebBrowserUtil.captureScreenshot();
                
            }

            @When("^I entered Name as per PAN card in existing bank customer as '(.*)'$")			
            public void WhenIEnteredNameAsPerPanCardInExistingBankCustomerAsnameAsPerPanCard(String  _nameAsPerPanCard)
            {
                workFlow.enterTextPanName(_nameAsPerPanCard,0,"Existing Bank Customer","Existing Bank Customer.NameasperPANcardTextBoxXPATH","XPATH");
                
            }

             @Then("^verify text PAN number in existing bank customer$")			
            public void ThenVerifyTextPanNumberInExistingBankCustomer()
            {
                Assertion.IsTrue(workFlow.verifyTextInLink(0,"Existing Bank Customer","Existing Bank Customer.PANnumberLabelXPATH","XPATH"), "Then verify text PAN number in existing bank customer");
                WebBrowserUtil.captureScreenshot();
                
            }

             @Then("^verify text name in existing bank customer$")			
            public void ThenVerifyTextNameInExistingBankCustomer()
            {
                Assertion.IsTrue(workFlow.verifyTextInLink(0,"Existing Bank Customer","Existing Bank Customer.nameLabelXPATH","XPATH"), "Then verify text name in existing bank customer");
                WebBrowserUtil.captureScreenshot();
                
            }

             @Then("^verify text DOB in existing bank customer$")			
            public void ThenVerifyTextDobInExistingBankCustomer()
            {
                Assertion.IsTrue(workFlow.verifyTextInLink(0,"Existing Bank Customer","Existing Bank Customer.DOBLabelXPATH","XPATH"), "Then verify text DOB in existing bank customer");
                WebBrowserUtil.captureScreenshot();
                
            }

             @Then("^verify text Email ID in existing bank customer$")			
            public void ThenVerifyTextEmailIdInExistingBankCustomer()
            {
                Assertion.IsTrue(workFlow.verifyTextInLink(0,"Existing Bank Customer","Existing Bank Customer.EmailIDLabelXPATH","XPATH"), "Then verify text Email ID in existing bank customer");
                WebBrowserUtil.captureScreenshot();
                
            }

             @Then("^verify text Mobile number in existing bank customer$")			
            public void ThenVerifyTextMobileNumberInExistingBankCustomer()
            {
                Assertion.IsTrue(workFlow.verifyTextInLink(0,"Existing Bank Customer","Existing Bank Customer.MobilenumberLabelXPATH","XPATH"), "Then verify text Mobile number in existing bank customer");
                WebBrowserUtil.captureScreenshot();
                
            }

            @When("^I selected Next in existing bank customer$")			
            public void WhenISelectedNextInExistingBankCustomer()
            {
            	startTime = System.currentTimeMillis();
                workFlow.clickedElement(0,"Existing Bank Customer","Existing Bank Customer.NextButtonXPATH","XPATH");
                WebBrowserUtil.waitForElementToBeVisible("//p[contains(.,'Select the account with which you want to continue the Xpress Cash Application')]");
                endTime = System.currentTimeMillis();
                timeTakenInSeconds = (endTime - startTime) / 1000.00;
               ExtentCucumberAdapter.addTestStepLog("Time taken to launch Customer Bank account page: "+timeTakenInSeconds + " Sec");
            }
    }