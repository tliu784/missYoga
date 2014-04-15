package com.android.myhealthmate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.android.entity.AccountController;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

public class Profile extends Activity {
	private EditText nameEditView;
	private Date birthDate;
	private EditText birthDateEditView;
	private EditText heightEditView;
	private EditText weightEditView;

	private Button submit;
	private Button cancel;

	private RadioGroup genderRadioGroup;
	private RadioGroup HypertensionRadioGroup;
	private RadioGroup diabetesRadioGroup;
	private RadioGroup insomniaRadioGroup;
	private RadioGroup cardioRadioGroup;

	private AccountController accountController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);

		birthDateEditView = (EditText) findViewById(R.id.txtbox_dob);
		heightEditView = (EditText) findViewById(R.id.txtbox_height);
		weightEditView = (EditText) findViewById(R.id.txtbox_weight);
		nameEditView = (EditText) findViewById(R.id.txtbox_name);
		genderRadioGroup = (RadioGroup) findViewById(R.id.gender);
		cardioRadioGroup = (RadioGroup) findViewById(R.id.cardio);
		insomniaRadioGroup = (RadioGroup) findViewById(R.id.insomnia);
		diabetesRadioGroup = (RadioGroup) findViewById(R.id.diabetes);
		HypertensionRadioGroup = (RadioGroup) findViewById(R.id.hypertension);

		accountController = AccountController.getInstance();

		submit = (Button) findViewById(R.id.profile_submit);

		cancel = (Button) findViewById(R.id.profile_cancel);

		if (!accountController.getAccount().isNewUser()) {
			submit.setVisibility(View.GONE);
			cancel.setVisibility(View.GONE);
			switchAllEditToView();
		}

		submit.setOnClickListener(getSubmitListener());
		cancel.setOnClickListener(getCancelListener());

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.profile_menu, menu);
		return super.onCreateOptionsMenu(menu);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.edit_profile: {

			switchAllEditToView();
			if (submit.getVisibility() == View.GONE) {
				submit.setVisibility(View.VISIBLE);
				cancel.setVisibility(View.VISIBLE);
			} else {
				submit.setVisibility(View.GONE);
				cancel.setVisibility(View.GONE);
			}
			return true;
		}
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void switchEditToView(int switcherID, int textViewID,int editViewID, String setText) {

		ViewSwitcher switcher = (ViewSwitcher) findViewById(switcherID);
		switcher.showPrevious();// or switcher.showPrevious();
		EditText myET = (EditText) findViewById(editViewID);
		myET.setText(setText);
		TextView myTV = (TextView) switcher.findViewById(textViewID);
		myTV.setText(setText);
	}
	
	private void switchEditToView(int switcherID, int textViewID,int editViewID, int setText ) {

		ViewSwitcher switcher = (ViewSwitcher) findViewById(switcherID);
		switcher.showPrevious();// or switcher.showPrevious();
		EditText myET = (EditText) findViewById(editViewID);
		myET.setText(Integer.toString(setText));
		TextView myTV = (TextView) switcher.findViewById(textViewID);
		myTV.setText(Integer.toString(setText));
	}

	
	private void switchRadioToView(int switcherID, int textViewID,int radioBuutonID, String setText) {
		ViewSwitcher switcher = (ViewSwitcher) findViewById(switcherID);
		switcher.showPrevious();// or switcher.showPrevious();
		RadioButton myET = (RadioButton) findViewById(radioBuutonID);
		myET.setChecked(true);
		TextView myTV = (TextView) switcher.findViewById(textViewID);
		myTV.setText(setText);
	}
	
	private void switchAllEditToView() {
		// name
		switchEditToView(R.id.name_switcher, R.id.txt_name,R.id.txtbox_name, accountController.getAccount().getName());
		// gender
		if (accountController.getAccount().isGender())
			switchRadioToView(R.id.gender_switcher, R.id.txt_gender,R.id.radio_profile_male, "Male");
		else
			switchRadioToView(R.id.gender_switcher, R.id.txt_gender, R.id.radio_profile_female,"Female");
		// birthday
		switchEditToView(R.id.dob_switcher, R.id.txt_dob,R.id.txtbox_dob, getDate(accountController.getAccount().getBirthDate()));
		// height
		switchEditToView(R.id.height_switcher, R.id.txt_height,R.id.txtbox_height, accountController.getAccount().getHeight());
		// weight
		switchEditToView(R.id.weight_switcher, R.id.txt_weight,R.id.txtbox_weight, accountController.getAccount().getWeight());
		// setHypertension
		if (accountController.getAccount().isHypertension())
			switchRadioToView(R.id.hypertension_switcher, R.id.txt_hypertension,R.id.radio_profile_hypertension_yes, "Yes");
		else
			switchRadioToView(R.id.hypertension_switcher, R.id.txt_hypertension,R.id.radio_profile_hypertension_no, "No");
		// setDiabetes
		if (accountController.getAccount().isDiabetes())
			switchRadioToView(R.id.diabetes_switcher, R.id.txt_diabetes,R.id.radio_profile_diabetes_yes, "Yes");
		else
			switchRadioToView(R.id.diabetes_switcher, R.id.txt_diabetes,R.id.radio_profile_diabetes_no, "No");
		// setInsomnia
		if (accountController.getAccount().isInsomnia())
			switchRadioToView(R.id.insomnia_switcher, R.id.txt_insomnia,R.id.radio_profile_insomnia_yes, "Yes");
		else
			switchRadioToView(R.id.insomnia_switcher, R.id.txt_insomnia,R.id.radio_profile_insomnia_no, "No");
		// setCardio
		if (accountController.getAccount().isCardio())
			switchRadioToView(R.id.cardio_switcher, R.id.txt_cardio,R.id.radio_profile_cardio_yes, "Yes");
		else
			switchRadioToView(R.id.cardio_switcher, R.id.txt_cardio,R.id.radio_profile_cardio_no, "No");

	}

	private void setHypertension() {

		int selectedId = HypertensionRadioGroup.getCheckedRadioButtonId();
		RadioButton hypertensionButton = (RadioButton) findViewById(selectedId);
		if (hypertensionButton.getText().toString().equals("Yes"))
			this.accountController.setProfileHypertension(true);
		else
			this.accountController.setProfileHypertension(false);
		// Toast.makeText(Profile.this, radioSexButton.getText(),
		// Toast.LENGTH_SHORT).show();
	}

	private void setDiabetes() {

		int selectedId = diabetesRadioGroup.getCheckedRadioButtonId();
		RadioButton diabetesButton = (RadioButton) findViewById(selectedId);
		if (diabetesButton.getText().toString().equals("Yes"))
			this.accountController.setProfileDiabets(true);
		else
			this.accountController.setProfileDiabets(false);
		// Toast.makeText(Profile.this, radioSexButton.getText(),
		// Toast.LENGTH_SHORT).show();
	}

	private void setInsomnia() {

		int selectedId = insomniaRadioGroup.getCheckedRadioButtonId();
		RadioButton insomniaButton = (RadioButton) findViewById(selectedId);
		if (insomniaButton.getText().toString().equals("Yes"))
			this.accountController.setProfileInsomnia(true);
		else
			this.accountController.setProfileInsomnia(false);
		// Toast.makeText(Profile.this, radioSexButton.getText(),
		// Toast.LENGTH_SHORT).show();
	}

	private void setCardio() {

		int selectedId = cardioRadioGroup.getCheckedRadioButtonId();
		RadioButton cardioButton = (RadioButton) findViewById(selectedId);
		if (cardioButton.getText().toString().equals(R.string.yes))
			this.accountController.setProfileCaradia(true);
		else
			this.accountController.setProfileCaradia(false);
		// Toast.makeText(Profile.this, radioSexButton.getText(),
		// Toast.LENGTH_SHORT).show();
	}

	private void setGender() {

		int selectedId = genderRadioGroup.getCheckedRadioButtonId();
		RadioButton radioSexButton = (RadioButton) findViewById(selectedId);
		if (selectedId == R.id.radio_profile_male)
			this.accountController.setProfileGender(true);
		else
			this.accountController.setProfileGender(false);
		Toast.makeText(Profile.this, radioSexButton.getText(), Toast.LENGTH_SHORT).show();

	}

	private void setName() {

		this.accountController.setProfileName(nameEditView.getText().toString());

	}

	private void setBirthDay() {
		birthDate = toDate(birthDateEditView.getText().toString());
		this.accountController.setProfileBirthDay(birthDate);

	}

	private void setHeight() {
		this.accountController.setProfileHeight(Integer.parseInt(heightEditView.getText().toString()));
	}

	private void setWeight() {

		this.accountController.setProfileWeight(Integer.parseInt(weightEditView.getText().toString()));
	}

	public Date toDate(String dateStr) {

		int dateStrLenWithoutDash = 8;
		int dateStrLenWithDash = 10;
		
		SimpleDateFormat inputDateFormat;
		
		if(dateStr.length() == dateStrLenWithoutDash)
			inputDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);
		else if(dateStr.length() == dateStrLenWithDash)
			inputDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		else
			return accountController.getAccount().getBirthDate();
			
		try {
			Date date = inputDateFormat.parse(dateStr);
			return date;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			Toast.makeText(this, "date or time format is incorrect", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
		return null;
	}

	public String getDate(Date date) {
		final SimpleDateFormat nextTimeFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		String dateStr = nextTimeFormat.format(date);
		return dateStr;
	}

	public OnClickListener getSubmitListener() {
		return new OnClickListener() {
			public void onClick(View v) {
				setName();
				setHeight();
				setWeight();
				setBirthDay();
				setGender();
				setHypertension();
				setCardio();
				setInsomnia();
				setDiabetes();

				switchAllEditToView();

				submit.setVisibility(View.GONE);
				cancel.setVisibility(View.GONE);
				accountController.setProfileNewUser(false);
			}
		};

	}

	public OnClickListener getCancelListener() {
		return new OnClickListener() {
			public void onClick(View v) {

				switchAllEditToView();
				submit.setVisibility(View.GONE);
				cancel.setVisibility(View.GONE);
				accountController.setProfileNewUser(false);
			}
		};
	}

}
