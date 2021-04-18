package com.dzone.albanoj2.zeal.test.system.client;

import java.util.function.Consumer;

import org.springframework.mock.web.MockHttpServletResponse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MockHttpClientResponse {

	private static final ObjectMapper MAPPER = new ObjectMapper();
	private final MockHttpServletResponse response;
	
	public MockHttpClientResponse(MockHttpServletResponse response) {
		this.response = response;
	}
	
	public boolean isSuccessful() {
		return getStatus() >= 200 && getStatus() < 300;
	}
	
	public MockHttpClientResponse thenIfSuccessful(Consumer<MockHttpClientResponse> handler) {
		
		if (isSuccessful()) {
			handler.accept(this);
		}
		
		return this;
	}
	
	public MockHttpClientResponse then(Consumer<MockHttpClientResponse> handler) {
		handler.accept(this);
		return this;
	}
	
	public <T> T getResponseBody(TypeReference<T> type) {
		try {
			return MAPPER.readValue(
				response.getContentAsString(), 
				type
			);
		}
		catch (Exception e) {
			throw new RuntimeException("Failed to convert response body.", e);
		}
	}
	
	public int getStatus() {
		return response.getStatus();
	}
}
