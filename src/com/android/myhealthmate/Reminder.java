package com.android.myhealthmate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.myhealthmate.test;
import com.android.reminder.MedReminderController;
import com.android.reminder.MedReminderModel;
import com.android.reminder.MedReminderModel.DurationUnit;

public class Reminder extends Activity {

	private LinearLayout previous = null;
	private LinearLayout previousEditContent = null;
	private TextView previousEditButton = null;
	LinearLayout linearLayout;
	ArrayList<TextView> titleArray;
	ArrayList<TextView> contentArray;
	private MedReminderController mrcInstance = null;
	private MedReminderModel currentReminder;
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd, HH:mm",
			Locale.ENGLISH);

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reminder);
		linearLayout = (LinearLayout) findViewById(R.id.reminder_item);

		titleArray = new ArrayList<TextView>();
		contentArray = new ArrayList<TextView>();

		mrcInstance = getReminders();
		addTestReminders(mrcInstance);

		for (MedReminderModel reminder : mrcInstance.getReminderList()) {
			createNote(reminder);
		}
	}
	

	private MedReminderController getReminders() {
		MedReminderController instance = MedReminderController.getInstance();
		instance.init(getApplicationContext());
		return instance;
	}



	@SuppressLint("ResourceAsColor")
	private LinearLayout createNote(MedReminderModel reminder) {
		LinearLayout reminderSection = new LinearLayout(this);
		LinearLayout newLinearLayout = new LinearLayout(this);
		LinearLayout newNoteLinearLayout = new LinearLayout(this);
		LinearLayout editNoteLinearLayout = new LinearLayout(this);

		TextView editButton = new TextView(this);

		// title section

		newLinearLayout = CreateTitle(reminder, editButton);

		// display content section
		newNoteLinearLayout = CreateNoteSection(reminder);

		// edit content section

		editNoteLinearLayout = CreateEditNoteSection(reminder,
				newNoteLinearLayout, newLinearLayout, editButton);

		// add three sections into layout
		linearLayout.addView(newLinearLayout);
		linearLayout.addView(newNoteLinearLayout);
		linearLayout.addView(editNoteLinearLayout);
		
		//linearLayout.addView(reminderSection);

		newLinearLayout.setOnClickListener(new MyListener(newNoteLinearLayout,
				editButton, editNoteLinearLayout));
		editButton.setOnClickListener(new MyNoteListener(newNoteLinearLayout,
				editNoteLinearLayout, reminder));
		
		return reminderSection;

	}

	private LinearLayout CreateTitle(MedReminderModel reminder, TextView edit) {
		LinearLayout titleSection = new LinearLayout(this);
		EditText notes = new EditText(this);
		TextView reminderTime = new TextView(this);
		TextView title = new TextView(this);
		TextView editButton = new TextView(this);

		editButton = edit;

		// title section
		titleSection.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				70));
		titleSection.setClickable(true);
		titleSection.setOrientation(LinearLayout.HORIZONTAL);

		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(reminder.getNextAlarmTime());
		reminderTime.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		reminderTime.setText(Integer.toString(calendar
				.get(Calendar.HOUR_OF_DAY))
				+ ":"
				+ Integer.toString(calendar.get(Calendar.MINUTE)));
		reminderTime.setTextSize(20);

		title.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		title.setText(reminder.getTitle());
		title.setTextSize(20);
		title.setPadding(20, 0, 40, 0);

		editButton.setCompoundDrawablesWithIntrinsicBounds(0, 0,
				R.drawable.ic_action_edit, 0);
		editButton.setGravity(Gravity.TOP);
		editButton.setClickable(true);
		editButton.setVisibility(View.GONE);

		titleSection.addView(reminderTime);
		titleSection.addView(title);
		titleSection.addView(editButton);
		return titleSection;
	}

	private LinearLayout CreateNoteSection(MedReminderModel reminder) {

		LinearLayout newNoteLinearLayout = new LinearLayout(this);
		TextView content = new TextView(this);
		TextView rdTime = new TextView(this);

		content.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));
		content.setText(reminder.getDetail());
		content.setBackgroundResource(R.drawable.textlines);
		content.setTextSize(20);

		// everyday section in display content section

		rdTime.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));
		if (reminder.isAlawys()) {
			rdTime.setText("Everyday");
		} else {
			rdTime.setText("Create date: "
					+ reminder.getStartTime().toString().replace("PDT ", "")
					+ "\n" + "Durition:" + reminder.getDuration() + " "
					+ reminder.getDunit().name());
		}
		rdTime.setBackgroundResource(R.drawable.textlines);
		rdTime.setTextSize(20);

		newNoteLinearLayout.addView(content);
		newNoteLinearLayout.addView(rdTime);

		newNoteLinearLayout.setLayoutParams(new LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		newNoteLinearLayout.setOrientation(LinearLayout.VERTICAL);
		newNoteLinearLayout.setVisibility(View.GONE);
		return newNoteLinearLayout;
	}

	private LinearLayout CreateEditNoteSection(MedReminderModel reminder,
			LinearLayout NoteLinearLayout, LinearLayout title, TextView editBt) {
		LinearLayout editNoteLinearLayout = new LinearLayout(this);
		LinearLayout oldNoteLinearLayout = NoteLinearLayout;
		LinearLayout oldTitle = title;
		TextView editButton = editBt;

		EditText editNotes = new EditText(this);

		editNoteLinearLayout.setLayoutParams(new LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		editNoteLinearLayout.setOrientation(LinearLayout.VERTICAL);

		// edit notes
		editNotes.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));

		// add start date section in edit content section

		LinearLayout startDateSection = new LinearLayout(this);
		startDateSection.setLayoutParams(new LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		startDateSection.setOrientation(LinearLayout.HORIZONTAL);

		TextView startDate = new TextView(this);
		startDate.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		startDate.setText("Start Date");
		startDate.setBackgroundResource(R.drawable.textlines);
		startDate.setTextSize(20);

		EditText editDate = new EditText(this);
		editDate.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));
		editDate.setHint("yyyymmdd");

		startDateSection.addView(startDate);
		startDateSection.addView(editDate);

		LinearLayout startTimeSection = new LinearLayout(this);
		startTimeSection.setLayoutParams(new LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		startTimeSection.setOrientation(LinearLayout.HORIZONTAL);

		TextView startTime = new TextView(this);
		startTime.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		startTime.setText("Start Time");
		startTime.setBackgroundResource(R.drawable.textlines);
		startTime.setTextSize(20);

		EditText editHours = new EditText(this);
		editHours.setLayoutParams(new LayoutParams(
				R.dimen.login_edittext_width, R.dimen.login_edittext_height));
		editHours.setHint("hh");

		TextView colon = new TextView(this);
		colon.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		colon.setText(":");
		colon.setTextSize(20);

		EditText editMins = new EditText(this);
		editMins.setLayoutParams(new LayoutParams(R.dimen.login_edittext_width,
				R.dimen.login_edittext_height));
		editMins.setHint("mm");

		startTimeSection.addView(startTime);
		startTimeSection.addView(editHours);
		startTimeSection.addView(colon);
		startTimeSection.addView(editMins);

		// add reminder time section in edit content section

		LinearLayout startDurationSection = new LinearLayout(this);
		startDurationSection.setLayoutParams(new LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		startDurationSection.setOrientation(LinearLayout.HORIZONTAL);

		TextView duration = new TextView(this);
		duration.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		duration.setText("Durition");
		duration.setBackgroundResource(R.drawable.textlines);
		duration.setTextSize(20);

		EditText editDuration = new EditText(this);
		editDuration.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		editDuration.setHint("number");

		TextView dayUnit = new TextView(this);
		dayUnit.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		dayUnit.setText("Day");
		dayUnit.setBackgroundResource(R.drawable.textlines);
		dayUnit.setTextSize(20);

		CheckBox checkboxForEveryday = new CheckBox(this);

		checkboxForEveryday.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		checkboxForEveryday.setFocusable(false);

		TextView everydayUnit = new TextView(this);
		everydayUnit.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		everydayUnit.setText("Everyday");
		everydayUnit.setBackgroundResource(R.drawable.textlines);
		everydayUnit.setTextSize(20);

		startDurationSection.addView(duration);
		startDurationSection.addView(editDuration);
		startDurationSection.addView(dayUnit);
		startDurationSection.addView(checkboxForEveryday);
		startDurationSection.addView(everydayUnit);

		// add repeat section
		LinearLayout setRepeatSection = new LinearLayout(this);
		setRepeatSection.setLayoutParams(new LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		setRepeatSection.setOrientation(LinearLayout.HORIZONTAL);

		TextView repeat = new TextView(this);
		repeat.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		repeat.setText("Repeat");
		repeat.setBackgroundResource(R.drawable.textlines);
		repeat.setTextSize(20);

		EditText setRepeatHours = new EditText(this);
		setRepeatHours.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		setRepeatHours.setHint("number");

		TextView hourUnit = new TextView(this);
		hourUnit.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		hourUnit.setText("Hour");
		hourUnit.setBackgroundResource(R.drawable.textlines);
		hourUnit.setTextSize(20);

		CheckBox checkboxTFHour = new CheckBox(this);
		TextView tfhours = new TextView(this);

		checkboxTFHour.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		checkboxTFHour.setFocusable(false);

		tfhours.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		tfhours.setText("24 Hours");
		tfhours.setBackgroundResource(R.drawable.textlines);
		tfhours.setTextSize(20);

		setRepeatSection.addView(repeat);
		setRepeatSection.addView(setRepeatHours);
		setRepeatSection.addView(hourUnit);
		setRepeatSection.addView(checkboxTFHour);
		setRepeatSection.addView(tfhours);

		// add save button
		LinearLayout buttonSection = new LinearLayout(this);
		buttonSection.setLayoutParams(new LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		buttonSection.setOrientation(LinearLayout.HORIZONTAL);

		ArrayList<EditText> editConponents = new ArrayList();
		editConponents.add(editNotes);
		editConponents.add(editDate);
		editConponents.add(editHours);
		editConponents.add(editMins);
		editConponents.add(editDuration);
		editConponents.add(setRepeatHours);
		

		checkboxForEveryday.setOnCheckedChangeListener(new MyCheckBoxListener(reminder,editDuration,dayUnit));		

		checkboxTFHour.setOnCheckedChangeListener(new MyCheckBoxListener(reminder,setRepeatHours,hourUnit));
		
		Button saveButton = new Button(this);
		saveButton.setText("Save");

		saveButton.setOnClickListener(new MySaveListener(reminder,
				editConponents,oldNoteLinearLayout, oldTitle,
				editButton));

		Button cancelButton = new Button(this);
		cancelButton.setText("Cancel");

		buttonSection.addView(saveButton);
		buttonSection.addView(cancelButton);

		// add all sections
		editNoteLinearLayout.addView(editNotes);
		editNoteLinearLayout.addView(startDateSection);
		editNoteLinearLayout.addView(startTimeSection);
		editNoteLinearLayout.addView(startDurationSection);
		editNoteLinearLayout.addView(setRepeatSection);
		editNoteLinearLayout.addView(buttonSection);
		editNoteLinearLayout.setVisibility(View.GONE);
		return editNoteLinearLayout;
	}
	
	

	private class MySaveListener implements Button.OnClickListener {
		ArrayList<EditText> editConponents;
		MedReminderModel reminderItem;
		MedReminderModel oldReminder;
		LinearLayout oldNoteLinearLayout;
		LinearLayout oldTitle;
		TextView editButton;

		public MySaveListener(MedReminderModel reminder,
				ArrayList<EditText> edit, LinearLayout noteLinearLayout,
				LinearLayout title, TextView editBt) {
			reminderItem = reminder;
			oldReminder = reminder;
			editConponents = edit;
			oldNoteLinearLayout = noteLinearLayout;
			oldTitle = title;
			editButton = editBt;
		}

		@Override
		public void onClick(View v) {
			reminderItem.setDetail(editConponents.get(0).getText().toString());

			String dateStr;
			dateStr = editConponents.get(1).getText().toString() + ", "
					+ editConponents.get(2).getText().toString() + ":"
					+ editConponents.get(3).getText().toString();

			try {
				Date date = dateFormat.parse(dateStr);
				reminderItem.setStartTime(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				Toast.makeText(Reminder.this,
						"date or time format is incorrect", Toast.LENGTH_SHORT)
						.show();
				e.printStackTrace();
			}


			if (!editConponents.get(4).getText().toString().equals("")) {
				int duration = Integer.valueOf(editConponents.get(4).getText()
						.toString());
				reminderItem.setDuration(duration);
			}

				


			if (!editConponents.get(5).getText().toString().equals("")) {
				int repeatHours = Integer.valueOf(editConponents.get(5)
						.getText().toString());
				reminderItem.setRepeat(repeatHours);
			}
			
			
			mrcInstance.activate(reminderItem.getId());
			
			Intent intent = getIntent();
		    finish();
		    startActivity(intent);

		}
	}
	
	private class MyCheckBoxListener implements CheckBox.OnCheckedChangeListener {
		MedReminderModel reminderItem;
		EditText editText;
		TextView textView;

		public MyCheckBoxListener(MedReminderModel reminder,EditText edit,TextView text) {
			 reminderItem = reminder;
			 editText = edit;
			 textView = text;
		}

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			// TODO Auto-generated method stub
			if(isChecked){
				reminderItem.setRepeat(24);
				editText.setText("");
				editText.setVisibility(View.GONE);
				textView.setVisibility(View.GONE);
			}
			else{
				editText.setVisibility(View.VISIBLE);
				textView.setVisibility(View.VISIBLE);
			}
		}
	}

	private class MyNoteListener implements TextView.OnClickListener {
		LinearLayout display;
		LinearLayout edit;

		public MyNoteListener(LinearLayout displayNote, LinearLayout editNote,
				MedReminderModel currentR) {
			display = displayNote;
			edit = editNote;
			currentReminder = currentR;
		}

		@Override
		public void onClick(View v) {
			display.setVisibility(View.GONE);
			edit.setVisibility(View.VISIBLE);

		}
	}

	private void closePrevious(LinearLayout content, TextView edit,
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

	private class MyListener implements TextView.OnClickListener {
		LinearLayout content;
		LinearLayout editContent;
		TextView editButton;

		public MyListener(LinearLayout current, TextView edit,
				LinearLayout currentEdit) {
			content = current;
			editContent = currentEdit;
			editButton = edit;
		}

		@Override
		public void onClick(View v) {
			if (content.isShown() || editContent.isShown()) {
				content.setVisibility(View.GONE);
				editButton.setVisibility(View.GONE);
				editContent.setVisibility(View.GONE);
			} else {
				content.setVisibility(content.isShown() ? View.GONE
						: View.VISIBLE);
				editButton.setVisibility(editButton.isShown() ? View.GONE
						: View.VISIBLE);
				closePrevious(content, editButton, editContent);
			}

		}
	}

}
