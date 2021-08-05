package game2;

import Game.assets.SoundManager;
import game2.data.DataLoader;
import game2.games.Game;
import game2.games.Walk;
import game2.ui.GamePanel;
import helper.json.FancyJSonParser;
import helper.json.JsonObject;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class Dungeon {

    public static final Random THE_RANDOM = new Random();

    public static void main(String[] args) {
        JsonObject setup;
        try {
            setup = new FancyJSonParser().readResource("setup.json");
        } catch (IOException ioException) {
            ioException.printStackTrace();
            System.exit(-1);
            return;
        }
        boolean useSeed = setup.getBoolean("useSeed");
        long seed = useSeed ? setup.getInt("seed") : THE_RANDOM.nextInt();
        System.out.println("Seed: " + seed);
        THE_RANDOM.setSeed(seed);
        int width = setup.getInt("width"), height = setup.getInt("height");
        String dataLocation = setup.getString("DataLocation");
        int frameDelay = setup.getInt("frameDelay");
        String name = setup.getString("Name");
        String iconPath = setup.getString("Icon");
        BufferedImage image;
        try {
            image = ImageIO.read(ClassLoader.getSystemResource(iconPath));
        } catch (IOException | IllegalArgumentException e){
            image = null;
            System.out.println("Bad Icon image");
        }
        Image icon = image;

        EventQueue.invokeLater(()-> {
            JFrame frame = new JFrame();
            frame.setTitle(name);
            frame.setIconImage(icon);

            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setResizable(false);
            Game game = new Walk(width, height, new DataLoader(dataLocation));
            GamePanel gamePanel = new GamePanel(game);
            gamePanel.setPreferredSize(new Dimension(width, height));
            frame.add(gamePanel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            gamePanel.init();
            game.init();
            game.start(frameDelay);
        });
    }
}
