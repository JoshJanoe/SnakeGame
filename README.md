# SnakeGame

@author Josh Janoe
created February 24, 2021
 
Basic snake game created using online tutorial by Krohn - Education on YouTube
Tutorial can be found at https://www.youtube.com/watch?v=9eQJAWhRHQg&feature=emb_logo
Code for initial version can be found in Version0 folder, changes listed in most recent SnakeGame.java file

Version 1 updates include:
-changes to basic formatting, and variable names
-added comments, instance variables and helper methods where useful
-slight alteration to snake and token design for improved visuals
-Pause feature added
-Retry feature added
-secondary token added
-running score added

Version 2 updates include:
-title screen
-made snake head slightly larger than body
-multiple apples/tokens
-added timer for tokens but only for after first collision
-apple timer for first location
-variable apple score/abilities based on color
 -cyan = slower/smaller, faster/bigger
 -green = x2.5 points, yellow = x5 points, etc..
-added settings class to store game settings/options variables
-added option/variable for allowing screen wrap when out of bounds
-created fading effect before tokens disappear
-randomized token active time
-adjusted fonts
-added comments

Version 3 planned updates:
-border wall
-replace out of bounds game over with wrap around
 -create opening in outer border
 -add borders/obstacles inside play area
-difficulty
 -adjust speed, number of apples, number of walls
-levels
 -progress after set number of points
-high score 		
-options screen
-use Damped Sine Function and opacity for fade()
