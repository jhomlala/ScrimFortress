

<%@page import="com.scrims.main.CookieInterface"%>
<% if (CookieInterface.getTeamName(request)!=null)
	{
	response.getWriter().print("<HEAD><META HTTP-EQUIV='Refresh' CONTENT='0; URL=Panel.jsp'></HEAD>");
	}%>


<jsp:include page="Main.jsp" />
<form class="form-horizontal" name="log" method="POST" action="ReceiverLogin" >
<fieldset>

<!-- Form Name -->
<legend>Team Panel</legend>

<!-- Text input-->
<div class="control-group">
  <label class="control-label" for="login">Login:</label>
  <div class="controls">
    <input id="login" name="login" placeholder="" class="input-xlarge" type="text">
    
  </div>
</div>

<!-- Password input-->
<div class="control-group">
  <label class="control-label" for="password">Password:</label>
  <div class="controls">
    <input id="password" name="password" placeholder="" class="input-xlarge" type="password">
    
  </div>
</div>

</fieldset><br>
<input type="submit" class="btn btn-primary" name="submit" value="Login"> 
</form>






<jsp:include page="Stopka.jsp" />
