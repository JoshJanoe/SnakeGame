package snake;

import java.awt.Color;
import java.awt.Font;

/**
 * 
 * @author Josh Janoe
 * @version 2
 * created February 26, 2021
 * 
 * Class created to store and alter game 
 * settings outside of main SnakeGame class
 *
 */
public class settings {

	protected static int numTokens = 15;//10;	//number of tokens on screen at any given time
	protected static int totalScore = 0;	//current game score
	protected static int segmentSize = 8;//6;	//size of the snake's body
	protected static int speed = 30;//40;		//increase for slower snake, decrease for faster	
	protected static int windowX = 600;//400;		//window width
	protected static int windowY = 600;//400;		//widnow height
	protected static int timeout = 10;		//number of second before a token automatically disappears and relocates
	protected static int tokenSize = 10;//8		//height and width of tokens
	protected static Color snakeColor = Color.green;		//snake color
	
	protected static boolean darkMode = true;
	protected static Color baseColor = Color.black;		//color of background
	protected static Color accentColor = Color.white;		//color of lines and accents
	protected static Color textColor = Color.red; 		//color of text
	
	protected static boolean wrapMode = false;
	
	static Font titleFont = new Font("Courier", 1, 40);
	static Font regFont = new Font("Courier", 1, 18);
	
	//unused constructor
	public settings() {
				
	}
	
	public static void darkMode(boolean b) {
		darkMode = b;
		if (!darkMode) {
			baseColor = Color.white;
			accentColor = Color.black;
			textColor = Color.blue;
			darkMode = false;
		}
		if (darkMode) {
			baseColor = Color.black;
			accentColor = Color.white;
			textColor = Color.red;
			darkMode = true;
		}
	}
	
}