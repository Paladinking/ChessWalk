package game2.tiles;

import game2.entities.Entity;

public class EmptyTile extends Tile {

    private Entity entity;
    private boolean hidden;

    public EmptyTile(){
        this.hidden = true;
    }

    @Override
    public Entity getEntity() {
        return entity;
    }

    @Override
    public void setEntity(Entity entity) {
        this.entity = entity;
        if (entity == null) return;
        if (hidden) entity.hide();
        else entity.show();
    }

    @Override
    public boolean isOpen() {
        return entity == null;
    }

    @Override
    public void hide() {
        super.hide();
        this.hidden = true;
        if (entity != null) entity.hide();
    }

    @Override
    public void show() {
        super.show();
        this.hidden = false;
        if (entity != null) entity.show();
    }
}
