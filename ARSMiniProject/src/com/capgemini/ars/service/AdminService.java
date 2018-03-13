package com.capgemini.ars.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.capgemini.ars.bean.FlightInformation;
import com.capgemini.ars.exception.AirlineException;

public interface AdminService {
	public int insertFlightDetails(FlightInformation flight) throws AirlineException;
	public int updateFlightDetails(FlightInformation flight) throws AirlineException;
	public int cancelFlightDetails(String flightId) throws AirlineException;
	public List<FlightInformation> viewFlightDetails() throws AirlineException;
	public FlightInformation searchFlightDetailsById(String flightId) throws AirlineException;
	public ArrayList<FlightInformation> searchFlightDetailsByPlace(String source,String destination) throws AirlineException;
	public boolean validateFlightNo(String flightId) throws AirlineException;
	public boolean validateDate(String deptDate) throws AirlineException;
	public boolean validateTime(String deptTime) throws AirlineException;
	public boolean validateSeatPrice(String pfSeats);
	public boolean validatePlaceName(String airline);
	public boolean validateTimePeriod(LocalDate departureDate,
			LocalDate arrivalDate, LocalTime depTime, LocalTime arrTime);
	boolean validateSeats(String fSeats);
	public boolean availableFlightNo(String flightNo) throws AirlineException;
}
