/**
 * 
 */
package com.capgemini.ars.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.capgemini.ars.bean.Users;
import com.capgemini.ars.exception.AirlineException;
import com.capgemini.ars.util.DBUtil;

/**
 * @author suadhika
 *
 */
public class LoginDao {
	public static Connection connection;
	public static Logger myLogger;
	
	static{
		PropertyConfigurator.configure("resources/log4j.properties");
		myLogger = Logger.getLogger(LoginDao.class.getName());
	}
	public LoginDao() {
		try {
			connection = new DBUtil().obtainConnection();
			myLogger.info("Connection Obtained ... at DAO");
		} catch (AirlineException e) {
			myLogger.error("ERROR : " + e);
			System.err.println(e.getMessage());
		}
	}
	public String logInValidation(String username,String password) throws AirlineException
	{
		String role="invalid";
		ResultSet resultSet = null;
		String sql = "SELECT role FROM users WHERE email=? AND password=?";
		PreparedStatement preparedStatement = null;
		try {
			// obtain PreparedStatement
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, username);// setting the date
			preparedStatement.setString(2, password);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()){
				role=resultSet.getString(1);
			}
		} catch (SQLException e) {
			myLogger.error("User not found , error occured : "
					+ e.getMessage());
			throw new AirlineException(e.getMessage());
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} catch (SQLException e) {
				myLogger.error("User not found , error occured :"
						+ e.getMessage());
				throw new AirlineException(e.getMessage());
			}
		}
		return role;
		
	}
	public int register(Users user) throws AirlineException{
		myLogger.info("register() invoked in AdminDaoImpl!!");
		
		String sql = "INSERT INTO Users VALUES(?,?,?,?,?,?)";
		PreparedStatement preparedStatement = null;
		int result=0;
		try {// obtain preparedStatement
			preparedStatement = connection.prepareStatement(sql);
			connection.setAutoCommit(false);// transaction starts

			// set the values for place holders
			preparedStatement.setString(1, user.getFirstName());
			preparedStatement.setString(2, user.getLastName());
			preparedStatement.setString(3, user.getEmail());
			preparedStatement.setString(4, user.getPassword());
			preparedStatement.setString(5, user.getRole());
			preparedStatement.setDouble(6, user.getMobileNo());
			// execute DML query
			result = preparedStatement.executeUpdate();

			connection.commit();// if insert successful the commit the
								// transaction
		} catch (SQLException e) {
			myLogger.error("ERRROR :  " + "Inserting user details failed  "
					+ e.getMessage());
			throw new AirlineException("ERRROR :  Inserting user details failed  "
					+ e.getMessage());
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
				// if exception then transaction will rollback
				connection.rollback();
			} catch (SQLException e) {
				myLogger.error("ERRROR :  " + "Inserting user details failed "
						+ e.getMessage());
				throw new AirlineException(
						"ERRROR : Inserting user details failed " + e.getMessage());
			}
		}
		return result;
		
	}
	public boolean validUserName(String userName) throws AirlineException {
		boolean flag=true;
		String sql="SELECT * FROM users WHERE email=?";
		PreparedStatement preparedStatement=null;
		try {
			preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setString(1, userName);
			ResultSet rs=preparedStatement.executeQuery();
			if(rs.next())
				flag=false;
		} catch (SQLException e) {
			myLogger.error("ERRROR :  " + "Validating email id failed "
					+ e.getMessage());
			throw new AirlineException(
					"ERRROR : Validating email id failed " + e.getMessage());
		}
		return flag;
	}
	public String getName(String email) throws AirlineException {
		String sql="SELECT first_name,last_name FROM users WHERE email=?";
		String name="";
		PreparedStatement preparedStatement=null;
		try {
			preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setString(1, email);
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next())
				name=resultSet.getString(1)+" "+resultSet.getString(2);
		} catch (SQLException e) {
			myLogger.error("ERRROR :  " + "Getting first name failed "
					+ e.getMessage());
			throw new AirlineException(
					"ERRROR : Getting first name failed " + e.getMessage());
		}
		return name;
	}

}
