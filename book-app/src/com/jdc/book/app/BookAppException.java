package com.jdc.book.app;

public class BookAppException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private boolean needToAlart;
	
	public BookAppException(String message, boolean needToAlart) {
		super(message);
		this.needToAlart = needToAlart;
	}
	
	public boolean isNeedToAlart() {
		return needToAlart;
	}

}
