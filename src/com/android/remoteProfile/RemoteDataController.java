package com.android.remoteProfile;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

import com.android.entity.AccountController;
import com.android.remoteProfile.RemoteRequestController.SendRequestResponseHandler;
import com.android.service.ResponseHandler;
import com.android.service.RestCallHandler;
import com.android.trend.ChartDataController;
import com.android.trend.RecordList;
import com.google.gson.Gson;

public class RemoteDataController {
	private static RemoteDataController instance = null;
	private RemoteRequestController rpc;
	private ChartDataController cdc;
	private RecordList rlc;
	private RemoteDataModel localUserData;	
	private ArrayList<RemoteDataModel> dataList = new ArrayList<RemoteDataModel>();
	boolean initialized = false;
	private Gson gson;
	
	protected RemoteDataController(){
	//put init() here after finish all data generation
	}

	public static RemoteDataController getInstance(){
		if (instance == null)
			instance = new RemoteDataController();
		return instance;
	}
	
	public void init(){
		if (!initialized){
			rpc= RemoteRequestController.getInstance();
			cdc= ChartDataController.getInstance();
			rlc = RecordList.getInstance();
			loadLocalUserData();
			gson = new Gson();
			initialized = true;
		}
		
	}
	
	private void loadLocalUserData(){
		RemoteDataModel data = new RemoteDataModel();
		AccountController acc = AccountController.getInstance();
		data.setOwnerEmail(acc.getAccount().getEmail());
		data.setOwnerName(acc.getAccount().getName());
		data.setHealthdata(cdc.getDataset());
		data.setEventdata(rlc.getRecordList());
		localUserData=data;
		dataList.add(localUserData);
	}
	
	public void upload() {
		
		String content = gson.toJson(localUserData);
		String url = ServerConstants.SERVER_URL + ServerConstants.UPLOAD_DATA_URL;
		UploadResponseHandler handler = new UploadResponseHandler();
		RestCallHandler upload = new RestCallHandler(handler, url, content);
		upload.handleResponse();
	}
	
	public RemoteDataModel getLocalUserData() {
		return localUserData;
	}
	public ArrayList<RemoteDataModel> getDataList() {
		return dataList;
	}
	
	class UploadResponseHandler implements ResponseHandler{

		@Override
		public void processResponse(String response) {
			ServerResponseModel serverResponse = gson.fromJson(response, ServerResponseModel.class);
			if (serverResponse.getType() == ServerResponseModel.ResponseType.UPLOAD)
				if (serverResponse.isSuccessful()){
					//upload success
					Log.d("upload","yeah");
				}
			
		}
		
	}
	
	public RemoteDataModel getDataModelByName(String name){
		for (RemoteDataModel userData: dataList){
			if(userData.getOwnerName().equals(name))
				return userData;
		}
		return null;
	}
	
	
	
	
	
}
