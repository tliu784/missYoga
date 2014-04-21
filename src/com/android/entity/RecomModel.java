package com.android.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Locale;



public class RecomModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3505325605382133867L;
	private int id;
	private String recommendation;
	private String url;
	private int severity;
	
	public RecomModel(){
		
	}
	
	public RecomModel(MedicineModel missedMed, boolean isQuestion){
		String recommendation=missedMed.getMissedText(isQuestion);
		this.id=1001;
		this.url="";
		this.recommendation=recommendation;
		this.severity=5;
	}
	
	public int getSeverity() {
		return severity;
	}
	public void setSeverity(int severity) {
		this.severity = severity;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRecommendation() {
		return recommendation;
	}
	public void setRecommendation(String recommendation) {
		this.recommendation = recommendation;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
	

	
}
