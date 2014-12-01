package com.example.apitest;

public class FellowPlayer {
	String
		championId,
		summonerId,
		teamId;
	
	public FellowPlayer(){
		championId = "";
		summonerId = "";
		teamId = "";
	}
	
	public FellowPlayer(String a, String b, String c){
		championId = a;
		summonerId = b;
		teamId = c;
	}

	public String getChampionID(){ return championId; }
	public String getSummonerID(){ return summonerId; }
	public String getTeamID(){ return teamId; }
	
	public void setChampionID(String a){ championId = a; }
	public void setSummonerID(String a){ summonerId = a; }
	public void setTeamID(String a){ teamId = a; }
}
