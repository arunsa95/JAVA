package com.capgemini.ars.bean;
import java.time.LocalDate;
import java.time.LocalTime;

public class FlightInformation {

	private String flightNumber;
	private String airlineName;
	private String departureCity;
	private String arrivalCity;
	private LocalDate departureDate;
	private LocalDate arrivalDate;
	private LocalTime departureTime;
	private LocalTime arrivalTime;
	private int firstClassSeats;
	private double firstClassFare;
	private int businessClassSeats;
	private double businessClassFare;

	public FlightInformation() {
		super();
	}

	public FlightInformation(String flightNumber, String airlineName,
			String departureCity, String arrivalCity, LocalDate departureDate,
			LocalDate arrivalDate, LocalTime departureTime,
			LocalTime arrivalTime, int firstClassSeats, double firstClassFare,
			int businessClassSeats, double businessClassFare) {
		super();
		this.flightNumber = flightNumber;
		this.airlineName = airlineName;
		this.departureCity = departureCity;
		this.arrivalCity = arrivalCity;
		this.departureDate = departureDate;
		this.arrivalDate = arrivalDate;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.firstClassSeats = firstClassSeats;
		this.firstClassFare = firstClassFare;
		this.businessClassSeats = businessClassSeats;
		this.businessClassFare = businessClassFare;
	}

	@Override
	public String toString() {
		return "FlightInformation [flightNumber=" + flightNumber
				+ ", airlineName=" + airlineName + ", departureCity="
				+ departureCity + ", arrivalCity=" + arrivalCity
				+ ", departureDate=" + departureDate + ", arrivalDate="
				+ arrivalDate + ", departureTime=" + departureTime
				+ ", arrivalTime=" + arrivalTime + ", firstClassSeats="
				+ firstClassSeats + ", firstClassFare=" + firstClassFare
				+ ", businessClassSeats=" + businessClassSeats
				+ ", businessClassFare=" + businessClassFare + "]";
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public String getAirlineName() {
		return airlineName;
	}

	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}

	public String getDepartureCity() {
		return departureCity;
	}

	public void setDepartureCity(String departureCity) {
		this.departureCity = departureCity;
	}

	public String getArrivalCity() {
		return arrivalCity;
	}

	public void setArrivalCity(String arrivalCity) {
		this.arrivalCity = arrivalCity;
	}

	public LocalDate getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(LocalDate departureDate) {
		this.departureDate = departureDate;
	}

	public LocalDate getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(LocalDate arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public LocalTime getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(LocalTime departureTime) {
		this.departureTime = departureTime;
	}

	public LocalTime getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(LocalTime arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public int getFirstClassSeats() {
		return firstClassSeats;
	}

	public void setFirstClassSeats(int firstClassSeats) {
		this.firstClassSeats = firstClassSeats;
	}

	public double getFirstClassFare() {
		return firstClassFare;
	}

	public void setFirstClassFare(double firstClassFare) {
		this.firstClassFare = firstClassFare;
	}

	public int getBusinessClassSeats() {
		return businessClassSeats;
	}

	public void setBusinessClassSeats(int businessClassSeats) {
		this.businessClassSeats = businessClassSeats;
	}

	public double getBusinessClassFare() {
		return businessClassFare;
	}

	public void setBusinessClassFare(double businessClassFare) {
		this.businessClassFare = businessClassFare;
	}

	public void print() {
		System.out.println("\n____________________________________________\n"
				+ "\n	Flight Number			:	"+flightNumber 
				+ "\n	Airline				:	"+airlineName
				+ "\n	Departure City			:	"+departureCity
				+ "\n	Arrival City			:	"+arrivalCity
				+ "\n	Departure Date		:	"+departureDate
				+ "\n	Arrival Date			:	" +arrivalDate
				+ "\n	Departure Time		:	"+departureTime
				+ "\n	Arrival Time			:	"+arrivalTime
				+ "\n	First Class Seats		:	"+firstClassSeats
				+ "\n	First Class Fare		:	"+firstClassFare
				+ "\n	Business Class Seats	:	"+businessClassSeats
				+ "\n	Business Class Fare		:	"+businessClassFare
				+ "\n____________________________________________\n");
	}


}
