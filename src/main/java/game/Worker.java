package game;

/**
 * Public class Worker is assigned to Players.
 */
public class Worker {

    private int owner;
    private int workerNumber;
    private boolean hasMoved;
    private int[][] position = new int[1][2];
    private GodCard god = null;

    /**
     * Worker constructor.
     * @param player player number that owns the worker.
     * @param worker worker number that ends up being the worker.
     */
    public Worker (int player, int worker) {
        this.owner = player;
        this.workerNumber = worker;
    }

    /**
     * GetOwner returns the Player Number assigned to this worker.
     * @return int value for owner.
     */
    public int getOwner() {
        return owner;
    }

    /**
     * Critical setPosition method used in updating a worker's position.
     * @param x destination X int.
     * @param y destination Y int.
     * @return returns the Worker that ends up being updated.
     */
    public Worker setPosition(int x, int y) {
        this.position[0][0] = x;
        this.position[0][1] = y;
        return this;
    }

    /**
     * Critical getPosition method used in determining a worker's location.
     * @return an integer array int[][] of the worker's position.
     */
    public int[][] getPosition() {
        return position;
    }

    /**
     * Gets the worker's location in X coordinate.
     * @return int X.
     */
    public int getWorkerX() {
        return position[0][0];
    }

    /**
     * Gets the worker's location in Y coordinate.
     * @return int Y.
     */
    public int getWorkerY() {
        return position[0][1];
    }

    /**
     * Gets the worker's move status. Critical for keeping track when determining valid build state.
     * @return boolean for onDeck or hasMoved.
     */
    public boolean getHasMoved() {
        return hasMoved;
    }

    /**
     * Updates the Worker's hasMoved status to true.
     */
    public void hasMoved() {
        hasMoved = true;
    }

    /**
     * Resets the worker's hasMoved status to false.
     */
    public void endOfTurn() {
        hasMoved = false;
    }

    /**
     * Sends back this worker's specific number.
     * @return int workerNumber.
     */
    public int getWorkerNumber() {
        return workerNumber;
    }

    /**
     * Assigns this worker a GodCard.
     * @param newGod card to be assigned.
     */
    public void setGodCard(GodCard newGod) {
        this.god = newGod;
    }

    /**
     * Returns this worker's God Card.
     * @return GodCard the worker's.
     */
    public GodCard getGodCard() {
        return this.god;
    }

    /**
     * Returns this worker's God CArd's name.
     * @return String of the God Card.
     */
    public String getGodName() {
        if (god == null) {
            return "All our gods have abandoned us.";
        }
        return this.god.toString();
    }

    /**
     * Returns the game-specific implementation of a worker's identity.
     * @return String of the Worker.
     */
    public String toString() {
        return ("P" + this.owner + "W" + this.workerNumber + "G" + this.god.toString());
    }

}
