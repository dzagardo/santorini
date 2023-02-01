package game;

/**
 * Abstract superclass for all AI players with different strategies.
 * To construct an AI player:
 * 1. Construct an instance (of its subclass) with the game Board
 * 2. Call setSeed() to set the computer's seed
 * 3. Call move() which returns the next move in an int[2] array of {row, col}.
 *
 * The implementation subclasses need to override abstract method move().
 * They shall not modify Cell[][], i.e., no side effect expected.
 * Assume that next move is available, i.e., not game-over yet.
 */
public abstract class AIPlayer {
    protected int ROWS = 5;  // number of rows
    protected int COLS = 5;  // number of columns

    protected Space[][] spaces; // the board's ROWS-by-COLS array of Cells
    protected Board board;
    protected Player mySeed;    // computer's seed
    protected Player oppSeed;   // opponent's seed

    /** Constructor with reference to game board */
    public AIPlayer(Board board) {
        spaces = board.getBoard();
    }

    /** Set/change the seed used by computer and opponent */
    public void setSeed(Player seed, Game game) {
        this.mySeed = seed;
        oppSeed = (seed.getPlayerNumber() == 1) ? game.getPlayerHelper(2) : game.getPlayerHelper(1);
    }

    /** Abstract method to get next move. Return int[2] of {row, col} */
    abstract int[] move();  // to be implemented by subclasses
}