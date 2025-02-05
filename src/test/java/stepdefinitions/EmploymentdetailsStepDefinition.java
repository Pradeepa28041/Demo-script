package stepdefinitions;
    import io.cucumber.java.en.*;
    import workflows.SeleniumWorkFlow;
    import org.junit.Assert;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;

import common.Assertion;
    import common.WebBrowserUtil;
    public class EmploymentdetailsStepDefinition
	{
        SeleniumWorkFlow workFlow = new SeleniumWorkFlow();
        static long startTime;
        public static long endTime;
        public static double timeTakenInSeconds;
        

            @When("^I entered org or company name in employment details as '(.*)'$")			
            public void WhenIEnteredOrgOrCompanyNameInEmploymentDetailsAsorgOrCompanyName(String  _orgOrCompanyName)
            {
                workFlow.enterText(_orgOrCompanyName,0,"Employment details","Employment details.orgorcompanynameTextBoxXPATH","XPATH");
                
            }

            @When("^I selected ORGANISATION TYPE in employment details$")			
            public void WhenISelectedOrganisationTypeInEmploymentDetails()
            {
                workFlow.clickedElement(0,"Employment details","Employment details.ORGANISATIONTYPEButtonXPATH","XPATH");
                
            }

            @When("^I selected Central Government in employment details$")			
            public void WhenISelectedCentralGovernmentInEmploymentDetails()
            {
                workFlow.clickedElement(0,"Employment details","Employment details.CentralGovernmentButtonXPATH","XPATH");
                
            }

            @When("^I entered COMPANY ADDRESS in employment details as '(.*)'$")			
            public void WhenIEnteredCompanyAddressInEmploymentDetailsAscompanyAddress(String  _companyAddress)
            {
                workFlow.enterText(_companyAddress,0,"Employment details","Employment details.COMPANYADDRESSTextBoxXPATH","XPATH");
                
            }

            @When("^I entered PINCODE in employment details as '(.*)'$")			
            public void WhenIEnteredPincodeInEmploymentDetailsAspincode(String  _pincode)
            {
                workFlow.enterText(_pincode,0,"Employment details","Employment details.PINCODETextBoxXPATH","XPATH");
                
            }

            @When("^I selected Area list in employment details$")			
            public void WhenISelectedAreaListInEmploymentDetails()
            {
                workFlow.clickedElement(0,"Employment details","Employment details.ArealistButtonXPATH","XPATH");
                
            }

            @When("^I selected ElectronicBangalore SouthBangalore 0012 in employment details$")			
            public void WhenISelectedElectronicbangaloreSouthbangalore0012InEmploymentDetails()
            {
                workFlow.clickedElement(0,"Employment details","Employment details.ElectronicBangaloreSouthBangalore0012ButtonXPATH","XPATH");
                
            }

            @When("^I selected current organisation years in employment details$")			
            public void WhenISelectedCurrentOrganisationYearsInEmploymentDetails()
            {
                workFlow.clickedElement(0,"Employment details","Employment details.currentorganisationyearsButtonXPATH","XPATH");
                
            }

            @When("^I selected current org years lst in employment details$")			
            public void WhenISelectedCurrentOrgYearsLstInEmploymentDetails()
            {
                workFlow.clickedElement(0,"Employment details","Employment details.currentorgyearslstButtonXPATH","XPATH");
                
            }

            @When("^I selected current organisation months in employment details$")			
            public void WhenISelectedCurrentOrganisationMonthsInEmploymentDetails()
            {
                workFlow.clickedElement(0,"Employment details","Employment details.currentorganisationmonthsButtonXPATH","XPATH");
                
            }

            @When("^I selected current org months lst in employment details$")			
            public void WhenISelectedCurrentOrgMonthsLstInEmploymentDetails()
            {
                workFlow.clickedElement(0,"Employment details","Employment details.currentorgmonthslstButtonXPATH","XPATH");
                
            }

            @When("^I selected total years in employment details$")			
            public void WhenISelectedTotalYearsInEmploymentDetails()
            {
                workFlow.clickedElement(0,"Employment details","Employment details.totalyearsButtonXPATH","XPATH");
                
            }

            @When("^I selected total years lst in employment details$")			
            public void WhenISelectedTotalYearsLstInEmploymentDetails()
            {
                workFlow.clickedElement(0,"Employment details","Employment details.totalyearslstButtonXPATH","XPATH");
                
            }

            @When("^I selected total months in employment details$")			
            public void WhenISelectedTotalMonthsInEmploymentDetails()
            {
                workFlow.clickedElement(0,"Employment details","Employment details.totalmonthsButtonXPATH","XPATH");
                
            }

            @When("^I selected total months lst in employment details$")			
            public void WhenISelectedTotalMonthsLstInEmploymentDetails()
            {
                workFlow.clickedElement(0,"Employment details","Employment details.totalmonthslstButtonXPATH","XPATH");
                
            }

            @When("^I entered NET MONTHLY SALARY in employment details as '(.*)'$")			
            public void WhenIEnteredNetMonthlySalaryInEmploymentDetailsAsnetMonthlySalary(String  _netMonthlySalary)
            {
                workFlow.enterText(_netMonthlySalary,0,"Employment details","Employment details.NETMONTHLYSALARYTextBoxXPATH","XPATH");
                
            }

            @When("^I entered MONTHLY RENT in employment details as '(.*)'$")			
            public void WhenIEnteredMonthlyRentInEmploymentDetailsAsmonthlyRent(String  _monthlyRent)
            {
                workFlow.enterText(_monthlyRent,0,"Employment details","Employment details.MONTHLYRENTTextBoxXPATH","XPATH");
                
            }

            @When("^I entered EXISTING EMIS IF ANY in employment details as '(.*)'$")			
            public void WhenIEnteredExistingEmisIfAnyInEmploymentDetailsAsexistingEmisIfAny(String  _existingEmisIfAny)
            {
                workFlow.enterText(_existingEmisIfAny,0,"Employment details","Employment details.EXISTINGEMISIFANYTextBoxXPATH","XPATH");
                
            }

            @When("^I selected NEXT button in employment details$")			
            public void WhenISelectedNextButtonInEmploymentDetails()
            {
            	startTime = System.currentTimeMillis();
                workFlow.clickedElement(0,"Employment details","Employment details.NEXTbuttonButtonXPATH","XPATH");
                WebBrowserUtil.waitForElementToBeVisible("//h1[contains(.,'Verify your income')]");
                endTime = System.currentTimeMillis();
                timeTakenInSeconds = (endTime - startTime) / 1000.00;
               ExtentCucumberAdapter.addTestStepLog("Time taken to launch Customer Income Details page: "+timeTakenInSeconds + " Sec");
            
            }
    }