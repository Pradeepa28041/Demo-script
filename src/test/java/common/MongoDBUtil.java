package common;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bson.Document;


import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;

public class MongoDBUtil {

	static String dbURL = "***jdbc cluster connection string ****";
	static String MasterUsername = "***master user name***";
	static String MasterUserPassword = "***master user password***";
	static String DBname = "***DB Name***";
	private static String path = System.getProperty("user.dir");
	static String query = null;

	public static MongoDatabase createConnection() {
		try {
			dbURL = CommonUtil.GetXMLData(
					Paths.get(path.toString(), "src", "test", "java", "DBSettings.xml").toString(), "MongoDB_DbURL");
			DBname = CommonUtil.GetXMLData(
					Paths.get(path.toString(), "src", "test", "java", "DBSettings.xml").toString(), "MongoDB_DbName");
		} catch (Exception ex) {
			throw new CustomException("DBSettings.xml file does not exist");
		}

		MongoClient client = new MongoClient(new MongoClientURI(dbURL));
		/**** Get database ****/
		// if database doesn't exists, MongoDB will create it for you
		MongoDatabase mydatabase = client.getDatabase(DBname);
		return mydatabase;
	}

	public static boolean VerifyDataInMongoDB(String queryParam, String value) {
		boolean isVerified = false;
		query = queryParam;
		/**** Get database ****/
		// if database doesn't exists, MongoDB will create it for you
		MongoDatabase mydatabase = createConnection();

		/**** Get collection / table from DB ****/
		// if collection doesn't exists, MongoDB will create it for you
		FindIterable<Document> mydatabaserecords = mydatabase.getCollection(query).find();
		MongoCursor<Document> iterator = mydatabaserecords.iterator();
		while (iterator.hasNext()) {
			Document doc = iterator.next();
			if (doc.containsValue(value)) {
				isVerified = true;
				System.out.println("MongoDB collection: " + doc.toString());
				ExtentCucumberAdapter.addTestStepLog("Value verified in MongoDB is: " + value);
				ExtentCucumberAdapter.addTestStepLog("MongoDB collection: " + doc.toString());
			}
		}
		return isVerified;
	}

	public static Document GetMongoDBData(String collection, BasicDBObject dbObject) {
		String collectioName = collection;
		MongoDatabase mydatabase = createConnection();
		/**** Get collection / table from DB ****/
		Document document = mydatabase.getCollection(collectioName).find(dbObject).sort(Sorts.descending("timestamp"))
				.first();

		System.out.println("MongoDB collection: " + document.toString());
		return document;
	}

	public static boolean CompareDbValues(String DBquery) {

		MongoDatabase mydatabase = createConnection();
		String[] queryValue = DBquery.split("--");
		String query = DbHelper.queryCopiedText(queryValue[0]);
		String[] dbDeatails = query.split("@@");
		boolean result = false;
		Object object = com.mongodb.util.JSON.parse(dbDeatails[1]);
		com.mongodb.BasicDBObject dbobject = (com.mongodb.BasicDBObject) object;

		/**** Get collection / table from DB ****/
		Document document = mydatabase.getCollection(dbDeatails[0]).find(dbobject).sort(Sorts.descending("timestamp"))
				.first();
		String compareValue = DbHelper.queryCopiedText(queryValue[1]).toUpperCase();
		if (document == null) {
			if (compareValue.equals("N/A") || compareValue.equals("null")) {
				return true;
			} else {
				return false;
			}
		} else {
			System.out.println("MongoDB collection: " + document.toString());
			if (dbDeatails.length == 3) {
				String res = "";
				String[] outputDataFields = dbDeatails[2].split(",");
				for (int i = 0; i < outputDataFields.length; i++) {
					try {
						res = document.get(outputDataFields[i]).toString().toUpperCase();
					} catch (Exception ex) {
						if (compareValue.equals("N/A") || compareValue.equals("null")) {
							return true;
						} else {
							return false;
						}
					}
					if (res == null) {
						if (compareValue.equals("N/A") || compareValue.equals("null")) {
							return true;
						} else {
							return false;
						}
					}
					if (res.contains(compareValue)) {
						result = true;
					}
					if (queryValue.length == 3) {
						if (res.contains(compareValue)) {
							result = false;
						} else {
							result = true;
						}
					}
				}
			} else {
				if (document.containsValue(queryValue[1])) {
					result = true;
				} else {
					result = false;
				}
			}
		}
		return result;
	}

	public static boolean compareCompleteList(String collection, String field) {
		String collectioName = collection;
		boolean verifyRes = false;
		MongoDatabase mydatabase = createConnection();

		List<String> allData = new ArrayList<>();
		/**** Get collection / table from DB ****/
		FindIterable<Document> mydatabaserecords = mydatabase.getCollection(collectioName).find();
		MongoCursor<Document> iterator = mydatabaserecords.iterator();
		while (iterator.hasNext()) {
			Document doc = iterator.next();
			System.out.println("MongoDB collection: " + doc.getString(field));
			allData.add(doc.getString(field).toString());
		}
		List<String> mainList = new ArrayList<>();
		Set<String> uniqueData = new HashSet<String>(allData);
		int count = 0;
		for (String strNumber : uniqueData) {
			mainList.add(strNumber);
			System.out.println(strNumber);
		}
		List<String> copiedListText = CommonUtil.getCopiedList();
		Collections.sort(mainList);
		Collections.sort(copiedListText);
		verifyRes = mainList.equals(copiedListText);
		return verifyRes;
	}

	public static boolean GetMongoDBDatanotPResent(String collection, BasicDBObject dbObject) {
		String collectioName = collection;
		MongoDatabase mydatabase = createConnection();

		/**** Get collection / table from DB ****/
		Document document = mydatabase.getCollection(collectioName).find(dbObject).projection(Projections.excludeId())
				.first();

		if (document == null) {
			return true;

		} else {
			return false;
		}
	}

	public static String getQuery(String queryParam) {

		try {
			query = CommonUtil.GetXMLData(
					Paths.get(path.toString(), "src", "test", "java", "DBSettings.xml").toString(), queryParam);
			return query;
		} catch (Exception ex) {
			throw new CustomException("DBSettings.xml file does not exist");
		}
	}

	public static boolean CompareDbValues1(String DBquery) {

//		String collection = "user";
//		String collectioName=collection;
		try {
			MongoDatabase mydb = createConnection();

			String[] queryVal = DBquery.split("--");
			String query = DbHelper.queryCopiedText(queryVal[0]);
			String[] dbDetails = query.split("@@");
			boolean result = false;
			Object object = com.mongodb.util.JSON.parse(dbDetails[1]);
			com.mongodb.BasicDBObject dbobject = (com.mongodb.BasicDBObject) object;

			FindIterable<Document> cursor = mydb.getCollection(dbDetails[0]/* collectioName */)
					.find(/* query1 */dbobject);
			String value = "";
			for (Document doc : cursor) {

				ExtentCucumberAdapter.addTestStepLog("MongoDB collection: " + doc.toString());
				value = (String) doc.get(dbDetails[2]);
				if (value != null)
					break;
			}
			String compareValue = DbHelper.queryCopiedText(queryVal[1]).toUpperCase();
			if (compareValue.equalsIgnoreCase(value)) {
				ExtentCucumberAdapter.addTestStepLog("Value verified in MongoDB is: " + value);
				return true;
			}
		} catch (Exception e) {
			ExtentCucumberAdapter.addTestStepLog("Error : " + e);
			return false;
		}
		return false;

//       System.out.println("MongoDB collection: " + document.toString());
	}
}
