package com.android.myhealthmate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.entity.AccountController;
import com.android.reminder.MedReminderController;
import com.android.reminder.MedReminderModel;
import com.android.reminder.ReminderViewController;
import com.android.reminder.ReminderViewController.TitleSection;

public class Reminder extends Activity {

	private LinearLayout previous = null;
	private LinearLayout previousEditContent = null;
	private TextView previousEditButton = null;
	private TitleSection previousTitle = null;
	LinearLayout linearLayout;
	ArrayList<ReminderViewController> reminderSectionArray = new ArrayList<ReminderViewController>();
	private MedReminderController mrcInstance = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(" Reminder");
		
		
		// Context context=getApplicationContext();
		setContentView(R.layout.reminder);

		TextView tv = (TextView) findViewById(R.id.txtHello);
		tv.setSelected(true);

		linearLayout = (LinearLayout) findViewById(R.id.reminder_item);
		mrcInstance = getReminders();
		addTestReminders(mrcInstance);

		createReminderSections();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.reminder_menu, menu);
		return super.onCreateOptionsMenu(menu);

	}

	private void createReminderSections() {
		for (MedReminderModel reminder : mrcInstance.getReminderList()) {
			ReminderViewController singleReminderSection = new ReminderViewController(reminder, Reminder.this,
					mrcInstance, Reminder.this);
			reminderSectionArray.add(singleReminderSection);
			linearLayout.addView(singleReminderSection.getReminderSection());
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.add_reminder: {
			MedReminderModel newReminder = new MedReminderModel();
			mrcInstance.addReminder(newReminder);
			ReminderViewController singleReminderSection = new ReminderViewController(newReminder, Reminder.this,
					mrcInstance, Reminder.this);
			reminderSectionArray.add(singleReminderSection);
			singleReminderSection.createReminder();
			linearLayout.addView(singleReminderSection.getReminderSection(), 1);
			return true;
		}
		case R.id.delete_reminder: {

			for (ReminderViewController reminderController : reminderSectionArray) {

				reminderController.toggleDeleteButton();
			}
			return true;
		}
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void hideAllDeleteButton() {
		for (ReminderViewController reminderController : reminderSectionArray) {
			reminderController.hideDeleteButton();
		}
	}

	private MedReminderController getReminders() {
		MedReminderController instance = MedReminderController.getInstance();
		instance.init(getApplicationContext());
		return instance;

	}

	public void closePrevious(LinearLayout content, TextView edit, LinearLayout editContent, TitleSection title) {
		if (previous != null)
			if (!previous.equals(content)) {
				previous.setVisibility(View.GONE);
				previousEditButton.setVisibility(View.GONE);
				previousEditContent.setVisibility(View.GONE);
				previousTitle.showDetailModeTitle();
				previousTitle.changeTitleBackgroundColorToDefault();
			}
		if (previous != null)
			if (!previous.equals(content)) {
				previous = content;
				previousEditButton = edit;
				previousEditContent = editContent;
				previousTitle = title;
			}
		if (previous == null) {
			previous = content;
			previousEditButton = edit;
			previousEditContent = editContent;
			previousTitle = title;
		}
	}

	public void addOneReminder(MedReminderController reminders) {
		Date creationTime = Calendar.getInstance().getTime();
		String title = "aspirin";
		String detail = "take 1 pill";
		int duration = 2;
		MedReminderModel.DurationUnit dunit = MedReminderModel.DurationUnit.Day;
		int repeat = 4;
		MedReminderModel.DurationUnit runit = MedReminderModel.DurationUnit.Hour;
		reminders.addReminder(creationTime, title, detail, duration, dunit, repeat, runit);
	}

	public void addTestReminders(MedReminderController reminders) {

		if (reminders.getReminderList().size() > 0)
			return;
		// reminder 1
		Date creationTime = Calendar.getInstance().getTime();
		String title = "Aspirin";
		String detail = "Take 1 pill";
		int duration = 1;
		MedReminderModel.DurationUnit dunit = MedReminderModel.DurationUnit.Day;
		int repeat = 4;
		MedReminderModel.DurationUnit runit = MedReminderModel.DurationUnit.Hour;
		reminders.addReminder(creationTime, title, detail, duration, dunit, repeat, runit);
		// reminder 2
		Date creationTime2 = Calendar.getInstance().getTime();
		String title2 = "Kanamycin";
		String detail2 = "Take 2 pills";
		int duration2 = 2;
		MedReminderModel.DurationUnit dunit2 = MedReminderModel.DurationUnit.Day;
		int repeat2 = 5;
		MedReminderModel.DurationUnit runit2 = MedReminderModel.DurationUnit.Day;
		reminders.addReminder(creationTime2, title2, detail2, duration2, dunit2, repeat2, runit2);

		// reminder 3

		Date creationTime3 = Calendar.getInstance().getTime();
		String title3 = "Qsymia";
		String detail3 = "Take 3 pills";
		int duration3 = 3;
		MedReminderModel.DurationUnit dunit3 = MedReminderModel.DurationUnit.Day;
		int repeat3 = 10;
		MedReminderModel.DurationUnit runit3 = MedReminderModel.DurationUnit.Day;
		reminders.addReminder(creationTime3, title3, detail3, duration3, dunit3, repeat3, runit3);

	}

}