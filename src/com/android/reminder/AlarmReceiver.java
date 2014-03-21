package com.android.reminder;

import com.android.myhealthmate.MainPage;
import com.android.myhealthmate.R;
import com.google.gson.Gson;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
		
	public static final String notificationState = "notification";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		//initialized controller
		MedReminderController mrc=MedReminderController.getInstance();
		mrc.init(context);  
		//get reminder via intent extra variable
		int id=intent.getExtras().getInt(AlarmService.reminderVar);
		MedReminderModel reminder=mrc.findbyid(id);
		
		NotificationManager mNM;
		Intent toActivity = new Intent(context, MainPage.class);
		toActivity.putExtra(notificationState, true);
		
		PendingIntent contentIntent = PendingIntent.getActivity(context, reminder.getId(),toActivity, 0);
		mNM = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);

		Notification.Builder ntbuilder = new Notification.Builder(context);
		ntbuilder.setContentTitle(reminder.getTitle());
		ntbuilder.setContentText(reminder.getDetail());
		ntbuilder.setTicker(reminder.getTitle());
		ntbuilder.setSmallIcon(R.drawable.ic_launcher_logo);
		ntbuilder.setAutoCancel(true);
		ntbuilder.setDefaults(-1);
		Bitmap bm = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.ic_launcher_logo);
		ntbuilder.setLargeIcon(bm);
		ntbuilder.setWhen(System.currentTimeMillis());
		ntbuilder.setContentIntent(contentIntent);
		Notification notification = ntbuilder.build();
		mNM.notify(reminder.getId(), notification);
		//set next alarm

		MedReminderController.getInstance().postAlarm(reminder.getId());
	
	}
	
	
}
