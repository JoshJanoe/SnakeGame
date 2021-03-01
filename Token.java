package snake;

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
public abstract class Token {

	protected int x;
	protected int y; 
	protected int score;
	protected int seconds = 10;
	
	protected Snake snake;
	protected Thread thread;
	
	private long startTime;
	private long elapsedTime;
	Random myRandom = new Random();
		
	/**
	 * 
	 */
	public Token(Snake s) {
		//Math.random() creates random number from 0 to 1
		//SnakeGame.windowX = 400, SnakeGame.segmentSize = 6,
		x = (int)(Math.random() * settings.windowX-settings.segmentSize-settings.tokenSize)+settings.tokenSize;
		y = (int)(Math.random() * (settings.windowY-50)-settings.segmentSize-settings.tokenSize)+settings.tokenSize;
		
		snake = s;	
		startTime = System.nanoTime();
	}
	
	public boolean timedMove() {
		long currTime = System.nanoTime();
		elapsedTime = (currTime - startTime)/1000000000;
		if (elapsedTime == 10) {
			tokenAcquired();
			return true;
		}
		return false;
	}
	
	public String getElapsedTime() {
		return String.valueOf(elapsedTime);
	}
	
	public void draw(Graphics g) {}
	
	public boolean snakeCollision() {
		//get location of center of snake head
		int snakeX = snake.getHeadX()+settings.segmentSize/2;
		int snakeY = snake.getHeadY()+settings.segmentSize/2;
		
		if (snakeX >= x-1 && snakeX <= (x + (settings.tokenSize + 1))) {
			if (snakeY >= y-1 && snakeY <= (y + (settings.tokenSize + 1))) {	
				tokenAcquired();
				return true;
			}
		}
		return false;
	}
	
	public void tokenAcquired() {}
	
	private Token getTokenType(int num) {
		if (num<=60) return new redAppleToken(snake);
		if (num>60 && num <= 85) return new greenAppleToken(snake);
		if (num>85 && num <=89) return new yellowAppleToken(snake);
		if (num>89 && num <=91.5) return new growToken(snake);
		if (num>91.5 && num <=94) return new shrinkToken(snake);
		if (num>94 && num <=96.5) return new speedToken(snake);
		if (num>96.5 && num <=99) return new slowToken(snake);
		return new redAppleToken(snake);
	}
	
}
