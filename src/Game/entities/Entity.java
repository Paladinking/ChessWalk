package Game.entities;

import Game.entities.actions.Action;
import Game.entities.actions.Stand;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity {
    public static final int SLEEPING= 0,WANDERING =1, HUNTING=2;
    int x, y,hp;

    int imgId;
    Action currentAction;

    int mode;

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

    public int getId(){
        return imgId;
    }
    public void clearAction(){
        currentAction = null;
    }
    public abstract BufferedImage[][] getAttackImages();
    public abstract void attackAnimation(int t,int dx,int dy);

    public abstract void afterAttack(int dx,int dy);

    public abstract Image getBlood();
}

