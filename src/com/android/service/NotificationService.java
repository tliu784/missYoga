package com.android.service;

import com.android.myhealthmate.MainPage;
import com.android.myhealthmate.R;
import com.android.myhealthmate.RecContent;
import com.android.reminder.AlarmReceiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;

public class NotificationService {

	public static final String recNotificationState = "recNotification";
	public static final int recNotificationID = 133;

	public NotificationService(Context context, String title, String content) {
		// get reminder via intent extra variable

		NotificationManager mNM;
		Intent toActivity = new Intent(context, RecContent.class);
		toActivity.putExtra(NotificationService.recNotificationState, content);
		toActivity.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

		PendingIntent recPageIntent = PendingIntent.getActivity(context, recNotificationID, toActivity,
				PendingIntent.FLAG_UPDATE_CURRENT);

		Intent toMainPage = new Intent(context, MainPage.class);
		toActivity.putExtra(AlarmReceiver.notificationState, false);
		
		PendingIntent mainPageIntent = PendingIntent.getActivity(context,recNotificationID, toMainPage, 
				PendingIntent.FLAG_CANCEL_CURRENT);

		mNM = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

		
		Notification notification = new Notification.Builder(context)
				.setContentTitle(title).setContentText(content)
				.setSmallIcon(R.drawable.ic_launcher_logo)
				.setContentIntent(recPageIntent).setAutoCancel(true)
				.setStyle(new Notification.BigTextStyle().bigText(content))
				.addAction(0, "See details", mainPageIntent)
				.addAction(0, "Ok, got it!", mainPageIntent).build();

		notification.priority = Notification.PRIORITY_MAX;
		// notification.flags = Notification.FLAG_ONGOING_EVENT;
		notification.defaults = Notification.DEFAULT_ALL;
		notification.flags |= Notification.FLAG_AUTO_CANCEL;

		mNM.notify(recNotificationID, notification);

	}

	public static void wakeUpScreen(Context context) {

		PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);

		boolean isScreenOn = pm.isScreenOn();
		WakeLock wl = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.FULL_WAKE_LOCK
				| PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.ON_AFTER_RELEASE, "MyLock");

		WakeLock wl_cpu = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyCpuLock");
		if (isScreenOn == false) {
			wl.acquire(50000);
			wl_cpu.acquire(10000);

		}

	}
}
