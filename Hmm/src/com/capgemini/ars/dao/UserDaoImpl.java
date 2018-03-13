/**
 * 
 */
package com.capgemini.ars.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.capgemini.ars.bean.BookingInformation;
import com.capgemini.ars.bean.FlightInformation;
import com.capgemini.ars.exception.AirlineException;



/**
 * @author suadhika
 *
 */
@Repository("UserDaoImpl")
public class UserDaoImpl implements UserDao{

	@PersistenceContext
	private EntityManager entityManager;

	public UserDaoImpl()
	{
		super();
	}

	public UserDaoImpl(EntityManager entityManager)
	{
		super();
		this.entityManager=entityManager;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	//bookFlight() allows the user to book a ticket
	@Override
	public int bookFlight(BookingInformation book) throws AirlineException {
		int status=1;
		try{
			entityManager.persist(book);
			updateSeats(getFlight(book.getFlightId()), book.getClassType(), book.getNoOfPassenger());
			status=book.getBookingId();
			entityManager.flush();
		}catch(Exception e)
		{
			status=0;
			throw new AirlineException("Insertion failed!!!! Please insert it again");
		}
		return status ;

	}

	private FlightInformation getFlight(String flightId) {
		FlightInformation flight=new FlightInformation();
		try{
			flight=entityManager.find(FlightInformation.class, flightId);
		}catch(NoResultException e)
		{
		}
		return flight;
	}

	//Seats will get automatically updated while booking a ticket
	private FlightInformation updateSeats(FlightInformation flight, String type,int noOfPassenger) throws AirlineException {

		if(type.equals("FIRST"))
		{
			flight.setFirstClassSeats(flight.getFirstClassSeats()-noOfPassenger);
		}
		else if(type.equals("BUSINESS"))
		{
			flight.setBusinessClassSeats(flight.getBusinessClassSeats()-noOfPassenger);
		}
		try {
			flight=entityManager.merge(flight);
		} catch (Exception e) {
			throw new AirlineException(e.getMessage());
		}
		return flight;
	}

	//We can find the available number of seats
	@Override
	public int getNoOfSeat(String type,String flightId) throws AirlineException {
		int no;
		String sql="";
		if(type.equals("FIRST"))
		{
			sql = "SELECT firstClassSeats FROM FlightInformation where flightNumber=:flightNo";
		}
		else if(type.equals("BUSINESS"))
		{
			sql = "SELECT businessClassSeats FROM FlightInformation where flightNumber=:flightNo";
		}
		else
		{
			return -1;
		}
		try {
			TypedQuery<Integer> query= entityManager.createQuery(sql,Integer.class);
			query.setParameter("flightNo",flightId);
			no=query.getSingleResult();

		}  catch (Exception e) {
			throw new AirlineException(e.getMessage());
		} 
		return no;
	}

	//Generates a unique booking Id
	private int getUniqueBookId() throws AirlineException {
		String sql = "SELECT BookingInformation_seq.currval FROM dual";
		int bookId = 0;
		try {
			TypedQuery<Integer> query=entityManager.createQuery(sql, Integer.class);
			bookId=query.getSingleResult();
		}  catch (Exception e) {
			throw new AirlineException(e.getMessage());
		}
		return bookId;
	}

	//It retrieves the booking status of a particular booking
	@Override
	public BookingInformation viewFlightStatus(int bookId,String userName) throws AirlineException {
		BookingInformation book = null;
		String sql = "SELECT book FROM BookingInformation book where bookingId=:bid and custEmail=:email";
		try {
			TypedQuery<BookingInformation> query=entityManager.createQuery(sql, BookingInformation.class);
			query.setParameter("bid", bookId);
			query.setParameter("email", userName);

			book=query.getSingleResult();
		}catch(NoResultException e)
		{
			book=new BookingInformation();
		}
		return book;
	}

	//User can update the number of passengers
	@Override
	public int updateFlight(FlightInformation flight, int noOfPassenger,int bookId,String userName) throws AirlineException {
		BookingInformation book=viewFlightStatus(bookId,userName);
		int result=0;
		if(book.getFlightId()!=null)
		{
			double price=(book.getTotalFare()*noOfPassenger/book.getNoOfPassenger());
			book.setTotalFare(price);
			int currNo=noOfPassenger-book.getNoOfPassenger();
			book.setNoOfPassenger(noOfPassenger);
			try{
				entityManager.merge(book);
				updateSeats(flight, book.getClassType(), currNo);
				result=1;
			}catch(Exception e)
			{
				throw new AirlineException();
			}
		}
		return result;
	}

	//Allows user to cancel a booking
	@Override
	public int cancelFlight(int bookId,String userName) throws AirlineException {
		int result=1;
		try{
			BookingInformation book=viewFlightStatus(bookId,userName);
			if(book.getFlightId()!=null)
			{
				try {
					entityManager.remove(book);
				} catch (Exception e) {
					throw new AirlineException();
				} 
			}
		}catch(NoResultException e){
			result=1;
		}
		return result;
	}

	//Retrieves all the flight details between two cities
	@Override
	public List<FlightInformation> viewFlightDetail(String src, String des)
			throws AirlineException {
		List<FlightInformation> list=null;
		String sql = "SELECT flights FROM FlightInformation flights where dep_city=:dep and arr_city=:arr";
		try {
			TypedQuery<FlightInformation> query=entityManager.createQuery(sql, FlightInformation.class);
			query.setParameter("dep",src);
			query.setParameter("arr", des);
			list=query.getResultList();
		}  catch (NoResultException e) {
			list=new ArrayList<>();
		} 
		return list;
	}


}
