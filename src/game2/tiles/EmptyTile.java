package game2.tiles;

import game2.entities.Entity;
import game2.levels.Level;
import game2.visuals.texture.StaticTexture;
import game2.visuals.texture.Texture;

public class EmptyTile extends Tile {

    private Entity entity;

    @Override
    public Entity getEntity() {
        return entity;
    }

    @Override
    protected Texture getTexture(int x, int y, int tileSize, Level level) {
        return new StaticTexture(level.getTileImage(), x * tileSize, y * tileSize, tileSize, tileSize);
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }
}
