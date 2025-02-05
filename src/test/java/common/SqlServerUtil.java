package common;

import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.List;
import java.sql.Statement;
import java.util.Properties;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;

public class SqlServerUtil {

	private Connection connection = null;
	private ResultSet results;
	static String dbURLdb = "***jdbc cluster connection string ****";
	static String dbMasterUsername = "***master user name***";
	static String dbMasterUserPassword = "***master user password***";
	private static String path = System.getProperty("user.dir");
	static String query1 = null;

	public SqlServerUtil() {

	}

	public Connection getConn() {
		return connection;
	}

	public void setConn(Connection conn) {
		this.connection = conn;
	}

	public void createConnection() {
		try {
			System.out.println("Entering getdata method... ");
			dbURLdb = CommonUtil.GetXMLData(
					Paths.get(path.toString(), "src", "test", "java", "DBSettings.xml").toString(), "Sql_DbURL");
			dbMasterUsername = CommonUtil.GetXMLData(
					Paths.get(path.toString(), "src", "test", "java", "DBSettings.xml").toString(),
					"Sql_DbUsername");
			dbMasterUserPassword = CommonUtil.GetXMLData(
					Paths.get(path.toString(), "src", "test", "java", "DBSettings.xml").toString(),
					"Sql_DbPassword");
			System.out.println(
					"DB details. : " + dbURLdb + " username " + dbMasterUsername + " password " + dbMasterUserPassword);
		} catch (Exception ex) {
			throw new CustomException("DBSettings.xml file does not exist");
		}

		try {
			if (this.connection == null) {
				setConnection();
			} else {
				System.out.println("Already connected to DB..");
			}
		} catch (Exception ex) {
			System.out.println(" ERROR MESSAGE " + ex.getMessage());
			ex.printStackTrace();
			System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
		}
	}

	public void setConnection() {
		String sqlurl = CommonUtil.GetXMLData(
				Paths.get(path.toString(), "src", "test", "java", "DBSettings.xml").toString(), "Sql_DbURL");
		String sqluser = CommonUtil.GetXMLData(
				Paths.get(path.toString(), "src", "test", "java", "DBSettings.xml").toString(),
				"Sql_DbUsername");
		String sqlpassword = CommonUtil.GetXMLData(
				Paths.get(path.toString(), "src", "test", "java", "DBSettings.xml").toString(),
				"Sql_DbPassword");
		System.out.println("DB details : " + sqlurl + " username " + sqluser + " password " + sqlpassword);

		Properties prop = new Properties();
		prop.setProperty("user", sqluser.trim());
		prop.setProperty("password", sqlpassword.trim());
		prop.setProperty("ssl", "true");
		// props.setProperty("trustServerCertificate","false");
		try {

			try {

				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			} catch (ClassNotFoundException ex) {
				ex.printStackTrace();
			}
			System.out.println("Before DB is not connected....");
			setConn(DriverManager.getConnection(sqlurl.trim(), prop));
			System.out.println("After DB is connected..");

			this.connection.setAutoCommit(true);

			if (connection != null) {
				System.out.println("Connected to the database!!!");
			} else {
				System.out.println("Failed to make connection!!");
			}
		} catch (SQLException exc1) {
			exc1.printStackTrace();
			System.err.println(exc1.getClass().getName() + ": " + exc1.getMessage());
		} catch (Exception ex1) {
			ex1.printStackTrace();
			System.err.println(ex1.getClass().getName() + ": " + ex1.getMessage());
		}
	}

	public Connection getConnection() {
		return this.connection;
	}

	public void closeConnection() {
		try {
			this.connection.close();
		} catch (SQLException exc1) {
			System.out.println(exc1.getMessage());
			exc1.printStackTrace();
			System.err.println(exc1.getClass().getName() + ": " + exc1.getMessage());
		} catch (Exception e1) {
			e1.printStackTrace();
			System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
		}
	}

	public SqlServerUtil(Connection conn) {
		this.connection = conn;
	}

	public void update(String query1) {
		int executeUpdteStatus = 0;
		boolean connStatus = true;
		try {
			System.out.println("Query value " + query1);
			createConnection();
			try {
				connStatus = this.connection.isClosed();
			} catch (SQLException e) {

			}
			if (!connStatus) {
				// PreparedStatement prepareStatement=this.conn.prepareStatement(query.trim());
				// executeUpdteStatus = prepareStatement.executeUpdate();
				Statement statement = this.connection.createStatement();
				executeUpdteStatus = statement.executeUpdate(query1.trim());
				System.out.println("Query is Updated in DB!!! ");
			} else {
				query1 = CommonUtil.GetXMLData(
						Paths.get(path.toString(), "src", "test", "java", "DBSettings.xml").toString(), query1);
				System.out.println("Query value is " + query1);
				setConnection();
				PreparedStatement prepareStatement = this.connection.prepareStatement(query1.trim());
				executeUpdteStatus = prepareStatement.executeUpdate();
				System.out.println(" DB update row count :" + executeUpdteStatus);
			}
		} catch (SQLException e1) {
			System.out.println(e1.getMessage());
			throw new CustomException(e1.getMessage(), e1);
			// e.printStackTrace();
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
			throw new CustomException(e1.getMessage(), e1);
			// e.printStackTrace();
			// return false;
		} finally {
			if (this.connection != null) {
				closeConnection();
			}
		}
	}

	public void select(String query) {
		try {
			String sqlQuerys = CommonUtil
					.GetXMLData(Paths.get(path.toString(), "src", "test", "java", "DBSettings.xml").toString(), query);
			getConnection();
			this.results = this.connection.prepareStatement(sqlQuerys).executeQuery();
			List abc = CommonUtil.resultSetToArrayList(results);

			System.out.println(abc.size());
			for (int i = 0; i < abc.size(); i++) {

				HashMap row = (HashMap) abc.get(i);

				for (Object mapVal : row.values())
					System.out.println(mapVal.toString());
			}

		} catch (SQLException exc) {
		} finally {
			if (this.connection != null) {
				closeConnection();
			}
		}
	}

	public List getData(String query2) {
		List resList = null;
		try {
			System.out.println("Entering getdata method... ");
			System.out.println("Query value " + query2);
		} catch (Exception ex) {
			throw new CustomException("DBSettings.xml file does not exist");
		}

		try {
			System.out.println("Before  set Connection ");
			setConnection();
			System.out.println("After  set Connection ");
			this.results = this.connection.createStatement().executeQuery(query2);
			// this.results = this.conn.prepareStatement(query).executeQuery();
			resList = CommonUtil.resultSetToArrayList(results);

			System.out.println("DB query result size : " + resList.size());

		} catch (SQLException ex) {
			System.out.println(" ERROR MESSAGE " + ex.getMessage());
			ex.printStackTrace();
			System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
		} catch (Exception ex) {
			System.out.println(" ERROR MESSAGE " + ex.getMessage());
			ex.printStackTrace();
			System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
		} finally {
			if (this.connection != null) {
				closeConnection();
			}

		}

		return resList;
	}

	public String getSingleData(String query2) {
		// System.out.println("getSingleData method -------------------------"+query2);
		List resultsList = null;
		try {

			System.out.println("Entering getdata method ");
			System.out.println("Query value " + query2);

		} catch (Exception ex) {
			throw new CustomException("DBSettings.xml file does not exist");
		}
		try {
			System.out.println("Before  setConnection--- ");
			setConnection();
			System.out.println("After  setConnection--- ");
			resultsList = getData(query2);
			System.out.println("getSingleData output -------------------------" + resultsList);
			System.out.println("DB query result size : " + resultsList);
			for (int i = 0; i < resultsList.size(); i++) {
				HashMap Hrow = (HashMap) resultsList.get(i);
				System.out.println("row value is :" + Hrow);
				for (Object mapVal : Hrow.values()) {
					// System.out.println("mapvalue should be :"+mapVal);
					ExtentCucumberAdapter.addTestStepLog("DB Value copied : " + mapVal);
					return mapVal.toString();
				}
			}
		} catch (Exception exc) {
			System.out.println(" ERROR MESSAGE " + exc.getMessage());
			exc.printStackTrace();
			System.err.println(exc.getClass().getName() + ": " + exc.getMessage());
		} finally {
			if (this.connection != null) {
				closeConnection();
			}

		}

		return "";
	}

	public boolean verifyDbData(String Paramquery) throws Exception {

		SqlServerUtil sql = new SqlServerUtil();
		String query = null;
		// String columnName=null;
		String compareValue = null;
		Paramquery=CommonUtil.GetData(Paramquery);
		String[] queryDetails = Paramquery.split("--");
		boolean status = false;

		if (queryDetails.length == 2) {
			query = queryDetails[0];
			compareValue = queryDetails[1];
			compareValue = compareValue.replaceAll("\\[", "").replaceAll("\\]", "");
		} else {
			// System.out.println("Invalid param------------------------");
			ExtentCucumberAdapter.addTestStepLog("Invalid parameter..");
			throw new Exception("Invalid parameter.");
		}

		try {
			System.out.println("compareValue Should be :- " + compareValue);
			List newresultList = getData(query);
			System.out.println("DB output : " + newresultList);
			for (int i = 0; i < newresultList.size(); i++) {
				HashMap row = (HashMap) newresultList.get(i);
				System.out.println("row value is :" + row);
				for (Object mapValue : row.values()) {
					// System.out.println("mapvalue should be :"+mapVal);
					if (mapValue == null) {
						ExtentCucumberAdapter.addTestStepLog("DB Output : Null");
						// System.out.println("Mapvalue is null :");
					} else if (mapValue.toString().equals("null")) {
						// System.out.println("Mapvalue is String null :");
						ExtentCucumberAdapter.addTestStepLog("DB Output : Null");
					} else if (mapValue.toString().contains(compareValue)) {
						ExtentCucumberAdapter.addTestStepLog("DB Output : " + newresultList + " , Compare Value : " + compareValue);
						// System.out.println("Compare value is
						// :"+mapVal.toString().contains(compareValue));
						status = true;
						return true;
					}
				}
			}
			return status;
		} catch (Exception exc1) {
			// System.out.println(" ERROR : " + e.getMessage());
			ExtentCucumberAdapter.addTestStepLog("Error : " + exc1);
			exc1.printStackTrace();
			return false;
		} finally {
			try {
				getConn().close();
			} catch (SQLException e) {
			}
		}
	}

	public static boolean validateResultSetRecords(ResultSet rs, String comparedValue, int columnIndex)
			throws SQLException {
		Object columnsObject = rs.getObject(columnIndex);
		String columnValue = null;
		if (columnsObject instanceof Integer) {
			columnValue = String.valueOf((int) columnsObject);

		} else if (columnsObject instanceof Boolean) {
			columnValue = String.valueOf((boolean) columnsObject);

		} else if (columnsObject instanceof Float) {
			columnValue = String.valueOf((Float) columnsObject);

		} else if (columnsObject instanceof Double) {
			columnValue = String.valueOf((Double) columnsObject);

		} else if (columnsObject instanceof Character) {
			columnValue = String.valueOf((Character) columnsObject);

		} else if (columnsObject instanceof String) {
			columnValue = (String) columnsObject;

		}
		System.out.println("result value " + columnValue);

		return comparedValue.equalsIgnoreCase(columnValue);
	}
}
