package com.ceva.base.common.utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.jolbox.bonecp.BoneCPConfig;

public class DBUtil {
	
	private static Logger logger = Logger.getLogger(DBUtil.class);
	
	private static ResourceBundle rb = null;
	
	static{

		try {
			rb = ResourceBundle.getBundle("resource/dbdetails");
		} catch (Exception e) {
			logger.debug("Exception while loading bundle please check the file.... dbdetails.properties under 'classes/resource' folder.");
		}
		
	}	

	
	public static Connection getDBConnection(){
		
		
		Connection connection=null;
		
		BoneCPConfig config = null;
		InputStream stream = null;
	
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			stream = DBUtil.class
					.getResourceAsStream("/resource/bone-cp-db-config.xml");
			config = new BoneCPConfig(stream, "");
			//jdbcUrl
			config.setUser(new DESEncryption().decrypt(config.getUser()));
			config.setPassword(new DESEncryption().decrypt(config.getPassword()));
		
			
		    connection = DriverManager.getConnection((config.getJdbcUrl()).trim(),config.getUser(),config.getPassword());
		    
		} catch (ClassNotFoundException e) {
			System.out.println("[SPUDBConnector][getCevaDBConnection]Class Not Found Exception  Message"+e.getMessage());
		}catch (SQLException e) {
			System.out.println("[SPUDBConnector][getCevaDBConnection]SQL Exception Message"+e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
		}
	      
		return connection;
	}
	
	
	public static void closeConnection(Connection con) {
	    try {
	      if (con != null) {
	        con.close();
	        con = null;
	      }
	    } catch (Exception e) {
	      logger.error("[DBUtils][closeConnection] e :[" + e.getMessage() + "]");
	    }
	  }
	
	
	public static void main(String[] args) {
		
		//System.out.println(getDBConnection());
		BoneCPConfig config = null;
		InputStream stream = null;
		try{
			stream = DBUtil.class
					.getResourceAsStream("/resource/bone-cp-db-config.xml");
			config = new BoneCPConfig(stream, "");
			//jdbcUrl
			config.setUser(new DESEncryption().decrypt(config.getUser()));
			config.setPassword(new DESEncryption().decrypt(config.getPassword()));
			
			System.out.println((config.getJdbcUrl()).trim());
			System.out.println(config.getUser());
			System.out.println(config.getPassword());
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
