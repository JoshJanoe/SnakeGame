/**
 * 
 */
package snake;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

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
public class Snake {

	List<Point> snakePoints;
	
	int xDir;
	int yDir;
	boolean isMoving;
	boolean elongate;
	
	final int snakeStartSize = 3;
	final int startX;
	final int startY;
	
	final int segmentSize = 6;
	int snakeSize;
	
	/**
	 * 
	 */
	public Snake() {
		snakePoints = new ArrayList<Point>();
		xDir = 0;
		yDir = 0;
		isMoving = false;
		elongate = false;
		
		startX = SnakeGame.windowX/2;
		startY = SnakeGame.windowY/2;
		snakeSize = snakeStartSize;
		
		snakePoints.add(new Point(startX,startY));
		for (int i = 1; i < snakeStartSize;i++) {
			snakePoints.add(new Point(startX - i*segmentSize, startY));
		}
	}

	public boolean snakeCollision() {
		//get current head position
		int x = this.getHeadX();
		int y = this.getHeadY();
		//check against current body/segment positions
		for (int i = 1; i < snakePoints.size(); i++) {
			if ( snakePoints.get(i).getX() == x && snakePoints.get(i).getY() == y ) {
				return true;
			}
		}
		return false;
	}
	
	public void draw(Graphics g) {				
		//set main color of snake
		g.setColor(Color.green);
		//draw rectangle for each snake segment
		for(Point p : snakePoints) {
			g.fillRect(p.getX(), p.getY(), segmentSize, segmentSize);
		}
		
		//set color of snake segment outline
		g.setColor(Color.white);
		//draw rectangle for each snake segment outline
		for(Point p : snakePoints) {
			g.drawRect(p.getX(), p.getY(), segmentSize, segmentSize);
		}
	}
	
	//moves snake by redrawing 
	public void move() {
		if (isMoving) {
			//first need to get current location
			Point start = snakePoints.get(0);
			Point last = snakePoints.get(snakePoints.size()-1);
			//set new location
			Point newStart = new Point(start.getX() + xDir*segmentSize, start.getY()+yDir*segmentSize);
			//each point becomes the point that previously preceded it
			for (int i=snakePoints.size()-1; i >=1; i--) {
				snakePoints.set(i, snakePoints.get(i-1));
			}
			snakePoints.set(0, newStart);
			if (elongate) {
				snakePoints.add(last);
				elongate = false;
			}
		}
				
	}
	
	public void grow() {
		elongate = true;
		snakeSize++;
	}
	
	public boolean isMoving() {
		return isMoving;
	}
	
	public void setIsMoving(boolean b) {
		isMoving = b;
	}
	
	public int getSegmentSize() {
		return segmentSize;
	}
	
	public void setElongate(boolean b) {
		elongate = b;
	}
	
	/**
	 * Basic X and Y getters and setters
	 * */
	public int getXDir() {
		return xDir;
	}
	
	public int getYDir() {
		return yDir;
	}
	
	public void setXDir(int x) {
		xDir = x;
	}
	
	public void setYDir(int y) {
		yDir = y;
	}
	
	//x position of head of snake
	public int getHeadX() {
		return snakePoints.get(0).getX();
	}
	
	//y position of head of snake
	public int getHeadY() {
		return snakePoints.get(0).getY();
	}
	
}
