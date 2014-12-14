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
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
	Context c;
	GameInfo
		game_info;
	String
		stringURL;
	SummonerInfo
		summoner_info;
	TextView
		field0,
		field1,
		field2,
		field3,
		field4,
		field5,
		field6,
		field7,
		field8,
		field9,
		field10,
		field11,
		field12,
		field13;
	
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
    	c =getActivity().getApplicationContext();
    	String icon = getArguments().getString("icon").trim();
    	String id = getArguments().getString("id").trim();
    	String name = getArguments().getString("name").trim();
    	View rootView = inflater.inflate(R.layout.fragment_game_data, container, false);
    	
    	field0 = (TextView)rootView.findViewById(R.id.game_summoner);
    	field1 = (TextView)rootView.findViewById(R.id.field_championid);
		field2 = (TextView)rootView.findViewById(R.id.field_gameid);
		field3 = (TextView)rootView.findViewById(R.id.field_gamemode);
		field4 = (TextView)rootView.findViewById(R.id.field_gametype);
		field5 = (TextView)rootView.findViewById(R.id.field_ipearned);
		field6 = (TextView)rootView.findViewById(R.id.field_mapid);
		field7 = (TextView)rootView.findViewById(R.id.field_spell1);
		field8 = (TextView)rootView.findViewById(R.id.field_spell2);
		field9 = (TextView)rootView.findViewById(R.id.field_teamid);
		field10 = (TextView)rootView.findViewById(R.id.field_level);
		field11 = (TextView)rootView.findViewById(R.id.field_goldearned);
		field12 = (TextView)rootView.findViewById(R.id.field_timeplayed);
		field13 = (TextView)rootView.findViewById(R.id.field_win);
		
		field0.setText(name);
		
		stringURL = "https://na.api.pvp.net/api/lol/na/v1.3/game/by-summoner/" + id + "/recent?api_key=d29d7e08-c066-4aad-b6fc-3e285ea5ceae";
		
		ConnectivityManager connMgr = (ConnectivityManager)c.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if(networkInfo != null && networkInfo.isConnected()) {
			new DownloadWebpageTask().execute(stringURL);
		} else {
			field1.setText("No network connection available");
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
				field1.setText("Game Not Found.");
			}
			else {
				try {
					JSONObject jObject = new JSONObject(result);
					JSONArray jArray = jObject.getJSONArray("games");
					JSONObject game = jArray.getJSONObject(0);
					JSONArray fellowPlayers = new JSONArray();
					JSONObject stats = new JSONObject();
					
					game_info = new GameInfo(game.optString("championId"),
							game.optString("createDate"), game.optString("gameId"),
							game.optString("gameMode"), game.optString("gameType"),
							game.optString("invalid"), game.optString("ipEarned"),
							game.optString("mapId"), game.optString("level"),
							game.optString("spell1"), game.optString("spell2"),
							game.optString("subType"), game.optString("teamId"));
					try{
						fellowPlayers = game.getJSONArray("fellowPlayers");
						game_info.setPlayers(fellowPlayers);
					}catch(Exception e){
						
					}
					try{
						stats = game.getJSONObject("stats");
						game_info.setStats(stats);
					}catch(Exception e){
						
					}
					
					field1.setText(game_info.getChampionId());
					field2.setText(game_info.getGameId());
					field3.setText(game_info.getGameMode());
					field4.setText(game_info.getGameType());
					field5.setText(game_info.getIpEarned());
					field6.setText(game.optString("mapId"));
					field7.setText(game.optString("spell1"));
					field8.setText(game.optString("spell2"));
					field9.setText(game.optString("teamId"));
					if(game_info.stats != null){
						field10.setText(stats.optString("level"));
						field11.setText(stats.optString("goldEarned"));
						field12.setText(stats.optString("timePlayed"));
						field13.setText(stats.optString("win"));
					}
				} catch (Exception e) { 
					field1.setText(e.toString());
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
        AssetManager assetManager = c.getAssets();
        InputStream istr = null;
        try {
            istr = assetManager.open("champion/Fizz.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(istr);
        //champion.setImageBitmap(bitmap);
    }
}