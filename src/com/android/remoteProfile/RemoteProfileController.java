package com.android.remoteProfile;

import com.android.service.ResponseHandler;
import com.android.service.RestCallHandler;
import com.google.gson.Gson;

public class RemoteProfileController {
	private static final String CHECK_REQUEST_URL = "";
	private static final String SEND_REQUEST_URL = "";
	private static final String APPROVE_REQUEST_URL = "";
	private static final String CHECK_REQUEST_STATUS_URL = "";
	private static final String UPLOAD_DATA_URL = "";
	private static final String DOWNLOAD_DATA_URL = "";

	private RemoteDataModel remotedata;
	private Gson gson;

	public RemoteProfileController() {
		gson = new Gson();
	}

	public void send_request(RemoteRequest request){
		SendRequestHandler sendRequestHandler = new SendRequestHandler();
		String content = gson.toJson(request);
		RestCallHandler sendRequest= new RestCallHandler(sendRequestHandler, SEND_REQUEST_URL, content);
		sendRequest.handleResponse();
	}

	class SendRequestHandler implements ResponseHandler {

		@Override
		public void processResponse(String response) {
			// TODO Auto-generated method stub

		}

	}

}
