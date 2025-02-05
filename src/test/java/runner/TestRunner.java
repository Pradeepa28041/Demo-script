package runner;

//Only extent report  is present with "spark-config" to set theme,title,report name etc in the extent report generated
//import java.text.SimpleDateFormat;
//import java.util.Date;
import org.junit.AfterClass;
import common.WebBrowser;
import io.cucumber.junit.Cucumber;
//import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.CucumberOptions;

import org.junit.BeforeClass; 
//import com.cucumber.listener.ExtentProperties;

import org.junit.runner.RunWith;


//import cucumber.api.CucumberOptions;
//import cucumber.api.junit.Cucumber;
import com.github.mkolisnyk.cucumber.runner.ExtendedCucumber;
import com.github.mkolisnyk.cucumber.runner.ExtendedCucumberOptions;
//import com.github.mkolisnyk.cucumber.runner.RetryAcceptance;

//@RunWith(ExtendedCucumber.class)
@RunWith(Cucumber.class)
/*@ExtendedCucumberOptions(
        jsonReport = "target/cucumber-reports/CucumberTestReport.json",
        retryCount = 0,
        detailedReport = true,
        detailedAggregatedReport = true,
        overviewReport = true,
        coverageReport = true,
        jsonUsageReport = "target/cucumber-reports/cucumber-usage.json",
        usageReport = false,
        toPDF = true,
        excludeCoverageTags = {"@flaky" },
        includeCoverageTags = {"@passed" },
        outputFolder = "target/cucumber-reports/extended-report")  */
@CucumberOptions(
		features = "src/test/java/features",
		glue= {"stepdefinitions","common"},
		//plugin = {"pretty", "html:out"}
		//plugin = {"ru.yandex.qatools.allure.cucumberjvm.AllureReporter","com.cucumber.listener.ExtentCucumberFormatter:"}
        //features={"."}
				plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:","common.StepListener"}
		)
public class TestRunner {
	public static int retries = 0;
	
	
	
	
/*	@BeforeClass
	public static void setup() {
		
		String reportPath=System.getProperty("ReportPath");
		
		if(reportPath != null && !reportPath.isEmpty())
		{
			System.out.println("Report Path is-----------"+reportPath);
			ExtentProperties extentProperties = ExtentProperties.INSTANCE;
			extentProperties.setReportPath(reportPath);
		}	 
	}  */ 

	
	@AfterClass
    public static void tearDown(){
		
		if (WebBrowser.isBrowserOpened())
        {           
            WebBrowser.closeBrowserInstance();
            
        }
    }
	
	
	
}
