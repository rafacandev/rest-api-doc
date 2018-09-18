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
	
}
