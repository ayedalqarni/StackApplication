package com.wit.stackapp.conner;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import com.wit.stackapp.main.UnitTester;
import com.wit.stackapp.main.VectorStack;

public class EquationCollector {

	private VectorStack<String> equations;
	
	/**
	 * Checks whether there is another equation
	 * available in the equation stack.
	 * 
	 * @return Whether there is another equation available
	 */
	public boolean hasNextEquation() {
		
		if (checkInit()) {
		
			return !equations.isEmpty();
		
		} else { System.out.println("EQUATIONCOLLECTOR NOT INITIALIZED"); return false; }
		
	}
	
	/**
	 * Gets the next available equation.
	 * 
	 * Can throw a stack exception if a get is
	 * attempted while equation stack is empty.
	 * 
	 * @return The next equation
	 */
	public String getNextEquation() {
		
		if (checkInit()) {
			
			//Can return exception
			return equations.pop();
		
		} else { System.out.println("EQUATIONCOLLECTOR NOT INITIALIZED"); return null; }
		
	}
	
	private boolean initialized = false;
	private boolean checkInit() { return initialized; }
	
	/**
	 * Constructs a new EquationCollector, collecting equations line
	 * by line from a file in the designated file path
	 * 
	 * @param filepath The path to the file to get equations from
	 */
	public EquationCollector(String filepath) {
		
		equations = new VectorStack<String>();
		
		if (filepath == null) { System.out.println("FILEPATH CANNOT BE NULL"); return; }
		
		try {
			
			File f = new File(filepath);
			
			Scanner fileIn = new Scanner(f);
		
			while (fileIn.hasNextLine()) {
				
				//Assumes each line of the file is a separate
				//equation. Validity can be checked by isValid()
				equations.push(fileIn.nextLine());
				
			}
			
			fileIn.close();
			
		} catch (FileNotFoundException e) {
			
			System.out.println("EQUATION COLLECTOR CAN'T FIND FILE: " + filepath);
		
		}
		
		initialized = true;
		
	}
	
	/**
	 * Checks if the given infix equation is syntactically correct.
	 * 
	 * Does not check for arithmetic errors.
	 * 
	 * @param equation - The infix equation to check.
	 * @return Whether the equation is syntactically correct.
	 */
	public static boolean isValid(String equation) {
		
		if (equation == null || equation.length() == 0) { return false; }

		VectorStack<Character> equationChars = new VectorStack<>();

		int openParens = 0;
		int closedParens = 0;
		
		for (int i = 0; i < equation.length(); i++) {
			
			char nextChar = 0;
			char prevChar = 0;
			
			char currentChar = equation.charAt(i);
			
			if (i + 1 < equation.length()) { nextChar = equation.charAt(i + 1); }
			
			if (!equationChars.isEmpty()) { prevChar = equationChars.peek(); }
			
			switch(getCharType(currentChar)) {
			
			case NUMBER:
				
				break;
				
			case OPERATOR:
				
				if ((getCharType(prevChar) != CharType.NUMBER && prevChar != ')')
						|| getCharType(nextChar) != CharType.NUMBER && nextChar != '(') { return false; }
				
				break;
				
				
			case PARENTHESIS:
			
				if (prevChar == '(' && currentChar == ')') { return false; }
				
				if (currentChar == '(') { openParens++; }
				
				if (currentChar == ')' && ++closedParens > openParens) { return false; }
				
				break;
				
			default:
				return false;
			
			}
			
			equationChars.push(currentChar);
			
		}
		
		if (closedParens != openParens) { return false; }
		
		return true;
		
	}
	
	private enum CharType { NUMBER, OPERATOR, PARENTHESIS, NONE }
	
	private static CharType getCharType(char character) {
		
		switch (character) {
		
			case ')':
			case '(':
				return CharType.PARENTHESIS;
						
			case '+':
			case '-':
			case '/':
			case '*':
				return CharType.OPERATOR;
			
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				return CharType.NUMBER;
				
			default:
				return CharType.NONE;
		
		}
		
	}
	
	/**
	 * Conducts unit tests. Prints directly to the console.
	 */
	public static void doUnitTests() {
		
		testContructor();
		
		testValidityChecking();
		
	}

	private static void testContructor() {
		
		UnitTester.printTestDescription("Getting Equations: normal file");
		
		EquationCollector collectorOne =  new EquationCollector("src/com/wit/stackapp/conner/test.txt");
		
		StringBuilder sb = new StringBuilder();
		
		while(collectorOne.hasNextEquation()) {
			
			sb.append(collectorOne.getNextEquation() + " ");
			
		}
		
		UnitTester.printResult("item6 item5 item4 item3 item2 item1 ", sb);
		
		
		UnitTester.printTestDescription("Getting Equations: null filepath");
		
		System.out.println("Expected: FILEPATH CANNOT BE NULL");
		
		EquationCollector collectorTwo = new EquationCollector(null);
		
		UnitTester.printTestDescription("Getting Equations: collector isn't initialized");
		System.out.println("Expected: EQUATIONCOLLECTOR NOT INITIALIZED");
		collectorTwo.getNextEquation();
		
		
		UnitTester.printTestDescription("Getting Equations: empty file");
		EquationCollector collectorThree = new EquationCollector("src/com/wit/stackapp/conner/EmptyFile.txt");
		
		sb = new StringBuilder("[]");
		
		while(collectorThree.hasNextEquation()) {
			
			sb.append(collectorThree.getNextEquation() + " ");
			
		}
		
		UnitTester.printResult("[]", sb.toString());
		
		
		UnitTester.printTestDescription("Getting Equations: A non-existant filepath");
		
		System.out.println("Expected: \tEQUATION COLLECTOR CAN'T FIND FILE: src/com/wit/stackapp/conner/unicorn.txt");
		@SuppressWarnings("unused")
		EquationCollector collectorFour = new EquationCollector("src/com/wit/stackapp/conner/unicorn.txt");
		
	}
	
	private static void testValidityChecking() {
		
		UnitTester.printTestDescription("File with only invalid expressions");
		
		int totalEquations = 0;
		int invalidExpressions = 0;
		
		EquationCollector collectorOne = new EquationCollector("src/com/wit/stackapp/conner/invalidOnly.txt");
		
		while(collectorOne.hasNextEquation()) {
			
			totalEquations++;
			
			if (!isValid(collectorOne.getNextEquation())) {
				
				invalidExpressions++;
				
			}
			
		}
		
		UnitTester.printResult("Total: 9 Invalid: 9", "Total: " + Integer.toString(totalEquations) + " Invalid: " + Integer.toString(invalidExpressions));
		
		
		UnitTester.printTestDescription("File with only valid expressions");
		
		totalEquations = 0;
		int validExpressions = 0;
		
		EquationCollector collectorTwo = new EquationCollector("src/com/wit/stackapp/conner/validOnly.txt");
		
		while(collectorTwo.hasNextEquation()) {
			
			totalEquations++;
			
			String e = collectorTwo.getNextEquation();
			
			if (isValid(e)) {
				
				validExpressions++;
				
			}
			
		}
		
		UnitTester.printResult("Total: 36 Valid: 36", "Total: " + Integer.toString(totalEquations) + " Valid: " + Integer.toString(validExpressions));
		
		
		UnitTester.printTestDescription("File with mixed expressions");
		
		totalEquations = 0;
		validExpressions = 0;
		invalidExpressions = 0;
		
		EquationCollector collectorThree = new EquationCollector("src/com/wit/stackapp/conner/mixedValidInvalid.txt");
		
		while(collectorThree.hasNextEquation()) {
			
			totalEquations++;
			
			String e = collectorThree.getNextEquation();
			
			if (isValid(e)) {
				
				validExpressions++;
				
			} else {
				
				invalidExpressions++;
				
			}
			
		}
		
		UnitTester.printResult("Total: 46 Valid: 36 Invalid: 10", "Total: " + Integer.toString(totalEquations) + " Valid: " + Integer.toString(validExpressions) + " Invalid: " + Integer.toString(invalidExpressions));
		
	}
	
}