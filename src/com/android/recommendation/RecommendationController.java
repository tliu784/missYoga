package com.android.recommendation;

import java.util.ArrayList;
import java.util.Date;

import android.util.Log;

import com.android.entity.AccountController;
import com.android.entity.HealthStatusModel;
import com.android.recommendation.EngineInputModel.ActData;
import com.android.recommendation.EngineInputModel.BPdata;
import com.android.recommendation.EngineInputModel.HRdata;
import com.android.recommendation.EngineInputModel.SleepData;
import com.android.recommendation.EngineInputModel.UserInfo;
import com.android.recommendation.EngineInputModel.WeightData;
import com.android.reminder.MedReminderModel;
import com.android.trend.ChartDataController;
import com.android.trend.ChartPointModel;
import com.google.gson.Gson;

public class RecommendationController {

	private UserInfo userinfo;
	private ArrayList<ChartPointModel> existingChartPoints;
	private ArrayList<ChartPointModel> newData;
	private int newDataIndex = 0;
	private static RecommendationController instance=null;

	public static RecommendationController getInstance(){
		if (instance==null)
			instance=new RecommendationController();
		return instance;
	}
	
	private RecommendationController() {
		init();
	}

	private void init() {
		userinfo = AccountController.getInstance().getAccount().getUserInfo();
	}

	public void refresh() {
		/*
		 * 
		 * 1. retrieve testing data, advance counter 2. display data on main
		 * page 3. add point to chart page 4. create json and send to remote
		 * engine 5. process response, create recom models 6. display recom in
		 * widget 7. display recom in app 8. add recom to chart page history
		 * section 9. if necessary, create notification
		 */
		ChartPointModel point = new ChartPointModel(new Date(),80,75,120,150,ChartPointModel.SLEEP_LOW,false);
		EngineInputModel eim=addData(point);
		Log.d("testing", new Gson().toJson(eim));
		
	}

	private EngineInputModel addData(ChartPointModel point) {
		double caltoduration = 0.1;
		EngineInputModel input = new EngineInputModel();
		input.setUserinfo(userinfo);
		Date pointtimestamp = point.getTimestamp();
		ChartDataController.getInstance().getDataset().add(point);
		ChartDataController.getInstance().shiftDisplayToEnd();
		HealthStatusModel hsm = ChartDataController.getInstance().getLastValue();
		Date timestamp=pointtimestamp;
		for (int i = 0; i < 7; i++) {
			//to be changed?
			ActData act = new ActData(timestamp, (int) (caltoduration * hsm.getAct_calories()));
			SleepData sleep = new SleepData(timestamp, hsm.getSleepTotal());
			HRdata hr = new HRdata(timestamp, hsm.getHr_count());
			BPdata bp = new BPdata(timestamp, hsm.getBp_diastolic(), hsm.getBp_systolic());
			input.getActivities().add(act);
			input.getBloodPressures().add(bp);
			input.getHeartBeats().add(hr);
			input.getSleep().add(sleep);
			timestamp=MedReminderModel.addDuration(timestamp, -1, MedReminderModel.DurationUnit.Day);
		}
		return input;

		/*
		 * EngineInputModel input = new EngineInputModel(); //testing data Date
		 * timestamp = new Date(); UserInfo userinfo = new UserInfo();
		 * userinfo.setAge(45); userinfo.setGender("male");
		 * userinfo.setCardio(true); userinfo.setDiabetes(true);
		 * userinfo.setHypertension(true); userinfo.setInsomnia(true);
		 * userinfo.setHeight(175);
		 * 
		 * for (int i=0; i<7; i++){ ActData act = new ActData(timestamp, 40);
		 * SleepData sleep = new SleepData(timestamp,500); HRdata hr = new
		 * HRdata(timestamp, 80); BPdata bp = new BPdata(timestamp, 80, 135);
		 * WeightData wt = new WeightData(timestamp, 40);
		 * userinfo.getWeight().add(wt); input.getActivities().add(act);
		 * input.getBloodPressures().add(bp); input.getHeartBeats().add(hr);
		 * input.getSleep().add(sleep); } input.setUserinfo(userinfo); return
		 * input;
		 */
	}
}
