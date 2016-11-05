package model;



public class Queen extends Piece implements LongMovement {

	public Queen(String color) {
		super(color);
		type = "queen";
		
	}
	
	

	@Override
	public boolean isValidMove(String input, Square[][] board) {
		return validityCheckerforAll(input, board);
	}



	
	public boolean validityCheckerforAll(String input, Square[][] board) {
		// TODO Auto-generated method stub
		String[] args = input.split(" ");
		char initFile = args[0].charAt(0);
		char initRank = args[0].charAt(1);
		char finalFile = args[1].charAt(0);
		char finalRank = args[1].charAt(1);
		//check if valid long movement
		if ((Math.abs(initFile - finalFile) == Math.abs(initRank - finalRank) 
				|| ((initFile == finalFile && initRank != finalRank) || (initFile != finalFile && initRank == finalRank)))
				&& !hasPiecesInbetween(initFile, initRank, finalFile, finalRank, board)) {
			if (board[finalFile-'a'][finalRank-49].getPiece() == null
				|| board[finalFile-'a'][finalRank-49].getPiece().isWhite() != isWhite()) {
				return true;
			}
		}
		
		return false;
	}

	
	@Override
	public Square[][] move(String input, Square[][] board) {
		return moveDesignerAndUpdater(input, board);
	}



	
	public Square[][] moveDesignerAndUpdater(String input, Square[][] board) {
		// TODO Auto-generated method stub
		String[] args = input.split(" ");
		char initFile = args[0].charAt(0);
		char initRank = args[0].charAt(1);
		char finalFile = args[1].charAt(0);
		char finalRank = args[1].charAt(1);
		
		Piece initPiece = board[initFile-'a'][initRank-49].getPiece();
		
		board[finalFile-'a'][finalRank-49].setPiece(initPiece);
		board[initFile-'a'][initRank-49].setPiece(null);
		
		moved(); // set hasMoved to true
		
		return board;
	}
}