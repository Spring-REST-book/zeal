package com.dzone.albanoj2.zeal.test.system.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class MockHttpClient {
	
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	@Autowired
	private MockMvc mockMvc;

	public MockHttpClientResponse execute(MockHttpServletRequestBuilder action) throws Exception {
		
		MockHttpServletResponse response = mockMvc.perform(action)
			.andReturn()
			.getResponse();
		
		return new MockHttpClientResponse(response);
	}
	
	public MockHttpClientResponse get(String url) throws Exception {
		return execute(MockMvcRequestBuilders.get(url));
	}
	
	public MockHttpClientResponse post(String url, Object body) throws Exception {
		return execute(
			MockMvcRequestBuilders.post(url)
			.content(json(body))
			.contentType(MediaType.APPLICATION_JSON));
	}

	private static String json(Object value) throws JsonProcessingException {
		return MAPPER.writeValueAsString(value);
	}
	
	public MockHttpClientResponse put(String url, Object body) throws Exception {
		return execute(
			MockMvcRequestBuilders.put(url)
			.content(json(body))
			.contentType(MediaType.APPLICATION_JSON));
	}
	
	public MockHttpClientResponse delete(String url) throws Exception {
		return execute(MockMvcRequestBuilders.delete(url));
	}
}
