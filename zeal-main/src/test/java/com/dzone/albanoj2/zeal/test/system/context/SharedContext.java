package com.dzone.albanoj2.zeal.test.system.context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.dzone.albanoj2.zeal.test.system.client.MockHttpClientResponse;

public class SharedContext<T> {

	private List<T> lastResponseBody = new ArrayList<>();
	private int lastResponseCode;

	public List<T> getLastResponseBody() {
		return lastResponseBody;
	}

	public void setLastResponseBody(List<T> responseBody) {
		this.lastResponseBody = responseBody;
	}
	
	public void setLastResponseBody(T responseBody) {
		this.lastResponseBody = Arrays.asList(responseBody);
	}

	public int getLastResponseCode() {
		return lastResponseCode;
	}

	public void setLastResponseCodeFrom(MockHttpClientResponse response) {
		this.lastResponseCode = response.getStatus();
	}
	
	public boolean nothingRetrieved() {
		return lastResponseBody == null || lastResponseBody.isEmpty();
	}
}
