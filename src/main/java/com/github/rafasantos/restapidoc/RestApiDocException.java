package com.github.rafasantos.restapidoc;

public class RestApiDocException extends RuntimeException {

	private static final long serialVersionUID = 8735738782674238896L;

	public RestApiDocException(String message, Exception exception) {
		super(message, exception);
	}

}
