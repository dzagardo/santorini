package game.GodCards;
import game.Game;
import game.Worker;
import game.GodCard;
import game.Board;
import game.Player;

/**
 Demeter: Your Worker may build one additional time, but not on the same space. Click on your worker to pass the additional build.
 */
public class Demeter implements GodCard {

    private static final int MAX_BOARD_INDEX = 4;
    private static final int WIN_CONDITION = 3;
    private boolean buildOnce;
    private int[][] previousBuild = new int[1][2];

    /**
     * Helper function that checks if the move is valid.
     * First conditional checks if buildPos is on the board.
     * Second conditional checks if x coordinate is adjacent to Game.Worker.
     * Third conditional checks if y coordinate is adjacent to Game.Worker.
     * Fourth conditional checks if Game.Space has room to build.
     * Fifth conditional checks if Game.Space is occupied.
     * Sixth conditional checks if Game.Space is Game.Worker's current space.
     * Seventh conditional checks if it is the Game.Player's turn.
     * Eighth conditional checks if the Game.Player has moved.
     * Ninth conditional checks if the Game.Worker building is the Game.Worker that has moved.
     * @param game Game for position analysis.
     * @param p Player for position analysis.
     * @param w Worker for position analysis.
     * @param x int x for x coordinate to validate build position.
     * @param y int y for y coordinate to validate build position.
     * @return true only if valid build position.
     */
    public boolean validateBuildPos(Game game, Player p, Worker w, int x, int y) {
        if (x < 0 || y < 0 || x > MAX_BOARD_INDEX || y > MAX_BOARD_INDEX) {
            return false;
        }
        if (x > w.getPosition()[0][0] + 1 || x < w.getPosition()[0][0] - 1) {
            return false;
        }
        if (y > w.getPosition()[0][1] + 1 || y < w.getPosition()[0][1] - 1) {
            return false;
        }
        if (game.getBoard().getSpace(x, y).getTowerHeight() >= MAX_BOARD_INDEX) {
            return false;
        }

        if (w.getOwner() != game.getTurn()) {
            return false;
        }
        if (!p.getHasMoved()) {
            return false;
        }
        if (!w.getHasMoved()) {
            return false;
        }
        return true;
    }

    /**
     * Helper function that checks if the move is valid.
     * First conditional checks if position is on the board.
     * Second conditional checks if x coordinate is adjacent to Game.Worker.
     * Third conditional checks if y coordinate is adjacent to Game.Worker.
     * Fourth conditional checks if Game.Space has room to move.
     * Fifth conditional checks if Game.Space is occupied.
     * Sixth conditional checks if towerHeight destination is valid.
     * Seventh conditional checks if space is Game.Worker's current space.
     * Eighth conditional checks if it is the Game.Player's turn.
     * @param board board for analysis.
     * @param turn to check if player's turn.
     * @param w Game.Worker for position analysis.
     * @param x int x for x coordinate to validate build position.
     * @param y int y for y coordinate to validate build position.
     * @return true only if valid build position.
     */
    public boolean validateMovePos(Board board, Worker w, int x, int y, int turn) {
        if (x < 0 || y < 0 || x > MAX_BOARD_INDEX || y > MAX_BOARD_INDEX) {
            return false;
        }
        if (x > w.getPosition()[0][0] + 1 || x < w.getPosition()[0][0] - 1) {
            return false;
        }
        if (y > w.getPosition()[0][1] + 1 || y < w.getPosition()[0][1] - 1) {
            return false;
        }
        if (board.getSpace(x, y).getTowerHeight() >= MAX_BOARD_INDEX) {
            return false;
        }
        if (board.getSpace(x, y).getOccupancy() != null) {
            return false;
        }
        if (board.getSpace(x, y).getTowerHeight() > board.getSpace(w.getPosition()).getTowerHeight() + 1) {
            return false;
        }
        if (w.getOwner() != turn) {
            return false;
        }
        return true;
    }

    /**
     * Build method that moves a worker to a valid space. Editing to run on GHActions.
     * Calls the validateBuildPos() method to verify destination validity.
     * Returns -1 if worker cannot build on specified space, or returns
     * the resulting height of tower that worker built.
     * @return Game a copy of the updated game state.
     */
    @Override
    public Game build(int x, int y, int pNum, int wNum, Game game, Player p) {
        Board board = game.getBoard();
        Worker w = p.getOnDeck();
        int[][] buildPos = new int[1][2];
        buildPos[0][0] = x;
        buildPos[0][1] = y;
        if (!validateBuildPos(game, p, w, x, y)) {
            return game;
        }
        if (!buildOnce) {
            if (x == w.getPosition()[0][0] && y == w.getPosition()[0][1]) {
                return game;
            }
            if (game.getBoard().getSpace(x, y).getOccupancy() != null) {
                return game;
            }
        } else {
            if (x == previousBuild[0][0] && y == previousBuild[0][1]) {
                return game;
            }
            if (x == w.getPosition()[0][0] && y == w.getPosition()[0][1]) {
                game.endTurn(game);
                buildOnce = false;
                return new Game(board, game.getHistory());
            }
            if (game.getBoard().getSpace(x, y).getOccupancy() != null) {
                return game;
            }
        }

        board.getSpace(x, y).buildTower();
        if (buildOnce) {
            game.endTurn(game);
            buildOnce = false;
            return new Game(board, game.getHistory());
        }
        previousBuild[0][0] = x;
        previousBuild[0][1] = y;
        buildOnce = true;

        return new Game(board, game.getHistory());
    }

    /**
     * Move method that moves a worker to a valid space.
     * Calls the validateMovePos() method to verify destination validity.
     * Returns worker's position after the method is called.
     * @return Game a copy of the updated game state.
     */
    @Override
    public Game move(int x, int y, int pNum, int wNum, Game game, Player p) {
        Board board = game.getBoard();
        if (p.getOnDeck() != null && (pNum == 1 || pNum == 2)) {
            p = game.getPlayerHelper(pNum);
            Worker w = game.getWorkerHelper(p, wNum);
            p.setOnDeck(w);
        } else if (p.getOnDeck() == null && (pNum == 1 || pNum == 2)) {
            p = game.getPlayerHelper(pNum);
            Worker w = game.getWorkerHelper(p, wNum);
            p.setOnDeck(w);
        } else {
            if (p.getOnDeck() == null) {
                return game;
            }
            if (!p.getOnDeck().getHasMoved()) {
                Worker w = p.getOnDeck();
                int turn = game.getTurn();
                int[][] movePos = new int[1][2];
                movePos[0][0] = x;
                movePos[0][1] = y;
                if (!validateMovePos(board, w, x, y, turn)) {
                    return game;
                }
                board.getSpace(p.workerGetPosHelper(w)).vacate();
                w = p.workerPosSetter(w, x, y);
                board.getSpace(p.workerGetPosHelper(w)).occupy(w);
                if (board.getSpace(x, y).getTowerHeight() == WIN_CONDITION) {
                    game.setGameOver();
                    game.endGame(p);
                }
                p.hasMoved();
                w.hasMoved();
            }
        }
        return new Game(board, game.getHistory());
    }

    @Override
    public String toString() {
        return "Demeter";
    }

    /**
     * Returns a copy of the god-specific rules.
     * @return String a string of rules.
     */
    @Override
    public String godRules() {
        return "Demeter: Your Worker may build one " +
                "additional time, but not on the same space. " +
                "Click on your worker to pass the additional build.";
    }
}
