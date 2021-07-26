package Game;

import Game.assets.Image;
import Game.assets.SoundManager;
import Game.entities.Enemy;
import Game.entities.Player;
import Game.levels.Level;
import Game.levels.Tilemap;
import Game.levels.Vision;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;




public class Board extends JPanel implements ActionListener, KeyListener, MouseListener, MouseWheelListener,MouseMotionListener {
    private Player player;

    Board() {
        setPreferredSize(new Dimension(800, 920));
        loadImages();
        loadEntities();
        setupBoard();
    }

    private void loadEntities() {
        player = new Player(GameState.startX, GameState.startY, 50, new Image(GameState.startX * GameState.tileSize, GameState.tileSize * GameState.startY, Image.PLAYER_FRONT));
        player.getImage().setAdjY(-20);
        Enemy.load();
        loadLevel(0);
    }


    private void loadLevel(int lvl) {
        Image.clear();
        player.setX(GameState.startX);
        player.setY(GameState.startY);
        GameState.currentLevel = lvl;
        if (lvl < Level.LEVELS.length) {
            Tilemap.loadTiles(Level.LEVELS[lvl]);
        }
        Tilemap.getTile(GameState.startX, GameState.startY).setEntity(player);
    }

    private void setupBoard() {
        GameState.playerTurn = true;
        addKeyListener(this);
        addMouseListener(this);
        addMouseWheelListener(this);
        addMouseMotionListener(this);
        addKeys();
        setFocusable(true);
        Timer timer = new Timer(16, this);
        timer.start();
        SoundManager.playSound("test.wav");

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
            if(GameState.playerX!=(GameState.playerX=player.getX()) || GameState.playerY!=(GameState.playerY=player.getY())){
                Tilemap.updateVision();
            }
        } else {
            if (!Enemy.tickAll()) {
                loadLevel(GameState.currentLevel + 1);
            }
            if (GameState.t++ > Enemy.getTickLength()) {
                GameState.playerTurn = true;
                GameState.t = 0;
                GameState.turns++;
            }
        }
        GameState.mouseRelease = false;
        /*if (Tilemap.getTile(GameState.mouseX, GameState.mouseY).getEntity() != player && !(Tilemap.getTile(GameState.mouseX, GameState.mouseY) instanceof WallTile))
            Image.put(ImageID.SELECTED_SQUARE_ID, new Image(GameState.mouseX * GameState.tileSize, GameState.mouseY * GameState.tileSize, Image.SELECTED_TILE));
        else Image.remove(ImageID.SELECTED_SQUARE_ID);*/
    }


    private void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.darkGray);
        Tilemap.draw(g);
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
        if (key == KeyEvent.VK_M) {
            Tilemap.showAll();
            System.gc();
        }
        if (key == KeyEvent.VK_H) {
            Tilemap.hideAll();
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
        if (mouseEvent.getButton() == MouseEvent.BUTTON2) {
            GameState.scroling = true;
            GameState.inMouseX = mouseEvent.getX();
            GameState.inMouseY = mouseEvent.getY();
        }

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
            int mouseX = ((mouseEvent.getX() - GameState.offsetX-GameState.globalAdjX) / GameState.tileSize) + GameState.globalX;
            int mouseY = ((mouseEvent.getY() - GameState.offsetY-GameState.globalAdjY) / GameState.tileSize) + GameState.globalY;
            GameState.mouseRelease = true;
            GameState.mouseX = mouseX;
            GameState.mouseY = mouseY;
        }
        if (mouseEvent.getButton() == MouseEvent.BUTTON2) {
            GameState.scroling = false;
        }
        if(mouseEvent.getButton()==MouseEvent.BUTTON3){
            new Vision(GameState.mouseX,GameState.mouseY).calculateVision();
        }
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if(GameState.tileSize>=GameState.minSize&&GameState.tileSize<=GameState.maxSize)
        GameState.tileSize += 2*e.getWheelRotation();
         else if(GameState.tileSize<=GameState.minSize&&e.getWheelRotation()>0){
             GameState.tileSize+=2*e.getWheelRotation();
         } else if(GameState.tileSize>=GameState.maxSize&&e.getWheelRotation()<0){
             GameState.tileSize+=2*e.getWheelRotation();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int difX = (GameState.inMouseX - e.getX());
        int difY = (GameState.inMouseY - e.getY());
        if ((GameState.scroling&&(GameState.globalX>=0||difX>0)&&(GameState.globalY>=0||difY>0)&&(GameState.globalX<(GameState.mapWidth-16 * GameState.initialSize / GameState.tileSize)||difX<0)&&(GameState.globalY<(GameState.mapHeight-16 *  GameState.initialSize/ GameState.tileSize)||difY<0))){
            GameState.globalAdjX -= difX % GameState.tileSize;
            GameState.inMouseX =e.getX();
            if (Math.abs(GameState.globalAdjX) > GameState.tileSize) {
                GameState.globalX-= GameState.globalAdjX / GameState.tileSize;
                GameState.globalAdjX%=GameState.tileSize;

            }
            GameState.globalAdjY -= difY % GameState.tileSize;
            GameState.inMouseY = e.getY();
            if(Math.abs(GameState.globalAdjY)>GameState.tileSize) {
                GameState.globalY -= GameState.globalAdjY / GameState.tileSize;
                GameState.globalAdjY%=GameState.tileSize;
            }
        }
    }


    @Override
    public void mouseMoved(MouseEvent e) {
    }

}
