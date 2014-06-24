
<%@page import="com.scrims.main.CookieInterface"%>
<%@page import="javax.servlet.http.Cookie"%>
<% String site = "Home.jsp" ;

	//session.setAttribute("TEAM",null);
	
	//session.invalidate();
	//CookieInterface.removeTeamCookies(request, response);
	 response.setContentType("text/html");
	
		 Cookie[] cookielist = request.getCookies();
		
	     if (cookielist!= null) {
	         for (int i = 0; i < cookielist.length; i++) 
	         	{
	        	 
	        	 
	        	 Cookie delCookie = new Cookie(cookielist[i].getName(), cookielist[i].getValue());

	        	 //delCookie.setDomain();
	        	 delCookie.setPath(cookielist[i].getPath());
	        	 delCookie.setMaxAge(0);
	        	 response.addCookie(delCookie);
	        	 
	        	 
	        	 
	         
	           }
	        }
	      cookielist = request.getCookies();
	response.setStatus(response.SC_MOVED_TEMPORARILY);
	response.setHeader("Location", site); 
out.println("<a href='Home.jsp'>Home</a>");
	%>