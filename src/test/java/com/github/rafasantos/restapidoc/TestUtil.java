package com.github.rafasantos.restapidoc;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

import io.restassured.RestAssured;

public class TestUtil {
	/**
	 * Useful URL used for testing
	 * 
	 * curl http://www.mocky.io/v2/5a08a8cb3200000a0a138011
	 * 
	 * {
	 *     "bookId": 1,
	 *     "title": "Documenting and testing APIs with Rest Doc Maker",
	 *     "tags": [
	 *         {"tagId": 1, "name": "Programming"},
	 *         {"tagId": 2, "name": "REST"}
	 *     ]
	 * }
	 * 
	 */
	public static final String URL = "http://www.mocky.io/v2/5a08a8cb3200000a0a138011";
	private static SpecificationFilter basicGetRequestSpecificationFilter;

	public static SpecificationFilter buildBasicGetRequestFilter() {
		if (basicGetRequestSpecificationFilter == null) {
			SpecificationFilter result = new SpecificationFilter();
			RestAssured.given().filter(result).get(URL).then()
				.body("bookId", equalTo(1))
				.and().body("tags.tagId", hasItems(1, 2))
				.and().body("tags.name", hasItems("Programming", "REST"));
			
			basicGetRequestSpecificationFilter = result;
			return result;
		} else {
			return basicGetRequestSpecificationFilter;
		}
	}

	public static String buildHtmlSnippet(String summaryText, String plainText, String curl, String response) {
		return
			"<div class=\"snippet\">\n" +
			"  <div class=\"snippet-labels\">\n" +
			"    <label for=\"snippet-content-1\" onclick=\"onSnippetLabelClick(this)\">Summary</label>\n" +
			"    <label for=\"snippet-content-2\" onclick=\"onSnippetLabelClick(this)\">Plain Text</label>\n" +
			"    <label for=\"snippet-content-3\" onclick=\"onSnippetLabelClick(this)\">curl</label>\n" +
			"  </div>\n" +
			"  <div id=\"snippet-content-1\" class=\"snippet-content active\">\n" +
			"    <pre>" + summaryText + "\n---- Response ----\n" + response + "</pre>\n" +
			"  </div>\n" +
			"  <div id=\"snippet-content-2\" class=\"snippet-content\">\n" +
			"    <pre>" + plainText + "\n---- Response ----\n" + response + "</pre>\n" +
			"  </div>\n" +
			"  <div id=\"snippet-content-3\" class=\"snippet-content\">\n" +
			"    <pre>" + curl + "\n---- Response ----\n" + response + "</pre>\n" +
			"  </div>\n" +
			"</div>";
	}

}
