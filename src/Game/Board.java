package Game;

import Game.assets.Image;
import Game.assets.ImageID;
import Game.entities.Enemy;
import Game.entities.actions.Movement;
import Game.entities.Player;
import Game.levels.Level;
import Game.levels.Tilemap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;


public class Board extends JPanel implements ActionListener, KeyListener,MouseListener{

    private boolean shiftKey = false;
    int gitTest;

    private HashMap<Integer,Boolean> keyPresses;
    private boolean playerTurn;

    private int t;
    Board(){

        setPreferredSize(new Dimension(800,800));
        loadImages();
        loadEntities();
        setupBoard();
    }

    private void loadEntities() {
        Player.load();
        Enemy.load();
        loadLevel(0);
    }
    private void loadLevel(int lvl){
        World.setCurrentLevel(lvl);
        if(lvl<Level.LEVELS.length) {
            Tilemap.loadTiles(Level.LEVELS[lvl]);
        }
    }

    private void setupBoard(){
        playerTurn = true;
        addKeyListener(this);
        addMouseListener(this);
        addKeys();
        setFocusable(true);
        Timer timer = new Timer(10,this);
        timer.start();


    }
    private void addKeys(){
        keyPresses = new HashMap<>();
        keyPresses.put(KeyEvent.VK_SHIFT,false);
        keyPresses.put(KeyEvent.VK_W,false);
        keyPresses.put(KeyEvent.VK_A,false);
        keyPresses.put(KeyEvent.VK_S,false);
        keyPresses.put(KeyEvent.VK_D,false);
    }
    private void loadImages(){
        Image.loadImages();
    }
    @Override
    public void actionPerformed(ActionEvent e){
        repaint();
        tick();
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    private void tick(){
        if(playerTurn) {
            Player.tick();
            if(Player.done) playerTurn = !playerTurn;
        } else {
            if(!Enemy.tickAll()){
                loadLevel(World.getCurrentLevel()+1);
            }
            t++;
            if(t==10){
                playerTurn = true;
                t=0;
                Player.done = false;
            }
        }
    }


    private void draw(Graphics g){
        Image.drawImages(g);
    }



    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int key = keyEvent.getKeyCode();
        //keyPresses.put(key,true);

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        int key = keyEvent.getKeyCode();
        //keyPresses.put(key,false);
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {



    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        int mouseX = mouseEvent.getX()/50;
        int mouseY = mouseEvent.getY()/50;
        if(playerTurn) {
            if (Tilemap.getTile(mouseX, mouseY).selected(Player.player)) {

                if (!keyPresses.get(KeyEvent.VK_SHIFT)) {
                    Player.clearMovements();
                }
                ArrayList<Movement> m = Movement.getMovement(Player.destination.x, Player.destination.y, mouseX, mouseY,Player.player);
                Player.addMovements(m);
                Image.put(ImageID.SELECTED_SQUARE_ID, new Image(Player.destination.x * 50, Player.destination.y * 50, Image.SELECTED_TILE));
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
