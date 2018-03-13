/**
 * 
 */
package com.capgemini.ars.bean;

/**
 * @author  suadhika
 * BOOKING_ID                                NOT NULL VARCHAR2(5)
 FLIGHT_ID                                           VARCHAR2(6)
 CUST_EMAIL                                         VARCHAR2(20)
 NO_OF_PASSENGER                                    NUMBER
 CLASS_TYPE                                         VARCHAR2(10)
 TOTAL_FARE                                         NUMBER(10,2)
 CREDITCARD_INFO                                    VARCHAR2(10)
 SRC_CITY                                           VARCHAR2(10)
 DEST_CITY                                          VARCHAR2(10)
 
 *
 */
public class BookingInformation {

	
	private int bookingId;
	private String flightId;
	private String custEmail;
	private int noOfPassenger;
	private String classType;
	private double totalFare;
	private String creditCardInfo;
	private String sourceCity;
	private String destinationCity;
	
	public BookingInformation(){
		
	}
	
	
	public BookingInformation(int bookingId, String flightId, String custEmail,
			int noOfPassenger, String classType, double totalFare,
			String creditCardInfo, String sourceCity,
			String destinationCity) {
		super();
		this.bookingId = bookingId;
		this.flightId = flightId;
		this.custEmail = custEmail;
		this.noOfPassenger = noOfPassenger;
		this.classType = classType;
		this.totalFare = totalFare;
		this.creditCardInfo = creditCardInfo;
		this.sourceCity = sourceCity;
		this.destinationCity = destinationCity;
	}


	public int getBookingId() {
		return bookingId;
	}


	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}


	public String getFlightId() {
		return flightId;
	}


	public void setFlightId(String flightId) {
		this.flightId = flightId;
	}


	public String getCustEmail() {
		return custEmail;
	}


	public void setCustEmail(String custEmail) {
		this.custEmail = custEmail;
	}


	public int getNoOfPassenger() {
		return noOfPassenger;
	}


	public void setNoOfPassenger(int noOfPassenger) {
		this.noOfPassenger = noOfPassenger;
	}


	public String getClassType() {
		return classType;
	}


	public void setClassType(String classType) {
		this.classType = classType;
	}


	public double getTotalFare() {
		return totalFare;
	}


	public void setTotalFare(double totalFare) {
		this.totalFare = totalFare;
	}


	


	public String getCreditCardInfo() {
		return creditCardInfo;
	}


	public void setCreditCardInfo(String creditCardInfo) {
		this.creditCardInfo = creditCardInfo;
	}


	public String getSourceCity() {
		return sourceCity;
	}


	public void setSourceCity(String sourceCity) {
		this.sourceCity = sourceCity;
	}


	public String getDestinationCity() {
		return destinationCity;
	}


	public void setDestinationCity(String destinationCity) {
		this.destinationCity = destinationCity;
	}


	public void print() {
		System.out.println("\n______________________________________________________________\n"
				+ "\n	Booking ID			:	"+bookingId
				+ "\n	Flight Number		:	"+flightId 
				+ "\n	Customer Email		:	"+custEmail
				+ "\n	Number of Passengers	:	"+noOfPassenger
				+ "\n	Class Type			:	"+classType
				+ "\n	Total Fare			:	"+totalFare
				+ "\n	Credit Card Info		:	"+creditCardInfo
				+ "\n	Source City			:	"+sourceCity
				+ "\n	Destination City		:	"+destinationCity
				+ "\n______________________________________________________________\n");
	}

}
