package snake;

/**
 * 
 * @author Josh Janoe
 * @version 2
 * created February 26, 2021
 * 
 * Point object for use in SnakeGame
 *
 */
public class Point {

	private int xCoord;
	private int yCoord;
	
	public Point() {
		xCoord=0;
		yCoord=0;
	}
	
	public Point(int x, int y) {
		xCoord = x;
		yCoord = y;
	}
	
	public void setX(int x) {
		xCoord = x;
	}
	
	public void setY(int y) {
		yCoord = y;
	}
	
	public int getX() {
		return xCoord;
	}
	
	public int getY() {
		return yCoord;
	}

}
