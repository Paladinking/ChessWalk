package Game.Items;

import Game.levels.Tilemap;

import java.awt.*;

public class Teleport extends Item {
    public Teleport(Image i){
        super(i,null);
    }

    @Override
    public void beforeUse() {
        Tilemap.getTile(entity.getX(),entity.getY()).removeEntity();
        entity.setX(entity.getX()+dx);
        entity.setY(entity.getY()+dy);
        Tilemap.getTile(entity.getX(),entity.getY()).setEntity(entity);
    }

    @Override
    public void afterUse() {

    }

    @Override
    public int getUseTime() {
        return 2;
    }

    @Override
    public void use(int t) {

    }
}
