/*****************************************************************************************************************************
Scrim Fortress Project
File: DBInterface.java
Description: MySQL Database interface, everything with mysql stuff is here. 
Author: Jakub Homlala
****************************************************************************************************************************/

package com.scrims.main;

//***************************************************************************************************************************
//IMPORTS
//***************************************************************************************************************************

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//***************************************************************************************************************************
//Main class
//***************************************************************************************************************************

public class DBInterface {
static String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
static String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
//private static String madress = String.format("jdbc:mysql://%s:%s/scrims", host, port);
private static String madress ="jdbc:mysql://localhost:3306/scrims";
private static String muser = "scrim";
private static String mpass = "tutek";
private static Connection conn;
private static Statement stat;	

//***************************************************************************************************************************
//Constructor of main class
//***************************************************************************************************************************

	public DBInterface() throws SQLException
	{
		
		//createTable();

	}
	
//***************************************************************************************************************************
//Connect() is used for connecting to database
// its create connection to db which will be used in functions
//***************************************************************************************************************************
	
	private static void connect() 
	{
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver ());
			conn = DriverManager.getConnection(madress,muser,mpass);
			//stat = conn.createStatement();
		} catch (SQLException e) {

			e.printStackTrace();
		}
       

		
	}
//***************************************************************************************************************************
//Removing connection 
//***************************************************************************************************************************	
	private static void removeConnection()
	{
		try {
			conn.close();
			//stat.close();

			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//***************************************************************************************************************************
// This function will only create tables if not exists(by installation)
//***************************************************************************************************************************

	private boolean createTable() {
		connect();

        String users = "CREATE TABLE IF NOT EXISTS TEAM(TEAM_ID int NOT NULL AUTO_INCREMENT,TEAM_NAME varchar(50),TEAM_TAG varchar(10),TEAM_TYPE varchar(10),TEAM_DIV varchar(10),TEAM_LOGIN varchar(30),TEAM_PASS varchar(100),TEAM_INFO varchar(100),TEAM_STEAMID varchar(50),PRIMARY KEY (TEAM_ID)); ";
        String scrims = "CREATE TABLE IF NOT EXISTS SCRIM(SCRIM_ID int NOT NULL AUTO_INCREMENT,SCRIM_TEAM1_ID int,SCRIM_TEAM2_ID int,SCRIM_DATE varchar(50),SCRIM_TIME varchar(50),SCRIM_INFO varchar(100),PRIMARY KEY (SCRIM_ID)); ";
        String scrims_pending = "CREATE TABLE IF NOT EXISTS SCRIM_PENDING(SCRIM_PENDING_ID int NOT NULL AUTO_INCREMENT,SCRIM_ID INT,SCRIM_PENDERS varchar(500),SCRIM_PENDERS_COUNT int,PRIMARY KEY (SCRIM_PENDING_ID)); ";
        String mail = "CREATE TABLE IF NOT EXISTS MAIL(MAIL_ID int NOT NULL AUTO_INCREMENT,MAIL_DATE varchar(50),MAIL_TIME varchar(50),MAIL_AUTHOR_ID varchar(50),MAIL_RECEIVER_ID varchar(50),MAIL_TITLE varchar(50),MAIL_MESSAGE varchar(500),PRIMARY KEY (MAIL_ID)); ";
        try {
        	stat = conn.createStatement();
            stat.execute(mail);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        removeConnection();
        return true;
		
	}
	//***************************************************************************************************************************
	//Removing connection 
	//***************************************************************************************************************************
	public static boolean ping()
	{
		System.out.println("TESTOWANIE POLACZENIA:");
		 
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("JDBC Driver Error.");
			e.printStackTrace();
			return false;
		}
	 
		System.out.println("JDBC exists.");
		Connection connection = null;
	 
		try {
			connection = DriverManager.getConnection(madress,muser,mpass);
	 
		} catch (SQLException e) {
			System.out.println("Connection failed.");
			e.printStackTrace();
			return false;
		}
		return true;
	 
	
	  
		
	}
	
	
	
	public int checkIfExists(String type,String value) throws SQLException {
	    // select the number of rows in the table
		// type - type of checking value in table
		// value - value checking
		connect();
	    ResultSet rs = null;
	    int rowCount = -1;
	    Statement stats = null;
		try {
	  

	      stats = conn.createStatement();
	      rs = stats.executeQuery("SELECT COUNT(*) FROM TEAM WHERE "+type+"='"+value+"'");

	      // get the number of rows from the result set
	      rs.next();
	      rowCount = rs.getInt(1);
	    } finally {
	      rs.close();
	      stats.close();
	    }
		removeConnection();
	    return rowCount;
	  }
	
	
 
	public int insert(String TEAM_NAMEC, String TEAM_TAGC, String TEAM_TYPEC, String TEAM_DIVC, String TEAM_LOGINC, String TEAM_PASSC, String TEAM_INFOC,String TEAM_STEAMID) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException
	 {
		connect();
		 
		 try {
	            PreparedStatement prepStmt = conn.prepareStatement(
	                    "insert into TEAM values (NULL, ?, ?, ?, ?, ?, ?, ?,?);");
	            prepStmt.setString(1, TEAM_NAMEC);
	            prepStmt.setString(2, TEAM_TAGC);
	            prepStmt.setString(3, TEAM_TYPEC);
	            prepStmt.setString(4, TEAM_DIVC);
	            prepStmt.setString(5, TEAM_LOGINC);
	            prepStmt.setString(6, Hash.hashPassword(TEAM_PASSC, TEAM_LOGINC));
	            prepStmt.setString(7, TEAM_INFOC);
	            prepStmt.setString(8, TEAM_STEAMID);
		        prepStmt.execute();
		        prepStmt.close();
		        
		       
		        return 1; 
		         
		        
	        } catch (SQLException e) {
	        	
	            return 0;

	         
	        }
		 	finally
		 	{
		 		conn.close();
		 		removeConnection();
		 	}
	 }
		 
	
	
	public static int login(String login,String passl) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException
	{
		connect();
		int approve = 0;
		

		String baza_pass = null;

		stat = conn.createStatement();
		ResultSet result = stat.executeQuery("SELECT * FROM TEAM WHERE TEAM_LOGIN ='"+login+"'");
		
		if (result.next())
		{
		baza_pass = result.getString("TEAM_PASS");
		if (baza_pass.equals(Hash.hashPassword(passl,login)))
			approve = 1;	
		}
		
		removeConnection();
		stat.close();
		result.close();
		if (approve == 0)
			
			return 0;
		
		else
		
			return 1;
	}
	
	
	public void loadTeamData(Team team, String TEAM_LOGIN) throws SQLException {
		connect();
		stat = conn.createStatement();
		ResultSet result = stat.executeQuery("SELECT * FROM TEAM WHERE TEAM_LOGIN ='"+TEAM_LOGIN+"'");
		
		if (result.next())
		{
			team.setName(result.getString("TEAM_NAME"));
			team.setTag(result.getString("TEAM_TAG"));
			team.setType(result.getString("TEAM_TYPE"));
			team.setDiv(result.getString("TEAM_DIV"));
			team.setType(result.getString("TEAM_TYPE"));
			team.setLogin(result.getString("TEAM_LOGIN"));
			team.setInfo(result.getString("TEAM_INFO"));
			team.setID(result.getInt("TEAM_ID"));
			team.setSteamID(result.getString("TEAM_STEAMID"));
			
			System.out.println("WAZNE: " +team.getInfo());
			//System.out.println(result.getString("TEAM_NAME"));
			//System.out.println(result.getString("TEAM_TAG"));
			//System.out.println(result.getString("TEAM_TYPE"));
			//System.out.println(result.getString("TEAM_DIV"));
			//System.out.println(result.getString("TEAM_LOGIN"));
			//System.out.println(result.getString("TEAM_INFO"));
			//System.out.println(result.getInt("TEAM_ID"));
			//System.out.println(result.getString("TEAM_STEAMID"));
			
			
		}
		removeConnection();
		stat.close();
		result.close();
	}
		
		
		public void loadTeamDataFromID(Team team, int TEAM_ID) throws SQLException {
			connect();
			ResultSet result = null;
			
			try
			{
				stat = conn.createStatement();
				result = stat.executeQuery("SELECT * FROM TEAM WHERE TEAM_ID ='"+TEAM_ID+"'");
				
				if (result.next())
				{
					team.setName(result.getString("TEAM_NAME"));
					team.setTag(result.getString("TEAM_TAG"));
					team.setType(result.getString("TEAM_TYPE"));
					team.setDiv(result.getString("TEAM_DIV"));
					team.setType(result.getString("TEAM_TYPE"));
					team.setLogin(result.getString("TEAM_LOGIN"));
					team.setInfo(result.getString("TEAM_INFO"));
					team.setID(result.getInt("TEAM_ID"));
					team.setSteamID(result.getString("TEAM_STEAMID"));
					
					System.out.println("WAZNE: " +team.getInfo());
					//System.out.println(result.getString("TEAM_NAME"));
					//System.out.println(result.getString("TEAM_TAG"));
					//System.out.println(result.getString("TEAM_TYPE"));
					//System.out.println(result.getString("TEAM_DIV"));
					//System.out.println(result.getString("TEAM_LOGIN"));
					//System.out.println(result.getString("TEAM_INFO"));
					//System.out.println(result.getInt("TEAM_ID"));
					//System.out.println(result.getString("TEAM_STEAMID"));
					
				}
			}
			catch (SQLException e)
			{
				System.out.println("ERROR: "+e);
			}
			finally
			{
				removeConnection();
				stat.close();
				result.close();
			}
		
		
	}
	public int checkScrimNumber() throws SQLException
	{
		connect();
		int scrimCount = 0;
		stat = conn.createStatement();
		ResultSet result = stat.executeQuery("SELECT * FROM SCRIM ");
		while (result.next())
		{
			// chceck does this record is up to date
			
			if (checkDate(result.getString("SCRIM_DATE")))
			{
				scrimCount++;
			}
		}
		stat.close();
		removeConnection();
		result.close();
		return scrimCount;
	}
	
	@SuppressWarnings("finally")
	public int checkScrimNumberForTeam(int ID) throws SQLException 
	{
		connect();
		int scrimCount = 0;
		ResultSet result = null;
		try
		{
				stat = conn.createStatement();
				 result = stat.executeQuery("SELECT * FROM SCRIM WHERE SCRIM_TEAM1_ID="+ID);
			
			
			while (result.next())
			{
				// chceck does this record is up to date
				
				if (checkDate(result.getString("SCRIM_DATE")))
				{
					scrimCount++;
				}
			}
			 result.close();
			 result = stat.executeQuery("SELECT * FROM SCRIM WHERE SCRIM_TEAM2_ID="+ID);
		
			while (result.next())
			{
				// chceck does this record is up to date
				
				if (checkDate(result.getString("SCRIM_DATE")))
				{
					scrimCount++;
				}
			}
		}
		catch (SQLException e)
		{
			System.out.println("ERROR: "+e);
		}
		finally
		{
		
		stat.close();
		result.close();
		removeConnection();
		return scrimCount;
		}
	}
	
	
	
	
	
	
	
	
	
	public void loadScrims(Scrim scrims[]) throws SQLException {
		connect();
		int iterator=0;
		stat = conn.createStatement();
		ResultSet result = stat.executeQuery("SELECT * FROM SCRIM ");


		while (result.next())
		{
			// chceck does this record is up to date
			
			if (checkDate(result.getString("SCRIM_DATE")))
			{

				
				scrims[iterator]= new Scrim();
				
				scrims[iterator].setID(Integer.parseInt(result.getString("SCRIM_ID")));
				
				scrims[iterator].setTeam1_ID(Integer.parseInt(result.getString("SCRIM_TEAM1_ID")));
				scrims[iterator].setTeam2_ID(Integer.parseInt(result.getString("SCRIM_TEAM2_ID")));
				scrims[iterator].setDate(result.getString("SCRIM_DATE"));
				scrims[iterator].setInfo(result.getString("SCRIM_INFO"));
				scrims[iterator].setTime(result.getString("SCRIM_TIME"));
				iterator=iterator+1;
			}
			
			
		}
		removeConnection();
		stat.close();
		result.close();
		
	}
	
	
	public int checkScrimNumberForTeamWherePending(int ID) throws SQLException
	{
		connect();
		int scrimCount = 0;
		stat = conn.createStatement();
		ResultSet result = stat.executeQuery("SELECT * FROM SCRIM WHERE SCRIM_TEAM1_ID="+ID);
		while (result.next())
		{
			// chceck does this record is up to date
			
			if (checkDate(result.getString("SCRIM_DATE")))
			{
				
				ResultSet result2 = stat.executeQuery("SELECT * FROM SCRIM_PENDING WHERE SCRIM_ID="+result.getString("SCRIM_ID"));
				while(result2.next())
				{
					if (Integer.parseInt(result2.getString("SCRIM_PENDERS_COUNT"))==0)
					{
						 scrimCount++;
					}
				}
				result2.close();
			}
		}
		removeConnection();
		result.close();
		stat.close();
		return scrimCount;
	}
	
	
	@SuppressWarnings("finally")
	public int countScrimsForTeamWherePending(int ID) throws SQLException {
		connect();
		ResultSet result = null;
		int counter1=0;
		int counter2=0;
		try
		{
			
			int passed = 0;
			int iterator=0;
			int scrim_id_for_team[]= new int[100];
			int active_pending_list[] = new int[100];
			stat = conn.createStatement();
			 result = stat.executeQuery("SELECT * FROM SCRIM WHERE SCRIM_TEAM1_ID="+ID);
			while (result.next())
			{
				// chceck does this record is up to date
				
				if (checkDate(result.getString("SCRIM_DATE")))
				{
					
					
					scrim_id_for_team[counter1]=Integer.parseInt(result.getString("SCRIM_ID"));
					counter1++;
				}
			}
			
			result = stat.executeQuery("SELECT * FROM SCRIM_PENDING");
			while (result.next())
				{
				 	int current= Integer.parseInt(result.getString("SCRIM_ID"));
				 	for (int i=0;i<counter1;i++)
				 	{
				 		if (scrim_id_for_team[i]==current)
				 		{
				 			passed = 1;
				 		}
				 		else
				 		{
				 			passed = 0;
				 		}
				 		if (passed==1)
					 	{
					 		active_pending_list[counter2]=current;
					 		counter2++;
					 	}
				 	}
				 	
				 	
				}
		}
		catch (SQLException e)
		
		{
			System.out.println("ERROR "+e);
		}
		finally
		{
			removeConnection();
			stat.close();
			result.close();
			return counter2;
		}
	}
	
	
	
	
	
	
	public void loadScrimsForTeamWherePending(Scrim scrims[],int ID) throws SQLException {
		connect();
		ResultSet result = null;
		try
		{
		int counter1=0;
		int counter2=0;
		int passed = 0;
		int iterator=0;
		int scrim_id_for_team[]= new int[100];
		int active_pending_list[] = new int[100];
		stat = conn.createStatement();
		
		 result = stat.executeQuery("SELECT * FROM SCRIM WHERE SCRIM_TEAM1_ID="+ID);
		while (result.next())
		{
			// chceck does this record is up to date
			
			if (checkDate(result.getString("SCRIM_DATE")))
			{
				
				
				scrim_id_for_team[counter1]=Integer.parseInt(result.getString("SCRIM_ID"));
				counter1++;
			}
		}
		result.close();
		result = stat.executeQuery("SELECT * FROM SCRIM_PENDING");
		while (result.next())
			{
			 	int current= Integer.parseInt(result.getString("SCRIM_ID"));
			 	for (int i=0;i<counter1;i++)
			 	{
			 		if (scrim_id_for_team[i]==current)
			 		{
			 			passed = 1;
			 		}
			 		else
			 		{
			 			passed = 0;
			 		}
			 		
			 		if (passed==1)
				 	{
				 		active_pending_list[counter2]=current;
				 		counter2++;
				 	}
			 	}
			 	
			 	
			}
		

		
		 result = stat.executeQuery("SELECT * FROM SCRIM WHERE SCRIM_TEAM1_ID="+ID);


		while (result.next())
		{
			// chceck does this record is up to date
			
			if (checkDate(result.getString("SCRIM_DATE")))
			{
				int current= Integer.parseInt(result.getString("SCRIM_ID"));
				for (int i=0;i<counter2;i++)
			 	{
			 		if (active_pending_list[i]==current)
			 		{
			 			passed = 1;
			 		}
			 		else
			 		{
			 			passed = 0;
			 		}
			 		
			 		if (passed==1)
				 	{
				 
						
						
						scrims[iterator]= new Scrim();
						
						scrims[iterator].setID(Integer.parseInt(result.getString("SCRIM_ID")));
						
						scrims[iterator].setTeam1_ID(Integer.parseInt(result.getString("SCRIM_TEAM1_ID")));
						scrims[iterator].setTeam2_ID(Integer.parseInt(result.getString("SCRIM_TEAM2_ID")));
						scrims[iterator].setDate(result.getString("SCRIM_DATE"));
						scrims[iterator].setInfo(result.getString("SCRIM_INFO"));
						scrims[iterator].setTime(result.getString("SCRIM_TIME"));
						iterator=iterator+1;
				 	}
			 		
			 	
			 	}
				
			 	
			}
			
			
		}	
		}
		catch (SQLException e)
		{
			System.out.println("ERROR "+e);
		}
		finally
		{
			
			stat.close();
			result.close();
			removeConnection();
		}
		
	}

	
	
	public void loadScrimsForTeam(Scrim scrims[],int ID) throws SQLException {
		connect();
		int iterator=0;
		stat = conn.createStatement();
		ResultSet result = stat.executeQuery("SELECT * FROM SCRIM WHERE SCRIM_TEAM1_ID="+ID);


		while (result.next())
		{
			// chceck does this record is up to date
			
			if (checkDate(result.getString("SCRIM_DATE")))
			{

				
				scrims[iterator]= new Scrim();
				
				scrims[iterator].setID(Integer.parseInt(result.getString("SCRIM_ID")));
				
				scrims[iterator].setTeam1_ID(Integer.parseInt(result.getString("SCRIM_TEAM1_ID")));
				scrims[iterator].setTeam2_ID(Integer.parseInt(result.getString("SCRIM_TEAM2_ID")));
				scrims[iterator].setDate(result.getString("SCRIM_DATE"));
				scrims[iterator].setInfo(result.getString("SCRIM_INFO"));
				scrims[iterator].setTime(result.getString("SCRIM_TIME"));
				iterator=iterator+1;
			}
		
			
			
			
		}
	
		 result = stat.executeQuery("SELECT * FROM SCRIM WHERE SCRIM_TEAM2_ID="+ID);


		while (result.next())
		{
			// chceck does this record is up to date
			
			if (checkDate(result.getString("SCRIM_DATE")))
			{

				
				scrims[iterator]= new Scrim();
				
				scrims[iterator].setID(Integer.parseInt(result.getString("SCRIM_ID")));
				
				scrims[iterator].setTeam1_ID(Integer.parseInt(result.getString("SCRIM_TEAM1_ID")));
				scrims[iterator].setTeam2_ID(Integer.parseInt(result.getString("SCRIM_TEAM2_ID")));
				scrims[iterator].setDate(result.getString("SCRIM_DATE"));
				scrims[iterator].setInfo(result.getString("SCRIM_INFO"));
				scrims[iterator].setTime(result.getString("SCRIM_TIME"));
				iterator=iterator+1;
			}
		
			
			
			
		}
		removeConnection();
		stat.close();
		result.close();
	}
	
	
	

	private boolean checkDate(String date_forcheck)
	{
		
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date today = Calendar.getInstance().getTime();        

		String todayDate = df.format(today);
		String date_today[] = todayDate.split("/");
		String date_check[] = date_forcheck.split("/");
		
		int day_today = Integer.parseInt(date_today[0]);
		int month_today = Integer.parseInt(date_today[1]);
		int day_check = Integer.parseInt(date_check[0]);
		int month_check = Integer.parseInt(date_check[1]);

		
		
		
		if (month_check>12 || day_check>31)
			return false;
		else
		{
			if (month_today > month_check)
				return false;
			else
				if (day_today > day_check)
					return false;
				else
					return true;
		}	

		
	}
	
	
	

	public boolean checkScrimTeam(int ID) throws SQLException 
	{
		connect();
		int s_count=0;
		stat = conn.createStatement();
		ResultSet result = stat.executeQuery("SELECT * FROM SCRIM WHERE SCRIM_TEAM1_ID ='"+ID+"'");
		while (result.next())
		{
			if (checkDate(result.getString("SCRIM_DATE")))
			{
				s_count++;
			}
		}

		removeConnection();
		result.close();
		stat.close();
		if(s_count>4)
			return false;
		else
			return true;
	}
	public boolean checkScrimTeamDate(int ID, String SCRIM_DATE, String SCRIM_TIME) throws SQLException {
		connect();
		stat = conn.createStatement();
		ResultSet result = stat.executeQuery("SELECT * FROM SCRIM WHERE SCRIM_TEAM1_ID ='"+ID+"'");
		int problemCount = 0;
	
		String ResultDate = "";
		//change date from for example 5/20/2014 for 20/05/2014
		String date_check[] = SCRIM_DATE.split("/");
		int day_check = Integer.parseInt(date_check[1]);
		int month_check = Integer.parseInt(date_check[0]);	
		int year_check = Integer.parseInt(date_check[2]);
		if (month_check<10)
			 ResultDate = day_check+"/0"+month_check+"/"+year_check;
		else
			 ResultDate = day_check+"/"+month_check+"/"+year_check;
		
		
		while (result.next())
		{
			if (checkDate(result.getString("SCRIM_DATE")))
			{
				if (result.getString("SCRIM_DATE").equals(ResultDate))
				{
					if (result.getString("SCRIM_TIME").equals(SCRIM_TIME))
					{
						problemCount++;
					}
				}
			}
		}
		removeConnection();
		result.close();
		stat.close();
		if (problemCount==0)
		
			return true;
		
		else
			return false;
	}

	public int addScrim(int ID, String SCRIM_DATE, String SCRIM_TIME, String SCRIM_INFO) 
	{
		 try {
			 	connect();
				String ResultDate = "";
				//change date from for example 5/20/2014 for 20/05/2014
				String date_check[] = SCRIM_DATE.split("/");
				int day_check = Integer.parseInt(date_check[1]);
				int month_check = Integer.parseInt(date_check[0]);	
				int year_check = Integer.parseInt(date_check[2]);
				if (month_check<10)
					 ResultDate = day_check+"/0"+month_check+"/"+year_check;
				else
					 ResultDate = day_check+"/"+month_check+"/"+year_check;
			 
			 
			 
			 
	            PreparedStatement prepStmt = conn.prepareStatement(
	                    "insert into SCRIM values (NULL, ?, ?, ?, ?, ?);");
	            prepStmt.setString(1, ""+ID);
	            prepStmt.setString(2, "-1");
	            prepStmt.setString(3, ResultDate);
	            prepStmt.setString(4, SCRIM_TIME);
	            prepStmt.setString(5, SCRIM_INFO);
		        prepStmt.execute();
		        
		        prepStmt.close();
		      
		        createScrimPendingList();
		        
		        return 1; 
		         
		        
	        } catch (SQLException e) {
	        	
	            return 0;
	         
	        }
		 	finally
		 	{
		 		removeConnection();
		 	}
	 }
	private void createScrimPendingList() throws SQLException 
	{
		int insertId=0;
		connect();
		//get the last highest number
		
		stat = conn.createStatement();
		ResultSet result = stat.executeQuery("SELECT * FROM SCRIM ");
		while (result.next())
		{
			int current = Integer.parseInt(result.getString("SCRIM_ID"));
			if (current>insertId)
				insertId=current;
		}
		
		
		
		PreparedStatement getLastInsertId = conn.prepareStatement("SELECT LAST_INSERT_ID()");  
		ResultSet rs = getLastInsertId.executeQuery();  
		
		    
		 System.out.println(insertId);
         PreparedStatement prepStmt = conn.prepareStatement(
                 "insert into SCRIM_PENDING values (NULL, ?, ?,?);");
         prepStmt.setString(1, ""+insertId);
         prepStmt.setString(2, "-1,");
         prepStmt.setString(3, "0");
         prepStmt.execute();
	     prepStmt.close();
		 
	     rs.close();
	     removeConnection();
		
		
		
	}
	
	public String getScrimPendingList(int SCRIM_ID) throws SQLException
		 {
			connect();
			stat = conn.createStatement();
			ResultSet result = stat.executeQuery("SELECT * FROM SCRIM_PENDING WHERE SCRIM_ID ='"+SCRIM_ID+"'");
			
			
			while (result.next())
			{
				return result.getString("SCRIM_PENDERS");
			}
			
			result.close();
			stat.close();
			removeConnection();
			return null;
			
		 }
		
	

	
	
	public void removeFromPendingList(int SCRIM_ID) throws SQLException
		{
			connect();
			stat = conn.createStatement();
			int result = stat.executeUpdate("DELETE FROM scrim_pending WHERE SCRIM_ID='"+SCRIM_ID+"'");
			stat.close();
			removeConnection();
		}
	
	public void addTeam2ToScrimList(int SCRIM_ID,int TEAM_ID) throws SQLException
	{
		connect();
		stat = conn.createStatement();
		int result = stat.executeUpdate("UPDATE scrim SET SCRIM_TEAM2_ID='"+TEAM_ID+"' WHERE SCRIM_ID='"+SCRIM_ID+"'");

		stat.close();
		removeConnection();
		        
	}
	
	
	public void addTeamToPendingList(int SCRIM_ID,int TEAM_ID) throws SQLException
	{
		System.out.println("tutaj"+SCRIM_ID);
		connect();
		stat = conn.createStatement();
		ResultSet result = stat.executeQuery("SELECT * FROM SCRIM_PENDING WHERE SCRIM_ID ='"+SCRIM_ID+"'");
		System.out.println("tutaj2"+SCRIM_ID);
		String penders = null;
		while (result.next())
		{
			penders = result.getString("SCRIM_PENDERS");
			penders = penders  + TEAM_ID + ",";
			
		}
		if (!(penders == null))
		System.out.println(penders);
		
		stat.executeUpdate("UPDATE SCRIM_PENDING SET SCRIM_PENDERS='"+penders+"' WHERE SCRIM_ID='"+SCRIM_ID+"'");
		result.close();
		stat.close();
		removeConnection();
	}
	
	
	public boolean checkIfAlreadyAddedToPendingList(int SCRIM_ID, int TEAM_ID) throws SQLException
	{
		connect();
		stat = conn.createStatement();
		ResultSet result = stat.executeQuery("SELECT * FROM SCRIM_PENDING WHERE SCRIM_ID ='"+SCRIM_ID+"'");
		String penders = null;
		int decision = 0 ;
		while (result.next())
		{
			penders = result.getString("SCRIM_PENDERS");
			String team_list[] = penders.split(",");
			for (int i=0;i<team_list.length;i++)
			{
				if (Integer.parseInt(team_list[i]) == TEAM_ID)
				{
					decision = 1;
					break;
				}
				else
					decision = 0;
			}
		}
		stat.close();
		result.close();
		removeConnection();
		if (decision == 0)
			return false;
		else
			return true;
	}
	
	public boolean checkIfTeamIsSameTeam(int SCRIM_ID, int TEAM_ID) throws SQLException
	{
		connect();
		int decision=0;
		stat = conn.createStatement();
		ResultSet result = stat.executeQuery("SELECT * FROM SCRIM WHERE SCRIM_ID ='"+SCRIM_ID+"'");
		while (result.next())
		{
			if (Integer.parseInt(result.getString("SCRIM_TEAM1_ID")) == TEAM_ID)
				decision = 1;
			else
				decision = 0;
		}
		result.close();
		stat.close();
		removeConnection();
		if (decision==1)
			return true;
		else
			return false;
	}
	
	public Scrim loadScrimWithID(int SCRIM_ID) throws SQLException
	{
		connect();
		stat = conn.createStatement();
		Scrim scrim = new Scrim();
		ResultSet result = stat.executeQuery("SELECT * FROM SCRIM WHERE SCRIM_ID ='"+SCRIM_ID+"'");
			while (result.next())
			{
				scrim.setID(Integer.parseInt(result.getString("SCRIM_ID")));
				scrim.setTeam1_ID(Integer.parseInt(result.getString("SCRIM_TEAM1_ID")));
				scrim.setTeam2_ID(Integer.parseInt(result.getString("SCRIM_TEAM2_ID")));
				scrim.setDate(result.getString("SCRIM_DATE"));
				scrim.setInfo(result.getString("SCRIM_INFO"));
				scrim.setTime(result.getString("SCRIM_TIME"));
			}
			result.close();
			stat.close();
			removeConnection();
		return scrim;
	}
	public void removeScrimWithID(int SCRIM_ID ) throws SQLException
	{
		//remove from scrim table
		connect();
		stat = conn.createStatement();
		stat.executeUpdate("DELETE FROM SCRIM WHERE SCRIM_ID='"+SCRIM_ID+"'");
		//remove from scrim_pending table
		
		ResultSet result = stat.executeQuery("SELECT * FROM SCRIM_PENDING WHERE SCRIM_ID ='"+SCRIM_ID+"'");
		if (result.next())
		{
			stat.executeUpdate("DELETE FROM SCRIM_PENDING WHERE SCRIM_ID='"+SCRIM_ID+"'");
		}
		
		result.close();
		stat.close();
		removeConnection();
	}
	public void changeTeamValue(String TEAM_COLUMN, String VALUE, String TEAMID) throws SQLException 
	{
		try
		{
			connect();
			stat = conn.createStatement();
			stat.executeUpdate("UPDATE TEAM SET "+TEAM_COLUMN+"='"+VALUE+"' WHERE TEAM_ID='"+TEAMID+"'");
			
		}
		catch (SQLException e)
		{
			System.out.println("ERROR"+e);
		}
		finally
		{
			removeConnection();
			stat.close();
		}
	}
	

	@SuppressWarnings("finally")
	
//***************************************************************************************************************************
//countMailsForID()
//Function that counts mails for db and return number
//Arguments : TEAM_ID , as the variables says its a team id fromdb
// type 0 - count mail received
// type 1 - count mails sent
//***************************************************************************************************************************
	public int countMailsForID(String TEAM_ID,int type) throws SQLException
	{
		ResultSet result=null;
		int count=0;
		try
		{
			
			connect();
			stat = conn.createStatement();
			if (type==0)
				result = stat.executeQuery("SELECT * FROM MAIL WHERE MAIL_RECEIVER_ID ='"+TEAM_ID+"'");
			if (type==1)
				result = stat.executeQuery("SELECT * FROM MAIL WHERE MAIL_AUTHOR_ID ='"+TEAM_ID+"'");
			while (result.next())
			{
				count++;
		
			}
		}
		catch (SQLException e)
		{
			System.out.println("ERROR"+e);
		}
		finally
		{
			stat.close();
			result.close();
			removeConnection();
			return count;
		}
			
			
		
		
	}
	
	
	
	public void loadMailsForID(String TEAM_ID, Mail[] mails,int type) throws SQLException 
	{	ResultSet result=null;
		int iterator=0;
		try
		{
			
			connect();
			stat = conn.createStatement();
			if (type==0)
				 result = stat.executeQuery("SELECT * FROM mail WHERE MAIL_RECEIVER_ID ='"+TEAM_ID+"'");
			if (type==1)
				result = stat.executeQuery("SELECT * FROM mail WHERE MAIL_AUTHOR_ID ='"+TEAM_ID+"'");
			while (result.next())
			{
				mails[iterator]= new Mail();
				
				mails[iterator].setID(Integer.parseInt(result.getString("MAIL_ID")));
				mails[iterator].setDate(result.getString("MAIL_DATE"));
				mails[iterator].setTime(result.getString("MAIL_TIME"));
				mails[iterator].setAuthor_ID(Integer.parseInt(result.getString("MAIL_AUTHOR_ID")));
				mails[iterator].setReceiver_ID(Integer.parseInt(result.getString("MAIL_RECEIVER_ID")));
				mails[iterator].setTitle(result.getString("MAIL_TITLE"));
				mails[iterator].setMessage(result.getString("MAIL_MESSAGE"));
			}
			
		}
		catch (SQLException e)
		{
			System.out.println("ERROR"+e);
		}
		finally
		{
			removeConnection();

		}
	}
	
	@SuppressWarnings("finally")
	public int countTeams() throws SQLException
	{
		ResultSet result=null;
		int count=0;
		try
		{
			
			connect();
			stat = conn.createStatement();
			result = stat.executeQuery("SELECT * FROM TEAM");
			
			while (result.next())
			{
				count++;
		
			}
		}
		catch (SQLException e)
		{
			System.out.println("ERROR"+e);
		}
		finally
		{
			stat.close();
			result.close();
			removeConnection();
			return count;
		}
				
	}
	
	@SuppressWarnings("finally")
	public Team[] loadTeams(int count) throws SQLException
	{
		ResultSet result=null;
		int iterator=0;
		Team teams[] = new Team[count];
		try
		{
			
			connect();
			stat = conn.createStatement();
			result = stat.executeQuery("SELECT * FROM TEAM");
			
			while (result.next())
			{
				teams[iterator] = new Team();
				teams[iterator].setName(result.getString("TEAM_NAME"));
				teams[iterator].setTag(result.getString("TEAM_TAG"));
				teams[iterator].setType(result.getString("TEAM_TYPE"));
				teams[iterator].setDiv(result.getString("TEAM_DIV"));
				teams[iterator].setType(result.getString("TEAM_TYPE"));
				teams[iterator].setLogin(result.getString("TEAM_LOGIN"));
				teams[iterator].setInfo(result.getString("TEAM_INFO"));
				teams[iterator].setID(result.getInt("TEAM_ID"));
				teams[iterator].setSteamID(result.getString("TEAM_STEAMID"));
				iterator++;
		
			}
		}
		catch (SQLException e)
		{
			System.out.println("ERROR"+e);
		}
		finally
		{
			stat.close();
			result.close();
			removeConnection();
			return teams;
		}
			
	}

	public void addMessage(int id, String title, String msg, String teamformsg) throws SQLException 
	{
		ResultSet result=null;
		int iterator=0;
		int id_tomsg = 0; //sent msg to SYSTEM if FAIL
		try
		{
			
			connect();
			stat = conn.createStatement();
			result = stat.executeQuery("SELECT * FROM TEAM WHERE TEAM_NAME='"+teamformsg+"'");
			
			while (result.next())
			{
				id_tomsg = Integer.parseInt(result.getString("TEAM_ID"));
				
			}
			
		
			result.close();
			String MAIL_DATE;
			String MAIL_TIME;
			
			DateFormat df = new SimpleDateFormat("HH:mm:ss");
			Date today = Calendar.getInstance().getTime();        
			DateFormat df2 = new SimpleDateFormat("dd-MM-yyyy");
			Date today2 = Calendar.getInstance().getTime();        

			
			
			MAIL_TIME = df.format(today);
			MAIL_DATE = df2.format(today2);
			PreparedStatement prepStmt = conn.prepareStatement(
                    "insert into mail values (NULL, ?, ?, ?, ?, ?, ?);");
            prepStmt.setString(1, MAIL_DATE);
            prepStmt.setString(2, MAIL_TIME);
            prepStmt.setString(3, String.valueOf(id));
            prepStmt.setString(4, String.valueOf(id_tomsg));
            prepStmt.setString(5, title);
            prepStmt.setString(6, msg);

	        prepStmt.execute();
	        prepStmt.close();
			
			
		}
		catch (SQLException e)
		{
			System.out.println("ERROR"+e);
		}
		finally
		{
			stat.close();
			result.close();
			removeConnection();

		}
		
		
	}
	
	
}
