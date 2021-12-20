package com.ceva.base.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;

import com.ceva.unionbank.services.handler.SimpleXMLParser;

public class DBConnector {

	private static Logger logger = Logger.getLogger(DBConnector.class);

	private static BoneCP connectionPool = null;

	static {
		setupDataSource();
	}

	private static void setupDataSource() {

		BoneCPConfig config = null;
		InputStream stream = null;
		try {
			// Loading JDBC Driver into the class.
			 Class.forName("oracle.jdbc.driver.OracleDriver");

			// loader = Thread.currentThread().getContextClassLoader();
			stream = DBConnector.class
					.getResourceAsStream("/resource/bone-cp-db-config.xml");
			//logger.debug("|DBConnector| DB XML Config Loading.");

			// setup the connection pool
			config = new BoneCPConfig(stream, "");
			config.setUser(new DESEncryption().decrypt(config.getUser()));
			config.setPassword(new DESEncryption().decrypt(config.getPassword()));
			
			connectionPool = new BoneCP(config); // setup the connection pool

			setConnectionPool(connectionPool);
			logger.debug(" |DBConnector| DB Properties loaded.");
		} catch (SQLException e) {
			e.printStackTrace();
			logger.debug(" |DBConnector| Getting error message in SQLException :: "
					+ e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(" |DBConnector| Getting error message in Exception :: "
					+ e.getMessage());
		} finally {

			try {
				config = null;
				if (stream != null) {
					stream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void shutdownConnPool() {
		BoneCP connectionPool = null;
		try {
			connectionPool = DBConnector.getConnectionPool();
			logger.info(" |DBConnector| contextDestroyed....");
			if (connectionPool != null) {
				connectionPool.close();
				connectionPool.shutdown(); // this method must be called only
				// once when the application stops.
				// you don't need to call it every time when you get a
				// connection from the Connection Pool
			//	logger.info(" |DBConnector| contextDestroyed.....Connection Pooling shut downed!");
			}
		} catch (Exception e) {
			logger.info(" |DBConnector| connection exception is :: "
					+ e.getMessage());
		}
	}

	public static Connection getConnection() throws SQLException {

		Connection conn = null;
		try {
			conn = getConnectionPool().getConnection();
			// will get a thread-safe connection from the BoneCP connection
			// pool.
			// synchronization of the method will be done inside BoneCP source
			logger.debug("connection is null [" + (conn == null) + "]");

			logger.info(" |DBConnector| Total Created Connections ["	+ getConnectionPool().getTotalCreatedConnections() + "]");
			logger.info(" |DBConnector| Total Free    Connections ["	+ getConnectionPool().getTotalFree() + "]");
			logger.info(" |DBConnector| Total Leased  Connections ["+ getConnectionPool().getTotalLeased() + "]");
			logger.info(" |DBConnector| Total Statements  Executed ["+ getConnectionPool().getStatistics().getStatementsExecuted() + "]");
			logger.info(" |DBConnector| Total Statements  Prepared ["+ getConnectionPool().getStatistics().getStatementsPrepared() + "]");

		} catch (Exception e) {
			conn = null;
		}
		return conn;
	}

	public static BoneCP getConnectionPool() {
		return connectionPool;
	}

	public static void setConnectionPool(BoneCP connectionPool) {
		DBConnector.connectionPool = connectionPool;
	}
	/*DS Connection */
	
	/*private static HashMap<String, String> map=null;
	private static String DSURL="DS-URL";
	private static String DSJNDI="DS-JNDI";
static {
	map = (HashMap<String, String>)SimpleXMLParser.parseXML("/configs/IntegratorInfo.xml");
	System.out.println("Weblogic Integrator Info "+map);
}
	public static Connection getConnection() throws SQLException {

		Connection conn = null;
		try {
			Context ctx = null;
			  Hashtable ht = new Hashtable();
			  ht.put(Context.INITIAL_CONTEXT_FACTORY,
			         "weblogic.jndi.WLInitialContextFactory");
			  ht.put(Context.PROVIDER_URL,
					  map.get(DSURL));
			  ctx = new InitialContext(ht);
			    javax.sql.DataSource ds 
			      = (javax.sql.DataSource) ctx.lookup (map.get(DSJNDI));
			    System.out.println("ds:"+ds);
			    conn = ds.getConnection();
			    System.out.println("conn:"+conn);
			

		} catch (Exception e) {
			conn = null;
			e.printStackTrace();
		}
		return conn;
	}*/

	public static void main(String[] args) throws SQLException {

		Connection connection = DBConnector.getConnection();
		PreparedStatement stmt = connection
				.prepareStatement("select 1+1 from dual");
		ResultSet rs = stmt.executeQuery();

		if (rs.next()) {
			logger.info(rs.getInt(1));
		}
		//connectionPool.close();
		//connectionPool.shutdown();
	}

}
