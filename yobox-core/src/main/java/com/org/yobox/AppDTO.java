package com.org.yobox;

import java.io.Serializable;
import java.util.Map;

import com.org.yobox.common.ResponseStatus;



public class AppDTO implements Serializable {
	

	private Map dataMap;
	
	

	private String errorMessage;

	private String infoMessage;
	
	private ResponseStatus responseStatus;
	
	
	
	/* getters and setters*/
	
	

	public ResponseStatus getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(ResponseStatus responseStatus) {
		this.responseStatus = responseStatus;
	}

	public Map getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map dataMap) {
		this.dataMap = dataMap;
	}

	
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getInfoMessage() {
		return infoMessage;
	}

	public void setInfoMessage(String infoMessage) {
		this.infoMessage = infoMessage;
	}



	
}
