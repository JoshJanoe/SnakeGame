package snake;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 
 * @author Josh Janoe
 * @version 2
 * created February 26, 2021
 * 
 * Timer to automatically respawn tokens after set amount of time
 *
 */
public class TokenTimer extends Timer {

	Timer timer;
	Token token;
	
	/**
	 * 
	 */
	public TokenTimer(int seconds, Token t) {
		// TODO Auto-generated constructor stub
		token = t;
		timer = new Timer();
		timer.schedule(new respawn(), seconds*1000);
	}

	class respawn extends TimerTask {
        public void run() {
            token.changePosition();
            timer.cancel(); //Terminate the timer thread
        }
    }

//    public static void main(String args[]) {
//        new TokenTimer(10);
//    }

}
