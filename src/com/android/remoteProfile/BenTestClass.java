package com.android.remoteProfile;

import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import com.android.entity.AccountController;
import com.android.myhealthmate.R;
import com.android.myhealthmate.Settings;
import com.android.reminder.MedReminderModel;
import com.android.reminder.MedReminderModel.DurationUnit;
import com.android.trend.ChartDataController;
import com.android.trend.RecordList;
import com.android.trend.RecordModel;
import com.android.trend.RecordModel.recordType;

public class BenTestClass {

	private RemoteRequestController userListController;
	private ArrayList<RemoteDataModel> userDataModelList = new ArrayList<RemoteDataModel>();
	private RemoteDataModel localUserData;
	public Context context;
	private RecordList recordListInstance;
	
	public BenTestClass(Settings settings){
		this.context = settings;
		userListController = new RemoteRequestController();
		userListController.init(settings);
		
		
		localUserData = generateLoacalData("ben@gmail.com");
		userDataModelList.add(localUserData);
		userDataModelList.add(generateRemoteData("terry@gmail.com"));
		userDataModelList.add(generateRemoteData("nicole@gmail.com"));
	}
	
	public RemoteDataModel findModelByEmail(String email){

		for(RemoteDataModel userData:userDataModelList )
			if(userData.getOwnerEmail().equals(email))
				return userData;
		return null;
	}
	
	public RemoteDataModel generateLoacalData(String email){
		recordListInstance = RecordList.getInstance();
		recordListInstance.init(this.context);
		
		RemoteDataModel data = new RemoteDataModel();
		//account
		AccountController acc=AccountController.getInstance();
		acc.setTestAccout();
		data.setOwnerEmail(email);
		//chart data
		ChartDataController chartData = getChartController(50);
		data.setHealthdata(chartData.getDataset());
		//event data
		data.setEventdata(recordListInstance.getRecordList());
		
		return data;
	}
	
	public RemoteDataModel generateRemoteData(String email){
		RemoteDataModel data = new RemoteDataModel();
		//account
		AccountController acc=AccountController.getInstance();
		acc.setTestAccout();
		data.setOwnerEmail(email);
		//chart data
		ChartDataController chartData = getChartController(50);
		data.setHealthdata(chartData.getDataset());
		//event data
		data.setEventdata(recordListGenerator(20));
		return data;		
	}
	
	private ChartDataController getChartController(int numberOfData){
		int hrFloor ;
		int hrCeiling ;
		int bplFloor ;
		int bphFloor ;
		int bplCeiling ;
		int bphCeiling ;
		int actFloor ;
		int actCeiling ;
		int sleepFloor ;
		int sleepCeiling ;
		hrFloor = context.getResources().getInteger(R.integer.hr_floor);
		hrCeiling = context.getResources().getInteger(R.integer.hr_ceiling);
		bplFloor = context.getResources().getInteger(R.integer.bpl_floor);
		bplCeiling = context.getResources().getInteger(R.integer.bpl_ceiling);
		bphFloor = context.getResources().getInteger(R.integer.bph_floor);
		bphCeiling = context.getResources().getInteger(R.integer.bph_ceiling);
		actFloor = context.getResources().getInteger(R.integer.act_floor);
		actCeiling = context.getResources().getInteger(R.integer.act_ceiling);
		sleepFloor = context.getResources().getInteger(R.integer.sleep_floor);
		sleepCeiling = context.getResources().getInteger(R.integer.sleep_ceiling);
		
		
		ChartDataController chartData = new ChartDataController(context,hrFloor, hrCeiling, bplFloor, bphFloor, bplCeiling, bphCeiling, actFloor, actCeiling, sleepFloor, sleepCeiling);
		chartData.createRandomData(numberOfData);
		return chartData;
	}
	
	private static ArrayList<RecordModel> recordListGenerator(  int numOfData) {
		
		ArrayList<RecordModel> recordList = new ArrayList<RecordModel>();
		Date date = new Date();

		for (int i = 0; i < numOfData; i++) {
			recordType type = null;
			double x = Math.random();
			if (x < 0.3) {
				type = recordType.Note;
			} else if (x < 0.6) {
				type = recordType.Recommendation;
			} else {
				type = recordType.Reminder;
			}

			RecordModel record = new RecordModel(type, date, "this is history record" + Integer.toString(i), "Reocrd",
					true);

			date = MedReminderModel.addDuration(date, -20, DurationUnit.Min);
			if (Math.random() > 0.7)
				recordList.add(record);
		}
		return recordList;
	}
	
	public RemoteRequestController getUserListController() {
		return userListController;
	}

	public void setUserListController(RemoteRequestController userListController) {
		this.userListController = userListController;
	}

	public ArrayList<RemoteDataModel> getUserDataModelList() {
		return userDataModelList;
	}

	public void setUserDataModelList(ArrayList<RemoteDataModel> userDataModelList) {
		this.userDataModelList = userDataModelList;
	}
	

}
