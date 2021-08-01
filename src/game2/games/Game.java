package game2.games;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 *The <code>Game</code> class contains some general code for implementing a game, using a <code>GamePanel</code>.
 * @see game2.ui.GamePanel
 */
public abstract class Game implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

    /**
     * GameListeners, probably only a <code>GamePanel</code>
     */
    protected final List<GameListener> listeners;

    /**
     * A boolean that is true while the game is ongoing, and false otherwise.
     */
    private boolean running;

    protected Game() {
        this.listeners = new ArrayList<>();
    }

    public void addListener(final GameListener listener){
        listeners.add(listener);
    }

    public abstract void draw(Graphics2D g);

    public abstract void init();

    public void start(int frameDelay){
        running = true;
        new Thread(()->loop(frameDelay)).start();
    }

    private void loop(int delay){
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                tick();
                if (!running) cancel();
            }
        }, delay, delay);
    }

    public void stop(){
        running = false;
    }

    public abstract void tick();


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
