package com.wit.stackapp.main;
import com.wit.stackapp.conner.CalculatorGui;
import com.wit.stackapp.conner.EquationCollector;

public class Main {

	public static void main(String[] args) {
		
		//CalculatorGui.startApplication(args);

		EquationCollector ec = new EquationCollector("Infix Calculator Expressions - multi-digit with invalid expressions -- 2016-10-04 01.txt");
		
		while (ec.hasNextEquation()) {
			
			String equation = ec.getNextEquation();
			
			if (EquationCollector.isValid(equation)) {
				
				System.out.println(equation + " is valid");
				
			} else { System.out.println(equation + " is not valid"); }
			
		}
		
	}

}
