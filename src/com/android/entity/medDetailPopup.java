package com.android.entity;

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

public class medDetailPopup extends DialogFragment {

	private MedicineListController medicineList;
	private Context context;

	public Dialog onCreateDialog(Context context) {

		this.context = context;

		AlertDialog.Builder builder = new AlertDialog.Builder(context);

		builder.setTitle("Medicine detail").setIcon(R.drawable.ic_dialog_icon_about)
		// Set the action buttons
				.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

					}

				});
		String medTextList = new String();
		for (MedicineModel model : medicineList.getInstance().getMedicineList())
			medTextList = medTextList + model.getTitle() + ": " + model.getDescription() + "\n";

		builder.setMessage(medTextList);

		return builder.create();
	}
}