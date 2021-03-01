package snake;

import java.awt.Color;
import java.awt.Graphics;

/**
 * 
 * @author Josh Janoe
 * @version 2
 * created March 1, 2021
 * 
 * Token/apple object for use in SnakeGame
 *
 */
public class speedToken extends Token {

	public speedToken(Snake s) {
		super(s);
	}
	
	/**
	 * This method allows the different token types 
	 * to be drawn in different ways
	 * 
	 * This type: red in color
	 */
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.white);
		g.fillRoundRect(x,y,settings.tokenSize,settings.tokenSize,settings.tokenSize,settings.tokenSize);
		g.drawString(">>>", x, y+settings.tokenSize);
	}
	
	/**
	 * This method allows for the different
	 * effects when different token types are acquired
	 * 
	 * This type: make snake longer and add points
	 */
	@Override
	public void tokenAcquired() {
		if(settings.speed > 20) {
			settings.speed -= 5;
		}				
		SnakeGame.replaceToken(this);
	}
	
}
