package snake;

/**
 * 
 * @author Josh Janoe
 * @version 0
 * created February 24, 2021
 * 
 * Basic snake game created using online tutorial by Krohn - Education on YouTube
 * Tutorial can be found at https://www.youtube.com/watch?v=9eQJAWhRHQg&feature=emb_logo
 *
 */
public class PointV0 {

	private int xCoord;
	private int yCoord;
	
	public PointV0() {
		xCoord=0;
		yCoord=0;
	}
	
	public PointV0(int x, int y) {
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
