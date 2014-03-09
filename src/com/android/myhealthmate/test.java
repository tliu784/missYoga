package com.android.myhealthmate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.android.entity.AccountModel;
import com.android.entity.ProfileModel;
import com.android.myhealthmate.R;
import com.android.service.FileOperation;
import com.android.service.RestCallHandler;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class test extends Activity {

	private EditText text1;
	private EditText text2;
	private Button test1;
	private Button test2;

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

	}

	private OnClickListener getTest1ClickListener() {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				testRest();
			}
		};
	}

	private OnClickListener getTest2ClickListener() {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				testDelete();
			}
		};
	}

	private void PopUP(Activity act, String content) {
		Toast.makeText(act, content, Toast.LENGTH_SHORT).show();
	}

	private void testRest() {
		String url = "http://healthengineherokuappcom.apiary.io/";
//		String url = "http://1-dot-stayhealthyserver.appspot.com/getReco/123";
		// String json =
		// "{\n    \"userinfo\": {\n        \"age\": 45,\n        \"gender\": \"male\",\n        \"height\": 168,\n        \"weight\": [\n            {\n                \"value\": 65.3,    //this is the data of current day\n                \"date\": \"2012-04-24\"\n            },\n            {\n                \"value\": 65.3,    // this should be average of last week\n                \"date\": \"2012-04-17\"    //by defult this should the last 7 days\n            },\n            {\n                \"value\": 65.3,    // this should be average of last month\n                \"date\": \"2012-03-24\"    //by defult this should the last 30 days\n            }\n        ],\n        \"hypertension\" : true,\n        \"diabetes\" : true,\n        \"insomnia\" : true,\n        \"cardio\" : true\n    },\n    \"activities\": [\n        {\n            \"distance\": 500,     //this is the data of current day\n            \"duration\": 7.3,\n            \"date\": \"2012-04-24\",\n            \"startTime\": \"18:20:42Z\",\n            \"steps\": 800\n        },\n        {\n            \"distance\": 1500,  // this is accumulation not average by last week\n            \"duration\": 140,\n            \"date\": \"2012-04-17\",\n            \"startTime\": \"\",    // timestamp should be empty\n            \"steps\": 1700\n        },\n        {\n            \"distance\": 12500, // this is accumulation not average by last month\n            \"duration\": 1430,\n            \"date\": \"2012-03-24\",\n            \"startTime\": \"\",   // timestamp should be empty\n            \"steps\": 49300\n        }\n    ],\n    \"sleep\": [\n        {\n            \"efficiency\": 4,    //this is the data of current day\n            \"date\": \"2012-04-24\",\n            \"startTime\": \"18:25:43Z\",\n            \"minutesAsleep\": 453,\n            \"minutesAwake\": 34,\n            \"awakeningsCount\": 8,\n            \"timeInBed\": 541\n        },\n        {\n            \"efficiency\": 4,   // this is the average of last week\n            \"date\": \"2012-04-17\",\n            \"startTime\": \"\",   // this should be empty\n            \"minutesAsleep\": 453,\n            \"minutesAwake\": 34,\n            \"awakeningsCount\": 8,\n            \"timeInBed\": 541\n        },\n        {\n            \"efficiency\": 4,  // this is the average of last month\n            \"date\": \"2012-03-24\",\n            \"startTime\": \"\",  // this should be empty\n            \"minutesAsleep\": 453,\n            \"minutesAwake\": 34,\n            \"awakeningsCount\": 8,\n            \"timeInBed\": 541\n        }\n    ],\n    \"heartBeats\": [\n        {\n            \"count\": 56,       //this is the data of current day\n            \"date\": \"2012-04-24\",\n            \"time\": \"18:23:43Z\"\n        },\n        {\n            \"count\": 60,       //this is the average of last week\n            \"date\": \"2012-04-17\",\n            \"time\": \"\"\n        },\n        {\n            \"count\": 59,      //this is the average of last month\n            \"date\": \"2012-03-24\",\n            \"time\": \"\"\n        }\n    ],\n    \"bloodPressures\": [\n        {\n            \"systolic\": 100,         //this is the data of current day\n            \"diastolic\": 71,\n            \"date\": \"2012-04-23\",\n            \"time\": \"18:23:43Z\"\n        },\n        {\n            \"systolic\": 100,         //this is the average of last week\n            \"diastolic\": 71,\n            \"date\": \"2012-04-17\",     \n            \"time\": \"\"               // timestamp should be empty\n        },\n        {\n            \"systolic\": 100,         //this is the average of last month\n            \"diastolic\": 71,\n            \"date\": \"2012-03-24\",\n            \"time\": \"\"               // timestamp should be empty\n        }\n    ]\n}";
		String json = "";
		// String result = RestCallHandler.getResponse(url, json);
	
		try {
			new RestCallHandler(test.this, url, json).getS();
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

	private void testDelete() {
		String filename = "filename.obj";
		String status;
		if (FileOperation.delete(filename, getApplicationContext())) {
			status = "yes";
		} else {
			status = "no";
		}
		PopUP(test.this, status);
	}

}
