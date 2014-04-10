package com.android.reminder;

import com.android.myhealthmate.MainPage;
import com.android.myhealthmate.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {

	public static final String notificationState = "notification";

	@Override
	public void onReceive(Context context, Intent intent) {
		// initialized controller
		MedReminderController mrc = MedReminderController.getInstance();
		mrc.init(context);
		// get reminder via intent extra variable
		int id = intent.getExtras().getInt(AlarmService.reminderVar);
		MedReminderModel reminder = mrc.findbyid(id);

		NotificationManager mNM;
		Intent toActivity = new Intent(context, MainPage.class);
		toActivity.putExtra(notificationState, true);
		toActivity.putExtra(AlarmService.reminderVar, id);

		PendingIntent contentIntent = PendingIntent.getActivity(context, reminder.getId(), toActivity, 0);
		mNM = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

		NotificationCompat.Builder ntbuilder = new NotificationCompat.Builder(context);

		ntbuilder.setTicker(reminder.getTitle());
		ntbuilder.setSmallIcon(R.drawable.ic_launcher_logo);
		ntbuilder.setAutoCancel(true);
		ntbuilder.setDefaults(-1);
		ntbuilder.setContentIntent(contentIntent);

		Notification notification = new Notification.Builder(context)
				.setContentTitle("Recommendation")
				.setContentText("test")
				.setSmallIcon(R.drawable.ic_launcher_logo)
				.setContentIntent(contentIntent)
				.setAutoCancel(true)
				.setStyle(
						new Notification.BigTextStyle()
								.bigText("Main page of the Health Canada Web site; links to topics covered on the Web site, latest advisories, news releases and current Web site highlights"))
				.addAction(0, "See details", contentIntent).addAction(0, "Ok, got it!", contentIntent).build();


		notification.priority = Notification.PRIORITY_MAX;
		// notification.flags = Notification.FLAG_ONGOING_EVENT;

		mNM.notify(reminder.getId(), notification);
		// set next alarm

		MedReminderController.getInstance().postAlarm(reminder.getId());

	}

}
