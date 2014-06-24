package com.scrims.main;

public class Team {
private  String Name;
private  String Tag;
private  String Div;
private  String Type;
private  String Login;
private  String Info;
private  int ID;
private  String SteamID;
///////////////////////////////////////////
/* SET */
///////////////////////////////////////////
public  void setName(String Name_received)
{
	Name = Name_received;
}

public  void setTag(String Tag_received)
{
	Tag = Tag_received;
}

public   void setDiv(String Div_received)
{
	Div = Div_received;
}

public void setType(String Type_received)
{
	Type = Type_received;
}

public  void setLogin(String Login_received)
{
	Login = Login_received;
}


public void setInfo(String Info_received)
{
	Info = Info_received;
}

public  void setID(int ID_received)
{
	ID = ID_received;
}

public  void setSteamID(String ID_received)
{
	SteamID = ID_received;
}
///////////////////////////////////////////
/* GET */
///////////////////////////////////////////

public String getName()
{
	return Name;
}

public String getTag()
{
	return Tag;
}

public String getDiv()
{
	return Div;
}

public String getType()
{
	return Type;
}

public String getLogin()
{
	return Login;
}

public String getInfo()
{
	return Info;
}

public int getID()
{
	return ID;
}

public String getSteamID()
{
	return SteamID;
}
}
