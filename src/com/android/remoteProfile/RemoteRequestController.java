package com.android.remoteProfile;

import java.util.ArrayList;

public class RemoteRequestController {

	ArrayList<RemoteRequestModel> remoteUserList = new ArrayList<RemoteRequestModel>();
	
	
	public RemoteRequestController(){
		
	}
	
	public RemoteRequestController(int i){
		
		RemoteRequestModel model1 = new RemoteRequestModel("terry@gmail.com" , "ben@gmail.com");
		model1.setApproved(true);
		model1.setOwnerName("terry");
		
		remoteUserList.add(model1);
		
		RemoteRequestModel model2 = new RemoteRequestModel("nicole@gmail.com" , "ben@gmail.com");
		model2.setApproved(true);
		model2.setOwnerName("nicole");
		remoteUserList.add(model2);		
		
	}
	
	public String getEmailByName(String name){
		for(RemoteRequestModel requestUser: remoteUserList)
			if(requestUser.getOwnerName().equals(name))
				return requestUser.getOwnerEmail();
		
		return "null";	
	}
	
	
	public ArrayList<RemoteRequestModel> getRemoteUserList() {
		return remoteUserList;
	}

	public void setRemoteUserList(ArrayList<RemoteRequestModel> remoteUserList) {
		this.remoteUserList = remoteUserList;
	}
	
}
