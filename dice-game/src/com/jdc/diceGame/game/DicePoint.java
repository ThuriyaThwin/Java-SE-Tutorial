package com.jdc.diceGame.game;

public enum DicePoint {
	
	One(1), Two(2), Three(3), Four(4), Five(5), Six(6);
	
	private int value;
	
	private DicePoint(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	public String getImageName() {
		return String.valueOf(value).concat(".png");
	}
}
