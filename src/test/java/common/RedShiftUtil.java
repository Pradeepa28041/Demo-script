package common;

import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

public class RedShiftUtil {
	static String dbredshiftURL = "***jdbc cluster connection string ****";
	static String MasterRSUsername = "***master user name***";
	static String MasterRSUserPassword = "***master user password***";
	// static List<String[]> list = null;
	private static String path = System.getProperty("user.dir");
	static String query = null;

	public List getData(String queryParams) {
		Connection conns = null;
		Statement stmt = null;
		List resultsList = null;
		try {
			query = CommonUtil.GetXMLData(
					Paths.get(path.toString(), "src", "test", "java", "DBSettings.xml").toString(), queryParams);
			dbredshiftURL = CommonUtil.GetXMLData(
					Paths.get(path.toString(), "src", "test", "java", "DBSettings.xml").toString(), "RedShift_DbURL");
			MasterRSUsername = CommonUtil.GetXMLData(
					Paths.get(path.toString(), "src", "test", "java", "DBSettings.xml").toString(),
					"RedShift_DbUsername");
			MasterRSUserPassword = CommonUtil.GetXMLData(
					Paths.get(path.toString(), "src", "test", "java", "DBSettings.xml").toString(),
					"RedShift_DbPassword");
		} catch (Exception exc) {
			throw new CustomException("DBSettings.xml file does not exists.."+exc.toString());
		}
		try {
			// Class.forName("com.amazon.redshift.jdbc.Driver");
			Class.forName("com.amazon.redshift.jdbc41.Driver");
			System.out.println("Connecting to database...");
			Properties prop = new Properties();
			// Uncomment the following line if using a keystore.
			prop.setProperty("sslfactory", "com.amazon.redshift.ssl.NonValidatingFactory");
			prop.setProperty("ssl", "true");
			prop.setProperty("user", MasterRSUsername);
			prop.setProperty("password", MasterRSUserPassword);
			conns = DriverManager.getConnection(dbredshiftURL, prop);
			// Try a simple query.
			System.out.println("Listing system tables...");
			stmt = conns.createStatement();
			String sql;
			// sql = "select * from information_schema.tables;";
			ResultSet rs1 = stmt.executeQuery(query);
			resultsList = CommonUtil.resultSetToArrayList(rs1);
			rs1.close();
			stmt.close();
			conns.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			// Finally block
			// to close resources.
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception ex) {
			}
			try {
				if (conns != null)
					conns.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		System.out.println("Finished connectivity test.");
		return resultsList;
	}

	public List getExcelData(String queryParam) {
		Connection conn = null;
		Statement stmt = null;
		List resultList = null;
		try {
			dbredshiftURL = CommonUtil.GetXMLData(
					Paths.get(path.toString(), "src", "test", "java", "DBSettings.xml").toString(), "RedShift_DbURL");
			// MasterRSUsername
			MasterRSUsername = CommonUtil.GetXMLData(
					Paths.get(path.toString(), "src", "test", "java", "DBSettings.xml").toString(),
					"RedShift_DbUsername");
			// MasterRSUserPassword
			MasterRSUserPassword = CommonUtil.GetXMLData(
					Paths.get(path.toString(), "src", "test", "java", "DBSettings.xml").toString(),
					"RedShift_DbPassword");
		} catch (Exception ex) {
			throw new CustomException("DBSettings.xml file does not exist");
		}
		try {			
			// Redshift JDBC 4.1 driver: com.amazon.redshift.jdbc41.Driver
			// Redshift JDBC 4 driver: com.amazon.redshift.jdbc4.Driver
			// Class.forName("com.amazon.redshift.jdbc.Driver");
			Class.forName("com.amazon.redshift.jdbc41.Driver");
			// Open a connection and define properties.
			System.out.println("Connecting to database...");
			Properties props = new Properties();

			// Uncomment the following line if using a keystore.
			props.setProperty("sslfactory", "com.amazon.redshift.ssl.NonValidatingFactory");
			props.setProperty("ssl", "true");
			props.setProperty("user", MasterRSUsername);
			props.setProperty("password", MasterRSUserPassword);
			conn = DriverManager.getConnection(dbredshiftURL, props);

			// Try a simple query.
			System.out.println("Listing system tables...");
			stmt = conn.createStatement();
			String sql;
			// sql = "select * from information_schema.tables;";
			ResultSet rs = stmt.executeQuery(queryParam);
			resultList = CommonUtil.resultSetToArrayList(rs);

			rs.close();
			stmt.close();
			conn.close();

		} catch (Exception ex) {
			// For convenience, handle all errors here.
			ex.printStackTrace();
		} finally {
			// Finally block to close resources.
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception ex) {
			} // nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		System.out.println("Finished connectivity test.");
		return resultList;
	}
}
