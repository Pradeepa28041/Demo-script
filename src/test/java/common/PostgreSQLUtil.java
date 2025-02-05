package common;

import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class PostgreSQLUtil {

	private Connection conn;
	private ResultSet results;
	static String dbURL = "***jdbc cluster connection string ****";
    static String MasterUsername = "***master user name***";
    static String MasterUserPassword = "***master user password***";  
    private static String path = System.getProperty("user.dir");
    static String query=null;
    
    public PostgreSQLUtil() {		
	}
    
	public void setConnection(String url, String user, String password) {
		//String format = String.format("jdbc:postgresql://localhost:5433/%s", database);
		Properties props = new Properties();
		props.put("user", user);
		props.put("password", password);
		try {
			this.conn = DriverManager.getConnection(url, props);
			this.conn.setAutoCommit(true);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public Connection getConnection() {
		return this.conn;
	}

	public void closeConnection() {
		try {
			this.conn.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public PostgreSQLUtil(Connection conn) {
		this.conn = conn;
	}

	public void update(String query) {
		try {
			this.conn.prepareStatement(query).executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void select(String query) {
		try {
			this.results = this.conn.prepareStatement(query).executeQuery();
			List abc = CommonUtil.resultSetToArrayList(results);

			System.out.println(abc.size());
			for (int i = 0; i < abc.size(); i++) {

				HashMap row = (HashMap) abc.get(i);

				for (Object mapVal : row.values())
					System.out.println(mapVal.toString());
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	
	
	public List getData(String queryParam)
	{
		List resultList=null;
		 try
	        {
	        query=	CommonUtil.GetXMLData(Paths.get(path.toString(), "src", "test", "java", "DBSettings.xml").toString(),queryParam);
	        System.out.println(query);
	        dbURL = CommonUtil.GetXMLData(Paths.get(path.toString(), "src", "test", "java", "DBSettings.xml").toString(),"PostgreSql_DbURL");
	        MasterUsername = CommonUtil.GetXMLData(Paths.get(path.toString(), "src", "test", "java", "DBSettings.xml").toString(),"PostgreSql_DbUsername");
	        MasterUserPassword = CommonUtil.GetXMLData(Paths.get(path.toString(), "src", "test", "java", "DBSettings.xml").toString(),"PostgreSql_DbPassword");
	        }
	        catch(Exception ex)
	        {
	        	throw new CustomException("DBSettings.xml file does not exist");
	        }

	    try {
	    	setConnection(dbURL, MasterUsername, MasterUserPassword);
			this.results = this.conn.prepareStatement(query).executeQuery();
			resultList =CommonUtil.resultSetToArrayList(results);

//			System.out.println(abc.size());
//			for (int i = 0; i < abc.size(); i++) {
//
//				HashMap row = (HashMap) abc.get(i);
//
//				for (Object mapVal : row.values())
//					System.out.println(mapVal.toString());
//			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	    finally{
	    	closeConnection();
	    }
	    
	   return resultList;	   
	}
	
}
