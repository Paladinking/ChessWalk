package Game.entities.actions;

import Game.assets.Image;
import Game.assets.ImageID;
import Game.entities.Entity;
import Game.levels.Tilemap;

public class Attack extends Action {
    private static final int UP = 0, DOWN = 1,LEFT = 2,RIGHT = 3, STILL = 4;

    private int deltaX,deltaY;
    private int damage;
    public Attack(int deltaX, int deltaY,int damage, Entity e){
        this.deltaX =deltaX;
        this.deltaY = deltaY;
        this.entity = e;
        this.damage = damage;
        beforeAction();
    }
    private int getDirection(){
        if (deltaX>0){
            Image.getImage(entity.getId()).setAdjX(0);
            Image.getImage(entity.getId()).setAdjY(0);
            return RIGHT;
        }
        if (deltaX<0) {
            Image.getImage(entity.getId()).setAdjX(-30);
            Image.getImage(entity.getId()).setAdjY(0);
            return LEFT;
        }
        if (deltaY>0){
            Image.getImage(entity.getId()).setAdjX(0);
            Image.getImage(entity.getId()).setAdjY(0);
            return DOWN;

        }
        if (deltaY<0){
            Image.getImage(entity.getId()).setAdjX(0);
            Image.getImage(entity.getId()).setAdjY(0);
            return UP;

        }
        return -1;
    }

    @Override
    public void preformAction() {
        ticks++;
        if(ticks == 21){
            afterAction();
            return;
        }
        Image.getImage(entity.getId()).setImg(Image.PLAYER_ATTACK[getDirection()][(ticks-1)/4]);
        ;
    }

    @Override
    public void beforeAction() {

    }

    @Override
    public void afterAction() {
        Tilemap.getTile(entity.getX()+deltaX,entity.getY()+deltaY).getEntity().damage(damage);
        Image.getImage(entity.getId()).setImg(Image.PLAYER_STILL[getDirection()]);
        Image.getImage(entity.getId()).setAdjX(0);
        Image.getImage(entity.getId()).setAdjY(0);

        entity.clearAction();
    }
}
