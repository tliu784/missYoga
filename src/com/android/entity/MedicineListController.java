package com.android.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import android.content.Context;

import com.android.service.FileOperation;

public class MedicineListController {

	private static final String FILENAME = "medList.obj";
	private static MedicineListController instance = new MedicineListController();
	private static boolean initialized = false;
	private MedicineList medicineList = new MedicineList();
	private Context context;

	protected MedicineListController() {

	}

	public void init(Context appcontext) {
		if (!initialized) {
			this.context = appcontext;
			load();
		}
		for (MedicineModel medicine : medicineList.getMedicineList()) {
			medicine.setNextTime();

			sortByNext();
		}
		initialized = true;

	}

	public Context getContext() {
		return context;
	}

	private void save() {
		FileOperation.save(medicineList, FILENAME, context);
	}

	private void load() {
		MedicineList storedEvents = (MedicineList) FileOperation.read(
				FILENAME, context);
		if (storedEvents != null) {
			medicineList = storedEvents;
		}
	}

	public static MedicineListController getInstance() {

		return instance;
	}

	public void addReminder(Date creationTime, String title, String detail,
			int duration, MedicineModel.DurationUnit dunit, int repeat,
			MedicineModel.DurationUnit runit) {
		medicineList.incrementalCount();
		int id = medicineList.getCount();
		MedicineModel newMedicine = new MedicineModel(id, creationTime,
				title, detail, duration, dunit, repeat, runit);
		medicineList.getMedicineList().add(newMedicine);
		sortByNext();
	}

	public void addReminder(MedicineModel reminder) {
		medicineList.incrementalCount();
		int id = medicineList.getCount();
		reminder.setId(id);
		medicineList.getMedicineList().add(reminder);
		sortByNext();
	}

	public ArrayList<MedicineModel> getReminderList() {
		return medicineList.getMedicineList();
	}

	public void removeMedicine(int id) {
		medicineList.getMedicineList().remove(findbyid(id));
		sortByNext();
	}

	public MedicineModel findbyid(int id) {
		for (MedicineModel medicine : medicineList.getMedicineList()) {
			if (medicine.getId() == id) {
				return medicine;
			}
		}
		return null;
	}

	private void sortByNext() {
		Collections.sort(medicineList.getMedicineList());
		save();
	}



}
