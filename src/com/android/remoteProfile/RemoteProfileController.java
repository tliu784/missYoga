package com.android.remoteProfile;

import android.util.Log;

import com.android.service.ResponseHandler;
import com.android.service.RestCallHandler;
import com.google.gson.Gson;

public class RemoteProfileController implements ResponseHandler{
	private static final String SERVER_URL="http://1-dot-stayhealthyserver.appspot.com/getReco/";
	
	private static final String CHECK_REQUEST_URL = "getPermission/";
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

	public void check_request(RemoteRequestModel request){
		String content = gson.toJson(request);
		RestCallHandler sendRequest= new RestCallHandler(this, SERVER_URL+CHECK_REQUEST_URL, content);
		sendRequest.handleResponse();
	}

	@Override
	public void processResponse(String response) {
		
		Log.d("url",SERVER_URL+CHECK_REQUEST_URL);
		Log.d("response", response);
		
		
		ServerResponseModel serverResponse=gson.fromJson(response, ServerResponseModel.class);
		switch (serverResponse.getType()){
		case APPROVE_REQUEST:
			//do not have to process response
			break;
		case CHECK_REQUEST:
			Log.d("response", response);
			if (serverResponse.isSuccessful())
				Log.d("sucess","yes");
			//if response exists, popup requesting approval
			//if approved, send approve request
			break;
		case CHECK_REQUEST_STATUS:
			//if approved==false, do nothing
			//if approved==true, popup approval notice, download data, create new remote user
			break;
		case DOWNLOAD:
			//process payload and save remote user data
			break;
		case SEND_REQUEST:
			Log.d("response", response);
			break;
		case UPLOAD:
			//do not have to process response
			break;
		default:
			break;
		
		}
		
	}

	

}
