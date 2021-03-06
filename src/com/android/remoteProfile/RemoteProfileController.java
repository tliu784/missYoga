package com.android.remoteProfile;

import android.util.Log;

import com.android.service.ResponseHandler;
import com.android.service.RestCallHandler;
import com.google.gson.Gson;

public class RemoteProfileController implements ResponseHandler {
	private static final String SERVER_URL = "http://1-dot-stayhealthyserver.appspot.com/getReco";

	private static final String CHECK_REQUEST_URL = "/checkRequest";
	private static final String SEND_REQUEST_URL = "/saveRequest";
	private static final String APPROVE_REQUEST_URL = "/approveRequest";
	private static final String CHECK_REQUEST_STATUS_URL = "/checkRequestStatus";
	private static final String UPLOAD_DATA_URL = "/upload";
	private static final String DOWNLOAD_DATA_URL = "/download";

	private RemoteDataModel remotedata;
	private Gson gson;

	public RemoteProfileController() {
		gson = new Gson();
	}

	public void check_request(RemoteRequestModel request) {
		String content = gson.toJson(request);
		RestCallHandler sendRequest = new RestCallHandler(this, SERVER_URL + CHECK_REQUEST_URL, content);
		sendRequest.handleResponse();
	}

	public void send_request(RemoteRequestModel request) {
		String content = gson.toJson(request);
		RestCallHandler sendRequest = new RestCallHandler(this, SERVER_URL + SEND_REQUEST_URL, content);
		sendRequest.handleResponse();
	}

	public void approve_request(RemoteRequestModel request) {
		request.setApproved(true);
		String content = gson.toJson(request);
		RestCallHandler sendRequest = new RestCallHandler(this, SERVER_URL + APPROVE_REQUEST_URL, content);
		sendRequest.handleResponse();

	}
	
	public void check_request_status(RemoteRequestModel request) {
		String content = gson.toJson(request);
		RestCallHandler sendRequest = new RestCallHandler(this, SERVER_URL + CHECK_REQUEST_STATUS_URL, content);
		sendRequest.handleResponse();

	}
	
	

	@Override
	public void processResponse(String response) {


	
		ServerResponseModel serverResponse = gson.fromJson(response, ServerResponseModel.class);

		if (serverResponse.getType()!= null) {
			Log.d("response", response);
			switch (serverResponse.getType()) {
			case APPROVE_REQUEST:
				// do not have to process response
				Log.d("type","approve_request");
				break;
			case CHECK_REQUEST:
				Log.d("type","checkt_request");
				// if response exists, popup requesting approval
				// if approved, send approve request
				break;
			case CHECK_REQUEST_STATUS:
				Log.d("type","check_request_status");
				// if approved==false, do nothing
				// if approved==true, popup approval notice, download data,
				// create new remote user
				break;
			case DOWNLOAD:
				Log.d("type","download");
				// process payload and save remote user data
				break;
			case SEND_REQUEST:
				Log.d("type","send_request");

				break;
			case UPLOAD:
				Log.d("type","upload");

				// do not have to process response
				break;
			default:
				break;

			}
		}
	}

}
