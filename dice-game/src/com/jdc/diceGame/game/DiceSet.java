package com.jdc.diceGame.game;

public class DiceSet implements GameItem{
	
	private Dice d1;
	private Dice d2;
	
	public DiceSet() {
		d1 = new Dice();
		d2 = new Dice();
	}
	
	@Override
	public void play() {
		d1.play();
		d2.play();
	}
	
	@Override
	public int compare(GameItem item) {
		DiceSet other = (DiceSet)item;
		
		if(isSamePoints() && other.isSamePoints()) {

			DicePoint myPoint = d1.getPoint();
			DicePoint yourPoint = other.d1.getPoint();
			
			if(!myPoint.equals(yourPoint)) {
				
				if(myPoint == DicePoint.One) {
					return 1;
				}
				
				if(yourPoint == DicePoint.One) {
					return -1;
				}
				
				return myPoint.getValue() - yourPoint.getValue();
			} 
		} else if (isSamePoints() && !other.isSamePoints()) {
			return 1;
		} else if (!isSamePoints() && other.isSamePoints()) {
			return -1;
		} else {
			return getTotal() - other.getTotal();
		}
		
		return 0;
	}
	
	public Dice getD1() {
		return d1;
	}

	public Dice getD2() {
		return d2;
	}

	private boolean isSamePoints() {
		return d1.getPoint().equals(d2.getPoint());
	}
	
	private int getTotal() {
		return d1.getPoint().getValue() + d2.getPoint().getValue();
	}

}
