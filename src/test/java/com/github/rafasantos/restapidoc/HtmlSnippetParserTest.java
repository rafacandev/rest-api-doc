package com.github.rafasantos.restapidoc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class HtmlSnippetParserTest {
	
	@Test
	public void shouldBuildHtmlSnippet() {
		String summaryText = "My summary";
		String plainText = "My plain text";
		String curl = "curl";
		String responseText = "Response text";
		String actualResult = HtmlSnippetParser.buildHtmlSnippet(summaryText, plainText, curl, responseText);
		String expectedResult = TestUtil.buildHtmlSnippet(summaryText, plainText, curl, responseText);
		assertEquals(expectedResult, actualResult);
	}

}
