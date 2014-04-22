package com.android.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.entity.MedicineModel.HealthEffect;
import com.android.myhealthmate.Profile;
import com.android.myhealthmate.R;
import com.android.reminder.MedReminderModel.DurationUnit;
import com.android.trend.RecordList;

@SuppressLint("ValidFragment")
public class AddMedicineDialogPopup extends DialogFragment {
	private RecordList recordListInstance;
	private Context context;
	private Spinner dropdown;
	private TextView medName;
	private TextView medDes;
	private TextView hour;
	private TextView min;
	private TextView repeat;
	private RadioButton radioBtnSelected;
	private RadioGroup radioBtnGroup;
	private View layout;
	private MedicineListController medicineList;
	private Profile profileAct;

	public Dialog onCreateDialog(final Context context, final Profile profileAct) {
		this.context = context;
		this.profileAct = profileAct;

		AlertDialog.Builder builder = new AlertDialog.Builder(context);

		LayoutInflater inflater = profileAct.getLayoutInflater();

		// Inflate and set the layout for the dialog
		// Pass null as the parent view because its going in the dialog layout

		layout = inflater.inflate(R.layout.add_medicine_layout, null);
		builder.setView(layout);

		medName = (TextView) layout.findViewById(R.id.txt_med_name);
		medDes = (TextView) layout.findViewById(R.id.txt_med_des);
		hour = (TextView) layout.findViewById(R.id.txt_start_hour);
		min = (TextView) layout.findViewById(R.id.txt_start_min);
		repeat = (TextView) layout.findViewById(R.id.txt_med_repeat);

		radioBtnGroup = (RadioGroup) layout.findViewById(R.id.repeat_unit);

		medicineList = MedicineListController.getInstance();

		dropdown = (Spinner) layout.findViewById(R.id.effect_spinner);
		String[] items = new String[] { "Heart rate", "Blood pressure", "Activity", "Sleep" };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(profileAct, android.R.layout.simple_spinner_item, items);
		dropdown.setAdapter(adapter);

		builder.setTitle("Add Medicine").setIcon(R.drawable.ic_dialog_icon_about)
		// Set the action buttons
				.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						MedicineModel med = addMedicineModel();
						if (med!=null)
							profileAct.addViewInMedSec(med);
						else
							Toast.makeText(context, "Incorrect Time Format", Toast.LENGTH_SHORT).show();
					}
				}).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						// addMissRecord(reminder);
					}
				});

		return builder.create();
	}

	public MedicineModel addMedicineModel() {
		MedicineModel medModel = new MedicineModel();
		setEffect(medModel);
		setRepeatUnit(medModel);
		medModel.setTitle(medName.getText().toString());
		medModel.setDescription(medDes.getText().toString());
		medModel.setRepeat(Integer.parseInt(repeat.getText().toString()));
		String timetext=hour.getText().toString() + ":" + min.getText().toString();
		Date starttime=toDate(timetext);
		if (starttime!=null){
			medModel.setStarttime(starttime);
			medicineList.addOneRecord(medModel);
			return medModel;
		}
//		medModel.setStarttime(toDate(hour.getText().toString() + ":" + min.getText().toString()));

		return null;
	}

	public Date toDate(String dateStr) {
		Calendar calendar = new GregorianCalendar();
		SimpleDateFormat inputDateFormat;
		Date now = new Date();
		calendar.setTime(now);
		int year       = calendar.get(Calendar.YEAR);
		int month      = calendar.get(Calendar.MONTH); 
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		inputDateFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);

		try {
			Date date = inputDateFormat.parse(dateStr);
			calendar.setTime(date);
			calendar.set(year, month, dayOfMonth);
			return calendar.getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			Toast.makeText(context, "time format is incorrect", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
		return null;
	}

	public void setEffect(MedicineModel medModel) {

		if (dropdown.getSelectedItem().toString().equals("Heart rate"))
			medModel.setEffect(HealthEffect.HR);
		else if (dropdown.getSelectedItem().toString().equals("Blood pressure"))
			medModel.setEffect(HealthEffect.BP);
		else if (dropdown.getSelectedItem().toString().equals("Activity"))
			medModel.setEffect(HealthEffect.ACTIVITY);
		else if (dropdown.getSelectedItem().toString().equals("Sleep"))
			medModel.setEffect(HealthEffect.SLEEP);
		else
			medModel.setEffect(HealthEffect.OTHERS);
	}

	public void setRepeatUnit(MedicineModel medModel) {
		int selectedId = radioBtnGroup.getCheckedRadioButtonId();
		// find the radiobutton by returned id
		radioBtnSelected = (RadioButton) layout.findViewById(selectedId);

		if (radioBtnSelected.getText().toString().equals("hours"))
			medModel.setRunit(DurationUnit.Hour);
		else if (radioBtnSelected.getText().toString().equals("days"))
			medModel.setRunit(DurationUnit.Day);
		else
			medModel.setRunit(DurationUnit.Day);
	}
}
