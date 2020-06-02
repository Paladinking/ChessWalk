package Game.Items;

import Game.entities.Entity;
import Game.entities.actions.Attack;
import Game.assetClasses.Image;
import Game.levels.Tilemap;

public class Sword extends Item {
    private int dmg = 10;
    private Attack attack;
    public Sword(int dmg,Entity owner){
        super(Image.SWORD,owner);
        this.dmg = dmg;
    }
    @Override
    public void beforeUse() {
        attack = new Attack(dx,dy,dmg,entity);
    }

    @Override
    public void afterUse() {

    }

    @Override
    public int getUseTime() {
        return Attack.tickLength;
    }

    @Override
    public void use(int t) {
        attack.preformAction();
    }
    @Override
    public boolean reUse(int dx, int dy, Entity entity){
        if(Math.abs(dx)>=2||Math.abs(dy)>=2||(dx==0&&dy==0)) return false;
        if(Tilemap.getTile(entity.getX()+dx,entity.getY()+dy).getEntity() ==null) return false;
        return super.reUse(dx,dy,entity);
    }

}
