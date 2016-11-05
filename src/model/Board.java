
package model;

import java.util.Arrays;


public class Board {
	
	private BoardDataAndClass data = new BoardDataAndClass();


	public Board() {
		
		boardGetterSetsRanks();
	}

	
	public void boardGetterSetsRanks() {
		extractedBoardHash();
		
		int file;
		int rank;
		rankinitalizer();
		
		
		rankChanger();
		
	
		pieceInitiator();
		
		blackwhitebolChecker();
	}


	/**
	 * 
	 */
	public void extractedBoardHash() {
		data.board = new Square[8][8];
		
		
		data.board[0][7] = new Square(new Rook("black"),"black");
		data.board[7][7] = new Square(new Rook("black"),"white");
		data.board[1][7] = new Square(new Knight("black"),"white");
		data.board[6][7] = new Square(new Knight("black"),"black");
		data.board[2][7] = new Square(new Bishop("black"),"black");
		data.board[5][7] = new Square(new Bishop("black"),"white");
		data.board[3][7] = new Square(new Queen("black"),"white");
		data.board[4][7] = new Square(new King("black"),"black");
	}


	/**
	 * 
	 */
	public void blackwhitebolChecker() {
		data.isDone = false;
		data.isStalemate = false;
		data.isResign = false;
		data.isWhiteWinner = false;
		data.isWhitesMove = true;
		data.isWhiteInCheck = false;
		data.isBlackInCheck = false;
		data.isInCheck = false;
		data.isDrawAvailable = false;
	}


	/**
	 * 
	 */
	public void pieceInitiator() {
		data.board[0][0] = new Square(new Rook("white"),"white");
		data.board[7][0] = new Square(new Rook("white"),"black");
		data.board[1][0] = new Square(new Knight("white"),"black");
		data.board[6][0] = new Square(new Knight("white"),"white");
		data.board[2][0] = new Square(new Bishop("white"),"white");
		data.board[5][0] = new Square(new Bishop("white"),"black");
		data.board[3][0] = new Square(new Queen("white"),"black");
		data.board[4][0] = new Square(new King("white"),"white");
	}


	/**
	 * 
	 */
	public void rankChanger() {
		int file;
		int rank;
		for(rank = 5; rank>=2; rank--){
			for(file = 0; file<8; file++){
				if ((file%2 == 0 && rank%2 == 0) || (file%2 != 0 && rank%2 != 0))
					data.board[file][rank] = new Square("white");
				else
					data.board[file][rank] = new Square("black");
			}
		}
		
		rank = 1; 
		for(file = 0; file < 8; file++)
		{
			if (file%2 == 0)
				data.board[file][rank] = new Square(new Pawn("white"),"black");
			else
				data.board[file][rank] = new Square(new Pawn("white"),"white");	
		}
	}


	/**
	 * 
	 */
	public void rankinitalizer() {
		int file, rank;
		
		rank = 6; 
		for(file = 0; file < 8; file++)
		{
			if (file%2 == 0)
				data.board[file][rank] = new Square(new Pawn("black"),"white");
			else
				data.board[file][rank] = new Square(new Pawn("black"),"black");	
		}
	}
	
	public void drawBoard()
	{
		int rank, file;
		
		for(rank = 7; rank >= 0; rank--)
		{
			for(file = 0; file < 8; file++)
			{
				if(data.board[file][rank].getPiece() != null){
					System.out.print(data.board[file][rank] + " ");
				}
				else{
					if(data.board[file][rank].isSquareBlack())
						System.out.print("## ");
					else
						System.out.print("   ");
				}
				
			}
			System.out.println(" " + (rank+1));
		}
		System.out.println(" a  b  c  d  e  f  g  h");
		System.out.println();
	}
	
	
	public void move(String input) {
		String[] args = input.split(" ");
		char initFile = args[0].charAt(0);
		char initRank = args[0].charAt(1);
		char finalFile = args[1].charAt(0);
		char finalRank = args[1].charAt(1);
		
		data.isDrawAvailable = false;
		
		if (args.length == 3 && args[2].equals("draw?"))
			data.isDrawAvailable = true;
			
		
		data.board = data.board[initFile-'a'][initRank-49].getPiece().move(input, data.board);
		
		testForCheck(data.board); //tests for check and checkmate
		
		changePlayer();
	}
	
	
	public boolean isValidMove(String input) {
		String[] args = input.split(" ");
		String initPos = args[0];
		String finalPos = args[1];
		char initFile = initPos.charAt(0);
		char initRank = initPos.charAt(1);
		char finalFile = finalPos.charAt(0);
		char finalRank = finalPos.charAt(1);
		
		// if the position is the same or there's no piece in the initial file or that piece is not the correct player's piece
		if (initPos.equals(finalPos)
				|| data.board[initFile-'a'][initRank-49].getPiece() == null
				|| data.board[initFile-'a'][initRank-49].getPiece().isWhite() != isWhitesMove()
				|| data.board[finalFile-'a'][finalRank-49].getPiece() instanceof King)
			return false;
		
		// if there's a third argument, and the piece isn't a pawn, return false (for promotion)
		if (args.length == 3 && !(data.board[initFile-'a'][initRank-49].getPiece() instanceof Pawn))
			return false;
		
		boolean isValidMove = data.board[initFile-'a'][initRank-49].getPiece().isValidMove(input, data.board);
		
	
		if (isValidMove) {
			Square[][] clone = createClone(data.board);
			boolean hasMoved = data.board[initFile-'a'][initRank-49].getPiece().hasMoved();
			clone = clone[initFile-'a'][initRank-49].getPiece().move(input, clone);
			data.board[initFile-'a'][initRank-49].getPiece().setHasMoved(hasMoved);
			return !isKingInCheck(clone, isWhitesMove());
		}
		
		return isValidMove;
	}
	
	/**
	 * askForInput
	 * asks for input depending on who's move it is
	 */
	public void askForInput() {
		if (isWhitesMove())
			System.out.print("White's move: ");
		else
			System.out.print("Black's move: ");
	}
	
	/**
	 * changePlayer
	 */
	public void changePlayer() {
		data.isWhitesMove = !data.isWhitesMove;
	}
	
	/**
	 * testForCheck
	 * first checks if king is in check
	 * then, if it is in check, check for checkmate
	 */
	public void testForCheck(Square[][] b) {
		if (isKingInCheck(data.board, !isWhitesMove()))
			setCheck();
		else
			unsetCheck();
			
	}
	
	/**
	 * setCheck
	 * sets isInCheck to true and checks for checkmate
	 */
	public void setCheck() {
		data.isInCheck = true;
		System.out.println();
		if (!testForCheckmate())
			System.out.println("Check");
	}
	
	/**
	 * unsetCheck
	 * sets isInCheck to false and checks for stalemate
	 */
	public void unsetCheck() {
		data.isInCheck = false;
		testForStalemate();
	}
	
	/**
	 * isInCheck
	 * @return true if isInCheck, else false
	 */
	public boolean isInCheck() {
		return data.isInCheck;
	}
	
	/**
	 * isDone Accessor
	 * @return			true if game is done, else false
	 */
	public boolean isDone() {
		return data.isDone;
	}
	/**
	 * isStalemate Accessor
	 * @return			true if game is stalemate, else false
	 */
	public boolean isStalemate() {
		return data.isStalemate;
	}
	/**
	 * isResign Accessor
	 * @return			true if game is resign, else false
	 */
	public boolean isResign() {
		return data.isResign;
	}
	/**
	 * isWhiteWinner Accessor
	 * Might be unnecessary
	 * @return			true if white won, else false
	 */
	public boolean isWhiteWinner() {
		return data.isWhiteWinner;
	}
	/**
	 * isWhitesMove Accessor
	 * @return			true if it is White's move, else false
	 */
	public boolean isWhitesMove() {
		return data.isWhitesMove;
	}
	
	/**
	 * isDrawAvailable
	 * @return			true if draw is available, else false
	 */
	public boolean isDrawAvailable() {
		return data.isDrawAvailable;
	}
	
	/* CHECKMATE STUFF */
	/*public void testForCheckmate()
	{
		String kingsLocation = getEnemyKingLocation(board, isWhitesMove);
		String[] args = kingsLocation.split(" ");
		int kingsFile = args[0].charAt(0) - 'a';
		int kingsRank = Character.getNumericValue(args[1].charAt(0))-1;
		if( !((King) board[kingsFile][kingsRank].getPiece()).isSafe(kingsFile, kingsRank, board)
				&& !((King) board[kingsFile][kingsRank].getPiece()).isSafe(kingsFile+1, kingsRank+1, board)
				&& !((King) board[kingsFile][kingsRank].getPiece()).isSafe(kingsFile, kingsRank+1, board)
				&& !((King) board[kingsFile][kingsRank].getPiece()).isSafe(kingsFile+1, kingsRank, board)
				&& !((King) board[kingsFile][kingsRank].getPiece()).isSafe(kingsFile+1, kingsRank-1, board)
				&& !((King) board[kingsFile][kingsRank].getPiece()).isSafe(kingsFile-1, kingsRank+1, board)
				&& !((King) board[kingsFile][kingsRank].getPiece()).isSafe(kingsFile-1, kingsRank-1, board)
				&& !((King) board[kingsFile][kingsRank].getPiece()).isSafe(kingsFile-1, kingsRank, board)
				&& !((King) board[kingsFile][kingsRank].getPiece()).isSafe(kingsFile, kingsRank-1, board)){
				
				if(isWhitesMove){
					isWhiteWinner = true;
					System.out.println("Checkmate");
					System.out.println("White wins");
					isDone=true;
				}
				else{
					isWhiteWinner = false;
					System.out.println("Checkmate");
					System.out.println("Black wins");
					isDone =true;
				}
				
		}
				
		
		
	}
	
	*/
	
	/**
	 * testForCheckmate
	 * if there are no valid moves from any piece on the board, then declare checkmate and end game
	 * @return true if it is checkmate, else false
	 */
	
	public boolean testForStalemate()
	{
		char initFile, initRank;
		for (int f = 0; f < 8; f++) {
			for (int r = 0; r < 8; r++) {
				initFile = (char) f;
				initFile += 'a';
				initRank = (char) r;
				initRank += 49;
				if (data.board[f][r].getPiece() != null && data.board[f][r].getPiece().isWhite() != isWhitesMove()) {
					if (hasValidMoves(initFile, initRank)) {
						return false;
					}
				}		
			}
		}
		
		System.out.println("Stalemate");
		data.isDone = true;
		
		return true;
	}
	
	/**
	 * testForCheckmate
	 * @return true if checkmate, else false
	 */
	public boolean testForCheckmate() 
	{
		char initFile, initRank;
		for (int f = 0; f < 8; f++) {
			for (int r = 0; r < 8; r++) {
				initFile = (char) f;
				initFile += 'a';
				initRank = (char) r;
				initRank += 49;
				if (data.board[f][r].getPiece() != null && data.board[f][r].getPiece().isWhite() != isWhitesMove()) {
					if (hasValidMoves(initFile, initRank)) {
						return false;
					}
				}		
			}
		}
		
		if(data.isWhitesMove){
			data.isWhiteWinner = true;
			System.out.println("Checkmate");
			System.out.println("White wins");
			data.isDone = true;
		}
		else{
			data.isWhiteWinner = false;
			System.out.println("Checkmate");
			System.out.println("Black wins");
			data.isDone = true;
		}
		
		return true;
	}
	
	/**
	 * isKingInCheck
	 * @param b
	 * @param isWhite
	 * @return true if king is in check, false otherwise
	 */
	public boolean isKingInCheck(Square[][] b, boolean isWhite) 
	{
		return checkForBlackOrWhite(b, isWhite);
	}

	/**
	 * @param b
	 * @param isWhite
	 * @return
	 */
	public boolean checkForBlackOrWhite(Square[][] b, boolean isWhite) {
		String kingLoc = getKingLocation(b, isWhite);
		char initFile, initRank;
		String initInput = "";
		String testCheckInput = "";
		for (int f = 0; f < 8; f++) {
			for (int r = 0; r < 8; r++) {
				initFile = (char) f;
				initFile += 'a';
				initRank = (char) r;
				initRank += 49;
				initInput = initFile + "" + initRank + "";
				testCheckInput = initInput + " " + kingLoc;
				//System.out.println(testCheckInput);
				/* DO NOT LET isValidMove return true if location is king */
				if (b[f][r].getPiece() != null && !kingLoc.equals(initInput)) {
					if (b[f][r].getPiece().isValidMove(testCheckInput, b)) {
						return true;
					}
				}
			}
		}
		
		return false;
	}
	

	
	
	
	/**
	 * getKingLocation
	 * @param board
	 * @param isWhite
	 * @return king location of white king if isWhite is true, else black king
	 */
	public String getKingLocation(Square[][] board, boolean isWhite) {
		return locationCheckerForPiece(board, isWhite);
	}

	/**
	 * @param board
	 * @param isWhite
	 * @return
	 */
	public String locationCheckerForPiece(Square[][] board, boolean isWhite) {
		String location = "";
		char kingFile, kingRank;
		for (int f = 0; f < 8; f++) {
			for (int r = 0; r < 8; r++) {
				if (board[f][r].getPiece() != null 
						&& board[f][r].getPiece().isWhite() == isWhite
						&& board[f][r].getPiece() instanceof King) {
					kingFile = (char)(f + 'a');
					kingRank = (char)(r + 49);
					location = "" + kingFile + "" + kingRank;
					//return location;
				}
			}
		}
		return location;
	}
	
	/**
	 * hasValidMoves
	 * @param file
	 * @param rank
	 * @return if piece at that location has valid moves, return true. Else, false
	 */
	public boolean hasValidMoves(char file, char rank) {
		return validityCheckerAndFileGenerator(file, rank);
	}

	public boolean validityCheckerAndFileGenerator(char file, char rank) {
		char finalFile, finalRank;
		String finalInput = "";
		String initInput = file + "" + rank + " ";
		
		for (int f = 0; f < 8; f++) {
			for (int r = 0; r < 8; r++) {
				if (data.board[f][r].getPiece() instanceof King)
					continue;
				finalFile = (char) f;
				finalFile += 'a';
				finalRank = (char) r;
				finalRank += 49;
				finalInput = finalFile + "" + finalRank + "";
				if (data.board[file-'a'][rank-49].getPiece().isValidMove(initInput + finalInput, data.board)) {
					Square[][] clone = createClone(data.board);
					clone = clone[file-'a'][rank-49].getPiece().move(initInput + finalInput, clone);
					if (!isKingInCheck(clone, !isWhitesMove()) && !isKingInCheck(clone, isWhitesMove())) {
						//System.out.println(initInput + finalInput);
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	
	public Square[][] createClone(Square[][] board) {
		return cloneChangerAndFormer(board);
	}

	public Square[][] cloneChangerAndFormer(Square[][] board) {
		Square[][] clone = new Square[8][8];
		
		for (int f = 0; f < 8; f++) {
			for (int r = 0; r < 8; r++) {
				cloneChecker(board, clone, f, r);
			}
		}
		
		return clone;
	}

	public void cloneChecker(Square[][] board, Square[][] clone, int f, int r) {
		try {
			clone[f][r] = (Square) board[f][r].clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}