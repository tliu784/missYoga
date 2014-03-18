package com.android.reminder;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import com.google.gson.Gson;

public class MedReminderModel implements Comparable<MedReminderModel>,
		Serializable {

	private static final long serialVersionUID = 1571465180592233361L;
	private int id;
	private Date startTime;
	private String title;
	private String detail;
	private int duration;
	private DurationUnit dunit;
	private int repeat;
	private DurationUnit runit;
	private Date nextAlarmTime;
	private boolean active = true;

	public enum DurationUnit implements Serializable {
		Day, Hour, Min, Sec;
	}

	public MedReminderModel(int id, Date startTime, String title,
			String detail, int duration, DurationUnit dunit, int repeat,
			DurationUnit runit) {
		this.id = id;
		this.startTime = startTime;
		this.title = title;
		this.detail = detail;
		this.duration = duration;
		this.dunit = dunit;
		this.repeat = repeat;
		this.runit = runit;
		this.nextAlarmTime = startTime;
		setNextTime();

	}

	public Date getEndTime() {
		return addDuration(startTime, duration, dunit);
	}

	public void setNextTime() {
		Date now = new Date();
		while (nextAlarmTime.compareTo(now) <= 0) {
			nextAlarmTime = addDuration(nextAlarmTime, repeat, runit);
		}
		autodeactivate();
	}

	private void autodeactivate() {
		if (duration > 0)
			if (nextAlarmTime.compareTo(getEndTime()) > 0)
				active = false;
	}
	
	public void setAlways(){
		duration=-1;
	}
	
	public boolean isAlawys(){
		return (duration<=0);
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
		setNextTime();
	}

	public void setActive(boolean isActive) {
		active = isActive;
	}

	public boolean isActive() {
		return active;
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

	public Date getNextAlarmTime() {
		return nextAlarmTime;
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

	@Override
	public int compareTo(MedReminderModel anotherReminder) {
		return this.nextAlarmTime.compareTo(anotherReminder.nextAlarmTime);
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