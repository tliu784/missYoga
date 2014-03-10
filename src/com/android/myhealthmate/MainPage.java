package com.android.myhealthmate;

import com.android.entity.RecomModel;
import com.android.service.RecomResponseHandler;
import com.android.service.RestCallHandler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.TextView;

public class MainPage extends Activity implements RecomResponseHandler{
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
		case R.id.action_account: {
			startActivity(new Intent(this, (new SignUp()).getClass()));
		}
		case R.id.action_refrash:
			 refresh();

		case R.id.action_settings:
			// openSettings();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void toggle_recom_box(View v) {
		rec_content.setVisibility(rec_content.isShown() ? View.GONE
				: View.VISIBLE);
	}

	private void refresh(){
		String url = "http://healthengineherokuappcom.apiary.io/";
		String json =
				 "{\n    \"userinfo\": {\n        \"age\": 45,\n        \"gender\": \"male\",\n        \"height\": 168,\n        \"weight\": [\n            {\n                \"value\": 65.3,    //this is the data of current day\n                \"date\": \"2012-04-24\"\n            },\n            {\n                \"value\": 65.3,    // this should be average of last week\n                \"date\": \"2012-04-17\"    //by defult this should the last 7 days\n            },\n            {\n                \"value\": 65.3,    // this should be average of last month\n                \"date\": \"2012-03-24\"    //by defult this should the last 30 days\n            }\n        ],\n        \"hypertension\" : true,\n        \"diabetes\" : true,\n        \"insomnia\" : true,\n        \"cardio\" : true\n    },\n    \"activities\": [\n        {\n            \"distance\": 500,     //this is the data of current day\n            \"duration\": 7.3,\n            \"date\": \"2012-04-24\",\n            \"startTime\": \"18:20:42Z\",\n            \"steps\": 800\n        },\n        {\n            \"distance\": 1500,  // this is accumulation not average by last week\n            \"duration\": 140,\n            \"date\": \"2012-04-17\",\n            \"startTime\": \"\",    // timestamp should be empty\n            \"steps\": 1700\n        },\n        {\n            \"distance\": 12500, // this is accumulation not average by last month\n            \"duration\": 1430,\n            \"date\": \"2012-03-24\",\n            \"startTime\": \"\",   // timestamp should be empty\n            \"steps\": 49300\n        }\n    ],\n    \"sleep\": [\n        {\n            \"efficiency\": 4,    //this is the data of current day\n            \"date\": \"2012-04-24\",\n            \"startTime\": \"18:25:43Z\",\n            \"minutesAsleep\": 453,\n            \"minutesAwake\": 34,\n            \"awakeningsCount\": 8,\n            \"timeInBed\": 541\n        },\n        {\n            \"efficiency\": 4,   // this is the average of last week\n            \"date\": \"2012-04-17\",\n            \"startTime\": \"\",   // this should be empty\n            \"minutesAsleep\": 453,\n            \"minutesAwake\": 34,\n            \"awakeningsCount\": 8,\n            \"timeInBed\": 541\n        },\n        {\n            \"efficiency\": 4,  // this is the average of last month\n            \"date\": \"2012-03-24\",\n            \"startTime\": \"\",  // this should be empty\n            \"minutesAsleep\": 453,\n            \"minutesAwake\": 34,\n            \"awakeningsCount\": 8,\n            \"timeInBed\": 541\n        }\n    ],\n    \"heartBeats\": [\n        {\n            \"count\": 56,       //this is the data of current day\n            \"date\": \"2012-04-24\",\n            \"time\": \"18:23:43Z\"\n        },\n        {\n            \"count\": 60,       //this is the average of last week\n            \"date\": \"2012-04-17\",\n            \"time\": \"\"\n        },\n        {\n            \"count\": 59,      //this is the average of last month\n            \"date\": \"2012-03-24\",\n            \"time\": \"\"\n        }\n    ],\n    \"bloodPressures\": [\n        {\n            \"systolic\": 100,         //this is the data of current day\n            \"diastolic\": 71,\n            \"date\": \"2012-04-23\",\n            \"time\": \"18:23:43Z\"\n        },\n        {\n            \"systolic\": 100,         //this is the average of last week\n            \"diastolic\": 71,\n            \"date\": \"2012-04-17\",     \n            \"time\": \"\"               // timestamp should be empty\n        },\n        {\n            \"systolic\": 100,         //this is the average of last month\n            \"diastolic\": 71,\n            \"date\": \"2012-03-24\",\n            \"time\": \"\"               // timestamp should be empty\n        }\n    ]\n}";
		RestCallHandler rest=new RestCallHandler(MainPage.this,url,json);
		rest.handleResponse();
	}
	
	private void updateRecBox(RecomModel[] recomArray) {
		if (recomArray != null) {
			rec_content.setText("");
			for (int i = 0; i < recomArray.length; i++) {
				rec_content.append(recomArray[i].getRecommendation());
				if (i < recomArray.length - 1) {
					rec_content.append("\n");
				}
			}
		}else{
			rec_content.setText("Unable to retrieve recommendation");
		}
	}
	
	@Override
	public void processRecom(RecomModel[] recomArray) {
		
		updateRecBox(recomArray);
	}


}
