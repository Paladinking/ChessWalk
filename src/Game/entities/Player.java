package Game.entities;

import Game.GameState;
import Game.Items.Boomerang;
import Game.Items.Empty;
import Game.Items.Item;
import Game.Items.Sword;
import Game.assetClasses.SoundManager;
import Game.entities.actions.ItemUse;
import Game.entities.pathfinding.Pathfinder;
import Game.assetClasses.Image;
import Game.assetClasses.ImageID;
import Game.entities.actions.Action;
import Game.entities.actions.Movement;
import Game.entities.actions.Stand;
import Game.levels.Tilemap;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class Player extends MovingEntity {

    private ArrayList<Action> actionQue;
    public Point destination;
    public ArrayList<Item> items;

    public Player(int x,int y,int hp) {
        actionQue = new ArrayList<>();
        items = new ArrayList<>();
        items.add(new Sword(6, this));
        items.add(new Boomerang(10, 810, this));
        for (int i = 0; i < 3; i++) items.add(new Empty());
        destination = new Point(1, 8);
        int imageX = 50, imageY = 375;
        currentAction = new Stand(this);
        imgId = ImageID.PLAYER_ID;
        this.x = x;
        this.y = y;
        this.hp = hp;


    }
    public void tick() {
        if (GameState.clearMovement||GameState.mouseRelease) {
            clearMovements();
            GameState.clearMovement = false;
        }
        if (currentAction instanceof Stand) {
            if (GameState.keyStates.get(KeyEvent.VK_SPACE)) {
                GameState.playerTurn = false;
            } else if (GameState.mouseRelease) {
                Item i = GameState.selected>=0&&GameState.selected<5 ? items.get(GameState.selected) : new Empty();
                if (ItemUse.readyItemUse(i, GameState.mouseX - x, GameState.mouseY - y, this))
                    addAction(new ItemUse(i, GameState.mouseX - x, GameState.mouseY - y, this));

                else if (Tilemap.getTile(GameState.mouseX, GameState.mouseY).selected(this)) {
                    if (!GameState.keyStates.get(KeyEvent.VK_SHIFT)) {
                        clearMovements();
                    }
                    ArrayList<Movement> m = Pathfinder.getMovement(x, y, GameState.mouseX, GameState.mouseY, this);

                    addMovements(m);
                }
            }
            if (actionQue.size() == 0) {
                return;
            }
            currentAction = actionQue.get(0);
            actionQue.remove(0);
            if (currentAction instanceof Movement) {
                Image.getImage(ImageID.PLAYER_ID).setImg(Image.PLAYER_STILL[((Movement) currentAction).getDirection()]);
                if (Tilemap.getTile(((Movement) currentAction).getDeltaX() + x, ((Movement) currentAction).getDeltaY() + y).getEntity() instanceof Enemy) {
                    currentAction = new Stand(this);
                }
                currentAction.addEntity(this);
            }
        }
        if (currentAction == null) {
            GameState.playerTurn = false;
            currentAction = new Stand(this);
            return;
        }

        currentAction.preformAction();
    }

    @Override
    public void selected() {

    }

    @Override
    public void damage(int dmg) {
        super.damage(dmg);
    }
    @Override
    public void playSound(int w){
        switch (w) {
            case (SoundManager.MOVE):
                SoundManager.playSound(new String[]{"step1.wav","step2.wav","step3.wav"});
                return;
            case SoundManager.ATTACK:
                SoundManager.playSound(new String[]{""});
                return;
            case SoundManager.HURT:
                SoundManager.playSound(new String[]{"playerHurt.wav","hurt1.wav"});
                return;
            case SoundManager.DIE:
                SoundManager.playSound(new String[]{""});
                return;
        }
    }

    @Override
    public BufferedImage[][] getAttackImages() {
        return Image.PLAYER_ATTACK;
    }

    public void addAction(Action a) {
        actionQue.add(a);
    }

    public void addMovements(ArrayList<Movement> m) {
        for (Movement m1 : m) {
            addAction(m1);
        }

    }

    public void clearMovements() {
        actionQue.clear();
    }

    public void replaceImage() {
        Image.put(ImageID.PLAYER_ID, new Image(GameState.tileSize * x, GameState.tileSize * y - 25, Image.PLAYER_FRONT));
    }
    @Override
    public Action assignAction(){
        if (actionQue.size()>0) {
            Action a = actionQue.get(0);
            actionQue.remove(0);
            a.addEntity(this);
            return a;
        }
        return new Stand(this);
    }
    public void setAction(Action a){
        currentAction = a;
    }
}

