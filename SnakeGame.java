/**
 * Added so far:
 * 		-title screen
 * 		-made snake head slightly larger than body
 * 		-multiple apples/tokens
 * 		-added timer for tokens but only for after first collision
 * 		-apple timer for first location
 * 		-variable apple score/abilities based on color
 * 			-cyan = slower/smaller, faster/bigger
 * 			-green = x2.5 points, yellow = x5 points, etc..
 * 		-add settings class to store game settings/options variables
 * 		-added option/variable for allowing screen wrap when out of bounds
 * 		-create fading effect before tokens disappear
 * 		-randomized token active time
 * 
 * To add:
 * 		-border wall
 * 			-replace out of bounds game over with wrap around
 * 			-create opening in outer border
 * 			-add borders/obstacles inside play area
 * 		-difficulty
 * 			-adjust speed, number of apples, number of walls
 * 		-levels
 * 			-prompt to progress after set number of points
 * 		-high score 		
 * 		-options screen
 * 		-use Damped Sine Function and opacity for fade()
 */
package snake;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


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

	static Graphics gfx;
	static Image img;
	static Thread thread;
	static Snake snake;	
	static List<Token> tokens;
	static boolean gameOver, paused, titleScreen;	
	static Random myRandom = new Random();
	static Font heading, regular;
	
	Long startTime;
	Long currTime;
	
	//static Barrier barrier = new Barrier();
	static List<Barrier> barriers;
	
	public SnakeGame() {
		// TODO Auto-generated constructor stub
		barriers = new ArrayList<Barrier>();
	}
	
	/**
	 * Initialize game, create window, 
	 * set variables to default values
	 * and create necessary game objects
	 */
	public void init() {
		//set window area 
		this.resize(settings.windowX,settings.windowY);
		//when game is in play gameOver and paused are false
		gameOver = false;
		paused = false;
		titleScreen = true;
		
		//set viewable area
		img = createImage(settings.windowX,settings.windowY);
		gfx = img.getGraphics();
		this.addKeyListener(this);
		
		//create game objects
		snake = new Snake();
		tokens = getTokens();
		
		thread = new Thread(this);
		thread.start();
		
		barrierMap1();
		startTime = System.nanoTime();
		settings.darkMode(true);
		
	}
	
	/**
	 * compiles list of randomly generated tokens
	 * according to predetermined probability
	 * 
	 * will later make Token class abstract and
	 * replace/remove abstractToken class
	 * 
	 * @return list of random tokens
	 */
	public ArrayList<Token> getTokens() {
		//create empty array of tokens to late return
		ArrayList<Token> tokenList = new ArrayList<Token>();
		//create numTokens number of tokens using loop
		for (int i = 0; i < settings.numTokens; i++) {
			//create random number for token type generation
			int rng = myRandom.nextInt(100);
			//create new token
			Token currToken = getTokenType(rng);
			//add token to array
			tokenList.add(currToken);
		}
		//return list of random tokens
		return tokenList;
	}
	
	/**
	 * Use preset values to generate tokens based on random input number
	 * 
	 * @param num is a random number used to select a token based on preset parameters
	 * @return a new token object
	 */
	private static Token getTokenType(int num) {
		//if num is 0-59 create red apple, 60% chance
		if (num<=59) return new redAppleToken(snake, gfx);
		//if num is 60-84 create red apple, 25% chance
		if (num>59 && num <= 84) return new greenAppleToken(snake, gfx);
		//if num is 85-89 create red apple, 5% chance
		if (num>84 && num <=89) return new yellowAppleToken(snake, gfx);
		//if num is 89-91.5 create red apple, 2.5% chance
		if (num>89 && num <=91.5) return new growToken(snake, gfx);
		//if num is 91.5-94 create red apple, 2.5% chance
		if (num>91.5 && num <=94) return new shrinkToken(snake, gfx);
		//if num is 94-96.5 create red apple, 2.5% chance
		if (num>94 && num <=96.5) return new speedToken(snake, gfx);
		//if num is 96.5-99 create red apple, 2.5% chance
		if (num>96.5 && num <=99) return new slowToken(snake, gfx);
		//if all else fails, place a standard red apple
		return new redAppleToken(snake, gfx);
	}
	
	/**
	 * Draws appropriate graphics for given screen
	 * based on game variable/booleans
	 * 
	 * Includes: Title Screen, Play Screen
	 * 			Pause Screen, and Game Over Screen
	 * 
	 * TODO: add Options/Settings screen and HighScores Screen
	 */
	public void paint(Graphics g) {
		//update time
		Long timeNow = System.nanoTime();
		currTime = (timeNow - startTime)/1000000000;
		//draw app area
		gfx.setColor(settings.baseColor);
		gfx.fillRect(0, 0, settings.windowX, settings.windowY);
		gfx.setColor(Color.gray);
		gfx.drawRect(0, 0, settings.windowX-1, settings.windowY-1);
		//create section for scoreboard
		gfx.setColor(settings.accentColor);
		gfx.drawLine(0, settings.windowY-50, settings.windowX, settings.windowY-50);
		//total and configure score
		gfx.setColor(settings.textColor);
		gfx.drawString("Score: " + settings.totalScore, settings.windowX-200, settings.windowY-20);
		gfx.drawString("Pause: Spacebar", 20, settings.windowY-20);
		//gfx.drawString("Timer:"+currTime, settings.windowX/2-20, settings.windowY-20);
		
		//start by loading title screen
		if (titleScreen) {
			titleScreen();
		}		
		//if not gameOver or paused continue playing
		if (!gameOver && !paused && !titleScreen) {
			currTime = 0L;
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
		
		//TODO add border
	}
	
	/**
	 * breakdown of different game screens for simplicity
	 * possible additions include optionsScreen() and titleScreen()
	 */
	private void titleScreen() {		
		gfx.setFont(settings.titleFont);
		gfx.setColor(settings.textColor);		
		gfx.drawString("SNAKE", settings.windowX/2-80, settings.windowY/2-75);		
		gfx.setFont(settings.regFont);
		gfx.drawString("by JKnow Media", settings.windowX/2-105, settings.windowY/2-50);
		gfx.drawString("Press ENTER to begin", settings.windowX/2-120, settings.windowY/2);
	}
	
	/**
	 * Displays objects (snake and tokens) while the game is in play
	 */
	private void playScreen() {
		snake.draw(gfx);
		for (Token t : tokens) {
			t.draw(gfx);
		}		
		for (Barrier b : barriers) {
			//b.draw(gfx);
		}
	}	
	
	/**
	 * Displays a Pause screen that stops game play until resumed by player
	 */
	private void pauseScreen() {
		gfx.setFont(settings.titleFont);
		//set text color to red
		gfx.setColor(settings.textColor);
		//write "PAUSE" in center of screen
		gfx.drawString("PAUSE", settings.windowX/2-75, settings.windowY/2-50);
		gfx.setFont(settings.regFont);
		//lock snake head x and y while paused
		snake.setIsMoving(false);
	}
	
	/**
	 * Displays game settings/options screen
	 * to allow user to modify certain settings for
	 * more customizable experience
	 * 
	 * options include: snake color, starting snake speed, and snake size 
	 */
	private void optionScreen() {
		gfx.setColor(settings.textColor);
		gfx.drawString("OPTIONS", settings.windowX/2-30, settings.windowY+50);
		
		//TODO make current option/section white
		//and display confirmation message upon selection
		String parameter = "";
		int setting = 0;
		
		//adjust snake color; provide preset options to avoid edge cases
		gfx.drawString("Snake Color", 50, 100);
		//options listed
		gfx.drawString("Green", 50, 120);
		gfx.drawString("Blue", 100, 120);
		gfx.drawString("Red", 150, 120);
		gfx.drawString("White", 200, 120);
		gfx.drawString("Yellow", 250, 120);
		
		//adjust snake Size; provide preset options to avoid edge cases
		gfx.drawString("Snake Size", 50, 150);
		//options listed
		gfx.drawString("Small", 50, 170);//segmentSize=4
		gfx.drawString("Medium", 100, 170);//segmentSize=6
		gfx.drawString("Large", 150, 170);//segmentSize=8
		
		//adjust snake speed; provide preset options to avoid edge cases
		gfx.drawString("Snake Speed", 50, 200);
		//options listed
		gfx.drawString("Slow", 50, 220);//speed = 60
		gfx.drawString("Medium", 100, 220);//speed = 40
		gfx.drawString("Fast", 150, 220);//speed = 20
	
		gfx.drawString(parameter +" SET TO "+setting, settings.windowX/2-50, settings.windowY/2+50);
		
		gfx.drawString("Press ENTER to return to the title screen", settings.windowX/2-50, settings.windowY/2+100);
		
	}
	
	/**
	 * Displays game over screen
	 * Also shows score and provides option to try again 
	 */
	private void gameOverScreen() {
		gfx.setFont(settings.titleFont);
		//set text color to red
		gfx.setColor(settings.textColor);
		//write "Game Over" in center of screen
		gfx.drawString("Game Over", settings.windowX/2-100, settings.windowY/2-50);
		gfx.setFont(settings.regFont);
		//display ending score under "Game Over"
		gfx.drawString("Score: " + settings.totalScore, settings.windowX/2-50, settings.windowY/2);
		//display instruction to press enter to start new game
		gfx.drawString("Press ENTER to try again", settings.windowX/2-135, settings.windowY/2+50);
	}
	
	/**
	 * Refreshes graphics/drawings on screen
	 */
	public void update(Graphics g) {
		paint(g);
	}
	
	/**
	 * Refreshes graphics/drawings on screen
	 */
	public void repaint(Graphics g) {
		paint(g);
	}
	
	/**
	 * Core game function
	 * Continuous loop moving snake object,
	 * checking for game over scenarios,
	 * and repainting screen
	 */
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
				for (Token t : tokens) {
					t.snakeCollision();
					t.timedMove();
				}
				for (Barrier b : barriers) {
					b.barrierCollision();
				}
			}
						
			this.repaint();
			try {
				//increase for slower snake, decrease for faster
				Thread.sleep(settings.speed);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Checks to see if conditions have been met for game over
	 * including snake collides with self, snake collides with wall
	 * or snake goes out of bounds (if wrap not enabled)
	 */
	private void checkGameOver() {
		//if snake goes out of bounds in X
		if(snake.getHeadX() < 0 || snake.getHeadX() > settings.windowX-settings.segmentSize) {
			//if wrapMode is inactive set gameOver to true
			if(!settings.wrapMode)gameOver = true;
			//if wrapMode is active wrap to other side
			if(settings.wrapMode)mapWrap();
		}
		if(snake.getHeadY() < 0 || snake.getHeadY() > settings.windowY-settings.segmentSize-50) {
			//gameOver = true;
			if(settings.wrapMode)mapWrap();
			if(!settings.wrapMode)gameOver = true;
		}
		//if snake hits self
		if (snake.snakeCollision() && snake.snakeSize > snake.snakeStartSize) {
			gameOver = true;
		}
		// TODO add scenario for if snake hits wall
		
		boolean wallHit = false;
		for (Barrier b : barriers) {
			if (b.barrierCollision()) {
				wallHit = true;
			}
		}
		if (wallHit) {
			gameOver = true;
		}
		
	}
	
	/**
	 * Replaces an existing token with a new one
	 * allowing for variable token types
	 * 
	 * @param t
	 */
	protected static void replaceToken(Token t) {
		//generate random number between 0-99
		int rng = myRandom.nextInt(100);
		//generate token based on random number
		Token currToken = getTokenType(rng);
		//replace current token with new token
		int currIndex = tokens.indexOf(t);
		tokens.set(currIndex, currToken);
	}
	
	/**
	 * Allows out of bounds occurrences to send snake to opposite bound
	 */
	private static void mapWrap() {
		//if snake goes out of bounds to the left, relocate to right
		if(snake.getHeadX() < 0){
			snake.setHeadX(settings.windowX-settings.segmentSize);
		}
		//if snake goes out of bounds to the right, relocate to left
		if (snake.getHeadX() > settings.windowX-settings.segmentSize) {
			snake.setHeadX(settings.segmentSize);
		}
		//if snake goes out of bounds at the top, relocate to bottom
		if(snake.getHeadY() < 0) {
			snake.setHeadY(settings.windowY-settings.segmentSize-50);
		}
		//if snake goes out of bounds at the bottom, relocate to top
		if (snake.getHeadY() > settings.windowY-settings.segmentSize-50) {
			snake.setHeadY(settings.segmentSize);
		}
	}
	
	/**
	 * creates barriers object around perimeter of map/window
	 */
	protected static void barrierMap1() {
		//create wall for each border side of map/window
		Barrier leftWall = new Barrier(1,1,15,settings.windowY-2-50, snake);
		Barrier rightWall = new Barrier(settings.windowX-16,1,15,settings.windowY-2-50, snake);
		Barrier topWall = new Barrier(1,1,settings.windowX-2,15, snake);
		Barrier bottomWall = new Barrier(1,settings.windowY-16-50,settings.windowX-2,15, snake);
		//add each barrier/wall to barrier array "barriers"
		SnakeGame.barriers.add(leftWall);
		SnakeGame.barriers.add(rightWall);
		SnakeGame.barriers.add(topWall);
		SnakeGame.barriers.add(bottomWall);
	}
	
	/**
	 * When a given key is pressed a provided action is performed
	 * 
	 * Key events vary depending on screen/game/setting booleans
	 * @param KeyEvent e is any key that is pressed on user keyboard during gameplay
	 */
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
				gameOver = false;
				titleScreen = false;
				snake = new Snake();
				tokens = getTokens();
				settings.segmentSize = 6;
				settings.speed = 40;
				settings.totalScore = 0;
			}			
		}
				
	}

	public void keyReleased(KeyEvent arg0) {}

	public void keyTyped(KeyEvent arg0) {}
			
}

