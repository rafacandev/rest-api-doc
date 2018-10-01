package com.github.rafasantos.restapidoc.sample;

import com.github.rafasantos.restapidoc.SpecificationFilter;
import com.github.rafasantos.restapidoc.SpecificationParser;

import io.restassured.RestAssured;

public class App {
	public static void main(String[] args) {
		SpecificationFilter filter = new SpecificationFilter();
		RestAssured.given()
			.filter(filter)
			.header("headerKey", "headerValue")
			.formParam("formParamKey", "formParamValue")
			.post("http://www.mocky.io/v2/5a08a8cb3200000a0a138011");
		SpecificationParser parser = new SpecificationParser(filter);
		System.out.println(
				"----- Request -----\n"
				+ parser.requestAsCurl()
				+ "\n\n----- Response -----\n" 
				+ parser.responseAsText());
	}
}
