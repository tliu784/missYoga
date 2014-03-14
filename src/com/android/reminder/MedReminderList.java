package com.android.reminder;

import java.io.Serializable;
import java.util.ArrayList;

public class MedReminderList implements Serializable{
	
	private static final long serialVersionUID = -3655329975219497567L;
	private int count = 0;
	private ArrayList<MedReminderModel> reminderList = new ArrayList<MedReminderModel>();
	
	public int getCount() {
		return count;
	}
	
	public void incrementalCount() {
		count++;
	}
	public ArrayList<MedReminderModel> getReminderList() {
		return reminderList;
	}
	
	
}
