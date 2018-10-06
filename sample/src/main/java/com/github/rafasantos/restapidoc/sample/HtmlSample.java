package com.github.rafasantos.restapidoc.sample;

import com.github.rafasantos.restapidoc.SpecificationFilter;
import com.github.rafasantos.restapidoc.SpecificationParser;
import io.restassured.RestAssured;

import java.io.*;
import java.util.stream.Collectors;

public class HtmlSample {

    public static void buildSample(String outputFile) {
        String javascript = readJavascript();
        String style = readStyle();
        String snippet = buildSnippet();
        String html = buildHtml(javascript, style, snippet);

        System.out.println(html);

        try (PrintWriter filePrinter = new PrintWriter(outputFile)) {
            filePrinter.write(html);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static String buildHtml(String javascript, String style, String snippet) {
        StringBuilder result = new StringBuilder("<HTML><HEAD>");
        result.append("<style>" + style + "</style>");
        result.append("<script type=\"text/javascript\">" + javascript + "</script>");
        result.append("</HEAD>");
        result.append(snippet);
        result.append("</HTML>");
        return result.toString();
    }

    private static String buildSnippet() {
        SpecificationFilter filter = new SpecificationFilter();
        RestAssured.given()
            .filter(filter)
            .header("headerKey", "headerValue")
            .formParam("formParamKey", "formParamValue")
            .post("http://www.mocky.io/v2/5a08a8cb3200000a0a138011");

        SpecificationParser result = new SpecificationParser(filter);
        return result.asHtmlSnippet();
    }

    private static String readJavascript() {
        InputStream inputStream = HtmlSample.class.getClassLoader().getResourceAsStream("snippet-script.js");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        return reader.lines().collect(Collectors.joining("\n"));
    }

    private static String readStyle() {
        InputStream inputStream = HtmlSample.class.getClassLoader().getResourceAsStream("snippet-style.css");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        return reader.lines().collect(Collectors.joining("\n"));
    }
}
