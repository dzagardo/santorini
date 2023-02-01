package game;

import game.GodCards.NoGodCard;

/**
 * Game.Player class has 2 workers and a player number.
 * The Game.Player constructor takes a number
 * for player number (1, 2, 3, 4), and passes it through
 * to the worker constructor to assign two workers
 * with an assigned player number.
 */
public class Player {

    int playerNumber;
    private boolean hasMoved;
    private Worker workerOne;
    private Worker workerTwo;
    private Worker onDeck;

    /**
     * Constructor for Game.Player. Making trivial change.
     * @param num
     */
    public Player(int num) {
        playerNumber = num;
        this.workerOne = new Worker(num, 1);
        this.workerTwo = new Worker(num, 2);
    }

    /**
     * Returns this player's worker 1.
     * @return a Worker to return.
     */
    public Worker getWorkerOne() {
        return workerOne;
    }

    /**
     * Returns this player's worker 2.
     * @return a Worker to return.
     */
    public Worker getWorkerTwo() {
        return workerTwo;
    }

    /**
     * Returns this player's number.
     * @return an int to return.
     */
    public int getPlayerNumber() {
        return playerNumber;
    }

    /**
     * Returns this player's move status.
     * @return boolean hasMoved.
     */
    public boolean getHasMoved() {
        return hasMoved;
    }

    /**
     * Assigns this player's move status.
     */
    public void hasMoved() {
        hasMoved = true;
    }

    /**
     * Resets the relevant turn-specific values for this player.
     */
    public void endOfTurn() {
        hasMoved = false; onDeck = null;
    }

    /**
     * Updates this Player's worker's position.
     * @param w new Worker to be updated.
     * @param x its x coordinate.
     * @param y its y coordinate.
     * @return passes back the Worker for additional processing.
     */
    public Worker workerPosSetter(Worker w, int x, int y) {
        Worker worker = w.setPosition(x, y);
        return worker;
    }

    /**
     * Sends back this player's worker's position.
     * @param w a Worker to be examined.
     * @return integer array of position.
     */
    public int[][] workerGetPosHelper(Worker w) {
        return w.getPosition();
    }

    /**
     * Updates this player's most recently moved worker.
     * @param w addresses the worker's status.
     */
    public void setOnDeck(Worker w) {
        onDeck = w;
    }

    /**
     * Returns the worker's most recently moved worker.
     * @return a Worker.
     */
    public Worker getOnDeck() {
        return onDeck;
    }

    /**
     * Updates the front end with relevant information.
     * @return
     */
    public String toString() {
        return ("Game.Player " + playerNumber + ", " + getWorkerOne().toString() + ", " + getWorkerTwo().toString());
    }

    /**
     * Sends back the Worker.
     * @param wNum int number.
     * @return a Worker.
     */
    public Worker getWorkerHelper(int wNum) {
        if (wNum == 1) {
            return workerOne;
        } else if (wNum == 2) {
            return workerTwo;
        }
        return null;
    }

    /**
     * Sends back this player's assigned god name.
     * @return String of God.
     */
    public String getGodName() {
        return workerOne.getGodName();
    }

    /**
     * Sends back this player's worker's god card.
     * @return GodCard.
     */
    public GodCard getGod() {
        if (workerOne.getGodCard() == null) {
            return new NoGodCard();
        }
        return workerOne.getGodCard();
    }
}
