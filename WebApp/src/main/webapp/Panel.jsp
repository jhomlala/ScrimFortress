
<%@page import="com.scrims.main.DBInterface"%>
<%@page import="com.scrims.main.Scrim"%>
<%@page import="com.scrims.main.Team"%>
<%@page import="com.scrims.main.CookieInterface"%>
<%@page import="com.scrims.main.SteamID"%>
<head>
	<script src="//code.jquery.com/jquery-1.10.2.js"></script>
	<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
	<script type="text/javascript" src="files/js/tooltipsy.min.js"></script>
</head>
<% 
Team team = null;
int approve = 0;
 String Name=null;
 String Tag=null;
 String Div=null;
 String Type=null;
 String Login=null;
 String Info=null;
 String SteamID=null;
 int ID=-1;
 if ((CookieInterface.getTeamName(request)==null))
{
	approve = 0;
	response.getWriter().print("<HEAD><META HTTP-EQUIV='Refresh' CONTENT='0; URL=Home.jsp'></HEAD>");
}
else
{
	approve = 1;
 	team = CookieInterface.getTeamFromCookies(request);
 	Name = team.getName();
 	Type = team.getType();
 	Tag = team.getTag();
 	Div = team.getDiv();
 	ID = team.getID();
 	Login = team.getLogin();
 	Info = team.getInfo();
 	SteamID = team.getSteamID();

}

%>
<jsp:include page="Main.jsp" />
<div class="alert alert-success alert-dismissable">
  <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
  <strong>Success!</strong> You logged into team panel succesfully!
</div>

<legend>Team Panel</legend>
<strong></strong>
<table class="table table-bordered" style="width: 50%">
  <tr class = "info">
    <td ><strong>ID</strong><br></td>
    <td ><%=ID%><br></td>
    <td><input type='button' class='btn btn-primary disabled' value='Change'></td>
  </tr>
  <tr class = "info">
    <td ><strong>Name</strong></td>
    <td ><%=Name%><br></td>
     <td> <a href='TeamEdit.jsp?action=name'><input type='button' class='btn btn-primary' value='Change'></a></td>
  </tr>
  <tr class = "info">
    <td ><strong>Division</strong></td>
    <td ><%=Div%></td>
     <td> <a href='TeamEdit.jsp?action=div'><input type='button' class='btn btn-primary' value='Change'></a></td>
  </tr>
  <tr class = "info">
    <td ><strong>Type</strong></td>
    <td ><%=Type%></td>
     <td><input type='button' class='btn btn-primary disabled' value='Change'></td>
  </tr>
    <tr class = "info">
    <td ><strong>Tag</strong></td>
    <td ><%=Tag%></td>
     <td> <a href='TeamEdit.jsp?action=tag'><input type='button' class='btn btn-primary' value='Change'></a></td>
  </tr>
  <tr class = "info">
    <td ><strong>Info</strong></td>
    <td ><%=Info%></td>
     <td> <a href='TeamEdit.jsp?action=info'><input type='button' class='btn btn-primary' value='Change'></a></td>
  </tr>
  <tr class = "info">
    <td ><strong>Login</strong></td>
    <td ><%=Login%></td>
     <td><input type='button' class='btn btn-primary disabled' value='Change'></td>
  </tr>
  <tr class = "info">
    <td ><strong>SteamID</strong></td>
    <td ><%=SteamID%></td>
     <td> <a href='TeamEdit.jsp?action=steamid'><input type='button' class='btn btn-primary' value='Change'></a></td>
  </tr>
</table>
 <a href='Logout.jsp'><input type='button' class='btn btn-primary' value='Logout'></a>
 <a href='Scrims.jsp'><input type='button' class='btn btn-primary' value='Scrims'></a></td>
 <a href='ScrimAdd.jsp'><input type='button' class='btn btn-primary' value='Add Scrim'></a>
	<br><br><br>
<legend>Scrims</legend>
<div class="panel panel-default">
  <!-- Default panel contents -->
  <div class="panel-heading">Your Team Scrims</div>


  <!-- Table -->
  <table class="table">
   <tr class = "info">
    <td ><strong>ID</strong></td>
	<td ><strong>Team 1</strong></td>
	<td ><strong>Team 2</strong></td>
    <td ><strong>Date</strong></td>
    <td ><strong>Time</strong></td>
    <td ><strong>Info</strong></td>
    <td ><strong>Action </strong></td>
  </tr>
	<% 
	DBInterface db = new DBInterface();
	SteamID sid = new SteamID();
	int scrimCount = db.checkScrimNumberForTeam(ID);
	if ((CookieInterface.getTeamName(request)==null))
	{
		approve = 0;
		response.getWriter().print("<HEAD><META HTTP-EQUIV='Refresh' CONTENT='0; URL=Home.jsp'></HEAD>");
	}
	else
	{
		
		if (scrimCount > 0)
		{
			Scrim scrims[] = new Scrim [100]; // declare max 100 scrims (i dont think so it would be more)
			db.loadScrimsForTeam(scrims,ID);
			
			while (scrims==null)
			{
				
			}
			for (int i=0;i<scrimCount;i++)
			{
			    
			
				int id = scrims[i].getID();
				int team1 = scrims[i].getTeam1_ID();
				int team2 = scrims[i].getTeam2_ID();
				String date =  scrims[i].getDate();
				String time =  scrims[i].getTime();
				String info =  scrims[i].getInfo();
			
				Team Team1 = new Team();
				Team Team2 = new Team();
				db.loadTeamDataFromID(Team1,team1 );
				
				if (!(team2 == -1) )
				{
					db.loadTeamDataFromID(Team2,team2 );
				
					out.print("<tr>");
					out.print("<td>");
					out.print(id);
					out.print("</td>");
					out.print("<td>");
					out.print("<b><a class='hastip' title='(ID:"+Team1.getID()+")"+Team1.getName()+"<br><a href=http://steamcommunity.com/profiles/"+sid.convertSteamIdToCommunityId(Team1.getSteamID())+">Leader profile</a>'>"+Team1.getTag()+"</a></b>");
					out.print(" <span class='label label-success'>("+Team1.getDiv()+")</span>");
					out.print("</td>");
					out.print("<td>");
					out.print("<b><a class='hastip' title='(ID:"+Team2.getID()+")"+Team2.getName()+"<br><a href=http://steamcommunity.com/profiles/"+sid.convertSteamIdToCommunityId(Team2.getSteamID())+">Leader profile</a>'>"+Team2.getTag()+"</a></b>");
					out.print(" <span class='label label-success'>("+Team2.getDiv()+")</span>");
					out.print("</td>");
					out.print("<td>");
					out.print(date);
					out.print("</td>");
					out.print("<td>");
					out.print(time);
					out.print("</td>");
					out.print("<td>");
					out.print(info);
					out.print("</td>");
					out.print("<td>");
					if (Team1.getID() == team.getID())
					{
						out.print("<a href='ScrimRemove.jsp?id="+scrims[i].getID()+"'><i class='glyphicon glyphicon-remove-sign'></i></a>");
					}
					else
						out.print(" ");
					out.print("</td>");
					
					out.print("</tr>");
					
					}
					else
					{
						out.print("<tr>");
						out.print("<td>");
						out.print(id);
						out.print("</td>");
						out.print("<td>");
						out.print("<b><a class='hastip' title='(ID:"+Team1.getID()+")"+Team1.getName()+"<br><a href=http://steamcommunity.com/profiles/"+sid.convertSteamIdToCommunityId(Team1.getSteamID())+">Leader profile</a>'>"+Team1.getTag()+"</a></b>");
						out.print(" <span class='label label-success'>("+Team1.getDiv()+")</span>");
						out.print("</td>");
						out.print("<td>");
						out.print("none");
						out.print("</td>");
						out.print("<td>");
						out.print(date);
						out.print("</td>");
						out.print("<td>");
						out.print(time);
						out.print("</td>");
						out.print("<td>");
						out.print(info);
						out.print("</td>");
						out.print("<td>");
						if (Team1.getID() == team.getID())
						{
							out.print("<a href='ScrimRemove.jsp?id="+scrims[i].getID()+"'><i class='glyphicon glyphicon-remove-sign'></i></a>");
						}
						else
							out.print(" ");
						out.print("</td>");
						out.print("</tr>");
					}
				}
				out.print(" </table></div>");
		
		}
		else
		{
		
			out.print("</table></div>Sorry there is no scrims to get! You can <a href='ScrimAdd.jsp'>Add a scim</a>!");
		}
	}
	%>
	
	<legend>Pending</legend>
 <div class="panel panel-default">
  <!-- Default panel contents -->
  <div class="panel-heading">Teams pending for your scrims</div>


  <!-- Table -->
  <table class="table">
   <tr class = "info">
    <td ><strong>Scrim ID</strong></td>

	<td ><strong>Date<strong></td>
	<td ><strong>Time<strong></td>
	<td ><strong>Teams<strong></td>
  </tr>
	<% 
	if ((CookieInterface.getTeamName(request)==null))
	{
		approve = 0;
		response.getWriter().print("<HEAD><META HTTP-EQUIV='Refresh' CONTENT='0; URL=Home.jsp'></HEAD>");
	}
	else
	{
		 db = new DBInterface();
		 scrimCount = db.checkScrimNumberForTeam(ID);

		if (scrimCount > 0)
		{
			Scrim scrims[] = new Scrim [100]; // declare max 100 scrims (i dont think so it would be more)
			int count = db.countScrimsForTeamWherePending( ID);
			
			db.loadScrimsForTeamWherePending(scrims,ID);
			System.out.println("AAA"+count);
			if (count>0)
			{
				for (int i=0;i<count;i++)
				{
					out.print("<tr>");
					out.print("<td>"+scrims[i].getID()+"</td>");
					out.print("<td>"+scrims[i].getDate()+"</td>");
					out.print("<td>"+scrims[i].getTime()+"</td>");
					
					String list = db.getScrimPendingList(scrims[i].getID());
					String team_list[] = list.split(",");
					
					out.print("<td>");
					if (team_list.length > 1 )
					{
						for (int k=0;k<team_list.length;k++)
						{
							if (!(Integer.parseInt(team_list[k]) == -1))
							{
								Team team_local = new Team();
								db.loadTeamDataFromID(team_local,Integer.parseInt(team_list[k]));
								out.print("<b>"+team_local.getTag() + "</b> ("+ team_local.getDiv()+") | ");
								out.print("<a href='ScrimAccept.jsp?id="+scrims[i].getID()+"&teamid="+team_local.getID()+"'><input type='button' class='btn btn-primary btn-xs' value='Accept'></a>");
								out.print("<br>");
							}
						}
					}
					else
					{
						out.print("None");
					}
					out.print("</td>");
					out.print("</tr>");
				}
			}
			out.print(" </table></div>");
		
		}
		else
		{
		
			out.print("</table></div>Sorry there is no scrims to get! You can <a href='ScrimAdd.jsp'>Add a scim</a>!");
		}
	}
	%>
<script type="text/javascript">
$('.hastip').tooltipsy();
</script>
	
<jsp:include page="Stopka.jsp" />