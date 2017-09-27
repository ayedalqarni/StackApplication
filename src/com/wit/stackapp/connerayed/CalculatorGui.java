package com.wit.stackapp.connerayed;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 * CalculatorGui is a JavaFX application
 * that allows the user to build an infix expression
 * and evaluate it like a calculator.
 * 
 * @author Conner Theberge
 *
 */
public class CalculatorGui extends Application {
	
	private static final int BUTTON_WIDTH = 48;
	private static final int BUTTON_HEIGHT = 48;
	private static final int MAX_DIGITS = 27;
	
	private int digits = 0;
	private boolean error = false;
	private boolean displayingResults = false;
	
	public static void startApplication(String[] commandLineArgs) {
		
		launch(commandLineArgs);
		
	}
	
	private Label lblEquationView;
	
	//Holds numbers for their respective Button instances
	private Map<Button, Integer> numberButtons = new HashMap<>();
	
	@Override
	public void start(Stage stage) throws Exception {
		
		Pane mainPane = new Pane();
		
		//Function Buttons
		Button btnClear = new Button("C");
		btnClear.setOnAction(eventHandler -> { clear(); });
		
		Button btnBackspace = new Button("<");
		btnBackspace.setOnAction(eventHandler -> { deleteCharFromEquationView(); });
		
		Button btnQuit = new Button("Q");
		btnQuit.setOnAction(eventHandler -> { Platform.exit(); });
		
		Button btnEquals = new Button("=");
		btnEquals.setOnAction(eventHandler -> { calculate(); });
		
		//Operator Buttons
		Button btnDivide = new Button("/");
		btnDivide.setOnAction(eventHandler -> { addCharToEquationView('/'); });
		
		Button btnMultiply = new Button("*");
		btnMultiply.setOnAction(eventHandler -> { addCharToEquationView('*'); });
		
		Button btnSubtract = new Button("-");
		btnSubtract.setOnAction(eventHandler -> { addCharToEquationView('-'); });
		
		Button btnAdd = new Button("+");
		btnAdd.setOnAction(eventHandler -> { addCharToEquationView('+'); });
		
		Button btnParenOpen = new Button("(");
		btnParenOpen.setOnAction(eventHandler -> { addCharToEquationView('('); });
		
		Button btnParenClose = new Button(")");
		btnParenClose.setOnAction(eventHandler -> { addCharToEquationView(')'); });
		
		//A temporary list of number buttons (ordered)
		//to assist with adding buttons at specific places
		//in the button rows.
		ArrayList<Button> numbButtons = new ArrayList<>();
		
		//Adds a number button for all numbers 0 - 9 (inclusive)
		for (int i = 0; i <= 9; i++) {
			
			Button b = new Button(Integer.toString(i));
			b.setOnAction(eventHandler -> {
				
				//Adds whatever number is associated with this button instance to the equation
				addCharToEquationView( Character.forDigit( numberButtons.get( (Button)eventHandler.getSource() ), 10 ) );
				
			});
			
			numberButtons.put(b, i);
			numbButtons.add(b);
			
		}
		
		VBox rowContainer = new VBox();
		
		//The rows of buttons
		ArrayList<HBox> buttonHolders = new ArrayList<>();
		buttonHolders.add(new HBox(btnClear, btnBackspace, btnQuit, btnDivide));
		buttonHolders.add(new HBox(numbButtons.get(7), numbButtons.get(8), numbButtons.get(9), btnMultiply));
		buttonHolders.add(new HBox(numbButtons.get(4), numbButtons.get(5), numbButtons.get(6), btnSubtract));
		buttonHolders.add(new HBox(numbButtons.get(1), numbButtons.get(2), numbButtons.get(3), btnAdd));
		buttonHolders.add(new HBox(numbButtons.get(0), btnParenOpen, btnParenClose, btnEquals));
		
		lblEquationView = new Label("");
		lblEquationView.setPrefSize(192, 64);
		lblEquationView.setStyle("-fx-background-color: lightgray;");
		lblEquationView.setTextAlignment(TextAlignment.CENTER);
		lblEquationView.setPadding(new Insets(4));
		
		rowContainer.getChildren().add(lblEquationView);
		rowContainer.getChildren().addAll(buttonHolders);
		
		//Resizes all buttons
		for (HBox holder : buttonHolders) {
		
			for (Node buttonNode : holder.getChildren()) {
				
				Button b = (Button)buttonNode;
				
				b.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
				
			}

		}
		
		mainPane.getChildren().add(rowContainer);
		
		mainPane.setPrefSize(182, 294);
		
		Scene scene = new Scene(mainPane);
		
		stage.setScene(scene);
		
		stage.setResizable(false);
		
		stage.show();
		
	}
	
	/**
	 * Clears the calculator display.
	 * 
	 * Resets the error & result display states.
	 */
	private void clear() {
		
		lblEquationView.setText("");
		
		digits = 0;
		
		if (error || displayingResults) {
			
			lblEquationView.setTextFill(Paint.valueOf("black"));
			
			error = false;
			
			displayingResults = false;
			
		}
		
	}
	
	/**
	 * Calculates the equation stored in the calculator's display
	 * and displays the results.
	 */
	private void calculate() {
		
		String equation = lblEquationView.getText();
		
		if (!error && !displayingResults) {
			
			//TODO calculate here
			
		}
		
	}
	
	/**
	 * Adds a specified character to the calculator's display
	 * 
	 * @param charToAdd The character to add to the display
	 */
	private void addCharToEquationView(char charToAdd) {
		
		if (error || displayingResults) { clear(); }
		
		if (digits < MAX_DIGITS) {
			
			lblEquationView.setText(lblEquationView.getText() + charToAdd);
			digits++;
		
		}
		
	}
	
	/**
	 * Deletes one character from the calculator's display
	 */
	private void deleteCharFromEquationView() {
		
		if (digits > 0 && !error && !displayingResults) {
		
			String curText = lblEquationView.getText();
			
			lblEquationView.setText(curText.substring(0, curText.length() - 1));
			
			digits--;
	
		}
		
	}

	/**
	 * Sets the error state to true.
	 * 
	 * Informs the user of the error.
	 */
	private void showError() {
		
		if (!error && !displayingResults) {
			
			clear();
			
			error = true;
			
			lblEquationView.setTextFill(Paint.valueOf("red"));
			
			lblEquationView.setText("ERROR!");
		
		}
		
	}
	
	/**
	 * Shows a specified equation and result on the
	 * calculator's display.
	 * 
	 * @param equation The equation the result is from
	 * @param result The calculated integer
	 */
	private void displayResults(String equation, int result) {
		
		if (!displayingResults) {
		
			lblEquationView.setText(equation + "\n= " + result);
		
			displayingResults = true;
		
		}
		
	}
	
}