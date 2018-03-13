/**
 * 
 */
package com.capgemini.ars.service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.capgemini.ars.bean.BookingInformation;
import com.capgemini.ars.bean.FlightInformation;
import com.capgemini.ars.dao.UserDao;
import com.capgemini.ars.dao.UserDaoImpl;
import com.capgemini.ars.exception.AirlineException;

/**
 * @author suadhika
 *
 */
public class UserServiceImpl implements UserService {

	static UserDao dao = null;
	static int f1001=1;
	static{
		dao = new UserDaoImpl();
	
	}
	@Override
	public int bookFlight(BookingInformation book) throws AirlineException {
		return dao.bookFlight(book);
	}

	@Override
	public BookingInformation viewFlightStatus(int bookId,String userName) throws AirlineException {
		return dao.viewFlightStatus(bookId,userName);
	}

	@Override
	public int updateFlight(int noOfPassenger, int bookId,String userName)
			throws AirlineException {
		return dao.updateFlight(noOfPassenger, bookId,userName);
	}

	@Override
	public int cancelFlight(int bookId,String userName) throws AirlineException {
		return dao.cancelFlight(bookId,userName);
	}

	@Override
	public List<FlightInformation> viewFlightDetail(String src, String des)
			throws AirlineException {
		return dao.viewFlightDetail(src, des);
	}

	@Override
	public int getNoOfSeat(String type,String flightId) throws AirlineException {
		return dao.getNoOfSeat(type,flightId);
	}

	
	

	@Override
	public boolean validateEmail(String custEmail) {
		// creating regex pattern String
				String patternStr = 
						"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
				// must be in proper email format
				// Now create matcher object.

				Pattern pattern = Pattern.compile(patternStr);
				Matcher matcher = pattern.matcher(custEmail);
				return matcher.matches();
	}

	@Override
	public boolean validateCreditCard(String credit) {
		String patternStr = 
				"^[1-9][0-9]{15}$";

		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(credit);
		
		return matcher.matches();
	}

	@Override
	public boolean validateNumber(String no) {
		String patternStr = 
				"^[0-9]{4,}$";

		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(no);
		
		return matcher.matches();
	}

	@Override
	public boolean validateName(String firstName) {
		String patternStr = 
				"^[A-Z][a-z A-Z]{2,19}$";

		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(firstName);
		
		return matcher.matches();
	}

	@Override
	public boolean validateInputNumber(String opt) {
		String patternStr = 
				"^[1-9][0-9]{0,3}$";

		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(opt);
		
		return matcher.matches();
	}

}
