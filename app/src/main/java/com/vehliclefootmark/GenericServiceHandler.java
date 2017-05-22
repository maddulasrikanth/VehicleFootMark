package com.vehliclefootmark;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonSyntaxException;
import com.vehliclefootmark.customexception.VehicleFootMarkException;
import com.vehliclefootmark.login.OnServiceRequestHandlerListener;
import com.vehliclefootmark.net.RequestPacket;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

public abstract class GenericServiceHandler extends AsyncTask<RequestPacket, Integer, Integer> {

	protected final static int RESPONSE_CODE_OK = 200;

	private String TAG = "GenericServiceHandler";
	private OnServiceRequestHandlerListener mListener;

	public GenericServiceHandler(Context context) {
		mListener = (OnServiceRequestHandlerListener) context;
	}

	@Override
	protected Integer doInBackground(RequestPacket... params) {
		int responseCode = RESPONSE_CODE_OK;
		try {

			processResult(executePostRequest(params[0]));


		} catch (JsonSyntaxException e) {
			responseCode = ErrorConstants.SOCKET_TIMEOUT_EXCEPTION;
		} catch (VehicleFootMarkException e) {
			responseCode = ErrorConstants.NETWORK_ERROR;
		}  catch (Exception e) {
			e.printStackTrace();
			responseCode = ErrorConstants.NETWORK_ERROR;
		}
		return responseCode;
	}

	private String executePostRequest(RequestPacket packet) throws IOException {
		HttpClient httpclient = new DefaultHttpClient();
		// specify the URL you want to post to
		HttpPost httppost = new HttpPost(packet.url);
			// create a list to store HTTP variables and their values
			List nameValuePairs = new ArrayList();
			Set<Entry<String, String>> entries = packet.paramters.entrySet();
			for (Entry<String, String> entry : entries) {
				nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				Log.i(TAG, "key::" + entry.getKey() + " value:: " + entry.getValue());
			}
			// add an HTTP variable and value pair
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			// send the variable and value, in other words post, to the URL
			HttpResponse response = httpclient.execute(httppost);
		return EntityUtils.toString(response.getEntity());
	}

	@Override
	protected void onPostExecute(Integer result) {
		if (result == RESPONSE_CODE_OK) {
			 //mListener.onResponseSuccess();
		} else {
			mListener.onResponseError(result);
		}
	}

	/**
	 * Process the result depend on specific handler
	 * 
	 * @param result
	 *            response from the server
	 * @throws JsonSyntaxException
	 */
	protected abstract void processResult(String result) throws JsonSyntaxException,
			VehicleFootMarkException;

}
