/*****************************************************************************************************************************
Scrim Fortress Project
File: Mailbox.java
Description: Mailbox interface , which users can communicate between teams.
Author: Jakub Homlala
****************************************************************************************************************************/


//***************************************************************************************************************************
// IMPORTS
//***************************************************************************************************************************

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
  




//***************************************************************************************************************************
//Main class 
//***************************************************************************************************************************

public class Mailbox extends HttpServlet{  
	
    private static final long serialVersionUID = 1L;  
    private PrintWriter out;
    private DBInterface DB;
    private HttpServletRequest G_request;
    private HttpServletResponse G_response;
    private Team team;
    //this class is like a main class 
    //every work will start from here
    
    public void doGet(HttpServletRequest request, HttpServletResponse response)    
            throws ServletException, IOException 
            {    

    			//setup needed data
    			G_request = request;
    			G_response = response;
    			
    			out = response.getWriter();
    			try {
    			//setup db connection 
					DB = new DBInterface();
				} catch (SQLException e) {
					e.printStackTrace();
				}
    			
    			// load Main.jsp
    			request.getRequestDispatcher("/Main.jsp").include(request, response);
    			out.println("<legend>Mailbox</legend>");
    			//check does user is logged in
    			// we have no message for anonymous

    			if (CookieInterface.getTeamName(G_request)==null)
    			{
    				
    				out.println("<div class='alert alert-danger'><strong>Error:</strong> You are not logged in. We have no mails for you.</div>");
    				
    			}
    			else
    			{
    				loadTeam();
    				out.println(" <a href='MailAdd.jsp'><input type='button' class='btn btn-primary' value='Send a message'></a>");
	    			try {
						loadMailbox();
					} catch (SQLException e) {
						e.printStackTrace();
					}
    			}
    			
    			
    			request.getRequestDispatcher("/Stopka.jsp").include(request, response);
    			
            }
    
    
//***************************************************************************************************************************
//loadTeam()
//load team data from cookies for this class
//***************************************************************************************************************************   
  private void loadTeam() 
   {
		team = new Team();
	 	team = CookieInterface.getTeamFromCookies(G_request);
		
	}
 //***************************************************************************************************************************
 //loadMailbox()
 //loading mails from database
 //show them in table 
 //***************************************************************************************************************************
	private void loadMailbox() throws SQLException 
	{
		// IMPLEMENTATION OF INBOX
		// MESSAGES WE RECEIVED FROM ANOTHER TEAMS
		
		int mail_count = DB.countMailsForID(String.valueOf(team.getID()),0);
		out.println("<h4>You have total <b>"+mail_count+"</b> messages in inbox.</h4>");
		if (mail_count>0)
		{
			Mail[] mail_holder = new  Mail[1000];
			try {
				DB.loadMailsForID(String.valueOf(team.getID()), mail_holder,0);
				 
			} catch (SQLException e) {

				e.printStackTrace();
			}
				
				out.println("  <table class='table' >");
			    out.println("<tr class = 'info'>");
			    out.println(" <td ><strong>ID</strong></td>");
			    out.println(" <td ><strong>Date</strong></td>");
			    out.println(" <td ><strong>Time</strong></td>");
			    out.println(" <td ><strong>From</strong></td>");
			    out.println(" <td ><strong>Title</strong></td>");
			    out.println(" <td ><strong>Message</strong></td>");
			    out.println("</tr>");
			    
			    
			for (int i=0;i<mail_count;i++)
			{
				out.println("<tr>");
				out.println(" <td >");
				out.println(mail_holder[i].getID());
				out.println(" </td >");
				out.println(" <td >");
				out.println(mail_holder[i].getDate());
				out.println(" </td >");
				out.println(" <td >");
				out.println(mail_holder[i].getTime());
				out.println(" </td >");
				out.println(" <td >");
				Team Team1 = new Team();
				DB.loadTeamDataFromID(Team1,mail_holder[i].getAuthor_ID());
				out.println(Team1.getName());
				out.println(" </td >");
				out.println(" <td >");
				out.println(mail_holder[i].getTitle());
				out.println(" </td >");
				out.println(" <td >");
				out.println(mail_holder[i].getMessage());
				out.println(" </td >");
				out.println("</tr>");
			}
			out.println("</table>");
			
			
		}
		
		// IMPLEMENTATION OF OUTBOX
		// MESSAGES WE SENT TO ANOTHER TEAM
				 mail_count = DB.countMailsForID(String.valueOf(team.getID()),1);
				out.println("<h4>You have total <b>"+mail_count+"</b> messages in outbox.</h4>");
				if (mail_count>0)
				{
					Mail[] mail_holder = new  Mail[1000];
					try {
						DB.loadMailsForID(String.valueOf(team.getID()), mail_holder,1);
					} catch (SQLException e) {

						e.printStackTrace();
					}
						
						out.println("  <table class='table' >");
					    out.println("<tr class = 'info'>");
					    out.println(" <td ><strong>ID</strong></td>");
					    out.println(" <td ><strong>Date</strong></td>");
					    out.println(" <td ><strong>Time</strong></td>");
					    out.println(" <td ><strong>To</strong></td>");
					    out.println(" <td ><strong>Title</strong></td>");
					    out.println(" <td ><strong>Message</strong></td>");
					    out.println("</tr>");
					    
					    
					for (int i=0;i<mail_count;i++)
					{
						out.println("<tr>");
						out.println(" <td >");
						out.println(mail_holder[i].getID());
						out.println(" </td >");
						out.println(" <td >");
						out.println(mail_holder[i].getDate());
						out.println(" </td >");
						out.println(" <td >");
						out.println(mail_holder[i].getTime());
						out.println(" </td >");
						out.println(" <td >");
						Team team = new Team();
						DB.loadTeamDataFromID(team,mail_holder[i].getReceiver_ID());
						System.out.println(team.getName());
						out.println(team.getName());
						out.println(" </td >");
						out.println(" <td >");
						out.println(mail_holder[i].getTitle());
						out.println(" </td >");
						out.println(" <td >");
						out.println(mail_holder[i].getMessage());
						out.println(" </td >");
						out.println("</tr>");
					}
					out.println("</table>");
					
					
				}
			}
    
    
}   