package model;

import java.util.Scanner;



public class Pawn extends Piece {

	private boolean enpassant;
	private boolean willEnpassant;
	
	public Pawn(String color) {
		super(color);
		type = "pawn";
		enpassant = true;
		willEnpassant = false;
		// TODO Auto-generated constructor stub
	}

		@Override
	public boolean isValidMove(String input, Square[][] board) {
		return enpasseCheckedAndUpdate(input, board);
	}

		/**
		 * @param input
		 * @param board
		 * @return
		 */
		public boolean enpasseCheckedAndUpdate(String input, Square[][] board) {
			// TODO Auto-generated method stub
			String[] args = input.split(" ");
			char initFile = args[0].charAt(0);
			char initRank = args[0].charAt(1);
			char finalFile = args[1].charAt(0);
			char finalRank = args[1].charAt(1);
			
			
			
			
			
			willEnpassant = false;
			
			// If pawn is going straight
			if (initFile == finalFile) {
				if (isWhite && initRank+1 == finalRank && board[finalFile-'a'][finalRank-49].getPiece() == null){
					enpassant = false;
					return true;
				}
				
				// is white and it's the first turn, then can move up to 2 spots
				if (isWhite && initRank == '2' && initRank+2 == finalRank && board[finalFile-'a'][finalRank-49].getPiece() == null){
				//	System.out.println("Is now enpassant eligible");
					enpassant = true;
					return true;
				}
				
				if (!isWhite && initRank-1 == finalRank && board[finalFile-'a'][finalRank-49].getPiece() == null)
				{
					enpassant = false;
					return true;
				}
				
				// is black and it's the first turn, then can move up to 2 spots
				if (!isWhite && initRank == '7' && initRank-2 == finalRank && board[finalFile-'a'][finalRank-49].getPiece() == null){
					//System.out.println("Is now enpassant eligible");
					enpassant = true;
					return true;
				}
			}
			
			
			//If pawn is going diagonal into spot of opponent
			if (isWhite && initRank+1 == finalRank && initFile+1 == finalFile && board[initFile-'a'+1][initRank-48].getPiece() != null && !board[initFile-'a'+1][initRank-48].getPiece().isWhite())
			{
				enpassant = false;
				return true;
			}
			if (isWhite && initRank+1 == finalRank && initFile-1 == finalFile && board[initFile-'a'-1][initRank-48].getPiece() != null && !board[initFile-'a'-1][initRank-48].getPiece().isWhite())
			{
				enpassant = false;
				return true;
			}
			
			if (!isWhite && initRank-1 == finalRank && initFile+1 == finalFile && board[initFile-'a'+1][initRank-50].getPiece() != null && board[initFile-'a'+1][initRank-50].getPiece().isWhite())
			{
				enpassant = false;
				return true;
			}
			if (!isWhite && initRank-1 == finalRank && initFile-1 == finalFile && board[initFile-'a'-1][initRank-50].getPiece() != null && board[initFile-'a'-1][initRank-50].getPiece().isWhite())
			{
				enpassant = false;
				return true;
			}
			
			//If pawn is going diagonal for 
			if (isWhite && initRank == '5' && finalRank =='6' && initFile+1 == finalFile && board[initFile-'a'+1][initRank-49].getPiece() != null && !board[initFile-'a'+1][initRank-49].getPiece().isWhite() && canEnpassant(initFile-'a'+1, initRank-49,board) && board[finalFile-'a'][finalRank-49].getPiece() == null)
			{
				enpassant = false;
				willEnpassant = true;
				return true;
			}
			if (isWhite && initRank == '5' && finalRank =='6' && initFile-1 == finalFile && board[initFile-'a'-1][initRank-49].getPiece() != null && !board[initFile-'a'-1][initRank-49].getPiece().isWhite() && canEnpassant(initFile-'a'-1, initRank-49,board) && board[finalFile-'a'][finalRank-49].getPiece() == null)
			{
				enpassant = false;
				willEnpassant = true;
				return true;
			}
			if (!isWhite && initRank == '4' && finalRank =='3'&& initFile+1 == finalFile && board[initFile-'a'+1][initRank-49].getPiece() != null && board[initFile-'a'+1][initRank-49].getPiece().isWhite() && canEnpassant(initFile-'a'+1, initRank-49,board) && board[finalFile-'a'][finalRank-49].getPiece() == null)
			{
				enpassant = false;
				willEnpassant = true;
				return true;
			}
			if (!isWhite && initRank == '4' && finalRank =='3'&& initFile-1 == finalFile && board[initFile-'a'-1][initRank-49].getPiece() != null && board[initFile-'a'-1][initRank-49].getPiece().isWhite() && canEnpassant(initFile-'a'-1, initRank-49, board) && board[finalFile-'a'][finalRank-49].getPiece() == null)
			{
				
				enpassant = false;
				willEnpassant = true;
				return true;
			}
			
			return false;
		}

	
	@Override
	public Square[][] move(String input, Square[][] board) {
		return maliseInputDetector(input, board);
	}

	/**
	 * @param input
	 * @param board
	 * @return
	 */
	public Square[][] maliseInputDetector(String input, Square[][] board) {
		// TODO Auto-generated method stub
		String[] args = input.split(" ");
		char initFile = args[0].charAt(0);
		char initRank = args[0].charAt(1);
		char finalFile = args[1].charAt(0);
		char finalRank = args[1].charAt(1);
		
		Piece initPiece = board[initFile-'a'][initRank-49].getPiece();
		
		board[finalFile-'a'][finalRank-49].setPiece(initPiece);
		board[initFile-'a'][initRank-49].setPiece(null);
		
		//if pawn is going diagonal for enpassant remove piece it is passing
		diagnalMoveUpdater(board, initFile, initRank, finalFile, finalRank);
				
		// check for promotion
		promotionChecker(input, board, finalFile, finalRank);
		
		moved(); // set hasMoved to true
		
		return board;
	}

	/**
	 * @param board
	 * @param initFile
	 * @param initRank
	 * @param finalFile
	 * @param finalRank
	 */
	public void diagnalMoveUpdater(Square[][] board, char initFile, char initRank, char finalFile, char finalRank) {
		if (initFile != finalFile && willEnpassant) { 
			if (isWhite && initRank+1 == finalRank && initFile+1 == finalFile)
				board[initFile-'a'+1][initRank-49].setPiece(null);
			if (isWhite && initRank+1 == finalRank && initFile-1 == finalFile)
				board[initFile-'a'-1][initRank-49].setPiece(null);
			
			if (!isWhite && initRank-1 == finalRank && initFile+1 == finalFile)
				board[initFile-'a'+1][initRank-49].setPiece(null);
			if (!isWhite && initRank-1 == finalRank && initFile-1 == finalFile)
				board[initFile-'a'-1][initRank-49].setPiece(null);
		}
	}

	/**
	 * @param input
	 * @param board
	 * @param finalFile
	 * @param finalRank
	 */
	public void promotionChecker(String input, Square[][] board, char finalFile, char finalRank) {
		if (isWhite && finalRank == '8')
			promotion(input, finalFile, finalRank, board, "white");
		if (!isWhite && finalRank == '1')
			promotion(input, finalFile, finalRank, board, "black");
	}
	
	
	public void promotion(String input, char finalFile, char finalRank, Square[][] board, String pieceColor) {
		//Scanner scan = new Scanner(System.in);
		//String input;
		//System.out.print("Choose promotion [Q, N, R, B]: ");
		String[] args = input.split(" ");
		if (args.length == 3) {
			switch(args[2])
			{
				case "Q":
					board[finalFile-'a'][finalRank-49].setPiece(new Queen(pieceColor));
					return;
				case "N":
					board[finalFile-'a'][finalRank-49].setPiece(new Knight(pieceColor));
					return;
				case "R":
					board[finalFile-'a'][finalRank-49].setPiece(new Rook(pieceColor));
					return;
				case "B":
					board[finalFile-'a'][finalRank-49].setPiece(new Bishop(pieceColor));
					return;
			}
		}
		
		// if there is no third argument, create a Queen
		board[finalFile-'a'][finalRank-49].setPiece(new Queen(pieceColor));
	}
	
	
	public boolean canEnpassant(int file, int rank, Square[][] board)
	{
		return validUpdaterForEnpassant(file, rank, board);
	}

	
	public boolean validUpdaterForEnpassant(int file, int rank, Square[][] board) {
		boolean wouldBeUnderAttack = false;
		boolean isWhite = board[file][rank].getPiece().isWhite();

		//Check for different locations to see if piece would have been under attack if it only moved one space up instead of two
		if(isWhite && rank!=0 && file!=7 && board[file + 1][rank].getPiece()!= null && !board[file + 1][rank].getPiece().isWhite()){
			wouldBeUnderAttack = true;
		}
		else if(isWhite && rank!=0 && file!=0 && board[file - 1][rank].getPiece()!= null && !board[file -1][rank].getPiece().isWhite()){
			wouldBeUnderAttack = true;
		}
		else if(!isWhite && rank!=7 && file!=7 && board[file + 1][rank].getPiece()!= null && board[file+1][rank].getPiece().isWhite()){
			wouldBeUnderAttack = true;
		}
		else if(!isWhite && rank!=7 && file!=0 &&board[file - 1][rank].getPiece()!= null && board[file-1][rank].getPiece().isWhite()){
			wouldBeUnderAttack = true;
		}

		return board[file][rank].getPiece().getEnpassant() && wouldBeUnderAttack;
	}
	
	
	
	public boolean getEnpassant()
	{
		return enpassant;
	}
}