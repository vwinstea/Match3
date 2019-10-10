/* SpecialPiece - special pieces that have special effects when destroyed
 * Author: Vance Winstead
 * Module: Final Project
 * Contains: Inheritance
 *
 * Variable:
 *	specialType otherType- specialType of piece that has special effect when destroyed	
 *
 * Constuctor:
 *	SpecialPiece(Type t, SpecialType st) - creates specialpeice with predefined type and specialType
 *
 *	Method:
 *	getSpecialType()
 */

public class SpecialPiece extends Piece {
	
	private SpecialType otherType;
	
	SpecialPiece (Type t, SpecialType oT) {
		super(t);
		otherType = oT;
		switch(otherType) {
			case BURST: button.setText(button.getText() + "\uf04D"); //unicode wingding
			break;
			case CROSS: button.setText(button.getText() + "\uf058");
			break;
			case TARGET: button.setText(button.getText() + "\uf0B1");
			break;
		}
	}
	
	public SpecialType getSpecialType () {
		return otherType;
	}
	
}