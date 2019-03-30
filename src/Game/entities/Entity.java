package Game.entities;

import Game.entities.actions.Action;

import java.awt.image.BufferedImage;

public abstract class Entity {
    protected int x, y;

    private static final BufferedImage[][] attackImages = new BufferedImage[4][5];
    int imgId;
     Action currentAction;

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

    public int getId(){
        return imgId;
    }
    public void clearAction(){
        currentAction = null;
    }
    public BufferedImage[][] getAttackImages(){
        return attackImages;
    }
}

