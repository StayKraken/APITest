package com.example.apitest;

import com.example.apitest.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
 
public class PlayerStats extends Fragment {
	public PlayerStats(){
	}
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_player_stats, container, false);
         
        return rootView;
    }
}