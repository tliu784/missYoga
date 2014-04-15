package com.android.entity;

import java.io.Serializable;
import java.util.Date;

import com.android.reminder.MedReminderModel;

public class MedicineModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8256080968192093095L;
	private Date starttime;
	private String title;
	private String description;
	private HealthEffect effect;
	private int repeat;
	private MedReminderModel.DurationUnit runit; 
	
	public enum HealthEffect {
		HR, BP, SLEEP, ACTIVITY
	}
	
	public MedicineModel(){}

	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
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
	
	
	
	

}
