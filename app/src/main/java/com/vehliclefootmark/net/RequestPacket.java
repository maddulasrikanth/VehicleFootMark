package com.vehliclefootmark.net;

import java.util.HashMap;

public class RequestPacket {
	
	public String url;
	public String method;
	public String body;
	// header key - value pair
	public HashMap<String, String> paramters;
	public long uid;

	public RequestPacket(String url, String method, HashMap<String, String> paramters, String body) {
		this.url = url;
		this.method = method;
		this.body = body;
		this.paramters = paramters;
		uid = System.currentTimeMillis();
	}

}
