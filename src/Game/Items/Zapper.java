package Game.Items;

import Game.GameState;
import Game.entities.Entity;
import Game.levels.Tilemap;

import java.awt.*;

public class Zapper extends Item {
    public Zapper(Image i){
        super(i,null);
    }
    @Override
    public void beforeUse() {

    }

    @Override
    public void afterUse() {
        Tilemap.getTile(entity.getX()+dx,entity.getY()+dy).Attack(200);
    }

    @Override
    public int getUseTime() {
        return 6;
    }

    @Override
    public void use(int t) {
        //drawLine(entity.getX()*GameState.tileSize,entity.getY()*GameState.tileSize,(entity.getX()+dx)*GameState.tileSize,(entity.getY()+dy)*GameState.tileSize);
    }
}
