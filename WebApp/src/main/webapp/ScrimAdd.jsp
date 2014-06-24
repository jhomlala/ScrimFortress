<%@page import="com.scrims.main.Team"%>
<%@page import="com.scrims.main.CookieInterface"%>
<%@page import="java.text.DateFormat" %>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="java.util.Calendar" %>
<%@page import="java.util.Date" %>
<%@page import="java.util.TimeZone" %>
<jsp:include page="Main.jsp" />
<link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
<script>
$(function() {
$( "#datepicker" ).datepicker();
});
</script>
<legend>Add Scrim</legend>
<form name="scrim" method="POST" action="ReceiverScrim">
<%if (CookieInterface.getTeamName(request)==null)

out.print("Please login to add scrim.<br><br>");

else
{

out.print("<label>Date: </label><input type='text' id='datepicker' name='date'><h6>Please pick a valid date. Max chose date is today + 2 weeks. MM/DD/YYYY</h6><br>");
out.print("<label>Time: </label><input type='text' name='time'><h6>Please pick a valid time. Valid time is for example 20:30.");
DateFormat df = new SimpleDateFormat(" '||'   HH:mm:ss '||' dd-MM-yyyy ||");
Date today = Calendar.getInstance().getTime();        

String todayDate = df.format(today);
out.print("<br>Current server time is: "+todayDate+"<br>Please post your scrim based on server time.<br> You can check timezones <a href='Timezones.jsp'>Here</a>.  </h6>");
out.print("<label>Information about scrim:</label><br><textarea name='info' cols='50' rows='5'></textarea><h6>Please tell here which maps you want to play and which server you want to use(post connect for ping-check).</h6><br>");
out.print("<input type='submit' class='btn btn-primary' name='submit' value='Add'> ");
}
%>






 <a href='Scrims.jsp'><input type='button' class='btn btn-primary' value='Back'></a></td>
</form>
<jsp:include page="Stopka.jsp" />
