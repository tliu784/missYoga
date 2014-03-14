package com.android.reminder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import android.content.Context;

import com.android.service.FileOperation;

public class MedReminderController  {


	private static final String FILENAME = "medreminders.obj";
	private static MedReminderController instance = new MedReminderController();
	private static boolean initialized=false;
	private MedReminderList reminderList=new MedReminderList();
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
		for (MedReminderModel reminder:reminderList.getReminderList()){
			reminder.setNextTime();
			if (reminder.isActive()){
				alarmService.setAlarm(reminder);
			}
			sortByNext();
		}
		initialized=true;
		
	}
	
	

	public Context getContext() {
		return context;
	}

	private void save() {
		FileOperation.save(reminderList, FILENAME, context);
	}

	private void load() {
		MedReminderList storedEvents = (MedReminderList) FileOperation.read(FILENAME, context);
		if (storedEvents != null) {
			reminderList=storedEvents;
		}
	}

	public static MedReminderController getInstance() {

		return instance;
	}



	public void addReminder(Date creationTime, String title, String detail,
			int duration, MedReminderModel.DurationUnit dunit, int repeat, MedReminderModel.DurationUnit runit) {
		reminderList.incrementalCount();
		int id =reminderList.getCount();
		MedReminderModel newReminder = new MedReminderModel(id, creationTime,
				title, detail, duration, dunit, repeat, runit);
		reminderList.getReminderList().add(newReminder);
		sortByNext();
		alarmService.setAlarm(findbyid(newReminder.getId()));
	}

	public ArrayList<MedReminderModel> getReminderList() {
		return reminderList.getReminderList();
	}

	public void removeReminder(int id) {
		reminderList.getReminderList().remove(findbyid(id));
		sortByNext();
	}

	public MedReminderModel findbyid(int id) {
		for (MedReminderModel reminder : reminderList.getReminderList()) {
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

	public void activate(int reminderId){
		//to be implemented
	}
	
	public void deactivate(int reminderID){
		//to be implemented
	}
	
	public void postAlarm(int reminderId) {
		MedReminderModel reminder = findbyid(reminderId);
		reminder.setNextTime();
		sortByNext();
		
		
		if (reminder.isActive()) {
			alarmService.setAlarm(reminder);
		}
	}
	

}
