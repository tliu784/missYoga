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

public class AlarmReceiver extends BroadcastReceiver {
		
	@Override
	public void onReceive(Context context, Intent intent) {
		String reminderJSON=intent.getExtras().getString(AlarmService.reminderVar);
		MedReminderModel reminder = new Gson().fromJson(reminderJSON, MedReminderModel.class);
		NotificationManager mNM;
		//to be modified with sending intent
		//need to call putExtras to pass reminder json
		//receiving activity need to handle history
		PendingIntent contentIntent = PendingIntent.getActivity(context, reminder.getId(),new Intent(context, MainPage.class), 0);
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
		MedReminderController mrc=MedReminderController.getInstance();
		mrc.init(context); //init and load 
		MedReminderController.getInstance().postAlarm(reminder.getId());
	
	}
	
	
}
