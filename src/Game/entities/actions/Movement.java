package Game.entities.actions;

import Game.assets.Image;
import Game.entities.Entity;

public class Movement extends Action{
     private static final int UP = 0, DOWN = 1,LEFT = 2,RIGHT = 3, STILL = 4;

    public int deltaX,deltaY;
    public void clear(){
        deltaY = 0;
        deltaX = 0;
    }
    public int getDirection(){
        if (deltaX>0) return RIGHT;
        if (deltaX<0) return LEFT;
        if (deltaY>0) return DOWN;
        if (deltaY<0) return UP;
        return STILL;
    }
    public Movement(int deltaX, int deltaY, Entity e){
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.entity =e;
        beforeAction();
    }
    public Movement(int deltaX,int deltaY){
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }
    public Movement(Movement m,Entity e){
        this(m.deltaX,m.deltaY,e);
    }


    @Override
    public void preformAction() {
        ticks++;
        if(ticks == 11){
            afterAction();
            return;
        }
        Image.getImage(entity.getId()).moveImage(deltaX*50/10,deltaY*50/10);

    }

    @Override
    public void beforeAction() {
        entity.setX(entity.getX()+deltaX);
        entity.setY(entity.getY()+deltaY);
    }

    @Override
    public void afterAction() {
        entity.clearAction();
    }
}