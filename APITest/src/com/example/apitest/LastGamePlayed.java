package com.example.apitest;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;


public class LastGamePlayed extends FragmentActivity implements TabListener {
	
	ViewPager viewPager;
	ActionBar actionBar;
	
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_game_played);
        
        viewPager=(ViewPager)findViewById(R.id.pager);
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener(){

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageSelected(int arg0) {
				actionBar.setSelectedNavigationItem(arg0);
			}
        });
        
        actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
        ActionBar.Tab tab1 = actionBar.newTab();
        tab1.setText("Game Data");
        tab1.setTabListener(this);
        
        ActionBar.Tab tab2 = actionBar.newTab();
        tab2.setText("Player Stats");
        tab2.setTabListener(this);
        
        actionBar.addTab(tab1);
        actionBar.addTab(tab2);
    }

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	}	
}

class MyAdapter extends FragmentPagerAdapter{

	public MyAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int arg0) {
		Fragment fragment=null;
		if(arg0==0)
			fragment = new GameData();
		else if(arg0==1)
			fragment = new PlayerStats();
		return fragment;
	}

	@Override
	public int getCount() {
		return 2;
	}
	
}