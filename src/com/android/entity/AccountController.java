package com.android.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.android.service.FileOperation;

import android.content.Context;

public class AccountController {

	private AccountModel account = new AccountModel();
	private static AccountController instance = null;
	private static final String FILENAME = "acountInfo.obj";
	private Context context;
	private boolean initialized = false;

	public AccountController() {

	}

	public void setTestAccout() {
		account.setEmail("benjamin.niu1990@gmail.com");
		account.setPassword("password");
		account.setUsername("username");
		account.setRemember(false);
		account.setName("Ben Niu");
		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss", Locale.CANADA);
		String dateInString = "31-08-1990 10:20:56";
		Date bday = null;
		try {
			bday = sdf.parse(dateInString);
		} catch (ParseException e) {

		}
		account.setBirthDate(bday);
		account.setHeight(178);
		account.setWeight(75);
		save();
	}

	public static AccountController getInstance() {
		if (instance == null) {
			instance = new AccountController();
		}
		return instance;
	}

	public void init(Context context) {
		if (!initialized) {
			this.context = context;
			load();
			initialized = true;
		}
	}

	public void setRemenber(boolean remenber) {
		this.account.setRemember(remenber);
		save();
	}

	public boolean isRemenbered() {
		return this.account.isRemenbered();
	}

	private void save() {
		FileOperation.save(account, FILENAME, context);
	}

	public void load() {
		AccountModel storedEvents = (AccountModel) FileOperation.read(FILENAME, context);
		if (storedEvents != null) {
			account = storedEvents;
		}
	}

	public AccountModel getAccount() {
		return account;
	}

	public void setAccountUserName(String username) {
		this.account.setUsername(username);
		save();
	}

	public void setAccountPassword(String password) {
		this.account.setPassword(password);
		save();
	}

	public void setAccountEmail(String email) {
		this.account.setEmail(email);
		save();
	}

	public void setProfileName(String name) {
		this.account.setName(name);
		save();
	}

	public void setProfileBirthDay(Date birthDay) {
		this.account.setBirthDate(birthDay);
		save();
	}

	public void setProfileHeight(int height) {
		this.account.setHeight(height);
		save();
	}

	public void setProfileWeight(int weight) {
		this.account.setWeight(weight);
		save();
	}

	public void setProfileHypertension(boolean hypertension) {
		this.account.setHypertension(hypertension);
		save();
	}

	public void setProfileDiabets(boolean diabetes) {
		this.account.setDiabetes(diabetes);
		save();
	}

	public void setProfileInsomnia(boolean insomnia) {
		this.account.setInsomnia(insomnia);
		save();
	}

	public void setProfileCaradia(boolean cardio) {
		this.account.setCardio(cardio);
		save();
	}

	public void setProfileGender(boolean gender) {
		this.account.setGender(gender);
		save();
	}

	public void setProfileNewUser(boolean newUser) {
		this.account.setNewUser(newUser);
		save();
	}

}
