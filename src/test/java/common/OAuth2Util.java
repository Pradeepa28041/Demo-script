package common;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class OAuth2Util {


	public static String getAccessTokenfromOAuthAPI(String text) {
		String accessToken = "";
		try {			
			JSONObject object = new JSONObject(text);
			Iterator<String> keys = object.keys();

			JsonParser parser = new JsonParser();
			JsonObject obj = parser.parse(text).getAsJsonObject();
			String client_authentication = obj.has("client_authentication") ? obj.get("client_authentication").getAsString() : "";
			String state = obj.has("state") ? obj.get("state").getAsString() : "";
			String authUrl = obj.has("authUrl") ? obj.get("authUrl").getAsString() : "";
			String clientId = obj.has("clientId") ? obj.get("clientId").getAsString() : "";
			String callbackUrl = obj.has("redirect_uri") ? obj.get("redirect_uri").getAsString() : "";
			String scope = obj.has("scope") ? obj.get("scope").getAsString() : "";
			String accessTokenUrl = obj.has("accessTokenUrl") ? obj.get("accessTokenUrl").getAsString() : "";
			String clientSecret = obj.has("clientSecret") ? obj.get("clientSecret").getAsString() : "";
			boolean userBrowser = false;

			String grant_type = obj.has("grant_type") ? obj.get("grant_type").getAsString() : "";
			String addTokenTo = obj.has("addTokenTo") ? obj.get("addTokenTo").getAsString() : "";

			if (grant_type.equals("authorization_code")) {
				String browserString = obj.get("useBrowser").getAsString();
				userBrowser = Boolean.parseBoolean(browserString);
				if(userBrowser) {
					String username="";
					String password="";
					String userBrowserData = obj.has("userBrowserData") ? obj.get("userBrowserData").getAsString() : "";
					JSONArray useBrowserDataArray = object.getJSONArray("useBrowserData");

					for (int i = 0; i < useBrowserDataArray.length(); i++) {
						JSONObject data = useBrowserDataArray.getJSONObject(i);
						String name = data.getString("name");
						String value = data.getString("value");

						if ("username".equals(name)) {
							username = value;
						} else if ("Password".equals(name)) {
							password = value;
						}
					}			
					String authorizationUrl	= authUrl+ "response_type=code" +"&client_id=" + clientId +
							"&redirect_uri=" + callbackUrl +
							"&scope=" + scope;

					WebDriver browser = WebBrowser.getBrowser();
					browser.get(authorizationUrl);

					JsonArray userBrowsers = obj.get("useBrowserData").getAsJsonArray();
					userBrowsers.forEach(action -> {
						JsonObject loactorObj = action.getAsJsonObject();
						String name = loactorObj.get("name").getAsString();
						String value = loactorObj.get("value").getAsString();
						String xpath = loactorObj.get("locator").getAsString();
						try{
							Thread.sleep(2000);
						}
						catch(Exception e)
						{}					
						if(!value.equals("")) {
							WebElement element = browser.findElement(By.xpath(xpath));
							element.sendKeys(value);
						}
						else {
							WebElement element = browser.findElement(By.xpath(xpath));
							element.click();
						}
					});
					Thread.sleep(5000);
					String currentUrl = browser.getCurrentUrl();	

					browser.close();

					Thread.sleep(10000);
					int codeStartIndex = currentUrl.indexOf("code=") + 5;
					int codeEndIndex = currentUrl.indexOf("&", codeStartIndex);
					if (codeEndIndex == -1) {
						codeEndIndex = currentUrl.length();
					}

					String encodedAuthorizationCode = currentUrl.substring(codeStartIndex, codeEndIndex);
					//String decodedAuthorizationCode = URLDecoder.decode(encodedAuthorizationCode, StandardCharsets.UTF_8);
					String decodedAuthorizationCode = encodedAuthorizationCode.replace("%2F", "/");

					//decodedAuthorizationCode = decodedAuthorizationCode.replace("%2F", "/");

					Response response = RestAssured.given()
							.formParam("code", decodedAuthorizationCode)
							.formParam("client_id", clientId)
							.formParam("client_secret", clientSecret)
							.formParam("redirect_uri", callbackUrl)
							.formParam("grant_type", "authorization_code")
							.post(accessTokenUrl);

					accessToken = response.jsonPath().getString("access_token");
					System.out.println(accessToken);
					ExtentCucumberAdapter.addTestStepLog("access token is: "+ accessToken);

				}
				else {
					String username = obj.has("auth_username") ? obj.get("auth_username").getAsString() : "";
					String password = obj.has("auth_password") ? obj.get("auth_password").getAsString() : "";


					Response response = RestAssured.given()
							.formParam("grant_type", "password")
							.formParam("client_id", clientId)
							.formParam("client_secret", clientSecret)
							.formParam("username", username)
							.formParam("password", password)
							.post(accessTokenUrl);
					System.out.println(response.getBody().asString());
					accessToken = response.jsonPath().getString("access_token");
					ExtentCucumberAdapter.addTestStepLog("access token is: "+ accessToken);
				}
			}
			else if (grant_type.equals("client_credentials")) {
				RequestSpecification requestSpec = RestAssured.given();

				requestSpec.baseUri(accessTokenUrl);
				Response response = requestSpec.auth().preemptive().basic(clientId, clientSecret)
						.param("grant_type", "client_credentials")
						.post();
				response.prettyPrint();
				System.out.println("Response code: " + response.statusCode());
				//System.out.println("status line: " + response.statusLine());
				accessToken = response.getBody().path("access_token");
				System.out.println("access token: " + accessToken);
			}
			else {
				String username = obj.has("username") ? obj.get("username").getAsString() : "";
				String password = obj.has("password") ? obj.get("password").getAsString() : "";


				RequestSpecification requestSpec = RestAssured.given()
						.contentType("application/x-www-form-urlencoded")
						.formParam("grant_type", "password")
						.formParam("client_id", clientId)
						.formParam("username", username)
						.formParam("password", password);
				Response response = requestSpec.post(accessTokenUrl);
				//System.out.println(response.getBody().asString());
				accessToken = response.jsonPath().getString("access_token");
				System.out.println("access token is: "+ accessToken);
				ExtentCucumberAdapter.addTestStepLog("access token is: "+ accessToken);
			}
			return accessToken;
		}
		catch(Exception e) {
			System.out.println("Err: " + e.getMessage());
			throw new CustomException(e.getMessage(), e);
		}

	}
}
