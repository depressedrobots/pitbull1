package de.cloudarts.aibattleai;

import java.net.MalformedURLException;
import java.net.URL;

public class URLCreator {

	private static final String ROOT_URL = "http://aibattle.soulmates-game.de/";
	

	
	public static URL getRequestMatchURL(String playerName_)
	{
		if( playerName_ == null || playerName_.isEmpty() )
		{
			playerName_ = "Wojtek";
		}
		
		URL url = null;
		
		try 
		{
			url = new URL(ROOT_URL + "requestmatch.php?playername=" + playerName_);
		} 
		catch (MalformedURLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return url;
	}
	
	public static URL getRequestMatchStatusURL(int matchID_)
	{
		URL url = null;
		
		try 
		{
			url = new URL(ROOT_URL + "getmatchstatus.php?matchid=" + matchID_);
		} 
		catch (MalformedURLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return url;
	}

	
	public static URL getPostActionURL(int matchID_, String playerName_, String token_, String action_)
	{
		URL url = null;
		String str = ROOT_URL + "postaction.php?matchid=" + matchID_;
		str += "&playername=" + playerName_;
		str += "&token=" + token_;
		str += "&action=" + action_;
		
		try 
		{
			url = new URL(str);
		} 
		catch (MalformedURLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return url;
	}

}
