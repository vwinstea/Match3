/* Piece.java - acts as a piece in a match3 game
 * Author: Vance Winstead
 * Module: Final Project
 *
 * Variables:
 *	Type type - what type of piece it is
 *	JButton button - GUI element that is used in display class
 *	
 *	Constuctors:
 *	Piece() - randomly generates a piece with one of the 7 different types
 *	Piece(int max) - randomly generates a piece with max setting the number of 
 *		options for types (ie max = 2 --> only two types available
 *	Piece(Type t) - generates a piece with a specific type
 *
 * Methods:
 *	toString() - returns the type as a string
 *	equals() - equal if types are the same 
 */

import java.awt.Color;
import javax.swing.JButton;
import java.awt.Font;

public class Piece {
	
	//Variables
	protected Type type;
	protected JButton button;
	
	//Constructors
	public Piece () { //random, all 7 pieces possible
		this(7);
	}
	
	public Piece (int max) { //max is number of types of pieces possible
		int num = (int) (Math.random()*max);
		button = new JButton();
		button.setFont(new Font(null, Font.PLAIN, 30));
		button.setBackground(Color.GRAY);
		switch (num) { //determines visual aspect of buttton based on int that coorilates to type
			case 0:
				type = Type.DIAMOND; 
				button.setForeground(Color.PINK);
				button.setText("\uf074"); //unicode wingding	
				break;
			case 1: 
				type = Type.SQUARE; 
				button.setForeground(Color.RED);
				button.setText("\uf06E"); 
				break;
			case 2: 
				type = Type.CIRCLE; 
				button.setForeground(Color.ORANGE);
				button.setText("\uf06C");	
				break;
			case 3: 
				type = Type.STAR; 
				button.setForeground(Color.YELLOW);
				button.setText("\uf0AB");	
				break;
			case 4:
				type = Type.TRISTAR;  
				button.setForeground(Color.GREEN);
				button.setText("\uf0A9");	
				break;
			case 5:
				type = Type.DROP;  
				button.setForeground(Color.BLUE);
				button.setText("\uf053");	
				break;
			case 6: 
				type = Type.RING; 
				button.setForeground(Color.MAGENTA);
				button.setText("\uf0A3");	
				break;
		}
	}
	
	public Piece (Type t) {
		type = t;
		button = new JButton();
		button.setFont(new Font(null, Font.PLAIN, 30));
		button.setBackground(Color.GRAY);
		switch (type) { //determines visual aspect of buttton based on type
			case DIAMOND:
				button.setForeground(Color.PINK);
				button.setText("\uf074");	
				break;
			case SQUARE: 
				button.setForeground(Color.RED);
				button.setText("\uf06E");
				break;
			case CIRCLE: 
				button.setForeground(Color.ORANGE);
				button.setText("\uf06C");	
				break;
			case STAR: 
				button.setForeground(Color.YELLOW);
				button.setText("\uf0AB");	
				break;
			case TRISTAR: 
				button.setForeground(Color.GREEN);
				button.setText("\uf0A9");	
				break;
			case DROP: 
				button.setForeground(Color.BLUE);
				button.setText("\uf053");	
				break;
			case RING: 
				button.setForeground(Color.MAGENTA);
				button.setText("\uf0A3");	
				break;
		}
	}
	
	//Getters + setters	
	public Type getType () {
		return type;
	}
	public JButton getButton () {
		return button;
	}
	
	//methods
	public String toString () {
		return type.toString();
	}
	
	public boolean equals(Object o) {
		if(o == null || !(o instanceof Piece)) {
			return false;
		}
		Piece otherPiece = (Piece) o;
		return type == otherPiece.getType();
	}

}