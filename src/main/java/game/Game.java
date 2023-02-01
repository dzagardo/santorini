package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Game.Game class for Homework 3. Game.Game has a board and two players.
 * We initialize the board in this class, as well as players 1 and 2.
 */
public class Game {

    private Board board;
    private Player playerOne;
    private Player playerTwo;
    private Player winner;
    private boolean gameOver = false;
    private int gameStart = 1;
    private int turn = 1;
    private static final int GAME_START = 4;
    private final List<Game> history;

    /**
     * Game constructor.
     */
    public Game() {
        this(new Board(), List.of());
    }

    /**
     * Game constructor with additional params.
     * @param board takes board to be adjusted.
     * @param history takes history to be assigned.
     */
    public Game(Board board, List<Game> history) {
        this.board = board;
        this.history = history;
        this.playerOne = new Player(1);
        this.playerTwo = new Player(2);
    }

    /**
     * Method resposible for updating game state.
     * @param game this game.
     * @param x x coordinates for lacing.
     * @param y y coordinates for placing.
     * @return returns this game's new board.
     */
    public Board gameStart(Game game, int x, int y) {
        int pNum;
        if (gameStart <= GAME_START) {
            pNum = 1;
        } else {
            pNum = 2;
        }
        int wNum;
        if (gameStart % 2 != 0) {
            wNum = 1;
        } else {
            wNum = 2;
        }
        Board board = game.getBoard();
        if (board.getSpace(x, y).getOccupancy() != null) {
            return board;
        }
        Player p = game.getPlayerHelper(pNum);
        Worker w = game.getWorkerHelper(p, wNum);
        w = p.workerPosSetter(w, x, y);
        Space[][] newBoard = board.updateWorkerSpace(w, x, y);
        Board newNewBoard = getBoard().newBoard(newBoard);
        game.board = newNewBoard;
        gameStart = gameStart + 1;
        return newNewBoard;
    }

    public Game move(Game game, int x, int y, int pNum, int wNum) {
        Player p = game.getPlayerHelper(game.getTurn());
        Worker w;
        if (pNum == 0 && p.getOnDeck() != null) {
            w = p.getOnDeck();
        } else if (pNum == 0 && p.getOnDeck() == null) {
            return game;
        } else {
            w = p.getWorkerHelper(wNum);
        }
        GodCard god = w.getGodCard();
        Game copy = god.move(x, y, pNum, wNum, game, p);
        List<Game> newHistory = new ArrayList<>(game.getHistory());
        newHistory.add(copy);
        return new Game(this.board, newHistory);
    }

    public Game build(int x, int y, int pNum, int wNum, Game game) {
        Player p = game.getPlayerHelper(game.getTurn());
        Worker w = p.getWorkerOne();
        GodCard god = w.getGodCard();
        Game copy = god.build(x, y, pNum, wNum, game, p);
        List<Game> newHistory = new ArrayList<>(game.getHistory());
        newHistory.add(copy);
        return new Game(this.board, newHistory);
    }

    public Game undo() {
        int s = this.history.size();
        if (s == 0) return this;

        Game oldGame = this.history.get(s-1);
        this.history.remove(s-1);
        return new Game(oldGame.board, oldGame.history);
    }

    public void endTurn(Game game) {
        if (turn == 1) {
            turn = 2;
        } else {
            turn = 1;
        }
        game.playerOne.endOfTurn();
        game.playerOne.getWorkerOne().endOfTurn();
        game.playerOne.getWorkerTwo().endOfTurn();
        game.playerTwo.endOfTurn();
        game.playerTwo.getWorkerOne().endOfTurn();
        game.playerTwo.getWorkerTwo().endOfTurn();
    }

    public boolean getGameOver() {
        return gameOver;
    }

    public void setGameOver() {
        this.gameOver = true;
    }

    /**
     * Returns an unsafe copy of the board.
     * @return a reference to board.
     */
    public Board getBoard() {
        return board;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int newTurn) {
        turn = newTurn;
    }

    /**
     * Helper method to get reference to player object.
     * @param num a number of the player.
     * @return Game.Player a reference to player object.
     */
    public Player getPlayerHelper(int num) {
        if (num == 1) {
            return playerOne;
        } else if (num == 2) {
            return playerTwo;
        } else {
            return null;
        }
    }

    /**
     * Helper method to get reference to worker object.
     * @param p Game.Player that has a worker.
     * @param num int Game.Worker that has a number num.
     * @return Game.Worker a reference to worker object.
     */
    public Worker getWorkerHelper(Player p, int num) {
        if (num == 1) {
            return p.getWorkerOne();
        } else if (num == 2) {
            return p.getWorkerTwo();
        } else {
            return null;
        }
    }

    public int getGameStart() {
        return gameStart;
    }

    public void setGameStart(int gameStart) {
        this.gameStart = gameStart;
    }

    public Player getInactivePlayer() {
        if (turn == 1) {
            return playerTwo;
        }
        return playerTwo;
    }

    public List getHistory() {
        return history;
    }

    public void endGame(Player p) {
        winner = p;
        System.out.println("Well done, Game.Player " + p.getPlayerNumber() + "! You have won the game.");
    }

    public Player getWinner() {
        return winner;
    }

    public String toString() {
        Game game = this;
        Board gameplay = game.getBoard();
        Space[][] gameState = gameplay.getSpaces(game);
        StringBuilder sb = new StringBuilder();
        String delim = "";
        if (this.winner == null) {
            for(Space[] s : gameState){
                sb.append(delim).append(Arrays.deepToString(s)).append('\n');
                delim = ",";
            }
            String result = sb.toString();
            String toReturn = "{ \"cells\": [" + result + "],"
                    + "\"turn\": "
                    + String.valueOf(this.turn)
                    + ",\"godOne\": "
                    + "\""
                    + playerOne.getGodName()
                    + "\""
                    + ",\"godTwo\": "
                    + "\""
                    + playerTwo.getGodName()
                    + "\""
                    + ",\"godOneRules\": "
                    + "\""
                    + playerOne.getGod().godRules()
                    + "\""
                    + ",\"godTwoRules\": "
                    + "\""
                    + playerTwo.getGod().godRules()
                    + "\""
                    + "}";
            return toReturn;
        }

        for(Space[] s : gameState){
            sb.append(delim).append(Arrays.deepToString(s)).append('\n');
            delim = ",";
        }
        String result = sb.toString();
        String toReturn = "{ \"cells\": [" + result + "],"
                + "\"turn\": "
                + String.valueOf(this.turn)
                + ",\"godOne\": "
                + "\""
                + playerOne.getGodName()
                + "\""
                + ",\"godTwo\": "
                + "\""
                + playerTwo.getGodName()
                + "\""
                + ",\"winner\": "
                + String.valueOf(this.winner.getPlayerNumber())
                + "}";
        return toReturn;
    }
}
