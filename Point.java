package snake;

/**
 * 
 * @author Josh Janoe
 * @version 1
 * created February 24, 2021
 * 
 * Basic snake game created using online tutorial by Krohn - Education on YouTube
 * Tutorial can be found at https://www.youtube.com/watch?v=9eQJAWhRHQg&feature=emb_logo
 * Code for initial version can be found as Version 0 (V0), changes listed above
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
