package com.github.rafasantos.restapidoc;

public class HtmlSnippetParser {

	private static int snippetContentCounter = 0;

	public static String buildHtmlSnippet(String summaryText, String plainText, String curl, String responseText) {
		int summaryCounter = ++snippetContentCounter;
		int plainTextCounter = ++snippetContentCounter;
		int curlCounter = ++snippetContentCounter;
		return
			"<div class=\"snippet\">\n" +
			"  <div class=\"snippet-labels\">\n" +
			"    <label for=\"snippet-content-" + summaryCounter + "\" onclick=\"onSnippetLabelClick(this)\">Summary</label>\n" +
			"    <label for=\"snippet-content-" + plainTextCounter + "\" onclick=\"onSnippetLabelClick(this)\">Plain Text</label>\n" +
			"    <label for=\"snippet-content-" + curlCounter + "\" onclick=\"onSnippetLabelClick(this)\">curl</label>\n" +
			"  </div>\n" +
			"  <div id=\"snippet-content-" + summaryCounter + "\" class=\"snippet-content active\">\n" +
			"    <pre>" + summaryText + "\n---- Response ----\n" + responseText + "</pre>\n" +
			"  </div>\n" +
			"  <div id=\"snippet-content-" + plainTextCounter + "\" class=\"snippet-content\">\n" +
			"    <pre>" + plainText + "\n---- Response ----\n" + responseText + "</pre>\n" +
			"  </div>\n" +
			"  <div id=\"snippet-content-" + curlCounter + "\" class=\"snippet-content\">\n" +
			"    <pre>" + curl + "\n---- Response ----\n" + responseText + "</pre>\n" +
			"  </div>\n" +
			"</div>";
	}

}
