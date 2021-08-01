package game2.entities;

import game2.visuals.Images;
import game2.visuals.texture.EntityTexture;
import game2.visuals.texture.Texture;

import java.awt.*;

public abstract class Entity {

    private EntityTexture texture;

    protected final Point gridPos;

    protected Entity(int x, int y) {
        this.gridPos = new Point(x, y);
    }

    public Point getPos(){
        return gridPos;
    }

    public abstract void tick();

    /**
     * Creates and returns a new <code>Texture</code> object for this <code>Entity</code>.
     * @param images The <code>Images</code> object containing the texture image.
     * @param tileSize The size of a tile in the <code>TileMap</code>.
     * @return The texture for this <code>Entity</code>.
     */
    protected abstract EntityTexture getTexture(Images images, int tileSize);

    /**
     * Creates a texture for this <code>Entity</code>.
     * @param images The <code>Images</code> object containing the texture image.
     * @param tileSize The size of a tile in the <code>TileMap</code>.
     */
    public void createTexture(Images images, int tileSize){
        this.texture = getTexture(images, tileSize);
    }

    /**
     * Returns this <code>Entity</code>:s current texture
     * @return the <code>Texture</code> object of this <code>Entity</code>.
     */
    public Texture getTexture(){
        return texture;
    }
}
