package com.android.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class MedicineList implements Serializable{
	
	private static final long serialVersionUID = -3655329975219497567L;
	private int count = 0;
	private ArrayList<MedicineModel> medicineList = new ArrayList<MedicineModel>();
	
	public int getCount() {
		return count;
	}
	
	public void incrementalCount() {
		count++;
	}
	public ArrayList<MedicineModel> getMedicineList() {
		return medicineList;
	}
	
	
}
