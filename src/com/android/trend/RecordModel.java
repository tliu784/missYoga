package com.android.trend;

import java.io.Serializable;
import java.util.Date;

import com.android.entity.RecomModel;

public class RecordModel implements Serializable{
	private boolean reminderItemExist;
	private boolean noteExist;
	private boolean recomExist;
	
	private String reminderTitle;
//	private String reminderDetail;
	private boolean ifMissedReminderItem;
	private RecomModel recommendation;
	private String personalNote; 
	private Date timestamp;
	
	
}
