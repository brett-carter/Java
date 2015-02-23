import java.util.Scanner;
import java.sql.*;

public class StudentFunctions{
	
	public static void main()throws Exception{
		//connect to the DB.
		Connection db_conn = DriverManager.getConnection("jdbc:sqlite:UnivDB.db");
		//create a new scanner to take input from the user.
		Scanner S = new Scanner(System.in);
		
		while(true){
			System.out.print("input format - \n" +
					"BACK (back to main menu)\n" +
					"EXIT (quit the program)\n" +
					"ADD SID, studentName, major, class, gpa (add a student to the db)\n" +
					"REMOVE SID (remove a student from the db)\n" +
					"FIND SID (return the students information)\n" +
					"SCHEDULE SID (returns the students class information)\n" +
					"WITHDRAW SID courseNum (removes a student from an enrolled course)\n" +
					"ENROLL SID CourseNum (add the student to a course\n");
			//user prompt
			System.out.print("STU-> Enter Command: ");
			//take the first string of the users input.
			String input2 = S.next();
			
			if(input2.equals("BACK")){
				//return to main menu.
				return;
			}else if(input2.equals("EXIT")){
				//end program
				System.exit(0);
			}else if(input2.equals("ADD")){
				//add a new student
				//takes SID, studentName, major, class, gpa.
				String Query = "INSERT INTO Student VALUES (" + S.nextLine() + ")";
				Statement stmt = db_conn.createStatement();
				stmt.executeUpdate(Query);
			}else if(input2.equals("REMOVE")){
				//remove an existing student.
				//takes SID.
				String Query = "DELETE FROM Student WHERE SID = " + S.nextLine();
				Statement stmt = db_conn.createStatement();
				stmt.executeUpdate(Query);
			}else if(input2.equals("FIND")){
				//returns the information for the student with the provided SID.
				String Query = "SELECT * FROM Student WHERE SID =" + S.nextLine();
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
			}else if(input2.equals("SCHEDULE")){
				//returns all course information for classes taken by the student.
				//takes SID.
				String Query = "SELECT courseNum, deptID,courseName, location, meetDay, meetTime  FROM ((Student NATURAL JOIN Enroll) NATURAL JOIN Course) WHERE SID =" + S.nextLine();
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
			}else if(input2.equals("WITHDRAWL")){
				//removes a student from the specified course. 
				//takes SID and courseNum
				String Query = "DELETE FROM Enroll WHERE SID = " + S.next() + "AND courseID =" + S.next();
				Statement stmt = db_conn.createStatement();
				stmt.executeUpdate(Query);
			}else if(input2.equals("ENROLL")){
				
			}
		}
	
	}
}	

