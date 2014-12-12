package com.example.apitest;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.example.apitest.R;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
 
public class GameData extends Fragment {
	private static final String DEBUG_TAG = "HttpExample";
	Context
		c = getActivity().getApplicationContext();
	GameInfo
		game_info;
	String
		stringURL;
	SummonerInfo
		summoner_info;
	TextView
		field1,
		field2,
		field3,
		field4,
		field5,
		field6;
	
	public GameData(){
	}
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState)
{
    super.onActivityCreated(savedInstanceState);

    Bundle bundle = getArguments();
    if(bundle != null)
    {
        //listMusics = bundle.getParcelableArrayList("arrayMusic");
        //listMusic.setAdapter(new MusicBaseAdapter(getActivity(), listMusics));
    }
}
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	View rootView = inflater.inflate(R.layout.fragment_game_data, container, false);
    	
    	field1 = (TextView)rootView.findViewById(R.id.recent_summoner);
		field2 = (TextView)rootView.findViewById(R.id.recent_gameid);
		field3 = (TextView)rootView.findViewById(R.id.recent_gamemode);
		field4 = (TextView)rootView.findViewById(R.id.recent_gametype);
		field5 = (TextView)rootView.findViewById(R.id.recent_ipearned);
		field6 = (TextView)rootView.findViewById(R.id.recent_goldearned);
		
		field1.setText(summoner_info.getName());
		
		stringURL = "https://na.api.pvp.net/api/lol/na/v1.3/game/by-summoner/" + summoner_info.getID() + "/recent?api_key=d29d7e08-c066-4aad-b6fc-3e285ea5ceae";
		
		ConnectivityManager connMgr = (ConnectivityManager)c.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if(networkInfo != null && networkInfo.isConnected()) {
			new DownloadWebpageTask().execute(stringURL);
		} else {
			field2.setText("No network connection available");
		}
    	
         
        return rootView;
    }
    
    private class DownloadWebpageTask extends AsyncTask<String,Void, String> {
		@Override
		protected String doInBackground(String... urls) {
			try {
				String data = downloadUrl(urls[0]);
				return data;
			} catch (IOException e) {
				return "Unable to retrieve web page. URL may be invalid.";
			}
		}
		@Override
		protected void onPostExecute(String result) {
			if(result.equals("Unable to retrieve web page. URL may be invalid.")){
				field2.setText("Game Not Found.");
			}
			else {
				try {
					JSONObject jObject = new JSONObject(result);
					JSONArray jArray = jObject.getJSONArray("games");
					JSONObject game1 = jArray.getJSONObject(0);
					JSONArray fellowPlayers = game1.getJSONArray("fellowPlayers");
					JSONObject stats = game1.getJSONObject("stats");
					
					game_info = new GameInfo(game1.optString("championId"),
							game1.optString("createDate"), game1.optString("gameId"),
							game1.optString("gameMode"), game1.optString("gameType"),
							game1.optString("invalid"), game1.optString("ipEarned"),
							game1.optString("mapId"), game1.optString("level"),
							game1.optString("spell1"), game1.optString("spell2"),
							game1.optString("subType"), game1.optString("teamId"));
					game_info.setPlayers(fellowPlayers);
					game_info.setStats(stats);
										
					field2.setText(game_info.getGameId());
					field3.setText(game_info.getGameMode());
					field4.setText(game_info.getGameType());
					field5.setText(game_info.getIpEarned());
					field6.setText(game_info.stats.getGoldEarned());
					
				} catch (JSONException e) { 
					field2.setText(e.toString());
				}
			}
		}
	}
	
	private String downloadUrl(String myURL)throws IOException {
		InputStream is= null;
		int len = 100000;
		
		try {
			URL url = new URL(myURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(10000);
			conn.setConnectTimeout(15000);
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			
			conn.connect();
			int response = conn.getResponseCode();
			Log.d(DEBUG_TAG, "The response is: " + response);
			is = conn.getInputStream();
			
			String contentAsString = readIt(is, len).trim();
			return contentAsString;
			
		} finally {
			if (is != null)
				is.close();
		}
	}
	
	//Reads an InputStream and converts it to a String.
	public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
		Reader reader = null;
		reader = new InputStreamReader(stream, "UTF-8");
		char[] buffer = new char[len];
		reader.read(buffer);
		return new String(buffer);
	}//readIt
}