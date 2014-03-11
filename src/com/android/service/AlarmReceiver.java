package com.android.service;

import com.android.myhealthmate.MainPage;
import com.android.myhealthmate.R;
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
		String notiTitle = "Time to take pills";
		String notiContent = "take that aspirin pill man";
		String tickerText = "New Health Reminder";

		NotificationManager mNM;
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0,	new Intent(context, MainPage.class), 0);
		mNM = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);

		Notification.Builder ntbuilder = new Notification.Builder(context);
		ntbuilder.setContentTitle(notiTitle);
		ntbuilder.setContentText(notiContent);
		ntbuilder.setTicker(tickerText);
		ntbuilder.setSmallIcon(R.drawable.ic_launcher_logo);
		ntbuilder.setAutoCancel(true);
		ntbuilder.setDefaults(-1);
		Bitmap bm = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.ic_launcher_logo);
		ntbuilder.setLargeIcon(bm);
		ntbuilder.setWhen(System.currentTimeMillis());
		ntbuilder.setContentIntent(contentIntent);
		Notification notification = ntbuilder.build();


		mNM.notify(0, notification);

	}
	
	
}
