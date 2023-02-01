package game;

import java.util.Arrays;
import java.util.StringJoiner;

public class Board {

    private static final int GAME_START = 7;
    private Space[][] board;
    private static final int BOARD_DIMENSION = 5;

    /**
     * New Game.Board method. Creates a 5x5 board, using
     * a 2D array data structure.
     */
    public Board() {
        board = new Space[BOARD_DIMENSION][BOARD_DIMENSION];
        for (int i = 0; i < BOARD_DIMENSION; i++) {
            for (int j = 0; j < BOARD_DIMENSION; j++) {
                board[i][j] = new Space(i, j);
            }
        }
    }

    public Board newBoard(Space[][] board) {
        this.board = board;
        return this;
    }

    public Space[][] getSpaces(Game game) {
        Space[][] spaces = new Space[BOARD_DIMENSION][BOARD_DIMENSION];
        Board board = game.getBoard();
        for (int x = 0; x < BOARD_DIMENSION; x++) {
            for (int y = 0; y < BOARD_DIMENSION; y++) {
                String text;
                String action = "/play?";
                if (board.getSpace(x, y).getOccupancy() == null) {
                    text = "P0W0";
                    if (game.getGameOver()) {
                        text = "P" + game.getWinner().getPlayerNumber() + "W" + 1 +"\"" + ", \"image\": \"p" + game.getWinner().getPlayerNumber();
                        System.out.println(text);
                    }
                } else {
                    text = board.getSpace(x, y).getOccupancy().toString();
                    if (game.getGameStart() != GAME_START) {
                        action ="/gods?";
                    } else if (!board.getSpace(x, y).getOccupancy().getHasMoved()) {
                        action = "/play?";
                    } else {
                        action = "/build?";
                    }
                }
                // if a worker's "hasMoved" is true, set all spaces to "/build?"
                if (game.getPlayerHelper(1).getWorkerOne().getHasMoved()
                        || game.getPlayerHelper(1).getWorkerTwo().getHasMoved()
                        || game.getPlayerHelper(2).getWorkerOne().getHasMoved()
                        || game.getPlayerHelper(2).getWorkerTwo().getHasMoved()) {
                    action = "/build?";
                }
                String link = action + "x=" + x + "&y=" + y + "&w=" + text;
                Space space = new Space(board.getSpace(x, y), text, link);
                space.setHeight(board.getSpace(x, y).getTowerHeight());
                spaces[x][y] = space;
            }
        }
        return spaces;
    }

    public Space build(int x, int y) {
        Space space = getSpace(x, y);
        space.buildTower();
        return space;
    }

    public Space[][] updateWorkerSpace(Worker w, int x, int y) {
        Space s = getSpace(x, y);
        s = s.occupy(w);
        board = updateSpace(s, x, y);
        return board;
    }

    public Space[][] updateSpace(Space space, int x, int y) {
        this.board[x][y] = space;
        return this.board;
    }

    public Space[][] getBoard() {
        return board;
    }

    public Space getSpace(int x, int y) {
        return board[x][y];
    }

    public Space getSpace(int[][] position) {
        return board[position[0][0]][position[0][1]];
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(System.lineSeparator());
        for (Space[] row : this.board) {
            sj.add(Arrays.toString(row));
        }
        String result = sj.toString();
        return "{ \"cells\": " + result + "}";
    }
}
