package com.jdc.book.app.models;

import java.util.List;

import javafx.scene.chart.PieChart.Data;

public class Summary {

	private String key;
	private int value;
	
	public Summary(String key, int value) {
		super();
		this.key = key;
		this.value = value;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	
	public Data data() {
		return new Data(key, value);
	}
	
	public static<T> List<Data> convert(List<T> list) {
		return null;
	}
}
