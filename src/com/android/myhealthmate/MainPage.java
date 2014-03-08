package com.android.myhealthmate;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainPage extends Activity {
	private TextView rec_content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homepage);

		rec_content = (TextView) findViewById(R.id.rec_content);
		rec_content.setVisibility(View.GONE);

	}

	public void toggle_contents(View v) {

		rec_content.setVisibility(rec_content.isShown() ? View.GONE
				: View.VISIBLE);
	}
}
