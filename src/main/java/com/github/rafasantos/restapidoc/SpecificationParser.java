package com.github.rafasantos.restapidoc;

import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;

public class SpecificationParser {

	private SpecificationFilter filter;
	
	public SpecificationParser(SpecificationFilter specificationFilter) {
		this.filter = specificationFilter;
	}
	
	protected FilterableRequestSpecification getRequest() {
		return filter.getFilterableRequestSpecification();
	}

	protected Response getResponse() {
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
		result.append(CurlParser.escapeUrl(getRequest().getURI()) + "\n");
		return result.toString();
	}

	public String requestAsText() {
		StringBuilder result = new StringBuilder();
		result.append(getRequest().getMethod() + " ");
		result.append(getRequest().getURI() + "\n");
		result.append(TextParser.buildHeaders(getRequest().getHeaders()));
		if (getRequest().getBody() != null) {
			result.append(TextParser.buildBody(getRequest()));
		} else {
			result.append(TextParser.buildFormParms(getRequest().getFormParams()));
		}
		return result.toString();
	}

	public String responseAsText() {
		StringBuilder result = new StringBuilder(getResponse().statusLine() + "\n");
		result.append(ResponseParser.buildHeaders(getResponse().getHeaders()));
		if (getResponse() != null && getResponse().getBody() != null) {
			result.append("-- Body --\n");
			result.append(getResponse().getBody().asString());
		}
		return result.toString();
	}

    public String asHtmlSnippet() {
		String summaryTab = buildSummary();
		String plainTextTab = requestAsText();
		String curlTab = requestAsCurl();
		return HtmlSnippetParser.buildHtmlSnippet(summaryTab, plainTextTab, curlTab, responseAsText());
    }

	private String buildSummary() {
		return getRequest().getMethod() + " " + getRequest().getURI() + "\n" + getResponse().statusLine();
	}

}
