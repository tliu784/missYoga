package com.android.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.android.reminder.MedReminderModel;
import com.android.reminder.MedReminderModel.DurationUnit;

public class MedicineModel implements Serializable{

	public boolean isReminder() {
		return reminder;
	}

	public void setReminder(boolean reminder) {
		this.reminder = reminder;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 8256080968192093095L;
	private Date starttime=new Date();
	private String title="";
	private String description="";
	private HealthEffect effect=HealthEffect.OTHERS;
	private int repeat=1;
	private boolean reminder=false;
	private MedReminderModel.DurationUnit runit=DurationUnit.Day; 
	
	public enum HealthEffect {
		HR, BP, SLEEP, ACTIVITY,OTHERS
	}
	
	public MedicineModel(){}

	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}
	
	public Date getLastTime(){
		Date now = new Date();
		Date missedTime=starttime;
		while (missedTime.compareTo(now)<0){
			missedTime=MedReminderModel.addDuration(missedTime, repeat, runit);
		}
		missedTime=MedReminderModel.addDuration(missedTime, (0-repeat), runit);
		return missedTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public HealthEffect getEffect() {
		return effect;
	}

	public void setEffect(HealthEffect effect) {
		this.effect = effect;
	}

	public int getRepeat() {
		return repeat;
	}

	public void setRepeat(int repeat) {
		this.repeat = repeat;
	}

	public MedReminderModel.DurationUnit getRunit() {
		return runit;
	}

	public void setRunit(MedReminderModel.DurationUnit runit) {
		this.runit = runit;
	}
	
	public void setTestData(){
		starttime = new Date();
		title = "Aspirin";
		description = "good for you";
		effect = HealthEffect.ACTIVITY;
		repeat = 6;
		reminder= false;
		runit = DurationUnit.Hour; 
	}
	
	public String getMissedText(boolean isQuestion){
		String typename="";
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mma", Locale.CANADA);
		switch (this.effect){
		case ACTIVITY:
			break;
		case BP:
			typename="Blood Pressure";
			break;
		case HR:
			break;
		case OTHERS:
			break;
		case SLEEP:
			break;
		default:
			break;
		
		}
		String question="";
		question=		"Your "
						+typename
						+" is abnormal, did you take "
						+this.title
						+" at "
						+sdf.format(getLastTime())
						+" ?";
		if (!isQuestion){
			question=		"Your "
					+typename
					+" is abnormal, it might be because of you didn't take "
					+this.title
					+" at "
					+sdf.format(getLastTime())
					+".";
		}
		return question;
	}
	
	

}
