package com.example.apitest;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("DefaultLocale")
@SuppressWarnings("unused")
public class MainActivity extends Activity {
	private static final String DEBUG_TAG = "HttpExample";
	private EditText sumName;		//Receives summoner name input
	char[]
		j;
	private TextView
		sum_id,		//Displays summoner information
		sum_name,
		sum_icon_id,
		sum_level;
		//revision_date;
	 Button
		buttonRecent,
		btSubmit,
		testBtn;
	String
		summoner;
	SummonerInfo
		summoner_info;
	ImageView
		summoner_icon;
	//String[]
		//summoner_info = new String[5];
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//.onCreate "creates" the activity. Once onCreate() finishes execution, the system
		//calls onStart() and onResume() in quick succession. These are two of the three static
		//states
		super.onCreate(savedInstanceState);
		//Sets the user interface layout for this Activity
		//The layout file is defined in the project res/layout/main_activity.xml file
		setContentView(R.layout.activity_main);
		
		//Initialize EditText, TextView, and Button members so we can use them later
		sumName = (EditText)findViewById(R.id.et1);
		
		sumName.setOnKeyListener(new OnKeyListener()
		{
		    public boolean onKey(View v, int keyCode, KeyEvent event)
		    {
		        if (event.getAction() == KeyEvent.ACTION_DOWN)
		        {
		            switch (keyCode)
		            {
		                case KeyEvent.KEYCODE_DPAD_CENTER:
		                case KeyEvent.KEYCODE_ENTER:
						try {
							myClickHandler(v);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		                    return true;
		                default:
		                    break;
		            }
		        }
		        return false;
		    }
		});		
		
		sum_id = (TextView)findViewById(R.id.tv1);
		sum_name = (TextView)findViewById(R.id.tv2);
		sum_icon_id = (TextView)findViewById(R.id.tv3);
		sum_level = (TextView)findViewById(R.id.tv4);
		//revision_date = (TextView)findViewById(R.id.tv5);
		btSubmit = (Button)findViewById(R.id.button1);
		buttonRecent = (Button)findViewById(R.id.button_bottom);
		testBtn = (Button)findViewById(R.id.button_version);
		summoner_icon = (ImageView)findViewById(R.id.summoner_icon);
		if(summoner_info != null){
			buttonRecent.setAlpha(1);
		} else
			buttonRecent.setAlpha(0);
	}
	
	//40474574

	public void myClickHandler(View view)throws IOException {
		summoner = sumName.getText().toString().toLowerCase().trim();
		String stringURL = "https://na.api.pvp.net/api/lol/na/v1.4/summoner/by-name/" + summoner + "?api_key=d29d7e08-c066-4aad-b6fc-3e285ea5ceae";
		
		summoner_info = new SummonerInfo();

		ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if(networkInfo != null && networkInfo.isConnected()) {
			new DownloadWebpageTask().execute(stringURL);
		} else {
			sum_id.setText("No network connection available");
			sum_name.setText("");
			sum_icon_id.setText("");
			sum_level.setText("");
			//revision_date.setText("");
		}//else
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
				sum_id.setText("Player Not Found."); sum_name.setText("");
				sum_icon_id.setText("");
				sum_level.setText(""); //revision_date.setText("");
			}
			else {
				try {
					JSONObject jObject = new JSONObject(result);
					JSONObject j = jObject.getJSONObject(summoner);
					
					summoner_info.setID( j.getString("id"));
					summoner_info.setName(j.getString("name"));
					summoner_info.setIcon(j.getString("profileIconId"));
					summoner_info.setLevel(j.getString("summonerLevel"));
					//summoner_info.setRevision(j.getString("revisionDate"));
					
					sum_id.setText(summoner_info.getID());
					sum_name.setText(summoner_info.getName());
					//sum_icon_id.setText(summoner_info.getIcon());
					setBitmapFromAsset(summoner_info.getIcon());					
					sum_level.setText(summoner_info.getLevel());
					//revision_date.setText(summoner_info.getRevision());
					
					buttonRecent.setAlpha(1);
					buttonRecent.setClickable(true);
										
					//"https://na.api.pvp.net/api/lol/na/v1.3/game/by-summoner/" + summoner_info[0] + "/recent?api_key=d29d7e08-c066-4aad-b6fc-3e285ea5ceae"
				} catch (JSONException e) { }
			}
		}
	}
	
	private String downloadUrl(String myURL)throws IOException {
		InputStream is= null;
		int len = 1000;
		
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
	
	public void click_recent(View view){
		Intent intent = new Intent(getApplicationContext(), RecentGames.class);
		Bundle bundle = new Bundle();  
        bundle.putParcelable("Summoner", summoner_info);  
        intent.putExtras(bundle);  
		startActivity(intent);
	}
	
	public void click_version(View view) {
		Intent intent = new Intent(getApplicationContext(), LastGamePlayed.class);
		startActivity(intent);
	}
	
	
	private void setBitmapFromAsset(String sum_icon)
    {
        AssetManager assetManager = getAssets();
        InputStream istr = null;
        try {
            istr = assetManager.open("profileicon/" + sum_icon + ".png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(istr);
        summoner_icon.setImageBitmap(bitmap);
        summoner_icon.setAlpha((float)1);
    }
	
	
}//end Activity
