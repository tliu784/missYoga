package com.android.trend;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ChartHelper {
	
	public float[] scaleToRange(float[] data, float floor, float ceiling){
		float[] result=new float[data.length];
		List list=Arrays.asList(data);
//		int max=Collections.max(list);
//		int min=Collections.min(list);
//		int dataRange=max-min;
		float resultRange=ceiling-floor;
		if (resultRange!=0){
			for (int i=0;i<data.length; i++){
//				result[i]=floor+(data[i]-min)/dataRange*resultRange;
			}
		}
		
		return result;
	}
	
	private float getMax(float[] data){
		float result = 0;
		return result;
		
	}
	
	private float getMin(float[] data){
		float result = 0;
		return result;
	}
}
