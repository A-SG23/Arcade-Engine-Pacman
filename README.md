# Arcade-Engine-Pacman
# README #
We will be making a Pacman-style game where the user can control the “main” character using the up, down, left, and right arrows. The goal is to collect as many “Collectible” objects as possible while avoiding the “Enemy”. If the Pacman touches the Enemy, the game is lost, but if they collect all of the coins, the game is won.

+ World abstract class (Subclasses: GameBoard)
+ Pacman class
+ Collectible abstract class 
+ Enemy class 
+ The movement of the enemies will be randomized 
+ (not a class) score is displayed; increments when the Pacman comes in contact with a Collectible object

How to Win:
+ get to a specified score before the time runs out

How to Lose:
+ time runs out before you achieve the specified score
+ collecting dangerous objects

## Link Examples ##
Provide links to examples of your game idea.  This can be a playable online game, screenshots, YouTube videos of gameplay, etc.
+ https://pacman.live/

## Class Design and Brainstorm ##
Pacman-style game 
“Main” character trying to collect something (coins, food, etc)
“Antagonists” (comparable to the Pacman ghosts) roaming around trying to mess you up
“Collectible” stuff (an abstract class that we can extend → ex. coins)
Main character + antagonist + the collectible items will be Actor subclasses 




