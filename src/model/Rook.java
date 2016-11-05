package model;


public class Rook extends Piece implements LongMovement {

	public Rook(String color) {
		super(color);
		type = "rook";
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public boolean isValidMove(String input, Square[][] board) {
		return rookChecker(input, board);
	}


	
	public boolean rookChecker(String input, Square[][] board) {
		// TODO Auto-generated method stub
		String[] args = input.split(" ");
		char initFile = args[0].charAt(0);
		char initRank = args[0].charAt(1);
		char finalFile = args[1].charAt(0);
		char finalRank = args[1].charAt(1);
		
		if ( ((initFile == finalFile && initRank != finalRank) || (initFile != finalFile && initRank == finalRank)) 
				&& !hasPiecesInbetween(initFile, initRank, finalFile, finalRank, board)) {
			if (board[finalFile-'a'][finalRank-49].getPiece() == null)
				return true;
			if (board[finalFile-'a'][finalRank-49].getPiece().isWhite() != board[initFile-'a'][initRank-49].getPiece().isWhite())
				return true;
		}
		
		return false;
	}

	
	@Override
	public Square[][] move(String input, Square[][] board) {
		return overallBoardcheck(input, board);
	}


	/**
	 * @param input
	 * @param board
	 * @return
	 */
	public Square[][] overallBoardcheck(String input, Square[][] board) {
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