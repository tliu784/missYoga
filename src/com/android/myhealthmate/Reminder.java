package com.android.myhealthmate;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Reminder extends Activity {

	private TextView reminderNotes;
	LinearLayout linearLayout;
	ArrayList<TextView> titleArray;
	ArrayList<TextView> contentArray;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reminder);
		linearLayout = (LinearLayout) findViewById(R.id.reminder_item);
		TextView valueTV = new TextView(this);

		valueTV.setText("hallo hallo");
		valueTV.setId(5);
		valueTV.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		//
		if (linearLayout != null)
			linearLayout.addView(valueTV);

		titleArray = new ArrayList<TextView>();
		contentArray = new ArrayList<TextView>();

		//for (int i = 0; i < 10; i++) {
			createNote(1);
	//	}
	

	}

//	private OnClickListener getBenPageClickListener(TextView content) {
//		return new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				content.setVisibility(reminderNotes.isShown() ? View.GONE
//						: View.VISIBLE);
//			}
//		};
//	}

	@SuppressLint("ResourceAsColor")
	private void createNote(int id) {
		LinearLayout newLinearLayout = new LinearLayout(this);
		TextView title = new TextView(this);
		final TextView content = new TextView(this);

		// newLinearLayout.setLayoutParams(new
		// LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
		// newLinearLayout.getChildAt(1);
		// newLinearLayout.setBackgroundResource(R.drawable.bolder);
		// newLinearLayout.setOrientation(LinearLayout.VERTICAL);

		title.setId(id);
		title.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));
		title.setBackgroundColor(R.color.light_grey);
		title.setText("Reminder");
		title.setClickable(true);
		title.setTextSize(20);

		titleArray.add(title);
		content.setId(id+1);
		content.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));
		content.setText("LLLLLLLLlalalalalla");
		content.setTextSize(20);
		

		
		contentArray.add(content);

		linearLayout.addView(title);
		linearLayout.addView(content);
		
		title.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				content.setVisibility(reminderNotes.isShown() ? View.GONE
						: View.VISIBLE);
			}
		});

	}

	public void ButtonOnClick(View v) {
		reminderNotes = (TextView) findViewById(v.getId());
		getResources().getIdentifier("ball_red", "drawable", getPackageName());

		toggle_recom_box(v);
	}

	public void toggle_recom_box(View v) {
		reminderNotes.setVisibility(reminderNotes.isShown() ? View.GONE
				: View.VISIBLE);
	}

}
