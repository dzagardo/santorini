package game;

/**
 * God card interface that allows for extensible implementation of new god cards.
 */
public interface GodCard {
    Game move(int x, int y, int pNum, int wNum, Game game, Player p);
    Game build(int x, int y, int pNum, int wNum, Game game, Player p);
    String godRules();
}
