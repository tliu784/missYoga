package com.android.reminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;

public class AlarmService {
	private Context context;
	public static final String reminderVar = "reminder";
	public AlarmService(Context context) {
		this.context = context;
	}

	public void setAlarm(MedReminderModel reminder) {
		AlarmManager am = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context, AlarmReceiver.class);
		int id=reminder.getId();
		intent.putExtra(reminderVar, id);
		PendingIntent pi = PendingIntent.getBroadcast(context, reminder.getId(), intent, 0);
		long firstTime = reminder.getNextAlarmTime().getTime();
		am.set(AlarmManager.RTC_WAKEUP, firstTime, pi);
	}
	
	public void cancelAlarm(MedReminderModel reminder){
		AlarmManager am = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context, AlarmReceiver.class);
		String reminderJSON = new Gson().toJson(reminder);
		intent.putExtra(reminderVar,reminderJSON);
		PendingIntent pi = PendingIntent.getBroadcast(context, reminder.getId(), intent, 0);
		am.cancel(pi);
	}
}
