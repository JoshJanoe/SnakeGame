/**
 * Added so far:
 * 		-changed some basic formatting
 * 		-adjusted variable names
 * 		-clarifying comments
 * 		-pause feature
 * 		-instance variables for screen size, snake segment size, token/apple size
 * 		-instance variable for speed
 * 		-changed token to circle/rounded rectangle
 * 		-created clearer snake segments using rectangle outline
 * 
 * To add:
 * 		-limited apple time
 * 		-multiple apples/tokens
 * 		-variable apple score/abilities based on color
 * 			-red = slower, green = faster, blue = double points, purple = triple points, etc..
 * 		-border wall
 * 			-replace out of bounds game over with wrap around
 * 		-new game/restart
 * 		-difficulty
 * 			-adjust speed, number of apples, number of walls
 * 		-levels
 * 			-prompt to progress after set number of points
 * 		-running scoreboard at top of window
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
 * @version 1
 * created February 24, 2021
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
	boolean gameOver;
	boolean paused;
	
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
		this.resize(windowX,windowY);
		gameOver = false;
		paused = false;
		img = createImage(windowX, windowY);
		gfx = img.getGraphics();
		this.addKeyListener(this);
		
		snake = new Snake();
		token = new Token(snake);
		
		thread = new Thread(this);
		thread.start();
	}
	
	public void paint(Graphics g) {
		gfx.setColor(Color.black);
		gfx.fillRect(0, 0, windowX, windowY);
		
		if (!gameOver && !paused) {
			snake.draw(gfx);
			token.draw(gfx);
		}
		else if (paused) {
			gfx.setColor(Color.RED);
			gfx.drawString("PAUSE", windowX/2-50, windowY/2-50);
			//lock snake head x and y while paused
			snake.setIsMoving(false);			
		}
		else {
			gfx.setColor(Color.RED);
			gfx.drawString("Game Over", windowX/2-50, windowY/2-50);
			gfx.drawString("Score: " + token.getScore(), windowX/2-50, windowY/2);
		}		
				
		g.drawImage(img,0,0,null);
		
		//add border
	}
	
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
		if(snake.getHeadY() < 0 || snake.getHeadY() > windowY-segmentSize) {
			gameOver = true;
		}
		//if snake hits self
		if (snake.snakeCollision()) {
			gameOver = true;
		}
		// TODO add scenario for if snake hits wall
				
	}

	public void keyPressed(KeyEvent e) {
		//remember 1 is down in Y and right in X
		//if snake is moving
		if (!snake.isMoving()) {
			//if directional key pressed other than head-to-tail direction
			if (e.getKeyCode() == KeyEvent.VK_UP 
					|| e.getKeyCode() == KeyEvent.VK_DOWN 
					|| e.getKeyCode() == KeyEvent.VK_RIGHT) {
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
		
		//if "enter" is pressed on keyboard while game over
//		if (gameOver) {		
//			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
//					gameOver = false;
//					SnakeGame();
//			}
//		}
	}

	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
			
}
