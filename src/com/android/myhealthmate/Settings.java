package com.android.myhealthmate;

import com.android.entity.AccountController;
import com.android.reminder.MedReminderModel;
import com.android.reminder.ReminderViewController;
import com.android.remoteProfile.BenTestClass;
import com.android.remoteProfile.RemoteRequestModel;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class Settings extends Activity {

	private LinearLayout nameSec;
	private LinearLayout nameEditSec;
	private LinearLayout passwordSec;

	private LinearLayout passwordEditSec;
	private LinearLayout emailSec;
	private LinearLayout emailEditSec;

	private GridLayout monitorSection;
	private GridLayout requestSection;

	private TextView emailDisplayView;
	private TextView nameDisplayView;

	private EditText lastName;
	private EditText firstName;
	private EditText oldPassword;
	private EditText newPassword;
	private EditText rePassword;
	private EditText email;

	private Button userNameSave;
	private Button userNameCancel;
	private Button passwordSave;
	private Button passwordCancel;
	private Button emailSave;
	private Button emailCancel;

	private BenTestClass benTestClass;
	private AccountController accountController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		// the display part

		accountController = AccountController.getInstance();
		accountController.init(Settings.this);
		nameSec = (LinearLayout) findViewById(R.id.name_display_sec);
		nameEditSec = (LinearLayout) findViewById(R.id.name_edit_sec);
		nameDisplayView = (TextView) findViewById(R.id.name_display_view);
		passwordSec = (LinearLayout) findViewById(R.id.password_display_sec);
		passwordEditSec = (LinearLayout) findViewById(R.id.password_edit_sec);
		emailSec = (LinearLayout) findViewById(R.id.email_display_sec);
		emailEditSec = (LinearLayout) findViewById(R.id.email_edit_sec);
		emailDisplayView = (TextView) findViewById(R.id.email_display_view);

		nameDisplayView.setText(accountController.getAccount().getUsername());
		emailDisplayView.setText(accountController.getAccount().getEmail());
		nameEditSec.setVisibility(View.GONE);
		emailEditSec.setVisibility(View.GONE);
		passwordEditSec.setVisibility(View.GONE);

		nameSec.setOnClickListener(getNameSecListener());
		passwordSec.setOnClickListener(getPasswordSecListener());
		emailSec.setOnClickListener(getEmailSecListener());

		// the edit part
		lastName = (EditText) findViewById(R.id.enterlastname);
		firstName = (EditText) findViewById(R.id.enterfirstname);
		oldPassword = (EditText) findViewById(R.id.enteroldpwd);
		newPassword = (EditText) findViewById(R.id.enterpwd);
		rePassword = (EditText) findViewById(R.id.enterpwd2);
		email = (EditText) findViewById(R.id.enterEmail);

		userNameSave = (Button) findViewById(R.id.settings_name_save);
		userNameCancel = (Button) findViewById(R.id.settings_name_cancel);
		passwordSave = (Button) findViewById(R.id.settings_pwd_save);
		passwordCancel = (Button) findViewById(R.id.settings_pwd_cancel);
		emailSave = (Button) findViewById(R.id.settings_account_save);
		emailCancel = (Button) findViewById(R.id.settings_account_cancel);

		userNameSave.setOnClickListener(getNameSaveBtnListener());
		userNameCancel.setOnClickListener(getNameCancelBtnListener());
		passwordSave.setOnClickListener(getPwdSaveBtnListener());
		passwordCancel.setOnClickListener(getPwdCancelBtnListener());
		emailSave.setOnClickListener(getAccountSaveBtnListener());
		emailCancel.setOnClickListener(getAccountCancelBtnListener());

		benTestClass = new BenTestClass(Settings.this);
		// userApprovalSection = new UserMonitorSection(Settings.this);
		// userApprovalSection.addNameLayout(benTestClass.getUserListController().getMinitoredRemoteUserList().get(1));
		monitorSection = (GridLayout) findViewById(R.id.monitor_sec);
		requestSection = (GridLayout) findViewById(R.id.request_sec);

		int i = 0;
		for (RemoteRequestModel requestModel : benTestClass.getUserListController().getMinitoredRemoteUserList()) {
			if (i != 0) {
				addViewInMonitorSec(requestModel);
			}
			i++;
		}

		for (RemoteRequestModel requestModel : benTestClass.getUserListController().getMinitoredRemoteUserList()) {
			addViewInRequestSec(requestModel);
		}

	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.settings_menu, menu);
		return super.onCreateOptionsMenu(menu);

	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.send_request: {
			
		}
		case R.id.refresh_request: {		
			
		}
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void addViewInRequestSec(RemoteRequestModel requestModel) {
		TextView nameContent = new TextView(this);
		TextView emailContent = new TextView(this);
		CheckBox approveStatus = new CheckBox(this);

		nameContent.setText(requestModel.getRequestorName());
		requestSection.addView(nameContent);

		emailContent.setText(requestModel.getRequestorEmail());
		requestSection.addView(emailContent);

		if (requestModel.isApproved())
			approveStatus.setChecked(true);
		else
			approveStatus.setChecked(false);
		approveStatus.setText(R.string.approval);
		requestSection.addView(approveStatus);
		checkboxListener(approveStatus, requestModel);
	}

	private void checkboxListener(CheckBox approveStatus, final RemoteRequestModel requestModel) {

		approveStatus.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					Toast.makeText(getApplicationContext(), requestModel.getOwnerName(), Toast.LENGTH_SHORT).show();
					// do something
					requestModel.setApproved(true);
				} else {
					// do something
					requestModel.setApproved(false);
				}
			}
		});
	}

	private void addViewInMonitorSec(RemoteRequestModel requestModel) {
		TextView nameContent = new TextView(this);
		TextView emailContent = new TextView(this);
		TextView status = new TextView(this);

		nameContent.setText(requestModel.getOwnerName());
		monitorSection.addView(nameContent);

		emailContent.setText(requestModel.getOwnerEmail());
		monitorSection.addView(emailContent);

		if (requestModel.isApproved())
			status.setText("Approved");
		else
			status.setText("Pending...");
		monitorSection.addView(status);
	}

	private void PopUp(Activity act, String content) {
		Toast.makeText(act, content, Toast.LENGTH_SHORT).show();
	}

	public OnClickListener getNameSaveBtnListener() {
		return new OnClickListener() {
			public void onClick(View v) {
				if (TextUtils.isEmpty(firstName.getText().toString()))
					PopUp(Settings.this, "first name can not be empty");
				else {
					String nameStr = firstName.getText().toString() + " " + lastName.getText().toString();
					accountController.setAccountUserName(firstName.getText().toString());
					nameDisplayView.setText(nameStr);
					nameEditSec.setVisibility(View.GONE);
				}
			}
		};
	}

	public OnClickListener getNameCancelBtnListener() {
		return new OnClickListener() {
			public void onClick(View v) {
				nameEditSec.setVisibility(View.GONE);
			}
		};
	}

	public OnClickListener getPwdSaveBtnListener() {
		return new OnClickListener() {
			public void onClick(View v) {
				if (TextUtils.isEmpty(oldPassword.getText().toString()))
					PopUp(Settings.this, "password can not be empty");
				else {
					String oldPwd = oldPassword.getText().toString();
					String newPwd1 = newPassword.getText().toString();
					String newPwd2 = rePassword.getText().toString();
					if (!oldPwd.equals(accountController.getAccount().getPassword()))
						PopUp(Settings.this, accountController.getAccount().getPassword());
					else if (!newPwd1.equals(newPwd2))
						PopUp(Settings.this, "new passwords do not match");
					else {
						accountController.setAccountPassword(newPwd2);
						passwordEditSec.setVisibility(View.GONE);
					}
				}
			}
		};
	}

	public OnClickListener getPwdCancelBtnListener() {
		return new OnClickListener() {
			public void onClick(View v) {
				passwordEditSec.setVisibility(View.GONE);
			}
		};
	}

	public OnClickListener getAccountSaveBtnListener() {
		return new OnClickListener() {
			public void onClick(View v) {
				if (TextUtils.isEmpty(email.getText().toString()))
					PopUp(Settings.this, "email can not be empty");
				else {
					String emailStr = email.getText().toString();
					accountController.setAccountEmail(emailStr);
					emailDisplayView.setText(emailStr);
					emailEditSec.setVisibility(View.GONE);
				}
			}
		};
	}

	public OnClickListener getAccountCancelBtnListener() {
		return new OnClickListener() {
			public void onClick(View v) {
				emailEditSec.setVisibility(View.GONE);
			}
		};
	}

	public OnClickListener getNameSecListener() {
		return new OnClickListener() {
			public void onClick(View v) {
				if (nameEditSec.getVisibility() == View.VISIBLE)
					nameEditSec.setVisibility(View.GONE);
				else
					nameEditSec.setVisibility(View.VISIBLE);
			}
		};
	}

	public OnClickListener getPasswordSecListener() {
		return new OnClickListener() {
			public void onClick(View v) {
				if (passwordEditSec.getVisibility() == View.VISIBLE)
					passwordEditSec.setVisibility(View.GONE);
				else
					passwordEditSec.setVisibility(View.VISIBLE);
			}
		};
	}

	public OnClickListener getEmailSecListener() {
		return new OnClickListener() {
			public void onClick(View v) {
				if (emailEditSec.getVisibility() == View.VISIBLE)
					emailEditSec.setVisibility(View.GONE);
				else
					emailEditSec.setVisibility(View.VISIBLE);

			}
		};
	}

}
