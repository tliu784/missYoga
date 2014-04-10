package com.android.myhealthmate;

import java.util.ArrayList;

import com.android.reminder.MedReminderController;
import com.android.reminder.MedicineModel;
import com.android.trend.RecordList;
import com.android.trend.RecordModel;
import com.android.trend.RecordModel.recordType;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;


public class ReminderDialogPopup extends DialogFragment {

	ArrayList<RecordModel> recordList = new ArrayList<RecordModel>();
	RecordList recordListInstance = RecordList.getInstance();

	MedicineModel reminder = null;

	public Dialog onCreateDialog(Context context, int id) {

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		reminder = updateReminderSection(context, id);
		// Set the dialog title
		recordListInstance.init(context);
		builder.setTitle(reminder.getTitle()).setMessage(reminder.getDetail()).setIcon(R.drawable.ic_dialog_icon_about)
		// Set the action buttons
				.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						addDoneRecord(reminder);
					}
				}).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						addMissRecord(reminder);
					}
				});

		return builder.create();
	}

	private void addDoneRecord(MedicineModel reminder) {
		recordListInstance.addOneRecord(recordType.Reminder, reminder.getNextAlarmTime(), reminder.getDetail(),reminder.getTitle(), false);
	}

	private void addMissRecord(MedicineModel reminder) {
		recordListInstance.addOneRecord(recordType.Reminder, reminder.getNextAlarmTime(), reminder.getDetail(), reminder.getTitle(), true);
	}

	public MedicineModel updateReminderSection(Context context, int id) {
		MedicineModel reminderItem = null;
		MedReminderController mrcInstance = MedReminderController.getInstance();
		mrcInstance.init(context);
		if (mrcInstance.getReminderList().size() > 0) {
			reminderItem = mrcInstance.findbyid(id); // get the reminder by id
			if (!reminderItem.isActive())
				reminderItem = null;

		}

		return reminderItem;
	}
}
