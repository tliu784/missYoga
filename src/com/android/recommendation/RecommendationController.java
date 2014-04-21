package com.android.recommendation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

import android.util.Log;

import com.android.entity.AccountController;
import com.android.entity.HealthStatusModel;
import com.android.entity.MedicineListController;
import com.android.entity.MedicineModel;
import com.android.entity.MedicineModel.HealthEffect;
import com.android.entity.RecomModel;
import com.android.myhealthmate.MainPage;
import com.android.myhealthmate.RecContent;
import com.android.recommendation.EngineInputModel.ActData;
import com.android.recommendation.EngineInputModel.BPdata;
import com.android.recommendation.EngineInputModel.HRdata;
import com.android.recommendation.EngineInputModel.SleepData;
import com.android.recommendation.EngineInputModel.UserInfo;
import com.android.reminder.MedReminderModel;
import com.android.service.NotificationService;
import com.android.service.ResponseHandler;
import com.android.service.RestCallHandler;
import com.android.trend.ChartDataController;
import com.android.trend.ChartPointModel;
import com.android.trend.RecordList;
import com.android.trend.RecordModel.recordType;
import com.google.gson.Gson;

public class RecommendationController implements ResponseHandler {

	private UserInfo userinfo;
	// private ArrayList<ChartPointModel> existingChartPoints;
	// private ArrayList<ChartPointModel> newData;
	private String[] demoJson;
	private int newDataIndex = 0;
	private static RecommendationController instance = null;
	private MainPage mainpage;
	private RecordList recordListController;

	public static RecommendationController getInstance() {
		if (instance == null)
			instance = new RecommendationController();
		return instance;
	}

	private RecommendationController() {
		init();
	}

	private void init() {
		userinfo = AccountController.getInstance().getAccount().getUserInfo();
		recordListController = RecordList.getInstance();
		loadDemoInput();
	}

	private void loadDemoInput() {
		demoJson = new String[5];
		demoJson[0] = getStringFromInputStream(RecommendationController.class
				.getResourceAsStream("/com/android/recommendation/test_data_0.json"));
		demoJson[1] = getStringFromInputStream(RecommendationController.class
				.getResourceAsStream("/com/android/recommendation/test_data_1.json"));
		demoJson[2] = getStringFromInputStream(RecommendationController.class
				.getResourceAsStream("/com/android/recommendation/test_data_2.json"));
		demoJson[3] = getStringFromInputStream(RecommendationController.class
				.getResourceAsStream("/com/android/recommendation/test_data_3.json"));
		demoJson[4] = getStringFromInputStream(RecommendationController.class
				.getResourceAsStream("/com/android/recommendation/test_data_4.json"));
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
		ChartPointModel point = new ChartPointModel(new Date(), 80, 75, 120, 150, ChartPointModel.SLEEP_LOW, false);
		EngineInputModel eim = addData(point);
		Log.d("testing", new Gson().toJson(eim));

	}

	public void getRecom() {
		Gson gson = new Gson();
		if (newDataIndex > 4) {
			newDataIndex = 4;
		}
		String json = demoJson[newDataIndex];
		EngineInputModel eim = null;
		try {
			eim = gson.fromJson(json, EngineInputModel.class);
		} catch (Exception e) {
		}

		if (eim != null) {
			HealthStatusModel hsm = eim.getHealthStatus();
			mainpage.updateHealthStatus(hsm);
			mainpage.updateTime(new Date());
		}
		String url = "http://health-engine.herokuapp.com/";
		RestCallHandler rest = new RestCallHandler(this, url, json);
		rest.handleResponse();
		newDataIndex++;
	}

	@Override
	public void processResponse(String jsonResponse) {
		boolean alreadynoti = false;
		Gson gson = new Gson();
		RecomModel[] recomArray = null;
		boolean modifiedRec = false;
		if (jsonResponse != null) {
			try {
				recomArray = gson.fromJson(jsonResponse, RecomModel[].class);
			} catch (Exception e) {
			}
		}
		if (recomArray != null) {

			for (int i = 0; i < recomArray.length; i++) {
				RecomModel somerec = recomArray[i];
				// add record in rec history
				// for example: recordlist.add(converted somerec);

				// check missed medicine
				HealthEffect he = HealthEffect.BP;
				MedicineModel missedMed = MedicineListController.getInstance().existMedicine(he);
				if (missedMed != null) {
					if (getRecType(somerec).equals(missedMed.getEffect())) {
						if (!alreadynoti) {
							RecContent.setMed(missedMed);
							RecContent.handleMedicine=true;
							new NotificationService(mainpage, "New Recommendation", missedMed.getMissedText(true), true);
							alreadynoti = true;
							modifiedRec = true;
						}
					} else {

					}
				}
				// optionally create notification
				if (somerec.getId() > 900 && (!alreadynoti)) {
					new NotificationService(mainpage, "New Recommendation", somerec.getRecommendation(), false);
					alreadynoti = true;
				}

				if (!modifiedRec)
					recordListController.addOneRecord(recordType.Recommendation, new Date(),
							somerec.getRecommendation(), toSeverityLevel(somerec.getSeverity()), false);

			}
			if (modifiedRec) {
				RecomModel[] recom = new RecomModel[1];
				recom[0] = new RecomModel(RecContent.getMed(),true); //question
				RecContent.setOriginalRecs(recomArray);
				mainpage.postRefresh(recom);
			} else
				mainpage.postRefresh(recomArray);
		}
	}

	private HealthEffect getRecType(RecomModel somerec) {
		if (somerec.getId() >= 200 && somerec.getId() < 300) {
			return HealthEffect.BP;
		}
		return HealthEffect.OTHERS;
	}

	public String toSeverityLevel(int level) {
		if (level == 1 || level == 2)
			return "Low";
		else if (level == 3)
			return "Medium";
		else
			return "High";
	}

	private EngineInputModel addData(ChartPointModel point) {
		double caltoduration = 0.1;
		EngineInputModel input = new EngineInputModel();
		input.setUserinfo(userinfo);
		Date pointtimestamp = point.getTimestamp();
		ChartDataController.getInstance().getDataset().add(point);
		ChartDataController.getInstance().shiftDisplayToEnd();
		HealthStatusModel hsm = ChartDataController.getInstance().getLastValue();
		Date timestamp = pointtimestamp;
		for (int i = 0; i < 7; i++) {
			// to be changed?
			ActData act = new ActData(timestamp, (int) (caltoduration * hsm.getAct_calories()));
			SleepData sleep = new SleepData(timestamp, hsm.getSleepTotal());
			HRdata hr = new HRdata(timestamp, hsm.getHr_count());
			BPdata bp = new BPdata(timestamp, hsm.getBp_diastolic(), hsm.getBp_systolic());
			input.getActivities().add(act);
			input.getBloodPressures().add(bp);
			input.getHeartBeats().add(hr);
			input.getSleep().add(sleep);
			timestamp = MedReminderModel.addDuration(timestamp, -1, MedReminderModel.DurationUnit.Day);
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

	// convert InputStream to String
	private String getStringFromInputStream(InputStream is) {

		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try {

			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();

	}

	public void setMainpage(MainPage mainpage) {
		this.mainpage = mainpage;
	}

	public MainPage getMainpage() {
		return mainpage;
	}
}
