package common;
import common.WebBrowser;

import org.junit.Assert;
public class Assertion{
	public static void IsTrue(boolean result,String message) {		
        CommonUtil.error="Assertion failure in step : "+message;
		if(WebBrowser.boolEachSoftAssersion) {
	    Hooks.softAssertions.assertTrue(result,message);
		}
		else {			
			Assert.assertTrue(result);
		}
	}
	public static void assertAll()
	{
		if(WebBrowser.boolEachSoftAssersion)
        {
             Hooks.softAssertions.assertAll();
        }
	}
}
