/**
 * Added so far:
 * 		-title screen
 * 
 * To add:
 * 		-limited apple time
 * 		-multiple apples/tokens
 * 		-variable apple score/abilities based on color
 * 			-yellow = slower/smaller, green = faster/bigger
 * 			-blue = double points, purple = triple points, etc..
 * 		-border wall
 * 			-replace out of bounds game over with wrap around
 * 			-create opening in outer border
 * 			-add borders/obstacles inside play area
 * 		-difficulty
 * 			-adjust speed, number of apples, number of walls
 * 		-levels
 * 			-prompt to progress after set number of points
 * 		-highscore 		
 * 		-options screen
 */
package snake;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * 
 * @author Josh Janoe
 * @version 2
 * created February 26, 2021
 * 
 * Basic snake game created using online tutorial by Krohn - Education on YouTube
 * Tutorial can be found at https://www.youtube.com/watch?v=9eQJAWhRHQg&feature=emb_logo
 * Code for initial version can be found as Version 0 (V0), changes listed above
 *
 */
public class SnakeGame extends Applet implements Runnable, KeyListener{

	Graphics gfx;
	Image img;
	Thread thread;
	Snake snake;
	Token token;
	Token token2;
	boolean gameOver;
	boolean paused;
	boolean titleScreen;
	
	static int windowX = 400;
	static int windowY = 400;
	static int segmentSize = 6;
	static int speed = 40;	//increase for slower snake, decrease for faster
	
	
	/**
	 * 
	 */
	public SnakeGame() {
		// TODO Auto-generated constructor stub
	}
	
	
	public void init() {
		//set window area 
		this.resize(windowX,windowY);
		//when game is in play gameOver and paused are false
		gameOver = false;
		paused = false;
		titleScreen = true;
		//set viewable area
		img = createImage(windowX,windowY);
		gfx = img.getGraphics();
		this.addKeyListener(this);
		
		snake = new Snake();
		token = new Token(snake);
		token2 = new Token(snake);
		
		thread = new Thread(this);
		thread.start();
	}
	
	public void paint(Graphics g) {
		//draw app area
		gfx.setColor(Color.black);
		gfx.fillRect(0, 0, windowX, windowY);
		//create section for scroeboard
		gfx.setColor(Color.white);
		gfx.drawLine(0, windowY-50, windowX, windowY-50);
		//total and configure score
		int totalScore = token.getScore()+token2.getScore();
		gfx.drawString("Score: " + totalScore, windowX/2+50, windowY-20);
		gfx.drawString("Pause: Spacebar", 50, windowY-20);
		
		//start by loading title screen
		if (titleScreen) {
			titleScreen();
		}		
		//if not gameOver or paused continue playing
		if (!gameOver && !paused && !titleScreen) {
			playScreen();
		}
		//if paused hide game and halt movement
		else if (paused) {
			pauseScreen();			
		}
		//if gameOver end game and show final score
		else if (gameOver) {
			gameOverScreen();
		}		
				
		g.drawImage(img,0,0,null);
		
		//add border
	}
	
	/**
	 * breakdown of different game screens for simplicity
	 * possible additions include optionsScreen() and titleScreen()
	 */
	private void titleScreen() {
		gfx.setColor(Color.RED);
		gfx.drawString("SNAKE", windowX/2-30, windowY/2-75);
		gfx.drawString("by JKnow Media", windowX/2-55, windowY/2-50);
		gfx.drawString("Press ENTER to begin", windowX/2-70, windowY/2);
	}
	
	private void playScreen() {
		snake.draw(gfx);
		token.draw(gfx);
		token2.draw(gfx);
	}	
	
	private void pauseScreen() {
		gfx.setColor(Color.RED);
		gfx.drawString("PAUSE", windowX/2-50, windowY/2-50);
		//lock snake head x and y while paused
		snake.setIsMoving(false);
	}
	
	private void gameOverScreen() {
		int totalScore = token.getScore()+token2.getScore();
		
		gfx.setColor(Color.RED);
		gfx.drawString("Game Over", windowX/2-50, windowY/2-50);
		gfx.drawString("Score: " + totalScore, windowX/2-50, windowY/2);
		gfx.drawString("Press ENTER to try again", windowX/2-85, windowY/2+50);
	}
	
	/**
	 * basic game function and drawing
	 */
	public void update(Graphics g) {
		paint(g);
	}
	
	public void repaint(Graphics g) {
		paint(g);
	}
	
	public void run() {
		//infinite loop
		for(;;) {
			//if not game over
			if(!gameOver) {
				//move snake
				snake.move();
				//check for game over
				this.checkGameOver();
				//check for token collision
				token.snakeCollision();
				token2.snakeCollision();
			}
						
			this.repaint();
			try {
				//increase for slower snake, decrease for faster
				Thread.sleep(speed);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void checkGameOver() {
		//if snake goes out of bounds
		if(snake.getHeadX() < 0 || snake.getHeadX() > windowX-segmentSize) {
			gameOver = true;
		}
		if(snake.getHeadY() < 0 || snake.getHeadY() > windowY-segmentSize-50) {
			gameOver = true;
		}
		//if snake hits self
		if (snake.snakeCollision() && snake.snakeSize > snake.snakeStartSize) {
			gameOver = true;
		}
		// TODO add scenario for if snake hits wall
				
	}

	public void keyPressed(KeyEvent e) {
		//remember 1 is down in Y and right in X
		//if snake is moving
		if (!snake.isMoving()) {
			//if directional key pressed other than start direction
			if ((e.getKeyCode() == KeyEvent.VK_UP && snake.getYDir() != 1)
				|| (e.getKeyCode() == KeyEvent.VK_DOWN && snake.getYDir() != -1)
				|| (e.getKeyCode() == KeyEvent.VK_RIGHT && snake.getXDir() != -1)
				|| (e.getKeyCode() == KeyEvent.VK_LEFT && snake.getXDir() != 1)) {
				//set isMoving to true
				snake.setIsMoving(true);
			}
		}
		//if "up" is pressed on keyboard
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			//if current direction is not equal to 1 (downward in window)			
			if(snake.getYDir() != 1) {
				//set new y direction to -1 (upward in window) and do not change x direction
				snake.setYDir(-1);
				snake.setXDir(0);
			}
		}
		//if "down" is pressed on keyboard
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			//if current direction is not equal to -1 (upward in window)
			if(snake.getYDir() != -1) {
				//set new y direction to 1 (downward in window) and do not change x direction
				snake.setYDir(1);
				snake.setXDir(0);
			}
		}
		//if "left" is pressed on keyboard
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			//if current direction is not equal to 1 (right in window)
			if(snake.getXDir() != 1) {
				//set new x direction to -1 (left in window) and do not change y direction
				snake.setXDir(-1);
				snake.setYDir(0);
			}
		}
		//if "right" is pressed on keyboard
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			//if current direction is not equal to -1 (left in window)
			if(snake.getXDir() != -1) {
				//set new x direction to 1 (right in window) and do not change y direction
				snake.setXDir(1);
				snake.setYDir(0);
			}
		}
		
		//if "spacebar" is pressed on keyboard
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			//if not currently paused
			if(!paused) {
				//pause game
				paused = true;
			}
			//if already paused
			else {
				//un-pause game
				paused = false;
			}
		}
		
		if (gameOver || titleScreen) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				token.resetScore();
				token2.resetScore();
				gameOver = false;
				titleScreen = false;
				snake = new Snake();
				token = new Token(snake);
				token2 = new Token(snake);
			}			
		}
				
	}

	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
			
}

