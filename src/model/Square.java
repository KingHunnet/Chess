
package model;


public class Square implements Cloneable {

	
	private Piece occupyingPiece;
	private boolean isBlack;
	
	public Square(String color) {
		this(null, color);
	}
	
	public Square(Piece piece, String color) {
		occupyingPiece = piece;
		isBlack = color.equals("black");
	}
	
	
	public void setPiece(Piece piece) {
		occupyingPiece = piece;
	}

	
	public Piece getPiece()
	{
		return occupyingPiece;
	}
	
	
	public String getPieceType()
	{
		return characterSwitcher();
	}

	/**
	 * @return
	 */
	public String characterSwitcher() {
		switch(occupyingPiece.type)
		{
			case "rook":
				return "R";
			case "knight":
				return "N";
			case "bishop":
				return "B";
			case "pawn":
				return "p";
			case "king":
				return "K";
			case "queen":
				return "Q";
			default:
				return "";
		}
	}
	
	public String getPieceColor()
	{
		if(occupyingPiece.isWhite)
			return "w";
		else
			return "b";
	}
	
	
	public boolean isSquareBlack()
	{
		return isBlack;
	}
	
	
	public String toString() {
		return getPieceColor() + getPieceType();
	}
	
	public Object clone() throws CloneNotSupportedException {
	    return super.clone();
	}
}