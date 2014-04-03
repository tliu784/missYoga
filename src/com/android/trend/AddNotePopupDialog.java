package com.android.trend;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.myhealthmate.R;
import com.android.myhealthmate.SleDetail;
import com.android.reminder.DetailSecTextView;
import com.android.trend.RecordModel.recordType;

public class AddNotePopupDialog extends DialogFragment {

	private RecordList recordListInstance;
	private Context context;
	private EditSection editSection;
	
	public Dialog onCreateDialog(final Context context) {

		this.context = context;
		recordListInstance = RecordList.getInstance();
		recordListInstance.init(context);
		
		editSection = new EditSection();

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		
		builder.setView(editSection.getEditSection());
		builder.setTitle("Add new note").setMessage("Add new message").setIcon(R.drawable.ic_dialog_icon_about)
		// Set the action buttons
				.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						RecordModel record = new RecordModel(recordType.Note, editSection.toDate(),editSection.getNoteContent(),editSection.getNoteTitle(), true);
						RecordViewSection recordView = new RecordViewSection(context, record.getType(),
								record.getTimeStamp(), record.getContent(),record.isMissed(),record.getTitle());
						recordListInstance.addOneRecord(record);
						if(recordListInstance.getIndexByDate(record.getTimeStamp()) >= 0)
						SleDetail.addHistorySection(recordView,recordListInstance.getIndexByDate(record.getTimeStamp()));
					}
				}).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						// addMissRecord(reminder);
					}
				});

		return builder.create();
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
		EditText test;
		
		public LinearLayout getEditSection(){
			return editSection;
		}
		
		public String getNoteContent(){
			return editDetail.getText().toString();
		}
		
		public String getNoteTitle(){
			return editTitle.getText().toString();
		}

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
			test = new EditText(context);
			init();
		}

		private void init() {

			Date date = new Date();
			

			editSection.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			editSection.setOrientation(LinearLayout.VERTICAL);
			editSection.setBackgroundResource(R.drawable.edit_reminder_section);
			editSection.setPadding(0, 10, 20, 10);

			// edit title section
			editTitleSection.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			editTitleSection.setOrientation(LinearLayout.HORIZONTAL);

			titleText.setText("Title");

			editTitle.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			editTitleSection.addView(titleText);
			editTitleSection.addView(editTitle);

			// edit detail section
			editDetailSection.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			editDetailSection.setOrientation(LinearLayout.HORIZONTAL);

			detailText.setText("Detail");

			editDetail.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			editDetailSection.addView(detailText);
			editDetailSection.addView(editDetail);

			// add start date section in edit content section
			startDateSection.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			startDateSection.setOrientation(LinearLayout.HORIZONTAL);

			startDate.setText("Start Date");

			editDate.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			editDate.setText(getDate(date));

			startDateSection.addView(startDate);
			startDateSection.addView(editDate);

			// add start date section in edit content section
			startTimeSection.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			startTimeSection.setOrientation(LinearLayout.HORIZONTAL);

			startTime.setText("Start Time");

			editHours.setLayoutParams(new LayoutParams(R.dimen.login_edittext_width, R.dimen.login_edittext_height));
			editHours.setText(getHour(date));

			colon.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			colon.setText(":");
			colon.setTextSize(16);

			editMins.setLayoutParams(new LayoutParams(R.dimen.login_edittext_width, R.dimen.login_edittext_height));
			editMins.setText(getMin(date));

			startTimeSection.addView(startTime);
			startTimeSection.addView(editHours);
			startTimeSection.addView(colon);
			startTimeSection.addView(editMins);

			// add all sections

			editSection.addView(editTitleSection);
			editSection.addView(editDetailSection);
			editSection.addView(startDateSection);
			editSection.addView(startTimeSection);
		}

		public Date toDate() {
			String dateStr;
			SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyyMMdd, HH:mm", Locale.ENGLISH);
			dateStr = editDate.getText().toString() + ", " + editHours.getText().toString() + ":"
					+ editMins.getText().toString();

			try {
				Date date = inputDateFormat.parse(dateStr);
				return date;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				Toast.makeText(context, "date or time format is incorrect", Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			}
			return null;
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

	}

}
