package com.capgemini.ars.service;

import java.time.LocalDate;
import java.util.List;

import com.capgemini.ars.bean.FlightInformation;
import com.capgemini.ars.exception.AirlineException;

public interface ExecutiveService {	

	//business methods
	public List<FlightInformation> displayFlightDetails() throws AirlineException;
	public List<FlightInformation> displayFlightOccupancyByPeriod(LocalDate fromDate, LocalDate toDate) throws AirlineException;
	public List<FlightInformation> displayFlightOccupancyByPlace(String source, String destination) throws AirlineException;

}
