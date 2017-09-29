package com.wit.stackapp.conner;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import com.wit.stackapp.main.VectorStack;

public class EquationCollector {

	private VectorStack<String> equations;
	public boolean hasNextEquation() {
		
		if (checkInit()) {
		
			return !equations.isEmpty();
		
		} else { System.out.println("EQUATIONCOLLECTOR NOT INITIALIZED"); return false; }
		
	}
	public String getNextEquation() {
		
		if (checkInit()) {
			
			//Can return exception
			return equations.pop();
		
		} else { System.out.println("EQUATIONCOLLECTOR NOT INITIALIZED"); return null; }
		
	}
	
	private boolean initialized = false;
	private boolean checkInit() { return initialized; }
	
	public EquationCollector(String filepath) {
		
		equations = new VectorStack<String>();
		
		if (filepath == null) { System.out.println("FILEPATH CANNOT BE NULL"); }
		
		try {
			
			Scanner fileIn = new Scanner(new File(filepath));
		
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

		for (int i = 0; i < equation.length(); i++) {
			
			int openParens = 0;
			int closedParens = 0;
			
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
	
	public static void doUnitTests() {
		
		
		
	}
	
}