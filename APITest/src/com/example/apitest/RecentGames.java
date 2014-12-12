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
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class RecentGames extends Activity {
	private static final String DEBUG_TAG = "HttpExample";
	GameInfo
		game_info;
	String
		stringURL;
	SummonerInfo
		summoner_info;
	TextView
		summoner_field,
		gameid_field,
		gamemode_field,
		gametype_field,
		ipearned_field,
		goldearned_field;
	ImageView
		champion;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recent_games);
		summoner_info = (SummonerInfo)getIntent().getParcelableExtra("Summoner");
		summoner_field = (TextView)findViewById(R.id.recent_summoner);
		gameid_field = (TextView)findViewById(R.id.recent_gameid);
		gamemode_field = (TextView)findViewById(R.id.recent_gamemode);
		gametype_field = (TextView)findViewById(R.id.recent_gametype);
		ipearned_field = (TextView)findViewById(R.id.recent_ipearned);
		goldearned_field = (TextView)findViewById(R.id.recent_goldearned);
		champion = (ImageView)findViewById(R.id.champion);
		
		summoner_field.setText(summoner_info.getName());
		
		stringURL = "https://na.api.pvp.net/api/lol/na/v1.3/game/by-summoner/" + summoner_info.getID() + "/recent?api_key=d29d7e08-c066-4aad-b6fc-3e285ea5ceae";
		
		ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if(networkInfo != null && networkInfo.isConnected()) {
			new DownloadWebpageTask().execute(stringURL);
		} else {
			gameid_field.setText("No network connection available");
		}
		setBitmapFromAsset();
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
				gameid_field.setText("Game Not Found.");
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
										
					gameid_field.setText(game_info.getGameId());
					gamemode_field.setText(game_info.getGameMode());
					gametype_field.setText(game_info.getGameType());
					ipearned_field.setText(game_info.getIpEarned());
					goldearned_field.setText(game_info.stats.getGoldEarned());
					
				} catch (JSONException e) { 
					gameid_field.setText(e.toString());
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
	
	private void setBitmapFromAsset()
    {
        AssetManager assetManager = getAssets();
        InputStream istr = null;
        try {
            istr = assetManager.open("champion/Fizz.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(istr);
        champion.setImageBitmap(bitmap);
    }		
}
