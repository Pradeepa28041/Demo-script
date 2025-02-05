package common;

import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DbHelper {

	private static int iterations = 10;

	// To get data from postgresql or redshift db and store in list
	public static List getData(String queryParam) {
		List resultList = null;
		String[] dbDetails = queryParam.split("_");
		if (dbDetails.length > 1) {
			if (dbDetails[0].toLowerCase().contains("RedShift".toLowerCase())) {
				RedShiftUtil redShiftUtil = new RedShiftUtil();
				resultList = redShiftUtil.getData(queryParam);
			} else if (dbDetails[0].toLowerCase().contains("PostgreSql".toLowerCase())) {
				PostgreSQLUtil postgreSQLUtil = new PostgreSQLUtil();
				resultList = postgreSQLUtil.getData(queryParam);
			}
		}
		return resultList;
	}

	public static String queryCopiedText(String query) {
		if (query.contains("@copied_text")) {
		for (int i = 1; i <= iterations; i++) {
			String value = "@copied_text" + i;
			if (query.contains(value)) {
				query = query.replace(value, CommonUtil.getCopiedCountText(Integer.toString(i)));
			}
		}
		if (query.contains("@copied_text")) {
			query = query.replace("@copied_text", CommonUtil.getCopiedText());
		}
		if (query.contains("N/A")) {
			query = query.replace("'N/A'", "null");
		}
		}
		return query;
	}

	public static String alphaNumeric32CopiedText(String query) {
		if (query.contains("@alphaNumeric32CopiedText")) {
		for (int i = 1; i <= iterations; i++) {
			String value = "@alphaNumeric32CopiedText" + i;
			if (query.contains(value)) {
				query = query.replace(value, CommonUtil.getAlphaNum32CopiedCountText(Integer.toString(i)));
			}
		}
		if (query.contains("@alphaNumeric32CopiedText")) {
			query = query.replace("@alphaNumeric32CopiedText", CommonUtil.getAlphaNum32CopiedText());
		}
		if (query.contains("N/A")) {
			query = query.replace("'N/A'", "null");
		}
		}
		return query;
	}
	public static String alphaNumeric64CopiedText(String query) {
		if (query.contains("@alphaNumeric64CopiedText")) {
		for (int i = 1; i <= iterations; i++) {
			String value = "@alphaNumeric64CopiedText" + i;
			if (query.contains(value)) {
				query = query.replace(value, CommonUtil.getAlphaNum64CopiedCountText(Integer.toString(i)));
			}
		}
		if (query.contains("@alphaNumeric64CopiedText")) {
			query = query.replace("@alphaNumeric64CopiedText", CommonUtil.getAlphaNum64CopiedText());
		}
		if (query.contains("N/A")) {
			query = query.replace("'N/A'", "null");
		}
		}
		return query;
	}

	
	public static String globalRandomText(String query) {	
		
	if (query.contains("@globalRanodmCopiedText")) {
		for (int i = 1; i <= iterations; i++) {
			String value = "@globalRanodmCopiedText" + i;
			if (query.contains(value)) {
				query = query.replace(value, CommonUtil.getGlobalRandomText(Integer.toString(i)));
			}
		}
		if (query.contains("@globalRanodmCopiedText")) {
			query = query.replace("@globalRanodmCopiedText", CommonUtil.getGlobalRandomText("1"));
		}
	}
		return query;
	}

	public static String replaceGlobalText(String query) {
		
     if (query.contains("@global_text")) {
		for (int i = 1; i <= iterations; i++) {
			String value = "@global_text" + i;
			if (query.contains(value)) {
				query = query.replace(value, CommonUtil.getGlobalText(Integer.toString(i)));
			}
		}
		}
		return query;
	}

	public static String textRandomCopiedText(String query) {
		if (query.contains("@verifyRandomCopiedText")) 			
	 {
			for (int i = 1; i <= iterations; i++) {
				String value = "@verifyRandomCopiedText" + i;
				if (query.contains(value)) {
					query = query.replace(value, CommonUtil.getRandomCopiedCountText(Integer.toString(i)));
				}
			}
		
		if (query.contains("@verifyRandomCopiedText")) 	
	 {
		 query = query.replace("@verifyRandomCopiedText", CommonUtil.getCopiedRandomText());
	 }
	 }
		return query;
	}

	public static String textRandomCopiedNumber(String query) {
		if (query.contains("@verifyRandomCopiedNumber")) {
			for (int i = 1; i <= iterations; i++) {
				String value = "@verifyRandomCopiedNumber" + i;
				if (query.contains(value)) {
					query = query.replace(value, CommonUtil.getRandomCopiedCountNumber(Integer.toString(i)));
				}
			}
	
			if (query.contains("@verifyRandomCopiedNumber")) 
		{
			query = query.replace("@verifyRandomCopiedNumber", String.valueOf(CommonUtil.getCopiedRandomNumber()));
		}
			}
		
	
		return query;
	}

}
