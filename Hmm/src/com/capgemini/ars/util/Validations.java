package com.capgemini.ars.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.capgemini.ars.exception.AirlineException;

@Component
public class Validations{

	public Validations() {
		super();
	}



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

	 
	public boolean validatePlaceName(String airline) {
		String patternStr = "^[a-zA-Z ]{1,30}$";
		// 3-20 char, first letter must be in caps

		// Now create Pattern object.
		Pattern pattern = Pattern.compile(patternStr);
		// Now create matcher object.
		Matcher matcher = pattern.matcher(airline);

		// now matcher matches
		return (matcher.matches());

	}

	 
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

	 
	public boolean validateSamePlace(String source, String destination)
			throws AirlineException {

		if (source.equals(destination))
			return false;
		else {
			return true;
		}
	}

	 
	public boolean validatePeriod(LocalDate fromDate, LocalDate toDate)
			throws AirlineException {

		String input1 = String.valueOf(fromDate);
		String input2 = String.valueOf(toDate);

		if (input2.compareTo(input1)>=0)
			return true;
		else
			return false;
	}

	public boolean validatePassword(String pass) {
		// creating regex pattern String
				String patternStr = "^((?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%!]).{6,20})$";
				
				// Now create Pattern object.
				Pattern pattern = Pattern.compile(patternStr);
				// Now create matcher object.
				Matcher matcher = pattern.matcher(pass);
				//now matcher matches
				return matcher.matches();
	}

	public boolean validateMobileNo(String phone) {
		
				// creating regex pattern String
				String patternStr = "^[789][0-9]{9}$";
				// 10 digits phone number
				// 1st number must start with 7 or 8 or 9
				// followed by 9 digits
				// Now create matcher object.

				Pattern pattern = Pattern.compile(patternStr);
				Matcher matcher = pattern.matcher(phone);
				return matcher.matches();
	}

	 
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

	 
	public boolean validateCreditCard(String credit) {
		String patternStr = 
				"^[1-9][0-9]{15}$";

		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(credit);
		
		return matcher.matches();
	}

	 
	public boolean validateNumber(String no) {
		String patternStr = 
				"^[0-9]{4,}$";

		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(no);
		
		return matcher.matches();
	}

	 
	public boolean validateName(String firstName) {
		String patternStr = 
				"^[A-Z][a-z A-Z]{2,19}$";

		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(firstName);
		
		return matcher.matches();
	}

	 
	public boolean validateInputNumber(String opt) {
		String patternStr = 
				"^[1-9][0-9]{0,3}$";

		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(opt);
		
		return matcher.matches();
	}

	
}
