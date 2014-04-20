package com.android.widget;

import com.android.entity.RecomModel;
import com.android.myhealthmate.MainPage;
import com.android.myhealthmate.R;
import com.android.service.ResponseHandler;
import com.android.service.RestCallHandler;
import com.google.gson.Gson;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class MyWidgetReceiver extends BroadcastReceiver implements ResponseHandler {
	public static int clickCount = 0;
	private String msg[] = null;
	private Context context;
	RemoteViews remoteViews;
	String rec_content;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		this.context = context;
		if (intent.getAction().equals(WidgetUtils.WIDGET_UPDATE_ACTION)) {
			updateWidgetPictureAndButtonListener(context);
		}
	}

	private void updateWidgetPictureAndButtonListener(Context context) {
		remoteViews = new RemoteViews(context.getPackageName(),
				R.layout.widget_layout);  
		remoteViews.setTextViewText(R.id.desc, rec_content);
				// re-registering for click listener
		MyWidgetProvider.pushWidgetUpdate(context.getApplicationContext(),
				remoteViews);
	}

	@Override
	public void processResponse(String response) {
		// TODO Auto-generated method stub
		
	}
	
	
}
