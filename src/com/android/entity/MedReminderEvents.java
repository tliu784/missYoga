package com.android.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.google.gson.Gson;

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
	}

	public void removeReminder(int id) {
		reminderList.remove(findbyid(id));
	}

	public MedReminderModel findbyid(int id) {
		for (MedReminderModel reminder : reminderList) {
			if (reminder.id == id) {
				return reminder;
			}
		}
		return null;
	}

	public class MedReminderModel {
		int id;
		Date creationTime;
		String title;
		String detail;
		int duration;
		DurationUnit dunit;
		int repeat;
		DurationUnit runit;
		Date nextAlarmTime;

		private MedReminderModel(int id, Date creationTime, String title,
				String detail, int duration, DurationUnit dunit, int repeat,
				DurationUnit runit) {
			this.id = id;
			this.creationTime = creationTime;
			this.title = title;
			this.detail = detail;
			this.duration = duration;
			this.dunit = dunit;
			this.repeat = repeat;
			this.runit = runit;
			this.nextAlarmTime = addDuration(this.creationTime, repeat, runit);
		}

		void setNextTime() {
			nextAlarmTime = addDuration(nextAlarmTime, duration, dunit);
		}

		@Override
		public String toString() {
			return new Gson().toJson(this);
		}

		public String getTitle() {
			return title;
		}

		public int getId() {
			return id;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getDetail() {
			return detail;
		}

		public void setDetail(String detail) {
			this.detail = detail;
		}

		public int getDuration() {
			return duration;
		}

		public void setDuration(int duration) {
			this.duration = duration;
		}

		public DurationUnit getDunit() {
			return dunit;
		}

		public void setDunit(DurationUnit dunit) {
			this.dunit = dunit;
		}

		public int getRepeat() {
			return repeat;
		}

		public void setRepeat(int repeat) {
			this.repeat = repeat;
		}

		public DurationUnit getRunit() {
			return runit;
		}

		public void setRunit(DurationUnit runit) {
			this.runit = runit;
		}
		
		
	}

	private static Date addDuration(Date start, int duration, DurationUnit dunit) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(start);
		switch (dunit) {
		case Day:
			cal.add(Calendar.DATE, duration);
			break;
		case Hour:
			cal.add(Calendar.HOUR, duration);
			break;
		case Min:
			cal.add(Calendar.MINUTE, duration);
			break;
		case Sec:
			cal.add(Calendar.SECOND, duration);
			break;
		}

		return cal.getTime();
	}
}
