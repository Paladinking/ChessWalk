package game2.games;

public interface GameListener {


    /**
     * Called when a tick has happened, and the game should be redrawn.
     */
    void tickHappened();

    void resize(int w, int h);


}
