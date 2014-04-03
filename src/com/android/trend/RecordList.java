package com.android.trend;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import android.content.Context;
import com.android.reminder.MedReminderModel;
import com.android.reminder.MedReminderModel.DurationUnit;
import com.android.service.FileOperation;
import com.android.trend.RecordModel.recordType;

public class RecordList implements Serializable {

	private static final long serialVersionUID = -3655329975219497567L;
	private static RecordList instance = null;
	private static final String FILENAME = "recordList.obj";
	private Context context;

	private ArrayList<RecordModel> recordList = new ArrayList<RecordModel>();

	public void addtestRecord() {
		recordList.add(new RecordModel());
	}

	protected RecordList() {

	}
	
	public void init(Context context) {
		this.context = context;
		load();
	}

	private void save() {
		FileOperation.save(recordList, FILENAME, context);
		
	}

	private void load() {
		@SuppressWarnings("unchecked")
		ArrayList<RecordModel> storedEvents = (ArrayList<RecordModel>) FileOperation.read(FILENAME, context);
		if (storedEvents != null) {
			recordList = storedEvents;
		}
	}

	public static RecordList getInstance() {
		if (instance == null) {
			instance = new RecordList();
		}
		return instance;
	}

	public void sortByNext() {
		Collections.sort(recordList);
		save();
	}

	public void addOneRecord(recordType type, Date date, String content, String title,boolean miss) {
		recordList.add(new RecordModel(type, date, content, title,miss));
		save();
	}
	
	public void addOneRecord(RecordModel record) {
		recordList.add(record);
		sortByNext();
		save();
	}

	public int getIndexByDate(Date date){
		int count = 0;
		for(RecordModel record : recordList){
			if (record.getTimeStamp().equals(date))
				return count;
			count ++;
		}
		return -1;
	}
	
	public ArrayList<RecordModel> getRecordList() {
		return recordList;
	}

	public int[] getOneHourRecord(Date startTime) {
		int[] startEndLong = { 0, 0, 0 };
		int counter = 0;
		boolean started = false;

		Date afterOneHour = MedReminderModel.addDuration(startTime, 1, DurationUnit.Hour);
		for (RecordModel record : recordList) {
			if (record.getTimeStamp().compareTo(startTime) >= 0 && record.getTimeStamp().compareTo(afterOneHour) <= 0) {
				if (!started) {
					startEndLong[0] = counter;
					started = true;
				}
			} else if (record.getTimeStamp().compareTo(startTime) < 0) {
				if (!started)
					return startEndLong;
				else {
					startEndLong[1] = counter - 1;
					startEndLong[2] = startEndLong[1] - startEndLong[0]+1;
					return startEndLong;
				}
			}
			counter++;
		}
		if (started) {
			startEndLong[1] = counter - 1;
			startEndLong[2] = startEndLong[1] - startEndLong[0]+1;
			return startEndLong;
		} else {
			return startEndLong;
		}
	}

}
