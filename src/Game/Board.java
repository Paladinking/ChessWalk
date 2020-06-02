package Game;

import Game.assetClasses.Image;
import Game.assetClasses.ImageID;
import Game.entities.Enemy;
import Game.entities.Player;
import Game.levels.Level;
import Game.levels.Tilemap;
import Game.levels.Tiles.Tile;
import Game.levels.Tiles.WallTile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;


public class Board extends JPanel implements ActionListener, KeyListener, MouseListener {
    Player player;

    Board() {
        setPreferredSize(new Dimension(900, 920));
        loadImages();
        loadEntities();
        setupBoard();
    }

    private void loadEntities() {
        player = new Player();
        Enemy.load();
        loadLevel(0);
    }

    private void loadLevel(int lvl) {
        Image.clear();
        player.setX(1);
        player.setY(8);
        player.replaceImage();
        GameState.currentLevel = lvl;
        if (lvl < Level.LEVELS.length) {
            Tilemap.loadTiles(Level.LEVELS[lvl]);
        }
    }

    private void setupBoard() {
        GameState.playerTurn = true;
        addKeyListener(this);
        addMouseListener(this);
        addKeys();
        setFocusable(true);
        Timer timer = new Timer(10, this);
        timer.start();


    }

    private void addKeys() {
        GameState.keyStates = new HashMap<>();
        GameState.keyStates.put(KeyEvent.VK_SHIFT, false);
        GameState.keyStates.put(KeyEvent.VK_SPACE, false);
    }

    private void loadImages() {
        Image.loadImages();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
        tick();
    }

    @Override
    public void paintComponent(Graphics g) {
        draw(g);
    }

    private void tick() {
        if (GameState.playerTurn) {
            player.tick();
            if (player.done) GameState.playerTurn = !GameState.playerTurn;
        } else {
            if (!Enemy.tickAll()) {
                loadLevel(GameState.currentLevel + 1);
            }
            if (GameState.t++ > Enemy.getTickLength()) {
                GameState.playerTurn = true;
                Tile[][] t2 = Tilemap.getTiles();
                GameState.t = 0;
                player.newTurn();
            }
        }
        GameState.mouseRelease = false;
        if (Tilemap.getTile(GameState.mouseX, GameState.mouseY).getEntity() != player && !(Tilemap.getTile(GameState.mouseX, GameState.mouseY) instanceof WallTile))
            Image.put(ImageID.SELECTED_SQUARE_ID, new Image(GameState.mouseX * GameState.tileSize, GameState.mouseY * GameState.tileSize, Image.SELECTED_TILE));
        else Image.remove(ImageID.SELECTED_SQUARE_ID);
    }


    private void draw(Graphics g) {
        g.setColor(Color.darkGray);
        g.fillRect(0, 0, getWidth(), getHeight());
        Image.drawImages(g);
        UI.drawUI(g, player);
    }


    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int key = keyEvent.getKeyCode();
        GameState.keyStates.put(key, true);//Boolean keys that can be checked


        //Some key-presses requires immediate Action
        if (key == KeyEvent.VK_C) {
            GameState.clearMovement = true;
        }
        if (key == KeyEvent.VK_1) {
            GameState.selected = GameState.selected == 0 ? -1 : 0;
        }
        if (key == KeyEvent.VK_2) {
            GameState.selected = GameState.selected == 1 ? -1 : 1;
        }
        if (key == KeyEvent.VK_3) {
            GameState.selected = GameState.selected == 2 ? -1 : 2;
        }
        if (key == KeyEvent.VK_4) {
            GameState.selected = GameState.selected == 3 ? -1 : 3;
        }
        if (key == KeyEvent.VK_5) {
            GameState.selected = GameState.selected == 4 ? -1 : 4;
        }

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        int key = keyEvent.getKeyCode();
        GameState.keyStates.put(key, false);
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {


    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        int mouseX = (mouseEvent.getX() - GameState.offsetX) / GameState.tileSize;
        int mouseY = (mouseEvent.getY() -GameState.offsetY)/ GameState.tileSize;
        GameState.mouseRelease = true;
        GameState.mouseX = mouseX;
        GameState.mouseY = mouseY;
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
