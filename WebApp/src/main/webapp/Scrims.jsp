<%@page import="com.scrims.main.DBInterface"%>
<%@page import="com.scrims.main.Scrim"%>
<%@page import="com.scrims.main.Team"%>
<%@page import="com.scrims.main.SteamID"%>
<jsp:include page="Main.jsp" />
<head>
	<script src="//code.jquery.com/jquery-1.10.2.js"></script>
	<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
	<script type="text/javascript" src="files/js/tooltipsy.min.js"></script>
</head>

<legend>Scrims</legend>
<div class="panel panel-default">
  <!-- Default panel contents -->
  <div class="panel-heading">Actual Scrims</div>


  <!-- Table -->
  <table class="table">
   <tr class = "info">
    <td ><strong>ID</strong></td>
    <td ><strong>Type</strong></td>
	<td ><strong>Team 1</strong></td>
	<td ><strong>Team 2</strong></td>
    <td ><strong>Date</strong></td>
    <td ><strong>Time</strong></td>
    <td ><strong>Info</strong></td>
  </tr>
	<% 
	DBInterface db = new DBInterface();
	int scrimCount = db.checkScrimNumber();
	if (scrimCount > 0)
	{
		Scrim scrims[] = new Scrim [100]; // declare max 100 scrims (i dont think so it would be more)
		db.loadScrims(scrims);
		
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
			if (Team1.getType().equals("9v9"))
				out.print("<span class='label label-info'>9v9</span>");
			else if (Team1.getType().equals("6v6"))
					out.print("<span class='label label-info'>6v6</span>");
				
			out.print("</td>");
			out.print("<td>");
			
			
			out.print("<b><a class='hastip' title='(ID:"+Team1.getID()+")"+Team1.getName()+"<br><a href=http://steamcommunity.com/profiles/"+SteamID.convertSteamIdToCommunityId(Team1.getSteamID())+">Leader profile</a>'>"+Team1.getTag()+"</a></b>");
			out.print("</td>");
			out.print("<td>");
			out.print("<b><a class='hastip' title='(ID:"+Team2.getID()+")"+Team2.getName()+"<br><a href=http://steamcommunity.com/profiles/"+SteamID.convertSteamIdToCommunityId(Team2.getSteamID())+">Leader profile</a>'>"+Team2.getTag()+"</a></b>");
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
			out.print("</tr>");
			}
			else
			{
				out.print("<tr>");
				out.print("<td>");
				out.print(id);
				out.print("</td>");
				
				out.print("<td>");
				if (Team1.getType().equals("9v9"))
					out.print("<span class='label label-info'>9v9</span>");
				else if (Team1.getType().equals("6v6"))
						out.print("<span class='label label-info'>6v6</span>");
					
				out.print("</td>");
				
				
				out.print("<td>");
				out.print("<b><a class='hastip' title='(ID:"+Team1.getID()+")"+Team1.getName()+"<br><a href=http://steamcommunity.com/profiles/"+SteamID.convertSteamIdToCommunityId(Team1.getSteamID())+">Leader profile</a>'>"+Team1.getTag()+"</a></b>");
				out.print(" <span class='label label-success'>("+Team1.getDiv()+")</span>");
				out.print("</td>");
				out.print("<td>");
				out.print("<a href='ScrimContact.jsp?id="+id+"'><input type='button' class='btn btn-primary btn-xs' value='Add'></a>");
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
				out.print("</tr>");
			}
		}
		out.print(" </table></div>");
	
	}
	else
	{
	
		out.print("</table></div>Sorry there is no scrims to get! You can <a href='ScrimAdd.jsp'>Add a scim</a>!");
	}
	%>
	

 <script type="text/javascript">
$('.hastip').tooltipsy();
</script>


<jsp:include page="Stopka.jsp" />
