package com.android.myhealthmate;

import com.android.myhealthmate.*;



import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Button signInButton;
	private Button signUpButton;
	private Button test;
	private EditText username;
	private EditText password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//get the value from view by using id
		username = (EditText) findViewById(R.id.enterUsername);
		password = (EditText) findViewById(R.id.enterPassword);
		signInButton = (Button) findViewById(R.id.sign_in);
		signUpButton = (Button) findViewById(R.id.sign_up);
		test  = (Button) findViewById(R.id.test);
		//set the listener for button
		signInButton.setOnClickListener(getSignInClickListener());
		signUpButton.setOnClickListener(getSignUpClickListener());
		test.setOnClickListener(getTestClickListener());
		
	}
	
	
	
    //listener for sign up button
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
	private void SwithActivity(Activity currentAct, Activity target) {
		Intent intent = new Intent(currentAct, target.getClass());
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
