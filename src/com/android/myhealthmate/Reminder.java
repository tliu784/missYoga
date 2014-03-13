package com.android.myhealthmate;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Reminder extends Activity {

	private TextView reminderNotes;
	
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
		
        
	}
	
	private void createNote(){
		LinearLayout newLinearLayout = new LinearLayout(this);
		TextView title = new TextView(this);
		TextView contexnt = new TextView(this);
		
		newLinearLayout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
		newLinearLayout.getChildAt(1);
		newLinearLayout.setBackgroundResource(R.drawable.bolder);
	//	newLinearLayout.setOrientation(VERTICAL);
	}
	
	public void ButtonOnClick(View v) {
	    reminderNotes = (TextView)findViewById(v.getId());
	    getResources().getIdentifier("ball_red", "drawable", getPackageName());
	   
	    toggle_recom_box(v);
	}
	
	
	public void toggle_recom_box(View v) {
		reminderNotes.setVisibility(reminderNotes.isShown() ? View.GONE
				: View.VISIBLE);
	}
	
}
