package com.android.widget;
import com.android.myhealthmate.R;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class MyWidgetProvider extends AppWidgetProvider {
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// initializing widget layout
		RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

		// register for button event
		remoteViews.setOnClickPendingIntent(R.id.sync_button,
				buildButtonPendingIntent(context));

		// updating view with initial data
	//	TextView title = (TextView) findByViewById (R.id.title);
		remoteViews.setTextViewText(R.id.title, getTitle());
		remoteViews.setTextViewText(R.id.desc, getDesc());
		
		pushWidgetUpdate(context, remoteViews);
	}


	public static PendingIntent buildButtonPendingIntent(Context context) {
		++MyWidgetReceiver.clickCount;

		// initiate widget update request
		Intent intent = new Intent();
		intent.setAction(WidgetUtils.WIDGET_UPDATE_ACTION);
		return PendingIntent.getBroadcast(context, 0, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);
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