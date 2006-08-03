package com.star.common.util.bean;

public class BeanException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public BeanException() {
	}
	
	public BeanException(Exception e) {
		super(e);
	}

	public BeanException(String message) {
		super(message);
	}

}
