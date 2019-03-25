package Game.levels.Tiles;

import Game.entities.Enemy;
import Game.entities.Entity;

public class EmptyTile extends Tile {

    @Override
    public void pressed(Entity e) {
        setEntity(e);
    }

    @Override
    public boolean selected(Entity e) {
        if(entity!=null){
            entity.selected();
            return !(e instanceof Enemy);
        }
        return true;
    }

    @Override
    public void stopped() {

    }

    @Override
    public void setEntity(Entity e) {
        entity = e;
    }

    @Override
    public void removeEntity() {
        entity = null;
    }

    @Override
    public Entity getEntity() {
        return entity;
    }
}
