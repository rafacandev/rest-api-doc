package com.github.rafasantos.restapidoc;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import io.restassured.RestAssured;
import static org.mockito.Mockito.*;

public class SpecificationParserTest {
	
	@Test
	public void shouldGetBasicRequestAsCurl() {
		SpecificationFilter filter = TestUtil.buildBasicGetRequestFilter();
		SpecificationParser fixture = new SpecificationParser(filter);
		String actualResult = fixture.requestAsCurl();
		String expectedResult = "curl -v \\\n"
				+ "http://www.mocky.io/v2/5a08a8cb3200000a0a138011";
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
				"http://www.mocky.io/v2/5a08a8cb3200000a0a138011";
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
	public void shouldGetBasicRequestHtmlSnippet() {
		String textRequest = "Text Request";
		String curlRequest = "curl Request";
		String textResponse = "Text Response";

		SpecificationFilter filter = mock(SpecificationFilter.class);
		SpecificationParser fixture = spy(new SpecificationParser(filter));
		doReturn(textRequest).when(fixture).requestAsText();
		doReturn(curlRequest).when(fixture).requestAsCurl();
		doReturn(textResponse).when(fixture).responseAsText();

		String actualResult = fixture.requestHtmlSnippet();
		String expectedResult = TestUtil.buildHtmlSnippet(textRequest, textRequest, curlRequest, textResponse);
		assertEquals(expectedResult, actualResult);
	}

}
