package com.android.trend;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ChartHelper {
	
	public int[] scaleToRange(int[] data, int floor, int ceiling){
		int[] result=new int[data.length];
		List list=Arrays.asList(data);
		int max=Collections.max(list);
		int min=Collections.min(list);
		int dataRange=max-min;
		int resultRange=ceiling-floor;
		if (resultRange!=0){
			for (int i=0;i<data.length; i++){
				result[i]=floor+(data[i]-min)/dataRange*resultRange;
			}
		}
		
		return result;
	}
}
