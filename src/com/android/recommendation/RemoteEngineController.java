package com.android.recommendation;

import java.util.Date;

import android.util.Log;

import com.android.recommendation.EngineInputModel.ActData;
import com.android.recommendation.EngineInputModel.BPdata;
import com.android.recommendation.EngineInputModel.HRdata;
import com.android.recommendation.EngineInputModel.SleepData;
import com.android.recommendation.EngineInputModel.UserInfo;
import com.android.recommendation.EngineInputModel.WeightData;
import com.android.service.ResponseHandler;
import com.android.service.RestCallHandler;
import com.google.gson.Gson;

public class RemoteEngineController implements ResponseHandler {
//	private static final String url = "http://healthengineherokuappcom.apiary.io/";
	 private static final String url = "http://health-engine.herokuapp.com/";
	private Gson gson = new Gson();

	public RemoteEngineController() {

	}
	
	public EngineInputModel generateInput(){
		EngineInputModel input = new EngineInputModel();
		//testing data
		Date timestamp = new Date();
		UserInfo userinfo = new UserInfo();
		userinfo.setAge(45);
		userinfo.setGender("male");
		userinfo.setCardio(true);
		userinfo.setDiabetes(true);
		userinfo.setHypertension(true);
		userinfo.setInsomnia(true);
		
		for (int i=0; i<7; i++){
			ActData act = new ActData(timestamp, 40);
			SleepData sleep = new SleepData(timestamp,500);
			HRdata hr = new HRdata(timestamp, 80);
			BPdata bp = new BPdata(timestamp, 80, 135);
			WeightData wt = new WeightData(timestamp, 40);
			userinfo.getWeight().add(wt);
			input.getActivities().add(act);
			input.getBloodPressures().add(bp);
			input.getHeartBeats().add(hr);
			input.getSleep().add(sleep);
		}
		input.setUserinfo(userinfo);
		return input;
	}

	public void getRecommendation(EngineInputModel input) {
		String json = gson.toJson(input);
		RestCallHandler rest = new RestCallHandler(this, url, json);
		rest.handleResponse();
	}

	@Override
	public void processResponse(String response) {
		Log.d("response",response);

	}

}
