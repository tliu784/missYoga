package com.android.myhealthmate;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.android.entity.AccountController;
import com.android.entity.RecomModel;
import com.android.recommendation.RecommendationController;
import com.android.reminder.MedReminderController;
import com.android.reminder.MedReminderModel;
import com.android.service.NotificationService;
import com.android.widget.MyWidgetProvider;
import android.app.ActionBar;
import android.app.Activity;
import android.app.NotificationManager;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
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

public class MainPage extends Activity {
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
	private String recommendation = "Recommendation";
	private ComponentName widget;
	private RemoteViews remoteViews;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homepage);
		NotificationManager mNM;
		mNM = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
		mNM.cancel(NotificationService.recNotificationID);
		setTitle(AccountController.getInstance().getAccount().getName());

	

		hrClickView = (LinearLayout) findViewById(R.id.hr);
		bpClickView = (LinearLayout) findViewById(R.id.bp);
		actClickView = (LinearLayout) findViewById(R.id.act);
		sleClickView = (LinearLayout) findViewById(R.id.sle);
		rdClickView = (LinearLayout) findViewById(R.id.rd);
		historyClickView = (TextView) findViewById(R.id.home_history);

		rec_content = (TextView) findViewById(R.id.rec_content);
		recommendation = rec_content.getText().toString();
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
				Intent intent = new Intent(MainPage.this, RecContent.class);
				intent.putExtra(NotificationService.recNotificationState, recommendation);
				startActivity(intent);

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
		RecommendationController rc=RecommendationController.getInstance();
		if (rc.getMainpage()==null)
			rc.setMainpage(this);
		rc.getRecom();
	}
	
	public void postRefresh(RecomModel[] recomArray){
		
		updateRecommendations(recomArray);
	}

	public void updateWidgetContent(String recContent) {
		remoteViews.setTextViewText(R.id.title, "Recommendation");
		remoteViews.setTextViewText(R.id.desc, recContent);
		AppWidgetManager.getInstance(this).updateAppWidget(widget, remoteViews);
	}

	private void updateRecommendations(RecomModel[] recomArray) {
		if (recomArray != null) {
			String recommendationContent ="";
			remoteViews.setTextViewText(R.id.title, "Health Recommendation");
			
			for (int i = 0; i < recomArray.length; i++) {
				recommendationContent+=(recomArray[i].getRecommendation());
				if (i < recomArray.length - 1) {
					recommendationContent+="\n\n";
				}
			}
			//update widgets and main page
			remoteViews.setTextViewText(R.id.desc, recommendationContent);
			AppWidgetManager.getInstance(this).updateAppWidget(widget, remoteViews);
			rec_content.setText(recommendationContent);
				
		} else {
			rec_content.setText("Unable to retrieve recommendation");
		}
		
		//restore refresh button
		menuItem.collapseActionView();
		menuItem.setActionView(null);
	}



}
