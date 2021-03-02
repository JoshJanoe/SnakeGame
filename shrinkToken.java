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
public class shrinkToken extends Token {

	public shrinkToken(Snake s, Graphics g) {
		super(s,g);
	}
	
	/**
	 * This method allows the different token types 
	 * to be drawn in different ways
	 * 
	 * This type: red in color
	 */
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.cyan);		
		g.fillRoundRect(x,y,settings.tokenSize,settings.tokenSize,settings.tokenSize,settings.tokenSize);
		g.drawString("-", x, y);
		
		g.setColor(settings.accentColor);
		g.drawRoundRect(x,y,settings.tokenSize,settings.tokenSize,settings.tokenSize,settings.tokenSize);
		
		fade(Color.cyan);
	}
	
	/**
	 * This method allows for the different
	 * effects when different token types are acquired
	 * 
	 * This type: make snake longer and add points
	 */
	@Override
	public void tokenAcquired() {
		if(settings.segmentSize>3) {
			settings.segmentSize -= 2;
			settings.speed -= 5;
		}				
		SnakeGame.replaceToken(this);
	}
	
}
