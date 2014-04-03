package com.android.reminder;


import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.TextView;

public class DetailSecTextView extends TextView{
	
	public DetailSecTextView(Context context){
		super(context);
		setFormat();
	}

	private void setFormat(){
		int textSize = 16;
		this.setPadding(0, 5, 10, 5);
		this.setTextColor(Color.BLACK);
		this.setTypeface(null, Typeface.BOLD);
		this.setTextSize(textSize);
		this.setGravity(Gravity.RIGHT);
//		this.setBackgroundResource(R.drawable.textlines);
		this.setLayoutParams(new LayoutParams(200, LayoutParams.WRAP_CONTENT));
	}
}
