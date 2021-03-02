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
public abstract class Token {

	Graphics gfx;
	
	protected int x;
	protected int y; 
	protected int score;
	protected int seconds = 10;
	
	protected Snake snake;
	protected Thread thread;
	
	private long startTime;
	private long elapsedTime;
	Random myRandom = new Random();
	int variableTime;
		
	/**
	 * 
	 */
	public Token(Snake s, Graphics g) {
		//Math.random() creates random number from 0 to 1
		//SnakeGame.windowX = 400, SnakeGame.segmentSize = 6,
		x = (int)(Math.random() * settings.windowX-settings.segmentSize-settings.tokenSize)+settings.tokenSize;
		y = (int)(Math.random() * (settings.windowY-50)-settings.segmentSize-settings.tokenSize)+settings.tokenSize;
		
		snake = s;
		gfx = g;
		startTime = System.nanoTime();
		variableTime = (int)(Math.random() * 100)+100;
	}
	
	public boolean timedMove() {
		long currTime = System.nanoTime();
		elapsedTime = (currTime - startTime)/100000000;
		if (elapsedTime == variableTime) {
			SnakeGame.replaceToken(this);
			return true;
		}
		return false;
	}
	
	public void fade(Color c) {
		long currTime = System.nanoTime();
		elapsedTime = (currTime - startTime)/100000000;
		int time = Math.toIntExact(elapsedTime);
		//double liveTime = 200 - Math.random()*100;
		//double fadeTime = liveTime-20;
		if (time % 2 == 0 && time > variableTime-20) {
			gfx.setColor(settings.baseColor);
			gfx.fillRoundRect(x,y,settings.tokenSize,settings.tokenSize,settings.tokenSize,settings.tokenSize);
			gfx.setColor(c);
			gfx.drawRoundRect(x,y,settings.tokenSize,settings.tokenSize,settings.tokenSize,settings.tokenSize);
		}
		if (time % 2 != 0 && time > variableTime-20) {
			gfx.setColor(c);
			gfx.fillRoundRect(x,y,settings.tokenSize,settings.tokenSize,settings.tokenSize,settings.tokenSize);
			gfx.setColor(settings.accentColor);
			gfx.drawRoundRect(x,y,settings.tokenSize,settings.tokenSize,settings.tokenSize,settings.tokenSize);
		}		
	}
	
	public String getElapsedTime() {
		return String.valueOf(elapsedTime);
	}
	
	public boolean snakeCollision() {
		int padding = settings.segmentSize/2;
		//get location of center of snake head
		int snakeX = snake.getHeadX()+padding;
		int snakeY = snake.getHeadY()+padding;
		
		if (snakeX >= x-padding && snakeX <= (x + (settings.tokenSize + padding))) {
			if (snakeY >= y-padding && snakeY <= (y + (settings.tokenSize + padding))) {	
				tokenAcquired();
				return true;
			}
		}
		return false;
	}
	
	public void draw(Graphics g) {}
	
	public void tokenAcquired() {}
	
}
