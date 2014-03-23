package com.android.myhealthmate;
import com.android.reminder.AlarmReceiver;

import android.app.AlertDialog.Builder;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Button signInButton;
	private Button nicoleButton;
	private Button test;
	private Button benButton;
	private EditText username;
	private EditText password;
	private TextView textSignUp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
	    getActionBar().hide();
		setContentView(R.layout.activity_main);
		//get the value from view by using id
		username = (EditText) findViewById(R.id.enterUsername);
		password = (EditText) findViewById(R.id.enterPassword);
		signInButton = (Button) findViewById(R.id.sign_in);
		nicoleButton = (Button) findViewById(R.id.nicole);
		benButton =  (Button) findViewById(R.id.ben);
		test  = (Button) findViewById(R.id.test);
		
		textSignUp = (TextView) findViewById(R.id.txt_link_sign_up);
		//set the listener for button
		signInButton.setOnClickListener(getSignInClickListener());
		
		nicoleButton.setOnClickListener(getNicolePageClickListener());
		
		textSignUp.setOnClickListener(getSignUpClickListener());
		
		test.setOnClickListener(getTestClickListener());
		
		benButton.setOnClickListener(getBenPageClickListener());
		
	}
	
	private OnClickListener getBenPageClickListener() {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				
			HrDetail benTest = new HrDetail();
			SwithActivity(MainActivity.this, benTest);
				}
			};
	}
	
	private OnClickListener getNicolePageClickListener() {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				SignIn nicoletest = new SignIn();
				SwithActivity(MainActivity.this, nicoletest);
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
   
	
	private OnClickListener getTestClickListener() {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				test testView = new test();
				SwithActivity(MainActivity.this, testView);
				}
			};
	}
	
	
	
	
    //listener for sign in button
	private OnClickListener getSignInClickListener() {
		return new OnClickListener() {

			@Override
			public void onClick(View v) {
				String editTextUsername = username.getText().toString();
				String editTextPassword = password.getText().toString();
				if (TextUtils.isEmpty(editTextUsername)) {
					PopUP(MainActivity.this, "Username cannot be empty");
				} else if (TextUtils.isEmpty(editTextPassword)) {
					PopUP(MainActivity.this, "Password cannot be empty");
				} else {
					AuthorizeAccount(editTextUsername, editTextPassword);
				}

			}
		};
	}

	private void PopUP(Activity act, String content) {
		Toast.makeText(act, content, Toast.LENGTH_SHORT).show();
	}

	protected void AuthorizeAccount(String name, String password) {
		if (checkAccount(name,password)) {
			MainPage mainPageView = new MainPage();
			SwithActivity(MainActivity.this, mainPageView);

		} else{
			PopUP(MainActivity.this, "The account is not correct! Please try again.");
		}
	}
	
	//check Account information. password and username are "yoga"
	private boolean checkAccount(String usr, String pass)
	{
		boolean valid;
		if (usr.equals("a") && pass.equals("a")) {
			valid = true;
		}
		else{
			valid = false;
		}
		return valid;
	}

	//use intent tocd swith the activity
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
