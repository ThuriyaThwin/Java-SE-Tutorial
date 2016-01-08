package com.jdc.cinema.dao;

public class DaoException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public DaoException(Exception e) {
		super(e);
	}

}
