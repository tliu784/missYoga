package com.android.remoteProfile;

import java.util.ArrayList;

import android.content.Context;

import com.android.trend.ChartDataController;
import com.android.trend.RecordList;

public class RemoteDataController {
	private RemoteRequestController rpc;
	private ChartDataController cdc;
	private RecordList rlc;
	private RemoteDataModel localUserData;	
	private ArrayList<RemoteDataModel> dataList = new ArrayList<RemoteDataModel>();
	boolean initialized = false;
	private Context context;
	
	protected RemoteDataController(){
		
	}
	
	public void init(Context context){
		if (!initialized){
			this.context=context;
			rpc= RemoteRequestController.getInstance();
//			cdc=ChartDataController
		}
		initialized=true;
	}
	
	public RemoteDataModel getLocalUserData() {
		return localUserData;
	}
	public ArrayList<RemoteDataModel> getDataList() {
		return dataList;
	}
	
	
	
	
}
