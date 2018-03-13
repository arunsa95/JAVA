package com.capgemini.ars.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.capgemini.ars.bean.FlightInformation;
import com.capgemini.ars.dao.AdminDaoImpl;
import com.capgemini.ars.exception.AirlineException;

public class AdminDaoTest {
	 static AdminDaoImpl adminDao;	
		@BeforeClass
		public static void testAdminDaoImpl(){
			adminDao= new AdminDaoImpl();
		}
	
	@Test
	public void testInsertFlightDetails1() throws AirlineException {
		LocalDate day = LocalDate.of(2095, 07, 24);
		FlightInformation flight= new FlightInformation("XYZ123", "IndiGo", "Kolkata", 
				"Mumbai", day,day, LocalTime.now(), LocalTime.of(18, 20), 
				40, 5000, 100, 2000);
		int result=adminDao.insertFlightDetails(flight);
		assertNotEquals(0,result);
	}
	@Test
	public void testInsertFlightDetails2() throws AirlineException {
		LocalDate day = LocalDate.of(2095, 07, 24);
		FlightInformation flight= new FlightInformation("XYZ235", "IndiGo", "Kolkata", 
				"Mumbai", day, day, LocalTime.of(13, 20), LocalTime.of(18, 20),
				40, 5000, 100, 2000);
		int result=adminDao.insertFlightDetails(flight);
		assertTrue(result<=0);
	}

	@Test
	public void testUpdateFlightDetails1() throws AirlineException {
		LocalDate day = LocalDate.of(2095, 07, 24);
		FlightInformation flight= new FlightInformation("XYZ123", "IndiGo", "Kolkata", 
				"Mumbai", day,day, LocalTime.now(), LocalTime.of(18, 20), 
				40, 6000, 100, 2000);
		int result=adminDao.updateFlightDetails(flight);
		assertNotEquals(0,result);
	}


	@Test
	public void testUpdateFlightDetails() throws AirlineException {
		LocalDate day = LocalDate.of(2095, 07, 24);
		FlightInformation flight= new FlightInformation("XYZ235", "IndiGo", "Kolkata", 
				"Mumbai", day,day, LocalTime.now(), LocalTime.of(18, 20), 
				40, 7000, 100, 2000);
		int result=adminDao.updateFlightDetails(flight);
		assertTrue(result>0);
	}
	@Test
	public void testCancelFlightDetails1() throws AirlineException {
		int status = adminDao.cancelFlightDetails("XYZ123");
		assertFalse(status<0);
	}
	@Test
	public void testCancelFlightDetails2() throws AirlineException {
		int status = adminDao.cancelFlightDetails("AAA111");
		assertEquals(0, status);
	}

	@Test
	public void testViewFlightDetails1() throws AirlineException {
		List<FlightInformation> flightList=adminDao.viewFlightDetails();
		assertTrue("Flight List Not Found",
				flightList!=null);
	}
	@Test
	public void testViewFlightDetails2() throws AirlineException {
		List<FlightInformation> flightList=adminDao.viewFlightDetails();
		assertNotEquals(null,flightList);
	}


	@Test
	public void testSearchFlightDetailsById1() throws AirlineException {
		FlightInformation flight = adminDao.searchFlightDetailsById("XYZ123");
		assertTrue(flight.getFlightNumber()!=null);
	}
	@Test
	public void testSearchFlightDetailsById2() throws AirlineException {
		FlightInformation flight = adminDao.searchFlightDetailsById("AAA111");
		assertTrue(flight.getAirlineName()==null);
	}

	@Test
	public void testSearchFlightDetailsByPlace1() throws AirlineException {
		String source="hjhehj";
		String destination="hbribhoeir";
		List<FlightInformation> flightList = adminDao.searchFlightDetailsByPlace(source, destination);
		assertNotEquals(null, flightList);
	}

	@Test
	public void testSearchFlightDetailsByPlace2() throws AirlineException {
		String source="Kolkata";
		String destination="Mumbai";
		List<FlightInformation> flightList = adminDao.searchFlightDetailsByPlace(source, destination);
		assertTrue(flightList!=null);
	}

}
