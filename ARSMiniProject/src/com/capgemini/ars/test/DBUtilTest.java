package com.capgemini.ars.test;
import static org.junit.Assert.assertNotNull;

import java.sql.Connection;

import org.junit.BeforeClass;
import org.junit.Test;

import com.capgemini.ars.exception.AirlineException;
import com.capgemini.ars.util.DBUtil;

/**
 * @author Smita
 *
 */
public class DBUtilTest {
	 static DBUtil dbUtil ;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void testDBUtil() throws AirlineException{
		dbUtil= new DBUtil();
		assertNotNull("DBUtil Object Not created..."
				,dbUtil);
	}
	@Test
	public void testConnection() throws AirlineException {
		Connection conn = dbUtil.obtainConnection();
		assertNotNull("Connection NOT obtained!",conn);
		if(conn!=null)System.out.println("connection Obtained ...."+conn);
	}

}
