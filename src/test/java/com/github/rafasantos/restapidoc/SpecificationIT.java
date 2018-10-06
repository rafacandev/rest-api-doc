package com.github.rafasantos.restapidoc;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

public class SpecificationIT {
	
	@Test
	public void shouldGetBasicRequestAsCurl() {
		SpecificationFilter filter = TestUtil.buildBasicGetRequestFilter();
		SpecificationParser fixture = new SpecificationParser(filter);
		String actualResult = fixture.requestAsCurl();
		String expectedResult = "curl -v \\\n"
				+ "http://www.mocky.io/v2/5a08a8cb3200000a0a138011\n";
		assertEquals(expectedResult, actualResult);
	}

	@Test
	public void shouldPostRequestAsCurl() {
		SpecificationFilter filter = new SpecificationFilter();
		RestAssured.given().filter(filter).header("headerKey", "headerValue").formParam("formParamKey", "formParamValue").post(TestUtil.URL);
		SpecificationParser fixture = new SpecificationParser(filter);
		String actualResult = fixture.requestAsCurl();
		String expectedResult = "curl -v -X POST \\\n" +
				"-H \"headerKey: headerValue\" \\\n" +
				"-H \"Content-Type: application/x-www-form-urlencoded; charset=ISO-8859-1\" \\\n" +
				"-F \"formParamKey=formParamValue\" \\\n" +
				"http://www.mocky.io/v2/5a08a8cb3200000a0a138011\n";
		assertEquals(expectedResult, actualResult);
	}

	@Test
	public void shouldGetBasicRequestAsText() {
		SpecificationFilter filter = TestUtil.buildBasicGetRequestFilter();
		SpecificationParser fixture = new SpecificationParser(filter);
		String actualResult = fixture.requestAsText();
		assertEquals("GET http://www.mocky.io/v2/5a08a8cb3200000a0a138011\n", actualResult);
	}

	@Test
	public void shouldGetBasicHtmlSnippet() throws Exception {
		String summary = "GET http://test.com\nOK 200";
		String plainRequest = "Request Tab";
		String curlRequest = "Curl Request";
		String textResponse = "Text Response";

		SpecificationParser fixture = spy(new SpecificationParser(null));

		doReturn(plainRequest).when(fixture).requestAsText();
		doReturn(curlRequest).when(fixture).requestAsCurl();
		doReturn(textResponse).when(fixture).responseAsText();

		FilterableRequestSpecification request = mock(FilterableRequestSpecification.class);
		doReturn("GET").when(request).getMethod();
		doReturn("http://test.com").when(request).getURI();
		doReturn(request).when(fixture).getRequest();

		Response response = mock(Response.class);
		doReturn("OK 200").when(response).statusLine();
		doReturn(response).when(fixture).getResponse();

		String actualResult = fixture.asHtmlSnippet();
		String expectedResult = TestUtil.buildHtmlSnippet(summary, plainRequest, curlRequest, textResponse);
		assertEquals(expectedResult, actualResult);
	}

}
