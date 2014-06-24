<jsp:include page="Main.jsp" />
<%@page import="com.scrims.main.Team"%>
<%@page import="com.scrims.main.DBInterface"%>
<%@page import="com.scrims.main.Scrim"%>
<%@page import="com.scrims.main.CookieInterface"%>
<legend>Accept</legend>
<div class="alert alert-success alert-dismissable">

<%
String scrimid = request.getParameter("id");
String teamid = request.getParameter("teamid");


if (CookieInterface.getTeamName(request)==null)

	response.getWriter().print("<HEAD><META HTTP-EQUIV='Refresh' CONTENT='0; URL=Home.jsp'></HEAD>");
		

else
{
	Team team = CookieInterface.getTeamFromCookies(request);
	if (teamid==null|| scrimid==null)
	{
		response.getWriter().print("<HEAD><META HTTP-EQUIV='Refresh' CONTENT='0; URL=Home.jsp'></HEAD>");
	}
	else
	{
		
			Team team_accepted = new Team();
			DBInterface db = new DBInterface();
			db.loadTeamDataFromID(team_accepted,Integer.parseInt(teamid));
			
			out.print("<strong>Success!</strong> Accepted team:");
			out.print("<br><b>ID("+team_accepted.getID()+"): ");
			out.print(team_accepted.getTag());
			out.print(" | "+team_accepted.getDiv());
			out.print("<br></b>For scrim with id:" +scrimid);
			
			
			//remove from pending list
			//add team to team2 column in db
			
			db.removeFromPendingList(Integer.parseInt(scrimid));
			db.addTeam2ToScrimList(Integer.parseInt(scrimid), Integer.parseInt(teamid));
		}
		
	
}
 
%>
</div>
 <a href='Panel.jsp'><input type='button' class='btn btn-primary' value='Back'></a></td>
<jsp:include page="Stopka.jsp" />