package game2.tiles;

import game2.entities.Entity;
import game2.enums.TextureState;

public class WallTile extends Tile {
    @Override
    public Entity getEntity() {
        return null;
    }

    @Override
    public void setEntity(Entity entity) {

    }

    @Override
    public boolean isOpen() {
        return false;
    }


}
