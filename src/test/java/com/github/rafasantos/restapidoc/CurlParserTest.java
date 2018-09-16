package com.github.rafasantos.restapidoc;

import static org.junit.Assert.assertEquals;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;

public class CurlParserTest {
	
	@Test
	public void shouldBuildGetMethod() {
		String actualResult = CurlParser.buildMethod("GET"); 
		String expectedResult = "curl -v \\\n";
		assertEquals(expectedResult, actualResult);
	}

	@Test
	public void shouldBuildPostMethod() {
		String actualResult = CurlParser.buildMethod("POST"); 
		String expectedResult = "curl -v -X POST \\\n";
		assertEquals(expectedResult, actualResult);
	}

	@Test
	public void shouldBuildEmptyHeaders() {
		Headers headers = new Headers(new Header("Accept", "*/*"));
		String actualResult = CurlParser.buildHeaders(headers); 
		String expectedResult = "";
		assertEquals(expectedResult, actualResult);
	}

	@Test
	public void shouldBuildSingleHeader() {
		Headers headers = new Headers(new Header("headerKey", "headerValue"));
		String actualResult = CurlParser.buildHeaders(headers); 
		String expectedResult = "-H \"headerKey: headerValue\" \\\n";
		assertEquals(expectedResult, actualResult);
	}

	@Test
	public void shouldBuildMultipleHeaders() {
		Headers headers = new Headers(new Header("headerKey", "headerValue1"),new Header("headerKey", "headerValue2"));
		String actualResult = CurlParser.buildHeaders(headers); 
		String expectedResult = "-H \"headerKey: headerValue1\" \\\n-H \"headerKey: headerValue2\" \\\n";
		assertEquals(expectedResult, actualResult);
	}

	@Test
	public void shouldBuildEmptyFormParameters() {
		Map<String, String> formParameters = new LinkedHashMap<>();
		String actualResult = CurlParser.buildFormParms(formParameters); 
		String expectedResult = "";
		assertEquals(expectedResult, actualResult);
	}

	@Test
	public void shouldBuildSingleFormParameters() {
		Map<String, String> formParameters = new LinkedHashMap<>();
		formParameters.put("formParameterKey", "formParameterValue");
		String actualResult = CurlParser.buildFormParms(formParameters); 
		String expectedResult = "-F \"formParameterKey=formParameterValue\" \\\n";
		assertEquals(expectedResult, actualResult);
	}

	@Test
	public void shouldBuildMultipleFormParameters() {
		Map<String, String> formParameters = new LinkedHashMap<>();
		formParameters.put("formParameterKey1", "formParameterValue1");
		formParameters.put("formParameterKey2", "formParameterValue2");
		String actualResult = CurlParser.buildFormParms(formParameters);
		String expectedResult = "-F \"formParameterKey1=formParameterValue1\" \\\n" + 
				"-F \"formParameterKey2=formParameterValue2\" \\\n";
		assertEquals(expectedResult, actualResult);
	}
	
	@Test
	public void shouldBuildEmptyBody() {
		SpecificationFilter filter = TestUtil.buildBasicGetRequestFilter();
		String actualResult = CurlParser.buildBody(filter.getFilterableRequestSpecification());
		assertEquals("", actualResult);
	}

	@Test
	public void shouldBuildBodyWithDataString() {
		SpecificationFilter filter = new SpecificationFilter();
		RestAssured.given().filter(filter).body("Body").post(TestUtil.URL);
		String actualResult = CurlParser.buildBody(filter.getFilterableRequestSpecification());
		assertEquals("-d \"Body\" \\\n", actualResult);
	}

	@Test
	public void shouldBuildBodyWithDataObject() {
		SpecificationFilter filter = new SpecificationFilter();
		Header data = new Header("key", "value"); // Could be any java bean, using header for convenience 
		RestAssured.given().filter(filter).contentType(ContentType.JSON).body(data).post(TestUtil.URL);
		String actualResult = CurlParser.buildBody(filter.getFilterableRequestSpecification());
		assertEquals("-d \"{\\\"name\\\":\\\"key\\\",\\\"value\\\":\\\"value\\\"}\" \\\n", actualResult);
	}
	
}
