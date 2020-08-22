package Game.entities.actions;

import Game.GameState;
import Game.assetClasses.Image;
import Game.assetClasses.ImageID;
import Game.assetClasses.SoundManager;
import Game.entities.Entity;
import Game.levels.Tilemap;

public class Attack extends Action {
    private static final int UP = 0, DOWN = 1,LEFT = 2,RIGHT = 3, STILL = 4;
    public static final int tickLength = 18;
    private int deltaX,deltaY;
    private int damage,bloodId;
    public Attack(int deltaX, int deltaY,int damage, Entity e){
        this.deltaX =deltaX;
        this.deltaY = deltaY;
        this.damage = damage;
        this.addEntity(e);
    }
    public static int getDirection(int dx, int dy,Entity e){
        if (dx>0){
            return RIGHT;
        }
        if (dx<0) {
            return LEFT;
        }
        if (dy>0){
            return DOWN;

        }
        if (dy<0){
            return UP;

        }
        return -1;
    }

    @Override
    public void preformAction() {
        entity.attackAnimation(ticks,deltaX,deltaY);
        if(ticks==tickLength()/2){
            bloodId = ImageID.getId();
            Entity target = Tilemap.getTile(entity.getX()+deltaX,entity.getY()+deltaY).getEntity();
            Image.put(bloodId,new Image(target.getX()* GameState.tileSize,target.getY()*GameState.tileSize+1,target.getBlood()));}
        super.preformAction();
    }

    @Override
    public int tickLength() {
        return tickLength;
    }

    @Override
    public void beforeAction() {
        entity.playSound(SoundManager.ATTACK);
    }

    @Override
    public void afterAction() {
        Tilemap.getTile(entity.getX()+deltaX,entity.getY()+deltaY).Attack(damage);
        entity.afterAttack(deltaX,deltaY);
        Image.remove(bloodId);
        super.afterAction();
    }
}
