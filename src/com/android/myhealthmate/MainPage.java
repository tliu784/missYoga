package com.android.myhealthmate;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import com.android.entity.AccountController;
import com.android.entity.HealthStatusModel;
import com.android.entity.RecomModel;
import com.android.recommendation.RecommendationController;
import com.android.reminder.MedReminderController;
import com.android.reminder.MedReminderModel;
import com.android.service.NotificationService;
import com.android.trend.RecordList;
import com.android.trend.RecordModel.recordType;
import com.android.widget.MyWidgetProvider;
import android.app.ActionBar;
import android.app.Activity;
import android.app.NotificationManager;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

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
	private Button homeTagBtn;
	private EditText homeTagTxt;

	// health status fields
	private TextView bpsys;
	private TextView bpdia;
	private TextView hrlast;
	private TextView hravg;
	private TextView actsteps;
	private TextView actcal;
	private TextView sleepdeep;
	private TextView sleeplight;
	private TextView sleepawake;
	private TextView updateTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homepage);

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

		homeTagBtn = (Button) findViewById(R.id.home_tag_btn);
		homeTagTxt = (EditText) findViewById(R.id.home_tag_txt);

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

		homeTagBtn.setOnClickListener(getTag());

		widget = new ComponentName(this, MyWidgetProvider.class);
		remoteViews = new RemoteViews(this.getPackageName(), R.layout.widget_layout);

		// health status fields
		bpsys = (TextView) findViewById(R.id.bp_systolic_content);
		bpdia = (TextView) findViewById(R.id.bp_diastolic_content);
		hrlast = (TextView) findViewById(R.id.hr_last_content);
		hravg = (TextView) findViewById(R.id.hr_average_content);
		actsteps = (TextView) findViewById(R.id.act_distance_content);
		actcal = (TextView) findViewById(R.id.act_calories_content);
		sleepdeep = (TextView) findViewById(R.id.sleep_deep_content);
		sleeplight = (TextView) findViewById(R.id.sleep_light_content);
		sleepawake = (TextView) findViewById(R.id.sleep_awake_content);
		updateTime = (TextView) findViewById(R.id.home_current_time);
		
		updateReminderSection();

	}

	@Override
	public void onResume() { // After a pause OR at startup
		super.onResume();
		// Refresh your stuff here
		updateReminderSection();
		// cancel notification
		NotificationManager mNM;
		mNM = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
		mNM.cancel(NotificationService.recNotificationID);
		RecomModel[] recom = null;
		if (RecContent.handleMedicine) {
			if (RecContent.medicineMissed) {
				recom = new RecomModel[1];
				recom[0] = new RecomModel(RecContent.getMed(), false);

				RecContent.medicineMissed = false;
			} else {
				recom = RecContent.getOriginalRecs();
			}
			if (recom != null) {
				updateRecommendations(recom);
				for (RecomModel rec : recom) {
					RecordList.getInstance().addOneRecord(recordType.Recommendation,
							new Date(), // date to be changed
							rec.getRecommendation(),
							RecommendationController.getInstance().toSeverityLevel(rec.getSeverity()), false);
				}

			}
			RecContent.resetFlags();
		}

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
			SimpleDateFormat dateformat = new SimpleDateFormat("MM/dd/yyyy", Locale.CANADA);
			SimpleDateFormat timeformat = new SimpleDateFormat("hh:mm a", Locale.CANADA);
			Date nexttime=reminder.getNextAlarmTime();
			rdDate.setText(dateformat.format(nexttime));
			rdTime.setText(timeformat.format(nexttime));

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

	private OnClickListener getTag() {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (homeTagTxt.getText().toString() != null) {
					RecordList.getInstance().addOneRecord(recordType.Note, new Date(), homeTagTxt.getText().toString(),
							"Note", false);
					Toast.makeText(v.getContext(), "Tag added", Toast.LENGTH_SHORT).show();
					homeTagTxt.setText("");
				} else {
					Toast.makeText(v.getContext(), "Tag cannot be empty", Toast.LENGTH_SHORT).show();
				}
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
		RecommendationController rc = RecommendationController.getInstance();
		if (rc.getMainpage() == null)
			rc.setMainpage(this);
		rc.getRecom();
	}

	public void postRefresh(RecomModel[] recomArray) {

		updateRecommendations(recomArray);
	}

	public void updateHealthStatus(HealthStatusModel hsm) {
		bpsys.setText(Integer.toString(hsm.getBp_systolic()));
		bpdia.setText(Integer.toString(hsm.getBp_diastolic()));
		hravg.setText(Integer.toString(hsm.getHr_count()));
		int adj = (int) (Math.random() * 5);
		if (Math.random() > 0.4) {
			adj = 0 - adj;
		}
		hrlast.setText(Integer.toString(hsm.getHr_count() + adj));
		actsteps.setText(Integer.toString(hsm.getAct_steps()));
		actcal.setText(Integer.toString(hsm.getAct_calories()));
		float deephr = hsm.getSleep_minDeep() / 60f;
		float lighthr = hsm.getSleep_minLight() / 60f;
		float awakehr = hsm.getSleep_minAwake() / 60f;
		String deeptext = String.format("%.1f", deephr);
		String lighttext = String.format("%.1f", lighthr);
		String awaketext = String.format("%.1f", awakehr);
		sleepdeep.setText(deeptext);
		sleeplight.setText(lighttext);
		sleepawake.setText(awaketext);
	}

	public void updateTime(Date timestamp) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mma MM/dd/yyyy", Locale.CANADA);
		updateTime.setText(sdf.format(timestamp));
	}

	public void updateWidgetContent(String recContent) {
		remoteViews.setTextViewText(R.id.title, "Recommendation");
		remoteViews.setTextViewText(R.id.desc, recContent);
		AppWidgetManager.getInstance(this).updateAppWidget(widget, remoteViews);
	}

	private void updateRecommendations(RecomModel[] recomArray) {
		if (recomArray != null) {
			if (recomArray.length > 0) {
				String recommendationContent = "";
				remoteViews.setTextViewText(R.id.title, "Health Recommendation");
				RecomModel mostImportant = recomArray[0];
				for (int i = 0; i < recomArray.length; i++) {
					recommendationContent += (recomArray[i].getRecommendation());
					if (i < recomArray.length - 1) {
						recommendationContent += "\n\n";
					}
					if (recomArray[i].getId()>900)
						mostImportant=recomArray[i];
				}
				// update widgets and main page
				String widgetText=mostImportant.getRecommendation();
				widgetText+="\n\n(Read "+(recomArray.length-1)+" more...)";
				remoteViews.setTextViewText(R.id.desc, widgetText);
				AppWidgetManager.getInstance(this).updateAppWidget(widget, remoteViews);
				rec_content.setText(recommendationContent);
			}
		} else {
			rec_content.setText("Unable to retrieve recommendation");
		}

		// restore refresh button
		menuItem.collapseActionView();
		menuItem.setActionView(null);
	}

}
