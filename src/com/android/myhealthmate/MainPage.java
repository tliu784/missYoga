package com.android.myhealthmate;

import java.util.Calendar;
import java.util.GregorianCalendar;
import com.android.entity.RecomModel;
import com.android.entity.TestingJson;
import com.android.reminder.AlarmReceiver;
import com.android.reminder.AlarmService;
import com.android.reminder.MedReminderController;
import com.android.reminder.MedReminderModel;
import com.android.service.ResponseHandler;
import com.android.service.RestCallHandler;
import com.android.widget.MyWidgetProvider;
import com.google.gson.Gson;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.TextView;

public class MainPage extends Activity implements ResponseHandler {
	private TextView rec_content;
	private MenuItem menuItem;
	private LinearLayout hrClickView;
	private LinearLayout bpClickView;
	private LinearLayout actClickView;
	private LinearLayout sleClickView;
	private LinearLayout rdClickView;
	private TextView historyClickView;
	private TextView rdTitle;
	private TextView rdDate;
	private TextView rdTime;

	private ComponentName widget;
	private RemoteViews remoteViews;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homepage);

		boolean fromNoti = false;
		savedInstanceState = getIntent().getExtras();
		fromNoti = savedInstanceState.getBoolean(AlarmReceiver.notificationState);
		if (fromNoti == true) {
			int id = savedInstanceState.getInt(AlarmService.reminderVar);
			Dialog mainPageDialog = new ReminderDialogPopup().onCreateDialog(this, id);
			mainPageDialog.show();

		}

		hrClickView = (LinearLayout) findViewById(R.id.hr);
		bpClickView = (LinearLayout) findViewById(R.id.bp);
		actClickView = (LinearLayout) findViewById(R.id.act);
		sleClickView = (LinearLayout) findViewById(R.id.sle);
		rdClickView = (LinearLayout) findViewById(R.id.rd);
		historyClickView = (TextView) findViewById(R.id.home_history);

		rec_content = (TextView) findViewById(R.id.rec_content);
		rec_content.setVisibility(View.GONE);

		rdTitle = (TextView) findViewById(R.id.rd_title);
		rdDate = (TextView) findViewById(R.id.rd_date);
		rdTime = (TextView) findViewById(R.id.rd_time);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE
				| ActionBar.DISPLAY_SHOW_CUSTOM);

		updateReminderSection();

		hrClickView.setOnClickListener(getTestClickListener());
		bpClickView.setOnClickListener(getBpClickListener());
		actClickView.setOnClickListener(getActClickListener());
		sleClickView.setOnClickListener(getSleClickListener());
		rdClickView.setOnClickListener(getRdClickListener());
		rec_content.setOnClickListener(getRecClickListener());
		historyClickView.setOnClickListener(getHistoryClickListener());

		widget = new ComponentName(this, MyWidgetProvider.class);
		remoteViews = new RemoteViews(this.getPackageName(), R.layout.widget_layout);
	}

	@Override
	public void onResume() { // After a pause OR at startup
		super.onResume();
		// Refresh your stuff here
		updateReminderSection();
	}

	public void updateReminderSection() {

		MedReminderController mrcInstance = MedReminderController.getInstance();
		mrcInstance.init(getApplicationContext());
		MedReminderModel reminder = null;
		if (mrcInstance.getReminderList().size() > 0) {
			reminder = mrcInstance.getReminderList().get(0); // get the first
																// reminder
			if (!reminder.isActive())
				reminder = null;
		}

		if (reminder != null) {
			rdTitle.setText(reminder.getTitle());
			Calendar calendar = GregorianCalendar.getInstance();
			calendar.setTime(reminder.getNextAlarmTime());

			rdDate.setText(Integer.toString(calendar.get(Calendar.MONTH)) + "/"
					+ Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)) + "/"
					+ Integer.toString(calendar.get(Calendar.YEAR)));

			rdTime.setText(Integer.toString(calendar.get(Calendar.HOUR_OF_DAY)) + ":"
					+ Integer.toString(calendar.get(Calendar.MINUTE)));
		} else {
			rdTitle.setText("No Task");
			rdDate.setText("");
			rdTime.setText("");
		}

	}

	private OnClickListener getTestClickListener() {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainPage.this, test.class));
			}
		};
	}

	private OnClickListener getHistoryClickListener() {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainPage.this, History.class));
			}
		};
	}

	// private OnClickListener getHrClickListener() {
	// return new OnClickListener() {
	// @Override
	// public void onClick(View v) {
	// startActivity(new Intent(MainPage.this, HrDetail.class));
	// }
	// };
	// }

	private OnClickListener getBpClickListener() {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainPage.this, BpDetail.class));
			}
		};
	}

	private OnClickListener getActClickListener() {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainPage.this, SignIn.class));
			}
		};
	}

	private OnClickListener getSleClickListener() {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainPage.this, SleDetail.class));
			}
		};
	}

	private OnClickListener getRdClickListener() {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainPage.this, Reminder.class));
			}
		};
	}

	private OnClickListener getRecClickListener() {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainPage.this, RecContent.class));
			}
		};
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
			startActivity(new Intent(this, (new Profile()).getClass()));
			return true;
		}
		case R.id.action_refresh: {

			menuItem = item;
			menuItem.setActionView(R.layout.progressbar);
			menuItem.expandActionView();
			refresh();
			return true;
		}
		case R.id.action_settings:
			openSettings();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void openSettings() {
		startActivity(new Intent(MainPage.this, Settings.class));
	}

	public void toggle_recom_box(View v) {
		rec_content.setVisibility(rec_content.isShown() ? View.GONE : View.VISIBLE);
	}

	private void refresh() {
		String url = "http://healthengineherokuappcom.apiary.io/";
		// String url = "http://health-engine.herokuapp.com/";
		String json = "{\n    \"userinfo\": {\n        \"age\": 45,\n        \"gender\": \"male\",\n        \"height\": 168,\n        \"weight\": [\n            {\n                \"value\": 65.3,    //this is the data of current day\n                \"date\": \"2012-04-24\"\n            },\n            {\n                \"value\": 65.3,    // this should be average of last week\n                \"date\": \"2012-04-17\"    //by defult this should the last 7 days\n            },\n            {\n                \"value\": 65.3,    // this should be average of last month\n                \"date\": \"2012-03-24\"    //by defult this should the last 30 days\n            }\n        ],\n        \"hypertension\" : true,\n        \"diabetes\" : true,\n        \"insomnia\" : true,\n        \"cardio\" : true\n    },\n    \"activities\": [\n        {\n            \"distance\": 500,     //this is the data of current day\n            \"duration\": 7.3,\n            \"date\": \"2012-04-24\",\n            \"startTime\": \"18:20:42Z\",\n            \"steps\": 800\n        },\n        {\n            \"distance\": 1500,  // this is accumulation not average by last week\n            \"duration\": 140,\n            \"date\": \"2012-04-17\",\n            \"startTime\": \"\",    // timestamp should be empty\n            \"steps\": 1700\n        },\n        {\n            \"distance\": 12500, // this is accumulation not average by last month\n            \"duration\": 1430,\n            \"date\": \"2012-03-24\",\n            \"startTime\": \"\",   // timestamp should be empty\n            \"steps\": 49300\n        }\n    ],\n    \"sleep\": [\n        {\n            \"efficiency\": 4,    //this is the data of current day\n            \"date\": \"2012-04-24\",\n            \"startTime\": \"18:25:43Z\",\n            \"minutesAsleep\": 453,\n            \"minutesAwake\": 34,\n            \"awakeningsCount\": 8,\n            \"timeInBed\": 541\n        },\n        {\n            \"efficiency\": 4,   // this is the average of last week\n            \"date\": \"2012-04-17\",\n            \"startTime\": \"\",   // this should be empty\n            \"minutesAsleep\": 453,\n            \"minutesAwake\": 34,\n            \"awakeningsCount\": 8,\n            \"timeInBed\": 541\n        },\n        {\n            \"efficiency\": 4,  // this is the average of last month\n            \"date\": \"2012-03-24\",\n            \"startTime\": \"\",  // this should be empty\n            \"minutesAsleep\": 453,\n            \"minutesAwake\": 34,\n            \"awakeningsCount\": 8,\n            \"timeInBed\": 541\n        }\n    ],\n    \"heartBeats\": [\n        {\n            \"count\": 56,       //this is the data of current day\n            \"date\": \"2012-04-24\",\n            \"time\": \"18:23:43Z\"\n        },\n        {\n            \"count\": 60,       //this is the average of last week\n            \"date\": \"2012-04-17\",\n            \"time\": \"\"\n        },\n        {\n            \"count\": 59,      //this is the average of last month\n            \"date\": \"2012-03-24\",\n            \"time\": \"\"\n        }\n    ],\n    \"bloodPressures\": [\n        {\n            \"systolic\": 100,         //this is the data of current day\n            \"diastolic\": 71,\n            \"date\": \"2012-04-23\",\n            \"time\": \"18:23:43Z\"\n        },\n        {\n            \"systolic\": 100,         //this is the average of last week\n            \"diastolic\": 71,\n            \"date\": \"2012-04-17\",     \n            \"time\": \"\"               // timestamp should be empty\n        },\n        {\n            \"systolic\": 100,         //this is the average of last month\n            \"diastolic\": 71,\n            \"date\": \"2012-03-24\",\n            \"time\": \"\"               // timestamp should be empty\n        }\n    ]\n}";
		// String json = TestingJson.input1;
		RestCallHandler rest = new RestCallHandler(MainPage.this, url, json);
		rest.handleResponse();
	}

	public void updateWidgetContent(String recContent) {
		remoteViews.setTextViewText(R.id.title, "Recoomendation");
		remoteViews.setTextViewText(R.id.desc, recContent);
		AppWidgetManager.getInstance(this).updateAppWidget(widget, remoteViews);
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
		} else {
			rec_content.setText("Unable to retrieve recommendation");
		}
		updateWidgetContent(rec_content.getText().toString());
		menuItem.collapseActionView();
		menuItem.setActionView(null);
	}

	@Override
	public void processResponse(String jsonResponse) {
		Gson gson = new Gson();
		RecomModel[] recomArray = null;

		if (jsonResponse != null)
			recomArray = gson.fromJson(jsonResponse, RecomModel[].class);
		updateRecBox(recomArray);
	}

}
