package com.scrims.main;


import java.io.IOException;  
import java.io.PrintWriter;  
  
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;  
import javax.servlet.ServletException;  
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
import javax.servlet.http.HttpSession;  
  








import com.scrims.main.DBInterface;  
public class ReceiverTeamEdit extends HttpServlet{  
    private static final long serialVersionUID = 1L;  
    private PrintWriter out;
    private DBInterface DB;
    
    public void doPost(HttpServletRequest request, HttpServletResponse response)    
            throws ServletException, IOException {    
    	
    	//Graphic Interface:
    	request.getRequestDispatcher("/Main.jsp").include(request, response);
    	
        response.setContentType("text/html");    
        out  = response.getWriter();    
        try {
			DB = new DBInterface();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        String name = request.getParameter("name");
        String tag = request.getParameter("tag");
        String info = request.getParameter("info");
        String team_type = request.getParameter("team_type");
        String team_div = request.getParameter("team_div");
        String steamid = request.getParameter("steamid");
        Team team = CookieInterface.getTeamFromCookies(request);
        
        if (name !=null)
        {
        	if (checkPostData(name,50))
        	{
        		try {
					DB.changeTeamValue("TEAM_NAME",name,String.valueOf(team.getID()));
					CookieInterface.reloadCookies(response,request); 
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		out.println("<div class='alert alert-success'><strong>Success:</strong> Name changed!</div>");
        	}
        	else
        	{
        		out.println("<div class='alert alert-danger'><strong>Error:</strong> Please insert right team name value. (MAX. 50 letters with no special chars!)</div>");
        	}
        }
        if (tag !=null)
        {
        	if (checkPostData(tag,10))
        	{
        		try {
					DB.changeTeamValue("TEAM_TAG",tag,String.valueOf(team.getID()));
					CookieInterface.reloadCookies(response,request); 
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		out.println("<div class='alert alert-success'><strong>Success:</strong> Tag changed!</div>");
        	}
        	else
        	{
        		out.println("<div class='alert alert-danger'><strong>Error:</strong> Please insert right tag value. (MAX. 10 letters with no special chars!)</div>");
        	}
        }
        if (info !=null)
        {
        	if (checkPostData(info,100))
        	{
        		try {
					DB.changeTeamValue("TEAM_INFO",info,String.valueOf(team.getID()));
					CookieInterface.reloadCookies(response,request); 
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		out.println("<div class='alert alert-success'><strong>Success:</strong>Info changed!</div>");
        	}
        	else
        	{
        		out.println("<div class='alert alert-danger'><strong>Error:</strong> Please insert right info value. (MAX. 100 letters with no special chars!)</div>");
        	}
        }
        if (steamid !=null)
        {
        	if (SteamID.convertSteamIdToCommunityId(steamid)!=0 || checkPostData(steamid,50))
        	{
        		try {
					DB.changeTeamValue("TEAM_STEAMID",steamid,String.valueOf(team.getID()));
					CookieInterface.reloadCookies(response,request); 
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		out.println("<div class='alert alert-success'><strong>Success:</strong> Steamid changed!</div>");
        	}
        	else
        	{
        		out.println("<div class='alert alert-danger'><strong>Error:</strong> Please insert right steamid value. (MAX. 50 letters with no special chars!)</div>");
        	}
        }
        
        if (team_div!=null)
        
        {
        	try {
				DB.changeTeamValue("TEAM_DIV",team_div,String.valueOf(team.getID()));
				CookieInterface.reloadCookies(response,request);
				out.println("<div class='alert alert-success'><strong>Success:</strong> Division changed!</div>");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        	
        }
        /*if (team_type!=null)
        {
        	try {
				DB.changeTeamValue("TEAM_TYPE",team_type,String.valueOf(team.getID()));
				CookieInterface.reloadCookies(response,request);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }*/
        out.println("<a href='Home.jsp'><input type='button' class='btn btn-primary' value='Home'></a> "); 
        out.println("<INPUT Type='button' class='btn btn-primary' VALUE='Back' onClick='history.go(-1);return true;'>");
    }    
    
    
    private boolean checkPostData(String String_check,int Max_length)
    {
    	if (!String_check.trim().isEmpty() && String_check !=null && String_check.length() < Max_length && specialCharacter(String_check))
    		return true;
    	else
    		return false;
    }
    
    
	private boolean specialCharacter(String stringSearch) {


		Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(stringSearch);
		boolean b = m.find();
		
		if (b) //if found special element
			return false;
		else
			return true;
	}



}   