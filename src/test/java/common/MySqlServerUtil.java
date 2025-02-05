package common;

import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;

public class MySqlServerUtil {

	private Connection conn = null;
	private ResultSet results;
	static String dbsqlURLdb = "***jdbc cluster connection string ****";
	static String dbsqlMasterUsername = "***master user name***";
	static String dbsqlMasterUserPassword = "***master user password***";
	private static String path = System.getProperty("user.dir");
	static String query1 = null;

	public MySqlServerUtil() {
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public void createConnection() {
		try {
			System.out.println("Entering getdata method ");
			dbsqlURLdb = CommonUtil.GetXMLData(
					Paths.get(path.toString(), "src", "test", "java", "DBSettings.xml").toString(), "MySql_DbURL");
			dbsqlMasterUsername = CommonUtil.GetXMLData(
					Paths.get(path.toString(), "src", "test", "java", "DBSettings.xml").toString(), "MySql_DbUsername");
			dbsqlMasterUserPassword = CommonUtil.GetXMLData(
					Paths.get(path.toString(), "src", "test", "java", "DBSettings.xml").toString(), "MySql_DbPassword");
			System.out.println("DB details : " + dbsqlURLdb + " username " + dbsqlMasterUsername + " password "
					+ dbsqlMasterUserPassword);
		} catch (Exception ex) {
			throw new CustomException("DBSettings.xml file does not exist");
		}
		try {
			if (this.conn == null) {
				setConnection();
			} else {
				System.out.println("Already connected to DB");
			}
		} catch (Exception e1) {
			System.out.println(" ERROR MESSAGE " + e1.getMessage());
			e1.printStackTrace();
			System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
		}

	}

	public void setConnection() {
		String url = CommonUtil.GetXMLData(
				Paths.get(path.toString(), "src", "test", "java", "DBSettings.xml").toString(), "MySql_DbURL");
		String user = CommonUtil.GetXMLData(
				Paths.get(path.toString(), "src", "test", "java", "DBSettings.xml").toString(), "MySql_DbUsername");
		String password = CommonUtil.GetXMLData(
				Paths.get(path.toString(), "src", "test", "java", "DBSettings.xml").toString(), "MySql_DbPassword");
		System.out.println("DB details : " + url + " username " + user + " password " + password);

		Properties props = new Properties();
		props.setProperty("user", user.trim());
		props.setProperty("password", password.trim());
		props.setProperty("ssl", "true");
		// props.setProperty("trustServerCertificate","false");
		try {
			System.out.println("Before MySqlDB is not connected");
			setConn(DriverManager.getConnection(url.trim(), props));
			System.out.println("After MySqlDB is connected");
			this.conn.setAutoCommit(true);
			if (conn != null) {
				System.out.println("Connected to the MySql database!");
			} else {
				System.out.println("Failed to make connection!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		} catch (Exception exc2) {
			exc2.printStackTrace();
			System.err.println(exc2.getClass().getName() + ": " + exc2.getMessage());
		}
	}

	public Connection getConnection() {
		return this.conn;
	}

	public void closeConnection() {
		try {
			this.conn.close();
		} catch (SQLException exc) {
			System.out.println(exc.getMessage());
			exc.printStackTrace();
			System.err.println(exc.getClass().getName() + ": " + exc.getMessage());
		} catch (Exception e2) {
			e2.printStackTrace();
			System.err.println(e2.getClass().getName() + ": " + e2.getMessage());
		}
	}

	public MySqlServerUtil(Connection conn) {
		this.conn = conn;
	}

	public void update(String query1) {
		int executeUpdteStatus = 0;
		boolean connStatus = true;
		try {
			// query1 = CommonUtil.GetXMLData(
			// Paths.get(path.toString(), "src", "test", "java",
			// "DBSettings.xml").toString(), queryParam);
			System.out.println("Query value " + query1);
			createConnection();
			try {
				connStatus = this.conn.isClosed();
			} catch (SQLException e) {
			}
			if (!connStatus) {
				// PreparedStatement prepareStatement=this.conn.prepareStatement(query.trim());
				// executeUpdteStatus = prepareStatement.executeUpdate();
				Statement statement = this.conn.createStatement();
				executeUpdteStatus = statement.executeUpdate(query1.trim());
				System.out.println("Query is Updated in DB ");

			} else {
				query1 = CommonUtil.GetXMLData(
						Paths.get(path.toString(), "src", "test", "java", "DBSettings.xml").toString(), query1);
				System.out.println("Query value " + query1);

				setConnection();
				PreparedStatement prepareStatement = this.conn.prepareStatement(query1.trim());
				executeUpdteStatus = prepareStatement.executeUpdate();
				System.out.println(" DB update row count :" + executeUpdteStatus);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new CustomException(e.getMessage(), e);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new CustomException(e.getMessage(), e);
			// e.printStackTrace();
			// return false;
		} finally {
			if (this.conn != null) {
				closeConnection();
			}

		}

	}

	public void select(String query) {
		try {
			String sqlQuery = CommonUtil
					.GetXMLData(Paths.get(path.toString(), "src", "test", "java", "DBSettings.xml").toString(), query);
			getConnection();
			this.results = this.conn.prepareStatement(sqlQuery).executeQuery();
			List abd = CommonUtil.resultSetToArrayList(results);
			System.out.println(abd.size());
			for (int i = 0; i < abd.size(); i++) {
				HashMap row = (HashMap) abd.get(i);
				for (Object mapVal : row.values())
					System.out.println(mapVal.toString());
			}
		} catch (SQLException e1) {
		} finally {
			if (this.conn != null) {
				closeConnection();
			}
		}
	}

	public List getdetails(String query) {
		List resultList = null;
		try {
			System.out.println("Before  setConnection ");
			setConnection();
			System.out.println("After  setConnection ");
			this.results = this.conn.createStatement().executeQuery(query);
			// this.results = this.conn.prepareStatement(query).executeQuery();
			resultList = CommonUtil.resultSetToArrayList(results);

			System.out.println("DB query result size : " + resultList.size());
		} catch (SQLException excep) {
			System.out.println(" ERROR MESSAGE " + excep.getMessage());
			excep.printStackTrace();
			System.err.println(excep.getClass().getName() + ": " + excep.getMessage());
		} catch (Exception exc) {
			System.out.println(" ERROR MESSAGE " + exc.getMessage());
			exc.printStackTrace();
			System.err.println(exc.getClass().getName() + ": " + exc.getMessage());
		} finally {
			if (this.conn != null) {
				closeConnection();
			}
		}
		return resultList;
	}

	public List getData(String query2) {
		List resultList = null;
		try {
			System.out.println("Entering getdata method.. ");
			System.out.println("Query value is " + query2);
		} catch (Exception exc) {
			throw new CustomException("DBSettings.xml file does not exist");
		}

		try {
			System.out.println("Before  setConnection... ");
			setConnection();
			System.out.println("After  setConnection... ");
			this.results = this.conn.createStatement().executeQuery(query2);
			// this.results = this.conn.prepareStatement(query).executeQuery();
			resultList = CommonUtil.resultSetToArrayList(results);

			System.out.println("DB query result size : " + resultList.size());

		} catch (SQLException e) {
			System.out.println(" ERROR MESSAGE.. " + e.getMessage());
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		} catch (Exception e) {
			System.out.println(" ERROR MESSAGE.. " + e.getMessage());
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		} finally {
			if (this.conn != null) {
				closeConnection();
			}
		}
		return resultList;
	}

	public String getSingleData(String query2) {
		// System.out.println("getSingleData method -------------------------"+query2);
		List resultList = null;
		try {

			System.out.println("Entering getdata method ");
			// query1 = CommonUtil.GetXMLData(
			// Paths.get(path.toString(), "src", "test", "java",
			// "DBSettings.xml").toString(), queryParam);
			System.out.println("Query value " + query2);

		} catch (Exception ex) {
			throw new CustomException("DBSettings.xml file does not exist");
		}

		try {
			System.out.println("Before  setConnection ");
			setConnection();
			System.out.println("After  setConnection ");
			// this.results = this.conn.createStatement().executeQuery(query2);

			// this.results = this.conn.prepareStatement(query).executeQuery();
			resultList = getData(query2);
			System.out.println("getSingleData output -------------------------" + resultList);
			System.out.println("DB query result size : " + resultList);
			for (int i = 0; i < resultList.size(); i++) {
				HashMap rows = (HashMap) resultList.get(i);
				System.out.println("row value is :" + rows);
				for (Object mapVal : rows.values()) {
					// System.out.println("mapvalue should be :"+mapVal);
					ExtentCucumberAdapter.addTestStepLog("DB Value copied : " + mapVal);
					return mapVal.toString();
				}
			}
		} catch (Exception ex) {
			System.out.println(" ERROR MESSAGE -  " + ex.getMessage());
			ex.printStackTrace();
			System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
		} finally {
			if (this.conn != null) {
				closeConnection();
			}
		}
		return "";
	}

	public boolean verifyDbData(String Paramquery) throws Exception {

		MySqlServerUtil sql = new MySqlServerUtil();
		// System.out.println("verifyDbdata param
		// query--------------------------->>"+Paramquery);
		// String commandParam = "select Version from
		// rtlspf_algoshack.dbo.NotificationBatch where Id=2449706--Version--1";
		String query1 = null;
		// String columnNames=null;
		String compareValues = null;
		String[] queryDetails = Paramquery.split("--");
		boolean status = false;
		if (queryDetails.length == 2) {
			query1 = queryDetails[0];
			compareValues = queryDetails[1];
			compareValues = compareValues.replaceAll("\\[", "").replaceAll("\\]", "");
		} else {
			// System.out.println("Invalid param------------------------");
			ExtentCucumberAdapter.addTestStepLog("Invalid parameters");
			throw new Exception("Invalid parameters");
		}

		try {

			System.out.println("compareValue Should be : " + compareValues);
			List dataList = getData(query1);
			System.out.println("DB output : " + dataList);
			for (int i = 0; i < dataList.size(); i++) {
				HashMap row = (HashMap) dataList.get(i);
				System.out.println("row value is :" + row);
				for (Object mapVal : row.values()) {
					// System.out.println("mapvalue should be :"+mapVal);
					if (mapVal == null) {
						ExtentCucumberAdapter.addTestStepLog("DB Output. : Null");
						// System.out.println("Mapvalue is null :");
					} else if (mapVal.toString().equals("null")) {
						// System.out.println("Mapvalue is String null :");
						ExtentCucumberAdapter.addTestStepLog("DB Output. : Null");
					} else if (mapVal.toString().contains(compareValues)) {
						ExtentCucumberAdapter.addTestStepLog("DB Output. : " + dataList + " , Compare Value : " + compareValues);
						// System.out.println("Compare value is
						// :"+mapVal.toString().contains(compareValue));
						status = true;
						return true;
					}
				}
			}
			return status;
		}

		catch (Exception exc1) {			
			// System.out.println(" ERROR : " + exc.getMessage());
			ExtentCucumberAdapter.addTestStepLog("Error : " + exc1);
			exc1.printStackTrace();
			return false;
		} finally {
			try {
				getConn().close();
			} catch (SQLException ex2) {

			}
		}
	}

	public static boolean validateResultSetRecords(ResultSet rs, String compareValue, int columnIndex)
			throws SQLException {
		Object columnObject = rs.getObject(columnIndex);
		String columnValue = null;
		if (columnObject instanceof Integer) {
			columnValue = String.valueOf((int) columnObject);

		} else if (columnObject instanceof Boolean) {
			columnValue = String.valueOf((boolean) columnObject);

		} else if (columnObject instanceof Float) {
			columnValue = String.valueOf((Float) columnObject);

		} else if (columnObject instanceof Double) {
			columnValue = String.valueOf((Double) columnObject);

		} else if (columnObject instanceof Character) {
			columnValue = String.valueOf((Character) columnObject);

		} else if (columnObject instanceof String) {
			columnValue = (String) columnObject;

		}
		System.out.println("result value " + columnValue);

		return compareValue.equalsIgnoreCase(columnValue);
	}
}
