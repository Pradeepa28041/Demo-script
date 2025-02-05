package stepdefinitions;
    import io.cucumber.java.en.*;
    import workflows.SeleniumWorkFlow;
    import org.junit.Assert;
    import common.Assertion;
    import common.WebBrowserUtil;
    public class NewFeatureStepDefinition
	{
        SeleniumWorkFlow workFlow = new SeleniumWorkFlow();
        

             @Then("^verify text Karnataka Bank logo in home page$")			
            public void ThenVerifyTextKarnatakaBankLogoInHomePage()
            {
                Assertion.IsTrue(workFlow.verifyTextInLink(0,"New Feature","New Feature.KarnatakaBanklogoLabelXPATH","XPATH"), "Then verify text Karnataka Bank logo in home page");
                WebBrowserUtil.captureScreenshot();
                
            }

             @Then("^verify text Select Bank Account in bank account$")			
            public void ThenVerifyTextSelectBankAccountInBankAccount()
            {
                Assertion.IsTrue(workFlow.verifyTextInLink(0,"New Feature","New Feature.SelectBankAccountLabelXPATH","XPATH"), "Then verify text Select Bank Account in bank account");
                WebBrowserUtil.captureScreenshot();
                
            }
    }