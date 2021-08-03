package game2.tiles;

import game2.entities.Entity;

public class EmptyTile extends Tile {

    private Entity entity;

    @Override
    public Entity getEntity() {
        return entity;
    }

    @Override
    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    @Override
    public boolean isOpen() {
        return entity == null;
    }
}
