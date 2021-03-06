package com.android.trend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import android.content.Context;

import com.android.entity.HealthStatusModel;
import com.android.service.FileOperation;
import com.android.trend.ChartHelper.GraphViewData;

public class ChartDataController {

	private static ChartDataController instance = null;
	private Context context;
	private int hrFloor;
	private int hrCeiling;
	private int bplFloor;
	private int bphFloor;
	private int bplCeiling;
	private int bphCeiling;
	private int actFloor;
	private int actCeiling;
	private int sleepFloor;
	private int sleepCeiling;
	private int displaySetLen = 24; // default 24
	private int currentDisplayStartIndex = 0;
	private boolean initialized = false;

	private ArrayList<ChartPointModel> dataset = new ArrayList<ChartPointModel>();
	private ArrayList<ChartPointModel> displayDataSet = new ArrayList<ChartPointModel>();

	private static final String filename = "chartdataset.obj";

	public enum SeriesType {
		HR, BPL, BPH, ACT, SLEEP;
	}

	public ArrayList<ChartPointModel> getDataset() {
		return dataset;
	}

	public void setDataset(ArrayList<ChartPointModel> dataset) {
		this.dataset = dataset;
		shiftDisplayToEnd();
	}

	public HealthStatusModel getHealthStatusValue(int index) {
		float calperstep = 0.35f;
		HealthStatusModel result = new HealthStatusModel();
		ChartPointModel lastPoint = dataset.get(index);

		result.setBp_diastolic((int) lastPoint.getBpl());
		result.setBp_systolic((int) lastPoint.getBph());
		result.setHr_count((int) lastPoint.getHr());
		double totalSleepAwake=0d;
		double totalSleepLight=0d;
		double totalSleepDeep=0d;
		double totalAct=0d;
		if (lastPoint.isSleep()){
			int i=index;
			while (dataset.get(i).isSleep() && i>=0){
				int duration=30;
				if (i>0)
					duration=ChartHelper.calcMinBetween(dataset.get(i).getTimestamp(), dataset.get(i-1).getTimestamp());
				if (dataset.get(i).getSleep()==ChartPointModel.SLEEP_HIGH)
					totalSleepDeep+=duration;
				if (dataset.get(i).getSleep()==ChartPointModel.SLEEP_MED)
					totalSleepLight+=duration;
				if (dataset.get(i).getSleep()==ChartPointModel.SLEEP_LOW)
					totalSleepAwake+=duration;
				i--;
			}
			while (!dataset.get(i).isSleep() && i >=0){
				totalAct+=dataset.get(i).getAct();
				i--;
			}
		}else{
			int i=index;
			
			while (!dataset.get(i).isSleep() && i >=0){
				totalAct+=dataset.get(i).getAct();
				i--;
			}
			while (dataset.get(i).isSleep() && i>=0){
				int duration=30;
				if (i>0)
					duration=ChartHelper.calcMinBetween(dataset.get(i).getTimestamp(), dataset.get(i-1).getTimestamp());
				if (dataset.get(i).getSleep()==ChartPointModel.SLEEP_HIGH)
					totalSleepDeep+=duration;
				if (dataset.get(i).getSleep()==ChartPointModel.SLEEP_MED)
					totalSleepLight+=duration;
				if (dataset.get(i).getSleep()==ChartPointModel.SLEEP_LOW)
					totalSleepAwake+=duration;
				i--;
			}
		
		}
			
		// accumulated
		result.setAct_calories((int) (totalAct * calperstep));
		result.setAct_steps((int) totalAct);
		result.setSleep_minAwake((int) totalSleepAwake);
		result.setSleep_minLight((int) totalSleepLight);
		result.setSleep_minDeep((int) totalSleepDeep);
		return result;

	}

	public HealthStatusModel getLastValue() {
		return getHealthStatusValue(dataset.size() - 1);
	}

	@SuppressWarnings("unchecked")
	public void loadLocalUserData() {
		ArrayList<ChartPointModel> loadedData = (ArrayList<ChartPointModel>) FileOperation.read(filename, context);
		if (loadedData != null)
			dataset = loadedData;
		shiftDisplayToEnd();
	}

	public void save() {
		FileOperation.save(dataset, filename, context);
	}

	protected ChartDataController() {

	}

	public static ChartDataController getInstance() {
		if (instance == null)
			instance = new ChartDataController();
		return instance;
	}

	public void init(Context context, int hrFloor, int hrCeiling, int bplFloor, int bphFloor, int bplCeiling,
			int bphCeiling, int actFloor, int actCeiling, int sleepFloor, int sleepCeiling) {
		if (!initialized) {
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
			this.context = context;
			loadLocalUserData();
			initialized = true;
		}
	}

	public void setDisplaySetLen(int displaySetLen) {
		this.displaySetLen = displaySetLen;
	}

	public int getDisplaySetLen() {
		int pointCount = dataset.size();
		if (pointCount >= displaySetLen)
			pointCount = displaySetLen;
		return pointCount;
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
		int endIndex = currentDisplayStartIndex + displaySetLen;
		if (endIndex >= dataset.size())
			endIndex = dataset.size();
		// sublist method endIndex is exclusive
		displayDataSet = new ArrayList<ChartPointModel>(dataset.subList(currentDisplayStartIndex, endIndex));
	}

	public void shiftDisplayData(int points) {
		int newStartIndex = currentDisplayStartIndex;
		newStartIndex = currentDisplayStartIndex + points;
		if (newStartIndex > dataset.size() - displaySetLen)
			newStartIndex = dataset.size() - displaySetLen;
		if (newStartIndex < 0)
			newStartIndex = 0;
		if (dataset.size() >= displaySetLen)
			displayDataSet = new ArrayList<ChartPointModel>(dataset.subList(newStartIndex, newStartIndex
					+ displaySetLen));
		else
			displayDataSet = dataset;
		currentDisplayStartIndex = newStartIndex;
	}

	public int shiftDisplayData(Date timestamp, int currentX) {
		int newX = currentDisplayStartIndex;
		int nearestIndex = dataset.size() - 1;
		for (int i = 0; i < dataset.size(); i++) {
			if (dataset.get(i).getTimestamp().compareTo(timestamp) > 0) {
				nearestIndex = i - 1;
				break;
			}
		}
		int currentXindex = currentDisplayStartIndex + currentX;
		int points = nearestIndex - currentXindex;
		shiftDisplayData(points); // currentDisplayStartIndex has been updated
		newX = nearestIndex - currentDisplayStartIndex;
		return newX;
	}

	public void shiftDisplayToEnd() {
		currentDisplayStartIndex = dataset.size() - displaySetLen;
		if (currentDisplayStartIndex < 0)
			currentDisplayStartIndex = 0;
		createDisplaySet();
	}

	public int getSleepFloor() {
		return sleepFloor;
	}

	public int getSleepCeiling() {
		return sleepCeiling;
	}

}
