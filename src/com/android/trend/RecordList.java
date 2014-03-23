package com.android.trend;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import com.android.reminder.MedReminderModel;
import com.android.trend.RecordModel.recordType;

public class RecordList implements Serializable {

	private static final long serialVersionUID = -3655329975219497567L;
	private int recordCount = 0;
	private ArrayList<RecordModel> recordList = new ArrayList<RecordModel>();

	public RecordList() {
		recordList.add(new RecordModel());
	}

	public int getCount() {
		return recordCount;
	}

	public void addOneRecord(recordType type, Date date, String content) {
		recordList.add(new RecordModel(type, date, content));
	}

	public void incrementalCount() {
		recordCount++;
	}

	public ArrayList<RecordModel> getRecordList() {
		return recordList;
	}

}
