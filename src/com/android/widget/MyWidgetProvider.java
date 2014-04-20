package com.android.widget;
import com.android.myhealthmate.R;
import com.android.myhealthmate.RecContent;
import com.android.service.NotificationService;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.TextView;

public class MyWidgetProvider extends AppWidgetProvider {
	TextView content;
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// initializing widget layout
		RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

		// register for button event
		remoteViews.setOnClickPendingIntent(R.id.widget_sec,
				buildButtonPendingIntent(context));

		//get the content sec
		
		// updating view with initial data
	//	TextView title = (TextView) findByViewById (R.id.title);
		remoteViews.setTextViewText(R.id.title, getTitle());
		remoteViews.setTextViewText(R.id.desc, getDesc());
		
		pushWidgetUpdate(context, remoteViews);
	}


	public static PendingIntent buildButtonPendingIntent(Context context) {
		++MyWidgetReceiver.clickCount;


		// initiate widget update request
		Intent toActivity = new Intent(context,RecContent.class);
		toActivity.setAction(WidgetUtils.WIDGET_UPDATE_ACTION);
		toActivity.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

		return PendingIntent.getActivity(context, 133, toActivity, PendingIntent.FLAG_UPDATE_CURRENT);


	}
	
	private static CharSequence getDesc() {
		return "This is a recommendation !!1";
	}

	private static CharSequence getTitle() {
		return "Recommendation";
	}

	public static void pushWidgetUpdate(Context context, RemoteViews remoteViews) {
		ComponentName myWidget = new ComponentName(context,
				MyWidgetProvider.class);
		AppWidgetManager manager = AppWidgetManager.getInstance(context);
		manager.updateAppWidget(myWidget, remoteViews);
	}
}
