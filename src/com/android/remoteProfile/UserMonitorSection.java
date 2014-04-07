package com.android.remoteProfile;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.myhealthmate.R;
import com.android.myhealthmate.Settings;
import com.android.trend.RecordModel.recordType;

public class UserMonitorSection {

	TextView nameContent;
	TextView emailContent;
	TextView status;
	GridView Monitorlayout;

	Context context;
	RemoteRequestModel requestUserModel;
	boolean approved;
	int topPadding = 15;
	int padding = 30;
	
	public UserMonitorSection(Settings settings){
	//	Monitorlayout = settings.getMonitorSection();
		nameContent = new TextView(settings);
		emailContent = new TextView(settings);
		status = new TextView(settings);
		Monitorlayout = new GridView(settings);
		this.context = settings;

		
	}
	
	
	public void addNameLayout(RemoteRequestModel requestModel){
		
		setNameViewFomat(this.nameContent,requestModel.getOwnerName());
		Monitorlayout.addView(nameContent); 
		
		setEmailViewFomat(this.emailContent,requestModel.getOwnerEmail());
		Monitorlayout.addView(emailContent);
		
		setNameViewFomat(this.status,requestModel.getOwnerName());
		Monitorlayout.addView(status);
		
	}

	
	private void  setNameViewFomat(TextView textView, String rRes){
		textView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		textView.setTextColor(context.getResources().getColor(R.color.black));
		textView.setText(rRes);
	}
	
	private void  setEmailViewFomat(TextView textView, String rRes){
		textView.setLayoutParams(new LayoutParams(150, LayoutParams.WRAP_CONTENT));
		textView.setTextColor(context.getResources().getColor(R.color.black));
		textView.setText(rRes);
	}
	


}
