package com.github.rafasantos.restapidoc;

import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;

public class SpecificationParser {

	private SpecificationFilter filter;
	
	public SpecificationParser(SpecificationFilter specificationFilter) {
		this.filter = specificationFilter;
	}
	
	private FilterableRequestSpecification getRequest() {
		return filter.getFilterableRequestSpecification();
	}

	private Response getResponse() {
		return filter.getResponse();
	}
	
	public String requestAsCurl() {
		StringBuilder result = new StringBuilder();
		result.append(CurlParser.buildMethod(getRequest().getMethod()));
		result.append(CurlParser.buildHeaders(getRequest().getHeaders()));
		if (getRequest().getBody() != null) {
			result.append(CurlParser.buildBody(getRequest()));
		} else {
			result.append(CurlParser.buildFormParms(getRequest().getFormParams()));
		}
		result.append(CurlParser.escapeUrl(getRequest().getURI()));
		return result.toString();
	}

	public String responseAsText() {
		if (getResponse() == null || getResponse().getBody() == null) {
			return null;
		} else {
			return getResponse().getBody().asString();
		}
	}
	
}
