package com.android.myhealthmate;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.TextView;

public class MainPage extends Activity {
	private TextView rec_content;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homepage);
		rec_content = (TextView) findViewById(R.id.rec_content);
		rec_content.setVisibility(View.GONE);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main, menu);
	    return super.onCreateOptionsMenu(menu);   
	    
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_settings:
	       //     openSettings();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	
	

	public void toggle_contents(View v) {

		rec_content.setVisibility(rec_content.isShown() ? View.GONE
				: View.VISIBLE);
	}
}
