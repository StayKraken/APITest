package com.example.apitest;

public class Stats {
	String
		assists,
		championsKilled,
		goldEarned,	
		goldSpent,
		item0,
		item1,
		item2,
		item3,
		killingSprees,
		largestKillingSpree,
		largestMultiKill,
		level,
		magicDamageDealtToChampions,
		minionsKilled,
		neutralMinionsKilled,
		neutralMinionsKilledYourJungle,
		numDeaths,
		physicalDamageDealtToCHampions,
		physicalDamageTaken,
		team,
		timePlayed,
		totalDamageDealt,
		totalDamageDealtToChampions,
		totalDamageTaken,
		trueDamageTaken,
		totalTimeCrowdControlDealt,
		win;
		
	public Stats(){
		assists = "";
		championsKilled = "";
		goldEarned = "";
		goldSpent = "";
		item0 = "";
		item1 = "";
		item2 = "";
		item3 = "";
		killingSprees = "";
		largestKillingSpree = "";
		largestMultiKill = "";
		level = "";
		magicDamageDealtToChampions = "";
		minionsKilled = "";
		neutralMinionsKilled = "";
		neutralMinionsKilledYourJungle = "";
		numDeaths = "";
		physicalDamageDealtToCHampions = "";
		physicalDamageTaken = "";
		team = "";
		timePlayed = "";
		totalDamageDealt = "";
		totalDamageDealtToChampions = "";
		totalDamageTaken = "";
		trueDamageTaken = "";
		totalTimeCrowdControlDealt = "";
		win = "";
	}
	
	public Stats(String a, String b, String c, String d, String e, String f,
			String g, String h, String i, String j, String k, String l, String m,
			String n, String o, String p, String q, String r, String s, String t,
			String u, String v, String w, String x, String y, String z, String aa){
		assists = a;
		championsKilled = b;
		goldEarned = c;
		goldSpent = d;
		item0 = e;
		item1 = f;
		item2 = g;
		item3 = h;
		killingSprees = i;
		largestKillingSpree = j;
		largestMultiKill = k;
		level = l;
		magicDamageDealtToChampions = m;
		minionsKilled = n;
		neutralMinionsKilled = o;
		neutralMinionsKilledYourJungle = p;
		numDeaths = q;
		physicalDamageDealtToCHampions = r;
		physicalDamageTaken = s;
		team = t;
		timePlayed = u;
		totalDamageDealt = v;
		totalDamageDealtToChampions = w;
		totalDamageTaken = x;
		trueDamageTaken = y;
		totalTimeCrowdControlDealt = z;
		win = aa;
	}
	
	public String getAssists(){ return assists; };
	public String getChampionsKilled(){ return championsKilled; };
	public String getGoldEarned(){ return goldEarned; };
}
