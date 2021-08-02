package game2.tiles;

import game2.entities.Entity;
import game2.enums.LevelImage;
import game2.levels.Level;
import game2.visuals.ImageData;
import game2.visuals.Images;
import game2.visuals.texture.StaticTexture;
import game2.visuals.texture.Texture;

import java.awt.image.BufferedImage;

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

    @Override
    protected Texture getTexture(int x, int y, int tileSize, Level level, Images images) {
        ImageData<LevelImage> data = level.getTile();
        BufferedImage image = images.getImage(level, LevelImage.TILE);
        int tx = x * tileSize - (data.width -tileSize) / 2, ty = y * tileSize - (data.height - tileSize);
        return new StaticTexture(image, 0, tx, ty, data.width, data.height);
    }
}
