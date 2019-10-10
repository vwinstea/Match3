/* InvalidSwapException.java - exception for failed swap in board class
 * Author: Vance Winstead
 * Module: final projet
 * Contains: custom exception
 *
 * Constructors:
 *	InvalidSwapException() - predefined message
 *	InvalidSwapException(String str) - str is the message
 */

public class InvalidSwapException extends Exception {
	
	public InvalidSwapException () {
 		super("Tiles must be adjacent in order to swap");
 	}
 	
 	public InvalidSwapException (String str) {
 		super(str);
 	}
	
}