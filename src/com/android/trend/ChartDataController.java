package com.android.trend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import android.util.Log;

import com.android.reminder.MedReminderModel;
import com.android.reminder.MedReminderModel.DurationUnit;
import com.android.trend.ChartHelper.GraphViewData;

public class ChartDataController {

	private final int hrFloor;
	private final int hrCeiling;
	private final int bplFloor;
	private final int bphFloor;
	private final int bplCeiling;
	private final int bphCeiling;
	private final int actFloor;
	private final int actCeiling;
	private final int sleepFloor;
	private final int sleepCeiling;
	private int displaySetLen = 24; // default 24
	private int currentDisplayStartIndex = 0;

	private ArrayList<ChartPointModel> dataset = new ArrayList<ChartPointModel>();
	private ArrayList<ChartPointModel> displayDataSet = new ArrayList<ChartPointModel>();

	public enum SeriesType {
		HR, BPL, BPH, ACT, SLEEP;
	}

	public ChartDataController(int hrFloor, int hrCeiling, int bplFloor, int bphFloor, int bplCeiling, int bphCeiling,
			int actFloor, int actCeiling, int sleepFloor, int sleepCeiling) {
		super();
		this.hrFloor = hrFloor;
		this.hrCeiling = hrCeiling;
		this.bplFloor = bplFloor;
		this.bphFloor = bphFloor;
		this.bplCeiling = bplCeiling;
		this.bphCeiling = bphCeiling;
		this.actFloor = actFloor;
		this.actCeiling = actCeiling;
		this.sleepFloor = sleepFloor;
		this.sleepCeiling = sleepCeiling;
	}

	public void setDisplaySetLen(int displaySetLen) {
		this.displaySetLen = displaySetLen;
	}

	public int getDisplaySetLen() {
		return displaySetLen;
	}

	public void sortDisplay() {
		Collections.sort(displayDataSet);
	}

	public void sortAll() {
		Collections.sort(dataset);
		Collections.sort(displayDataSet);
	}

	public ArrayList<ChartPointModel> getDisplayDataSet() {
		return displayDataSet;
	}

	public GraphViewData[] generateGraphData(SeriesType sType) {
		double[] originalData = new double[displayDataSet.size()];
		double[] adjData = new double[displayDataSet.size()];
		double floor = 0;
		double ceiling = 0;

		// set floor and ceiling
		switch (sType) {
		case ACT:
			floor = actFloor;
			ceiling = actCeiling;
			break;
		case BPH:
			floor = bphFloor;
			ceiling = bphCeiling;
			break;
		case BPL:
			floor = bplFloor;
			ceiling = bplCeiling;
			break;
		case HR:
			floor = hrFloor;
			ceiling = hrCeiling;
			break;
		case SLEEP:
			floor = sleepFloor;
			ceiling = sleepCeiling;
			break;
		default:
			break;
		}

		for (int i = 0; i < displayDataSet.size(); i++) {
			// populate originalData array
			originalData[i] = getValue(i, sType);
		}

		adjData = ChartHelper.scaleToRange(originalData, floor, ceiling);
		return ChartHelper.createGraphViewData(adjData);

	}

	public double getValue(int xValue, SeriesType sType) {
		if (xValue < 0 || xValue >= displayDataSet.size())
			return -1;
		ChartPointModel point = displayDataSet.get(xValue);
		double value = -1;

		switch (sType) {
		case HR:
			value = point.getHr();
			break;
		case BPH:
			value = point.getBph();
			break;
		case BPL:
			value = point.getBpl();
			break;
		case ACT:
			if (point.isSleep())
				value = 0d;
			else
				value = point.getAct();
			break;
		case SLEEP:
			if (point.isSleep())
				value = point.getSleep();
			else
				value = 0d;
			break;
		default:
			break;
		}
		return value;
	}

	private void createDisplaySet() {
		displayDataSet.clear();
		int endIndex=currentDisplayStartIndex+displaySetLen;
		if (endIndex>=dataset.size())
			endIndex=dataset.size();
		//sublist method endIndex is exclusive
		displayDataSet=new ArrayList<ChartPointModel>(dataset.subList(currentDisplayStartIndex, endIndex));
	}

	public void shiftDisplayData(int points) {
		int newStartIndex = currentDisplayStartIndex;
		newStartIndex = currentDisplayStartIndex - points;
		if (newStartIndex>dataset.size()-displaySetLen)
			newStartIndex=dataset.size()-displaySetLen;
		if (newStartIndex < 0)
			newStartIndex = 0;
		if (dataset.size()>=displaySetLen)
			displayDataSet= new ArrayList<ChartPointModel> ( dataset.subList(newStartIndex, newStartIndex+displaySetLen));
		else
			displayDataSet=dataset;
		currentDisplayStartIndex=newStartIndex;
	}
	
	public void shiftDisplayToEnd(){
		currentDisplayStartIndex=dataset.size()-displaySetLen;
		if (currentDisplayStartIndex<0)
			currentDisplayStartIndex=0;
		createDisplaySet();
	}

	public void createRandomData(int count) {
		dataset.clear();
		double hr;
		double bpl;
		double bph;
		double act;
		double sleep;
		boolean isSleep = false;
		Date beginningTime = MedReminderModel.addDuration(new Date(), (0 - count), DurationUnit.Hour);
		Date timestamp = beginningTime;
		for (int i = 0; i < count; i++) {
			// need to adjust time later
			hr = ChartHelper.getSingleRandomData(70, 140);
			bpl = ChartHelper.getSingleRandomData(40, 100);
			bph = ChartHelper.getSingleRandomData(70, 190);
			act = ChartHelper.getSingleRandomData(10, 200);
			sleep = ChartHelper.getSleepRandomData();
			if (i % (displaySetLen / 2) == 0) {
				isSleep = !isSleep;
			}
			dataset.add(new ChartPointModel(timestamp, hr, bpl, bph, act, sleep, isSleep));
			timestamp = MedReminderModel.addDuration(timestamp, 1, DurationUnit.Hour);
		}
				
		shiftDisplayToEnd();
	}

}
