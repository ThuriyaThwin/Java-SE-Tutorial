package com.jdc.diceGame.game;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Dice implements GameItem{
	
	private List<DicePoint> points = Arrays.asList(DicePoint.values());

	@Override
	public void play() {
		Collections.shuffle(points);
	}

	@Override
	public int compare(GameItem item) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public DicePoint getPoint() {
		return points.get(0);
	}

}
