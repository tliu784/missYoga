package com.android.myhealthmate;

import com.android.reminder.AlarmReceiver;
import com.android.service.NotificationService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class RecContent extends Activity {

	private TextView recContent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rec_details);
		recContent = (TextView) findViewById(R.id.rec);

		String fromNoti = "RecContent";
		savedInstanceState = getIntent().getExtras();

		if (getIntent().getStringExtra(NotificationService.recNotificationState) != null) {
			fromNoti = savedInstanceState.getString(NotificationService.recNotificationState);
			setRecContent(fromNoti);
		}
	}

	public void onNewIntent(Intent intent) {
		if (getIntent().getStringExtra(NotificationService.recNotificationState) != null) {
			String fromNoti = getIntent().getExtras().getString(NotificationService.recNotificationState);
			setRecContent(fromNoti);
		}

	}

	public void setRecContent(String content) {

		recContent.setText(content);

	}

}
