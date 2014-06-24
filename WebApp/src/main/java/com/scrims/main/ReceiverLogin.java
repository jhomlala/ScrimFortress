package com.scrims.main;

 
import java.io.IOException;  
import java.io.PrintWriter;  
  
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;  
import javax.servlet.ServletException;  
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
import javax.servlet.http.HttpSession;  
  





import com.scrims.main.DBInterface;  
public class ReceiverLogin extends HttpServlet{  
    private static final long serialVersionUID = 1L;  
    private PrintWriter out;
    private DBInterface DB;
    private HttpSession session;
    private Team team = new Team();
    private HttpServletRequest G_request;
    private HttpServletResponse G_response;
    public void doPost(HttpServletRequest request, HttpServletResponse response)    
            throws ServletException, IOException {    
    	
    	G_request = request;
    	G_response = response;
    	//Graphic Interface:
    	request.getRequestDispatcher("/Main.jsp").include(request, response);
    	
    	
    	// Session Interface:
    	session = request.getSession();
    	
        response.setContentType("text/html");    
        out  = response.getWriter();    

        String TEAM_LOGIN = request.getParameter("login");    
        String TEAM_PASS = request.getParameter("password");   

        
        try {
        	DB = new DBInterface();
			checkPostData(TEAM_LOGIN,TEAM_PASS);
		} catch (SQLException | NoSuchAlgorithmException | InvalidKeySpecException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 

        out.println("<a href='Home.jsp'><input type='button' class='btn btn-primary' value='Home'></a> "); 
        out.println("<INPUT Type='button' class='btn btn-primary' VALUE='Back' onClick='history.go(-1);return true;'>");
    }    
    
    private void checkPostData(String TEAM_LOGIN, String TEAM_PASS) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException, ServletException, IOException 
    {
		if (!TEAM_LOGIN.trim().isEmpty() && TEAM_LOGIN !=null && TEAM_LOGIN.length() < 31)
			if (!TEAM_PASS.trim().isEmpty() && TEAM_PASS !=null && TEAM_PASS.length() < 31)
			{
				checkSQLData(TEAM_LOGIN,TEAM_PASS);
			}
			else
				out.println("<div class='alert alert-danger'><strong>Error:</strong> Please insert right team pass value. (MAX. 30 letters)</div>");
		else
			out.println("<div class='alert alert-danger'><strong>Error:</strong> Please insert right team login value. (MAX. 30 letters)</div>");
		
	}

	private void checkSQLData(String TEAM_LOGIN, String TEAM_PASS) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException, ServletException, IOException 
	{
		if (DB.login(TEAM_LOGIN, TEAM_PASS)==1)
		{
			
			DB.loadTeamData(team,TEAM_LOGIN);
			while (team.getInfo()==null)
			{
				// do nothing until team receive info from DB.
			}
			

			//session.setAttribute("TEAM",team);
			
			Cookie cookie = new Cookie((String)session.getId(),String.valueOf(team.getID()));
			cookie.setMaxAge(60*60);
			
			CookieInterface.setCookiesTeamValues(G_response,team);
			
			G_response.addCookie(cookie);
			
			String site = "Panel.jsp" ;
			G_response.setStatus(G_response.SC_MOVED_TEMPORARILY);
			G_response.setHeader("Location", site); 
			
			
		}
		else
		out.println("<div class='alert alert-danger'><strong>Error:</strong>Wrong login/pass!</div>");
	}


}   