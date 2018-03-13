package com.capgemini.ars.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.capgemini.ars.bean.BookingInformation;
import com.capgemini.ars.bean.FlightInformation;
import com.capgemini.ars.dao.UserDao;
import com.capgemini.ars.dao.UserDaoImpl;
import com.capgemini.ars.exception.AirlineException;


public class UserDaoImplTest {

	private static UserDao userDao =null;
	@BeforeClass
	public static void setUp() throws Exception {
		userDao = new UserDaoImpl();
	}


	@Test
	public void testBookFlight1() throws AirlineException {
		//fail("Not yet implemented");
		 /*BOOKING_ID                                NOT NULL VARCHAR2(5)
		 FLIGHTNO                                           VARCHAR2(5)
		 CUST_EMAIL                                         VARCHAR2(20)
		 NO_OF_PASSENGER                                    NUMBER
		 CLASS_TYPE                                         VARCHAR2(10)
		 TOTAL_FARE                                         NUMBER(10,2)
		 SEAT_NUMBER                                        VARCHAR2(200)
		 CREDITCARD_INFO                                    VARCHAR2(10)
		 SEC_CITY                                           VARCHAR2(10)
		 DEST_CITY                                          VARCHAR2(10)*/
		 
		BookingInformation book = new BookingInformation(100,"ABC123","abc@g.com",1,"FIRST",2000,"1234567890","Kolkata","Mumbai");
		int bookID = userDao.bookFlight(book);
		assertTrue(bookID!=0);
	}
	@Test
	public void testBookFlight2() throws AirlineException {
		BookingInformation book = new BookingInformation(101,"ABC123","abc@g.com",1,"FIRST",2000,"1234567890","Kolkata","Mumbai");
		int bookID = userDao.bookFlight(book);
		assertTrue(bookID!=0);
	}
	@Test
	public void testViewFlight1() throws AirlineException {
		BookingInformation book = userDao.viewFlightStatus(100,"abc@g.com");
		assertNotEquals(book,null);
	}
	@Test
	public void testViewFlight2() throws AirlineException {
		BookingInformation book = userDao.viewFlightStatus(101,"abc@g.com");
		assertFalse(book.getBookingId()<=0);
	}
	@Test
	public void testCancelFlight1() throws AirlineException {
		int status = userDao.cancelFlight(100,"abc@g.com");
		assertFalse(status<0);
	}
	@Test
	public void testCancelFlight2() throws AirlineException {
		int status = userDao.cancelFlight(101,"abc@g.com");
		assertNotEquals(0,status);
	}
	@Test
	public void testUpdateFlight1() throws AirlineException {
		int result=0;
		result = userDao.updateFlight(2, 100,"abc@g.com");
		assertFalse(result!=0);
	}
	@Test
	public void testUpdateFlight2() throws AirlineException {
		int result=0;
		result = userDao.updateFlight(2, 101,"abc@g.com");
		assertNotEquals(0,result);
	}

	@Test
	public void testViewFlightDetails1() throws AirlineException {
		List<FlightInformation> flightList = userDao.viewFlightDetail("Mumbai","Kolkata");
		assertNotEquals(null, flightList);
	}

	@Test
	public void testViewFlightDetails2() throws AirlineException {
		List<FlightInformation> flightList = userDao.viewFlightDetail("Mumbai","Kolkata");
		assertEquals(0, flightList.size());
	}


}
