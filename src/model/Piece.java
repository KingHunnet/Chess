
package model;


public abstract class Piece implements Cloneable {

	boolean isWhite;
	String type;
	boolean hasMoved;
	
	public Piece(String color) {
		isWhite = color.equals("white");
		type = null;
		hasMoved = false;
	}
	
	
	public boolean isWhite() {
		return isWhite;
	}
	
	
	public boolean hasMoved() {
		return hasMoved;
	}
	
	
	public void moved() {
		hasMoved = true;
	}
	
	
	public boolean testForCheck(char file, char rank, Square[][] board) {
		String f = file+"", r = rank+"";
		String testCheckInput = f + r + " " + getEnemyKingLocation(board, isWhite());
		
		return isValidMove(testCheckInput, board);
	}
	
	
	
	public String getEnemyKingLocation(Square[][] board, boolean isCurrentlyWhite) {
		return enemyLocator(board, isCurrentlyWhite);
	}



	public String enemyLocator(Square[][] board, boolean isCurrentlyWhite) {
		String location = "";
		char kingFile, kingRank;
		for (int f = 0; f < 8; f++) {
			for (int r = 0; r < 8; r++) {
				if (board[f][r].getPiece() != null 
						&& board[f][r].getPiece().isWhite() != isCurrentlyWhite
						&& board[f][r].getPiece() instanceof King) {
					kingFile = (char)(f + 'a');
					kingRank = (char)(r + 49);
					location = "" + kingFile + kingRank;
					return location;
				}
			}
		}
		
		return null;
	}
	
	
	public abstract boolean isValidMove(String input, Square[][] board);
	
	
	public abstract Square[][] move(String input, Square[][] board);
	
	
	public boolean canEnpassant()
	{
		return false;
	}
	
	
	public boolean getEnpassant()
	{
		return false;
	}
	
	public Object clone() throws CloneNotSupportedException {
	    return super.clone();
	}
	
	public void setHasMoved(boolean m) {
		hasMoved = m;
	}
	
}