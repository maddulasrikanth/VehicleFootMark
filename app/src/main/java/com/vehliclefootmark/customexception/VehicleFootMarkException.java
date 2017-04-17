package com.vehliclefootmark.customexception;

public class VehicleFootMarkException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private int mErrorCode;

	/**
	 * @constructor
	 * @param errorcode
	 */
	public VehicleFootMarkException(int errorcode) {
		mErrorCode = errorcode;
	}

	/**
	 * Return the error code
	 * 
	 * @return
	 */
	public int getErrorCode() {
		return mErrorCode;
	}

}
