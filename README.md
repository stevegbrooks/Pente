# Pente
This program can play the game Pente either between two humans, two computer players, or a human and a computer player.

# MyBoard
The meat of this class is in the placeStone() method, which utilizes the private methods checkPente() and checkCapture(). 

My approach for checkPente() is to find an adjacent friendly stone next to the stone that was just placed.  If found, then it records the direction that second stone is in, and creates an array 8 intersections long (3 in the opposite direction of the two stones that were just identified, and 3 in the forward direction).  It checks for pente by checking this array.

My approach for checkCapture() is similar to checkPente(), but instead of creating an array, it simply goes down a if/else tree checking for the unique signature of a capture (2 enemy stones flanked on either side by 2 friendly stones).

The placeStone() method throws exceptions if it receives a getMove() that is illegal, and those exceptions are caught in the Game class.

# SbrPlayer

I attempted the extra credit, which makes SbrPlayer at times very good, but at times unbelievably bad at the game of Pente.  Its really good at finding opportunities for captures, but bad at blocking your pente opportunities, especially when those opportunities are made inside-out, ie when filling in a gap creates the pente.

SbrPlayers approach relies on an extensive getMove() method, which is deterministic for the first 4 moves, and then utilizes a single method called flankOrExtend, which, based on the stone color passed to it, attempts to flank enemy pieces, or extend a current friendly run. One can switch priorities for defense/offense by switching around the order in which flankOrExtend(enemyColor) flankOrExtend(friendlyColor) are prioritized in the getMove() method.  The methods utilize similar set ups as the MyBoard methods do for looking for patters in arrays of pieces dictated by finding 2 pieces and recording the direction one is as compared to the other.

SbrPlayer also keeps track of its last move with a HashMap, which is useful sometimes when it cant find a move with flankOrExtend(). 

SbrPlayer does not return illegal moves (that I know of!).

# Game 

This class determines the order that the players go in, and alternates turns. It also catches exceptions thrown by MyBoard, and displays info about the game as it runs. As a human, if you enter an illegal move, you'll be requested to enter a legal move, and if you don't the game will not proceed.