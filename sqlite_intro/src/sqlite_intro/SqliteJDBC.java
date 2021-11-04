package sqlite_intro;
import java.sql.Statement;
import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.SQLException;
import java.sql.*;
import java.util.*;
   
public class SqliteJDBC {
	 public static void connect() throws Exception{  
		 Connection c = null;
	    	  
	         Class.forName("org.sqlite.JDBC");
	         c = DriverManager.getConnection("jdbc:sqlite:D:\\workspace\\sqlite_intro\\sqlite_db\\mulesoft.db");
	         System.out.println("Opened database successfully");
	        	 DatabaseMetaData dbm = c.getMetaData();
	        	 ResultSet tables = dbm.getTables(null, null, "Movies", null);
	        	 if (tables.next()) 
	        	 {
	    		     System.err.println("Table Already Created" );
	        	 }
	        	 else
	        		 create(c);
	        	 
	        	 menu(c);
	        c.close();
	         
	         
	         

	         
	      
	      
	    	  	System.out.println("Done ");
	    	  	
	      
	      
	      
	    }
	 public static void menu(Connection c) throws SQLException
	 {
	     Scanner Sc=new Scanner(System.in);
	     int option,selection;
	     String name,actor,actress,director;
	     int released;
	     System.out.println("Select from the MENU to perform below operations :");
	     System.out.println("1. Insertion ");
	     System.out.println("2. Selection ");
	     System.out.println("3. EXIT");
	     option = Sc.nextInt();
	     Sc.nextLine();
	     switch(option)
	     {
	     case 1: 
	    	 {System.out.println("Enter Moive Name :");
	     		name=Sc.nextLine();
     			System.out.println("Enter Actor Name :");
     			actor=Sc.nextLine();
     			System.out.println("Enter Actress Name :");
     			actress=Sc.nextLine();
     			System.out.println("Enter Director Name :");
     			director=Sc.nextLine();
     			System.out.println("Enter Released year :");
     			released=Sc.nextInt();
     			Sc.nextLine();
     			insert(c,name,actor,actress,director,released);
     			break;
	    	 }
	     case 2: 
	     { System.out.println("Select from the below operations to retrieve from SELECT:");
		     System.out.println("1. List of all movies by an actor");
		     System.out.println("2. List of all movies by an actress");
		     System.out.println("3. List of all movies by a director");
		     System.out.println("4. List of all movies ");

		     selection=Sc.nextInt();
		     Sc.nextLine();
		     select(c,selection);
	    	 break;
	     }
	     case 3: return;
	     default: {System.out.println("Enter Valid Option");break;}
     			
	     }
	     Sc.close();

	     

	 }
	 public static void create(Connection c)
	 {
		 Statement stmt = null;
		 try {
			stmt = c.createStatement();
			 String sql = "CREATE TABLE Movies " +
                     "(ID INT PRIMARY KEY ," +
                     " NAME           TEXT    NOT NULL, " + 
                     " ACTOR          TEXT ," + 
                     " ACTRESS        TEXT ," + 
                     " DIRECTOR TEXT NOT NULL , RELEASED YEAR)"; 
			 
			 stmt.executeUpdate(sql);
			 System.out.println("Table name Movies is created succesfully");
			 stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	 }
	 public static void insert(Connection c,String name,String actor,String actress,String director,int year)
	 {
		 Statement stmt = null;
		 try {
			String sql = "INSERT INTO Movies (NAME,ACTOR,ACTRESS,DIRECTOR,RELEASED) " +
	                  "VALUES (?,?,?,?,?);"; 
			PreparedStatement pstmt = c.prepareStatement(sql);
			
			pstmt.setString(1, name);
			pstmt.setString(2, actor);
			pstmt.setString(3, actress);
			pstmt.setString(4, director);
			pstmt.setInt(5,year);

	        pstmt.executeUpdate();
	        System.out.println("Inserted Sucessfully");
	        pstmt.close();
 		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	 public static void select (Connection c,int selection) throws SQLException,ArrayIndexOutOfBoundsException
	 {
		 ResultSet rs=null;
		 String sql=null,name=null;
		 PreparedStatement pstmt = null;
		 Scanner Sc=new Scanner(System.in);
		 if(selection ==1)
		 {
	     sql = "SELECT ACTOR,NAME FROM Movies Where ACTOR LIKE ? ";
	     System.out.println("Enter Actor name:");
	     name=Sc.nextLine();
		 }
		 else if(selection==2)
		 {
		  sql = "SELECT ACTRESS,NAME FROM Movies Where ACTRESS LIKE ? ";
		  System.out.println("Enter Actress name:");
		  name=Sc.nextLine();
		 }
		 else if(selection==3)
		 {
		 sql = "SELECT DIRECTOR,NAME FROM Movies Where DIRECTOR LIKE ? ";
	     System.out.println("Enter Director name:");
	     name=Sc.nextLine();
		 }
		 else if(selection==4)
		 {
			 sql = "SELECT * FROM Movies ";

		 }
		 pstmt = c.prepareStatement(sql);
		 if(selection!=4)
		 pstmt.setString(1, name);
		 rs  = pstmt.executeQuery();
		 if(selection==1)
   	      System.out.println("Actor   | \t Movie");
   	   	 if(selection==2)
   	      System.out.println("Actresses    | \t Movie");
   	     if(selection==3)
		      System.out.println("Director    | \t Movie");
   	     if(selection==4)
	    	  System.out.println("Name  | \t Actor   | \t Actress   | \t Director   | \t Released");

   	   System.out.println("-------------------------------------------------------------------");
	      while ( rs.next() ){
	    	  if(selection!=4)
	    	  {
	    	   System.out.println(rs.getString(1)+" | \t"+rs.getString(2));
		      }
	    	  else
	    	  {
	    	   System.out.println(rs.getString(2)+" | \t"+rs.getString(3)+" | \t"+rs.getString(4)+" | \t"+
	    			   rs.getString(5)+" | \t"+rs.getInt(6));

	    	  }
	   System.out.println("-------------------------------------------------------------------");

	        
	      }
	      rs.close();
	      pstmt.close();
	      Sc.close();
	 }
	   
	    public static void main(String[] args) throws Exception {  
	        connect();  
	    }  

}
