package com.capgemini.ars.service;

import java.time.LocalDate;
import java.util.List;

import com.capgemini.ars.bean.FlightInformation;
import com.capgemini.ars.dao.ExecutiveDaoImpl;
import com.capgemini.ars.dao.ExecutiveDao;
import com.capgemini.ars.exception.AirlineException;

public class ExecutiveServiceImpl implements ExecutiveService {

	private static ExecutiveDao executiveDao;
	static {
		executiveDao = new ExecutiveDaoImpl();
	}

	@Override
	public List<FlightInformation> displayFlightDetails()
			throws AirlineException {

		return executiveDao.displayFlightDetails();
	}

	@Override
	public List<FlightInformation> displayFlightOccupancyByPeriod(
			LocalDate fromDate, LocalDate toDate) throws AirlineException {

		return executiveDao.displayFlightOccupancyByPeriod(fromDate, toDate);
	}

	@Override
	public List<FlightInformation> displayFlightOccupancyByPlace(String source,
			String destination) throws AirlineException {

		return executiveDao.displayFlightOccupancyByPlace(source, destination);
	}

	@Override
	public boolean validateSamePlace(String source, String destination)
			throws AirlineException {

		if (source.equals(destination))
			return false;
		else {
			return true;
		}
	}

	@Override
	public boolean validatePeriod(LocalDate fromDate, LocalDate toDate)
			throws AirlineException {

		String input1 = String.valueOf(fromDate);
		String input2 = String.valueOf(toDate);

		if (input2.compareTo(input1)>=0)
			return true;
		else
			return false;
	}

	

	

}
