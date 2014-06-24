<jsp:include page="Main.jsp" />
<script src="<c:url value="files/js/block.js" />"></script>

<form class="form-horizontal" name="reg" method="POST" action="Receiver" >
<fieldset>

<!-- Form Name -->
<legend>Register</legend>

<!-- Text input-->
<div class="control-group"  >
  <label class="control-label" for="team_name">Team name:</label>
  <div class="controls">
    <input id="team_name" name="team_name" placeholder="for ex. Your Name Highlander Team" class="input-xlarge" type="text">
    <p class="help-block">Please insert here your team name.</p>
  </div>
</div>

<!-- Text input-->
<div class="control-group">
  <label class="control-label" for="team_tag">Team tag:</label>
  <div class="controls">
    <input id="team_tag" name="team_tag" placeholder="for ex. YNHT" class="input-xlarge" type="text">
    <p class="help-block">Please insert here your team tag. Tag should be short.</p>
  </div>
</div>

<!-- Multiple Radios -->
<div class="control-group">
  <label class="control-label" for="team_type">Team type:</label>
  <div class="controls">
    <label class="radio" for="team_type-0">
      <input name="team_type" id="team_type-0" value="9v9" checked="checked" type="radio">
      9v9(Higlander)
    </label>
    <label class="radio" for="team_type-1">
      <input name="team_type" id="team_type-1" value="6v6" type="radio">
      6v6
    </label>
  </div>
</div>

<!-- Select Basic -->
<div class="control-group">
  <label class="control-label" for="team_div">Team division</label>
  <div class="controls">
    <select id="team_div" name="team_div" class="input-xlarge">

      <option>6 Div</option>
      <option>5 Div</option>
      <option>4 Div</option>
      <option>3 Div</option>
      <option>2 Div</option>
      <option>1 Div</option>
      <option>Prem</option>
    </select>
  </div>
</div>

<!-- Text input-->
<div class="control-group">
  <label class="control-label" for="team_leaderlogin">Leader login:</label>
  <div class="controls">
    <input id="team_leaderlogin" name="team_leaderlogin" placeholder="your login" class="input-xlarge" type="text">
    <p class="help-block">Please insert here your login. You will use this to login to team panel.</p>
  </div>
</div>

<!-- Password input-->
<div class="control-group">
  <label class="control-label" for="team_leaderpass">Leader pass:</label>
  <div class="controls">
    <input id="team_leaderpass" name="team_leaderpass" placeholder="your pass" class="input-xlarge" type="password">
    <p class="help-block">Please insert here your pass . You will use this to login to team panel.</p>
  </div>
</div>

<!-- Text input-->
<div class="control-group">
  <label class="control-label" for="team_steamid">Leader steamid:</label>
  <div class="controls">
    <input id="team_steamid" name="team_steamid" placeholder="STEAM_._...." class="input-xlarge" type="text">
    <p class="help-block">Please insert here your steamid. You can get your steamid from <a href="http://steamidfinder.com/">here</a>.
    <br> For example , valid STEAMID is : <i>STEAM_0:1:32262283</i>.</p>
  </div>
</div>


<!-- Textarea -->
<div class="control-group">
  <label class="control-label" for="team_info">Information about team</label>
  <div class="controls">                     
    <textarea id="team_info" name="team_info" >Info.</textarea>
     <p class="help-block">Please insert here some information about your team. You can paste everything you want here, but<br>
     try to add some cool information , like your adress to etf2l or ugc profile. Max 100 chars.</p>
  </div>
</div>
<br>




<input type="submit" class="btn btn-primary" name="submit" value="Register"> 
</fieldset>
</form>

<br>



<jsp:include page="Stopka.jsp" />
