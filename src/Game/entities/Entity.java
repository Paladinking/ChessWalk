package Game.entities;

import Game.GameState;
import Game.entities.actions.Action;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity {
    public static final int SLEEPING= 0,WANDERING =1, HUNTING=2;
    int x, y,hp,state;
    Game.assetClasses.Image image;
    Action currentAction;

    int mode;

    public Game.assetClasses.Image getImage(){
        return image;
    }

    public abstract void tick();

    public abstract void selected();

    public abstract void damage(int dmg);

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public abstract Action assignAction();

    public abstract void playSound(int w);

    public void draw(Graphics g, int x,int y){
        image.draw(g,x,y);
    }
    public void clearAction(){
        currentAction = null;
    }
    public abstract BufferedImage[][] getAttackImages();
    public abstract void attackAnimation(int t,int dx,int dy);

    public abstract void afterAttack(int dx,int dy);

    public abstract Image getBlood();
}

