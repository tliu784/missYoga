package com.android.recommendation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class EngineInputModel {

	private ArrayList<ActData> activities = new ArrayList<ActData>();
	private ArrayList<BPdata> bloodPressures = new ArrayList<BPdata>();
	private ArrayList<HRdata> heartBeats = new ArrayList<HRdata>();
	private ArrayList<SleepData> sleep = new ArrayList<SleepData>();
	private UserInfo userinfo;
	
		
	public ArrayList<ActData> getActivities() {
		return activities;
	}

	public void setActivities(ArrayList<ActData> activities) {
		this.activities = activities;
	}

	public ArrayList<BPdata> getBloodPressures() {
		return bloodPressures;
	}

	public void setBloodPressures(ArrayList<BPdata> bloodPressures) {
		this.bloodPressures = bloodPressures;
	}

	public ArrayList<HRdata> getHeartBeats() {
		return heartBeats;
	}

	public void setHeartBeats(ArrayList<HRdata> heartBeats) {
		this.heartBeats = heartBeats;
	}

	public ArrayList<SleepData> getSleep() {
		return sleep;
	}

	public void setSleep(ArrayList<SleepData> sleep) {
		this.sleep = sleep;
	}

	public UserInfo getUserinfo() {
		return userinfo;
	}

	public void setUserinfo(UserInfo userinfo) {
		this.userinfo = userinfo;
	}

	public static class ActData extends Base {
		int duration;

		public ActData(Date timestamp, int duration) {
			super(timestamp);
			this.duration = duration;
		}
	}

	public static class SleepData extends Base {
		int minutesAsleep;

		public SleepData(Date timestamp, int minutesAsleep) {
			super(timestamp);
			this.minutesAsleep = minutesAsleep;
		}
	}

	public static class HRdata extends Base {
		int count;

		public HRdata(Date timestamp, int count) {
			super(timestamp);
			this.count = count;
		}
	}

	public static class BPdata extends Base {
		int diastolic;
		int systolic;

		public BPdata(Date timestamp, int diastolic, int systolic) {
			super(timestamp);
			this.diastolic = diastolic;
			this.systolic = systolic;
		}
	}

	public static class WeightData extends Base {
		int value;

		public WeightData(Date timestamp, int value) {
			super(timestamp);
			this.value = value;
		}
	}
	
	public static class UserInfo{
		int age;
		boolean cardio;
		boolean diabetes;
		String gender;
		boolean hypertension;
		boolean insomnia;
		ArrayList<WeightData> weight = new ArrayList<WeightData>();
		
		public int getAge() {
			return age;
		}
		public void setAge(int age) {
			this.age = age;
		}
		public boolean isCardio() {
			return cardio;
		}
		public void setCardio(boolean cardio) {
			this.cardio = cardio;
		}
		public boolean isDiabetes() {
			return diabetes;
		}
		public void setDiabetes(boolean diabetes) {
			this.diabetes = diabetes;
		}
		public String getGender() {
			return gender;
		}
		public void setGender(String gender) {
			this.gender = gender;
		}
		public boolean isHypertension() {
			return hypertension;
		}
		public void setHypertension(boolean hypertension) {
			this.hypertension = hypertension;
		}
		public boolean isInsomnia() {
			return insomnia;
		}
		public void setInsomnia(boolean insomnia) {
			this.insomnia = insomnia;
		}
		public ArrayList<WeightData> getWeight() {
			return weight;
		}
		public void setWeight(ArrayList<WeightData> weight) {
			this.weight = weight;
		}

			
	}

	public static class Base {
		String date;

		Base(Date timestamp) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CANADA);
			this.date = sdf.format(timestamp);
		}
	}
}
