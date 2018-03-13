package com.capgemini.ars.service;

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
	public boolean availableFlightNo(String flightNo) throws AirlineException;
}
