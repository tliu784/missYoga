package com.android.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;



public class ProfileModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1528113211416967978L;
	private String name;
	private Date bday;
	private int weight;
	private int height;
	private boolean genderMale;
	private boolean hypertension = false;
    private boolean diabetes = false;
    private boolean insomnia = false;
    private boolean cardio = false;

	public ProfileModel(){
		
	}
    
    public ProfileModel(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getGender(){
		if (genderMale)
			return "Male";
		else
			return "Female";
	}
	
	public void setGenderMale(){
		genderMale=true;
	}
	
	public void setGenderFemale(){
		genderMale=false;
	}
	
	public int getAge(){
		Calendar dob = Calendar.getInstance();  
		dob.setTime(bday);  
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

	public void setBday(Date bday) {
		this.bday = bday;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean isHypertension() {
		return hypertension;
	}

	public void setHypertension(boolean hypertension) {
		this.hypertension = hypertension;
	}

	public boolean isDiabetes() {
		return diabetes;
	}

	public void setDiabetes(boolean diabetes) {
		this.diabetes = diabetes;
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
	
	
}
