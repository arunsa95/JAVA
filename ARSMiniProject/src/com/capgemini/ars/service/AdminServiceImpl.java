package com.capgemini.ars.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.capgemini.ars.bean.FlightInformation;
import com.capgemini.ars.dao.AdminDaoImpl;
import com.capgemini.ars.dao.AdminDao;
import com.capgemini.ars.exception.AirlineException;

public class AdminServiceImpl implements AdminService {

	private static AdminDao adminDao;
	static{
		adminDao=new AdminDaoImpl();
	}
	@Override
	public int insertFlightDetails(FlightInformation flight)
			throws AirlineException {
		return adminDao.insertFlightDetails(flight);
	}

	@Override
	public int updateFlightDetails(FlightInformation flight)
			throws AirlineException {
		return adminDao.updateFlightDetails(flight);
	}

	@Override
	public int cancelFlightDetails(String flightId) throws AirlineException {
		return adminDao.cancelFlightDetails(flightId);
	}

	@Override
	public List<FlightInformation> viewFlightDetails() throws AirlineException {
		return adminDao.viewFlightDetails();
	}

	@Override
	public FlightInformation searchFlightDetailsById(String flightId)
			throws AirlineException {
		return adminDao.searchFlightDetailsById(flightId);
	}

	@Override
	public ArrayList<FlightInformation> searchFlightDetailsByPlace(
			String source, String destination) throws AirlineException {
		return adminDao.searchFlightDetailsByPlace(source, destination);
	}

	@Override
	public boolean validateFlightNo(String flightId) throws AirlineException {
		// String input = String.valueOf(flightNumber);
				String patternStr = "^[A-Z]{3}[0-9]{3}$";
				// 3-20 char, first letter must be in caps

				// Now create Pattern object.
				Pattern pattern = Pattern.compile(patternStr);
				// Now create matcher object.
				Matcher matcher = pattern.matcher(flightId);
				
				return (matcher.matches());
	}

	@Override
	public boolean availableFlightNo(String flightNo) throws AirlineException {
		if(adminDao.searchFlightDetailsById(flightNo).getFlightNumber()!=null)
			return false;
		else
			return true;
	}

	@Override
	public boolean validateDate(String deptDate) throws AirlineException {
		try{
		String month=deptDate.substring(3, 5);
		String day=deptDate.substring(0, 2);

			String dobRegex = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[0-2])/((20)\\d\\d)";
		// formatting String dob to LocalDate
		DateTimeFormatter formatter = 
				DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate enteredDate = LocalDate.parse(deptDate, formatter);
		// student should be min 21 years
		LocalDate today = LocalDate.now();
		Pattern dobPatter = Pattern.compile(dobRegex);
		Matcher dobMatcher = dobPatter.matcher(deptDate);
		if (dobMatcher.matches()) {
			if (enteredDate.isBefore(today)) {
				return false;
			} else {
				if("02".equals(month))
				{
					if(enteredDate.isLeapYear())
					{
						if(Integer.parseInt(day)>29)
							return false;
					}
					else
					{
						if(Integer.parseInt(day)>28)
							return false;
					}
				}
				if("04".equals(month)||"06".equals(month)||"09".equals(month)||"11".equals(month))
					if(Integer.parseInt(day)>30)
						return false;
				return true;
			}
		} else {
			return false;
		}
		}catch(Exception e)
		{
			throw new AirlineException("Inputs must be in specified format!!");
		}
	}

	@Override
	public boolean validateTime(String deptTime) throws AirlineException {
		String dobRegex = "([01][0-9]|2[0-3]):[0-5][0-9]";
		try{
		Pattern dobPatter = Pattern.compile(dobRegex);
		Matcher dobMatcher = dobPatter.matcher(deptTime);
		if (dobMatcher.matches()) {
				return true;
			} else {
				return false;
			}
		}catch(Exception e)
		{
			throw new AirlineException("Inputs must be in specified format!!");
		}
	}

	@Override
	public boolean validateSeats(String input) {
		boolean flag = false;
		String patternStr = "^[0-9]{1,4}$";
		// 3-20 char, first letter must be in caps

		// Now create Pattern object.
		Pattern pattern = Pattern.compile(patternStr);
		// Now create matcher object.
		Matcher matcher1 = pattern.matcher(input);
		// now matcher matches
		
		if (matcher1.matches() ) {
			int fSeats=Integer.parseInt(input);
			if (fSeats<= 1000) {
				flag = true;
			}
		}
		return flag;
	}

	@Override
	public boolean validateSeatPrice(String input) {
		
		
		boolean flag = false;
		String patternStr = "^[0-9]{3,5}(.[0-9]{1,2})?$";
		Pattern pattern = Pattern.compile(patternStr);
		// Now create matcher object.
		Matcher matcher1 = pattern.matcher(input);
		
		if (matcher1.matches() ) {
			double pfSeats=Double.parseDouble(input);
			if ((pfSeats > 0) ) {
				flag = true;
			}
		}
		return flag;
	}

	@Override
	public boolean validatePlaceName(String airline) {
		String patternStr = "^[a-zA-Z ]{3,30}$";
		// 3-20 char, first letter must be in caps

		// Now create Pattern object.
		Pattern pattern = Pattern.compile(patternStr);
		// Now create matcher object.
		Matcher matcher = pattern.matcher(airline);

		// now matcher matches
		return (matcher.matches());

	}

	@Override
	public boolean validateTimePeriod(LocalDate departureDate,LocalDate arrivalDate, LocalTime depTime, LocalTime arrTime) {
		String input1 = String.valueOf(departureDate);
		String input2 = String.valueOf(arrivalDate);
		String input3 = String.valueOf(depTime);
		String input4 = String.valueOf(arrTime);

		if (((input2.compareTo(input1)==0)
				&& (input4.compareTo(input3)>0))||(input2.compareTo(input1)>0))
			return true;
		else
			return false;
	}


}
