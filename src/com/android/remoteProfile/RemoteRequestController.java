package com.android.remoteProfile;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

import com.android.entity.AccountController;

import com.android.myhealthmate.Settings;
import com.android.service.FileOperation;
import com.android.service.ResponseHandler;
import com.android.service.RestCallHandler;
import com.google.gson.Gson;

public class RemoteRequestController {

	ArrayList<RemoteRequestModel> remoteUserList = new ArrayList<RemoteRequestModel>();

	private static final String filename = "remoteuserlist.obj";
	private static RemoteRequestController instance = null;
	private Settings callbackAct;
	private Context context;
	private String accountEmail;
	private String accountName;
	private Gson gson;
	private boolean initialized = false;

	public void initSettings(Settings act) {
		this.callbackAct = act;
	}

	public void initContext(Context context) {
		if (!initialized) {
			this.context = context;
			accountEmail = AccountController.getInstance().getAccount().getEmail();
			accountName = AccountController.getInstance().getAccount().getName();
			gson = new Gson();
			load();
		}
		initialized = true;
	}

	public static RemoteRequestController getInstance() {
		if (instance == null)
			instance = new RemoteRequestController();
		return instance;
	}

	protected RemoteRequestController() {

	}

	public void createTestData() {
		remoteUserList.clear();
		if (remoteUserList.size() == 0) {

			RemoteRequestModel model1 = new RemoteRequestModel("daddy@gmail.com", accountEmail);
			model1.setRequestorName("Ben");
			model1.setApproved(true);
			model1.setOwnerName("Mr. Niu");

			remoteUserList.add(model1);

			RemoteRequestModel model3 = new RemoteRequestModel(accountEmail, "daughter@gmail.com");
			model3.setRequestorName("Lit.Ben");
			model3.setApproved(true);
			model3.setOwnerName("Ben");

			remoteUserList.add(model3);

			save();
		}
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
		RemoteRequestModel request = new RemoteRequestModel();
		request.setOwnerEmail(accountEmail);
		request.setOwnerName(accountName);
		String content = gson.toJson(request);
		CheckRequestResponseHandler handler = new CheckRequestResponseHandler();
		String url = ServerConstants.SERVER_URL + ServerConstants.CHECK_REQUEST_URL;
		RestCallHandler checkRequest = new RestCallHandler(handler, url, content);
		checkRequest.handleResponse();

	}

	public void check_request_status(RemoteRequestModel request) {
		String content = gson.toJson(request);
		CheckRequestStatusResponseHandler handler = new CheckRequestStatusResponseHandler(request);
		String url = ServerConstants.SERVER_URL + ServerConstants.CHECK_REQUEST_STATUS_URL;
		RestCallHandler checkRequestStatus = new RestCallHandler(handler, url, content);
		checkRequestStatus.handleResponse();
	}

	public void approve_request(RemoteRequestModel request) {
		request.setApproved(true);
		String content = gson.toJson(request);
		ApproveRequestResponseHandler handler = new ApproveRequestResponseHandler(request);
		String url = ServerConstants.SERVER_URL + ServerConstants.APPROVE_REQUEST_URL;
		RestCallHandler approveRequest = new RestCallHandler(handler, url, content);
		approveRequest.handleResponse();
	}

	private void load() {
		ArrayList<RemoteRequestModel> loaded;
		loaded = (ArrayList<RemoteRequestModel>) FileOperation.read(filename, context);
		if (loaded != null) {

			remoteUserList = loaded;
		}
	}

	private void save() {

		FileOperation.save(remoteUserList, filename, context);

	}

	public String getEmailByName(String name) {
		for (RemoteRequestModel requestUser : remoteUserList)
			if (requestUser.getOwnerName().equals(name))

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
			// testing
			Log.d("response", gson.toJson(serverResponse.getRequests()));
			Log.d("list", gson.toJson(remoteUserList));
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
								// update UI to display new requests
								// optionally pop up for approval
								Log.d("response", gson.toJson(serverResponse.getRequests()));
								Log.d("list", gson.toJson(remoteUserList));
							}
						} else {
							// no requests, do nothing
						}

					}
				}
		}
	}

	class CheckRequestStatusResponseHandler implements ResponseHandler {

		private RemoteRequestModel request;

		// make sure pass in the pointer to the request in the remoteUserList;
		public CheckRequestStatusResponseHandler(RemoteRequestModel request) {
			this.request = request;
		}

		@Override
		public void processResponse(String response) {
			boolean approved = false;
			RemoteRequestModel responseRequest = null;
			ServerResponseModel serverResponse = gson.fromJson(response, ServerResponseModel.class);
			if (serverResponse.getType() == ServerResponseModel.ResponseType.CHECK_REQUEST_STATUS)
				if (serverResponse.isSuccessful()) {
					if (serverResponse.getRequests() != null)
						responseRequest = serverResponse.getRequests().get(0);
					if (responseRequest != null)
						approved = responseRequest.isApproved();
				}
			if (approved) {
				// do something tell user request has been approved
				request.setApproved(approved);
				Log.d("check status", "approved");
			} else {
				// do nothing
				Log.d("check status", "not approved");
			}
		}

	}

	class ApproveRequestResponseHandler implements ResponseHandler {
		private RemoteRequestModel request;

		// make sure pass in the pointer to the request in the remoteUserList;
		public ApproveRequestResponseHandler(RemoteRequestModel request) {
			this.request = request;

		}

		@Override
		public void processResponse(String response) {
			ServerResponseModel serverResponse = gson.fromJson(response, ServerResponseModel.class);
			if (serverResponse.getType() == ServerResponseModel.ResponseType.APPROVE_REQUEST)
				if (serverResponse.isSuccessful()) {
					request.setApproved(true);
					// update UI set approved
					Log.d("approve request", "yeah");
					Log.d("list", gson.toJson(remoteUserList));
				} else {
					request.setApproved(false);
				}

		}

	}

}
