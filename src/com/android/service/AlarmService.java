package com.android.service;

import java.util.Date;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class AlarmService {
	private Context context;
	public static final String titleVar = "title";
	public static final String detailVar = "detail";
	public static final String tickerVar = "ticker";
	public static final String idVar = "id";
	public AlarmService(Context context) {
		this.context = context;
	}

	public void setAlarm(Date when, String title, String detail, String ticker, int id) {

		AlarmManager am = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context, AlarmReceiver.class);
		intent.putExtra(titleVar, title);
		intent.putExtra(detailVar, detail);
		intent.putExtra(tickerVar, ticker);
		intent.putExtra(idVar, id);
		PendingIntent pi = PendingIntent.getBroadcast(context, id, intent, 0);
		long firstTime = when.getTime();
		am.set(AlarmManager.RTC_WAKEUP, firstTime, pi);
	}
}
