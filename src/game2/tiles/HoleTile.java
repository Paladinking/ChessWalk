package game2.tiles;

import game2.entities.Entity;
import game2.enums.TileType;
import game2.levels.Level;
import game2.visuals.ImageData;
import game2.visuals.Images;
import game2.visuals.texture.StaticTexture;
import game2.visuals.texture.Texture;

import java.awt.image.BufferedImage;

public class HoleTile extends Tile{
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
