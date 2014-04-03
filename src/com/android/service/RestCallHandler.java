package com.android.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.entity.StringEntity;

import com.android.entity.RecomModel;
import com.google.gson.Gson;

import android.os.AsyncTask;
import android.util.Log;

public class RestCallHandler {

	private ResponseHandler callBackActivity;
	private String url;
	private String content;

	public RestCallHandler(ResponseHandler sendToActivity, String url, String content) {
		this.callBackActivity = sendToActivity;
		this.url = url;
		this.content = content;
	}

	public void handleResponse() {
		new HttpAsyncTask().execute(url);
	}

	private void processResponse(String jsonResponse) {

		callBackActivity.processResponse(jsonResponse);
	}

	private String getResponse(String url, String message) {

		HttpClient hc = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		String response = null;
		InputStream is = null;
		try {
			post.setEntity(new StringEntity(message, "UTF8"));
			post.setHeader("Content-type", "application/json");
			HttpResponse resp = hc.execute(post);

			if (resp != null) {

				is = resp.getEntity().getContent();

				if (is != null) {
					response = convertStreamToString(is);
				}
			}

			Log.d("Status line", "" + resp.getStatusLine().getStatusCode());
		} catch (Exception e) {
			return null;
		}
		return response;
	}

	private class HttpAsyncTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {

			return getResponse(url, content);
		}

		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) {
			processResponse(result);
		}
	}

	private static String convertStreamToString(InputStream is) throws IOException {
		if (is != null) {
			Writer writer = new StringWriter();

			char[] buffer = new char[1024];
			try {
				Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				int n;
				while ((n = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, n);
				}
			} finally {
				is.close();
			}
			return writer.toString();
		} else {
			return null;
		}
	}
}
