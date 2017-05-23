package com.vehliclefootmark.constants;

import android.annotation.SuppressLint;
import java.util.HashMap;
import java.util.Map;

@SuppressLint("UseSparseArrays")
public class ErrorConstants {

	public static final Map<Integer, String> ERROR_LIST;
	public static final int NETWORK_ERROR = 10001;

	static {
		ERROR_LIST = new HashMap<Integer, String>();
		ERROR_LIST.put(NETWORK_ERROR, "Unable to reach Server. Please try after some time.");
	}
}
