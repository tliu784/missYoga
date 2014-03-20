package com.android.trend;

import com.jjoe64.graphview.GraphViewDataInterface;

public class ChartHelper {

	public static GraphViewData[] createGraphViewData(double[] data) {
		// implement a simple x value
		GraphViewData[] graphData = new GraphViewData[data.length];
		for (int i = 0; i < data.length; i++) {
			graphData[i] = new GraphViewData((double) i, data[i]);
		}
		return graphData;
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

	public static GraphViewData[] generateRandomDataWithZero(int len, int startZero, int lenZero, double floor,
			double ceiling) {
		double[] y = new double[len];
		for (int i = 0; i < len; i++) {
			if (i >= startZero && i < lenZero + startZero)
				y[i] = 0;
			else
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
			else 
			if (Math.random() <= 0.33) {
				y[i] = 0.1; //adjust here for better fake chart
			} else {
				if (Math.random()>0.66){
					y[i]=0.7;
				}else{
					y[i]=0.4;
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
