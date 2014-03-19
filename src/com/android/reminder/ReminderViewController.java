package com.android.reminder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import com.android.myhealthmate.R;
import com.android.myhealthmate.Reminder;
import com.android.reminder.MedReminderModel.DurationUnit;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ReminderViewController {
	private LinearLayout reminderSection;
	private MedReminderModel reminder;
	private TitleSection titleSec;
	private DetailSection detailSec;
	private EditSection editSec;
	private Context context;
	private MedReminderController mrcInstance;
	private Reminder callbackAct;

	public ReminderViewController(MedReminderModel reminder, Context context,
			MedReminderController controller, Reminder reminderAct) {
		this.reminder = reminder;
		this.context = context;
		this.mrcInstance = controller;
		this.titleSec = new TitleSection();
		this.detailSec = new DetailSection();
		this.editSec = new EditSection();
		this.reminderSection = new LinearLayout(context);
		this.callbackAct = reminderAct;

		// call 3 constructors
		// call 3 set listeners
		titleSec.setListeners();
		editSec.setListeners();

		reminderSection.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		reminderSection.setOrientation(LinearLayout.VERTICAL);
		reminderSection.addView(titleSec.titleSection);
		reminderSection.addView(detailSec.detailSection);
		reminderSection.addView(editSec.editSection);
	}

	public LinearLayout getReminderSection() {
		return reminderSection;
	}

	class TitleSection {
		LinearLayout titleSection;
		EditText notes;
		TextView reminderTime;
		TextView title;
		TextView editButton;

		TitleSection() {
			// constructor: new all views
			titleSection = new LinearLayout(context);
			notes = new EditText(context);
			reminderTime = new TextView(context);
			title = new TextView(context);
			editButton = new TextView(context);
			init();
		}

		private void init() {
			titleSection.setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT, 70));
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
		}

		private void setListeners() {
			editButtonListener();
			titleSectionListener();
		}

		private void titleSectionListener() {
			final LinearLayout currentDetailSection = detailSec.detailSection;
			final LinearLayout currentEditSection = editSec.editSection;
			titleSection.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (currentDetailSection.isShown()
							|| currentEditSection.isShown()) {
						currentDetailSection.setVisibility(View.GONE);
						editButton.setVisibility(View.GONE);
						currentEditSection.setVisibility(View.GONE);
					} else {
						currentDetailSection.setVisibility(currentDetailSection
								.isShown() ? View.GONE : View.VISIBLE);
						editButton.setVisibility(editButton.isShown() ? View.GONE
								: View.VISIBLE);
						callbackAct.closePrevious(currentDetailSection,
								editButton, currentEditSection);
					}
				}
			});
		}

		private void editButtonListener() {
			final LinearLayout currentDetailSection = detailSec.detailSection;
			final LinearLayout currentEditSection = editSec.editSection;
			editButton.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					currentDetailSection.setVisibility(View.GONE);
					currentEditSection.setVisibility(View.VISIBLE);
				}
			});
		}
	}

	class DetailSection {
		private LinearLayout detailSection;
		TextView content;
		TextView rdTime;

		DetailSection() {
			detailSection = new LinearLayout(context);
			content = new TextView(context);
			rdTime = new TextView(context);
			init();
		}

		private void init() {
			content.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT));
			content.setBackgroundResource(R.drawable.textlines);
			content.setTextSize(20);

			// everyday section in display content section

			rdTime.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT));

			rdTime.setBackgroundResource(R.drawable.textlines);
			rdTime.setTextSize(20);

			setDetailPageContent();
			detailSection.addView(content);
			detailSection.addView(rdTime);

			detailSection.setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			detailSection.setOrientation(LinearLayout.VERTICAL);
			detailSection.setVisibility(View.GONE);

		}

		private void setDetailPageContent() {
			// everyday section in display content section
			content.setText(reminder.getDetail());
			if (reminder.isAlawys()) {
				rdTime.setText("Everyday");
			} else {
				rdTime.setText("Create date: "
						+ reminder.getStartTime().toString() + "\n"
						+ "Durition:" + reminder.getDuration() + " "
						+ reminder.getDunit().name());
			}
		}

	}

	class EditSection {
		LinearLayout editSection;
		EditText editNotes;
		LinearLayout startDateSection;
		TextView startDate;
		EditText editDate;
		LinearLayout startTimeSection;
		TextView startTime;
		EditText editHours;
		TextView colon;
		EditText editMins;
		LinearLayout startDurationSection;
		TextView duration;
		EditText editDuration;
		TextView dayUnit;
		CheckBox checkboxForEveryday;
		TextView everydayUnit;
		LinearLayout setRepeatSection;
		TextView repeat;
		EditText setRepeatHours;
		TextView hourUnit;
		CheckBox checkboxTFHour;
		TextView tfhours;
		LinearLayout buttonSection;
		Button saveButton;
		Button cancelButton;

		EditSection() {
			editSection = new LinearLayout(context);
			editNotes = new EditText(context);
			startDateSection = new LinearLayout(context);
			startDate = new TextView(context);
			editDate = new EditText(context);
			startTimeSection = new LinearLayout(context);
			startTime = new TextView(context);
			editHours = new EditText(context);
			colon = new TextView(context);
			editMins = new EditText(context);
			startDurationSection = new LinearLayout(context);
			duration = new TextView(context);
			editDuration = new EditText(context);
			dayUnit = new TextView(context);
			checkboxForEveryday = new CheckBox(context);
			everydayUnit = new TextView(context);
			setRepeatSection = new LinearLayout(context);
			repeat = new TextView(context);
			setRepeatHours = new EditText(context);
			hourUnit = new TextView(context);
			checkboxTFHour = new CheckBox(context);
			tfhours = new TextView(context);
			buttonSection = new LinearLayout(context);
			saveButton = new Button(context);
			cancelButton = new Button(context);
			init();
		}

		private void setListeners() {
			checkboxListener();
			saveButtonListener();
		}

		private void saveButtonListener() {
			final SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyyMMdd, HH:mm", Locale.ENGLISH);

			saveButton.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					//check all edit text fields 
				//	if (true) {
						reminder.setDetail(editNotes.getText().toString());

						String dateStr;
						dateStr = editDate.getText().toString() + ", "
								+ editHours.getText().toString() + ":"
								+ editMins.getText().toString();

						try {
							Date date = dateFormat.parse(dateStr);
							reminder.setStartTime(date);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							Toast.makeText(callbackAct,
									"date or time format is incorrect",
									Toast.LENGTH_SHORT).show();
							e.printStackTrace();
						}

						if (!editDuration.getText().toString().equals("")) {
							int duration = Integer.valueOf(editDuration
									.getText().toString());
							reminder.setDuration(duration);
						}
						if (!setRepeatHours.getText().toString().equals("")) {
							int repeatHours = Integer.valueOf(setRepeatHours
									.getText().toString());
							reminder.setRepeat(repeatHours);
						}
						mrcInstance.activate(reminder.getId());

						detailSec.setDetailPageContent();
						detailSec.detailSection.setVisibility(View.VISIBLE);
						editSec.editSection.setVisibility(View.GONE);
				//	}
				}
			});
		}

		private void checkboxListener() {

			checkboxForEveryday
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {

						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							// TODO Auto-generated method stub
							if (isChecked) {
								reminder.setAlways();
								editDuration.setText("");
								editDuration.setVisibility(View.GONE);
								dayUnit.setVisibility(View.GONE);
							} else {
								editDuration.setVisibility(View.VISIBLE);
								dayUnit.setVisibility(View.VISIBLE);
							}
						}
					});

			checkboxTFHour
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {

						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							// TODO Auto-generated method stub
							if (isChecked) {
								reminder.setRepeat(24);
								reminder.setRunit(DurationUnit.Hour);
								setRepeatHours.setText("");
								setRepeatHours.setVisibility(View.GONE);
								hourUnit.setVisibility(View.GONE);
							} else {
								setRepeatHours.setVisibility(View.VISIBLE);
								hourUnit.setVisibility(View.VISIBLE);
							}
						}
					});
		}

		private void init() {

			editSection.setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			editSection.setOrientation(LinearLayout.VERTICAL);

			// edit notes
			editNotes.setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

			// add start date section in edit content section
			startDateSection.setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			startDateSection.setOrientation(LinearLayout.HORIZONTAL);

			startDate.setLayoutParams(new LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			startDate.setText("Start Date");
			startDate.setBackgroundResource(R.drawable.textlines);
			startDate.setTextSize(20);

			editDate.setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			editDate.setHint("yyyymmdd");

			startDateSection.addView(startDate);
			startDateSection.addView(editDate);

			// add start date section in edit content section
			startTimeSection.setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			startTimeSection.setOrientation(LinearLayout.HORIZONTAL);

			startTime.setLayoutParams(new LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			startTime.setText("Start Time");
			startTime.setBackgroundResource(R.drawable.textlines);
			startTime.setTextSize(20);

			editHours
					.setLayoutParams(new LayoutParams(
							R.dimen.login_edittext_width,
							R.dimen.login_edittext_height));
			editHours.setHint("hh");

			colon.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT));
			colon.setText(":");
			colon.setTextSize(20);

			editMins.setLayoutParams(new LayoutParams(
					R.dimen.login_edittext_width, R.dimen.login_edittext_height));
			editMins.setHint("mm");

			startTimeSection.addView(startTime);
			startTimeSection.addView(editHours);
			startTimeSection.addView(colon);
			startTimeSection.addView(editMins);

			// add reminder duration time section in edit content section

			startDurationSection.setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			startDurationSection.setOrientation(LinearLayout.HORIZONTAL);

			duration.setLayoutParams(new LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			duration.setText("Durition");
			duration.setBackgroundResource(R.drawable.textlines);
			duration.setTextSize(20);

			editDuration.setLayoutParams(new LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			editDuration.setHint("number");

			dayUnit.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT));
			dayUnit.setText("Day");
			dayUnit.setBackgroundResource(R.drawable.textlines);
			dayUnit.setTextSize(20);

			checkboxForEveryday.setLayoutParams(new LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			checkboxForEveryday.setFocusable(false);

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
			setRepeatSection.setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			setRepeatSection.setOrientation(LinearLayout.HORIZONTAL);

			repeat.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT));
			repeat.setText("Repeat");
			repeat.setBackgroundResource(R.drawable.textlines);
			repeat.setTextSize(20);

			setRepeatHours.setLayoutParams(new LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			setRepeatHours.setHint("number");

			hourUnit.setLayoutParams(new LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			hourUnit.setText("Hour");
			hourUnit.setBackgroundResource(R.drawable.textlines);
			hourUnit.setTextSize(20);

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

			buttonSection.setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			buttonSection.setOrientation(LinearLayout.HORIZONTAL);

			saveButton.setText("Save");
			cancelButton.setText("Cancel");

			buttonSection.addView(saveButton);
			buttonSection.addView(cancelButton);

			// add all sections
			editSection.addView(editNotes);
			editSection.addView(startDateSection);
			editSection.addView(startTimeSection);
			editSection.addView(startDurationSection);
			editSection.addView(setRepeatSection);
			editSection.addView(buttonSection);
			editSection.setVisibility(View.GONE);
		}
	}
}
