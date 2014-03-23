package com.android.trend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

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

	private ArrayList<ChartPointModel> dataset = new ArrayList<ChartPointModel>();
	private ArrayList<ChartPointModel> displayDataSet = new ArrayList<ChartPointModel>();

	public enum SeriesType {
		HR, BPL, BPH, ACT, SLEEP;
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

	public void createRandomDisplaySet() {
		displayDataSet.clear();
		int len = 24;
		Date timestamp = new Date();
		double hr;
		double bpl;
		double bph;
		double act;
		double sleep;
		boolean isSleep;
		for (int i = 0; i < len; i++) {
			// need to adjust time later
			hr = ChartHelper.getSingleRandomData(70, 140);
			bpl = ChartHelper.getSingleRandomData(40, 100);
			bph = ChartHelper.getSingleRandomData(70, 190);
			act = ChartHelper.getSingleRandomData(10, 200);
			sleep = ChartHelper.getSleepRandomData();
			if (i < len / 2)
				isSleep = false;
			else
				isSleep = true;
			displayDataSet.add(new ChartPointModel(timestamp, hr, bpl, bph, act, sleep, isSleep));
		}
	}

}
