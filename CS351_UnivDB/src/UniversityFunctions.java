import java.util.Scanner;
import java.sql.*;

public class UniversityFunctions {

	public static void main() throws Exception{
		Class.forName("org.sqlite.JDBC");
		
		//connect to the database.
		Connection db_conn = DriverManager.getConnection("jdbc:sqlite:UnivDB.db");
		//create a new scanner to take the user input.
		Scanner S = new Scanner(System.in);
		while(true){
			System.out.print("Enter Command: ");
			String input = S.next();
			if(input.equals("STU")){
				//enter the student menu
				System.out.println("Chose STUDENT");
				StudentFunctions.main();
				continue;
			}
			else if(input.equals("FAC")){
				//enter the faculty menu.
				System.out.println("Chose FAC");
				continue;
			}else if(input.equals("EXIT")){
				//exit the program
				System.out.println("Chose EXIT");
				System.exit(0);
			}else if(input.equals("SELECT")){
				//custom query. read it and return result.
				String Query = "SELECT" + S.nextLine();
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
				continue;
			}else{
				//incorrect input return error msg.
				System.out.println("WRONG INPUT. RETRY.");
			}
			//advance passed the newline char.
			S.nextLine();
		}
	}
}

