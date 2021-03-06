package com.example.apitest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GameInfo {
	FellowPlayer
		player1,
		player2,
		player3,
		player4,
		player5,
		player6,
		player7,
		player8,
		player9;
	Stats
		stats;
	String
		championId,
		createDate,
		gameId,
		gameMode,
		gameType,
		invalid,
		ipEarned,
		mapId,
		level,
		spell1,
		spell2,
		subType,
		teamId;
	
	public GameInfo(){
		player1 = null;
		player2 = null;
		player3 = null;
		player4 = null;
		player5 = null;
		player6 = null;
		player7 = null;
		player8 = null;
		player9 = null;
		
		stats = null;
		
		championId = "";
		createDate = "";
		gameId = "";
		gameMode = "";
		gameType = "";
		invalid = "";
		ipEarned = "";
		mapId = "";
		level = "";
		spell1 = "";
		spell2 = "";
		subType = "";
		teamId = "";
	}
	
	public GameInfo(String a, String b, String c, String d,String e, String f, String g,
			String h, String i, String j, String k, String l, String m){
		player1 = null;
		player2 = null;
		player3 = null;
		player4 = null;
		player5 = null;
		player6 = null;
		player7 = null;
		player8 = null;
		player9 = null;
		
		stats = null;
		
		championId = a;
		createDate = b;
		gameId = c;
		gameMode = d;
		gameType = e;
		invalid = f;
		ipEarned = g;
		mapId = h;
		level = i;
		spell1 = j;
		spell2 = k;
		subType = l;
		teamId = m;
	}
	
	public void setStats(JSONObject stat) throws JSONException{
		stats = new Stats(stat.optString("assists"), stat.optString("championsKilled"),stat.optString("goldEarned"),
				stat.optString("goldSpent"),stat.optString("item0"),stat.optString("item1"),
				stat.optString("item2"),stat.optString("item3"),stat.optString("killingSprees"),
				stat.optString("largestKillingSpree"),stat.optString("largestMultiKill"),stat.optString("level"),
				stat.optString("magicDamageDealtToChampions"),stat.optString("minionsKilled"),stat.optString("neutralMinionsKilled"),
				stat.optString("neutralMinionsKilledYourJungle"),stat.optString("numDeaths"),stat.optString("physicalDamageDealtToChampions"),
				stat.optString("physicalDamageTaken"),stat.optString("team"),stat.optString("timePlayed"),
				stat.optString("totalDamageDealt"),stat.optString("totalDamageDealtToChampions"),stat.optString("totalDamageTaken"),
				stat.optString("trueDamageTaken"),stat.optString("totalTimeCrowdControlDealt"),stat.optString("win"));
	}
	
	public void setPlayers(JSONArray players){
		for(int i = 0; i < players.length(); i++){
			if(i==0){
				try{
					JSONObject object1 = players.getJSONObject(i);
					player1 = new FellowPlayer(object1.optString("championId"),
							object1.optString("summonerId"), object1.optString("teamId"));
				} catch (JSONException e){}
			} else if(i == 1){
				try{
					JSONObject object1 = players.getJSONObject(i);
					player2 = new FellowPlayer(object1.optString("championId"),
							object1.optString("summonerId"), object1.optString("teamId"));
				} catch (JSONException e){}
			} else if(i == 2){
				try{
					JSONObject object1 = players.getJSONObject(i);
					player3 = new FellowPlayer(object1.optString("championId"),
							object1.optString("summonerId"), object1.optString("teamId"));
				} catch (JSONException e){}
			} else if(i == 3){
				try{
					JSONObject object1 = players.getJSONObject(i);
					player4 = new FellowPlayer(object1.optString("championId"),
							object1.optString("summonerId"), object1.optString("teamId"));
				} catch (JSONException e){}
			} else if(i == 4){
				try{
					JSONObject object1 = players.getJSONObject(i);
					player5 = new FellowPlayer(object1.optString("championId"),
							object1.optString("summonerId"), object1.optString("teamId"));
				} catch (JSONException e){}
			} else if(i == 5){
				try{
					JSONObject object1 = players.getJSONObject(i);
					player6 = new FellowPlayer(object1.optString("championId"),
							object1.optString("summonerId"), object1.optString("teamId"));
				} catch (JSONException e){}
			} else if(i == 6){
				try{
					JSONObject object1 = players.getJSONObject(i);
					player7 = new FellowPlayer(object1.optString("championId"),
							object1.optString("summonerId"), object1.optString("teamId"));
				} catch (JSONException e){}
			} else if(i == 7){
				try{
					JSONObject object1 = players.getJSONObject(i);
					player8 = new FellowPlayer(object1.optString("championId"),
							object1.optString("summonerId"), object1.optString("teamId"));
				} catch (JSONException e){}
			} else if(i == 8){
				try{
					JSONObject object1 = players.getJSONObject(i);
					player9 = new FellowPlayer(object1.optString("championId"),
							object1.optString("summonerId"), object1.optString("teamId"));
				} catch (JSONException e){}
			}
		}
	}
	
	public String getChampionId(){ return championId; }
	public String getCreateDate(){ return createDate; }
	public String getGameId(){ return gameId; }
	public String getGameMode(){ return gameMode; }
	public String getGameType(){ return gameType; }
	public String getInvalid(){ return invalid; }
	public String getIpEarned(){ return ipEarned; }
	public String getMapId(){ return mapId; }
	public String getLevel(){ return level; }
	public String getSpell1(){ return spell1; }
	public String getSpell2(){ return spell2; }
	public String getSubType(){ return subType; }
	public String getTeamId(){ return teamId; }
}
