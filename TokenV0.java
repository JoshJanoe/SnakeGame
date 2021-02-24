package snake;

import java.awt.Color;
import java.awt.Graphics;

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
public class TokenV0 {

	private int x;
	private int y; 
	private int score;
	
	private SnakeV0 snake;
	
	public TokenV0(SnakeV0 s) {
		x = (int)(Math.random() * 395);
		y = (int)(Math.random() * 395);
		
		snake = s;			
	}
	
	public void changePosition() {
		x = (int)(Math.random() * 395);
		y = (int)(Math.random() * 395);
	}

	public int getScore() {
		return score;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.green);
		g.fillRect(x,y,6,6);
	}
	
	public boolean snakeCollision() {
		int snakeX = snake.getHeadX() + 2;
		int snakeY = snake.getHeadY() + 2;
		if (snakeX >= (x - 1) && snakeX <= (x + 7)) {
			if (snakeY >= (y - 1) && snakeY <= (y + 7)) {
				changePosition();
				score ++;
				snake.setElongate(true);
				return true;
			}
		}
		return false;
	}

}
