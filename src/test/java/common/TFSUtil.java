
package common;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.json.JSONArray;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class TFSUtil {

	public String AUTHORIZATION = "Authorization";
	public String APPLICATION_JSON = "application/json";
	public static String TFSAuth = "TFSAUTH";
	public String CONTENT_TYPE = "Content-Type";
	public String POINTS = "points[";
	
	public static String yourTfsUrl="yourUrl";
	public static String tfsServer="yourServer";
	public static String tfsCollection="TPC_Region16";
	public static String tfsProject="project_Name";

	private static String path = System.getProperty("user.dir");
	public static String APPLICATIONSETTINGS = "ApplicationSettings.xml";
	public static String stestSuiteId = CommonUtil.GetXMLData(
			Paths.get(path.toString(), "src", "test", "java", APPLICATIONSETTINGS).toString(), "TestSuiteId");
	public static String authKey = CommonUtil
			.GetXMLData(Paths.get(path.toString(), "src", "test", "java", APPLICATIONSETTINGS).toString(), TFSAuth);
	public static int testSuiteId;
	public static String url = CommonUtil.GetXMLData(
			Paths.get(path.toString(), "src", "test", "java", APPLICATIONSETTINGS).toString(), "AUTH_HOST_URI");
	

	private Response adsQueryResp;

	public void setAdsResponse(Response response) {
		this.adsQueryResp = response;
	}

	public Response getAdsResponse() {
		return adsQueryResp;
	}

	private String reqIdsCsv;

	public void setReqIdsCsv(String reqIdsCsv) {
		this.reqIdsCsv = reqIdsCsv;
	}

	public String getReqIdsCsv() {
		return reqIdsCsv;
	}

	private String testIdsCsv;

	public void setTestIdsCsv(String testIds) {
		this.testIdsCsv = testIds;
	}

	public String getTestIdsCsv() {
		return testIdsCsv;
	}

	private String adsFolderId;

	public void setAdsFolderId(String folderId) {
		this.adsFolderId = folderId;
	}

	public String getAdsFolderId() {
		return adsFolderId;
	}

	private List<String> uniqTestList;

	public void setUniqTestList(List<String> testList) {
		this.uniqTestList = new ArrayList<>(testList);
	}

	public List<String> getUniqTestList() {
		return uniqTestList;
	}

	private List<String> nodeArray;

	public void setNodeArray(List<String> nodeArrayList) {
		this.nodeArray = nodeArrayList;
	}

	public List<String> getNodeArray() {
		return nodeArray;
	}

	public int[] getTestPointId(int testCaseId) {
		int len;
		int[] testIds = new int[2];
		int tstSuiteId = getTestSuiteId();
		RestAssured.baseURI = "https://"+tfsServer+".com";
		
		Response response = given().header(CONTENT_TYPE, APPLICATION_JSON).header(AUTHORIZATION, authKey)
				.body("{\n" + "  \"PointsFilter\": {\n" + "    \"TestcaseIds\": [\n" + testCaseId + "\n" + "    ]\n"
						+ "  }\n" + "}")
				.post("/tfs/TPC_Region16/"+tfsProject+"/_apis/test/points?api-version=6.0-preview.2").then().log().all().extract()
				.response();
		len = response.jsonPath().getInt("points.size()");
		for (int i = 0; i < len; i++) {
			if (response.jsonPath().getInt(POINTS + i + "].suite.id") == tstSuiteId) {
				testIds[0] = response.jsonPath().getInt(POINTS + i + "].id");
				testIds[1] = response.jsonPath().getInt(POINTS + i + "].testPlan.id");
				break;
			}
		}
		return testIds;

	}

	public List<Map<String, Object>> getTestPointIdList(String testCaseIds) {
		int length, testPoint, testPlan;
		List<Map<String, Object>> testList = new ArrayList<>();
		RestAssured.baseURI = "https://"+tfsServer+".com";
		
		Response response1 = given().header(CONTENT_TYPE, APPLICATION_JSON).header(AUTHORIZATION, authKey)
				.body("{\n" + "  \"PointsFilter\": {\n" + "    \"TestcaseIds\": [\n" + testCaseIds + "\n" + "    ]\n"
						+ "  }\n" + "}")
				.post("/tfs/TPC_Region16/"+tfsProject+"/_apis/test/points?api-version=6.0-preview.2").then().log().all().extract()
				.response();
		length = response1.jsonPath().getInt("points.size()");
		for (int j = 0; j < length; j++) {
			Map<String, Object> testIds = new HashMap<String, Object>();
			if (response1.jsonPath().getInt(POINTS + j + "].suite.id") == Integer.parseInt(stestSuiteId)) {
				testPoint = response1.jsonPath().getInt(POINTS + j + "].id");
				System.out.println(POINTS + j + "].testPlanId is: " + testPoint);
				testPlan = response1.jsonPath().getInt(POINTS + j + "].testPlan.id");
				testIds.put("testPointId", testPoint);
				testIds.put("testPlan", testPlan);
				testList.add(testIds);
			}

		}
		return testList;
	}

	public List<Map<String, Object>> getTestResultIds(List<Map<String, Object>> testList) {
		int len, testRunId, testResultId;
		int tstSuiteId = getTestSuiteId();
		List<Map<String, Object>> testResultsList = new ArrayList<>();
		Map<String, Object> testPlan = testList.get(0);

		int testPlanId = (int) testPlan.get("testPlan");
		RestAssured.baseURI = "https://"+tfsServer+".com";
		
		Response response = given().header(CONTENT_TYPE, APPLICATION_JSON).header(AUTHORIZATION, authKey).header(
				"accept",
				"application/json;api-version=6.0-preview.2;excludeUrls=true;enumsAsNumbers=true;msDateFormat=true;noArrayWrap=true")
				.body(PayLoad.bulkGenerateRunId(testList)).patch("/tfs/TPC_Region16/"+tfsProject+"/_apis/testplan/Plans/"
						+ testPlanId + "/Suites/" + tstSuiteId + "/TestPoint")
				.then().log().all().extract().response();
		JSONObject resultJson;
		JSONArray jsonArray = new JSONArray(response.body().asString());
		len = jsonArray.length();
		for (int i = 0; i < len; i++) {
			Map<String, Object> testResultsMap = new HashMap<String, Object>();
			resultJson = jsonArray.getJSONObject(i).getJSONObject("results");
			testRunId = resultJson.getInt("lastTestRunId");
			testResultId = resultJson.getInt("lastResultId");
			testResultsMap.put("testRunId", testRunId);
			testResultsMap.put("testResultId", testResultId);
			testResultsList.add(testResultsMap);
		}
		System.out.println("Test Result List is : " + testResultsList);
		return testResultsList;
	}

	public void bulkUpdateTestResults(List<Map<String, Object>> testResults, String version) {
		System.out.println("Inside Bulk upload method");
		System.out.println("Version is :" + version);
		RestAssured.baseURI = "https://"+tfsServer+".com";
		
		Response response = given().header(CONTENT_TYPE, APPLICATION_JSON).header(AUTHORIZATION, authKey)
				.body(PayLoad.bulkUploadResultsToTFS(testResults, version))
				.post("/tfs/TPC_Region16/6c85a2d3-7c45-4990-b6b1-aa92429d388a/_api/_testresult/Update?__v=5").then()
				.log().all().extract().response();
		System.out.println("response: " + response.getBody().asString());

	}

	public static int getTestSuiteId() {
		try {
			testSuiteId = Integer.parseInt(stestSuiteId);
		} catch (Exception ex) {
			throw new CustomException(ex.getMessage());

		}
		return testSuiteId;

	}

	public int getTestRunId(int testCaseId, int testPointId, int testPlanId) {
		int testRunId;
		String sTestRunId;
		RestAssured.baseURI = "https://"+tfsServer+".com";
		
		Response response = given().header(CONTENT_TYPE, APPLICATION_JSON).header(AUTHORIZATION, authKey).body(
				"{\"runModel\":\"{\\\"title\\\":\\\"Automated Test Results\\\",\\\"iteration\\\":\\\""+tfsProject+"\\\",\\\"state\\\":2,\\\"testPlanId\\\":"
						+ testPlanId + "}\",\"testResultCreationRequestModels\":\"[{\\\"testCaseId\\\":" + testCaseId
						+ ",\\\"testPointId\\\":" + testPointId + "}]\"}")
				.post("/tfs/TPC_Region16/6c85a2d3-7c45-4990-b6b1-aa92429d388a/_api/_testrun/Create?__v=5").then().log()
				.all().extract().response();
		sTestRunId = response.jsonPath().getString("testResultCreationResponseModels.id.testRunId");
		sTestRunId = sTestRunId.substring(1, sTestRunId.length() - 1);
		testRunId = Integer.parseInt(sTestRunId);
		return testRunId;

	}

	public void updateResultsToTFS(int testCaseId, int testPointId, int testRunId, List<Map<String, Object>> testSteps,
			String userName, String password) {
		JSONObject payload = new JSONObject();
		
		payload.put("testCaseId", testCaseId);
		payload.put("testPointId", testPointId);
		payload.put("testRunId", testRunId);
		payload.put("testCaseStatus", "Passed");
		payload.put("comments", "comments to be added ");
		payload.put("testSteps", testSteps);
		String pload = payload.toString();

		RestAssured.baseURI = "https://"+tfsServer+".com";
		Response response = given()

				.header(AUTHORIZATION, authKey).body(pload)
				.post("/TestRunUpdateService.svc/UpdateTest/Json?server="+tfsServer+"&collection="+tfsCollection+"&project="+tfsProject)
				.then().log().all().extract().response();
		System.out.println(response);
	}

}

class PayLoad {

	public static String bulkGenerateRunId(List<Map<String, Object>> testList) {

		List<Object> tstIds = new ArrayList<>();
		Map<String, Object> testPlan;
		int testPlnId;
		int length = testList.size();
		for (int i = 0; i < length; i++) {
			JSONObject payload = new JSONObject();
			JSONObject payload2 = new JSONObject();
			testPlan = testList.get(i);
			testPlnId = (int) testPlan.get("testPointId");
			payload.put("id", testPlnId);
			payload2.put("outcome", 2);
			payload.put("results", payload2);
			tstIds.add(payload);
		}
		System.out.println("Payload for Bulk Run Ids is " + tstIds.toString());

		return tstIds.toString();

	}

	public static String bulkUploadResultsToTFS(List<Map<String, Object>> testList, String version) {

		int len;
		len = testList.size();
		Map<String, Object> testResult;
		testResult = testList.get(0);
		String testRunid = testResult.get("testRunId").toString();
		System.out.println("Test RunId is :" + testRunid);
		String testResultId = testResult.get("testResultId").toString();
		String results = "[{\"testCaseResult\":{\"outcome\":2,\"state\":5},\"actionResults\":[{\"outcome\":2,\"comment\":\"Tested in Version: "
				+ version + "\",";
		results = results
				+ "\"iterationId\":1}],\"actionResultDeletes\":[],\"parameters\":[],\"parameterDeletes\":[],\"testRunId\":"
				+ testRunid + ",\"testResultId\":" + testResultId + "}";
		System.out.println("Result String is " + results);
		for (int i = 1; i < len; i++) {
			testResult = testList.get(i);
			testResultId = testResult.get("testResultId").toString();
			results = results
					+ ",{\"testCaseResult\":{\"outcome\":2,\"state\":5},\"actionResults\":[{\"outcome\":2,\"comment\":\"Tested in Version: "
					+ version + "\",";
			results = results
					+ "\"iterationId\":1}],\"actionResultDeletes\":[],\"parameters\":[],\"parameterDeletes\":[],\"testRunId\":"
					+ testRunid + ",\"testResultId\":" + testResultId + "}";

		}
		results = results + "]";
		JSONObject payload = new JSONObject();
		payload.put("updateRequests", results);
		System.out.println("Payload of Bulkupdate is " + payload.toString());
		return payload.toString();
	}


}
