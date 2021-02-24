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
 * @version 0
 * created February 24, 2021
 * 
 * Basic snake game created using online tutorial by Krohn - Education on YouTube
 * Tutorial can be found at https://www.youtube.com/watch?v=9eQJAWhRHQg&feature=emb_logo
 *
 */
public class SnakeGameV0 extends Applet implements Runnable, KeyListener{

	Graphics gfx;
	Image img;
	Thread thread;
	SnakeV0 snake;
	TokenV0 token;
	boolean gameOver;
	
	public SnakeGameV0() {
		
	}
	
	
	public void init() {
		this.resize(400,400);
		gameOver = false;
		img = createImage(400, 400);
		gfx = img.getGraphics();
		this.addKeyListener(this);
		
		snake = new SnakeV0();
		token = new TokenV0(snake);
		
		thread = new Thread(this);
		thread.start();
	}
	
	public void paint(Graphics g) {
		gfx.setColor(Color.black);
		gfx.fillRect(0, 0, 400, 400);
		
		if (!gameOver) {
			snake.draw(gfx);
			token.draw(gfx);
		}
		else {
			gfx.setColor(Color.RED);
			gfx.drawString("Game Over", 150, 150);
			gfx.drawString("Score: " + token.getScore(), 150, 200);
		}		
				
		g.drawImage(img,0,0,null);
		
	}
	
	public void update(Graphics g) {
		paint(g);
	}
	
	public void repaint(Graphics g) {
		paint(g);
	}
	
	public void run() {
		for(;;) {
			
			if(!gameOver) {
				snake.move();
				this.checkGameOver();
				token.snakeCollision();
			}
						
			this.repaint();
			try {
				Thread.sleep(40);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public void checkGameOver() {
		if(snake.getHeadX() < 0 || snake.getHeadX() > 396) {
			gameOver = true;
		}
		if(snake.getHeadY() < 0 || snake.getHeadY() > 396) {
			gameOver = true;
		}
		if (snake.snakeCollision()) {
			gameOver = true;
		}
						
	}

	public void keyPressed(KeyEvent e) {
		if (!snake.isMoving()) {
			if (e.getKeyCode() == KeyEvent.VK_UP 
					|| e.getKeyCode() == KeyEvent.VK_DOWN 
					|| e.getKeyCode() == KeyEvent.VK_RIGHT) {
				snake.setIsMoving(true);
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			if(snake.getYDir() != 1) {
				snake.setYDir(-1);
				snake.setXDir(0);
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			if(snake.getYDir() != -1) {
				snake.setYDir(1);
				snake.setXDir(0);
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if(snake.getXDir() != 1) {
				snake.setXDir(-1);
				snake.setYDir(0);
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if(snake.getXDir() != -1) {
				snake.setXDir(1);
				snake.setYDir(0);
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
