package com.android.trend;

public class ChartHelper {
	
	public static double[] scaleToRange(double[] data, double floor, double ceiling){
		double[] result=new double[data.length];
		double max=getMax(data);
		double min=getMin(data);
		double dataRange=max-min;
		double resultRange=ceiling-floor;
		if (resultRange!=0){
			for (int i=0;i<data.length; i++){
				result[i]=floor+(data[i]-min)/dataRange*resultRange;
			}
		}
		return result;
	}
	
	private static double getMax(double[] data){
		double result = -1;
		if (data.length>0){
			result = data[0];
			for (int i=0; i<data.length; i++){
				if (data[i]>result){
					result=data[i];
				}
			}
		}
		return result;
	}
	
	private static double getMin(double[] data){
		double result = -1;
		if (data.length>0){
			result = data[0];
			for (int i=0; i<data.length; i++){
				if (data[i]<result){
					result=data[i];
				}
			}
		}
		return result;
	}
	
	
}
