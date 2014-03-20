package com.android.reminder;

import com.android.myhealthmate.R;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

public class DetailSecTextView extends TextView{
	
	public DetailSecTextView(Context context){
		super(context);
		setFormat();
	}

	private void setFormat(){
		int padding = 5;
		int textSize = 20;
		this.setPadding(padding, padding, padding, padding);
		this.setTextColor(Color.BLACK);
		this.setTextSize(textSize);
		this.setGravity(Gravity.CENTER);
		this.setBackgroundResource(R.drawable.textlines);
		this.setLayoutParams(new LayoutParams(200, LayoutParams.WRAP_CONTENT));
	}
}
