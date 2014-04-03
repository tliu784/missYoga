package com.android.entity;

import java.util.Date;

import com.android.service.FileOperation;


import android.content.Context;

public class AccountController {

	private AccountModel account = new AccountModel();
	private static AccountController instance = null;
	private static final String FILENAME = "acountInfo.obj";
	private Context context;

	public AccountController() {

	}

	public void setTestAccout() {
		account.setEmail("ben123@gmail.com");
		account.setPassword("password");
		account.setUsername("username");
		account.setRemenber(false);
		save();
	}

	public static AccountController getInstance() {
		if (instance == null) {
			instance = new AccountController();
		}
		return instance;
	}

	public void init(Context context) {
		this.context = context;
		load();
	}

	public void setRemenber(boolean remenber) {
		this.account.setRemenber(remenber);
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

	public void setProfileHeight(String height) {
		this.account.setHeight(height);
		save();
	}

	public void setProfileWeight(String weight) {
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
