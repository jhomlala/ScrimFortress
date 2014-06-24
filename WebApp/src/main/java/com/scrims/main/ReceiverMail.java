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
public class ReceiverMail extends HttpServlet{  
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
        String title = request.getParameter("title");
        String msg = request.getParameter("msg");
        String teamformsg = request.getParameter("team");
        
        Team team = CookieInterface.getTeamFromCookies(request);
        
        if (title!=null)
        	if (checkPostData(title,50))
        		if (msg!=null)
        			if (checkPostData(msg,500))
        			{
        				if (team.getName().equals(teamformsg))
        				{
        					out.println("<div class='alert alert-danger'><strong>Error:</strong> You cant write message to yourself.</div>");
        				}
        				else
        				{
        				out.println("<div class='alert alert-success'><strong>Success:</strong>Message sent</div>");
						try {
							DB.addMessage(team.getID(),title,msg,teamformsg);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
        				}
						
        			}
        			else
        				out.println("<div class='alert alert-danger'><strong>Error:</strong> Please write a message. (MAX. 500 chars)</div>");
        		else
        			out.println("<div class='alert alert-danger'><strong>Error:</strong> Please write a message. (MAX. 500 chars)</div>");
        	else
        		out.println("<div class='alert alert-danger'><strong>Error:</strong> Please write a title. (MAX. 50 chars)</div>");
        else
        	out.println("<div class='alert alert-danger'><strong>Error:</strong> Please write a title. (MAX. 50 chars)</div>");

        
        
        out.println("<a href='Home.jsp'><input type='button' class='btn btn-primary' value='Home'></a> "); 
        out.println("<INPUT Type='button' class='btn btn-primary' VALUE='Back' onClick='history.go(-1);return true;'>");
        request.getRequestDispatcher("/Stopka.jsp").include(request, response);
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