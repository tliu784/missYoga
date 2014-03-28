package com.android.trend;

import java.io.Serializable;
import java.util.Date;

import com.android.entity.RecomModel;
import com.android.reminder.MedReminderModel;

public class RecordModel implements Comparable<RecordModel>, Serializable {

	private String content;
	private Date timeStamp;
	private recordType type;
	private boolean miss;

	public enum recordType implements Serializable {
		recommendation, note, reminder;
	}

	public RecordModel() {
		this.content = "There is a record example";
		this.timeStamp = new Date();
		this.type = recordType.recommendation;
		this.miss = true;
	}

	public RecordModel(recordType type, Date date, String content, boolean miss) {
		this.content = content;
		this.timeStamp = date;
		this.type = type;
		this.miss = miss;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public recordType getType() {
		return type;
	}

	public void setType(recordType type) {
		this.type = type;
	}

	@Override
	public int compareTo(RecordModel another) {
		return -(this.timeStamp.compareTo(another.timeStamp));
	}

}
