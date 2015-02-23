import java.util.Scanner;
import java.sql.*;

public class FacutlyOperations {
	
	public static void main() throws Exception{
		//connect to the database
		Connection db_conn = DriverManager.getConnection("jdbc:sqlite:UnivDB.db");
		//create a new scanner to take input from the user.
		Scanner S = new Scanner(System.in);
		
		while(true){
			//print out the user prompt.
			System.out.print("input format -\n" +
				"BACK (back to main menu)\n" +
				"EXIT (quit the program)\n" +
				"ADD FID, Firstname, Middlname, Lastname, officeBuilding, officeRoom, Rank, Salary, PrimaryDept (add a faculty member to the db)\n" +
				"REMOVE FID (remove a faculty member from the db)\n" +
				"FIND FID (return the faculty members information)\n" +
				"SCHEDULE FID (returns the faculty members class information)\n" +
				"GRANTS FID (returns all grants associated with the faculty member\n");
			System.out.print("FAC-> Enter Command: ");
			//take the first string of the users input.
			String input3 = S.next();
			
			if(input3.equals("BACK")){
				//return to main menu.
				return;
			}else if(input3.equals("EXIT")){
				//end program
				System.exit(0);
			}else if(input3.equals("ADD")){
				//add a new faculty member to the db.
				//takes FID, Firstname, Middlname, Lastname, officeBuilding, officeRoom, Rank, Salary, PrimaryDept.
				String Query = "INSERT INTO Faculty VALUES (" + S.nextLine() + ")";
				Statement stmt = db_conn.createStatement();
				stmt.executeUpdate(Query);
			}else if(input3.equals("FIND")){
				//find the information for a faculty member.
				//takes FID.
				String Query = "SELECT * FROM Faculty WHERE FID =" + S.nextLine();
				Statement stmt = db_conn.createStatement();
				ResultSet rs = stmt.executeQuery(Query);
				ResultSetMetaData MData = rs.getMetaData();
				int col = MData.getColumnCount();
				String row = "";
				while (rs.next())
			    {
					for(int i = 1; i <= col; i ++){
						row += rs.getString(i) + " ";
					}
			      System.out.println(row);
			      row = "";
			    }
			    rs.close();
			}else if(input3.equals("REMOVE")){
				//remove an existing faculty member
				//takes FID.
				String Query = "DELETE FROM Faculty WHERE SID = " + S.nextLine();
				Statement stmt = db_conn.createStatement();
				stmt.executeUpdate(Query);
			}else if(input3.equals("SCHEDULE")){
				//return the list of classes taught by the faculty member
				//takes FID.
				String Query = "SELECT courseNum, deptID,courseName, location, meetDay, meetTime  FROM (Teaches NATURAL JOIN Course) WHERE FID =" + S.nextLine();
				Statement stmt = db_conn.createStatement();
				ResultSet rs = stmt.executeQuery(Query);
				ResultSetMetaData MData = rs.getMetaData();
				int col = MData.getColumnCount();
				String row = "";
				while (rs.next())
			    {
					for(int i = 1; i <= col; i ++){
						row += rs.getString(i) + " ";
					}
			      System.out.println(row);
			      row = "";
			    }
			    rs.close();
			}else if(input3.equals("GRANTS")){
				//returns the list of grants for the selected faculty member.
				//takes FID.
				String Query = "SELECT * FROM Grant WHERE FID =" + S.nextLine();
				Statement stmt = db_conn.createStatement();
				ResultSet rs = stmt.executeQuery(Query);
				ResultSetMetaData MData = rs.getMetaData();
				int col = MData.getColumnCount();
				String row = "";
				while (rs.next())
			    {
					for(int i = 1; i <= col; i ++){
						row += rs.getString(i) + " ";
					}
			      System.out.println(row);
			      row = "";
			    }
			    rs.close();
			}
		}
	}
}
