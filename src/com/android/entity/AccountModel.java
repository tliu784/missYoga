package com.android.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import com.android.recommendation.EngineInputModel.UserInfo;
import com.android.recommendation.EngineInputModel.WeightData;

public class AccountModel implements Serializable {
	private static final long serialVersionUID = -1430163807755003319L;
	private String username;
	private String password;
	private String email;
	private String name;
	private Date birthDate = new Date();
	private int height = 0;
	private int weight = 0;
	private boolean Hypertension = false;
	private boolean Diabetes = false;
	private boolean insomnia = false;
	private boolean cardio = false;
	private boolean genderMale = false;
	private boolean remenber = false;
	private boolean newUser = true;

	public AccountModel() {

	}

	public UserInfo getUserInfo() {
		UserInfo userinfo = new UserInfo();
		{
			userinfo.setAge(getAge());
			if (genderMale)
				userinfo.setGender("male");
			else
				userinfo.setGender("female");
			userinfo.setCardio(cardio);
			userinfo.setDiabetes(Diabetes);
			userinfo.setHypertension(Hypertension);
			userinfo.setInsomnia(insomnia);
			userinfo.setHeight(height);
		}
		int weightcount = 3;
		for (int i = 0; i < weightcount; i++) {
			userinfo.getWeight().add(new WeightData(new Date(), 40));
		}
		return userinfo;
	}

	private int getAge() {
		Calendar dob = Calendar.getInstance();
		dob.setTime(birthDate);
		Calendar today = Calendar.getInstance();
		int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
		if (today.get(Calendar.MONTH) < dob.get(Calendar.MONTH)) {
			age--;
		} else if (today.get(Calendar.MONTH) == dob.get(Calendar.MONTH)
				&& today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)) {
			age--;
		}
		return age;
	}

	public AccountModel(String username, String password, String email) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.remenber = false;
	}

	public boolean isNewUser() {
		return newUser;
	}

	public void setNewUser(boolean newUser) {
		this.newUser = newUser;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public boolean isHypertension() {
		return Hypertension;
	}

	public void setHypertension(boolean hypertension) {
		Hypertension = hypertension;
	}

	public boolean isDiabetes() {
		return Diabetes;
	}

	public void setDiabetes(boolean diabetes) {
		Diabetes = diabetes;
	}

	public boolean isInsomnia() {
		return insomnia;
	}

	public void setInsomnia(boolean insomnia) {
		this.insomnia = insomnia;
	}

	public boolean isCardio() {
		return cardio;
	}

	public void setCardio(boolean cardio) {
		this.cardio = cardio;
	}

	public boolean isGender() {
		return genderMale;
	}

	public void setGender(boolean gender) {
		this.genderMale = gender;
	}

	public void setRemember(boolean remenber) {
		this.remenber = remenber;
	}

	public boolean isRemenbered() {
		return this.remenber;
	}

	public AccountModel(String username) {
		this.username = username;
	}

	public boolean checkLogin(String user, String pass) {
		return (username.equals(user) && password.equals(pass));
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
