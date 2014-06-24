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
public class Receiver extends HttpServlet{  
    private static final long serialVersionUID = 1L;  
    private PrintWriter out;
    private DBInterface DB;
    
    public void doPost(HttpServletRequest request, HttpServletResponse response)    
            throws ServletException, IOException {    
    	
    	//Graphic Interface:
    	request.getRequestDispatcher("/Main.jsp").include(request, response);
    	
        response.setContentType("text/html");    
        out  = response.getWriter();    

        String TEAM_NAME = request.getParameter("team_name");    
        String TEAM_TAG = request.getParameter("team_tag");   
        String TEAM_TYPE = request.getParameter("team_type");   
        String TEAM_DIV = request.getParameter("team_div");   
        String TEAM_LOGIN = request.getParameter("team_leaderlogin");   
        String TEAM_PASS = request.getParameter("team_leaderpass");
        String TEAM_INFO = request.getParameter("team_info");
        String TEAM_STEAMID = request.getParameter("team_steamid");
        
        try {
        	DB = new DBInterface();
			checkPostData(TEAM_NAME,TEAM_TAG,TEAM_TYPE,TEAM_DIV,TEAM_LOGIN,TEAM_PASS,TEAM_INFO,TEAM_STEAMID);
		} catch (SQLException | NoSuchAlgorithmException | InvalidKeySpecException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 

        out.println("<a href='Home.jsp'><input type='button' class='btn btn-primary' value='Home'></a> "); 
        out.println("<INPUT Type='button' class='btn btn-primary' VALUE='Back' onClick='history.go(-1);return true;'>");
    }    
    
    private void checkPostData(String TEAM_NAMEC, String TEAM_TAGC, String TEAM_TYPEC,String TEAM_DIVC,String TEAM_LOGINC,String TEAM_PASSC,String TEAM_INFOC,String TEAM_STEAMID) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException 
    {
		if (!TEAM_NAMEC.trim().isEmpty() && TEAM_NAMEC !=null && TEAM_NAMEC.length() < 51 && specialCharacter(TEAM_NAMEC))
			if (!TEAM_TAGC.trim().isEmpty() && TEAM_TAGC !=null && TEAM_TAGC.length() < 11)
				if (!TEAM_LOGINC.trim().isEmpty() && TEAM_LOGINC !=null && TEAM_LOGINC.length() < 31 && specialCharacter(TEAM_LOGINC))
					if (!TEAM_PASSC.trim().isEmpty() && TEAM_PASSC !=null && TEAM_PASSC.length() < 31)
						//IF DATA IS VALID , WE CAN PROCEED TO SQL CHECK
						if (!TEAM_STEAMID.trim().isEmpty() && TEAM_STEAMID !=null && TEAM_STEAMID.length()< 51 && SteamID.convertSteamIdToCommunityId(TEAM_STEAMID)!=0 )
					
							checkSQLData( TEAM_NAMEC,  TEAM_TAGC,  TEAM_TYPEC, TEAM_DIVC, TEAM_LOGINC, TEAM_PASSC, TEAM_INFOC,TEAM_STEAMID);
						else
							out.println("<div class='alert alert-danger'><strong>Error:</strong> Please insert right team steamid value. (MAX. 50 letters)</div>");
					else
		
						out.println("<div class='alert alert-danger'><strong>Error:</strong> Please insert right team pass value. (MAX. 30 letters)</div>");
				else
	
					out.println("<div class='alert alert-danger'><strong>Error:</strong> Please insert right team login value. (MAX. 30 letters with no special chars!)</div>");
			else
				out.println("<div class='alert alert-danger'><strong>Error:</strong> Please insert right team tag value. (MAX. 10 letters)</div>");
		else
			out.println("<div class='alert alert-danger'><strong>Error:</strong> Please insert right team name value. (MAX. 50 letters with no special chars!)</div>");
		
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

	private void checkSQLData(String TEAM_NAMEC, String TEAM_TAGC, String TEAM_TYPEC, String TEAM_DIVC, String TEAM_LOGINC, String TEAM_PASSC, String TEAM_INFOC,String TEAM_STEAMID) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException 
	{
		if (DB.checkIfExists("TEAM_NAME",TEAM_NAMEC)==0)
			if (DB.checkIfExists("TEAM_LOGIN",TEAM_LOGINC)==0)
			{
				System.out.println(TEAM_STEAMID);
				if (DB.insert(TEAM_NAMEC,  TEAM_TAGC,  TEAM_TYPEC, TEAM_DIVC, TEAM_LOGINC, TEAM_PASSC, TEAM_INFOC, TEAM_STEAMID)==1)
				{
					
					out.println("<div class='alert alert-success'><strong>Success:</strong> Registration successful! You can now login to your team panel!<br><strong>Login: </strong>"+TEAM_LOGINC+"<br><strong>Pass: </strong>"+TEAM_PASSC+"</div>");
					
				}
				else
					out.println("<div class='alert alert-danger'><strong>Error:</strong> Database error. Please try again!</div>");
			}
			else
				out.println("<div class='alert alert-danger'><strong>Error:</strong> There is already team with this login!</div>");
		else
			out.println("<div class='alert alert-danger'><strong>Error:</strong> There is already team with this name!</div>");
	}


}   