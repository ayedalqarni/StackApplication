package com.wit.stackapp.main;

/**
 * A class with static functions to streamline unit testing
 * 
 * @author Conner Theberge
 *
 */
public class UnitTester {

	/**
	 * Prints a formatted version of the given string on a single line
	 * after a single newline
	 * 
	 * ex: "--<title>--"
	 * 
	 * @param title The title of the unit test
	 */
	public static void printTestDescription(String title) {
		
		System.out.println("\n--" + title + "--");
		
	}
	
	/**
	 * Prints a result with the expected and actual results. Uses the
	 * result object's toString() method
	 * 
	 * @param expected - What string you expect result to print to the console
	 * @param result - The result of the test (a variable, object)
	 */
	public static <T> void printResult(String expected, T result) {
		
		if (expected == null) { System.out.println("Expected: \tnull"); } else {
		System.out.println("Expected: \t" + expected); }
		
		if (result == null) { System.out.println("Actual: \tnull"); } else {
			System.out.println("Actual: \t" + result); }
		
	}
	
}