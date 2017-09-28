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
		
		} else { return false; }
		
	}
	public String getNextEquation() {
		
		if (checkInit()) {
			
			//Can return exception
			return equations.pop();
		
		} else { return null; }
		
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
	
	public static boolean isValid(String equation) {
		
		if (equation == null || equation.length() == 0) { return false; }
		
		StringBuilder numberBuilder = new StringBuilder();
		
		VectorStack<Integer> nums = new VectorStack<>();
		VectorStack<Character> ops = new VectorStack<>();
		int openParens = 0;
		int closedParens = 0;
		
		//Deconstruct the equation, but don't equate anything.
		//equates anything to the right of a division.
		
		for(int i = 0; i < equation.length(); i++) {
			
			char currentChar = equation.charAt(i);
			
			CharType type = getCharType(currentChar);
			
			switch(type) {
			
			case NONE:
				
				//There's a character we don't recognize. It must be invalid.
				return false;
				
			case OPERATOR:
				
				if (currentChar == '(') { openParens++; }
				if (currentChar == ')') { closedParens++; }
				
				ops.push(currentChar);
				
				if (numberBuilder.length() > 0) {
					
					//The character we got isn't a number.
					//if we're recording a number, the number is now finished
					nums.push(Integer.parseInt(numberBuilder.toString()));
					numberBuilder.delete(0, numberBuilder.length());
					
				}
				
				break;
			
			case NUMBER:
				numberBuilder.append(currentChar);
				break;
				
			}
			
		}
		
		if (openParens != closedParens) { return false; }
		
		return true;
		
	}
	
	private enum CharType { NUMBER, OPERATOR, NONE }
	
	private static CharType getCharType(char character) {
		
		switch (character) {
		
			case ')':
			case '(':
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
	
}