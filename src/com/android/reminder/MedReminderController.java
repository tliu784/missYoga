package com.android.reminder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import android.content.Context;
import android.util.Log;

import com.android.service.FileOperation;

public class MedReminderController {

	private static final String FILENAME = "medreminders.obj";
	private static MedReminderController instance = new MedReminderController();
	private static boolean initialized = false;
	private MedicineList reminderList = new MedicineList();
	private Context context;
	private AlarmService alarmService;

	protected MedReminderController() {

	}

	public void init(Context appcontext) {
		if (!initialized) {
			this.context = appcontext;
			alarmService = new AlarmService(context);
			load();
		}
		for (MedicineModel reminder : reminderList.getReminderList()) {
			reminder.setNextTime();
			if (reminder.isActive()) {
				alarmService.setAlarm(reminder);
			}
			sortByNext();
		}
		initialized = true;

	}

	public Context getContext() {
		return context;
	}

	private void save() {
		FileOperation.save(reminderList, FILENAME, context);
	}

	private void load() {
		MedicineList storedEvents = (MedicineList) FileOperation.read(
				FILENAME, context);
		if (storedEvents != null) {
			reminderList = storedEvents;
		}
	}

	public static MedReminderController getInstance() {

		return instance;
	}

	public void addReminder(Date creationTime, String title, String detail,
			int duration, MedicineModel.DurationUnit dunit, int repeat,
			MedicineModel.DurationUnit runit) {
		reminderList.incrementalCount();
		int id = reminderList.getCount();
		MedicineModel newReminder = new MedicineModel(id, creationTime,
				title, detail, duration, dunit, repeat, runit);
		reminderList.getReminderList().add(newReminder);
		sortByNext();
		alarmService.setAlarm(findbyid(newReminder.getId()));
	}

	public void addReminder(MedicineModel reminder) {
		reminderList.incrementalCount();
		int id = reminderList.getCount();
		reminder.setId(id);
		reminderList.getReminderList().add(reminder);
		sortByNext();
		if (reminder.isActive())
			alarmService.setAlarm(findbyid(reminder.getId()));
	}

	public ArrayList<MedicineModel> getReminderList() {
		return reminderList.getReminderList();
	}

	public void removeReminder(int id) {
		deactivate(id);
		reminderList.getReminderList().remove(findbyid(id));
		sortByNext();
	}

	public MedicineModel findbyid(int id) {
		for (MedicineModel reminder : reminderList.getReminderList()) {
			if (reminder.getId() == id) {
				return reminder;
			}
		}
		return null;
	}

	private void sortByNext() {
		Collections.sort(reminderList.getReminderList());
		save();
	}

	// must call this after update an existing reminder
	public void activate(int reminderId) {
		MedicineModel reminder = findbyid(reminderId);
		Log.d("id", Integer.toString(reminderId));
		if (reminder != null) {
			reminder.setActive(true);
			reminder.setNextTime();
			if (reminder.isActive()) {
				alarmService.setAlarm(reminder);
			}
		}
		sortByNext();
	}

	public void deactivate(int reminderId) {
		MedicineModel reminder = findbyid(reminderId);
		reminder.setActive(false);
		alarmService.cancelAlarm(reminder);
		sortByNext();
	}

	public void postAlarm(int reminderId) {
		MedicineModel reminder = findbyid(reminderId);
		reminder.setNextTime();
		sortByNext();

		if (reminder.isActive()) {
			alarmService.setAlarm(reminder);
		}
	}

}
