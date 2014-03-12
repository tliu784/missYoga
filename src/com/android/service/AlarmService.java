package com.android.service;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class AlarmService {
	private Context context;

	public AlarmService(Context context) {
		this.context = context;
	}

	public void SetAlarm() {
		AlarmManager am = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		Intent i = new Intent(context, AlarmReceiver.class);
		PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
		Calendar c = Calendar.getInstance();
		c.add(Calendar.SECOND, 5);
		long firstTime = c.getTimeInMillis();

		am.set(AlarmManager.RTC_WAKEUP, firstTime, pi);
	}
}
