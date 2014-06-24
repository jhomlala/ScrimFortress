<%@page import="com.scrims.main.DBInterface"%>
<%
DBInterface db = new DBInterface();
if (db.ping())
{
	out.println("ZYJE");
}
else
{
	out.println("NIEZYJE");
}

%>