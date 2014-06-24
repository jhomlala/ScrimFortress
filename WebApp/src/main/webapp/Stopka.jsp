 
 
 
<%@page import="java.text.DateFormat" %>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="java.util.Calendar" %>
<%@page import="java.util.Date" %>
 
 <%		DateFormat df = new SimpleDateFormat("    HH:mm:ss '|' dd-MM-yyyy");
Date today = Calendar.getInstance().getTime();        

String todayDate = df.format(today); %>

 </div>


<footer>
<br>
<center><a href="http://www.github.com/jhomlala/Scrim-Fortress" ><img src="files/GitHub-Mark-64px.png" width = 24;></img></a> V. 0.2 | © 2014 |  Created by <a href="http://steamcommunity.com/profiles/76561198024790295">Jakub Homlala</a>    |

<%=todayDate %><br>
<a href="Home.jsp">Home</a> | <a href="Scrims.jsp">Scrims</a> | <a href="Register.jsp">Register</a> | <a href="Contact.jsp">Contact</a> | <a href="Panel.jsp">Panel</a> | <a href="Timezones.jsp">Timezones</a>
</center>
</footer>
  
 
  

    <script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css"></script>
 	<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
	
</body>
</html> 