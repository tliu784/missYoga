package com.android.myhealthmate;

import com.android.entity.AccountController;
import com.android.entity.MedicineListController;
import com.android.reminder.AlarmReceiver;
import com.android.reminder.MedReminderController;
import com.android.remoteProfile.BenTestClass;
import com.android.remoteProfile.RemoteDataController;
import com.android.remoteProfile.RemoteRequestController;
import com.android.trend.ChartDataController;
import com.android.trend.ChartHelper;
import com.android.trend.RecordList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class MainActivity extends Activity {

	private Button signInButton;

	private EditText username;
	private EditText password;
	private TextView textSignUp;
	private AccountController accountController;
	private CheckBox remenberMe;
	private MedicineListController medicineList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		getActionBar().hide();
		setContentView(R.layout.activity_main);
		// get the value from view by using id
		username = (EditText) findViewById(R.id.enterUsername);
		password = (EditText) findViewById(R.id.enterPassword);
		signInButton = (Button) findViewById(R.id.sign_in);
		remenberMe = (CheckBox) findViewById(R.id.cb_remember_me);

		accountController = AccountController.getInstance();
		accountController.init(getApplicationContext());
		accountController.setTestAccout();
		
		medicineList = MedicineListController.getInstance();
		medicineList.init(getApplicationContext());
		

		if (accountController.isRemenbered()) {
			username.setText(accountController.getAccount().getUsername());
			password.setText(accountController.getAccount().getPassword());
			remenberMe.setChecked(true);
		}

		remenberMe.setOnCheckedChangeListener(getRemenberMeCheckBoxListener());

		textSignUp = (TextView) findViewById(R.id.txt_link_sign_up);
		// set the listener for button
		signInButton.setOnClickListener(getSignInClickListener());

		textSignUp.setOnClickListener(getSignUpClickListener());

		initAllDataControllers();
	}

	private void initAllDataControllers() {
		int hrFloor = this.getResources().getInteger(R.integer.hr_floor);
		int hrCeiling = this.getResources().getInteger(R.integer.hr_ceiling);
		int bplFloor = this.getResources().getInteger(R.integer.bpl_floor);
		int bplCeiling = this.getResources().getInteger(R.integer.bpl_ceiling);
		int bphFloor = this.getResources().getInteger(R.integer.bph_floor);
		int bphCeiling = this.getResources().getInteger(R.integer.bph_ceiling);
		int actFloor = this.getResources().getInteger(R.integer.act_floor);
		int actCeiling = this.getResources().getInteger(R.integer.act_ceiling);
		int sleepFloor = this.getResources().getInteger(R.integer.sleep_floor);
		int sleepCeiling = this.getResources().getInteger(R.integer.sleep_ceiling);

		ChartDataController.getInstance().init(getApplicationContext(), hrFloor, hrCeiling, bplFloor, bphFloor,
				bplCeiling, bphCeiling, actFloor, actCeiling, sleepFloor, sleepCeiling);
		RecordList.getInstance().init(getApplicationContext());
		MedReminderController.getInstance().init(getApplicationContext());
		AccountController.getInstance().init(getApplicationContext());
		RemoteRequestController.getInstance().initContext(getApplicationContext());
		MedicineListController.getInstance().init(getApplicationContext());
		
		//create requestModel test data
		RemoteRequestController.getInstance().createTestData();
		
		ChartDataController.getInstance().setDataset(ChartHelper.createRandomData(200));
//		ChartHelper.recordListGenerator(RecordList.getInstance().getRecordList());
		RecordList.getInstance().setRecordList(BenTestClass.recordListGenerator(50));
		RemoteDataController.getInstance().init();
		
		//create dataModel test data
		RemoteDataController.getInstance().getDataList().add(BenTestClass.generateRemoteData("terry@gmail", "Terry"));
		RemoteDataController.getInstance().getDataList().add(BenTestClass.generateRemoteData("nicole@gmail", "Nicole"));
	}

	private OnCheckedChangeListener getRemenberMeCheckBoxListener() {
		return new OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked)
					accountController.setRemenber(true);
				else
					accountController.setRemenber(false);
			}
		};
	}


	private OnClickListener getSignUpClickListener() {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				SignUp signUpView = new SignUp();
				SwithActivity(MainActivity.this, signUpView);
			}
		};
	}

	// listener for sign in button
	private OnClickListener getSignInClickListener() {
		return new OnClickListener() {

			@Override
			public void onClick(View v) {
				// String editTextUsername = username.getText().toString();
				// String editTextPassword = password.getText().toString();
				// if (TextUtils.isEmpty(editTextUsername)) {
				// PopUP(MainActivity.this, "Username cannot be empty");
				// } else if (TextUtils.isEmpty(editTextPassword)) {
				// PopUP(MainActivity.this, "Password cannot be empty");
				// } else {
				// AuthorizeAccount(editTextUsername, editTextPassword);
				//
				// }
				MainPage mainPageView = new MainPage();
				SwithActivity(MainActivity.this, mainPageView);
			}
		};

	}

	private void PopUP(Activity act, String content) {
		Toast.makeText(act, content, Toast.LENGTH_SHORT).show();
	}

	protected void AuthorizeAccount(String name, String password) {
		if (checkAccount(name, password)) {
			MainPage mainPageView = new MainPage();
			SwithActivity(MainActivity.this, mainPageView);

		} else {
			PopUP(MainActivity.this, "The account is not correct! Please try again.");
		}
	}

	// check Account information. password and username are "yoga"
	private boolean checkAccount(String usr, String pass) {
		boolean valid;
		if (usr.equals(accountController.getAccount().getUsername())
				&& pass.equals(accountController.getAccount().getPassword())) {
			valid = true;
		} else {
			valid = false;
		}
		return valid;
	}

	// use intent tocd swith the activity
	protected void SwithActivity(Activity currentAct, Activity target) {
		Intent intent = new Intent(currentAct, target.getClass());
		intent.putExtra(AlarmReceiver.notificationState, false);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
