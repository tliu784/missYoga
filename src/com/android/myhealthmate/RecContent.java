package com.android.myhealthmate;

import com.android.reminder.AlarmReceiver;
import com.android.service.NotificationService;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class RecContent extends Activity {

	private TextView recContent;
	private static TextView recommendationContent;
	public static boolean medicineMissed=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rec_details);

		NotificationManager mNM;
		mNM = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
		mNM.cancel(NotificationService.recNotificationID);

		//switch to main activity
		medicineMissed=true;
		Intent openMainPage= new Intent(RecContent.this, MainPage.class);
        openMainPage.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(openMainPage);
		finish();
		
		
//		recContent = (TextView) findViewById(R.id.rec);
//		recommendationContent = (TextView) findViewById(R.id.rec);
//		String fromNoti = "RecContent";
//		savedInstanceState = getIntent().getExtras();
//
//		if (getIntent().getStringExtra(NotificationService.recNotificationState) != null) {
//			fromNoti = savedInstanceState.getString(NotificationService.recNotificationState);
//			setRecContent(fromNoti);
//		}
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
	
	public static void updateRecContent(String content){
		//recommendationContent.setText(content);
	}

}
