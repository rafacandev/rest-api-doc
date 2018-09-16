package com.github.rafasantos.restapidoc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class RequestFilter implements Filter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RequestFilter.class);
	private FilterableRequestSpecification filterableRequestSpecification;

	@Override
	public Response filter(
			FilterableRequestSpecification requestSpecification,
			FilterableResponseSpecification responseSpecification,
			FilterContext filterContext) {
		
		this.filterableRequestSpecification = requestSpecification;
		
		if (LOGGER.isDebugEnabled()) {
			StringBuilder loggingText = new StringBuilder();
			loggingText.append("basePath:"		+ requestSpecification.getBasePath() + "\n");
			loggingText.append("baseUri: "		+ requestSpecification.getBaseUri() + "\n");
			loggingText.append("contentType: "	+ requestSpecification.getContentType() + "\n");
			loggingText.append("derivedPath: "	+ requestSpecification.getDerivedPath() + "\n");
			loggingText.append("method: "		+ requestSpecification.getMethod() + "\n");
			loggingText.append("port: "			+ requestSpecification.getPort() + "\n");
			loggingText.append("headers: "		+ requestSpecification.getHeaders() + "\n");
			loggingText.append("requestParams(): " + requestSpecification.getRequestParams() + "\n");
			loggingText.append("queryParams(): " + requestSpecification.getQueryParams() + "\n");
			if (requestSpecification.getBody() != null) {
				loggingText.append("body: " + requestSpecification.getBody().toString());
			}
			LOGGER.trace(loggingText.toString());
		}
		
		return filterContext.next(requestSpecification, responseSpecification);
	}
	
	public FilterableRequestSpecification getFilterableRequestSpecification() {
		return filterableRequestSpecification;
	}

}
