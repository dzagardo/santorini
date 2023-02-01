# The User Attempts to Move a game.Worker

Contract CO1: move

Operation: god.move(int x, int y, int pNum, int wNum, Game game, Player p);

Cross References: Use cases: the user attempts to move a worker

Preconditions: game.game instance has been initiated. Both players have set initial worker positions. game.Worker is not null. Active player has selected the Demeter god card.

Postconditions:

* Occupancy of worker's current space is updated.
* Occupancy of worker's destination space is updated.
* game.Player's hasMoved status is updated.
* game.Player's onDeck Worker is updated.
* If (destination.towerHeight == WIN_CONDITION), game instance ends and game.Player is the winner.