package com.jdc.book.app.models;

import java.util.List;

public interface SummaryModel {

	public enum Type { Category, Author }
	
	List<Summary> getSummary(Type type);
}
