package com.android.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import android.content.Context;


import com.android.entity.MedicineModel.HealthEffect;
import com.android.service.FileOperation;

public class MedicineListController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1507349753825015045L;
	private static MedicineListController instance = null;
	private boolean initialized = false;
	private static final String FILENAME = "medicineList.obj";
	private Context context;

	private ArrayList<MedicineModel> medicineList = new ArrayList<MedicineModel>();

	public static MedicineListController getInstance() {
		if (instance == null) {
			instance = new MedicineListController();
		}
		return instance;
	}

	protected MedicineListController() {

	}

	public void init(Context context) {

		if (!initialized) {
			this.context = context;
			// addTestMedRecord();
			load();
			initialized = true;
		}

	}

	public void removeMedByName(String name) {
		Iterator<MedicineModel> iter = medicineList.iterator();

		while (iter.hasNext()) {
		    MedicineModel model = iter.next();

		    if (model.getTitle().equals(name))
				iter.remove();
		}
		
		
//		for (MedicineModel model : medicineList) {
//			if (model.getTitle().equals(name))
//				medicineList.remove(model);
//		}
		save();
	}

	public void save() {
		FileOperation.save(medicineList, FILENAME, context);

	}

	private void load() {
		@SuppressWarnings("unchecked")
		ArrayList<MedicineModel> storedEvents = (ArrayList<MedicineModel>) FileOperation.read(FILENAME, context);
		if (storedEvents != null) {
			medicineList = storedEvents;
		}
	}

	public ArrayList<MedicineModel> getMedicineList() {
		return medicineList;
	}

	public void addOneRecord(MedicineModel record) {
		medicineList.add(record);
		save();
	}

	public void setReminderByTitle(String title, boolean isReminder) {
		for (MedicineModel model : medicineList) {
			if (model.getTitle().equals(title))
				model.setReminder(isReminder);
		}
		save();
	}

	public MedicineModel existMedicine(HealthEffect effect) {
		for (MedicineModel medicine : medicineList) {
			if (medicine.getEffect().equals(effect)) {
				return medicine;
			}
		}
		return null;
	}

	// public void addTestMedRecord() {
	// MedicineModel record = new MedicineModel();
	// record.setTestData();
	// addOneRecord(record);
	// }

}
