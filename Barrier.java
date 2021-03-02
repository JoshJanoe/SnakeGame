package snake;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

/**
 * 
 * @author Josh Janoe
 * @version 2
 * created February 26, 2021
 * 
 * Token/apple object for use in SnakeGame
 *
 */
public class Barrier {

	protected int x;
	protected int y; 
	protected int width;
	protected int height;
		
	protected static Snake snake;
	protected Thread thread;
	
	/**
	 * 
	 */
	public Barrier() {
		//empty constructor for dummy Barrier
	}
	
	public Barrier(int startx, int starty, int w, int h, Snake s) {
		x = startx;
		y = starty;
		width = w;
		height = h;
		
		snake = s;
	}
	
	public void draw(Graphics g) {
		//draw outer barrier
		g.setColor(Color.gray);
		g.fillRect(x, y, width, height);
	}
	
	public boolean barrierCollision() {
		//get location of center of snake head
		int snakeX = snake.getHeadX()+settings.segmentSize/2;
		int snakeY = snake.getHeadY()+settings.segmentSize/2;
		
		if (snakeX >= x && snakeX <= x+width) {
			if (snakeY >= y && snakeY <= y+height) {	
				return true;
			}
		}
		return false;
	}
		
}
