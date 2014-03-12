package com.android.entity;

import java.io.Serializable;

public class AccountModel implements Serializable{
	private static final long serialVersionUID = -1430163807755003319L;
	private String username;
	private String password;
	private String email;
	
	public AccountModel() {
	
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
