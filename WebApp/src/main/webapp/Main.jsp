 

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.scrims.main.Team"%>
<%@page import="com.scrims.main.DBInterface"%>
<%@page import="com.scrims.main.CookieInterface"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<link rel="stylesheet" href="files/css/bootstrap.min.css">
<link rel="stylesheet" href="files/styl.css">
<link rel="stylesheet" href="files/datepicker.css">

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ScrimFortress.tf</title>

<link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">





</head>
<body>
   <div class="container">
    <!-- Fixed navbar -->
    <div class="navbar navbar-default navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
       		  <a class="navbar-brand" rel="home" href="Home.jsp" title="ScrimFortress.tf"><img style="max-width:50px; max-height:100px; margin-top: -20px;" src="files/logo.png" >ScrimFortress.tf</a>
          
        </div>
        <div class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
            <li ><a href="Home.jsp"><span class="glyphicon glyphicon-home"></span> Home</a></li>
            
           
            <li><a href="Scrims.jsp"> Scrims <span class="badge"><% DBInterface db = new DBInterface(); out.print(db.checkScrimNumber());%></span></a></li>
           
            <li><a href="TeamPanel.jsp"> Team panel</a></li>
            <li><a href="Contact.jsp">Contact</a></li>
			<li><a href="Timezones.jsp">Timezones</a></li>
          
          
          <% 

          if (!(CookieInterface.getTeamName(request)==null))
			{    
        	    out.print(" </ul>");
        	    
				out.print("<p class='navbar-text navbar-right'></span><a href='Mailbox'><span class='glyphicon glyphicon-envelope'></a> Logged as <b><a href='TeamPanel.jsp' class='navbar-link'>"+CookieInterface.getTeamName(request)+"</a> </b>|  <a href='Logout.jsp'>Logout</a></p>");
			}
          else
        	  	out.print(" <li><a href='Register.jsp'> Register</a></li>");
          		out.print(" </ul>");
			%>
         
        </div><!--/.nav-collapse -->
      </div>
    </div>

<div class="page-header">
 	<h1>Welcome to Scrim Fortress!</h1>
 </div>
<div class="alert alert-info">
 	 <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
  <strong>Information!</strong> This site is currently in alfa phase. If you found bugs, please contact creators!
 </div>
<div class="well">
	
	
	









