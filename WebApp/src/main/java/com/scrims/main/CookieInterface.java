package com.scrims.main;

import java.sql.SQLException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieInterface {

	public static int getCookie(HttpServletRequest req)
	{
		String result="-1";
		Cookie[] cookies = req.getCookies();
		for (int i=0;i<cookies.length;i++)
		{
			if (cookies[i].getName().equals((String)req.getSession().getId()))
			{
				result = cookies[i].getValue();
			}
		}
		return Integer.parseInt(result);
	}
	
	public static int getCookieNumber(HttpServletRequest req)
	{
		int result = 0;
		Cookie[] cookies = req.getCookies();
		for (int i=0;i<cookies.length;i++)
		{
			if (cookies[i].getName().equals((String)req.getSession().getId()))
			{
				result++;
			}
		}
		return result;
	}
	
	
	public static void setCookiesTeamValues(HttpServletResponse res, Team team)
	{
		Cookie cookie_id = new Cookie("TEAM_ID",String.valueOf(team.getID()));
		Cookie cookie_name = new Cookie("TEAM_NAME",team.getName());
		Cookie cookie_type = new Cookie("TEAM_TYPE",team.getType());
		Cookie cookie_tag = new Cookie("TEAM_TAG",team.getTag());
		Cookie cookie_div = new Cookie("TEAM_DIV",team.getDiv());
		Cookie cookie_login = new Cookie("TEAM_LOGIN",team.getLogin());
		Cookie cookie_info = new Cookie("TEAM_INFO",team.getInfo());
		Cookie cookie_steamid = new Cookie("TEAM_STEAMID",team.getSteamID());
		
		cookie_id.setMaxAge(3600);
		cookie_name.setMaxAge(3600);
		cookie_type.setMaxAge(3600);
		cookie_tag.setMaxAge(3600);
		cookie_div.setMaxAge(3600);
		cookie_login.setMaxAge(3600);
		cookie_info.setMaxAge(3600);
		cookie_steamid.setMaxAge(3600);
		
		res.addCookie(cookie_id);
		res.addCookie(cookie_name);
		res.addCookie(cookie_type);
		res.addCookie(cookie_tag);
		res.addCookie(cookie_div);
		res.addCookie(cookie_login);
		res.addCookie(cookie_info);
		res.addCookie(cookie_steamid);
	}
	
	public static String getTeamName(HttpServletRequest req)
	{
		String name = null;
		Cookie[] cookies = req.getCookies();
		
		if (!(cookies==null))
		{
			for (int i=0;i<cookies.length;i++)
			{

				if (cookies[i].getName().equals("TEAM_NAME"))
				{
					name=cookies[i].getValue();
				}
			}
		}
		return name;
	}
	
	public static String getID(HttpServletRequest req)
	{
		String id = null;
		Cookie[] cookies = req.getCookies();
		
		if (!(cookies==null))
		{
			for (int i=0;i<cookies.length;i++)
			{
			
				if (cookies[i].getName().equals("TEAM_ID"))
				{
					id=cookies[i].getValue();
				}
			}
		}
		return id;
	}
	
	
	private static String getValueOfCookie(HttpServletRequest req,String CookieName)
	{
		String result = "";
		Cookie[] cookies = req.getCookies();
		
		if (!(cookies==null))
		{
			for (int i=0;i<cookies.length;i++)
			{

				if (cookies[i].getName().equals(CookieName))
				{
					result=cookies[i].getValue();
				}
			}
		}
		return result;
	}
	
	
	
	public static Team getTeamFromCookies(HttpServletRequest req)
	{
		Team team_build = new Team();
		team_build.setID(Integer.parseInt(getValueOfCookie(req,"TEAM_ID")));
		team_build.setName(getValueOfCookie(req,"TEAM_NAME"));
		team_build.setDiv(getValueOfCookie(req,"TEAM_DIV"));
		team_build.setType(getValueOfCookie(req,"TEAM_TYPE"));
		team_build.setLogin(getValueOfCookie(req,"TEAM_LOGIN"));
		team_build.setTag(getValueOfCookie(req,"TEAM_TAG"));
		team_build.setInfo(getValueOfCookie(req,"TEAM_INFO"));
		team_build.setSteamID(getValueOfCookie(req,"TEAM_STEAMID"));
		return team_build;
	}

	public static void reloadCookies(HttpServletResponse response,HttpServletRequest request) throws SQLException 
	{
		
	 
		 response.setContentType("text/html");
		 
		 String id = null;
		 id = getID(request);
		 Cookie[] cookielist = request.getCookies();
	     if (cookielist!= null) {
	         for (int i = 0; i < cookielist.length; i++) 
	         	{
	        	 Cookie delCookie = new Cookie(cookielist[i].getName(), cookielist[i].getValue());
	        	 //delCookie.setDomain();
	        	 delCookie.setPath(cookielist[i].getPath());
	        	 delCookie.setMaxAge(0);
	        	 response.addCookie(delCookie);

	           }
	        }
		DBInterface db = new DBInterface();
		Team team = new Team();
		db.loadTeamDataFromID(team, Integer.parseInt(id));
		setCookiesTeamValues(response,team);
		
	}

	
	
	
}
