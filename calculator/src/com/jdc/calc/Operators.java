package com.jdc.calc;

public enum Operators implements Calculatable{
	
	PLUS("+") {
		@Override
		public double calculate(double first, double second) {
			return first + second;
		}
		
	}, 
	MINUS("-") {
		@Override
		public double calculate(double first, double second) {
			return first - second;
		}
	}, 
	MULTIPLY("*") {
		@Override
		public double calculate(double first, double second) {
			return first * second;
		}
	}, 
	DIVIDED("/") {
		@Override
		public double calculate(double first, double second) {
			return first / second;
		}
	};
	
	private String ope;
	
	private Operators(String ope) {
		this.ope = ope;
	}
	
	public String getOpe() {
		return ope;
	}
	
	public static Calculatable getCalculator(String ope) {
		
		for(Operators o : Operators.values()) {
			if(o.getOpe().equals(ope)) {
				return o;
			}
		}
		
		return null;
	}
}
