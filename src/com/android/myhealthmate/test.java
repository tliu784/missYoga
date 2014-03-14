package com.android.myhealthmate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.android.entity.AccountModel;
import com.android.entity.ProfileModel;
import com.android.myhealthmate.R;
import com.android.reminder.AlarmService;
import com.android.reminder.MedReminderController;
import com.android.reminder.MedReminderList;
import com.android.reminder.MedReminderModel;
import com.android.service.FileOperation;
import com.google.gson.Gson;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewDataInterface;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class test extends Activity {

	private EditText text1;
	private EditText text2;
	private Button test1;
	private Button test2;
	private TextView testText;
	private MedReminderController reminders;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.filetest);
		text1 = (EditText) findViewById(R.id.terry_text1);
		text2 = (EditText) findViewById(R.id.terry_text2);
		test1 = (Button) findViewById(R.id.test1);
		test2 = (Button) findViewById(R.id.test2);
		test1.setOnClickListener(getTest1ClickListener());
		test2.setOnClickListener(getTest2ClickListener());
		testText = (TextView) findViewById(R.id.terry_test_box);
		createChart();
		
	}
	
	private void createChart(){
		// init example series data
		GraphViewSeries exampleSeries = new GraphViewSeries(new GraphViewData[] {
		      new GraphViewData(1, 2.0d)
		      , new GraphViewData(2, 1.5d)
		      , new GraphViewData(3, 2.5d)
		      , new GraphViewData(4, 1.0d)
		});
		 
		GraphView graphView = new LineGraphView(
		      this // context
		      , "GraphViewDemo" // heading
		);
		graphView.addSeries(exampleSeries); // data
		 
		LinearLayout layout = (LinearLayout) findViewById(R.id.graph1);
		layout.addView(graphView);
	}
	
	public class GraphViewData implements GraphViewDataInterface {
	    private double x,y;

	    public GraphViewData(double x, double y) {
	        this.x = x;
	        this.y = y;
	    }

	    @Override
	    public double getX() {
	        return this.x;
	    }

	    @Override
	    public double getY() {
	        return this.y;
	    }
	}

	private OnClickListener getTest1ClickListener() {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				//testReminderController();
				testReminderController();
			}
		};
	}

	private OnClickListener getTest2ClickListener() {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				String filename = "medreminders.obj";
//				MedReminderList list = (MedReminderList) FileOperation.read(
//						filename, getApplicationContext());
				ArrayList<MedReminderModel> list=reminders.getReminderList();
				testText.setMovementMethod(new ScrollingMovementMethod());
				testText.setText(new Gson().toJson(list));
			}
		};
	}

	private void PopUP(Activity act, String content) {
		Toast.makeText(act, content, Toast.LENGTH_SHORT).show();
	}
	
	private void testReminderController(){
		testDelete("medreminders.obj");
		reminders = MedReminderController.getInstance();
		reminders.init(this.getApplicationContext());
		addTestReminders(reminders);
//		MedReminderModel reminder=reminders.findbyid(2);
//	
//		new AlarmService(this.getApplicationContext()).setAlarm(reminder);
		testText.setText(Integer.toString(MedReminderController.getInstance().getReminderList().size()));
	}
	
	private void testAlarm(){
		Date creationTime = Calendar.getInstance().getTime();
		String title = "aspirin";
		String detail = "take 1 pill";
		int duration = 2;
		MedReminderModel.DurationUnit dunit = MedReminderModel.DurationUnit.Day;
		int repeat = 4;
		MedReminderModel.DurationUnit runit = MedReminderModel.DurationUnit.Sec;
		MedReminderModel reminder=new MedReminderModel(1,creationTime, title, detail, duration, dunit,
				repeat, runit);
		new AlarmService(this.getApplicationContext()).setAlarm(reminder);
		testText.setText(reminder.toString());
	}

	public void addTestReminders(MedReminderController reminders) {
		
		if (reminders.getReminderList().size()>0)
			return;
		// reminder 1
		Date creationTime = Calendar.getInstance().getTime();
		String title = "aspirin";
		String detail = "take 1 pill";
		int duration = 2;
		MedReminderModel.DurationUnit dunit = MedReminderModel.DurationUnit.Day;
		int repeat = 4;
		MedReminderModel.DurationUnit runit = MedReminderModel.DurationUnit.Hour;
		reminders.addReminder(creationTime, title, detail, duration, dunit,
				repeat, runit);
		// reminder 2
		Date creationTime2 = Calendar.getInstance().getTime();
		String title2 = "title 2";
		String detail2 = "take 2 pills";
		int duration2 = 2;
		MedReminderModel.DurationUnit dunit2 = MedReminderModel.DurationUnit.Day;
		int repeat2 = 5;
		MedReminderModel.DurationUnit runit2 = MedReminderModel.DurationUnit.Sec;
		reminders.addReminder(creationTime2, title2, detail2, duration2,
				dunit2, repeat2, runit2);

		// reminder 3

		Date creationTime3 = Calendar.getInstance().getTime();
		String title3 = "title 3";
		String detail3 = "take 3 pills";
		int duration3 = 2;
		MedReminderModel.DurationUnit dunit3 = MedReminderModel.DurationUnit.Day;
		int repeat3 = 10;
		MedReminderModel.DurationUnit runit3 = MedReminderModel.DurationUnit.Sec;
		reminders.addReminder(creationTime3, title3, detail3, duration3,
				dunit3, repeat3, runit3);
		
	}

	private void testMedRemModle() {
	
		String text = "";
		for (MedReminderModel reminder : reminders.getReminderList()) {
			text += reminder.toString();
			text += "\n";
		}
		testText.setText(text);
	}

	private void testRest() {
		String url = "http://healthengineherokuappcom.apiary.io/";
		// String url =
		// "http://1-dot-stayhealthyserver.appspot.com/getReco/123";
		// String json =
		// "{\n    \"userinfo\": {\n        \"age\": 45,\n        \"gender\": \"male\",\n        \"height\": 168,\n        \"weight\": [\n            {\n                \"value\": 65.3,    //this is the data of current day\n                \"date\": \"2012-04-24\"\n            },\n            {\n                \"value\": 65.3,    // this should be average of last week\n                \"date\": \"2012-04-17\"    //by defult this should the last 7 days\n            },\n            {\n                \"value\": 65.3,    // this should be average of last month\n                \"date\": \"2012-03-24\"    //by defult this should the last 30 days\n            }\n        ],\n        \"hypertension\" : true,\n        \"diabetes\" : true,\n        \"insomnia\" : true,\n        \"cardio\" : true\n    },\n    \"activities\": [\n        {\n            \"distance\": 500,     //this is the data of current day\n            \"duration\": 7.3,\n            \"date\": \"2012-04-24\",\n            \"startTime\": \"18:20:42Z\",\n            \"steps\": 800\n        },\n        {\n            \"distance\": 1500,  // this is accumulation not average by last week\n            \"duration\": 140,\n            \"date\": \"2012-04-17\",\n            \"startTime\": \"\",    // timestamp should be empty\n            \"steps\": 1700\n        },\n        {\n            \"distance\": 12500, // this is accumulation not average by last month\n            \"duration\": 1430,\n            \"date\": \"2012-03-24\",\n            \"startTime\": \"\",   // timestamp should be empty\n            \"steps\": 49300\n        }\n    ],\n    \"sleep\": [\n        {\n            \"efficiency\": 4,    //this is the data of current day\n            \"date\": \"2012-04-24\",\n            \"startTime\": \"18:25:43Z\",\n            \"minutesAsleep\": 453,\n            \"minutesAwake\": 34,\n            \"awakeningsCount\": 8,\n            \"timeInBed\": 541\n        },\n        {\n            \"efficiency\": 4,   // this is the average of last week\n            \"date\": \"2012-04-17\",\n            \"startTime\": \"\",   // this should be empty\n            \"minutesAsleep\": 453,\n            \"minutesAwake\": 34,\n            \"awakeningsCount\": 8,\n            \"timeInBed\": 541\n        },\n        {\n            \"efficiency\": 4,  // this is the average of last month\n            \"date\": \"2012-03-24\",\n            \"startTime\": \"\",  // this should be empty\n            \"minutesAsleep\": 453,\n            \"minutesAwake\": 34,\n            \"awakeningsCount\": 8,\n            \"timeInBed\": 541\n        }\n    ],\n    \"heartBeats\": [\n        {\n            \"count\": 56,       //this is the data of current day\n            \"date\": \"2012-04-24\",\n            \"time\": \"18:23:43Z\"\n        },\n        {\n            \"count\": 60,       //this is the average of last week\n            \"date\": \"2012-04-17\",\n            \"time\": \"\"\n        },\n        {\n            \"count\": 59,      //this is the average of last month\n            \"date\": \"2012-03-24\",\n            \"time\": \"\"\n        }\n    ],\n    \"bloodPressures\": [\n        {\n            \"systolic\": 100,         //this is the data of current day\n            \"diastolic\": 71,\n            \"date\": \"2012-04-23\",\n            \"time\": \"18:23:43Z\"\n        },\n        {\n            \"systolic\": 100,         //this is the average of last week\n            \"diastolic\": 71,\n            \"date\": \"2012-04-17\",     \n            \"time\": \"\"               // timestamp should be empty\n        },\n        {\n            \"systolic\": 100,         //this is the average of last month\n            \"diastolic\": 71,\n            \"date\": \"2012-03-24\",\n            \"time\": \"\"               // timestamp should be empty\n        }\n    ]\n}";
		String json = "";
		// String result = RestCallHandler.getResponse(url, json);

		try {
			// new RestCallHandler(test.this, url, json).getS();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// if (result != null) {
		// PopUP(test.this, result);
		// } else {
		// PopUP(test.this, "nope");
		// }
	}

	private void testLogin() {
		String filename = "account.obj";
		String editTextUsername = text1.getText().toString();
		String editTextPassword = text2.getText().toString();
		/*
		 * AccountModel account = new AccountModel(editTextUsername);
		 * account.setPassword(editTextPassword); FileOperation.save(account,
		 * filename, getApplicationContext());
		 */
		AccountModel savedAccount = (AccountModel) FileOperation.read(filename,
				getApplicationContext());

		String status;
		if (savedAccount.checkLogin(editTextUsername, editTextPassword)) {
			status = "yes";
		} else {
			status = "no";
		}
		PopUP(test.this, status);
	}

	private void testAge() {
		ProfileModel savedUser;
		String editTextUsername = text1.getText().toString();
		ProfileModel user = new ProfileModel(editTextUsername);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date bday;
		try {
			bday = sdf.parse("21/12/1984");
			user.setBday(bday);
			int age = user.getAge();
			PopUP(test.this, Integer.toString(age));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void testSave() {
		ProfileModel savedUser;
		String filename = "filename.obj";
		String editTextUsername = text1.getText().toString();
		ProfileModel user = new ProfileModel(editTextUsername);
		FileOperation.save(user, filename, getApplicationContext());
		savedUser = (ProfileModel) FileOperation.read(filename,
				getApplicationContext());
		if (savedUser != null) {
			PopUP(test.this, savedUser.getName());
		}
	}

	private void testDelete(String filename) {

		String status;
		if (FileOperation.delete(filename, getApplicationContext())) {
			status = "yes";
		} else {
			status = "no";
		}
		PopUP(test.this, status);
	}

}
