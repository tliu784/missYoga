package com.android.controlmodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import com.android.entity.MedReminderModel;

public class MedReminderEvents implements Serializable {

	private static final long serialVersionUID = -7670973146662933249L;
	private int count = 0;

	public enum DurationUnit implements Serializable {
		Day, Hour, Min, Sec;
	}

	private ArrayList<MedReminderModel> reminderList = new ArrayList<MedReminderModel>();

	public void addReminder(Date creationTime, String title, String detail,
			int duration, DurationUnit dunit, int repeat, DurationUnit runit) {
		count++;
		int id = count;
		MedReminderModel newReminder = new MedReminderModel(id, creationTime,
				title, detail, duration, dunit, repeat, runit);
		reminderList.add(newReminder);
		sortByNext();
	}

	public ArrayList<MedReminderModel> getReminderList() {
		return reminderList;
	}

	public void removeReminder(int id) {
		reminderList.remove(findbyid(id));
		sortByNext();
	}

	public MedReminderModel findbyid(int id) {
		for (MedReminderModel reminder : reminderList) {
			if (reminder.getId() == id) {
				return reminder;
			}
		}
		return null;
	}
	
	private void sortByNext(){
		Collections.sort(reminderList);
	}

	
}
