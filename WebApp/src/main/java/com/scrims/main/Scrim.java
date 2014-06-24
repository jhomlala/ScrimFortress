package com.scrims.main;

public class Scrim {
	private  int ID;
	private  int Team1_ID;
	private  int Team2_ID;
	private  String Date;
	private  String Time;
	private  String Info;
	
	/*SET*/
	
	public void setID(int ID_received)
	{
		this.ID = ID_received;
	}
	public void setTeam1_ID(int Team1_ID_received)
	{
		Team1_ID = Team1_ID_received;
	}
	public void setTeam2_ID(int Team2_ID_received)
	{
		Team2_ID = Team2_ID_received;
	}
	public void setDate(String Date_received)
	{
		Date = Date_received;
	}
	public void setTime(String Time_received)
	{
		Time = Time_received;
	}
	public void setInfo(String Info_received)
	{
		Info = Info_received;
	}
	
	public Scrim()
	{
		this.ID=-1;
		this.Team1_ID=-1;
		this.Team2_ID=-1;
		this.Date="empty";
		this.Time="empty";
		this.Info="empty";
	}
	
	/* GET */
	
	public int getID()
	{
		return ID;
	}
	
	public int getTeam1_ID()
	{
		return Team1_ID;
	}	
	public int getTeam2_ID()
	{
		return Team2_ID;
	}	
	public String getDate()
	{
		return Date;
	}	
	public String getTime()
	{
		return Time;
	}	
	
	public String getInfo()
	{
		return Info;
	}	
	
	
	
	
}
