/**
 * 
 */
package com.capgemini.ars.service;

import java.util.List;

import com.capgemini.ars.bean.BookingInformation;
import com.capgemini.ars.bean.FlightInformation;
import com.capgemini.ars.exception.AirlineException;

/**
 * @author kenshah
 *
 */
public interface UserService {

	public int bookFlight(BookingInformation book) throws AirlineException;
	
	public BookingInformation viewFlightStatus(int bookId,String userName) throws AirlineException;
	
	public int updateFlight(FlightInformation flight,int noOfPassenger,int bookId,String userName) throws AirlineException;
	
	public int cancelFlight(int bookId,String userName) throws AirlineException;
	public int getNoOfSeat(String type,String flightId) throws AirlineException;
	public List<FlightInformation> viewFlightDetail(String src,String des) throws AirlineException;

}
