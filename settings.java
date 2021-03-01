package snake;

import java.awt.Color;
import java.util.HashMap;

/**
 * Added so far:
 * 		-title screen
 * 		-made snake head slightly larger than body
 * 		-multiple apples/tokens
 * 		-added timer for tokens but only for after first collision


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

	protected static int numTokens = 10;
	protected static int totalScore = 0;
	protected static int segmentSize = 6;
	protected static int speed = 40;	//increase for slower snake, decrease for faster	
	protected static int windowX = 400;
	protected static int windowY = 400;
	protected static int timeout = 10;
	protected static int tokenSize = 8;
	protected static Color color = Color.green;
	
	public settings() {
				
	}
	
//	public static void addPoints(int points) {
//		totalScore += points;
//	}

//	/**
//	 * @return the numTokens
//	 */
//	public static int getNumTokens() {
//		return numTokens;
//	}
//
//	/**
//	 * @param numTokens the numTokens to set
//	 */
//	public static void setNumTokens(int num) {
//		numTokens = num;
//	}
//
//	/**
//	 * @return the totalScore
//	 */
//	public static int getTotalScore() {
//		return totalScore;
//	}
//
//	/**
//	 * @param totalScore the totalScore to set
//	 */
//	public static void setTotalScore(int score) {
//		totalScore = score;
//	}
//
//	/**
//	 * @return the segmentSize
//	 */
//	public static int getSegmentSize() {
//		return segmentSize;
//	}
//
//	/**
//	 * @param segmentSize the segmentSize to set
//	 */
//	public static void setSegmentSize(int size) {
//		segmentSize = size;
//	}
//
//	/**
//	 * @return the speed
//	 */
//	public static int getSpeed() {
//		return speed;
//	}
//
//	/**
//	 * @param speed the speed to set
//	 */
//	public static void setSpeed(int s) {
//		speed = s;
//	}
//
//	/**
//	 * @return the windowX
//	 */
//	public static int getWindowX() {
//		return windowX;
//	}
//
//	/**
//	 * @param windowX the windowX to set
//	 */
//	public static void setWindowX(int X) {
//		windowX = X;
//	}
//
//	/**
//	 * @return the windowY
//	 */
//	public static int getWindowY() {
//		return windowY;
//	}
//
//	/**
//	 * @param windowY the windowY to set
//	 */
//	public static void setWindowY(int Y) {
//		windowY = Y;
//	}
//
//	/**
//	 * @return the color
//	 */
//	public static Color getColor() {
//		return color;
//	}
//
//	/**
//	 * @param color the color to set
//	 */
//	public static void setColor(Color c) {
//		color = c;
//	}
//	
//	
	
}