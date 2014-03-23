package com.android.trend;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;

import com.android.reminder.MedReminderList;
import com.android.reminder.MedReminderModel;
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

	public void addOneRecord(recordType type, Date date, String content,boolean miss) {
		recordList.add(new RecordModel(type, date, content,miss));
		save();
	}

	public ArrayList<RecordModel> getRecordList() {
		return recordList;
	}

}
