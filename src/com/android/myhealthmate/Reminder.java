package com.android.myhealthmate;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Reminder extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reminder);
		
		LinearLayout linearLayout = (LinearLayout)  findViewById(R.id.reminder_item);
		TextView valueTV = new TextView(this);
        
		
		valueTV.setText("hallo hallo");
        valueTV.setId(5);
        valueTV.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
//
        if (linearLayout!=null)
        	linearLayout.addView(valueTV);
		//addReminderSection();
	}
	
	private void addReminderSection() {
		
	    

	        
	}
}
