/**
 * 
 */
package com.capgemini.ars.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.capgemini.ars.bean.BookingInformation;
import com.capgemini.ars.bean.FlightInformation;
import com.capgemini.ars.dao.UserDao;
import com.capgemini.ars.exception.AirlineException;

/**
 * @author suadhika
 *
 */
@Component("UserServiceImpl")
@EnableTransactionManagement
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	 private UserDao userDao;

	public UserServiceImpl()
	{
		super();
	}

	public UserServiceImpl(UserDao userDao)
	{
		super();
		this.userDao=userDao;
	}
	
	public  UserDao getDao() {
		return userDao;
	}

	public  void setDao(UserDao dao) {
		this.userDao = dao;
	}

	@Override
	public int bookFlight(BookingInformation book) throws AirlineException {
		return userDao.bookFlight(book);
	}

	@Override
	public BookingInformation viewFlightStatus(int bookId,String userName) throws AirlineException {
		return userDao.viewFlightStatus(bookId,userName);
	}

	@Override
	public int updateFlight(FlightInformation flight,int noOfPassenger, int bookId,String userName)
			throws AirlineException {
		return userDao.updateFlight(flight,noOfPassenger, bookId,userName);
	}

	@Override
	public int cancelFlight(int bookId,String userName) throws AirlineException {
		return userDao.cancelFlight(bookId,userName);
	}

	@Override
	public List<FlightInformation> viewFlightDetail(String src, String des)
			throws AirlineException {
		return userDao.viewFlightDetail(src, des);
	}

	@Override
	public int getNoOfSeat(String type,String flightId) throws AirlineException {
		return userDao.getNoOfSeat(type,flightId);
	}

	
	
}
