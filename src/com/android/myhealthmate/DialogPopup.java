package com.android.myhealthmate;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.android.reminder.AlarmReceiver;
import com.android.reminder.MedReminderController;
import com.android.reminder.MedReminderModel;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;

public class DialogPopup extends DialogFragment {
	
	MedReminderModel reminder = null;
	// public static final String notificationState = "notification";
	public Dialog onCreateDialog(Context context) {
		//boolean fromNoti = savedInstanceState.getBoolean(AlarmReceiver.notificationState);
		//if (fromNoti == true) {
			// Where we track the selected items
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			reminder = updateReminderSection(context);
			// Set the dialog title
			builder.setTitle(reminder.getTitle())
					.setMessage(reminder.getDetail())
					.setIcon(R.drawable.ic_dialog_icon_about)
					// Set the action buttons
					.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int id) {
							Log.d("dialog", "click confirm");
						}
					}).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int id) {
							Log.d("dialog", "click cancel");
						}
					});

			return builder.create();
		}

	public MedReminderModel updateReminderSection(Context context) {
		MedReminderModel reminderItem = null;
		MedReminderController mrcInstance = MedReminderController.getInstance();
		mrcInstance.init(context);
		if (mrcInstance.getReminderList().size() > 0) {
			reminderItem = mrcInstance.getReminderList().get(0); // get the first
																// reminder
			if (!reminderItem.isActive())
				reminderItem = null;
			
			
		}

		return reminderItem;
	}
}
