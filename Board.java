/* Board.java - stores pieces in a board and allows them to swap and make matches
 * Author: Vance Winstead
 * Module: Final Project
 * Contains: polymorphism, recursion
 *
 * Variables:
 *	- board (Piece[][]) - contains all pieces in the board, polymorphic
 *	- points (int) - number of points awarded in total
 *
 * Constructor:
 *	Board (int size) - creates new board of a given size, then calls resetBoard()
 *
 * Getters and Setters: getSize(), getPiece(), getScore()
 * Methods:
 *	toString () - returns a printed version of theboard
 *	equals () - equal if all types are the same
 *	resetBoard() - fills a board with randomly generated pieces with none in a 
 *		row of 3 or more, sets score back to 0
 *	numInARow (int r, int c) - gives largest number of pieces in a row relative to 
 *		a particular location in any direction
 *	numInARowVertical/NumInARowHorizontal (int r, int c) - gives number of pieces 
 *		in a row in that direction
 *	numInARow (int r, int c, Type t, Direction dir) - recursive, returns the number 
 *		of pieces in a row to left, right up, or down; called by all other numINARow methods
 *	swap(JButton 1, JButton 2) - converts JButtons back to pieces then it attempts to swap them. If the
 *		 pieces are too far from one another or it does not create a match InvalidSwapException is thrown
 *	match3 (int row, int col) - attmpts to find 3 or more in a row and destroy all pieces
 *		that are in that grouping. If grouping is more than three, a special piece is formed and added to the array (polymorphism) 
 *	fallDown() - fills all null spots with pieces from above, if there are no more pieces above
 *		it is filled with a randomly generated piece
 *	 destroy(int row, int col) - destroys a non-null piece and gives points to the
 *		 player. If the piece is a special piece, it's special ability is activated 
 *		 and bonus points are rewarded
 */

import javax.swing.JButton;
import java.util.ArrayList;

public class Board {
	
	//Variables	
	private Piece[][] board;
	private int score;
	
	//Constructor
	public Board (int size) {
		board = new Piece[size][size];
		resetBoard();
	}
	
	//getters
	public int getSize() {
		return board.length;
	}
	public Piece getPiece(int row, int col) {
		return board[row][col];
	}
	public int getScore() {
		return score;
	}
	
	//methods
	//toString and equals
	public String toString () {
		String str = "";
		for(int r = 0; r < board.length; r++) {
			for (int c = 0; c < board.length; c++) {
				str += String.format("%-10s", board[r][c]);
			}
			str += "\n";
		}
		return str;
	}
	
	public boolean equals (Object o) {
		if(o == null || !(o instanceof Board)) {
			return false;
		}
		Board otherBoard = (Board) o;
		if (otherBoard.getSize() != this.getSize()) {
			return false;
		}
		for(int r = 0; r < board.length; r++) {
			for (int c = 0; c < board.length; c++) {
				if(!this.getPiece(r,c).equals(otherBoard.getPiece(r,c))) {
					return false;
				}
			}
		}
		return true;
	}
	

	public void resetBoard () {
		for(int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				do {
					board[row][col] = new Piece();
				} while(numInARow(row, col) >= 3);
			}
		}
		score = 0;
	}
	
	private enum Direction { //used in numInARow()
		LEFT, UP, RIGHT, DOWN
	}
	
	//highest num in row
	public int numInARow (int row, int col) {
		int horizontal = numInARowHorizontal(row, col);
		int vertical = numInARowVertical(row, col);
		if(horizontal > vertical) {
			return horizontal;
		} else {
			return vertical;
		}
	}
	
	//num in a row in row
	public int numInARowHorizontal (int row, int col) {
		return numInARow(row, col - 1, board[row][col].getType(), Direction.LEFT) + 
			numInARow(row, col + 1, board[row][col].getType(), Direction.RIGHT) + 1;
	}
	//num in a row col
	public int numInARowVertical (int row, int col) {
		return numInARow(row - 1, col, board[row][col].getType(), Direction.UP)
			+ numInARow(row + 1, col, board[row][col].getType(), Direction.DOWN) + 1;
	}
	
	//Calculates num in a row
	public int numInARow (int row, int col, Type type, Direction dir) { //recursive method
		if(row < 0 || row >= board.length || col < 0 || col >= board.length) { //out of bounds
			return 0;
		}
		if(board[row][col] == null || type != board[row][col].getType()) { //doesn't match
			return 0;
		}
		if (dir == Direction.UP) { //recursion
			return numInARow(row - 1, col, type, Direction.UP) + 1;
		} else if (dir == Direction.DOWN){
			return numInARow(row + 1, col, type, Direction.DOWN) + 1;
		} else if (dir == Direction.LEFT) {
			return numInARow(row, col - 1, type, Direction.LEFT) + 1;
		} else {
			return numInARow(row, col + 1, type, Direction.RIGHT) + 1;
		}
	}
	
	//swap two pieces
	public void swap(JButton b1, JButton b2) throws InvalidSwapException {
		int r1 = 0, c1 = 0, r2 = 0, c2 = 0;
		for(int row = 0; row < board.length; row++) { //convert buttons back to identifiable pieces
			for (int col = 0; col < board[row].length; col++) {
				if(b1.equals(board[row][col].getButton())) {
					r1 = row;
					c1 = col;
				}
				if(b2.equals(board[row][col].getButton())) {
					r2 = row;
					c2 = col;
				}
			}
		} 
		if((r1 == r2 && Math.abs(c2 - c1) == 1) || (Math.abs(r2 - r1) == 1 && c1 == c2)) {
			Piece temp = board[r1][c1];
			board[r1][c1] = board[r2][c2];
			board[r2][c2] = temp;
			if(r1 > r2 ? !match3(r1, c1) & !match3(r2, c2) : !match3(r2, c2) & !match3(r1, c1)) { //undo swap if match isn't made, destroy top row fist if match is made
				temp = board[r1][c1];
				board[r1][c1] = board[r2][c2];
				board[r2][c2] = temp;
				throw new InvalidSwapException("No match");
			}
		} else {
			throw new InvalidSwapException(r1 + "," + c1 + " " + r2 + "," + c2 + " Too far");
		}
	} 
	
	//If you get 3 or more in a row, destroy all three peices
	public boolean match3 (int row, int col) {
		Piece p = board[row][col];
		int horizontal = numInARowHorizontal(row, col);
		int vertical = numInARowVertical(row, col);
		if (vertical < 3 && horizontal < 3) { //no match
			return false;
		}
		
		//destroy surrounding pieces
		if(horizontal >= 3) {
			for(int i = col - 1; i >= 0 && i < board.length && p.equals(board[row][i]); i--) { //Left
				destroy(row, i);
			}
			for(int i = col + 1; i >= 0 && i < board.length && p.equals(board[row][i]); i++) { //Right
				destroy(row, i);
			}
		}
		if (vertical >= 3) {
			for(int i = row - 1; i >= 0 && i < board.length && p.equals(board[i][col]); i--) { //Up
				destroy(i, col);
			}
			for(int i = row + 1; i >= 0 && i < board.length && p.equals(board[i][col]); i++) { //Down
				destroy(i, col);
			}
		}
		
		//make special piece if applicable
		if (horizontal >= 5 || vertical >= 5) {
			board[row][col] = new SpecialPiece(p.getType(), SpecialType.TARGET);
		} else if(horizontal >= 3 && vertical >= 3) {
			board[row][col] = new SpecialPiece(p.getType(), SpecialType.CROSS);
		} else if (horizontal == 4 || vertical == 4) {
			board[row][col] = new SpecialPiece(p.getType(), SpecialType.BURST);
		} else {
			destroy(row, col);
		}
		fallDown();
		return true;
	}
	
	//replaces all gaps in the board by filling pieces down or generating new ones if none are above
	public void fallDown () {
		System.err.println("falldown"); //used for error checking
		ArrayList<Integer> replacedRows = new ArrayList<Integer>();
		ArrayList<Integer> replacedCols = new ArrayList<Integer>();
		for (int col = 0; col < board.length; col++) {
			for (int row = board.length - 1; row >= 0; row --) {
				if(board[row][col] == null) {
					replacedRows.add(row);
					replacedCols.add(col);
					for (int i = row; i >= 0; i--) {
						if(board[i][col] != null) {
							board[row][col] = board[i][col];
							board[i][col] = null;
							break;
						}
					}
					if(board[row][col] == null) {
						board[row][col] = new Piece();	
					}
				}
			}
		}
		for(int i = 0; i < replacedRows.size(); i++) {
			match3(replacedRows.get(i), replacedCols.get(i));
		}
	}
	
	//Destroys a piece, awards point, and triggers special action for special pieces
	public void destroy (int row, int col) {
		Piece piece = board[row][col];
		if(piece == null) {
			System.err.println("null block"); //error checking
			return;
		}
		board[row][col] = null; //destroy main piece
		score += 100; //+100 points for destroying piece
		System.err.println("Destroying " + row +"," + col); //error checking
		if (piece instanceof SpecialPiece) { //trigger special effects
			SpecialType type = ((SpecialPiece) piece).getSpecialType();
			System.err.println(type); //error checking
			if(type == SpecialType.BURST) { //destroy all pieces in a 1 piece radius
				for(int r = row - 1; r <= row + 1; r++) {
					for (int c = col - 1; c <= col + 1; c++) {
						if(r >= 0 && r < board.length && c >= 0 && c < board.length) { //out of bounds check
							destroy(r, c); 
						} else {
							System.err.println("out of bounds"); //error checking
						}
					}
				}
				score += 250;
			} else if(type == SpecialType.CROSS) { //destroy all pieces in it's row and col
				for(int r = 0; r < board.length; r++) {
					destroy(r, col); 
				}
				for(int c = 0; c < board.length; c++) {
					destroy(row, c); 
				}
				score += 500;
			} else if (type == SpecialType.TARGET) {
				Type t = piece.getType();
				for(int r = 0; r < board.length; r++) {
					for (int c = 0; c < board.length; c++) {
						if(board[r][c] != null && t == board[r][c].getType()) {
							destroy(r, c);
						}
					}
				}
				score += 1000;
			}
			fallDown();
		}
	}
	
}