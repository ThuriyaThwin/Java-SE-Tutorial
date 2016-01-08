package com.jdc.diceGame.user;

import java.io.Serializable;

import com.jdc.diceGame.game.DiceSet;

public abstract class Player implements Serializable{
	
	private static final long serialVersionUID = 1L;

	protected String name;
	protected int amount;
	transient protected DiceSet diceSet;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public int setBalance(int voteMoney) {
		this.amount += voteMoney;
		return this.amount;
	}
	public DiceSet getDiceSet() {
		
		if(null == diceSet) {
			diceSet = new DiceSet();
		}
		
		return diceSet;
	}
	public void setDiceSet(DiceSet diceSet) {
		this.diceSet = diceSet;
	}

}
