
package model;



public class Bishop extends Piece implements LongMovement {

	
	public Bishop(String color) {
		super(color);
		type = "bishop";
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * Returns boolean indicating whether the move indicated for this Bishop is valid
	 *
	 * 
	 */
	@Override
	public boolean isValidMove(String input, Square[][] board) {
		// TODO Auto-generated method stub
		String[] args = input.split(" ");
		char initFile = args[0].charAt(0);
		char initRank = args[0].charAt(1);
		char finalFile = args[1].charAt(0);
		char finalRank = args[1].charAt(1);
		
		if (Math.abs(initFile - finalFile) == Math.abs(initRank - finalRank) && !hasPiecesInbetween(initFile, initRank, finalFile, finalRank, board)) {
			if (board[finalFile-'a'][finalRank-49].getPiece() == null)
				return true;
			if (board[finalFile-'a'][finalRank-49].getPiece().isWhite() != board[initFile-'a'][initRank-49].getPiece().isWhite())
				return true;
		}
		
		return false;
	}

	/**
	 * Returns updated board after moving piece according to input
	 *
	 * 
	 */
	@Override
	public Square[][] move(String input, Square[][] board) {
		return facetsChanged(input, board);
	}

	public Square[][] facetsChanged(String input, Square[][] board) {
		// TODO Auto-generated method stub
		String[] args = input.split(" ");
		char initFile = args[0].charAt(0);
		char initRank = args[0].charAt(1);
		char finalFile = args[1].charAt(0);
		char finalRank = args[1].charAt(1);
		
		hashIntroduced(board, initFile, initRank, finalFile, finalRank);
		
		moved(); // set hasMoved to true
		
		return board;
	}

	public void hashIntroduced(Square[][] board, char initFile, char initRank, char finalFile, char finalRank) {
		Piece initPiece = board[initFile-'a'][initRank-49].getPiece();
		
		board[finalFile-'a'][finalRank-49].setPiece(initPiece);
		board[initFile-'a'][initRank-49].setPiece(null);
	}

}