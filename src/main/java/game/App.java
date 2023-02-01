package game;

import java.io.IOException;
import java.util.Map;


import game.GodCards.Apollo;
import game.GodCards.Demeter;
import game.GodCards.Minotaur;
import game.GodCards.Pan;
import game.GodCards.NoGodCard;
import fi.iki.elonen.NanoHTTPD;


// trivial change
public class App extends NanoHTTPD {

    private static final int PORT = 8080;
    private static final int WORKER_CHAR = 3;
    private static final int GOD_START = 3;
    private static final int GAME_START = 7;

    public static void main(String[] args) {
        try {
            new App();
        } catch (IOException ioe) {
            System.err.println("Couldn't start server:\n" + ioe);
        }
    }

    private Game game;

    public App() throws IOException {
        super(PORT);

        game = new Game();

        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        System.out.println("\nRunning!\n");
    }

    @Override
    public Response serve(IHTTPSession session) {
        String uri = session.getUri();
        Map<String, String> params = session.getParms();

        if (uri.equals("/newgame")) {
            game = new Game();
            return newFixedLengthResponse(game.toString());
        }
        if (game.getGameOver()) {
            return newFixedLengthResponse(game.toString());
        }
        if (params.equals("")) {
            return newFixedLengthResponse(game.toString());
        }
        if (uri.equals("/gods")) {
            if (game.getGameStart() <= 2) {
                String god = params.get("g");
                selectGods(game, god);
            } else {
                return newFixedLengthResponse(game.toString());
            }
        } else if (uri.equals("/play")) {
            System.out.println(params);
            int x = Integer.parseInt(params.get("x"));
            int y = Integer.parseInt(params.get("y"));
            String w = params.get("w");
            int pNum = Integer.parseInt("" + w.charAt(1));
            int wNum = Integer.parseInt("" + w.charAt(WORKER_CHAR));
            if (game.getGameStart() < GOD_START) {
                return newFixedLengthResponse(game.toString());
            }
            // if players have not set player positions
            if (game.getGameStart() != GAME_START) {
                game.gameStart(game, x, y);
            } else if (uri.equals("/play") && game.getGameStart() == GAME_START) {
                game.move(game, x, y, pNum, wNum);
            }
        } else if (uri.equals("/build")) {
            System.out.println(params);
            int x = Integer.parseInt(params.get("x"));
            int y = Integer.parseInt(params.get("y"));
            String w = params.get("w");
            int pNum = Integer.parseInt("" + w.charAt(1));
            int wNum = Integer.parseInt("" + w.charAt(WORKER_CHAR));
            game.build(x, y, pNum, wNum, game);
        } else if (uri.equals("/undo")) {
            this.game = this.game.undo();
        }
        return newFixedLengthResponse(game.toString());
    }

    /**
     * SelectGods method to be called at the start of each game to set each god to appropriate player.
     * @param game a game to be updated.
     * @param god god to be assigned.
     * @return
     */
    public Game selectGods(Game game, String god) {
        if (game.getPlayerHelper(1).getGodName().equalsIgnoreCase(god) && !game.getPlayerHelper(1).getGodName().equalsIgnoreCase("nogodcard")) {
            return game;
        }
        int pNum;
        if (game.getGameStart() <= 1) {
            pNum = 1;
        } else {
            pNum = 2;
        }
        Player p = game.getPlayerHelper(pNum);
        game.setGameStart(game.getGameStart() + 1);
        createGodCard(p, god);
        return game;
    }

    /**
     * Creates a God Card based on the string passed from the front end.
     * @param p Player to be assigned a card.
     * @param god The god to be assigned.
     */
    private void createGodCard(Player p, String god) {
        Worker one = p.getWorkerOne();
        Worker two = p.getWorkerTwo();
        if (god.compareToIgnoreCase("pan") == 0) {
            GodCard card = new Pan();
            one.setGodCard(card);
            two.setGodCard(card);
        } else if (god.compareToIgnoreCase("demeter") == 0) {
            GodCard card = new Demeter();
            one.setGodCard(card);
            two.setGodCard(card);
        } else if (god.compareToIgnoreCase("minotaur") == 0) {
            GodCard card = new Minotaur();
            one.setGodCard(card);
            two.setGodCard(card);
        } else if (god.compareToIgnoreCase("apollo") == 0) {
            GodCard card = new Apollo();
            one.setGodCard(card);
            two.setGodCard(card);
        } else {
            GodCard card = new NoGodCard();
            one.setGodCard(card);
            two.setGodCard(card);
        }
    }

    public static class Test {
        public Test() {
        }

        public String getText() {
            return "Hello World!";
        }
    }
}
