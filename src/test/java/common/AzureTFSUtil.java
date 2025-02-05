package common;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Base64;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Base64;

public class AzureTFSUtil {

    public static String ORGANIZATION = "";
    public static String PROJECT = "";
    public static String PAT = "";
    public static String path = System.getProperty("user.dir");
    public static String APPLICATIONSETTINGS = "ApplicationSettings.xml";

    public static void createAzureTFSIssue(String title, String description) {
        String AzureIntegrationTFSCredentials = CommonUtil.GetXMLData(
                Paths.get(path.toString(), "src", "test", "java", "ApplicationSettings.xml").toString(),
                "AzureIntegrationTFSCredentials");

        String[] credentials = AzureIntegrationTFSCredentials.split(",");
        ORGANIZATION = credentials[0];
        PROJECT = credentials[1];
        PAT = credentials[2];

        try {
            // Check if an issue with the same title already exists
            if (issueExists(title)) {
                System.out.println("An issue with the title '" + title + "' already exists.");
                return; // Exit if a duplicate issue is found
            }

            // Set the URL for the Azure DevOps REST API endpoint to create a new work item
            URL url = new URL("https://dev.azure.com/" + ORGANIZATION + "/" + PROJECT + "/_apis/wit/workitems/$Issue?api-version=7.0");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Set up authentication with PAT
            String auth = ":" + PAT;
            String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
            conn.setRequestProperty("Authorization", "Basic " + encodedAuth);

            // Set the request method and headers
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json-patch+json");
            conn.setDoOutput(true);

            // Set up JSON array for the payload
            JSONArray jsonArray = new JSONArray();
            jsonArray.put(new JSONObject().put("op", "add").put("path", "/fields/System.Title").put("value", title));
            jsonArray.put(new JSONObject().put("op", "add").put("path", "/fields/System.Description").put("value", description));

            String jsonPayload = jsonArray.toString(); // Convert to string
            System.out.println("Generated JSON Payload: " + jsonPayload);

            // Send the JSON payload to the server
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonPayload.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Get the response code
            int responseCode = conn.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            if (responseCode == 200 || responseCode == 201) {
                System.out.println("Work item created successfully.");
            } else {
                System.out.println("Failed to create work item.");
            }

            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean issueExists(String title) {
        try {
            // URL to search for work items with the specified title
            URL searchUrl = new URL("https://dev.azure.com/" + ORGANIZATION + "/" + PROJECT +
                    "/_apis/wit/wiql?api-version=7.0");
            HttpURLConnection conn = (HttpURLConnection) searchUrl.openConnection();

            // Set up authentication with PAT
            String auth = ":" + PAT;
            String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
            conn.setRequestProperty("Authorization", "Basic " + encodedAuth);

            // Set the request method and headers for the WIQL query
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // Define the WIQL query to search for work items with the specified title
            String wiqlQuery = "{\"query\": \"Select [System.Id] From WorkItems Where [System.Title] = '" + title + "'\"}";

            // Send the WIQL query to the server
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = wiqlQuery.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Get the response code
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                // Parse the response to check if any work items were found
                String responseBody = new String(conn.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
                JSONObject responseJson = new JSONObject(responseBody);
                JSONArray workItems = responseJson.optJSONArray("workItems");

                // Return true if any work items were found with the same title
                return workItems != null && workItems.length() > 0;
            } else {
                System.out.println("Failed to search for existing issues. Response Code: " + responseCode);
            }

            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Default to false if an error occurs
    }
}



