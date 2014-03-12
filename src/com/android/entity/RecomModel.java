package com.android.entity;

import java.io.Serializable;



public class RecomModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3505325605382133867L;
	private int id;
	private String recommendation;
	private String url;
	

	
	
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
