package game2;

import game2.games.Game;
import game2.games.Walk;
import game2.ui.GamePanel;
import helper.json.FancyJSonParser;
import helper.json.JsonObject;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Dungeon {

    public static final Random THE_RANDOM = new Random();

    private static final long seed = 2533289183713569834L;

    private static final boolean useSeed = false;

    public static final int WIDTH = 800, HEIGHT = 800;

    public static final int FRAME_DELAY = 16;

    public static void main(String[] args) {
        long seed = useSeed ? Dungeon.seed : THE_RANDOM.nextLong();
        System.out.println("Seed: " + seed);
        THE_RANDOM.setSeed(seed);
        EventQueue.invokeLater(()-> {
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setResizable(false);
            Game game = new Walk(WIDTH, HEIGHT);
            GamePanel gamePanel = new GamePanel(game);
            gamePanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
            frame.add(gamePanel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            gamePanel.init();
            game.init();
            game.start(FRAME_DELAY);
        });
    }
}
