package snake;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

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
public class SnakeV0 {

	List<PointV0> snakePoints;
	
	int xDir;
	int yDir;
	boolean isMoving;
	boolean elongate;
	
	final int snakeStartSize = 20;
	final int startX = 150;
	final int startY = 150;
	
	/**
	 * 
	 */
	public SnakeV0() {
		snakePoints = new ArrayList<PointV0>();
		xDir = 0;
		yDir = 0;
		isMoving = false;
		elongate = false;
		snakePoints.add(new PointV0(startX,startY));
		for (int i = 1; i < snakeStartSize;i++) {
			snakePoints.add(new PointV0(startX - i*4, startY));
		}
	}

	public boolean snakeCollision() {
		int x = this.getHeadX();
		int y = this.getHeadY();
		for (int i = 1; i < snakePoints.size(); i++) {
			if ( snakePoints.get(i).getX() == x && snakePoints.get(i).getY() == y ) {
				return true;
			}
		}
		return false;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.white);
		for(PointV0 p : snakePoints) {
			g.fillRect(p.getX(), p.getY(), 4, 4);
		}
	}
	
	public void move() {
		if (isMoving) {
			PointV0 temp = snakePoints.get(0);
			PointV0 last = snakePoints.get(snakePoints.size()-1);
			PointV0 newStart = new PointV0(temp.getX() + xDir*4, temp.getY()+yDir*4);
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
	
	public boolean isMoving() {
		return isMoving;
	}
	
	public void setIsMoving(boolean b) {
		isMoving = b;
	}
	
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
	
	public int getHeadX() {
		return snakePoints.get(0).getX();
	}
	
	public int getHeadY() {
		return snakePoints.get(0).getY();
	}
	
	public void setElongate(boolean b) {
		elongate = b;
	}

}
