package com.android.remoteProfile;

import java.util.ArrayList;

import android.content.Context;


import com.android.entity.AccountController;

import com.android.service.FileOperation;
import com.android.service.ResponseHandler;
import com.android.service.RestCallHandler;
import com.google.gson.Gson;

public class RemoteRequestController {

	ArrayList<RemoteRequestModel> remoteUserList = new ArrayList<RemoteRequestModel>();


	private static final String filename = "remoteuserlist.obj";
	private static RemoteRequestController instance = null;
//	private Settings callbackAct;
	private Context context;
	private final String accountEmail;
	private final String accountName;
	private Gson gson;

//	public void init(Settings act) {
//		this.callbackAct = act;
//		this.context = act;
//	}

	public static RemoteRequestController getInstance() {
		if (instance == null)
			instance = new RemoteRequestController();
		return instance;
	}

	protected RemoteRequestController() {

		accountEmail = AccountController.getInstance().getAccount().getEmail();
		accountName = AccountController.getInstance().getAccount().getName();
		gson = new Gson();
		load();
		// RemoteRequestModel model0 = new RemoteRequestModel("ben@gmail.com",
		// "ben@gmail.com");
		// model0.setApproved(true);
		// model0.setOwnerName("Ben");
		// remoteUserList.add(model0);
		//
		// RemoteRequestModel model1 = new RemoteRequestModel("terry@gmail.com",
		// "ben@gmail.com");
		// model1.setApproved(true);
		// model1.setOwnerName("Terry");
		//
		// remoteUserList.add(model1);
		//
		// RemoteRequestModel model2 = new
		// RemoteRequestModel("nicole@gmail.com", "ben@gmail.com");
		// model2.setApproved(true);
		// model2.setOwnerName("Nicole");
		// remoteUserList.add(model2);

	}

	public void send_request(String ownerEmail) {
		RemoteRequestModel request = new RemoteRequestModel();
		request.setRequestorEmail(accountEmail);
		request.setRequestorName(accountName);
		request.setApproved(false);
		request.setOwnerEmail(ownerEmail);
		request.setOwnerName("...");
		String content = gson.toJson(request);
		String url = ServerConstants.SERVER_URL + ServerConstants.SEND_REQUEST_URL;
		SendRequestResponseHandler handler = new SendRequestResponseHandler(request);
		RestCallHandler sendRequest = new RestCallHandler(handler, url, content);
		sendRequest.handleResponse();
	}

	public void check_request() {
		boolean requestExists = false;
		RemoteRequestModel request = new RemoteRequestModel();
		request.setOwnerEmail(accountEmail);
		request.setOwnerName(accountName);
		String content = gson.toJson(request);
		CheckRequestResponseHandler handler = new CheckRequestResponseHandler();
		String url = ServerConstants.SERVER_URL + ServerConstants.CHECK_REQUEST_URL;
		RestCallHandler checkRequest = new RestCallHandler(handler, url, content);
		checkRequest.handleResponse();

	}

	private void load() {
		ArrayList<RemoteRequestModel> loaded;
		loaded = (ArrayList<RemoteRequestModel>) FileOperation.read(filename, context);
		if (loaded != null)
			remoteUserList = loaded;
	}

	private void save() {
		FileOperation.save(remoteUserList, filename, context);
	}


	
	
	

	
	public String getEmailByName(String name){
		for(RemoteRequestModel requestUser: remoteUserList)
			if(requestUser.getOwnerName().equals(name))

				return requestUser.getOwnerEmail();
		return null;
	}

	private boolean ifRequestExist(RemoteRequestModel request) {
		String ownerEmail = request.getOwnerEmail();
		String requestorEmail = request.getRequestorEmail();

		for (RemoteRequestModel existingrequest : remoteUserList)
			if (existingrequest.getOwnerEmail().equalsIgnoreCase(ownerEmail)
					&& existingrequest.getRequestorEmail().equalsIgnoreCase(requestorEmail))
				return true;
		return false;
	}

	public ArrayList<RemoteRequestModel> getMinitoredRemoteUserList() {
		ArrayList<RemoteRequestModel> result = new ArrayList<RemoteRequestModel>();
		for (RemoteRequestModel request : remoteUserList) {
			if (request.getRequestorEmail().equalsIgnoreCase(accountEmail))
				result.add(request);
		}
		return result;
	}

	public ArrayList<RemoteRequestModel> getViewedByRemoteUserList() {
		ArrayList<RemoteRequestModel> result = new ArrayList<RemoteRequestModel>();
		for (RemoteRequestModel request : remoteUserList) {
			if (request.getOwnerEmail().equalsIgnoreCase(accountEmail))
				result.add(request);
		}
		return result;
	}

	// nested classes for handling responses

	class SendRequestResponseHandler implements ResponseHandler {
		private RemoteRequestModel request;

		private SendRequestResponseHandler(RemoteRequestModel request) {
			this.request = request;
		}

		@Override
		public void processResponse(String response) {

			ServerResponseModel serverResponse = gson.fromJson(response, ServerResponseModel.class);
			if (serverResponse.getType() == ServerResponseModel.ResponseType.SEND_REQUEST)
				if (serverResponse.isSuccessful())
					if (!ifRequestExist(request))
						remoteUserList.add(request);
			// callbackAct.updateUI
		}
	}

	class CheckRequestResponseHandler implements ResponseHandler {

		private ArrayList<RemoteRequestModel> viewRequests;

		private CheckRequestResponseHandler() {
		};

		@Override
		public void processResponse(String response) {
			ServerResponseModel serverResponse = gson.fromJson(response, ServerResponseModel.class);
			if (serverResponse.getType() == ServerResponseModel.ResponseType.CHECK_REQUEST)
				if (serverResponse.isSuccessful()) {
					viewRequests = serverResponse.getRequests();
					if (viewRequests != null) {
						if (viewRequests.size() > 0) {
							for (RemoteRequestModel viewRequest : viewRequests) {
								remoteUserList.add(viewRequest);
								//update UI to display new requests
								//optionally pop up for approval
							}
						}else{
							//no requests, do nothing
						}

					}
				}
		}
	}

}
