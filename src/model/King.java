
package model;



public class King extends Piece implements LongMovement {

	/**
	 * @param color
	 */
	public King(String color) {
		super(color);
		type = "king";
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public boolean isValidMove(String input, Square[][] board) {
		return doubleCheckerforMoveSafety(input, board);
	}

	
	public boolean doubleCheckerforMoveSafety(String input, Square[][] board) {
		// TODO Auto-generated method stub
		String[] args = input.split(" ");
		char initFile = args[0].charAt(0);
		char initRank = args[0].charAt(1);
		char finalFile = args[1].charAt(0);
		char finalRank = args[1].charAt(1);
		
		if (initFile == finalFile && Math.abs(initRank - finalRank) == 1 
				&& (board[finalFile-'a'][finalRank-49].getPiece() == null || board[finalFile-'a'][finalRank-49].getPiece().isWhite() != isWhite()))
			return true;
		if (initRank == finalRank && Math.abs(initFile - finalFile) == 1 
				&& (board[finalFile-'a'][finalRank-49].getPiece() == null || board[finalFile-'a'][finalRank-49].getPiece().isWhite() != isWhite()))
			return true;
		if( Math.abs(initFile - finalFile) == 1 && Math.abs(initRank-finalRank) ==1
				&& (board[finalFile-'a'][finalRank-49].getPiece() == null || board[finalFile-'a'][finalRank-49].getPiece().isWhite() != isWhite()))
			return true;
		if (!hasMoved()){
			if(finalFile == 'g' && (finalRank-48 == 1 || finalRank-48 == 8)
					&& board[7][finalRank-49].getPiece() instanceof Rook
					&& !board[7][finalRank-49].getPiece().hasMoved()
					&& board[7][finalRank-49].getPiece().isWhite() == isWhite()
					&& !hasPiecesInbetween(initFile, initRank, 'h', finalRank, board)){
				return true;
				
			}
			if(finalFile == 'c' && (finalRank-48 == 1 || finalRank-48 == 8)
					&& board[0][finalRank-49].getPiece() instanceof Rook
					&& !board[0][finalRank-49].getPiece().hasMoved()
					&& board[0][finalRank-49].getPiece().isWhite() == isWhite()
					&& !hasPiecesInbetween(initFile, initRank, 'a', finalRank, board)){
				return true;
				
			}
		}
		
		return false;
	}

	@Override
	public Square[][] move(String input, Square[][] board) {
		return pieceMoverUpdater(input, board);
	}

	
	public Square[][] pieceMoverUpdater(String input, Square[][] board) {
		// TODO Auto-generated method stub
		String[] args = input.split(" ");
		char initFile = args[0].charAt(0);
		char initRank = args[0].charAt(1);
		char finalFile = args[1].charAt(0);
		char finalRank = args[1].charAt(1);
		
		if (!hasMoved() && finalFile == 'g' && (finalRank-48 == 1 || finalRank-48 == 8)
					&& board[7][finalRank-49].getPiece() instanceof Rook
					&& !board[7][finalRank-49].getPiece().hasMoved()
					&& board[7][finalRank-49].getPiece().isWhite() == isWhite()
					&& !hasPiecesInbetween(initFile, initRank, 'h', finalRank, board)){
				board = castling(initFile, initRank, 'h', finalRank, board);
				
		}
		else if (!hasMoved() && finalFile == 'c' && (finalRank-48 == 1 || finalRank-48 == 8)
					&& board[0][finalRank-49].getPiece() instanceof Rook
					&& !board[0][finalRank-49].getPiece().hasMoved()
					&& board[0][finalRank-49].getPiece().isWhite() == isWhite()
					&& !hasPiecesInbetween(initFile, initRank, 'a', finalRank, board)){
				board = castling(initFile, initRank, 'a', finalRank, board);
				
		}		
		else {
			Piece initPiece = board[initFile-'a'][initRank-49].getPiece();
		
			board[finalFile-'a'][finalRank-49].setPiece(initPiece);
			board[initFile-'a'][initRank-49].setPiece(null);
		}
		
		moved(); // set hasMoved to true
		
		return board;
	}
	
	public Square[][] castling(char initFile, char initRank, char finalFile, char finalRank, Square[][] board) {
		Piece king = board[initFile-'a'][initRank-49].getPiece();
		Piece rook = board[finalFile-'a'][finalRank-49].getPiece();
		
		castlerAndSurround(finalFile, finalRank, board, king, rook);
		aChecker(initFile, initRank, finalFile, finalRank, board, king, rook);
		
		return board;
	}

	
	public void aChecker(char initFile, char initRank, char finalFile, char finalRank, Square[][] board, Piece king,
			Piece rook) {
		if (finalFile == 'a') {
			board[finalFile-'a'+2][finalRank-49].setPiece(king);
			board[finalFile-'a'+3][finalRank-49].setPiece(rook);
			board[initFile-'a'][initRank-49].setPiece(null);
			board[finalFile-'a'][finalRank-49].setPiece(null);
		}
	}

	
	public void castlerAndSurround(char finalFile, char finalRank, Square[][] board, Piece king, Piece rook) {
		if (finalFile == 'h') {
			board[6][finalRank-49].setPiece(king);
			board[5][finalRank-49].setPiece(rook);
			board[7][finalRank-49].setPiece(null);
			board[4][finalRank-49].setPiece(null);
		}
	}
	
	
}