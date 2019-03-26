package Game.entities.actions;

import Game.entities.Entity;
import Game.entities.Player;
import Game.levels.Tilemap;

public class Attack extends Action {
    private int deltaX,deltaY;
    private int damage;
    public Attack(int deltaX, int deltaY,int damage, Entity e){
        this.deltaX =deltaX;
        this.deltaY = deltaY;
        this.entity = e;
        this.damage = damage;
        beforeAction();
    }

    @Override
    public void preformAction() {
        ticks++;
        if(ticks == 11){
            afterAction();
            return;
        }
        //ANIMATE THE ATTACK (DONE LATER)
    }

    @Override
    public void beforeAction() {

    }

    @Override
    public void afterAction() {
        Tilemap.getTile(entity.getX()+deltaX,entity.getY()+deltaY).getEntity().damage(damage);
        entity.clearAction();
    }
}
