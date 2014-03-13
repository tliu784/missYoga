package com.android.myhealthmate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.controlmodel.MedReminderEvents;
import com.android.entity.MedReminderModel;
import com.android.myhealthmate.test;

public class Reminder extends Activity {

	private TextView previous = null;
	LinearLayout linearLayout;
	ArrayList<TextView> titleArray;
	ArrayList<TextView> contentArray;
	
	
	private MedReminderEvents createReminders(){
		MedReminderEvents reminders = new MedReminderEvents();
		//reminder 1
		Date creationTime = Calendar.getInstance().getTime();
		String title = "aspirin";
		String detail = "take 1 pill";
		int duration = 2;
		MedReminderEvents.DurationUnit dunit = MedReminderEvents.DurationUnit.Day;
		int repeat = 4;
		MedReminderEvents.DurationUnit runit = MedReminderEvents.DurationUnit.Hour;
		reminders.addReminder(creationTime, title, detail, duration, dunit,
				repeat, runit);
		//reminder 2
		Date creationTime2 = Calendar.getInstance().getTime();
		String title2 = "title 2";
		String detail2 = "take 2 pills";
		int duration2 = 2;
		MedReminderEvents.DurationUnit dunit2 = MedReminderEvents.DurationUnit.Day;
		int repeat2 = 5;
		MedReminderEvents.DurationUnit runit2 = MedReminderEvents.DurationUnit.Sec;
		reminders.addReminder(creationTime2, title2, detail2, duration2, dunit2,
				repeat2, runit2);
		
		//reminder 3
	
		Date creationTime3 = Calendar.getInstance().getTime();
		String title3 = "title 3";
		String detail3 = "take 3 pills";
		int duration3 = 2;
		MedReminderEvents.DurationUnit dunit3 = MedReminderEvents.DurationUnit.Day;
		int repeat3 = 10;
		MedReminderEvents.DurationUnit runit3 = MedReminderEvents.DurationUnit.Sec;
		reminders.addReminder(creationTime3, title3, detail3, duration3, dunit3,
				repeat3, runit3);
		return reminders;
	}
	
	private void somefunction(){
	MedReminderEvents reminders = createReminders();
	ArrayList<MedReminderModel> reminderlist=reminders.getReminderList();
//	reminderlist.get(0);
	for (MedReminderModel reminder: reminderlist){
		reminder.getTitle();
	}
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reminder);
		linearLayout = (LinearLayout) findViewById(R.id.reminder_item);

		titleArray = new ArrayList<TextView>();
		contentArray = new ArrayList<TextView>();

		for (int i = 0; i < 10; i++) {
			createNote();
		}

	}

	// private OnClickListener getBenPageClickListener(TextView content) {
	// return new OnClickListener() {
	// @Override
	// public void onClick(View v) {
	// content.setVisibility(reminderNotes.isShown() ? View.GONE
	// : View.VISIBLE);
	// }
	// };
	// }

	@SuppressLint("ResourceAsColor")
	private void createNote() {
		// LinearLayout newLinearLayout = new LinearLayout(this);
		TextView title = new TextView(this);
		TextView content = new TextView(this);

		// newLinearLayout.setLayoutParams(new
		// LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
		// newLinearLayout.getChildAt(1);
		// newLinearLayout.setBackgroundResource(R.drawable.bolder);
		// newLinearLayout.setOrientation(LinearLayout.VERTICAL);

		title.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));
		title.setBackgroundColor(R.color.light_grey);
		title.setText("Reminder");
		title.setClickable(true);
		title.setTextSize(20);

		titleArray.add(title);

		content.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));
		content.setText("LLLLLLLLlalalalalla");
		content.setTextSize(20);
		content.setVisibility(View.GONE);
		contentArray.add(content);

		linearLayout.addView(title);
		linearLayout.addView(content);
		title.setOnClickListener(new MyListener(content));
		// title.setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// content.setVisibility(content.isShown() ? View.GONE
		// : View.VISIBLE);
		// }
		// });

	}

	private void closePrevious(TextView content) {
		if (previous != null)
			if (!previous.equals(content))
				previous.setVisibility(View.GONE);
		if (previous != null)
			if (!previous.equals(content))
				previous = content;
		if (previous == null) {
			previous = content;
		}
	}

	private class MyListener implements TextView.OnClickListener {
		TextView content;
		TextView previousContent;

		public MyListener(TextView current) {
			content = current;
			previousContent = previous;
		}

		@Override
		public void onClick(View v) {
			content.setVisibility(content.isShown() ? View.GONE : View.VISIBLE);

			//
			// if (previousContent != null) {
			// if (!previousContent.equals(content)) {
			// previousContent.setVisibility(View.GONE);
			// }
			// }
			closePrevious(content);

		}
	}

}
