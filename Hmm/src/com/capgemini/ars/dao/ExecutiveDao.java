/**
 * 
 */
package com.capgemini.ars.dao;

import java.time.LocalDate;
import java.util.List;

import com.capgemini.ars.bean.FlightInformation;
import com.capgemini.ars.exception.AirlineException;


/**
 * @author suadhika
 *
 */
public interface ExecutiveDao {

	public List<FlightInformation> displayFlightDetails() throws AirlineException;
	public List<FlightInformation> displayFlightOccupancyByPeriod(LocalDate fromDate, LocalDate toDate) throws AirlineException;
	public List<FlightInformation> displayFlightOccupancyByPlace(String source, String destination) throws AirlineException;

	
}
