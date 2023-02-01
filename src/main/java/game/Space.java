package game;

/**
 * Space is a critical component within the Game, and holds important data relating to occupancy and tower heights.
 */
public class Space {
    private Worker worker;
    private int towerHeight;
    private int x;
    private int y;
    private String link;
    private String text;
    private static final int MAX_TOWER_HEIGHT = 4;

    /**
     * Basic Space constructor.
     * @param xPos x coordinate of this space.
     * @param yPos y coordinate of this space.
     */
    public Space(int xPos, int yPos) {
        this.worker = null;
        this.towerHeight = 0;
        this.x = xPos;
        this.y = yPos;
    }

    /**
     * Advanced Space constructor that allows for processing of identity specific data.
     * @param space the Space to be updated.
     * @param text Text to be assigned to this space.
     * @param link The hyperlink for service.
     */
    public Space(Space space, String text, String link) {
        this.x = space.getX();
        this.y = space.getY();
        this.towerHeight = space.getTowerHeight();
        this.worker = space.getOccupancy();
        this.text = text;
        this.link = link;
    }

    /**
     * This is how we determine the level of towers.
     * @return integer level of tower on this space.
     */
    public int getTowerHeight() {
        return towerHeight;
    }

    /**
     * Build tower is critical for worker access. It allows the creation and updating work to be done.
     */
    public void buildTower() {
        // Gently ignore if space is full
        if (towerHeight >= MAX_TOWER_HEIGHT) {
            return;
        }
        towerHeight++;
    }

    /**
     * Good method to determine the state of this Space with respect to worker occupancy. Good to access Worker object from other class.
     * @return Worker this space's worker.
     */
    public Worker getOccupancy() {
        return worker;
    }

    /**
     * Updates the occupancy of this Space.
     * @param w Worker to occupy.
     * @return return the Space of this.
     */
    public Space occupy(Worker w) {
        this.worker = w;
        return this;
    }

    /**
     * Sets worker on this space to be null.
     */
    public void vacate() {
        this.worker = null;
    }

    /**
     * Sends back the X coordinate of this space.
     * @return int X coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Sends back the Y Coordinate of this space.
     * @return int Y coordinate.
     */
    public int getY() {
        return y;
    }

    /**
     * This String is critical for front end information processing.
     * @return String a toString that allows for data processing.
     */
    public String toString() {
        if (worker == null) {
            return "{ \"text\": \"t" + this.towerHeight + "\","
                    + " \"worker\": \"" + "P0W0" + "\","
                    + " \"link\": \"" + this.link + "\"}";
        }
        return "{ \"text\": \"t"+ this.towerHeight + "\","
                + " \"worker\": \"" + this.worker + "\","
                + " \"link\": \"" + this.link + "\","
                + " \"image\": \"p" + this.worker.getOwner() + "\"}";
    }

    /**
     * This method updates the link text.
     * @param link a link to be updated.
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * This method updates the text text.
     * @param text a STring to be updated.
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * This method bypasses the buildTower method and sets height directly.
     * @param height integer to be assigned.
     */
    public void setHeight(int height) {
        this.towerHeight = height;
    }
}
