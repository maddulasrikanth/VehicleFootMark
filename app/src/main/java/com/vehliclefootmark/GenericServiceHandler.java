package com.vehliclefootmark;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map.Entry;
import java.util.Set;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonSyntaxException;
import com.vehliclefootmark.customexception.VehicleFootMarkException;
import com.vehliclefootmark.login.OnServiceRequestHandlerListener;
import com.vehliclefootmark.net.RequestPacket;

public abstract class GenericServiceHandler extends AsyncTask<RequestPacket, Integer, Integer> {

	protected final static int RESPONSE_CODE_OK = 200;
	private DefaultHttpClient client = null;
	private HttpParams params;
	private int CONNECTION_TIMEOUT = 60000; // 10seconds
	private int WAIT_RESPONSE_TIMEOUT = 60000;
	private HttpGet httpGET = null;
	private HttpResponse httpResponse;
	private HttpEntity httpEntity;
	private String response = null;
	private HttpPut httpPUT = null;

	private String TAG = "GenericServiceHandler";
	private OnServiceRequestHandlerListener mListener;

	public GenericServiceHandler(Context context) {
		mListener = (OnServiceRequestHandlerListener) context;
	}

	@Override
	protected Integer doInBackground(RequestPacket... params) {
		int responseCode = RESPONSE_CODE_OK;
		try {
			if (params[0].method.equals("GET")) {
				processResult(executeGETRequest(params[0]));
			} else {
				processResult(executePUTRequest(params[0]));
			}

		} catch (ConnectTimeoutException e) {
			responseCode = ErrorConstants.SOCKET_TIMEOUT_EXCEPTION;
		} catch (ClientProtocolException e) {
			responseCode = ErrorConstants.NETWORK_ERROR;
		} catch (IllegalArgumentException e) {
			responseCode = ErrorConstants.NETWORK_ERROR;
		} catch (IOException e) {
			responseCode = ErrorConstants.NETWORK_ERROR;
		}
		return responseCode;
	}

	@Override
	protected void onPostExecute(Integer result) {
		if (result == RESPONSE_CODE_OK) {
			// mListener.onResponseSuccess();
		} else {
			mListener.onResponseError(result);
		}
	}

	public String executeGETRequest(RequestPacket packet) throws ClientProtocolException,
			IOException, IllegalArgumentException, ConnectTimeoutException {
		try {
			client = new DefaultHttpClient();

			params = client.getParams();

			HttpConnectionParams.setConnectionTimeout(params, CONNECTION_TIMEOUT);
			HttpConnectionParams.setSoTimeout(params, WAIT_RESPONSE_TIMEOUT);
			HttpConnectionParams.setTcpNoDelay(params, true);

			httpGET = new HttpGet(packet.url);

			//if(Config.DEBUG)
				Log.d(TAG, "" + packet.url);

			/** Set the Headers */

			httpGET.setHeader("Accept", "application/json");
			httpGET.setHeader("appkey", "7yny97nmlLhppyxdC6gegeb3bzg2");
			httpGET.setHeader("ckey", "cKYRD+VtjH8GF+L+9C7zAzsDGJDpM7/V");

			/* Add the HTTP headers and its values here */

			if (packet.paramters != null && packet.paramters.size() > 0) {
				Set<Entry<String, String>> entries = packet.paramters.entrySet();
				for (Entry<String, String> entry : entries) {
					httpGET.setHeader(entry.getKey(), entry.getValue());
					Log.i(TAG, "key::" + entry.getKey() + " value:: " + entry.getValue());
				}
			}

			Log.i(TAG, "Total headers:: " + httpGET.getAllHeaders().length);

			/** Make the Http call here */

			httpResponse = client.execute(httpGET);
			httpEntity = httpResponse.getEntity();

			Log.i(TAG, "status:: " + httpResponse.getStatusLine());

			if (httpEntity != null) {
				try {
					response = EntityUtils.toString(httpEntity);
					Log.d("IN_REST", response);

				} finally {
					httpEntity.consumeContent();
				}
			}

			/** Shut down HttpClient */

			if (client != null) {
				client.getConnectionManager().shutdown();
			}
		} catch (Exception e) {
			httpGET.abort();
			Log.e("REST_ERR", e.toString());
		}

		return response;
	}

	public String executePUTRequest(RequestPacket packet) throws ClientProtocolException,
			IOException, IllegalArgumentException, ConnectTimeoutException {
		try {
			client = new DefaultHttpClient();

			params = client.getParams();

			HttpConnectionParams.setConnectionTimeout(params, CONNECTION_TIMEOUT);
			HttpConnectionParams.setSoTimeout(params, WAIT_RESPONSE_TIMEOUT);
			HttpConnectionParams.setTcpNoDelay(params, true);

			httpPUT = new HttpPut(packet.url);

			Log.d(TAG, "" + packet.url);

			/** Set the Headers */

			httpPUT.setHeader("Accept", "application/json");
			httpPUT.setHeader("appkey", "7yny97nmlLhppyxdC6gegeb3bzg2");
			httpPUT.setHeader("ckey", "cKYRD+VtjH8GF+L+9C7zAzsDGJDpM7/V");

			/* Add the HTTP headers and its values here */

			if (packet.paramters != null && packet.paramters.size() > 0) {
				Set<Entry<String, String>> entries = packet.paramters.entrySet();
				for (Entry<String, String> entry : entries) {
					httpPUT.setHeader(entry.getKey(), entry.getValue());
					Log.i(TAG, "key::" + entry.getKey() + " value:: " + entry.getValue());
				}
			}
			Log.i(TAG, "Total headers:: " + httpPUT.getAllHeaders().length);

			/** Make the Http call here */

			httpResponse = client.execute(httpPUT);
			httpEntity = httpResponse.getEntity();

			Log.i(TAG, "status:: " + httpResponse.getStatusLine());

			if (httpEntity != null) {
				try {
					response = EntityUtils.toString(httpEntity);
					Log.d("IN_REST", response);

				} finally {
					httpEntity.consumeContent();
				}
			}

			/** Shut down HttpClient */

			if (client != null) {
				client.getConnectionManager().shutdown();
			}
		} catch (Exception e) {
			httpPUT.abort();
			Log.e("REST_ERR", e.toString());
		}

		return response;
	}

	private void trustEveryone() {
		try {
			HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			});
			SSLContext context = SSLContext.getInstance("TLS");
			context.init(null, new X509TrustManager[] { new X509TrustManager() {
				public void checkClientTrusted(X509Certificate[] chain, String authType)
						throws CertificateException {
				}

				public void checkServerTrusted(X509Certificate[] chain, String authType)
						throws CertificateException {
				}

				public X509Certificate[] getAcceptedIssuers() {
					return new X509Certificate[0];
				}
			} }, new SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * check with the response code
	 * 
	 * @param responseCode
	 *            response code got for the request
	 * @throws IOException
	 */
	public void checkResponseCode(int responseCode) throws IOException, VehicleFootMarkException {
		switch (responseCode) {
		case 200:
			// success
			break;
		case 404:
			throw new VehicleFootMarkException(ErrorConstants.URL_NOT_FOUND);
		case 401:
			throw new VehicleFootMarkException(ErrorConstants.AUTHENTICATION_ERROR);
		case 500:
		default:
			throw new IOException();
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
