package com.github.rafasantos.restapidoc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import io.restassured.http.Header;
import io.restassured.http.Headers;

public class ResponseParserTest {

	@Test
	public void shouldParseHeaders() {
		Headers headers = new Headers(
				new Header("headerName1", "headerValue1"), 
				new Header("headerName2", "headerValue2"));
		String actualResult = ResponseParser.buildHeaders(headers);
		String expectedResult = "-- Headers:\n   headerName1: headerValue1\n"
				+ "   headerName2: headerValue2\n";
		assertEquals(expectedResult, actualResult);
	}
	
}
