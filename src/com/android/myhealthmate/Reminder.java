package com.android.myhealthmate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.reminder.MedReminderController;
import com.android.reminder.MedReminderModel;
import com.android.reminder.ReminderViewController;

public class Reminder extends Activity {

	private LinearLayout previous = null;
	private LinearLayout previousEditContent = null;
	private TextView previousEditButton = null;
	LinearLayout linearLayout;
	ArrayList<TextView> titleArray;
	ArrayList<TextView> contentArray;
	private MedReminderController mrcInstance = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Context context=getApplicationContext();
		setContentView(R.layout.reminder);
		linearLayout = (LinearLayout) findViewById(R.id.reminder_item);


		mrcInstance = getReminders();
		addTestReminders(mrcInstance);

		createReminderSections();
	}
	
	private void createReminderSections(){
		for (MedReminderModel reminder : mrcInstance.getReminderList()) {
			ReminderViewController singleReminderSection = new ReminderViewController(reminder,getApplicationContext(),mrcInstance,Reminder.this);
			linearLayout.addView(singleReminderSection.getReminderSection());
		}
	}

	private MedReminderController getReminders() {
		MedReminderController instance = MedReminderController.getInstance();
		instance.init(getApplicationContext());
		return instance;

	}
	
	public void closePrevious(LinearLayout content, TextView edit,
			LinearLayout editContent) {
		if (previous != null)
			if (!previous.equals(content)) {
				previous.setVisibility(View.GONE);
				previousEditButton.setVisibility(View.GONE);
				previousEditContent.setVisibility(View.GONE);
			}
		if (previous != null)
			if (!previous.equals(content)) {
				previous = content;
				previousEditButton = edit;
				previousEditContent = editContent;
			}
		if (previous == null) {
			previous = content;
			previousEditButton = edit;
			previousEditContent = editContent;
		}
	}
	
	public void addTestReminders(MedReminderController reminders) {

		if (reminders.getReminderList().size() > 0)
			return;
		// reminder 1
		Date creationTime = Calendar.getInstance().getTime();
		String title = "aspirin";
		String detail = "take 1 pill";
		int duration = 2;
		MedReminderModel.DurationUnit dunit = MedReminderModel.DurationUnit.Day;
		int repeat = 4;
		MedReminderModel.DurationUnit runit = MedReminderModel.DurationUnit.Hour;
		reminders.addReminder(creationTime, title, detail, duration, dunit,
				repeat, runit);
		// reminder 2
		Date creationTime2 = Calendar.getInstance().getTime();
		String title2 = "title 2";
		String detail2 = "take 2 pills";
		int duration2 = 2;
		MedReminderModel.DurationUnit dunit2 = MedReminderModel.DurationUnit.Day;
		int repeat2 = 5;
		MedReminderModel.DurationUnit runit2 = MedReminderModel.DurationUnit.Day;
		reminders.addReminder(creationTime2, title2, detail2, duration2,
				dunit2, repeat2, runit2);

		// reminder 3

		Date creationTime3 = Calendar.getInstance().getTime();
		String title3 = "title 3";
		String detail3 = "take 3 pills";
		int duration3 = 2;
		MedReminderModel.DurationUnit dunit3 = MedReminderModel.DurationUnit.Day;
		int repeat3 = 10;
		MedReminderModel.DurationUnit runit3 = MedReminderModel.DurationUnit.Day;
		reminders.addReminder(creationTime3, title3, detail3, duration3,
				dunit3, repeat3, runit3);

	}

}