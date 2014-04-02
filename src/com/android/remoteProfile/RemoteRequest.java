package com.android.remoteProfile;

public class RemoteRequest {
	private String ownerEmail;
	private String requestorEmail;
	private boolean approved;
	public RemoteRequest(String ownerEmail, String requestorEmail) {
		super();
		this.ownerEmail = ownerEmail;
		this.requestorEmail = requestorEmail;
		this.approved = false;
	}
	public String getOwnerEmail() {
		return ownerEmail;
	}
	public void setOwnerEmail(String ownerEmail) {
		this.ownerEmail = ownerEmail;
	}
	public String getRequestorEmail() {
		return requestorEmail;
	}
	public void setRequestorEmail(String requestorEmail) {
		this.requestorEmail = requestorEmail;
	}
	public boolean isApproved() {
		return approved;
	}
	public void setApproved(boolean approved) {
		this.approved = approved;
	}
	
	
	
	
}
