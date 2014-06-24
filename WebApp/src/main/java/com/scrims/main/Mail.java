/*****************************************************************************************************************************
Scrim Fortress Project
File: Mail.java
Description: Mail model , which will be used in Mailbox.java
Author: Jakub Homlala
****************************************************************************************************************************/

package com.scrims.main;

//***************************************************************************************************************************
//Main class
//***************************************************************************************************************************

public class Mail 
{
	private int ID;
	private String date;
	private String time;
	private int author_id;
	private int receiver_id;
	private String title;
	private String message;

//***************************************************************************************************************************
// GETTERS and SETTERS
//***************************************************************************************************************************
	public  void setID(int ID_received)
	{
		ID = ID_received;
	}
	public void setDate(String date_received)
	{
		date = date_received;
	}
	public void setTime(String time_received)
	{
		time = time_received;
	}
	public void setAuthor_ID(int author_id_received)
	{
		author_id = author_id_received;
	}
	public void setReceiver_ID(int receiver_id_received)
	{
		receiver_id = receiver_id_received;
	}
	public void setTitle(String title_received)
	{
		title = title_received;
	}
	public void setMessage(String message_received)
	{
		message = message_received;
	}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public int getID()
	{
		return ID;
	}
	public String getDate()
	{
		return date;
	}
	public String getTime()
	{
		return time;
	}
	public int getAuthor_ID()
	{
		return author_id;
	}
	public int getReceiver_ID()
	{
		return receiver_id;
	}
	public String getTitle()
	{
		return title;
	}
	public String getMessage()
	{
		return message;
	}
	
}
