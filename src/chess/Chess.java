package chess;

import java.util.Scanner;

import model.Board;


public class Chess {
	
	private static Board game;
	private static Scanner scan = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		startGame();
		
	}

	
	public static void startGame() {
		String input;
		boolean isValidInput, isValidMove;
		game = new Board();
		
		while (!game.isDone()) {
			donesRun();
		}
		
		
	}


	public static void donesRun() {
		String input;
		boolean isValidInput;
		boolean isValidMove;
		game.drawBoard();
		game.askForInput();
		input = scan.nextLine();
		isValidInput = isValidInput(input);
		isValidMove = true;
		isValidMove = checkerInputs(input, isValidInput, isValidMove);
		input = valiousNite(input, isValidInput, isValidMove);
		game.move(input);
		System.out.println();
	}


	public static String valiousNite(String input, boolean isValidInput, boolean isValidMove) {
		while(!isValidInput || !isValidMove) {
			if (!isValidInput)
				System.out.println("Invalid Input, please use the format: fileRank fileRank");
			if (!isValidMove)
				System.out.println("Illegal move, try again");
			game.askForInput();
			input = scan.nextLine();
			isValidInput = isValidInput(input);
			isValidMove = true;
			isValidMove = checkerInputs(input, isValidInput, isValidMove);

		}
		return input;
	}


	public static boolean checkerInputs(String input, boolean isValidInput, boolean isValidMove) {
		if (isValidInput)
			isValidMove = game.isValidMove(input);
		return isValidMove;
	}
	
	/**
	 * 
	 * isValidInput()
	 * @param input
	 * @return boolean
	 * 
	 * checks if the input is in the right form and returns it.....it also checks various forms and other 
	 * 
	 */
	public static boolean isValidInput(String input) {
		//exits if resignation and declares winner
		isValidFormCheckers(input);
			
		isDrawCheckers(input);
		
		return differentCheckersAndForms(input);	
	}


	public static boolean differentCheckersAndForms(String input) {
		//Checks if input is for movement, promotion, or draw
		return input.matches("[abcdefgh][12345678] [abcdefgh][12345678]") 
				|| input.matches("[abcdefgh][12345678] [abcdefgh][12345678] [QNRB]")
				|| input.matches("[abcdefgh][12345678] [abcdefgh][12345678] draw[?]");
	}


	public static void isDrawCheckers(String input) {
		//exits if draw
		if (input.trim().equals("draw")){
			System.out.println("Draw");
			System.exit(0);
		}
	}


	public static void isValidFormCheckers(String input) {
		if (input.trim().equals("resign"))
		{
			if(game.isWhitesMove()){
				System.out.println("Black wins");
			}
			else{
				System.out.println("White wins");
			}
			
			System.exit(0);
		}
	}
	
}