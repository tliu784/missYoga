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
	
	public Dialog onCreateDialog(final Context context, ChartPointModel chartPointData) {

		this.context = context;
		recordListInstance = RecordList.getInstance();
		recordListInstance.init(context);
		
		editSection = new EditSection(chartPointData);

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		
		String dataStr = "BPH : "+(int) chartPointData.getBph()+
				"  | BPL : "+(int) chartPointData.getBpl()+
				"  | HR : "+(int) chartPointData.getHr();
		
		builder.setView(editSection.getEditSection());
		builder.setTitle("Add new note").setMessage(dataStr).setIcon(R.drawable.ic_dialog_icon_about)
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
		TextView editDate;
		LinearLayout startTimeSection;
		DetailSecTextView startTime;
		TextView editHours;
		TextView colon;
		TextView editMins;
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

		EditSection(ChartPointModel chartPointData) {
			editSection = new LinearLayout(context);

			editTitleSection = new LinearLayout(context);
			titleText = new DetailSecTextView(context);
			editTitle = new EditText(context);
			editDetailSection = new LinearLayout(context);
			detailText = new DetailSecTextView(context);
			editDetail = new EditText(context);
			startDateSection = new LinearLayout(context);
			startDate = new DetailSecTextView(context);
			editDate = new TextView(context);
			startTimeSection = new LinearLayout(context);
			startTime = new DetailSecTextView(context);
			editHours = new TextView(context);
			colon = new TextView(context);
			editMins = new TextView(context);
			test = new EditText(context);
			init(chartPointData);
		}

		private void init(ChartPointModel chartPointData) {

			Date date =  chartPointData.getTimestamp();
			

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

			startDate.setText("Date: ");
			editDate.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			
			editDate.setText(getDate(date));
			editDate.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

			startDateSection.addView(startDate);
			startDateSection.addView(editDate);

			// add start date section in edit content section
//			startTimeSection.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
//			startTimeSection.setOrientation(LinearLayout.HORIZONTAL);
//
//			startTime.setText("Time: ");
//			startTime.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
//			
//			editHours.setText(getHour(date));
//			editHours.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
//			
//
//			colon.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
//			colon.setText(":");
//			
//			
//			editMins.setText(getMin(date));
//			editMins.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
//			
//
//			startTimeSection.addView(startTime);


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
		
		
		public String getDate(Date date) {
			final SimpleDateFormat nextTimeFormat = new SimpleDateFormat("yyyyMMdd HH:mm a", Locale.ENGLISH);
			String dateStr = nextTimeFormat.format(date);
			return dateStr;
		}

	}

}
