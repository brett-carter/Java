/**
 * For CS351 Projedt 2
 * 
 * @author Your Name Here
 * 
 */

import java.sql.*;


public class UnivDB {

	public static void main(String args[]) throws Exception {
		Class.forName("org.sqlite.JDBC");
		
		//connect to my university file
		Connection db_conn = DriverManager.getConnection("jdbc:sqlite:test_database.db");

		//prepare an SQL statement
		Statement stmt = db_conn.createStatement();
		
		UniversityFunctions.main();
		
		//use executeUpdate() to run queries write involve making changes (writing) to the database
		//such as UPDATE, INSERT, DELETE, CREATE TABLE, DROP TABLE, etc
		stmt.executeUpdate("DROP TABLE IF EXISTS users;");
		stmt.executeUpdate("CREATE TABLE users (" +
				"ssn TEXT," +
				"name TEXT," +
				"PRIMARY KEY (ssn)" +
				");");

		stmt.executeUpdate("INSERT INTO users VALUES ('111-11-1111', 'Homer')");
		stmt.executeUpdate("INSERT INTO users VALUES ('222-22-2222', 'Marge')");
		stmt.executeUpdate("INSERT INTO users VALUES ('333-33-3333', 'Bart')");
		stmt.executeUpdate("INSERT INTO users VALUES ('444-44-4444', 'Lisa')");
		stmt.executeUpdate("INSERT INTO users VALUES ('555-55-5555', 'Maggie')");
		
		//use executeQuery() to run queries which involve reading from the database,
		//such as SELECT
	    ResultSet rs = stmt.executeQuery("SELECT * FROM users AS u1;");

	    //ResultSet is an iterator of tuples returned!
	    //Every .next() returns a new tuple
	    while (rs.next())
	    {
	      System.out.println("User name is " + rs.getString("name") + " and their SSN is " + rs.getString("ssn"));
	    }
	    rs.close();	//close result set

	    //close database statement
	    stmt.close();

	    
	    //close connection to university file
	    db_conn.close();
		}
}
