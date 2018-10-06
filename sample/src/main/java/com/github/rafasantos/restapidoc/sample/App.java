package com.github.rafasantos.restapidoc.sample;

public class App {

	public static void main(String[] args) {
		CurlSample.buildSample();
		HtmlSample.buildSample(args[0]);
	}

}
