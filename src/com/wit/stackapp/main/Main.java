package com.wit.stackapp.main;
import com.wit.stackapp.conner.CalculatorGui;
import com.wit.stackapp.conner.EquationCollector;

public class Main {

	public static final String TEST_FILE_PATH = "Infix Calculator Expressions - multi-digit with invalid expressions -- 2016-10-04 01.txt";
	
	public static void main(String[] args) {

		//CalculatorGui.startApplication(args);
		
		EquationCollector.doUnitTests();
		
	}

}
