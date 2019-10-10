/* Match3Display.java - Displays board and takes in user input
 * Author: Vance Winstead
 * Module: Final Project
 * Problem Statement: Create a GUI that allows the user to interact with the board 
 *		class and play match 3
 * Cotains: Custom exception, GUI
 *
 * Algorithm:
 *	1. Create variables all GUI items listed in step 2 with an astrisk, the board, number of turns, 
 *		highscore, and if the game is being played
 *	2. GUI layout: Border layout
 *		A) PAGE_START: gridLayout(1, 3)
 *			a) flowLayout
 *				I) number of turns*
 *				II) reset button* (don't add, just create)
 *			b) title*
 *			c) flowLayout
 *				I) highscore*
 *				II) score*
 *		B) CENTER : gridLayout(8,8)
 *			a) JButtons from board (also add action listener
 *	3. Create an actionListener where:
 *		a) if the source is the reset button, call restartGame from setp 5
 *		b) if playing, set the source to a selectedButton if there is no slected button
 *			otherwise attempt to swap the source with the selected button and catch 
 *			the InvalidSwapException if it occurcs
 *	4. Create an update method that updates the turn and score textfields and 
 *		replaces the center JPanel with a new JPanel with a redrawn board
 *	5. restartGame method - reset the board, turn, and score, set playing to true, remove reset button
 *	6. endGame method - set playing to false, set new highscore, add reset button
 */

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Match3Display extends JFrame implements ActionListener {
	
	//variables
	private Board board;
	private JButton selectedTile;
	private JPanel grid;
	private JPanel info;
	private JPanel turnPanel;
	private JLabel turnDisplay;
	private int turns;
	private JLabel score;
	private JLabel highscoreDisplay;
	private int highscore;
	private JButton reset;
	private boolean playing;
	private Font commonFont;
	
	public Match3Display () {
		//Set up window
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		setTitle("Match3");
		setSize(1000, 600);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		
		//Info for User at top of page
		commonFont = new Font("Verdana", Font.PLAIN, 18);
		info = new JPanel(new GridLayout(1, 3));
		turnPanel = new JPanel(new FlowLayout(FlowLayout.LEADING)); //Contains turnDisplay and reset
		turns = 20;
		turnDisplay = new JLabel("Turns: " + turns);
		turnDisplay.setFont(commonFont);
		reset = new JButton("Reset");
		reset.setBackground(Color.RED);
		reset.setForeground(Color.WHITE);
		reset.setFont(commonFont);
		reset.addActionListener(this);
		playing = true;
		turnPanel.add(turnDisplay);
		JLabel title = new JLabel("Match 3"); //Title of project
		title.setFont(new Font("Impact", Font.PLAIN, 30));
		JPanel scores = new JPanel(new FlowLayout(FlowLayout.TRAILING)); //Displays score and highscore
		highscoreDisplay = new JLabel("Highscore: 0");
		highscoreDisplay.setFont(commonFont);
		highscore = 0;
		score = new JLabel("Score: 0");
		score.setFont(commonFont);
		scores.add(highscoreDisplay);
		scores.add(score);
		info.add(turnPanel);
		info.add(title);
		info.add(scores);
		this.add(info, BorderLayout.PAGE_START);
		
		//Create Board for first time
		board = new Board(8);
		grid = new JPanel( new GridLayout(board.getSize(), board.getSize()));
		for(int row = 0; row < board.getSize(); row++) {
			for (int col = 0; col < board.getSize(); col++) {
				grid.add(board.getPiece(row, col).getButton());
				board.getPiece(row, col).getButton().addActionListener(this);
			}
		}
		this.add(grid, BorderLayout.CENTER);
	}
	
	//Listener for all buttons in display
	public void actionPerformed (ActionEvent e) {
		JButton source = (JButton) e.getSource();
		if(source.equals(reset)) { //For reset button 
			restartGame();
			return; //ends method
		}
		if(playing) { //For buttons in board
			if(selectedTile == null) {
				selectedTile = source;
				selectedTile.setBorder(BorderFactory.createLineBorder(Color.RED, 4));	
			} else { //If a tile was already selected
				try {
					System.err.println();
					board.swap(selectedTile, source);
					turns--;
					update();
				}
				catch (InvalidSwapException ee) { //custom exception thrown if swap is too far or if swap does not create 3 in a row 
					System.err.println(ee.getMessage()); //error checking
				}
				finally {
					selectedTile.setBorder(BorderFactory.createEmptyBorder()); 
					selectedTile = null; //unselect title
					if(turns == 0) { //end condition
						endGame();
					}
				}
			}
		}
			
		
	}
	
	//Updates JFrame for every change in board
	public void update () {
		turnDisplay.setText("Turns: " + turns);
		score.setText("Score: " + board.getScore());
		this.remove(grid);
		grid = new JPanel( new GridLayout(board.getSize(), board.getSize()));
		for(int row = 0; row < board.getSize(); row++) {
			for (int col = 0; col < board.getSize(); col++) {
					grid.add(board.getPiece(row, col).getButton());
					if(board.getPiece(row, col).getButton().getActionListeners().length == 0) {
						board.getPiece(row, col).getButton().addActionListener(this);
					}
			}
		}
		this.add(grid, BorderLayout.CENTER);
		this.validate();
		this.repaint();
	}
	
	//prepares user for a new game
	public void restartGame() {
		board.resetBoard();
		turns = 20;
		turnDisplay.setText("Turns: " + turns);
		turnPanel.remove(reset);
		update();
		playing = true;
	}
	
	//ends current game if turn limit is reached
	public void endGame() {
		playing = false;
		turnDisplay.setText("Out of turns!");
		turnPanel.add(reset);
		if(board.getScore() > highscore) {
			highscore = board.getScore();
			highscoreDisplay.setText("Highscore: " + highscore);
		}
	}
	
	//creates display
	public static void main (String[] args) {
		Match3Display display = new Match3Display();
		display.setVisible(true);	
	}
	
}