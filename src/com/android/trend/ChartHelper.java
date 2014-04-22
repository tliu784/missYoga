package com.android.trend;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.android.recommendation.RecommendationController;
import com.android.reminder.MedReminderModel;
import com.android.reminder.MedReminderModel.DurationUnit;
import com.android.trend.RecordModel.recordType;
import com.jjoe64.graphview.GraphViewDataInterface;

public class ChartHelper {

	public static ArrayList<RecordModel> recordListGenerator(ArrayList<ChartPointModel> dataset) {

		ArrayList<RecordModel> recordList = new ArrayList<RecordModel>();

		String[] bptips = { "Dark chocolate may help lower blood pressure.",
				"Drink tea instead of coffee may help lower blood pressure.",
				"Flaxseeds may help lower blood pressure.",
				"Reducing sodium in your diet may help lower blood pressure.",
				"Limiting the amount of alcohol you drink may help lower blood pressure.",
				"Avoiding tobacco products and secondhand smoke may help lower blood pressure." };
		String[] hrtips = { "Do aerobic exercise regularly on a gradually increased basis.",
				"Reduce stress where possible.", "Practise deep breath regularly." };
		for (ChartPointModel point : dataset) {
			boolean reasonableHighAct = false;
			if (!point.isSleep()) {
				if (Math.random() > 0.9) {
					// add note
					if (point.isHighAct()) {
						String noteText = "Hit the gym!";

						RecordModel record = new RecordModel(recordType.Recommendation, point.getTimestamp(), noteText,
								"Note", false);
						reasonableHighAct = true;
						recordList.add(record);
					}

					if (point.isLowAct()) {
						String noteText = "Do some readings";

						RecordModel record = new RecordModel(recordType.Note, point.getTimestamp(), noteText, "Note",
								false);
						recordList.add(record);
					}

				}

				if (!reasonableHighAct) {
					if (point.isHighBP() && Math.random() > 0.9) {
						// add high bp
						int level = (int) Math.round(getSingleRandomData(1, 5));
						String title = RecommendationController.getInstance().toSeverityLevel(level);
						String recomText = "Your blood pressure is high. ";
						recomText += getRandomString(bptips);
						RecordModel record = new RecordModel(recordType.Recommendation, point.getTimestamp(),
								recomText, title, false);
						recordList.add(record);
					}
					if (point.isHighHR() && Math.random() > 0.9) {
						// add high hr
						int level = (int) Math.round(getSingleRandomData(1, 5));
						String title = RecommendationController.getInstance().toSeverityLevel(level);
						String recomText = "Your heartrate is high. ";
						recomText += getRandomString(hrtips);
						RecordModel record = new RecordModel(recordType.Recommendation, point.getTimestamp(),
								recomText, title, false);
						recordList.add(record);
					}
				}
			}
		}

		return recordList;
	}

	private static String getRandomString(String[] list) {
		int index = (int) Math.round(getSingleRandomData(0, list.length - 1));
		return list[index];
	}

	public static Date toPreviousWholeHour(Date d) {
		Calendar c = new GregorianCalendar();
		c.setTime(d);
		// if (c.get(Calendar.MINUTE) >= 30)
		// c.add(Calendar.HOUR, 1);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return c.getTime();
	}

	public static int calcMinBetween(Date laterDate, Date earlierDate) {
		if (earlierDate == null)
			return 30;

		return (int) ((laterDate.getTime() / 60000) - (earlierDate.getTime() / 60000));
	}

	public static ArrayList<ChartPointModel> createRandomData(int count) {
		ArrayList<ChartPointModel> dataset = new ArrayList<ChartPointModel>();
		double hr;
		double bpl;
		double bph;
		double act;
		double sleep;
		boolean isSleep = false;
		Date beginningTime = MedReminderModel.addDuration(new Date(), (1 - count), DurationUnit.Hour);
		Date timestamp = ChartHelper.toPreviousWholeHour(beginningTime);
		for (int i = 0; i < count; i++) {
			// need to adjust time later
			hr = ChartHelper.getSingleRandomData(70, 140);
			bpl = ChartHelper.getSingleRandomData(40, 100);
			bph = ChartHelper.getSingleRandomData(70, 190);
			act = ChartHelper.getSingleRandomData(10, 200);
			sleep = ChartHelper.getSleepRandomData();
			if (i % (24 / 2) == 0) {
				isSleep = !isSleep;
			}
			dataset.add(new ChartPointModel(timestamp, hr, bpl, bph, act, sleep, isSleep));
			timestamp = MedReminderModel.addDuration(timestamp, 1, DurationUnit.Hour);
		}
		return dataset;

	}

	public static ArrayList<ChartPointModel> createReasonableRandomData(int days) {
		ArrayList<ChartPointModel> dataset = new ArrayList<ChartPointModel>();
		double hr;
		double bpl;
		double bph;
		double act;
		double sleep;
		boolean isSleep = false;
		Calendar calendar = new GregorianCalendar();
		Date beginningTime = MedReminderModel.addDuration(new Date(), (1 - days), DurationUnit.Day);
		Date timestamp = ChartHelper.toPreviousWholeHour(beginningTime);
		Date now = new Date();
		while (timestamp.compareTo(now) < 0) {
			// need to adjust time later

			calendar.setTime(timestamp);
			int hourofday = calendar.get(Calendar.HOUR_OF_DAY);
			if (hourofday >= 1 && hourofday < 6)
				isSleep = true;
			else
				isSleep = false;
			double sleepmarkdown = 0.8;
			double raw = Math.random();
			if (isSleep) {
				raw = raw * sleepmarkdown;
			}
			hr = ChartHelper.getSingleRandomData(65, 140, raw);
			bpl = ChartHelper.getSingleRandomData(40, 100, raw);
			bph = ChartHelper.getSingleRandomData(70, 190, raw);
			while (bph <= bpl) {
				bph = ChartHelper.getSingleRandomData(70, 190, raw);
			}
			act = ChartHelper.getSingleRandomData(10, 500, raw);
			sleep = ChartHelper.getSleepRandomData();

			dataset.add(new ChartPointModel(timestamp, hr, bpl, bph, act, sleep, isSleep));
			timestamp = MedReminderModel.addDuration(timestamp, 1, DurationUnit.Hour);
		}
		return dataset;
	}

	
	public static GraphViewData[] createGraphViewData(double[] data) {
		// implement a simple x value
		GraphViewData[] graphData = new GraphViewData[data.length];
		for (int i = 0; i < data.length; i++) {
			graphData[i] = new GraphViewData((double) i, data[i]);
		}
		return graphData;
	}

	public static double getSingleRandomData(double min, double max) {
		double raw = Math.random();
		double result = min + raw * (max - min);
		return result;
	}

	private static double getSingleRandomData(double min, double max, double raw) {
		double adj = (Math.random() - 0.5);
		double result = min + raw * (max - min);
		result = result + result * adj;
		if (result > max)
			result = max;
		if (result < min)
			result = min;
		return result;
	}

	public static double getSleepRandomData() {
		double value = 0;
		if (Math.random() <= 0.33) {
			value = ChartPointModel.SLEEP_LOW; // adjust here for better fake
												// chart
		} else {
			if (Math.random() > 0.66) {
				value = ChartPointModel.SLEEP_HIGH;
			} else {
				value = ChartPointModel.SLEEP_MED;
			}
		}
		return value;

	}

	public static GraphViewData[] createGraphViewData(double[] data, int minX) {
		// implement a simple x value
		GraphViewData[] graphData = new GraphViewData[data.length];
		for (int i = 0; i < data.length; i++) {
			graphData[i] = new GraphViewData((double) (i + minX), data[i]);
		}
		return graphData;
	}

	public static GraphViewData[] generateRandomData(int len, double floor, double ceiling) {
		double[] y = new double[len];
		for (int i = 0; i < len; i++) {
			y[i] = Math.random();
		}
		double[] adjY = scaleToRange(y, floor, ceiling);
		return createGraphViewData(adjY);
	}

	public static GraphViewData[] generateSleepData(int len, int startZero, int lenZero, double floor, double ceiling) {
		double[] y = new double[len];
		for (int i = 0; i < len; i++) {
			if (i >= startZero && i < lenZero + startZero)
				y[i] = 0;
			else if (Math.random() <= 0.33) {
				y[i] = 0.1; // adjust here for better fake chart
			} else {
				if (Math.random() > 0.66) {
					y[i] = 0.7;
				} else {
					y[i] = 0.4;
				}
			}

		}
		double[] adjY = scaleToRange(y, floor, ceiling);
		return createGraphViewData(adjY);
	}

	public static double[] scaleToRange(double[] data, double floor, double ceiling) {
		double[] result = new double[data.length];
		double max = getMax(data);
		double min = getMin(data);
		double dataRange = max - min;
		double resultRange = ceiling - floor;
		if (resultRange != 0) {
			for (int i = 0; i < data.length; i++) {
				result[i] = floor + (data[i] - min) / dataRange * resultRange;
			}
		}
		return result;
	}

	private static double getMax(double[] data) {
		double result = -1;
		if (data.length > 0) {
			result = data[0];
			for (int i = 0; i < data.length; i++) {
				if (data[i] > result) {
					result = data[i];
				}
			}
		}
		return result;
	}

	private static double getMin(double[] data) {
		double result = -1;
		if (data.length > 0) {
			result = data[0];
			for (int i = 0; i < data.length; i++) {
				if (data[i] < result) {
					result = data[i];
				}
			}
		}
		return result;
	}

	public static class GraphViewData implements GraphViewDataInterface {
		private double x, y;

		public GraphViewData(double x, double y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public double getX() {
			return this.x;
		}

		@Override
		public double getY() {
			return this.y;
		}
	}

}
