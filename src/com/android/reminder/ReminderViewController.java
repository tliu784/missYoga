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

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
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
	private boolean isNew;

	public ReminderViewController(MedReminderModel reminder, Context context, MedReminderController controller,
			Reminder reminderAct) {
		this.reminder = reminder;
		this.context = context;
		this.mrcInstance = controller;
		this.titleSec = new TitleSection();
		this.detailSec = new DetailSection();
		this.editSec = new EditSection();
		this.reminderSection = new LinearLayout(context);
		this.callbackAct = reminderAct;
		this.isNew = false;

		// call 3 constructors
		// call 3 set listeners
		titleSec.setListeners();
		editSec.setListeners();

		reminderSection.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		reminderSection.setPadding(0, 0, 0, 5);
		reminderSection.setOrientation(LinearLayout.VERTICAL);
		reminderSection.addView(titleSec.titleSection);
		reminderSection.addView(detailSec.detailSection);
		reminderSection.addView(editSec.editSection);
	}

	public LinearLayout getReminderSection() {
		return reminderSection;
	}

	public void toggleDeleteButton() {
		titleSec.deleteButton.setVisibility(titleSec.deleteButton.isShown() ? View.GONE : View.VISIBLE);
	}

	public void hideDeleteButton() {
		titleSec.deleteButton.setVisibility(View.GONE);
	}

	public void createReminder() {
		isNew = true;
		titleSec.editModeTitle.setText("Create Reminder");
		titleSec.editModeTitle();
		titleSec.titleSection.setBackgroundResource(R.drawable.highlight_select_title_section);
		detailSec.detailSection.setVisibility(View.GONE);
		editSec.editSection.setVisibility(View.VISIBLE);
	}

	public class TitleSection {
		LinearLayout titleSection;
		EditText notes;
		LinearLayout contentSection;
		TextView reminderTime;
		TextView title;
		TextView editButton;
		TextView editModeTitle;
		TextView deleteButton;
		TextView validButton;
		LinearLayout buttonSection;
		LinearLayout validSection;

		TitleSection() {
			// constructor: new all views
			titleSection = new LinearLayout(context);
			buttonSection = new LinearLayout(context);
			validSection = new LinearLayout(context);
			notes = new EditText(context);
			reminderTime = new TextView(context);
			title = new TextView(context);
			contentSection = new LinearLayout(context);
			editButton = new TextView(context);
			editModeTitle = new TextView(context);
			deleteButton = new TextView(context);
			validButton = new TextView(context);
			init();
		}

		private void init() {
			// --------------------content section----------------------
			titleSection.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 100));
			titleSection.setClickable(true);
			titleSection.setOrientation(LinearLayout.HORIZONTAL);
			titleSection.setBackgroundResource(R.drawable.textlines);

			contentSection.setLayoutParams(new LayoutParams(500, LayoutParams.MATCH_PARENT));
			contentSection.setOrientation(LinearLayout.HORIZONTAL);

			reminderTime.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
			reminderTime.setGravity(Gravity.CENTER);
			reminderTime.setTextSize(16);
			reminderTime.setPadding(10, 0, 0, 0);
			reminderTime.setTextColor(context.getResources().getColor(R.color.black));
			reminderTime.setTypeface(null, Typeface.BOLD);

			title.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			title.setGravity(Gravity.CENTER_VERTICAL);
			title.setPadding(10, 0, 40, 0);
			title.setTextSize(16);
			title.setTypeface(null, Typeface.BOLD);
//			title.setTextAppearance(context, R.style.title);

			contentSection.addView(reminderTime);
			contentSection.addView(title);

			// --------------------valid section----------------------
			validSection.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
			validSection.setGravity(Gravity.BOTTOM);
			validButton.setPadding(10, 0, 0, 0);
			// validButton
			if (reminder.isActive()) {
				validButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_alarms_light, 0, 0, 0);
			} else {
				validButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_alarms, 0, 0, 0);
				validButton.setGravity(Gravity.TOP);
			}
			validButton.setClickable(true);
			validSection.addView(validButton);

			// ----------------buttonSection---------------------------------
			editButton.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			editButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_edit, 0, 0, 0);
			editButton.setClickable(true);
			editButton.setVisibility(View.GONE);

			deleteButton.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			deleteButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_remove, 0, 0, 0);
			deleteButton.setVisibility(View.GONE);
			deleteButton.setClickable(true);

			editModeTitle.setText("Edit Reminder");
			editModeTitle.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			editModeTitle.setTextSize(20);
			editModeTitle.setTextColor(Color.WHITE);
			editModeTitle.setGravity(Gravity.CENTER);

			buttonSection.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			buttonSection.setGravity(Gravity.RIGHT);
			buttonSection.setOrientation(LinearLayout.HORIZONTAL);
			buttonSection.setPadding(0, 10, 10, 0);

			buttonSection.addView(editButton);
			buttonSection.addView(deleteButton);

			setTitleContent();
			titleSection.addView(validSection);
			titleSection.addView(contentSection);
			titleSection.addView(buttonSection);
		}

		public void setTitleContent() {
			if (reminder != null) {
				Calendar calendar = GregorianCalendar.getInstance();
				calendar.setTime(reminder.getNextAlarmTime());

				reminderTime.setText(Integer.toString(calendar.get(Calendar.HOUR_OF_DAY)) + ":"
						+ Integer.toString(calendar.get(Calendar.MINUTE)));
				title.setText(reminder.getTitle());
			}
		}

		public void editModeTitle() {
			if (titleSection.getChildAt(0).equals(validSection)) {
				titleSection.removeView(validSection);
				titleSection.removeView(contentSection);
				// titleSection.removeView(title);
				titleSection.removeView(buttonSection);
				titleSection.addView(editModeTitle);
			}
		}

		public void showDetailModeTitle() {
			if (!titleSection.getChildAt(0).equals(validSection)) {
				titleSection.addView(validSection);
				titleSection.addView(contentSection);
				// titleSection.addView(title);
				titleSection.addView(buttonSection);
				titleSection.removeView(editModeTitle);
			}
		}

		private void setListeners() {
			editButtonListener();
			titleSectionListener();
			deleteButtonListener();
			// validButtonListener();
		}

		// private void validButtonListener() {
		// validButton.setOnClickListener(new OnClickListener() {
		// public void onClick(View v) {
		// if (reminder.isActive()) {
		// mrcInstance.deactivate(reminder.getId());
		// validButton.setCompoundDrawablesWithIntrinsicBounds(0,
		// 0, R.drawable.ic_action_alarms_dark, 0);
		//
		// } else {
		// mrcInstance.activate(reminder.getId());
		// validButton.setCompoundDrawablesWithIntrinsicBounds(0,
		// 0, R.drawable.ic_action_alarms_light, 0);
		// }
		// }
		// });
		//
		// }

		private void deleteButtonListener() {
			deleteButton.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					reminderSection.removeView(titleSec.titleSection);
					reminderSection.removeView(detailSec.detailSection);
					reminderSection.removeView(editSec.editSection);
					mrcInstance.removeReminder(reminder.getId());
				}
			});

		}

		private void titleSectionListener() {
			final LinearLayout currentDetailSection = detailSec.detailSection;
			final LinearLayout currentEditSection = editSec.editSection;
			titleSection.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					callbackAct.hideAllDeleteButton();
					if (currentDetailSection.isShown() || currentEditSection.isShown()) {
						titleSec.showDetailModeTitle();
						changeTitleBackgroundColorToDefault();
						currentDetailSection.setVisibility(View.GONE);
						editButton.setVisibility(View.GONE);
						currentEditSection.setVisibility(View.GONE);
					} else {
						if (currentDetailSection.isShown()) {
							currentDetailSection.setVisibility(View.GONE);
							changeTitleBackgroundColorToDefault();
							titleSec.showDetailModeTitle();
						} else {
							currentDetailSection.setVisibility(View.VISIBLE);
							changeTitleBackgroundColorToBlue();
						}
						editButton.setVisibility(editButton.isShown() ? View.GONE : View.VISIBLE);
						callbackAct.closePrevious(currentDetailSection, editButton, currentEditSection, titleSec);
					}
				}
			});
		}

		public void changeTitleBackgroundColorToBlue() {
			titleSection.setBackgroundResource(R.drawable.highlight_select_title_section);
			title.setTextColor(Color.WHITE);
		}

		@SuppressLint("ResourceAsColor")
		public void changeTitleBackgroundColorToDefault() {
			title.setTextColor(R.color.black);
			title.setTypeface(null, Typeface.BOLD);
			titleSection.setBackgroundResource(R.drawable.textlines);
		}

		private void editButtonListener() {

			final LinearLayout currentDetailSection = detailSec.detailSection;
			final LinearLayout currentEditSection = editSec.editSection;
			editButton.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					hideDeleteButton();
					currentDetailSection.setVisibility(View.GONE);
					editModeTitle();
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
			content.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			// content.setBackgroundResource(R.drawable.textlines);
			content.setTextSize(16);
			content.setPadding(20, 10, 10, 10);
			content.setTextColor(Color.WHITE);

			// everyday section in display content section
			rdTime.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			// rdTime.setBackgroundResource(R.drawable.textlines);
			rdTime.setPadding(20, 0, 10, 10);
			rdTime.setTextSize(16);
			rdTime.setTextColor(Color.WHITE);

			setDetailPageContent();
			detailSection.addView(content);
			detailSection.addView(rdTime);

			detailSection.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			detailSection.setOrientation(LinearLayout.VERTICAL);
			detailSection.setVisibility(View.GONE);
			detailSection.setBackgroundResource(R.drawable.detail_section);

		}

		private void setDetailPageContent() {
			// everyday section in display content section
			content.setText(reminder.getDetail());
			if (reminder.isAlawys()) {
				rdTime.setText("Everyday");
			} else {
				final SimpleDateFormat nextTimeFormat = new SimpleDateFormat("HH:mm MM/dd/yyyy", Locale.ENGLISH);
				String dateStr = nextTimeFormat.format(reminder.getNextAlarmTime());
				rdTime.setText("Next Time: " + dateStr);
			}
		}

	}

	class EditSection {
		LinearLayout editSection;
		LinearLayout editTitleSection;
		DetailSecTextView titleText;
		EditText editTitle;
		LinearLayout editDetailSection;
		DetailSecTextView detailText;
		EditText editDetail;
		LinearLayout startDateSection;
		DetailSecTextView startDate;
		EditText editDate;
		LinearLayout startTimeSection;
		DetailSecTextView startTime;
		EditText editHours;
		TextView colon;
		EditText editMins;
		LinearLayout startDurationSection;
		DetailSecTextView duration;
		EditText editDuration;
		TextView dayUnit;
		CheckBox checkboxForEveryday;
		TextView everydayUnit;
		LinearLayout setRepeatSection;
		DetailSecTextView repeat;
		EditText setRepeatHours;
		TextView hourUnit;
		CheckBox checkboxTFHour;
		TextView tfhours;
		LinearLayout buttonSection;
		Button saveButton;
		Button cancelButton;
		EditText test;

		EditSection() {
			editSection = new LinearLayout(context);

			editTitleSection = new LinearLayout(context);
			titleText = new DetailSecTextView(context);
			editTitle = new EditText(context);
			editDetailSection = new LinearLayout(context);
			detailText = new DetailSecTextView(context);
			editDetail = new EditText(context);
			startDateSection = new LinearLayout(context);
			startDate = new DetailSecTextView(context);
			editDate = new EditText(context);
			startTimeSection = new LinearLayout(context);
			startTime = new DetailSecTextView(context);
			editHours = new EditText(context);
			colon = new TextView(context);
			editMins = new EditText(context);
			startDurationSection = new LinearLayout(context);
			duration = new DetailSecTextView(context);
			editDuration = new EditText(context);
			dayUnit = new TextView(context);
			checkboxForEveryday = new CheckBox(context);
			everydayUnit = new TextView(context);
			setRepeatSection = new LinearLayout(context);
			repeat = new DetailSecTextView(context);
			setRepeatHours = new EditText(context);
			hourUnit = new TextView(context);
			checkboxTFHour = new CheckBox(context);
			tfhours = new TextView(context);
			buttonSection = new LinearLayout(context);
			saveButton = new Button(context);
			cancelButton = new Button(context);
			test = new EditText(context);
			init();
		}

		private void setListeners() {
			checkboxListener();
			saveButtonListener();
			cancelButtonListenr();
		}

		private void cancelButtonListenr() {
			cancelButton.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					if (isNew) {
						reminderSection.removeView(titleSec.titleSection);
						reminderSection.removeView(detailSec.detailSection);
						reminderSection.removeView(editSec.editSection);
						mrcInstance.removeReminder(reminder.getId());
					} else {
						titleSec.showDetailModeTitle();
						detailSec.detailSection.setVisibility(View.VISIBLE);
						editSec.editSection.setVisibility(View.GONE);
					}
				}
			});
		}

		

		public String getHour(Date date) {
			final SimpleDateFormat nextTimeFormat = new SimpleDateFormat("HH", Locale.ENGLISH);
			String dateStr = nextTimeFormat.format(date);
			return dateStr;
		}
		
		public String getMin(Date date) {
			final SimpleDateFormat nextTimeFormat = new SimpleDateFormat("mm", Locale.ENGLISH);
			String dateStr = nextTimeFormat.format(date);
			return dateStr;
		}
		
		public String getDate(Date date) {
			final SimpleDateFormat nextTimeFormat = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);
			String dateStr = nextTimeFormat.format(date);
			return dateStr;
		}

		
		private void saveButtonListener() {
			final SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyyMMdd, HH:mm", Locale.ENGLISH);

			saveButton.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					hideDeleteButton();
					// check all edit text fields
					if (true) {
						reminder.setTitle(editTitle.getText().toString());
						reminder.setDetail(editDetail.getText().toString());

						String dateStr;
						dateStr = editDate.getText().toString() + ", " + editHours.getText().toString() + ":"
								+ editMins.getText().toString();

						try {
							Date date = inputDateFormat.parse(dateStr);
							reminder.setStartTime(date);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							Toast.makeText(callbackAct, "date or time format is incorrect", Toast.LENGTH_SHORT).show();
							e.printStackTrace();
						}

						if (!editDuration.getText().toString().equals("")) {
							int duration = Integer.valueOf(editDuration.getText().toString());
							reminder.setDuration(duration);
						}
						if (!setRepeatHours.getText().toString().equals("")) {
							int repeatHours = Integer.valueOf(setRepeatHours.getText().toString());
							reminder.setRepeat(repeatHours);
						}
						mrcInstance.activate(reminder.getId());

						detailSec.setDetailPageContent();
						titleSec.setTitleContent();
						detailSec.detailSection.setVisibility(View.VISIBLE);
						editSec.editSection.setVisibility(View.GONE);
						titleSec.showDetailModeTitle();
						isNew = false;
						if (reminder.isActive()) {
							titleSec.validButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_alarms_light, 0, 0, 0);
						} else {
							titleSec.validButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_alarms, 0, 0, 0);							
						}
					}
				}
			});
		}

		private void checkboxListener() {

			checkboxForEveryday.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
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

			checkboxTFHour.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
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

			editSection.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			editSection.setOrientation(LinearLayout.VERTICAL);
			editSection.setBackgroundResource(R.drawable.edit_reminder_section);
			editSection.setPadding(0, 10, 20, 10);

			// edit title section
			editTitleSection.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			editTitleSection.setOrientation(LinearLayout.HORIZONTAL);

			titleText.setText("Title");

			editTitle.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			editTitle.setText(reminder.getTitle());
			editTitleSection.addView(titleText);
			editTitleSection.addView(editTitle);

			// edit detail section
			editDetailSection.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			editDetailSection.setOrientation(LinearLayout.HORIZONTAL);

			detailText.setText("Detail");

			editDetail.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			editDetail.setText(reminder.getDetail());

			editDetailSection.addView(detailText);
			editDetailSection.addView(editDetail);

			// add start date section in edit content section
			startDateSection.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			startDateSection.setOrientation(LinearLayout.HORIZONTAL);

			startDate.setText("Start Date");

			editDate.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));			
			editDate.setText(getDate(reminder.getNextAlarmTime()));
			
			startDateSection.addView(startDate);
			startDateSection.addView(editDate);

			// add start date section in edit content section
			startTimeSection.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			startTimeSection.setOrientation(LinearLayout.HORIZONTAL);

			startTime.setText("Start Time");

			editHours.setLayoutParams(new LayoutParams(R.dimen.login_edittext_width, R.dimen.login_edittext_height));
			editHours.setText(getHour(reminder.getNextAlarmTime()));

			colon.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			colon.setText(":");
			colon.setTextSize(16);

			editMins.setLayoutParams(new LayoutParams(R.dimen.login_edittext_width, R.dimen.login_edittext_height));
			editMins.setText(getMin(reminder.getNextAlarmTime()));

			startTimeSection.addView(startTime);
			startTimeSection.addView(editHours);
			startTimeSection.addView(colon);
			startTimeSection.addView(editMins);

			// add reminder duration time section in edit content section

			startDurationSection
					.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			startDurationSection.setOrientation(LinearLayout.HORIZONTAL);

			duration.setText("Duration");

			editDuration.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			editDuration.setHint("");

			dayUnit.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			dayUnit.setText("days");
			dayUnit.setTypeface(null, Typeface.BOLD);
			// dayUnit.setBackgroundResource(R.drawable.textlines);
			dayUnit.setTextSize(16);

			checkboxForEveryday.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			checkboxForEveryday.setPadding(20, 0, 10, 0);
			checkboxForEveryday.setFocusable(false);

			everydayUnit.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			everydayUnit.setText("Everyday");
			everydayUnit.setTypeface(null, Typeface.BOLD);
			everydayUnit.setGravity(Gravity.RIGHT);
//			everydayUnit.setBackgroundResource(R.drawable.textlines);
			everydayUnit.setTextSize(16);

			startDurationSection.addView(duration);
			startDurationSection.addView(editDuration);
			startDurationSection.addView(dayUnit);
			startDurationSection.addView(checkboxForEveryday);
			startDurationSection.addView(everydayUnit);

			// add repeat section
			setRepeatSection.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			setRepeatSection.setOrientation(LinearLayout.HORIZONTAL);

			repeat.setText("Repeat");

			setRepeatHours.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			setRepeatHours.setHint("number");

			hourUnit.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			hourUnit.setText("hours");
			hourUnit.setTypeface(null, Typeface.BOLD);
			// hourUnit.setBackgroundResource(R.drawable.textlines);
			hourUnit.setTextSize(16);

			checkboxTFHour.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			checkboxTFHour.setPadding(20, 0, 10, 0);
			checkboxTFHour.setFocusable(false);

			tfhours.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			tfhours.setText("Once");
			tfhours.setTypeface(null, Typeface.BOLD);
			tfhours.setGravity(Gravity.RIGHT);
//			tfhours.setBackgroundResource(R.drawable.textlines);
			tfhours.setTextSize(16);

			setRepeatSection.addView(repeat);
			setRepeatSection.addView(setRepeatHours);
			setRepeatSection.addView(hourUnit);
			setRepeatSection.addView(checkboxTFHour);
			setRepeatSection.addView(tfhours);
			setRepeatSection.setPadding(0, 0, 0, 10);

			buttonSection.setLayoutParams(new LayoutParams(500, 70));
			buttonSection.setOrientation(LinearLayout.HORIZONTAL);
			buttonSection.setGravity(Gravity.RIGHT);
			buttonSection.setPadding(0, 0, 0, 10);

			saveButton = (Button) ((Activity) context).getLayoutInflater().inflate(
					R.layout.edit_reminder_section_button, null);
			saveButton.setText("Save");
			
			cancelButton = (Button) ((Activity) context).getLayoutInflater().inflate(
					R.layout.edit_reminder_section_button, null);
			cancelButton.setText("Cancel");
			
			TextView buttonSecBlankView = new TextView(context);
			buttonSecBlankView.setLayoutParams(new LayoutParams(40, 0));
			
			// add save button
			buttonSection.addView(saveButton);
			buttonSection.addView(buttonSecBlankView);
			buttonSection.addView(cancelButton);		
			
			// add all sections

			editSection.addView(editTitleSection);
			editSection.addView(editDetailSection);
			editSection.addView(startDateSection);
			editSection.addView(startTimeSection);
			editSection.addView(startDurationSection);
			editSection.addView(setRepeatSection);
			editSection.addView(buttonSection);
			editSection.setVisibility(View.GONE);
		}

	}

}
