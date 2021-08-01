package game2.tiles;

import game2.entities.Entity;
import game2.levels.Level;
import game2.visuals.texture.StaticTexture;
import game2.visuals.texture.Texture;

public class WallTile extends Tile {
    @Override
    public Entity getEntity() {
        return null;
    }

    @Override
    protected Texture getTexture(int x, int y, int tileSize, Level level) {
        int wallImageYOffset = 20;
        return new StaticTexture(level.getWallImage(), x * tileSize, (y * tileSize) - wallImageYOffset, tileSize, tileSize + wallImageYOffset);
    }

    @Override
    public boolean setEntity(Entity entity) {
        return false;
    }

    @Override
    public boolean isOpen() {
        return false;
    }
}
