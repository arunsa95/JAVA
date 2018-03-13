/**
 * 
 */
package com.capgemini.ars.ui;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.capgemini.ars.bean.BookingInformation;
import com.capgemini.ars.bean.FlightInformation;
import com.capgemini.ars.bean.Users;
import com.capgemini.ars.exception.AirlineException;
import com.capgemini.ars.service.AdminService;
import com.capgemini.ars.service.ExecutiveService;
import com.capgemini.ars.service.LoginService;
import com.capgemini.ars.service.UserService;
import com.capgemini.ars.util.Validations;

/**
 * @author suadhika
 *
 */
@Component
public class AirlineClient {

	@Autowired
	private   AdminService adminService;
	@Autowired
	private   ExecutiveService executiveService;
	@Autowired
	private   UserService userService;
	@Autowired
	private   LoginService loginService;
	@Autowired
	private Validations valid;
	private   Scanner scanner=new Scanner(System.in);
	private   FlightInformation flight = new FlightInformation();
	private   Users user = new Users();
	//private   Logger myLogger = null;
	private   String role = null;

	public AirlineClient(AdminService adminService,Validations validate,ExecutiveService executiveService,UserService userService,LoginService loginService)
	{
		super();
		this.adminService=adminService;
		this.executiveService=executiveService;
		this.userService=userService;
		this.loginService=loginService;
		this.valid=validate;
	}

	public AirlineClient()
	{
		super();
	}

	/**
	 * @param args
	 * @throws AirlineException
	 */
	public static void main(String[] args)  {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:/spring.xml");

		AirlineClient ui = ctx.getBean(AirlineClient.class);
		int option = 0;
		
		do {
			option = ui.showMenu(option);
			switch (option) {
			case 1:
				ui.login();
				break;
			case 2:
				ui.register();
				break;
			case 3:
				ui.exit();
				break;
			default:
				System.err
				.println("Wrong option entered... kindly enter choice (1-3) only");
				break;
			}
		} while (true);
	}

	private   int showMenu(int option) {
		while (true) // while
		{

			System.out.print("\n__________________________________________\n"
					+ "\n	Airline Reservation Application           \n"
					+ "\n__________________________________________\n"
					+ "\n	1. Login" + "\n	2. Register" + "\n	3. Exit"
					+ "\n__________________________________________\n"
					+ "\n	Enter your Choice :");
			String opt = scanner.nextLine();
			System.out
			.println("\n__________________________________________\n");
			if (valid.validateInputNumber(opt)) {
				option = Integer.parseInt(opt);
				break;
			} else
				System.err.println("	\nInput should be only Numbers and between (1-3) only, Try again!!!");
		}// End Of While
		return option;
	}

	private   void register(){
		user = new Users();
		String email;
		try{
		while (true) // While-1
		{
			System.out.print("	Enter your Email ID : ");
			email = scanner.nextLine().toLowerCase();
			if (valid.validateEmail(email)) {
				if (loginService.validUserName(email)) {
					user.setEmail(email);
					break;
				} else {
					System.err.println("Email ID already exists, Try again!!!");
					//myLogger.error("Email ID" + email + "already exists");
				}
			} else
				System.err.println("Email must be in specified format, e.g. abc@xyz.com, Try Again");
		}// End Of While-1
		String firstName;
		while (true) // While-2
		{
			System.out.print("	Enter your First Name : ");
			firstName = scanner.nextLine();
			if (valid.validateName(firstName)) 
			{
				user.setFirstName(firstName);
				break;
			}
			else
				System.err
				.println("Name must be in specified format, e.g. Sudeshna, Try Again");
		}// End Of While-2
		String lastName;
		while (true) // While-3
		{
			System.out.print("	Enter your Last Name : ");
			lastName = scanner.nextLine();
			if (valid.validateName(lastName)) 
			{
				user.setLastName(lastName);
				break;
			}
			else
				System.err
				.println("Name must be in specified format, e.g. Adhikary, Try Again");
		}// End Of While-3
		while (true) // While-4
		{
			String password, cPassword;
			while (true) // While-4.1
			{
				System.out.print("	Enter password : ");
				password = scanner.nextLine();
				if (valid.validatePassword(password))
					break;
				else
					System.err
					.println("Password must be in specified format, at least 1 UpperCase Letter, 1 LowerCase Letter, 1 Number, 1 Special Character and minimum 6-characters, Try Again");
			} // End Of While-4.1
			while (true) // While-4.2
			{
				System.out.print("	Confirm password : ");
				cPassword = scanner.nextLine();
				{
					if (valid.validatePassword(cPassword))
						break;
					else
						System.err
						.println("Password must be in specified format, at least 1 UpperCase Letter, 1 LowerCase Letter, 1 Number, 1 Special Character and minimum 6-characters, Try Again");
				}
			} // End Of While-4.2
			if (password.equals(cPassword)) {
				user.setPassword(password);
				break;
			} else
				System.err.println("Passwords doesn't match, Try again!!!");
		} // End Of While-4

		while (true) // While-5
		{
			System.out.print("	Enter your Mobile Number : ");
			String mobile = scanner.nextLine();
			if (valid.validateMobileNo(mobile)) {
				Double mobileNo = Double.parseDouble(mobile);
				user.setMobileNo(mobileNo);
				break;
			} else
				System.err
				.println("Mobile Number must be in specified format, i.e. Start with 7/8/9 and total 10 numbers, Try again!!!");
		} // End Of While-5
		user.setRole("User");
		int status = loginService.register(user);
		if (status != 0) {
			System.out
			.println("\n__________________________________________\n");
			System.out.println("	Welcome " + user.getFirstName()+" "+user.getLastName());
			//myLogger.info(user.getFirstName()+" with email: "+email + " registered and logged in!!!");
			showFinalMenu("User", email);
		} else {
			System.err.println("Something went wrong while registering.");
			//myLogger.error("Something went wrong while registering.");
		}
		}catch(AirlineException e){
			System.err.println("Something went wrong while registering.");
			//myLogger.error("Something went wrong while registering.");
		}
	}

	private   void login()  {

		String email;
		try{
		while (true) // While-1
		{
			System.out.print("	Enter Email ID : ");
			email = scanner.nextLine().toLowerCase();
			if (valid.validateEmail(email)) {
				if (!loginService.validUserName(email)) 
					break;
				else {
					System.err.println("Email ID does not exist, Try again!!!");
				}
			} else
				System.err.println("Email must be in specified format, e.g. abc@xyz.com, Try Again");
		}
		System.out.print("	Enter Password : ");
		String password = scanner.nextLine();
		role = loginService.logInValidation(email, password);
		if ("invalid".equals(role)) {
			System.err.println("Email ID, Password doesn't match!!! Try again!");
			//myLogger.error("Email ID" + email
				//	+ ", Password doesn't match!!! Try again!");
		} else {
			System.out
			.println("\n__________________________________________\n");
			System.out.println("	Welcome " + loginService.getName(email));
			showFinalMenu(role, email);
			//myLogger.info(email + " logged in!!!");
		}
		}catch(AirlineException e)
		{
			System.err.println("Something went wrong while logging in.");
			//myLogger.error("Something went wrong while logging in.");
		}
	}

	//shows the menu depending on the role the client holds
	private   void showFinalMenu(String role, String userName)
			throws AirlineException {

		if ("Admin".equals(role)) {
			int option = 0;
			try {
				do {
					option = showAdminMenu(option);
					switch (option) {
					case 1:
						insertFlightDetails();
						break;
					case 2:
						updateFlightDetails();
						break;
					case 3:
						cancelFlightDetails();
						break;
					case 4:
						viewFlightDetails();
						break;
					case 5:
						searchFlightDetailsById();
						break;
					case 6:
						searchFlightDetailsByPlace();
						break;
					case 7:
						logOut();
						break;
					default:
						System.err
						.println("Wrong option entered... kindly enter choice (1-7) only");
						break;
					}
				} while (true);
			} catch (InputMismatchException e) {
				System.err.println("Input should be only Numbers ");
			}
		} else if ("Executive".equals(role)) {
			int option = 0;
			try {
				do {
					option = showExecutiveMenu(option);
					switch (option) {
					case 1:
						displayFlightDetails();
						break;
					case 2:
						displayFlightOccupancyByPeriod();
						break;
					case 3:
						displayFlightOccupancyByPlace();
						break;
					case 4:
						logOut();
						break;
					default:
						System.err
						.println("Wrong option entered... kindly enter choice (1-4) only");
						break;
					}
				} while (true);
			} catch (InputMismatchException e) {
				System.err.println("Input should be only Numbers ");
			}
		} else if ("User".equals(role)) {
			int option = 0;
			try {
				// crud
				do {
					option = showUserMenu(option);
					switch (option) {
					case 1:
						bookFlight(userName);
						break;
					case 2:
						viewFlightStatus(userName);
						break;
					case 3:
						updateFlight(userName);
						break;
					case 4:
						cancelFlight(userName);
						break;
					case 5:
						searchFlightDetailsByPlace();
						break;
					case 6:
						logOut();
						break;
					default:
						System.err
						.println("Wrong option entered... kindly enter choice (1-6) only");
						break;
					}
				} while (true);

			} catch (InputMismatchException e) {
				System.err.println("Input should be only Numbers ");
			}
		}
	}

	private   void logOut() {
		System.out
		.println("\n__________________________________________\n");
		System.out.println("	You have successfully logged out!!!");
		//myLogger.info("User successfully logged out!!!");
		String[] args = { "\0" };
		main(args);

	}

	private   void cancelFlight(String userName) {
		System.out
		.println("\n__________________________________________\n");
		int bookId = 0;
		try {
			while (true) // while
			{
				System.out.print("	Enter Booking ID to cancel : ");
				String id = scanner.nextLine();
				if (valid.validateNumber(id)) {
					bookId = Integer.parseInt(id);
					break;
				} else
					System.err.println("Input should be only Numbers and atleast 4 digits");
			} // End Of While
			if (userService.viewFlightStatus(bookId, userName).getFlightId() != null) {
				int status = userService.cancelFlight(bookId, userName);
				if (status > 0) {
					System.out.println("	Booking " + bookId + " cancelled.");
					//myLogger.info("Booking " + bookId + " cancelled.");
				}

				else {
					//myLogger.error("Booking " + bookId + " Does Not Exist!!");
					System.err.println("Booking " + bookId
							+ " Does Not exist!!");
				}
			} else {
				//myLogger.error("Booking " + bookId + " Does Not Exist!!");
				System.err.println("Booking " + bookId + " Does Not exist!!");
			}
		} catch (AirlineException e) {
			//myLogger.error("Something went wrong...while Searching Record..."
				//	+ e.getMessage());
			System.err
			.println("Something went wrong...while Searching Record..."
					+ e.getMessage());
		}

	}

	private   void updateFlight(String userName) {
		System.out
		.println("\n__________________________________________\n");
		int bookId = 0;
		int flag = 0;
		int number = 0;
		try {
			while (true) // While-1
			{
				System.out.print("	Enter Booking ID to update : ");
				String id = scanner.nextLine();
				if (valid.validateNumber(id)) {
					bookId = Integer.parseInt(id);
					break;
				} else
					System.err.println("Input should be only Numbers and minimum 4 digits");

			} // End of While-1
			BookingInformation book = userService.viewFlightStatus(bookId,
					userName);
			if (book.getFlightId() != null) {
				while (true) // while-2
				{
					int avlSeats;
					while (true) // while-2.1
					{
						avlSeats = userService.getNoOfSeat(book.getClassType(),
								book.getFlightId());
						System.out
						.println("	Current Available Seats in Flight "
								+ book.getFlightId() + " of "
								+ book.getClassType() + " CLASS is : "
								+ avlSeats);
						System.out
						.print("	Enter Updated Number of Passengers : ");
						String noPass = scanner.nextLine();
						if (valid.validateInputNumber(noPass)) {
							number = Integer.parseInt(noPass);
							break;
						} else
							System.err.println("Input should be only Numbers ");
					} // End Of while-2.1
					if (number <= avlSeats) {
						flag = userService.updateFlight(adminService.searchFlightDetailsById(book.getFlightId()),number, bookId,
									userName);
						if (flag != 0) {
							System.out
							.println("Number of Passengers for Booking Id "
									+ bookId + " is updated.");
							//myLogger.info("Number of Passengers for Booking Id"
								//	+ bookId + "is updated.");
							break;
						} else {
							//myLogger.error("Booking for Booking Id " + bookId
								//	+ " Does Not Exist!!");
							System.err.println("Booking for Booking Id "
									+ bookId + " Does Not Exist!!");
						}
					} else
						System.err
						.println("Enter Number of Passengers less than or equal to Available Seats");
				} // End Of while-2
			} else {
				//myLogger.error("Booking " + bookId + " Does Not Exist!!");
				System.err.println("Booking " + bookId + " Does Not Exist!!");
			}
		} catch (AirlineException e) {
			//myLogger.error("Something went wrong..."
				//	+ "while Searching Record..." + e.getMessage());

			System.err.println("Something went wrong..."
					+ "while Searching Record..." + e.getMessage());
		}

	}

	private   void viewFlightStatus(String userName) {
		System.out
		.println("\n__________________________________________\n");
		int bookId = 0;
		BookingInformation booking = new BookingInformation();
		while (true) {				//while
			System.out.print("	Enter Booking ID to view Status : ");
			String id = scanner.nextLine();
			if (valid.validateNumber(id)) {
				bookId = Integer.parseInt(id);
				break;
			} else {
				System.err.println("Input should be only Numbers  and atleast 4 digits ");
			}

		} // End OF while
		try {
			booking = userService.viewFlightStatus(bookId, userName);
			if (booking.getFlightId() != null)
				booking.print();
			else {
				System.err.println("Booking Does Not Exist!!");
			}

		} catch (AirlineException e) {
			//myLogger.error("Something went wrong..."
				//	+ "while Searching Record..." + e.getMessage());

			System.err.println("Something went wrong..."
					+ "while Searching Record..." + e.getMessage());
		}
	}

	private   void bookFlight(String userName)  {
		System.out
		.println("\n__________________________________________\n");
		/*
		 * private String bookingId; private String custEmail; private int
		 * noOfPassenger; private String classType; private double totalFare;
		 * private int seatNumber; private String creaditCardInfo; private
		 * String srcCity; private String destCity;
		 */
		List<FlightInformation> flightList;
		String deptCity, arrCity;
		try{
			while(true){//while-0
				while (true) { // while-1
					while (true) { // while-1.1
						System.out.print("	Enter Departure City : ");
						deptCity = scanner.nextLine().toUpperCase();
						if (valid.validatePlaceName(deptCity)) {

							break;
						} else {
							System.err
							.println("Departure City must be characters only, Try Again");
						}
					} // end of while-1.1

					while (true) { // while-1.2
						System.out.print("	Enter Arrival City : ");
						arrCity = scanner.nextLine().toUpperCase();
						if (valid.validatePlaceName(arrCity)) {

							break;
						} else {
							System.err
							.println("Arrival City must be characters only, Try Again");
						}
					} // end of while-1.2
					if (valid.validateSamePlace(deptCity, arrCity)) {
						flight.setDepartureCity(deptCity);
						flight.setArrivalCity(arrCity);
						break;
					} else {
						System.err
						.println("Departure City can not be same as Arrival City, Try Again");

					}
				}// end of while-1
				try {
					flightList = userService.viewFlightDetail(
							deptCity, arrCity);
					if (flightList.size() != 0) {
						for (FlightInformation flightInfo : flightList)
							flightInfo.print();
						break;
					} else {
						System.err.println("No flight found between " + deptCity
								+ " and " + arrCity + ".");
					}
				} catch (AirlineException e) {
					//myLogger.error("Something went wrong..."
						//	+ "while Searching Record..." + e.getMessage());

					System.err.println("Something went wrong..."
							+ "while Searching Record..." + e.getMessage());
				}
			}//while-0
			String classType, flightId;
			while (true) // while-2
			{
				while (true) { // while-2.1
					System.out.print("	Enter Flight Number : ");
					flightId = scanner.nextLine();
					if (valid.validateFlightNo(flightId)) {
						break;
					} else {
						System.err
						.println("Flight Number must be in specified format,e.g ABC123, Try Again");
					}
				} // End Of while-2.1
				if (!adminService.availableFlightNo(flightId)) {
					int status = 0;
					for (FlightInformation flightInfo : flightList) {
						if (flightInfo.getFlightNumber().equals(flightId)) {
							status = 1;
						}
					}
					if (status == 0) {
						System.err
						.println("Flight with flight number "
								+ flightId
								+ ", does not exist between "
								+ deptCity + " and " + arrCity
								+ ", try again!!!");
					} else
						break;
				} else {
					System.err.println("Flight with flight number " + flightId
							+ ", does not exist, try again!!!");
				}
			} // end of while-2

			while (true) {
				System.out.print("	Enter Class(FIRST OR BUSINESS) : ");
				classType = scanner.nextLine().toUpperCase(); // while-3
				if ("FIRST".equals(classType) || "BUSINESS".equals(classType)) {
					break;
				} else {
					System.err
					.println("Class Type must be FIRST or BUSINESS only, Try Again");
				}
			} // End Of While-3

			while (true) { // While-4
				int num = 0;
				while (true) { // while-4.1
					System.out.print("	Enter Number of Passengers : ");
					String noPass = scanner.nextLine();
					if (valid.validateInputNumber(noPass)) {
						num = Integer.parseInt(noPass);
						break;
					} else {
						System.err
						.println("Please Insert only numbers and minimum 1 passenger, try again!!!");
					}
				} // End Of while4.1
				if (num > userService.getNoOfSeat(classType, flightId)) {
					System.err
					.println("No of Passengers cannot exceed the Available Seats");
				} else {
					double price = 0;
					for (FlightInformation flightInfo : flightList) {
						if (flightInfo.getFlightNumber().equals(flightId)) {
							if (classType.equals("FIRST"))
								price = num * flightInfo.getFirstClassFare();
							else
								price = num * flightInfo.getBusinessClassFare();
							break;
						}
					}
					System.out.println("	Total Price for your booking is : "
							+ price);
					//myLogger.info("Total Price for " + loginService.getName(userName) + " is : " + price);
					String credit;
					while (true) // while-4.2
					{
						System.out.print("	Enter Credit Card Information : ");
						credit = scanner.nextLine();
						if (valid.validateCreditCard(credit))
							break;
						else {
							System.err
							.println("Credit Card Info must be 16-digits, Try again!!!");
						//	myLogger.error("Credit Card Info must be 16-digits, Try again!!!");
						}
					} // End Of while-4.2
					BookingInformation book = new BookingInformation();
					book.setCustEmail(userName);
					book.setClassType(classType);
					book.setFlightId(flightId);
					book.setDestinationCity(arrCity);
					book.setSourceCity(deptCity);
					book.setNoOfPassenger(num);
					book.setTotalFare(price);
					book.setCreditCardInfo(credit);
					int bookingId = userService.bookFlight(book);
					if (bookingId > 0) {
						FlightInformation flight=adminService.searchFlightDetailsById(flightId);
						System.out.println("\n\n\n************************************************************************************************\n\nTicket Details\n"
								+ "\n************************************************************************************************\n\n\t\t\t\t"+flight.getAirlineName().toUpperCase()
								+ "\n\nFlight Number			Destination			Source			Booking ID\n"
								+ flightId+"				"+arrCity.toUpperCase()+"				"+ deptCity.toUpperCase()+"			"+bookingId
								+"\n\nDeparture Date		Departure Time		Arrival Date		Arrival Time\n"
								+flight.getDepartureDate()+"			"+flight.getDepartureTime()+"				"+flight.getArrivalDate()+"		"+flight.getArrivalTime()
								+ "\n\nClass				Number of Passengers	Customer Name	\n"
								+ classType+"				"+num+"					"+loginService.getName(userName)
								+ "\n\nTotal price paid : "+price);
						System.out.println("\n************************************************************************************************\n\n***** Happy Journey *****\n\n");
				//		myLogger.info("Booking Successful with Unique Id "
				//				+ bookingId);
						break;
					} else {
					//	myLogger.error("Booking Unsuccessful!!! Please try again!!!");
						System.err.println("Booking Unsuccessful!!!");
					}

				}

			} // End Of While-4
		}catch(AirlineException e){
			//myLogger.error("Some Error Occurred!! Please Try again!!");
			System.err.println("Some Error Occurred!! Please Try again!!");
		}

	}

	private   void displayFlightOccupancyByPlace() {
		System.out
		.println("\n__________________________________________\n");
		String source, destination;
		List<FlightInformation> flightList;
		try {
			while (true) { // While-1
				while (true) { // while-1.1
					System.out.print("	Enter Source City : ");
					source = scanner.nextLine().toUpperCase();
					if (valid.validatePlaceName(source)) {
						flight.setDepartureCity(source);
						break;
					} else {
						System.err
						.println("Source City must be in characters only!! Try Again");
					}
				} // End Of While-1.1

				while (true) { // while-1.2
					System.out.print("	Enter Destination City : ");
					destination = scanner.nextLine().toUpperCase();
					if (valid.validatePlaceName(destination)) {
						flight.setArrivalCity(destination);
						break;
					} else {
						System.err
						.println("Destination City must be in characters only!! Try Again");
					}
				} // End Of While-1.2
				if (valid.validateSamePlace(source, destination)) {
					flight.setDepartureCity(source);
					flight.setArrivalCity(destination);
					break;
				} else {
					System.err
					.println("Destination City must not be same as Source City, Try Again");

				}
			} // End of While-1
			flightList = executiveService.displayFlightOccupancyByPlace(source,
					destination);
			if (flightList.size() != 0) {
				System.out
				.println("\n__________________________________________\n");
				for (FlightInformation flightInfo : flightList) {
					// flightno,airline,FirstSeats,BussSeat
					System.out.println("	Flight Number : "
							+ flightInfo.getFlightNumber() + "\n	Airline : "
							+ flightInfo.getAirlineName()
							+ "\n	Departure Date : "+flightInfo.getDepartureDate()
							+ "\n	Departure Time : "+flightInfo.getDepartureTime()
							+ "\n	Arrival Date : "+flightInfo.getArrivalDate()
							+ "\n	Arrival Time : "+flightInfo.getArrivalTime()
							+ "\n	First Class Seats : "
							+ flightInfo.getFirstClassSeats()
							+ "\n	Business Class Seats : "
							+ flightInfo.getBusinessClassSeats());

					System.out
					.println("\n__________________________________________\n");
				}
			} else {
				//myLogger.error("No Flight Exists!!");
				System.err.println("No Flight Exists between "+source+" and "+destination+"!!");
			}
		} catch (AirlineException e) {
		//	myLogger.error("Something went wrong..."
		//			+ "while Searching Record..." + e.getMessage());

			System.err.println("Something went wrong..."
					+ "while Searching Record..." + e.getMessage());
		}

	}

	private   void displayFlightOccupancyByPeriod() {
		System.out
		.println("\n__________________________________________\n");
		LocalDate to, from;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		List<FlightInformation> flightList;
		try {
			while (true) { // while-1

				while (true) { // While-1.1
					System.out.print("	Enter Starting Date : ");
					String deptDate = scanner.nextLine();
					try {
						if (valid.validateDate(deptDate)) {

							from = LocalDate.parse(deptDate, formatter);
							// flight.setDepartureDate(depDate);
							break;
						} else {
							System.err
							.println("Please Enter Correct Date of Departure."
									+ " Date Must be in (dd/MM/yyyy) format "
									+ " and it cannot be less than the today");

						}
					} catch (AirlineException e) {
						System.err
						.println("Please Enter Correct Date of Departure"
								+ " Date Must be in (dd/MM/yyyy) format "
								+ " and it cannot be less than the today");
					}
				} // End of While-1.1

				while (true) { // while-1.2
					System.out.print("	Enter Ending Date : ");
					String arrvDate = scanner.nextLine();
					try {
						if (valid.validateDate(arrvDate)) {
							to = LocalDate.parse(arrvDate, formatter);
							// flight.setArrivalDate(arrDate);
							break;
						} else {
							System.err
							.println("Please Enter Correct Date of arrival"
									+ " Date Must be in (dd/MM/yyyy) format "
									+ " and it cannot be less than the today");
						}
					} catch (AirlineException e) {
						System.err
						.println("Please Enter Correct Date of arrival"
								+ " Date Must be in (dd/MM/yyyy) format "
								+ " and it cannot be less than the today");
					}
				} // End of While-1.2

				if (valid.validatePeriod(from, to)) {
					flight.setDepartureDate(from);
					flight.setArrivalDate(to);
					break;
				} else {
					System.err
					.println("Please enter Correct Date\nStarting Date cannot be less than Ending");

				}
			} // End OF While-1
			flightList = executiveService.displayFlightOccupancyByPeriod(from,
					to);
			if (flightList.size() != 0) {
				System.out
				.println("\n__________________________________________\n");
				for (FlightInformation flightInfo : flightList) {

					System.out.println("	Flight Number : "
							+ flightInfo.getFlightNumber() + "\n	Airline : "
							+ flightInfo.getAirlineName()
							+ "\n	Departure City : "+flightInfo.getDepartureCity()
							+ "\n	Arrival City : "+flightInfo.getArrivalCity()
							+ "\n	Departure Date : "+flightInfo.getDepartureDate()
							+ "\n	Departure Time : "+flightInfo.getDepartureTime()
							+ "\n	Arrival Date : "+flightInfo.getArrivalDate()
							+ "\n	Arrival Time : "+flightInfo.getArrivalTime()
							+ "\n	First Class Seats : "
							+ flightInfo.getFirstClassSeats()
							+ "\n	Business Class Seats : "
							+ flightInfo.getBusinessClassSeats());
					System.out
					.println("\n__________________________________________\n");
				}
			} else {
			//	myLogger.error("No flight exists between "+to+" and "+from+"!!!");
				System.err.println("No flight exists between these days!!!");
			}
		} catch (AirlineException e) {
			//myLogger.error("Something went wrong..."
			//		+ "while Searching Record..." + e.getMessage());

			System.err.println("Something went wrong..."
					+ "while Searching Record..." + e.getMessage());
		}

	}

	private   void displayFlightDetails() {
		System.out
		.println("\n__________________________________________\n");
		List<FlightInformation> flightList;
		try {
			flightList = executiveService.displayFlightDetails();
			if (flightList.size() != 0) {
				for (FlightInformation flightInfo : flightList)
					flightInfo.print();
			} else {
				//myLogger.error("No flight exists in database!!!");
				System.err.println("No flight exists in database!!!");
			}
		} catch (AirlineException e) {
			//myLogger.error("Something went wrong..."
			//		+ "while Searching Record..." + e.getMessage());

			System.err.println("Something went wrong..."
					+ "while Searching Record..." + e.getMessage());
		}

	}

	private   void searchFlightDetailsByPlace() {
		System.out
		.println("\n__________________________________________\n");
		String deptCity, arrCity;
		List<FlightInformation> flightList;
		try {
			while (true) { // while-1
				while (true) { // while-1.1
					System.out.print("	Enter Departure City : ");
					deptCity = scanner.nextLine().toUpperCase();
					if (valid.validatePlaceName(deptCity)) {

						break;
					} else {
						System.err
						.println("Departure City must be in characters only!!!, Try Again");
					}
				} // End Of while-1.1

				while (true) { // while-1.2
					System.out.print("	Enter Arrival City : ");
					arrCity = scanner.nextLine().toUpperCase();
					if (valid.validatePlaceName(arrCity)) {

						break;
					} else {
						System.err
						.println("Arrival City must be in characters only!!, Try Again");
					}
				} // End Of while-1.2
				if (valid.validateSamePlace(deptCity, arrCity)) {
					flight.setDepartureCity(deptCity);
					flight.setArrivalCity(arrCity);
					break;
				} else {
					System.err
					.println("Arrival City must not be same as Departure City, Try Again");

				}
			} // end of while-1
			flightList = adminService.searchFlightDetailsByPlace(deptCity,
					arrCity);
			if (flightList.size() > 0) {
				System.out
				.println("\n__________________________________________\n");
				for (FlightInformation flightInfo : flightList)
					flightInfo.print();
			} else {
				System.err.println("No flight between these two cities!!!");
			}
		} catch (AirlineException e) {
		//	myLogger.error("Something went wrong..."
		//			+ "while Searching Record..." + e.getMessage());

			System.err.println("Something went wrong..."
					+ "while Searching Record..." + e.getMessage());
		}
	}

	private   void searchFlightDetailsById() {
		System.out
		.println("\n__________________________________________\n");
		String flightId = null;
		try {

			while (true) { // While-1
				System.out.print("	Enter Flight Number to be searched : ");
				flightId = scanner.nextLine();
				if (valid.validateFlightNo(flightId)) {
					// flight.setFlightNumber(flightNo);
					break;
				} else {
					System.err
					.println("Flight Number must be in specified format, e.g ABC123, Try Again");
				}
			} // End OF While-1
			flight = adminService.searchFlightDetailsById(flightId);
			flight.print();
		}catch(NullPointerException e)
		{
			System.err.println("Flight "+flightId+" Does Not Exist!!");
		}
		catch (AirlineException e) {
		//	myLogger.error("Something went wrong..."
		//			+ "while Searching Record..." + e.getMessage());

			System.err.println("Something went wrong..."
					+ "while Searching Record..." + e.getMessage());
		}

	}

	private   void viewFlightDetails() {
		System.out
		.println("\n__________________________________________\n");
		List<FlightInformation> flightList;
		try {
			flightList = adminService.viewFlightDetails();
			if (flightList.size() > 0) {
				for (FlightInformation flightInfo : flightList)
					flightInfo.print();
			} else {
			//	myLogger.error("No flight exists in database!!!");
				System.err.println("No flight exists in database!!!");
			}
		} catch (AirlineException e) {
		//	myLogger.error("Something went wrong..."
		//			+ "while Searching Record..." + e.getMessage());

			System.err.println("Something went wrong..."
					+ "while Searching Record..." + e.getMessage());
		}

	}

	private   void cancelFlightDetails() {
		System.out
		.println("\n__________________________________________\n");
		String flightId = null;
		int status = 0;
		try {
			while (true) { // While-1
				System.out.print("	Enter Flight Number  to cancel : ");
				flightId = scanner.nextLine();
				if (valid.validateFlightNo(flightId)) {

					break;
				} else {
					System.err
					.println("Flight Number must be in specified format, e.g ABC123, Try Again");
				}
			} // end of While-1
			status = adminService.cancelFlightDetails(flightId);
			if (status != 0)
				System.out.println("Flight " + flightId + " cancelled.");
			else {
			//	myLogger.error("Flight does Not Exist!!");
				System.err.println("Flight Does Not Exist!!");
			}
		} catch (AirlineException e) {
			//myLogger.error("Something went wrong..."
				//	+ "while Searching Record..." + e.getMessage());

			System.err.println("Something went wrong..."
					+ "while Searching Record..." + e.getMessage());
		}

	}

	private   void updateFlightDetails() {

		System.out
		.println("\n__________________________________________\n");
		while (true) { // While-1

			System.out.print("	Enter the Flight Number you want to update : ");
			String flightNo = scanner.nextLine();
			try{
				if (valid.validateFlightNo(flightNo)) {
					if (!adminService.availableFlightNo(flightNo)) {
						flight = adminService.searchFlightDetailsById(flightNo);
						System.out.println("	Flight Number : " + flightNo
								+ "\n	Airline : " + flight.getAirlineName()
								+ "\n	Departure City : "
								+ flight.getDepartureCity()
								+ "\n	Arrival City : " + flight.getArrivalCity()
								+ "\n	Departure Date : "
								+ flight.getDepartureDate());

						while (true) { // While-1.1
							LocalDate depDate, arrDate;
							String deptDate,arrvDate;
							while (true) { // while-1.1.1
								System.out.print("	Enter updated Departure Date : ");
								deptDate = scanner.nextLine();
								try {
									if (valid.validateDate(deptDate)) {
										DateTimeFormatter formatter = DateTimeFormatter
												.ofPattern("dd/MM/yyyy");
										depDate = LocalDate.parse(deptDate,
												formatter);
										break;
									} else {
										System.err
										.println("Please Enter Correct Date of departure"
												+ " Date Must be in (dd/MM/yyyy) format "
												+ " and it cannot be less than the today");
									}
								} catch (AirlineException e) {
									System.err
									.println("Please Enter Correct Date of departure"
											+ " Date Must be in (dd/MM/yyyy) format "
											+ " and it cannot be less than the today");
								}
							} // end of while1.1.1
							System.out.println("\n	Arrival Date : "
									+ flight.getArrivalDate());

							while (true) { // while-1.1.2
								System.out.print("	Enter updated Arrival Date : ");
								arrvDate = scanner.nextLine();
								try {
									if (valid.validateDate(arrvDate)) {
										DateTimeFormatter formatter = DateTimeFormatter
												.ofPattern("dd/MM/yyyy");
										arrDate = LocalDate.parse(arrvDate,
												formatter);
										break;
									} else {
										System.err
										.println("Please Enter Correct Date of arrival"
												+ " Date Must be in (dd/MM/yyyy) format "
												+ " and it cannot be less than the today");
									}
								} catch (AirlineException e) {
									System.err
									.println("Please Enter Correct Date of Arrival"
											+ " Date Must be in (dd/MM/yyyy) format "
											+ " and it cannot be less than the today");
								}
							} // End OF While-1.1.2

							if (valid.validatePeriod(depDate,
									arrDate)) {
								flight.setDepartureDate(depDate);
								flight.setArrivalDate(arrDate);
								break;
							} else {
								System.err
								.println("Please Enter Correct Date Arrival Date cannot be less than Departure Date");

							}
						} // End Of while-1.1
						while (true) { // while-2
							LocalTime depTime, arrTime;
							System.out.println("\n	Departure Time : "
									+ flight.getDepartureTime());

							while (true) { // while-2.1
								System.out.print("	Enter updated Departure Time : ");
								String deptTime = scanner.nextLine();
								try {
									if (valid.validateTime(deptTime)) {
										DateTimeFormatter formatter = DateTimeFormatter
												.ofPattern("HH:mm");
										depTime = LocalTime.parse(deptTime,
												formatter);

										break;
									} else {
										System.err
										.println("Please Enter Correct Time of Departure"
												+ " Time Must be in (hh:mm) format ");
									}
								} catch (AirlineException e) {
									System.err
									.println("**********Please Enter Correct Time of Arrival"
											+ " Time Must be in (hh:mm) format "
											+ "**********");
								}
							} // End Of While-2.1
							System.out.println("\n	Arrival Time : "
									+ flight.getArrivalTime());

							while (true) { // while-2.2
								System.out.print("	Enter Updated Arrival Time : ");
								String arrvTime = scanner.nextLine();
								try {
									if (valid.validateTime(arrvTime)) {
										DateTimeFormatter formatter = DateTimeFormatter
												.ofPattern("HH:mm");
										arrTime = LocalTime.parse(arrvTime,
												formatter);

										break;
									} else {
										System.err
										.println("Please Enter Correct Time of Arrival"
												+ " Time Must be in (hh:mm) format ");
									}
								} catch (AirlineException e) {
									System.err
									.println("Please Enter Correct Time of Arrival"
											+ " Time Must be in (hh:mm) format ");								}
							} // end of while-2.2
							if (valid.validateTimePeriod(
									flight.getDepartureDate(),
									flight.getArrivalDate(), depTime, arrTime)) {
								flight.setDepartureTime(depTime);
								flight.setArrivalTime(arrTime);
								break;
							} else {
								System.err
								.println("Please Enter Correct Time Arrival Time cannot be less than or equal to Departure Time");

							}
						} // End Of While-2
						break;
					} else {
						System.err
						.println("Flight Number does not Exist in Database!!!");
					}
				} else {
					System.err
					.println("Flight Number must be in specified format, e.g ABC123 \nAnd Available in Database to update, Try Again");
				}
			} catch (AirlineException e) {
				System.err.println("Flight Number must be in specified format \nAnd Available in Database to update, Try Again");
			}
		} // End OF While-1
		try{
			int status = adminService.updateFlightDetails(flight);
			if (status > 0) {
				System.out.println("Flight Details updated for Flight Number " +flight.getFlightNumber()+"!!!");
			//	myLogger.info("Flight Details updated for Flight Number " +flight.getFlightNumber()+"!!!");
			} else {
			//	myLogger.info("Something went wrong..."
				//		+ "while Fetching all Record...");
				System.err.println("Something went wrong..."
						+ "while Fetching all Record...");
			}
		} catch (AirlineException e) {
		//	myLogger.info("Something went wrong..."
		//			+ "while Fetching all Record..." + e.getMessage());
			System.err.println("Something went wrong..."
					+ "while Fetching all Record..." + e.getMessage());
		}



	}

	private   void insertFlightDetails() {
		/*
		 * private String flightNumber; private String airlineName;
		 * validatePlaceName private String departureCity; private String
		 * arrivalCity; private LocalDate departureDate; private LocalDate
		 * arrivalDate; private LocalTime departureTime; private LocalTime
		 * arrivalTime; private int firstClassSeats; private double
		 * firstClassFare; private int businessClassSeats; private double
		 * businessClassFare;
		 */
		System.out
		.println("\n__________________________________________\n");
		String flightNo;
		try{
			while (true) { // while-1

				System.out.print("	Enter Flight Number : ");
				flightNo = scanner.nextLine();
				if (valid.validateFlightNo(flightNo)
						&& adminService.availableFlightNo(flightNo)) {
					flight.setFlightNumber(flightNo);
					break;
				} else {
					System.err
					.println("Please Flight Number must be in specified format, e.g ABC123, Try Again");
				}
			} // end of while-1
			while (true) { // while-2
				System.out.print("	Enter Airline Name : ");
				String airline = scanner.nextLine();
				if (valid.validatePlaceName(airline)) {
					flight.setAirlineName(airline);
					break;
				} else {
					System.err
					.println("Airline must be in characters only!!, Try Again");
				}
			} // end of while-2
			while (true) { // while-3
				String deptCity,arrCity;
				while (true) { // while-3.1
					System.out.print("	Enter Departure City : ");
					deptCity = scanner.nextLine().toUpperCase();
					if (valid.validatePlaceName(deptCity)) {
						flight.setDepartureCity(deptCity);
						break;
					} else {
						System.err
						.println("Departure City must be in characters only!!, Try Again");
					}
				} // End Of While-3.1

				while (true) { // while-3.2
					System.out.print("	Enter Arrival City : ");
					arrCity = scanner.nextLine().toUpperCase();
					if (valid.validatePlaceName(arrCity)) {
						flight.setArrivalCity(arrCity);
						break;
					} else {
						System.err
						.println("Arrival City must be in characters only!! Try Again");
					}
				} // End Of While-3.2
				if (valid.validateSamePlace(deptCity, arrCity)) {
					flight.setDepartureCity(deptCity);
					flight.setArrivalCity(arrCity);
					break;
				} else {
					System.err
					.println("Arrival City must not be same as Departure City, Try Again");

				}
			} // end of while-3

			while (true) { // while-4
				LocalDate depDate, arrDate;

				while (true) { // while-4.1
					System.out.print("	Enter Departure Date : ");
					String deptDate = scanner.nextLine();
					try {
						if (valid.validateDate(deptDate)) {
							DateTimeFormatter formatter = DateTimeFormatter
									.ofPattern("dd/MM/yyyy");
							depDate = LocalDate.parse(deptDate, formatter);

							break;
						} else {
							System.err
							.println("Please Enter Correct Date of Departure."
									+ " Date Must be in (dd/MM/yyyy) format "
									+ " and it cannot be less than the today");

						}
					} catch (AirlineException e) {
						System.err.println("Please Enter Correct Date of departure"
								+ " Date Must be in (dd/MM/yyyy) format "
								+ " and it cannot be less than the today");
			//			myLogger.error("Please Enter Correct Date of departure"
			//					+ " Date Must be in (dd/MM/yyyy) format "
			//					+ " and it cannot be less than the today");

					}
				} // end of while-4.1

				while (true) { // while-4.2
					System.out.print("	Enter Arrival Date : ");
					String arrvDate = scanner.nextLine();
					try {
						if (valid.validateDate(arrvDate)) {
							DateTimeFormatter formatter = DateTimeFormatter
									.ofPattern("dd/MM/yyyy");
							arrDate = LocalDate.parse(arrvDate, formatter);

							break;
						} else {
							System.err
							.println("Please Enter Correct Date of arrival"
									+ " Date Must be in (dd/MM/yyyy) format "
									+ " and it cannot be less than the today");
						}
					} catch (AirlineException e) {
						System.err.println("Please Enter Correct Date of Arrival"
								+ " Date Must be in (dd/MM/yyyy) format "
								+ " and it cannot be less than the today");
				//		myLogger.error("Please Enter Correct Date of Arrival"
				//				+ " Date Must be in (dd/MM/yyyy) format "
				//				+ " and it cannot be less than the today");
					}
				} // end of while-4.2

				if (valid.validatePeriod(depDate, arrDate)) {
					flight.setDepartureDate(depDate);
					flight.setArrivalDate(arrDate);
					break;
				} else {
					System.err
					.println("Please Enter Correct Date Arrival Date cannot be less than Departure Date");

				}
			} // end of while-4
			LocalTime depTime, arrTime;
			while (true) {//while-5

				while (true) { // while-5.1
					System.out.print("	Enter Departure Time : ");
					String deptTime = scanner.nextLine();
					try {
						if (valid.validateTime(deptTime)) {
							DateTimeFormatter formatter = DateTimeFormatter
									.ofPattern("HH:mm");
							depTime = LocalTime.parse(deptTime, formatter);

							break;
						} else {
							System.err
							.println("Please Enter Correct Time of Departure"
									+ " Time Must be in (hh:mm) format ");
						}
					} catch (AirlineException e) {
						System.err.println("Please Enter Correct Time of Arrival"
								+ " Time Must be in (hh:mm) format ");
					//	myLogger.error("Please Enter Correct Time of Arrival"
				//				+ " Time Must be in (hh:mm) format ");
					}
				} // end of while-5.1

				while (true) { // while-5.2
					System.out.print("	Enter Arrival Time : ");
					String arrvTime = scanner.nextLine();
					try {
						if (valid.validateTime(arrvTime)) {
							DateTimeFormatter formatter = DateTimeFormatter
									.ofPattern("HH:mm");
							arrTime = LocalTime.parse(arrvTime, formatter);

							break;
						} else {
							System.err
							.println("Please Enter Correct Time of Arrival"
									+ " Time Must be in (hh:mm) format ");
						}
					} catch (AirlineException e) {
						System.err.println("Please Enter Correct Time of arrival"
								+ " Time Must be in (hh:mm) format ");
					}
				} // end of while-5.2

				if (valid.validateTimePeriod(flight.getDepartureDate(),
						flight.getArrivalDate(), depTime, arrTime)) {
					flight.setDepartureTime(depTime);
					flight.setArrivalTime(arrTime);
					break;
				} else {
					System.err
					.println("Please Enter Correct Time, Arrival Time cannot be less than Departure Time");
				}
			}//end of while-5
			String firstSeats;

			while (true) { // while-6
				System.out.print("	Enter number of First Class Seats : ");
				firstSeats = scanner.nextLine();
				if (valid.validateSeats(firstSeats)) {
					flight.setFirstClassSeats(Integer.parseInt(firstSeats));
					break;
				} else {
					System.err
					.println("Seat Number must be in numbers only, Try Again");
				}
			} // end of while-6
			while (true) { // /while-7
				System.out.print("	Enter price of First Class Seats : ");
				String firstSeatPrice = scanner.nextLine();
				if (valid.validateSeatPrice(firstSeatPrice)) {
					flight.setFirstClassFare(Double.parseDouble(firstSeatPrice));
					break;
				} else {
					System.err.println("Price must be in numbers only, Try Again");
				}
			} // end of while-7
			while (true) { // while-8
				System.out.print("	Enter number of Business Class Seats : ");
				String businessSeats = scanner.nextLine();
				if (valid.validateSeats(businessSeats)) {
					flight.setBusinessClassSeats(Integer.parseInt(businessSeats));
					break;
				} else {
					System.err
					.println("Seat Number must be in numbers only, Try Again");
				}
			} // end of while-8
			while (true) { //while-9
				System.out.print("	Enter price of Business Class Seats : ");
				String pbSeats = scanner.nextLine();// while-10
				if (valid.validateSeatPrice(pbSeats)) {
					flight.setBusinessClassFare(Double.parseDouble(pbSeats));
					break;
				} else {
					System.err.println("Price must be in numbers only, Try Again");
				}
			} // end of while-9
			int status = adminService.insertFlightDetails(flight);
			if (status > 0) {
				System.out
				.println("Flight Information Inserted Successfully!!!");
		//		myLogger.info("Flight Information of flight number : "+flightNo+"  Inserted Successfully!!!");
			} else
				System.err.println("Some Error occured!! try again!!!");
		//	myLogger.error("Some Error occured!! try again!!!");
		} catch (AirlineException e) {
			System.err.println("Some Error occured!! try again!!!");
			//myLogger.error("Some Error occured!! try again!!!");
		}

	}

	private   int showUserMenu(int option) {
		while (true) // while
		{
			System.out
			.print("\n__________________________________________\n"
					+ "\n	Airline Reservation Application\n"
					+ "\n__________________________________________\n"

							+ "\n	Enter your Choice (1-6)\n"
							+ "\n	1. Book Flight Ticket"
							+ "\n	2. Display Flight Status"
							+ "\n	3. Update Flight Reservation"
							+ "\n	4. Cancel Flight Reservation"
							+ "\n	5. View All Flight Details"
							+ "\n	6. Log Out"
							+ "\n__________________________________________\n	Enter your Choice :");

			String opt = scanner.nextLine();
			if (valid.validateInputNumber(opt)) {
				option = Integer.parseInt(opt);
				break;
			} else {
				System.err.println("Input should be only Numbers and between (1-6) only, Try again!!!");
			}
		} // end of while
		return option;
	}

	private   int showExecutiveMenu(int option) {
		while (true) { // while
			System.out
			.print("\n__________________________________________\n"

							+ "\n	Airline Reservation Application\n"
							+ "\n__________________________________________\n"

							+ "\n	Enter your Choice (1-4)\n"
							+ "\n	1. Display Flight Details"
							+ "\n	2. Display FlightOccupancy By Period"
							+ "\n	3. Display FlightOccupancy By Place"
							+ "\n	4. Log Out"
							+ "\n__________________________________________\n	Enter your Choice :");
			String opt = scanner.nextLine();
			if (valid.validateInputNumber(opt)) {
				option = Integer.parseInt(opt);
				break;
			} else {
				System.err.println("Input should be only Numbers and between (1-4) only, Try again!!!");
			}
		} // end of while

		return option;
	}

	private   void exit() {
	//	myLogger.info("Ending Application at " + LocalDateTime.now());
		System.out.println("Thank you for using Airline Application!!!"
				+ " Do Visit Again!!!");
		scanner.close();
		System.exit(0);

	}

	private   int showAdminMenu(int option) {
		while (true) { // while
			System.out
			.print("\n__________________________________________\n"
					+ "\n	Airline Reservation Application\n"
					+ "\n__________________________________________\n"

							+ "\n	Enter your Choice (1-7)\n"
							+ "\n	1. Insert Flight Details"
							+ "\n	2. Update Flight Details"
							+ "\n	3. Cancel Flight Details"
							+ "\n	4. View Flight Details"
							+ "\n	5. Search Flight Details By FlightId"
							+ "\n	6. Search Flight Details By Place"
							+ "\n	7. Log Out"
							+ "\n__________________________________________\n	Enter your Choice :");
			String opt = scanner.nextLine();
			if (valid.validateInputNumber(opt)) {
				option = Integer.parseInt(opt);
				break;
			} else {
				System.err.println("Input should be only Numbers and between (1-7) only, Try again!!!");
			}
		} // end of while
		return option;
	}

}
