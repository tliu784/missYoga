package com.android.myhealthmate;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class RecContent extends Activity {
	
	private TextView recContent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rec_details);		
		 recContent = (TextView) findViewById(R.id.rec_detail);
	}

	
	public void setRecContent(String content){
	
		recContent.setText(content);
	}
	
}
