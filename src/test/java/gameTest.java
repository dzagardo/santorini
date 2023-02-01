//import static org.junit.Assert.*;
//
//import game.*;
//import org.junit.Test;
//
//import java.io.IOException;
//
//public class gameTest {
//
//    App app = new App();
//    Game game = new Game();
//
//    public gameTest() throws IOException {
//    }
//
//    @Test
//    public void checkMove() {
//        String god = "Apollo";
//        game = app.selectGods(game, god);
//        String godTwo = "Minotaur";
//        game = app.selectGods(game, godTwo);
//        game.gameStart(game, 0, 1);
//        game.gameStart(game, 0, 2);
//        game.gameStart(game, 0, 3);
//        game.gameStart(game, 0, 4);
//        game.move(game, 1, 1, 1, 1);
//        assertTrue(game.getPlayerHelper(1).getWorkerOne().getWorkerX() == 1);
//    }
//
//    @Test
//    public void checkBuild() {
//        String god = "Apollo";
//        game = app.selectGods(game, god);
//        String godTwo = "Minotaur";
//        game = app.selectGods(game, godTwo);
//        game.gameStart(game, 0, 1);
//        game.gameStart(game, 0, 2);
//        game.gameStart(game, 0, 3);
//        game.gameStart(game, 0, 4);
//        game.move(game, 1, 1, 1, 1);
//        game.build(1, 2, 1, 1, game);
//        assertTrue(game.getBoard().getSpace(1, 2).getTowerHeight() == 1);
//    }
//
//    @Test
//    public void checkWorkerNumber() {
//        String god = "Apollo";
//        game = app.selectGods(game, god);
//        String godTwo = "Minotaur";
//        game = app.selectGods(game, godTwo);
//        assertTrue(game.getPlayerHelper(1).getWorkerOne().getWorkerNumber() == 1);
//    }
//
//    @Test
//    public void checkPlayerNumber() {
//        String god = "Apollo";
//        game = app.selectGods(game, god);
//        String godTwo = "Minotaur";
//        game = app.selectGods(game, godTwo);
//        assertTrue(game.getPlayerHelper(1).getPlayerNumber() == 1);
//    }
//
//    @Test
//    public void checkSpaceOccupancy() {
//        Worker w = new Worker(1, 1);
//        Space s = new Space(1, 1);
//        s.occupy(w);
//        assertTrue(s.getOccupancy() != null);
//    }
//
//}
