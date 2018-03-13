package com.capgemini.ars.test;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.capgemini.ars.bean.FlightInformation;
import com.capgemini.ars.dao.ExecutiveDaoImpl;
import com.capgemini.ars.exception.AirlineException;

public class ExecutiveDaoTest {
	static ExecutiveDaoImpl executiveDao;

	@BeforeClass
	public static void testExecutiveDao() {
		executiveDao = new ExecutiveDaoImpl();
	}

	@Test
	public void testDisplayFlightDetails1() throws AirlineException {
		List<FlightInformation> flightList = executiveDao.displayFlightDetails();
		assertTrue("flight List Not Found", flightList != null);
	}

	@Test
	public void testDisplayFlightDetails2() throws AirlineException {
		List<FlightInformation> flightList = executiveDao.displayFlightDetails();
		assertNotEquals(null, flightList);
	}


	@Test
	public void testDisplayFlightOccupancyByPeriod1() throws AirlineException {
		LocalDate to = LocalDate.of(2095, 07, 24);
		LocalDate from = LocalDate.now();
		List<FlightInformation> flightList = executiveDao.displayFlightOccupancyByPeriod(from,to);
		assertTrue("flightInformationList not found", flightList!=null);

	}
	@Test
	public void testDisplayFlightOccupancyByPeriod2() throws AirlineException {
		LocalDate to = LocalDate.of(2095, 07, 24);
		LocalDate from = LocalDate.of(2099, 07, 24);
		List<FlightInformation> flightList = executiveDao.displayFlightOccupancyByPeriod(from,to);
		assertEquals(0, flightList.size());

	}
	@Test
	public void testDisplayFlightOccupancyByPlace1() throws AirlineException {
		String source="Kolkata";
		String destination="Mumbai";
		List<FlightInformation> flightList = executiveDao.displayFlightOccupancyByPlace(source, destination);
		assertNotEquals(null, flightList);
	}
	@Test
	public void testDisplayFlightOccupancyByPlace2() throws AirlineException {
		String source="Kolkataaaaa";
		String destination="Mumbaiiiiii";
		List<FlightInformation> flightList = executiveDao.displayFlightOccupancyByPlace(source, destination);
		assertEquals(0, flightList.size());
	}

}
