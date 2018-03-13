package com.capgemini.ars.util;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import oracle.jdbc.pool.OracleDataSource;

//import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.capgemini.ars.exception.AirlineException;


public class DBUtil {
	private static Connection connection = null;
	private static DBUtil instance = null;
	private static Properties props = null;
	private static OracleDataSource dataSource = null;
	private static Logger myLogger = null;
	// step 1 :logger code
	static {
		PropertyConfigurator
		.configure("resources/log4j.properties");
		myLogger = Logger.getLogger(
				DBUtil.class.getName());
	}

	/*****************************************************************************
	 * - @throws AirlineException - Private Constructor - Desc:Loads the
	 * jdbc.properties file and Driver Class and gets the connection
	 * 
	 * @throws AirlineException
	 ********************************************************************************/
	public DBUtil() throws AirlineException {
		try {
			props = loadProperties();
			myLogger.info("Properties loaded");
			dataSource = prepareDataSource();
			myLogger.info("Datasource Prepared!");
		} catch (IOException e) {
			myLogger.error(" Could not read the database details from properties file : "+e.getMessage());
			throw new AirlineException(
					" Could not read the database details from properties file : "+e.getMessage());
		} catch (SQLException e) {
			myLogger.error(e.getMessage());
			throw new AirlineException(e.getMessage());
		}

	}

	/*****************************************************************
	 * - Method Name:getInstance() - Input Parameters : - Return Type :
	 * DBUtil instance - Throws : AirlineException - Author : suadhika -
	 * Creation Date : 09/10/2017 - Description : Singleton and Thread safe
	 * class
	 *******************************************************************/
	public static DBUtil getInstance() throws AirlineException {
		synchronized (DBUtil.class) {
			if (instance == null) {
				instance = new DBUtil();
			}
		}
		return instance;
	}

	/****************************************************
	 * - Method Name:getConnection() 
	 * - Return Type : Connection object 
	 * - Author : suadhika 
	 * - Creation Date : 09/10/2017 
	 * - Description : Returns connection object
	 ****************************************************/

	public Connection obtainConnection() throws AirlineException {
		try {
			connection = dataSource.getConnection();
			if(connection!=null)
				myLogger.info("Connection Obtained!");
			else
				myLogger.error("Connection failed!!");
		} catch (SQLException e) {
			myLogger.error("Connection Failed!");
			throw new AirlineException(" Database connection problem : "+e.getMessage());
		}
		return connection;
	}

	/****************************************************
	 * - Method Name:loadProperties() 
	 * - Return Type : Properties object 
	 * - Author : suadhika 
	 * - Creation Date : 09/10/2017 
	 * - Description : Returns Properties object
	 ****************************************************/
	private Properties loadProperties() throws IOException {

		if (props == null) {
			Properties newProps = new Properties();
			String fileName = "resources/jdbc.properties";

			InputStream inputStream = new FileInputStream(fileName);
			newProps.load(inputStream);

			inputStream.close();

			return newProps;
		} else {
			return props;
		}
	}

	/****************************************************
	 * - Method Name:prepareDataSource() 
	 * - Return Type : OracleDataSource object  
	 * - Author : suadhika 
	 * - Creation Date : 09/10/2017 
	 * - Description : Returns OracleDataSource object
	 ****************************************************/
	private OracleDataSource prepareDataSource() throws SQLException {

		if (dataSource == null) {
			if (props != null) {
				String connectionURL = props.getProperty("url");
				String username = props.getProperty("username");
				String password = props.getProperty("password");

				dataSource = new OracleDataSource();
				dataSource.setURL(connectionURL);
				dataSource.setUser(username);
				dataSource.setPassword(password);
			}
		}
		return dataSource;
	}
	
}
