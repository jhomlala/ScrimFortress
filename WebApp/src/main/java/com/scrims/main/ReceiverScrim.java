package com.scrims.main;


import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ReceiverScrim extends HttpServlet{  
    private static final long serialVersionUID = 1L;  
    private PrintWriter out;
    private DBInterface DB;
    private HttpSession session;
    private HttpServletRequest G_request;
    
    public void doPost(HttpServletRequest request, HttpServletResponse response)    
            throws ServletException, IOException {    
    	G_request = request;
    	//Graphic Interface:
    	request.getRequestDispatcher("/Main.jsp").include(request, response);
    	
        response.setContentType("text/html");    
        out  = response.getWriter();   
        session = request.getSession();
        String SCRIM_DATE = request.getParameter("date");   
        String SCRIM_TIME = request.getParameter("time");    
        String SCRIM_INFO = request.getParameter("info");   
        try {
			checkPostData(SCRIM_TIME,SCRIM_DATE,SCRIM_INFO);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException
				| SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    
        out.println("<INPUT Type='button' class='btn btn-primary' VALUE='Back' onClick='history.go(-1);return true;'>");
        out.println("<a href='Home.jsp'><input type='button' class='btn btn-primary' value='Home'></a> "); 
    }    
    
    private void checkPostData(String SCRIM_TIME,String SCRIM_DATE,String SCRIM_INFO) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException 
    {
    	if (!SCRIM_DATE.trim().isEmpty() && SCRIM_DATE !=null && checkDate(SCRIM_DATE))
    		if (!SCRIM_TIME.trim().isEmpty() && SCRIM_TIME !=null &&   isnotAlpha(SCRIM_TIME) && checkTime(SCRIM_TIME) )
    			if (!SCRIM_INFO.trim().isEmpty() && SCRIM_INFO !=null && checkInfo(SCRIM_INFO))
    				{
    					checkSQLData( SCRIM_TIME, SCRIM_DATE,SCRIM_INFO);
    				}
    			else
    				out.println("<div class='alert alert-danger'><strong>Error:</strong> Please insert info (max 100 char)!</div>");
    		else
    			out.println("<div class='alert alert-danger'><strong>Error:</strong> Please insert valid time!</div>");
		else
			out.println("<div class='alert alert-danger'><strong>Error:</strong> Please insert valid date! Only date from today can be chose.</div>");
		
	}

	private boolean checkInfo(String SCRIM_INFO) {
		if (SCRIM_INFO.length() <=100)
			return true;
		else
			return false;
	}

	private void checkSQLData(String SCRIM_TIME, String SCRIM_DATE, String SCRIM_INFO) throws SQLException 
	{
		//check does team have not more than 5 scrims
		//check does team put same scrim in same time
		DB = new DBInterface(); 
	 	Team team  = CookieInterface.getTeamFromCookies(G_request);
	 	int ID = team.getID();
		if (DB.checkScrimTeam(ID))
		{
			if (DB.checkScrimTeamDate(ID,SCRIM_DATE,SCRIM_TIME))
			{
				if ( DB.addScrim(ID,SCRIM_DATE,SCRIM_TIME,SCRIM_INFO) == 1)
				{
					out.println("<div class='alert alert-success'><strong>Success:</strong> Posted your scrim!</div>");
				}
				else
					out.println("<div class='alert alert-danger'><strong>Error:</strong> Sorry! Something wrong with our systems. Please try again!</div>");
				
			}
			else
				out.println("<div class='alert alert-danger'><strong>Error:</strong> Sorry! You have posted already scrim with this date and time!</div>");

		}
		else
			out.println("<div class='alert alert-danger'><strong>Error:</strong>Sorry! You have 5 active scrims. You cant add more in this time!</div>");

		
		
		
	}

	private boolean checkTime(String SCRIM_TIME) {
		String time_check[] = SCRIM_TIME.split(":");
		int hour = -1;
		int minutes = -1;
		if ( time_check.length == 0 )
			return false;
		else
		if (SCRIM_TIME.contains(":")) // check does time is set with good pattern <number>:<number> with this if someone put 20000:3000 will work and will be stopped in next if
		{
			{
				boolean parsable = true;
				try{
					hour = Integer.parseInt(time_check[0]);
				    minutes = Integer.parseInt(time_check[1]);
	
				}catch(NumberFormatException e){
				parsable = false;
				}
			
				if (parsable)
				{
					if (hour>=0 && hour<=23)
						if (minutes>=0 && minutes<=59)
							return true;
						else
							return false;
					else
						return false;
				}
				else 
					return false;
			}
		}
		else
			return false;
	}

	private boolean checkDate(String SCRIM_DATE) {
		
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date today = Calendar.getInstance().getTime();        

		String todayDate = df.format(today);
		String date_today[] = todayDate.split("/");
		String date_check[] = SCRIM_DATE.split("/");
		

		if (date_check.length > 0)
		{
			if (countLetter(SCRIM_DATE)==2)// check does string contains 2 chars "/" - with this user cant pass date like 222222222222 or // or anything shit
			{
				int day_today = Integer.parseInt(date_today[0]);
				int month_today = Integer.parseInt(date_today[1]);
				int day_check = Integer.parseInt(date_check[1]);
				int month_check = Integer.parseInt(date_check[0]);
				int year_today = Integer.parseInt(date_today[2]);
				int year_check = Integer.parseInt(date_check[2]);
			
				if (month_check>12 || day_check>31 || year_check > year_today+1)
					return false;
				else
				if (month_today > month_check)
					return false;
				else
					if (day_today > day_check)
						return false;
					else
						return true;
			}
			else
				
				return false;

		}	
		else
			return false;
	
	}

	public boolean isnotAlpha(String name) {
	    char[] chars = name.toCharArray();

	    for (char c : chars) {
	        if(!Character.isLetter(c)) {
	            return true;
	        }
	    }

	    return false;
	}


	public int countLetter(String StringToCheck)
	{
		
		String str = StringToCheck;
		int letterCount = 0;
		int index = -1;
		while((index = str.indexOf('/', index+1)) > 0)
		    letterCount++;
		return letterCount;
	}
	}