package com.android.entity;

import java.io.Serializable;
import java.util.Date;

public class DeviceDataModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4751568213230044764L;
	private Date timestamp;
	private int hr_count;
	private int bp_systolic;
	private int bp_diastolic;
	private int act_distance;
	private float act_duration;
	private int act_steps;
	private int sleep_efficiency;
	private int sleep_minAsleep;
	private int sleep_minAwake;
	private int sleep_countAwake;
	private int sleep_timeInBed;

	public DeviceDataModel() {
	}

	public DeviceDataModel(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	

}
