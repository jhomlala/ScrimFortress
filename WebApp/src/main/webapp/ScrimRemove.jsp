<jsp:include page="Main.jsp" />
<%@page import="com.scrims.main.Team"%>
<%@page import="com.scrims.main.DBInterface"%>
<%@page import="com.scrims.main.CookieInterface"%>
<%@page import="com.scrims.main.Scrim"%>
<legend>Remove</legend>


<%
String scrimid = request.getParameter("id");


if (CookieInterface.getTeamName(request)==null)

	response.getWriter().print("<HEAD><META HTTP-EQUIV='Refresh' CONTENT='0; URL=Home.jsp'></HEAD>");
		

else
{
	Team team = CookieInterface.getTeamFromCookies(request);
	if ( scrimid==null)
	{
		response.getWriter().print("<HEAD><META HTTP-EQUIV='Refresh' CONTENT='0; URL=Home.jsp'></HEAD>");
	}
	else
	{
		
			DBInterface db = new DBInterface();	
			Scrim scrim_to_delete = db.loadScrimWithID(Integer.parseInt(scrimid));
			if (!(scrim_to_delete.getID() == - 1))
			
			{
				if (scrim_to_delete.getTeam1_ID() == team.getID() )
				{
					db.removeScrimWithID(Integer.parseInt(scrimid));
					out.print("<div class='alert alert-success '>");
					out.print("<strong>Success!</strong>");
					//out.print(scrim_to_delete.getID());
					out.print(" Removed scrim with id:" +scrimid);
					out.print("</div>");
				}
				else
				{
					out.print("<div class='alert alert-danger '>");
					out.print("<strong>Error!</strong>");
					out.print("You can only remove your team scrim!" );
					out.print("</div>");
				}
			}
			else
			{
				{
					out.print("<div class='alert alert-danger '>");
					out.print("<strong>Error!</strong>");
					out.print("Please try again." );
					out.print("</div>");
				}
			}
	

		}
		
	
}
 
%>

 <a href='Panel.jsp'><input type='button' class='btn btn-primary' value='Back'></a></td>
<jsp:include page="Stopka.jsp" />