package com.android.entity;

import java.io.Serializable;

import com.android.service.FileOperation;

public class AccountModel implements Serializable{
	private static final long serialVersionUID = -1430163807755003319L;
	private String username;
	private String password;
	private String email;
	private boolean remenber;
	
	
	
	
	public AccountModel() {
	
	}
	
	public AccountModel(String username, String password, String email) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.remenber = false;
	}

	public void setRemenber(boolean remenber){
		this.remenber = remenber;
	}
	
	public boolean isRemenbered(){
		return this.remenber;
	}
	
	public AccountModel(String username){
		this.username=username;
	}
	
	public boolean checkLogin(String user, String pass){
		return (username.equals(user) && password.equals(pass) );
	}
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
