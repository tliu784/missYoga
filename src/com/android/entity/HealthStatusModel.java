package com.android.entity;

import java.util.Date;

public class HealthStatusModel {
	private Date timestamp;
	private int hr_count;
	private int bp_systolic;
	private int bp_diastolic;
	private int act_calories;
	private int act_steps;
	private int sleep_minAwake;
	private int sleep_minLight;
	private int sleep_minDeep;

	public HealthStatusModel() {
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public int getHr_count() {
		return hr_count;
	}

	public void setHr_count(int hr_count) {
		this.hr_count = hr_count;
	}

	public int getBp_systolic() {
		return bp_systolic;
	}

	public void setBp_systolic(int bp_systolic) {
		this.bp_systolic = bp_systolic;
	}

	public int getBp_diastolic() {
		return bp_diastolic;
	}

	public void setBp_diastolic(int bp_diastolic) {
		this.bp_diastolic = bp_diastolic;
	}

	public int getAct_calories() {
		return act_calories;
	}

	public void setAct_calories(int act_calories) {
		this.act_calories = act_calories;
	}

	public int getAct_steps() {
		return act_steps;
	}

	public void setAct_steps(int act_steps) {
		this.act_steps = act_steps;
	}

	public int getSleep_minAwake() {
		return sleep_minAwake;
	}

	public void setSleep_minAwake(int sleep_minAwake) {
		this.sleep_minAwake = sleep_minAwake;
	}

	public int getSleep_minLight() {
		return sleep_minLight;
	}

	public void setSleep_minLight(int sleep_minLight) {
		this.sleep_minLight = sleep_minLight;
	}

	public int getSleep_minDeep() {
		return sleep_minDeep;
	}

	public void setSleep_minDeep(int sleep_minDeep) {
		this.sleep_minDeep = sleep_minDeep;
	}

}
