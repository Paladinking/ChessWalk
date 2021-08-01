package game2.ui;

import game2.games.Game;
import game2.games.GameListener;

import javax.swing.*;
import java.awt.*;

/**
 * A swing component that displays a <code>Game</code>.
 */
public class GamePanel extends JPanel implements GameListener {

    private final Game game;

    public GamePanel(Game game) {
        this.game = game;
    }

    public void init() {
        this.addMouseListener(game);
        this.addKeyListener(game);
        this.addMouseWheelListener(game);
        this.addMouseMotionListener(game);
        this.setFocusable(true);
        this.requestFocus();
        game.addListener(this);
    }

    @Override public void paintComponent(Graphics g){
        game.draw((Graphics2D)g);
    }

    @Override
    public void tickHappened() {
        EventQueue.invokeLater(this::repaint);
    }
}
