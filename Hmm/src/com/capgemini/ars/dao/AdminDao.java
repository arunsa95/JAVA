package com.capgemini.ars.dao;

import java.util.ArrayList;
import java.util.List;

import com.capgemini.ars.bean.FlightInformation;
import com.capgemini.ars.exception.AirlineException;

public interface AdminDao {
public int insertFlightDetails(FlightInformation flightInformation) throws AirlineException;
public int updateFlightDetails(FlightInformation flightInformation) throws AirlineException;
public int cancelFlightDetails(String flightId) throws AirlineException;
public List<FlightInformation> viewFlightDetails() throws AirlineException;
public FlightInformation searchFlightDetailsById(String flightId) throws AirlineException;
public ArrayList<FlightInformation> searchFlightDetailsByPlace(String source,String destination) throws AirlineException;
}
