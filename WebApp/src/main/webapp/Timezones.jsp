<jsp:include page="Main.jsp" />
 
<%@page import="java.text.DateFormat" %>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="java.util.Calendar" %>
<%@page import="java.util.Date" %>
<%@page import="java.util.TimeZone" %>
<legend>Timezones</legend>

<h4>Server time:</h4>
 <%		
 DateFormat df = new SimpleDateFormat(" '||'   HH:mm:ss '||' dd-MM-yyyy ||");
 Date today = Calendar.getInstance().getTime();        

 String todayDate = df.format(today);
 out.print(todayDate);

SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));


//Time in GMT
out.println("<h4>Europe:</h4>");
out.println("GMT: "+ dateFormat.format(new Date()) ); 

//GMT+1
dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+1"));
out.println( "<br>GMT+1(Central European Time Zone): " + dateFormat.format(new Date()) );

//GMT+2
dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+2"));
out.println( "<br>GMT+2(Eastern European Time Zone): " + dateFormat.format(new Date()) );

//GMT+4
dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+4"));
out.println( "<br>GMT+4(Moscow Time Zone): " + dateFormat.format(new Date()) );


out.println("<h4>North America:</h4>");
//GMT - 8
dateFormat.setTimeZone(TimeZone.getTimeZone("GMT-8"));
out.println( "GMT-8(Pacific Time Zone): " + dateFormat.format(new Date()) );

//GMT - 6
dateFormat.setTimeZone(TimeZone.getTimeZone("GMT-6"));
out.println( "<br>GMT-8(Central Time Zone): " + dateFormat.format(new Date()) );

//GMT - 5
dateFormat.setTimeZone(TimeZone.getTimeZone("GMT-5"));
out.println( "<br>GMT-8(Eastern Time Zone): " + dateFormat.format(new Date()) );

out.println("<h4>Asia:</h4>");
//GMT +9
dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+9"));
out.println( "GMT+9(Korea Time Zone): " + dateFormat.format(new Date()) );


 %>



<jsp:include page="Stopka.jsp" />
