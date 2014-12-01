package com.example.apitest;
import android.os.Parcel;  
import android.os.Parcelable; 

public class SummonerInfo implements Parcelable {
	String
		ID,
		name,
		icon,
		level,
		revision;
	
	public SummonerInfo(){
		ID = "";
		name = "";
		icon = "";
		level = "";
		revision = "";
	}
	
	public SummonerInfo(String a, String b, String c, String d, String e){
		ID = a;
		name = b;
		icon = c;
		level = d;
		revision = e;
	}
	
	public String getID(){ return ID; } 
	public String getName(){ return name; }
	public String getIcon(){ return icon; }
	public String getLevel(){ return level; }
	public String getRevision(){ return revision; }

	public void setID(String a){ ID = a; };
	public void setName(String a){ name = a; };
	public void setIcon(String a){ icon = a; };
	public void setLevel(String a){ level = a; };
	public void setRevision(String a){ revision = a; };
	
	public static final Parcelable.Creator<SummonerInfo> CREATOR = new Creator<SummonerInfo>() {  
		 public SummonerInfo createFromParcel(Parcel source) {  
			 SummonerInfo summoner_info = new SummonerInfo();  
			 summoner_info.ID = source.readString();  
			 summoner_info.name = source.readString();
			 summoner_info.icon = source.readString();
			 summoner_info.level = source.readString();
			 summoner_info.revision = source.readString();
		     return summoner_info;  
		 }
		 public SummonerInfo[] newArray(int size) {  
		     return new SummonerInfo[size];  
		 } 
	};
	
	public int describeContents() {  
		 return 0;  
	}  
	public void writeToParcel(Parcel parcel, int flags) {  
		parcel.writeString(ID);  
		parcel.writeString(name);  
		parcel.writeString(icon);
		parcel.writeString(level);
		parcel.writeString(revision);
	}
	
}
