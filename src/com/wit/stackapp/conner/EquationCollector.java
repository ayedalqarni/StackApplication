package com.wit.stackapp.conner;

import com.wit.stackapp.main.VectorStack;

public class EquationCollector {

	private VectorStack<String> equations;
	public boolean hasNextEquation() {
		
		return equations.isEmpty();
		
	}
	public String getNextEquation() {
		
		//Can return exception
		return equations.pop();
		
	}
	
	private boolean initialized = false;
	private boolean checkInit() { return initialized; }
	
	public EquationCollector(String filepath) {
		
		if (filepath == null) {
				
			System.out.println("CAN'T FIND FILE: " + filepath);
		
		}
		
		equations = new VectorStack<String>();
		
		initialized = true;
		
	}
	
}