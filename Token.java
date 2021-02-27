package snake;

import java.awt.Color;
import java.awt.Graphics;

/**
 * 
 * @author Josh Janoe
 * @version 2
 * created February 26, 2021
 * 
 * Token/apple object for use in SnakeGame
 *
 */
public class Token {

	private int x;
	private int y; 
	private int score;
	private int tokenSize;
	private int seconds = 10;
	
	private Snake snake;
	private Thread thread;
	
	private TokenTimer timer;	
	
	/**
	 * 
	 */
	public Token(Snake s) {
		//Math.random() creates random number from 0 to 1
		//SnakeGame.windowX = 400, SnakeGame.segmentSize = 6,
		x = (int)(Math.random() * SnakeGame.windowX-SnakeGame.segmentSize-tokenSize)+tokenSize;
		y = (int)(Math.random() * (SnakeGame.windowY-50)-SnakeGame.segmentSize-tokenSize)+tokenSize;
		
		snake = s;		
		tokenSize = SnakeGame.segmentSize+SnakeGame.segmentSize/2;
		
		//timer = new TokenTimer(seconds,this);
	}
	
	public void changePosition() {
		//Math.random() creates random number from 0 to 1		
		x = (int)(Math.random() * SnakeGame.windowX-SnakeGame.segmentSize-tokenSize)+tokenSize;
		y = (int)(Math.random() * (SnakeGame.windowY-50)-SnakeGame.segmentSize-tokenSize)+tokenSize;
		
		timer = new TokenTimer(seconds,this);
	}

	public int getScore() {
		return score;
	}
	
	
	public void draw(Graphics g) {
		g.setColor(Color.red);
		g.fillRoundRect(x,y,tokenSize,tokenSize,tokenSize,tokenSize);
	}
	
	public boolean snakeCollision() {
		//get location of center of snake head
		int snakeX = snake.getHeadX()+snake.getSegmentSize()/2;
		int snakeY = snake.getHeadY()+snake.getSegmentSize()/2;
		
		if (snakeX >= x-1 && snakeX <= (x + (tokenSize + 1))) {
			if (snakeY >= y-1 && snakeY <= (y + (tokenSize + 1))) {
				changePosition();
				SnakeGame.addPoints();
				snake.grow();
			}
		}
		return false;
	}
	
}
