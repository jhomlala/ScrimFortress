<jsp:include page="Main.jsp" />
<%@page import="com.scrims.main.Team"%>
<%@page import="com.scrims.main.DBInterface"%>
<%@page import="com.scrims.main.CookieInterface"%>
<%@page import="com.scrims.main.Scrim"%>
<legend>Edit Team</legend>

<%
String action = request.getParameter("action");


if (CookieInterface.getTeamName(request)==null)
{
	response.getWriter().print("<HEAD><META HTTP-EQUIV='Refresh' CONTENT='0; URL=Home.jsp'></HEAD>");
}

else
{

	if ( action==null)
	{
		//response.getWriter().print("<HEAD><META HTTP-EQUIV='Refresh' CONTENT='0; URL=Home.jsp'></HEAD>");
	}
	else
	{
		
	
		
		
		if (action.equals("name") || action.equals("tag") || action.equals("info") || action.equals("steamid"))
		{
			out.println("<form  method='POST' action='ReceiverTeamEdit' >");
			out.println("<label class='control-label' for='login'>Change value of "+action+":</label>");
			if (action.equals("name"))
			{
			
				out.println(" <input id='login' name='name' placeholder='' class='input-xlarge' type='text'>");

			}
			if (action.equals("tag"))
			{
			
				out.println(" <input id='login' name='tag' placeholder='' class='input-xlarge' type='text'>");

			}
			if (action.equals("info"))
			{
			
				out.println(" <input id='login' name='info' placeholder='' class='input-xlarge' type='text'>");

			}
			
			if (action.equals("steamid"))
			{
			
				out.println(" <input id='login' name='steamid' placeholder='' class='input-xlarge' type='text'>");

			}
			out.println("<button type='submit' class='btn btn-default'>Change</button>");	
			out.println("</form>");
			
		}
		if (action.equals("div"))
		{
			out.println("<form  method='POST' action='ReceiverTeamEdit' >");
			out.println("<div class='control-group'>");
			out.println("<label class='control-label' for='team_div'>Team division</label>");
			out.println("<div class='controls'>");
			out.println("<select id='team_div' name='team_div' class='input-xlarge'>");
			out.println("<option>6 Div</option>");
			out.println("<option>5 Div</option>");
			out.println("<option>4 Div</option>");
			out.println("<option>3 Div</option>");
			out.println("<option>2 Div</option>");
			out.println("<option>1 Div</option>");
			out.println("<option>Prem</option>");
			out.println("< </select>");
			out.println("</div>");
			out.println("<br>");
			out.println("<button type='submit' class='btn btn-default'>Change</button>");
			out.println("</div>");
			
		}
		/* if (action.equals("type"))
		{
			
			
			out.println("<form  method='POST' action='ReceiverTeamEdit' >");
			out.println("<div class=;control-group;>");
			  out.println("<label class='control-label' for='team_type'>Team type</label>");
			  out.println("<div class='controls'>");
				  out.println("<label class='radio' for='team_type-0'>");
				  out.println("<input name='team_type' id='team_type-0' value='9v9' checked='checked' type='radio'>");
				  out.println(" 9v9(Higlander)");
			      out.println("</label>");
				  out.println(" <label class='radio' for='team_type-1'>");
				  out.println(" <input name='team_type' id='team_type-1' value='6v6' type='radio'>");
				  out.println(" 6v6");
				  out.println("</label>");
			  out.println(" </div>");
			 out.println("</div>");
			 out.println("<button type='submit' class='btn btn-default'>Change</button>");	
			
		}*/
		
		
		
		
		
	
	}
		
	
}
 
%>
<br>
 <a href='Panel.jsp'><input type='button' class='btn btn-primary' value='Back'></a></td>
<jsp:include page="Stopka.jsp" />