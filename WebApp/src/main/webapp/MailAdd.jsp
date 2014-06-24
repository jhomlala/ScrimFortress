<%@page import="com.scrims.main.CookieInterface"%>
<%@page import="com.scrims.main.DBInterface"%>
<%@page import="com.scrims.main.Team"%>
<% 
if (CookieInterface.getTeamName(request)==null)
{
	response.getWriter().print("<HEAD><META HTTP-EQUIV='Refresh' CONTENT='0; URL=Home.jsp'></HEAD>");
	
}
%>
<jsp:include page="Main.jsp" />

<script src="<c:url value="files/js/block.js" />"></script>

<form class="form-horizontal" name="reg" method="POST" action="ReceiverMail" >

<legend>Send a message</legend>



  	<b>  To:</b><br> 

    <select id="teams" name="team" class="input-xlarge">

 	<%
 	DBInterface db = new DBInterface();
 	int count = db.countTeams();
 	Team teams[] = new Team[count];
 	teams = db.loadTeams(count);
 	for (int i=0;i<count;i++)
 	{
 		out.println("<option>"+ teams[i].getName()+"</option>");
 	}
 	%>
    </select><br><br>
  	<b>  Title:</b><input type="text"  name="title" class="form-control" placeholder="Title"><br>

  	<b>  Message:</b><textarea class="form-control" rows="3" name="msg" placeholder="500 chars"></textarea>
  	
  <br>
  <button type="submit" class="btn btn-primary">Submit</button>
</form>



<jsp:include page="Stopka.jsp" />
