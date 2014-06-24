<jsp:include page="Main.jsp" />
<%@page import="com.scrims.main.Team"%>
<%@page import="com.scrims.main.DBInterface"%>
<%@page import="com.scrims.main.CookieInterface"%>
<%@page import="com.scrims.main.Scrim"%>
<legend>Scrim Contact</legend>
<%



if (CookieInterface.getTeamName(request)==null)
{
	out.println("<div class='alert alert-danger'><strong>Error!</strong> Please login to add your team to scrim!</div>");
	
}
else
{
	Team team = CookieInterface.getTeamFromCookies(request);

	String scrimid=request.getParameter("id");
	
	DBInterface db = new DBInterface();
	if (!(db.checkIfTeamIsSameTeam(Integer.parseInt(scrimid),team.getID())))
	{
		if (db.checkIfAlreadyAddedToPendingList(Integer.parseInt(scrimid),team.getID())) // check if we already added to this match ( we dont want to be added twice )
		{
			out.println("<div class='alert alert-danger'><strong>Error!</strong> You already added your team to this match!</div>");
		}
		else
		{
			Scrim scrim = new Scrim();
			scrim = db.loadScrimWithID(Integer.parseInt(scrimid));
			Team team_to_load = new Team();
			
			db.loadTeamDataFromID(team_to_load, scrim.getTeam1_ID());
			if (team_to_load.getType().equals(team.getType()))
			{
				db.addTeamToPendingList(Integer.parseInt(scrimid),team.getID());
				System.out.println(Integer.parseInt(scrimid));
				System.out.println(team.getID());
				out.println("<div class='alert alert-success'><strong>Sucess:</strong> You added your team to this match!</div>");
			}
			else
				out.println("<div class='alert alert-danger'><strong>Error:</strong> You cant play with not same team type as your!</div>");
		}
	}
	else
	{
		out.println("<div class='alert alert-danger'><strong>Error:</strong> You cant play with yourself!</div>");
	}
}
%>

 <a href='Scrims.jsp'><input type='button' class='btn btn-primary' value='Back'></a></td>
<jsp:include page="Stopka.jsp" />
