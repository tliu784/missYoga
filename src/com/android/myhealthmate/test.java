package com.android.myhealthmate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.android.entity.AccountModel;
import com.android.entity.ProfileModel;
import com.android.myhealthmate.R;
import com.android.service.FileOperation;

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
				testSave();
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
