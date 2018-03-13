/**
 * 
 */
package com.capgemini.ars.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.capgemini.ars.bean.FlightInformation;
import com.capgemini.ars.exception.AirlineException;
import com.capgemini.ars.util.DBUtil;

/**
 * @author suadhika
 *
 */
public class AdminDaoImpl implements AdminDao {

	// step 1 :declare Connection and Logger variables
		private Connection connection;
		private static Logger myLogger = null;
		// step 2 :static block for logger code
		static {
			PropertyConfigurator.configure("resources/log4j.properties");
			myLogger = Logger.getLogger(AdminDaoImpl.class.getName());
		}

		// step 3 : obtain connection in the constructor
		public AdminDaoImpl() {
			try {
				connection = new DBUtil().obtainConnection();
				myLogger.info("Connection Obtained ... at DAO");
			} catch (AirlineException e) {
				myLogger.error("ERROR : " + e);
				System.err.println(e.getMessage());
			}
		}

		// step 4 : override methods
	/* (non-Javadoc)
	 * @see com.capgemini.ars.dao.IAdminDao#insertFlightDetails(com.capgemini.ars.bean.FlightInformation)
	 */
	@Override
	public int insertFlightDetails(FlightInformation flightInformation)
			throws AirlineException {
		myLogger.info("Insert FlightDetails() invoked in AdminDaoImpl!!");
		
		//Flight_Information: flightno(varchar2(5)), airline (varchar2(10)),
		//dep_city (varchar2(10)), arr_city (varchar2(10)), dep_date (date), 
		//arr_date (date), dep_time, arr_time, FirstSeats (number), FirstSeatFare 
		//(number(m,n)), BussSeats (number), BussSeatsFare (number(m,n))
		
		String sql = "INSERT INTO Flight_Information VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement preparedStatement = null;
		int result=0;
		try {// obtain preparedStatement
			preparedStatement = connection.prepareStatement(sql);
			connection.setAutoCommit(false);// transaction starts

			// set the values for place holders
			preparedStatement.setString(1, flightInformation.getFlightNumber());
			preparedStatement.setString(2, flightInformation.getAirlineName());
			preparedStatement.setString(3, flightInformation.getDepartureCity().toUpperCase());
			preparedStatement.setString(4, flightInformation.getArrivalCity().toUpperCase());
			// convert LocalDate to Sql Date
			preparedStatement.setDate(5, java.sql.Date.valueOf(flightInformation.getDepartureDate()));
			preparedStatement.setDate(6, java.sql.Date.valueOf(flightInformation.getArrivalDate()));
			preparedStatement.setTime(7, java.sql.Time.valueOf(flightInformation.getDepartureTime()));
			preparedStatement.setTime(8, java.sql.Time.valueOf(flightInformation.getArrivalTime()));
			preparedStatement.setInt(9, flightInformation.getFirstClassSeats());
			preparedStatement.setDouble(10, flightInformation.getFirstClassFare());
			preparedStatement.setInt(11, flightInformation.getBusinessClassSeats());
			preparedStatement.setDouble(12, flightInformation.getBusinessClassFare());
			// execute DML query
			result = preparedStatement.executeUpdate();
			connection.commit();// if insert successful the commit the
								// transaction
		} catch (SQLException e) {
			myLogger.error("ERRROR :  " + "Inserting flight details failed  "
					+ e.getMessage());
			e.printStackTrace();
			throw new AirlineException("ERRROR :  Inserting flight details failed  "
					+ e.getMessage());
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
				// if exception then transaction will rollback
				connection.rollback();
			} catch (SQLException e) {
				myLogger.error("ERRROR :  " + "Inserting flight details failed "
						+ e.getMessage());
				throw new AirlineException(
						"ERRROR : Inserting flight details failed " + e.getMessage());
			}
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see com.capgemini.ars.dao.IAdminDao#updateFlightDetails(com.capgemini.ars.bean.FlightInformation)
	 */
	@Override
	public int updateFlightDetails(FlightInformation flightInformation)
			throws AirlineException {
		myLogger.info("Update FlightDetails() invoked in AdminDaoImpl!!");
		
		//Flight_Information: flight_no(varchar2(5)), airline (varchar2(10)),
		//dep_city (varchar2(10)), arr_city (varchar2(10)), dep_date (date), 
		//arr_date (date), dep_time, arr_time, First_Seats (number), First_Seat_Fare 
		//(number(m,n)), Buss_Seats (number), Buss_Seats_Fare (number(m,n))
		
		String sql = "UPDATE Flight_Information SET dep_date=?,arr_date=?,dep_time=?,arr_time=? WHERE flight_no=?";
		PreparedStatement preparedStatement = null;
		int result=0;
		try {// obtain preparedStatement
			preparedStatement = connection.prepareStatement(sql);
			connection.setAutoCommit(false);// transaction starts

			// set the values for place holders
			// convert LocalDate to Sql Date
			preparedStatement.setDate(1, java.sql.Date.valueOf(flightInformation.getDepartureDate()));
			preparedStatement.setDate(2, java.sql.Date.valueOf(flightInformation.getArrivalDate()));
			preparedStatement.setTime(3, java.sql.Time.valueOf(flightInformation.getDepartureTime()));
			preparedStatement.setTime(4, java.sql.Time.valueOf(flightInformation.getArrivalTime()));
			preparedStatement.setString(5, flightInformation.getFlightNumber());
			
			// execute DML query
			result = preparedStatement.executeUpdate();

			connection.commit();// if successful the commit the
								// transaction
		} catch (SQLException e) {
			myLogger.error("ERRROR :  " + "Updating flight details failed  "
					+ e.getMessage());
			throw new AirlineException("ERRROR :  Updating flight details failed  "
					+ e.getMessage());
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
				// if exception then transaction will rollback
				connection.rollback();
			} catch (SQLException e) {
				myLogger.error("ERRROR :  " + "Updating flight details failed "
						+ e.getMessage());
				throw new AirlineException(
						"ERRROR : Updating flight details failed " + e.getMessage());
			}
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see com.capgemini.ars.dao.IAdminDao#cancelFlightDetails(java.lang.String)
	 */
	@Override
	public int cancelFlightDetails(String flightId) throws AirlineException {
		myLogger.info("Cancel FlightDetails() invoked in AdminDaoImpl!!");
		
		//Flight_Information: flight_no(varchar2(5)), airline (varchar2(10)),
		//dep_city (varchar2(10)), arr_city (varchar2(10)), dep_date (date), 
		//arr_date (date), dep_time, arr_time, First_Seats (number), First_Seat_Fare 
		//(number(m,n)), Buss_Seats (number), Buss_Seats_Fare (number(m,n))
		
		String sql = "DELETE FROM Flight_Information WHERE flight_no=?";
		PreparedStatement preparedStatement = null;
		int result=0;
		try {// obtain preparedStatement
			preparedStatement = connection.prepareStatement(sql);
			connection.setAutoCommit(false);// transaction starts

			// set the values for place holders
			preparedStatement.setString(1, flightId);
			
			// execute DML query
			result = preparedStatement.executeUpdate();

			connection.commit();// if successful the commit the
								// transaction
		} catch (SQLException e) {
			myLogger.error("ERRROR :  " + "Cancelling flight details failed  "
					+ e.getMessage());
			throw new AirlineException("ERRROR :  Cancelling flight details failed  "
					+ e.getMessage());
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
				// if exception then transaction will rollback
				connection.rollback();
			} catch (SQLException e) {
				myLogger.error("ERRROR :  " + "Cancelling flight details failed "
						+ e.getMessage());
				throw new AirlineException(
						"ERRROR : Cancelling flight details failed " + e.getMessage());
			}
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see com.capgemini.ars.dao.IAdminDao#viewAllFlights()
	 */
	@Override
	public List<FlightInformation> viewFlightDetails() throws AirlineException {
		myLogger.info("View FlightDetails() invoked in AdminDaoImpl!!");
		ArrayList<FlightInformation> flightList = null;
		Statement statement = null;
		ResultSet resultSet = null;
		FlightInformation flightInformation = null;
		String sql = "SELECT * FROM Flight_Information";
		try {
			// obtain statement
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			flightList = new ArrayList<>();
			while (resultSet.next()) {
				flightInformation = new FlightInformation();
				// fetch the column data
				// and set to the flight object
				
				//Flight_Information: flight_no(varchar2(5)), airline (varchar2(10)),
				//dep_city (varchar2(10)), arr_city (varchar2(10)), dep_date (date), 
				//arr_date (date), dep_time, arr_time, First_Seats (number), First_Seat_Fare 
				//(number(m,n)), Buss_Seats (number), Buss_Seats_Fare (number(m,n))
				
				flightInformation.setFlightNumber(resultSet.getString(1));
				flightInformation.setAirlineName(resultSet.getString(2));
				flightInformation.setDepartureCity(resultSet.getString(3));
				flightInformation.setArrivalCity(resultSet.getString(4));
				flightInformation.setDepartureDate(resultSet.getDate(5).toLocalDate());
				flightInformation.setArrivalDate(resultSet.getDate(6).toLocalDate());
				flightInformation.setDepartureTime(resultSet.getTime(7).toLocalTime());
				flightInformation.setArrivalTime(resultSet.getTime(8).toLocalTime());
				flightInformation.setFirstClassSeats(resultSet.getInt(9));
				flightInformation.setFirstClassFare(resultSet.getDouble(10));
				flightInformation.setBusinessClassSeats(resultSet.getInt(11));
				flightInformation.setBusinessClassFare(resultSet.getDouble(12));
				// add the trainee object to the list
				flightList.add(flightInformation);
			}
		} catch (SQLException e) {
			myLogger.error("FlightList not found , error occured : "
					+ e.getMessage());
			throw new AirlineException(e.getMessage());
		} finally {
			try {
				if (statement != null)
					statement.close();
			} catch (SQLException e) {
				myLogger.error("FlightList not found , error occured :"
						+ e.getMessage());
				throw new AirlineException(e.getMessage());
			}
		}
		return flightList;
	}

	/* (non-Javadoc)
	 * @see com.capgemini.ars.dao.IAdminDao#searchFlightDetailsById(java.lang.String)
	 */
	@Override
	public FlightInformation searchFlightDetailsById(String flightId) throws AirlineException {
		myLogger.info("Search FlightDetailsById() invoked in AdminDaoImpl!!");
		FlightInformation flightInformation=null;
		ResultSet resultSet = null;
		String sql = "SELECT * FROM Flight_Information WHERE flight_no=?";
		
		PreparedStatement preparedStatement = null;
		try {
			// obtain preparedStatement
			flightInformation= new FlightInformation();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, flightId);// setting the date
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()){
				// fetch the column data
				// and set to the flight object
				
				//Flight_Information: flight_no(varchar2(5)), airline (varchar2(10)),
				//dep_city (varchar2(10)), arr_city (varchar2(10)), dep_date (date), 
				//arr_date (date), dep_time, arr_time, First_Seats (number), First_Seat_Fare 
				//(number(m,n)), Buss_Seats (number), Buss_Seats_Fare (number(m,n))
				
				flightInformation.setFlightNumber(resultSet.getString(1));
				flightInformation.setAirlineName(resultSet.getString(2));
				flightInformation.setDepartureCity(resultSet.getString(3));
				flightInformation.setArrivalCity(resultSet.getString(4));
				flightInformation.setDepartureDate(resultSet.getDate(5).toLocalDate());
				flightInformation.setArrivalDate(resultSet.getDate(6).toLocalDate());
				flightInformation.setDepartureTime(resultSet.getTime(7).toLocalTime());
				flightInformation.setArrivalTime(resultSet.getTime(8).toLocalTime());
				flightInformation.setFirstClassSeats(resultSet.getInt(9));
				flightInformation.setFirstClassFare(resultSet.getDouble(10));
				flightInformation.setBusinessClassSeats(resultSet.getInt(11));
				flightInformation.setBusinessClassFare(resultSet.getDouble(12));
			}
		} catch (SQLException e) {
			myLogger.error("Flight not found , error occured : "
					+ e.getMessage());
			throw new AirlineException();
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} catch (SQLException e) {
				myLogger.error("Flight not found , error occured :"
						+ e.getMessage());
				throw new AirlineException(e.getMessage());
			}
		}
		return flightInformation;
	}

	/* (non-Javadoc)
	 * @see com.capgemini.ars.dao.IAdminDao#searchFlightDetailsByPlace(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<FlightInformation> searchFlightDetailsByPlace(String source, String destination)
			throws AirlineException {
		myLogger.info("Search FlightDetailsByPlace() invoked in AdminDaoImpl!!");
		ArrayList<FlightInformation> flightList = null;
		ResultSet resultSet = null;
		FlightInformation flightInformation = null;
		flightList = new ArrayList<>();
		String sql = "SELECT * FROM Flight_Information WHERE dep_city = ? AND arr_city = ?";

		PreparedStatement preparedStatement = null;
		try {
			// obtain preparedStatement
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, source.toUpperCase());
			preparedStatement.setString(2, destination.toUpperCase());
			resultSet = preparedStatement.executeQuery();
			flightList = new ArrayList<>();
			while (resultSet.next()) {
				flightInformation=new FlightInformation();
				flightInformation.setFlightNumber(resultSet.getString(1));
				flightInformation.setAirlineName(resultSet.getString(2));
				flightInformation.setDepartureCity(resultSet.getString(3));
				flightInformation.setArrivalCity(resultSet.getString(4));
				flightInformation.setDepartureDate(resultSet.getDate(5).toLocalDate());
				flightInformation.setArrivalDate(resultSet.getDate(6).toLocalDate());
				flightInformation.setDepartureTime(resultSet.getTime(7).toLocalTime());
				flightInformation.setArrivalTime(resultSet.getTime(8).toLocalTime());
				flightInformation.setFirstClassSeats(resultSet.getInt(9));
				flightInformation.setFirstClassFare(resultSet.getDouble(10));
				flightInformation.setBusinessClassSeats(resultSet.getInt(11));
				flightInformation.setBusinessClassFare(resultSet.getDouble(12));
				flightList.add(flightInformation);
			}
		} catch (SQLException e) {
			myLogger.error("flightInformationList not found , error occured : "
					+ e.getMessage());
			throw new AirlineException(e.getMessage());
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} catch (SQLException e) {
				myLogger.error("flightInformationList not found , error occured :"
						+ e.getMessage());
				throw new AirlineException(e.getMessage());
			}
		}
		return flightList;
	}

}
