package com.capgemini.ars.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.capgemini.ars.bean.FlightInformation;
import com.capgemini.ars.exception.AirlineException;
import com.capgemini.ars.util.DBUtil;

public class ExecutiveDaoImpl implements ExecutiveDao {
	// step 1 :declare Connection and Logger variables
			private Connection connection;
			private static Logger myLogger = null;
			// step 2 :static block for logger code
			static {
				PropertyConfigurator.configure("resources/log4j.properties");
				myLogger = Logger.getLogger(ExecutiveDaoImpl.class.getName());
			}
			public ExecutiveDaoImpl() {
				try {
					connection = new DBUtil().obtainConnection();
					myLogger.info("Connection Obtained ... at DAO");
				} catch (AirlineException e) {
					myLogger.error("ERROR : " + e);
					System.err.println(e.getMessage());
				}
			}
	@Override
	public List<FlightInformation> displayFlightDetails()
			throws AirlineException {
		myLogger.info("Display FlightDetails invoked in executiveDaoImpl");
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

	@Override
	public List<FlightInformation> displayFlightOccupancyByPeriod(
			LocalDate fromDate, LocalDate toDate) throws AirlineException {

		myLogger.info("Display FlightOccupancyByPeriod invoked in executiveDaoImpl");
		ArrayList<FlightInformation> flightInformationList = null;
		ResultSet resultSet = null;
		FlightInformation flightInformation = null;
		flightInformationList = new ArrayList<>();
		
		/*flight_no(varchar2(5)), airline (varchar2(10)), dep_city (varchar2(10)), arr_city 
		(varchar2(10)), dep_date (date), arr_date (date), dep_time, arr_time, First_Seats 
		(number), First_Seat_Fare (number(m,n)), Buss_Seats (number), Buss_Seats_Fare 
		(number(m,n))*/
		
		String sql = "SELECT flight_no,airline,First_Seats,Buss_Seats FROM Flight_Information WHERE dep_date BETWEEN ? AND ?";

		PreparedStatement preparedStatement = null;
		try {
			// obtain PreparedStatement
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setDate(1, java.sql.Date.valueOf(fromDate));
			preparedStatement.setDate(2, java.sql.Date.valueOf(toDate));// setting the date
			resultSet = preparedStatement.executeQuery();
			flightInformationList = new ArrayList<>();
			while (resultSet.next()) {
				flightInformation = new FlightInformation();
				// fetch the column data
				flightInformation.setFlightNumber(resultSet.getString(1));
				flightInformation.setAirlineName(resultSet.getString(2));

				flightInformation.setFirstClassSeats(resultSet.getInt(3));
				flightInformation.setBusinessClassSeats(resultSet.getInt(4));

				// add the flightInformation object to the list
				flightInformationList.add(flightInformation);
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
		return flightInformationList;
	}

	
	@Override
	public List<FlightInformation> displayFlightOccupancyByPlace(String source,
			String destination) throws AirlineException {
		myLogger.info("Display FlightOccupancyByPlace invoked in executiveDaoImpl");
		ArrayList<FlightInformation> flightInformationList = null;
		ResultSet resultSet = null;
		FlightInformation flightInformation = null;
		flightInformationList = new ArrayList<>();
		String sql = "SELECT flight_no,airline,First_Seats,Buss_Seats FROM Flight_Information WHERE dep_city = ? AND arr_city = ?";

		PreparedStatement preparedStatement = null;
		try {
			// obtain pst
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, source.toUpperCase());
			preparedStatement.setString(2, destination.toUpperCase());
			resultSet = preparedStatement.executeQuery();
			flightInformationList = new ArrayList<>();
			while (resultSet.next()) {
				flightInformation = new FlightInformation();
				// fetch the column data
				flightInformation.setFlightNumber(resultSet.getString(1));
				flightInformation.setAirlineName(resultSet.getString(2));

				flightInformation.setFirstClassSeats(resultSet.getInt(3));
				flightInformation.setBusinessClassSeats(resultSet.getInt(4));

				// add the flightInformation object to the list
				flightInformationList.add(flightInformation);
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
		return flightInformationList;
	}

}
