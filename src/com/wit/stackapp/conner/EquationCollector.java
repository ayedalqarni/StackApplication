package com.wit.stackapp.conner;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

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
		
		equations = new VectorStack<String>();
		
		if (filepath == null) { System.out.println("FILEPATH CANNOT BE NULL"); }
		
		try {
			
			Scanner fileIn = new Scanner(new File(filepath));
		
			while (fileIn.hasNextLine()) {
				
				//Assumes each line of the file is a seperate
				//equation. Validity can be checked by isValid()
				equations.push(fileIn.nextLine());
				
			}
			
			fileIn.close();
			
		} catch (FileNotFoundException e) {
			
			System.out.println("EQUATION COLLECTOR CAN'T FIND FILE: " + filepath);
		
		}
		
		initialized = true;
		
	}
	
}