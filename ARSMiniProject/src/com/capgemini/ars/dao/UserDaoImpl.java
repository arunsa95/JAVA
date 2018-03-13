/**
 * 
 */
package com.capgemini.ars.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.capgemini.ars.bean.BookingInformation;
import com.capgemini.ars.bean.FlightInformation;
import com.capgemini.ars.exception.AirlineException;
import com.capgemini.ars.util.DBUtil;



/**
 * @author suadhika
 *
 */
public class UserDaoImpl implements UserDao{


	// step 1 :declare Connection and Logger variables
	private Connection connection;
	private static Logger myLogger = null;
	// step 2 :static blog for logger code
	static {
		PropertyConfigurator
		.configure("resources/log4j.properties");
		myLogger = Logger
				.getLogger(UserDaoImpl.class.getName());
	}
	// step 3 : obtain connection in the constructor
	public UserDaoImpl() {
		try {
			connection = new DBUtil().obtainConnection();
			myLogger.info("Connection Obtained ... at DAO");
		} catch (AirlineException e) {
			myLogger.error("ERROR : " + e);
			System.err.println(e.getMessage());
		}
	}

	@Override
	public int bookFlight(BookingInformation bookingInformation) throws AirlineException {

		myLogger.info("Bookflight() is invoked in userDaoImpl");
		String sql = "INSERT INTO Booking_Information VALUES(bookinginformation_seq.NEXTVAL,?,?,?,?,?,?,?,?)";
		PreparedStatement preparedStatement = null;
		int result = 0;
		int bookId=0;

		int currentSeats=0;
		currentSeats= getNoOfSeat(bookingInformation.getClassType(),bookingInformation.getFlightId());
		if(currentSeats>=bookingInformation.getNoOfPassenger())
		{
			try {// obtain PreparedStatement
				preparedStatement = connection.prepareStatement(sql);
				connection.setAutoCommit(false);// transaction starts

				// set the values for place holders
				preparedStatement.setString(1, bookingInformation.getFlightId());
				preparedStatement.setString(2, bookingInformation.getCustEmail());
				preparedStatement.setInt(3, bookingInformation.getNoOfPassenger());
				preparedStatement.setString(4, bookingInformation.getClassType());
				preparedStatement.setDouble(5, bookingInformation.getTotalFare());
				preparedStatement.setString(6, bookingInformation.getCreditCardInfo());
				preparedStatement.setString(7, bookingInformation.getSourceCity().toUpperCase());
				preparedStatement.setString(8, bookingInformation.getDestinationCity().toUpperCase());	
				// execute DML query
				result = preparedStatement.executeUpdate();
				if(result>0){
					bookId=getUniqueBookId();
					updateSeats(bookingInformation.getFlightId(),bookingInformation.getClassType(),bookingInformation.getNoOfPassenger());
					
						connection.commit();// if insert successful the commit the
				
					// transaction
					myLogger.info("Booking details getting inserted "
							+ "....unique booking ID : "+bookId);
				
				}
				else
				{
					myLogger.error("ERRROR :  " + "New Booking failed  ");
				}

			} catch (SQLException e) {
				myLogger.error("ERRROR :  " + "New Booking failed  "
						+ e.getMessage());
				throw new AirlineException("ERRROR :  New Booking failed  "
						+ e.getMessage());
			} finally {
				try {
					if (preparedStatement != null)
						preparedStatement.close();
					// if exception then transaction will rollback
					//connection.rollback();
				} catch (SQLException e) {
					myLogger.error("ERRROR :  " + "New Booking failed "
							+ e.getMessage());
					throw new AirlineException("ERRROR : New Booking failed "
							+ e.getMessage());
				}
			}
		}
		else
		{
			throw new AirlineException("Number of avilable seats is less than you required");
		}

		return bookId;
	}
	private int updateSeats(String flightId, String type,int noOfPassenger) throws AirlineException {
		myLogger.info("Updating number of seats in userDaoImpl");
		String sql="";
		int result=0;
		if(type.equals("FIRST"))
		{
			sql = "UPDATE flight_Information SET first_seats=first_seats-? WHERE flight_no=?";
		}
		else if(type.equals("BUSINESS"))
		{
			sql = "UPDATE flight_Information SET buss_seats=buss_seats-? WHERE flight_no=?";
		}
		PreparedStatement preparedStatement=null;
		try {
			preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setInt(1, noOfPassenger);
			preparedStatement.setString(2, flightId);
			result=preparedStatement.executeUpdate();
		} catch (SQLException e) {
			myLogger.error("Updating no of seats , error occured :"
					+ e.getMessage());
			throw new AirlineException(e.getMessage());
		}
		return result;
	}

	@Override
	public int getNoOfSeat(String type,String flightId) throws AirlineException {

		myLogger.info("Generating no of seat available");
		String sql="";
		if(type.equals("FIRST"))
		{
			sql = "SELECT first_seats FROM flight_Information WHERE flight_no=?";
		}
		else if(type.equals("BUSINESS"))
		{
			sql = "SELECT buss_seats FROM flight_Information WHERE flight_no=?";
		}
		else
		{
			return -1;
		}
		int noOfSeats = 0;
		PreparedStatement preparedStatement=null;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, flightId);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				noOfSeats = rs.getInt(1);
				myLogger.info("getting no of available seats : "
						+ noOfSeats);
			} else {
				myLogger.error("Error in getting number of seats. ");
			}
		}  catch (SQLException e) {
			myLogger.error("Error in getting number of seats. : "
					+ e.getMessage());
			throw new AirlineException(e.getMessage());
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} catch (SQLException e) {
				myLogger.error("Error in getting number of seats. :"
						+ e.getMessage());
				throw new AirlineException(e.getMessage());
			}
		}
		return noOfSeats;
	}

	private int getUniqueBookId() throws AirlineException {
		myLogger.info("Generating unique bookId in userDaoImpl");
		String sql = "SELECT BookingInformation_seq.currval FROM dual";
		int bookId = 0;
		Statement statement = null;
		try {
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			if (resultSet.next()) {
				bookId = resultSet.getInt(1);
				myLogger.info("Booking ID Auto-generated by sequence is : "
						+ bookId);
			} else {
				myLogger.error("Error in generating Booking Id ");
			}
		}  catch (SQLException e) {
			myLogger.error("Error in generating Booking Id : "
					+ e.getMessage());
			throw new AirlineException(e.getMessage());
		} finally {
			try {
				if (statement != null)
					statement.close();
			} catch (SQLException e) {
				myLogger.error("Error in generating Booking Id :"
						+ e.getMessage());
				throw new AirlineException(e.getMessage());
			}
		}
		return bookId;
	}

	@Override
	public BookingInformation viewFlightStatus(int bookId,String userName) throws AirlineException {
		BookingInformation bookingInformation = new BookingInformation();
		myLogger.info("Generating BookingInformation info in userDaoImpl");
		String sql = "SELECT * FROM Booking_Information WHERE Booking_id=? AND cust_email=?";
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, bookId);
			preparedStatement.setString(2, userName);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				bookingInformation = new BookingInformation();
				bookingInformation.setBookingId(rs.getInt(1));
				bookingInformation.setFlightId(rs.getString(2));
				bookingInformation.setCustEmail(rs.getString(3));
				bookingInformation.setNoOfPassenger(rs.getInt(4));
				bookingInformation.setClassType(rs.getString(5));
				bookingInformation.setTotalFare(rs.getDouble(6));
				bookingInformation.setCreditCardInfo(rs.getString(7));
				bookingInformation.setSourceCity(rs.getString(8));
				bookingInformation.setDestinationCity(rs.getString(9));
				myLogger.info("Generated BookingInformation info for booking ID : "+bookId);
			} else {
				myLogger.error("Generating BookingInformation info for booking ID : "+bookId+", error occured ");
			}
		}  catch (SQLException e) {
			myLogger.error("Generating BookingInformation info , error occured : "
					+ e.getMessage());
			throw new AirlineException(e.getMessage());
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} catch (SQLException e) {
				myLogger.error("Generating BookingInformation info , error occured :"
						+ e.getMessage());
				throw new AirlineException(e.getMessage());
			}
		}
		return bookingInformation;
	}

	@Override
	public int updateFlight(int noOfPassenger,int bookId,String userName) throws AirlineException {
		myLogger.info("update flight is invoked");
		BookingInformation bookingInformation=viewFlightStatus(bookId,userName);
		double price=(bookingInformation.getTotalFare()*noOfPassenger/bookingInformation.getNoOfPassenger());
		int currentSeats=noOfPassenger-bookingInformation.getNoOfPassenger();
		String sql = "UPDATE Booking_Information SET no_of_passenger=?,total_fare=? WHERE Booking_id=? AND cust_email=?";
		PreparedStatement preparedStatement = null;
		int result = 0;

		try {// obtain ps
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, noOfPassenger);
			preparedStatement.setDouble(2, price);
			preparedStatement.setInt(3, bookId);
			preparedStatement.setString(4, userName);
			connection.setAutoCommit(false);// transaction starts
			// execute DML query	
			// execute DML query
			result = preparedStatement.executeUpdate();
			updateSeats(bookingInformation.getFlightId(), bookingInformation.getClassType(), currentSeats);
			myLogger.info("Booking Information updated for Booking Id : "+bookId);
			
			connection.commit();// if insert successful the commit the
			// transaction
		} catch (SQLException e) {
			myLogger.error("ERRROR :  " + " Update BookingInformation failed  "
					+ e.getMessage());
			throw new AirlineException("ERRROR :Update BookingInformation failed  "
					+ e.getMessage());
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
				// if exception then transaction will rollback
				//connection.rollback();
			} catch (SQLException e) {
				myLogger.error("ERRROR :  " + "Update BookingInformation failed "
						+ e.getMessage());
				throw new AirlineException("ERRROR : Update BookingInformation failed "
						+ e.getMessage());
			}
		}
		return result;
	}

	@Override
	public int cancelFlight(int bookId,String userName) throws AirlineException {

		myLogger.info("Cancel flight is invoked in userDaoImpl");
		String sql = "DELETE FROM Booking_Information WHERE Booking_id=? AND cust_email=?";
		PreparedStatement preparedStatement = null;
		int result=0,status=0;
		BookingInformation book=new BookingInformation();
		try {// obtain preparedStatement
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, bookId);
			preparedStatement.setString(2, userName);
			connection.setAutoCommit(false);// transaction starts
			book=viewFlightStatus(bookId, userName);
			// execute DML query
			result = preparedStatement.executeUpdate();
			myLogger.info("Booking Cancelled for Booking Id : "+bookId);
			if(result>0)
			{
				status=updateSeats(book.getFlightId(), book.getClassType(), book.getNoOfPassenger()*(-1));
				if(status>0)
					connection.commit();// if insert successful the commit the
			}
			// transaction
		} catch (SQLException e) {
			myLogger.error("ERRROR :  " + " Cancel BookingInformation failed  "
					+ e.getMessage());
			throw new AirlineException("ERRROR :Cancel BookingInformation failed  "
					+ e.getMessage());
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
				// if exception then transaction will rollback
				//connection.rollback();
			} catch (SQLException e) {
				myLogger.error("ERRROR :  " + "Cancel BookingInformation failed "
						+ e.getMessage());
				throw new AirlineException("ERRROR : Cancel BookingInformation failed "
						+ e.getMessage());
			}
		}
		return status;
	}

	@Override
	public List<FlightInformation> viewFlightDetail(String src, String des)
			throws AirlineException {
		List<FlightInformation> flightList=new ArrayList<FlightInformation>();
		FlightInformation flightInformation = null;
		myLogger.info("View Flight details invoked in userDaoImpl");
		String sql="";
		sql = "SELECT * FROM flight_information WHERE dep_city=? AND arr_city=?";
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, src.toUpperCase());
			preparedStatement.setString(2, des.toUpperCase());
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				flightInformation = new FlightInformation();
				flightInformation.setFlightNumber(resultSet.getString(1));
				flightInformation.setAirlineName(resultSet.getString(2));
				flightInformation.setDepartureCity(resultSet.getString(3));
				flightInformation.setArrivalCity(resultSet.getString(4));
				flightInformation.setDepartureDate(resultSet.getDate(5).toLocalDate());
				flightInformation.setArrivalDate(resultSet.getDate(6).toLocalDate());		
				Time arrTime = resultSet.getTime(7);
				flightInformation.setDepartureTime(arrTime.toLocalTime());
				Time time = resultSet.getTime(8);
				flightInformation.setArrivalTime(time.toLocalTime());
				flightInformation.setFirstClassSeats(resultSet.getInt(9));
				flightInformation.setFirstClassFare(resultSet.getDouble(10));
				flightInformation.setBusinessClassSeats(resultSet.getInt(11));
				flightInformation.setBusinessClassFare(resultSet.getDouble(12));

				flightList.add(flightInformation);
				myLogger.info("Flight details between Cities "+src+" and "+des+" are showed.");
			} 
		}  catch (SQLException e) {
			myLogger.error(" Error occured while Viewing flightinformation details : "
					+ e.getMessage());
			throw new AirlineException(e.getMessage());
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} catch (SQLException e) {
				myLogger.error("Error occured while Viewing flightinformation details :"
						+ e.getMessage());
				throw new AirlineException(e.getMessage());
			}
		}
		return flightList;
	}

	
}
