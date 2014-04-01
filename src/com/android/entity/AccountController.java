package com.android.entity;

import java.io.Serializable;
import java.util.ArrayList;

import com.android.service.FileOperation;
import com.android.trend.RecordList;
import com.android.trend.RecordModel;

import android.content.Context;

public class AccountController implements Serializable{
	
	private AccountModel account = new AccountModel();
	private static AccountController instance = null;
	private static final String FILENAME = "acountInfo.obj";
	private Context context;
	
	public AccountController(){
		
	}
	
	public void setTestAccout(){
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
	
	public void setRemenber(boolean remenber){
		this.account.setRemenber(remenber);
		save();
	}
	
	public boolean isRemenbered(){
		return this.account.isRemenbered();
	}
	
	private void save() {
		FileOperation.save(account, FILENAME, context);	
	}
	
	public void load(){
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

	public void setAccountPassword(String password){
		this.account.setPassword(password);
		save();
	}
	
	public void setAccountEmail(String email){
		this.account.setEmail(email);
		save();
	}

}
