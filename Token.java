package snake;

import java.awt.Color;
import java.awt.Graphics;

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
public class Token {

	private int x;
	private int y; 
	private int score;
	
	private Snake snake;
	
	private int tokenSize;
	
	/**
	 * 
	 */
	public Token(Snake s) {
		//Math.random() creates random number from 0 to 1
		x = (int)(Math.random() * SnakeGame.windowX-SnakeGame.segmentSize+1);
		y = (int)(Math.random() * SnakeGame.windowY-SnakeGame.segmentSize+1);
		
		snake = s;		
		tokenSize = SnakeGame.segmentSize+SnakeGame.segmentSize/2;
	}
	
	public void changePosition() {
		//Math.random() creates random number from 0 to 1
		x = (int)(Math.random() * SnakeGame.windowX-SnakeGame.segmentSize+1);
		y = (int)(Math.random() * SnakeGame.windowY-SnakeGame.segmentSize+1);
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
				score += 100;
				snake.setElongate(true);
				snake.setElongate(true);
				return true;
			}
		}
		return false;
	}

}
