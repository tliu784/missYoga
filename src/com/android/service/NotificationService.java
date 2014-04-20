package com.android.service;

import com.android.myhealthmate.R;
import com.android.myhealthmate.RecContent;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.support.v4.app.NotificationCompat;

public class NotificationService{

	public static final String recNotificationState = "recNotification";
	
	public NotificationService(Context context,String title, String content){
			// get reminder via intent extra variable
	
		NotificationManager mNM;
		Intent toActivity = new Intent(context,RecContent.class);
		toActivity.putExtra(NotificationService.recNotificationState, content);
		toActivity.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

		PendingIntent contentIntent = PendingIntent.getActivity(context, 133, toActivity, PendingIntent.FLAG_UPDATE_CURRENT);

		mNM = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

		NotificationCompat.Builder ntbuilder = new NotificationCompat.Builder(context);

		ntbuilder.setTicker("ben test");
		ntbuilder.setSmallIcon(R.drawable.ic_launcher_logo);
		ntbuilder.setAutoCancel(true);
		ntbuilder.setDefaults(-1);
		ntbuilder.setContentIntent(contentIntent);

		Notification notification = new Notification.Builder(context)
				.setContentTitle(title)
				.setContentText("test")
				.setSmallIcon(R.drawable.ic_launcher_logo)
				.setContentIntent(contentIntent)
				.setAutoCancel(true)
				.setStyle(
						new Notification.BigTextStyle()
								.bigText(content))
								.addAction(0, "See details", contentIntent).addAction(0, "Ok, got it!", contentIntent).build();


		notification.priority = Notification.PRIORITY_MAX;
		// notification.flags = Notification.FLAG_ONGOING_EVENT;
		
		mNM.notify(133, notification);
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
