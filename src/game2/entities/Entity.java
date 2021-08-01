package game2.entities;

import game2.visuals.Images;
import game2.visuals.texture.Texture;

import java.awt.*;

public abstract class Entity {

    private Texture texture;

    protected final Point pos;

    protected Entity(int x, int y) {
        this.pos = new Point(x, y);
    }

    public Point getPos(){
        return pos;
    }

    public abstract void tick();

    /**
     * Creates and returns a new <code>Texture</code> object for this <code>Entity</code>.
     * @param images The <code>Images</code> object containing the texture image.
     * @return The texture for this <code>Entity</code>.
     */
    protected abstract Texture getTexture(Images images);

    /**
     * Creates a texture for this <code>Entity</code>.
     * @param images The <code>Images</code> object containing the texture image.
     */
    public void createTexture(Images images){
        this.texture = getTexture(images);
    }

    /**
     * Returns this <code>Entity</code>:s current texture
     * @return the <code>Texture</code> object of this <code>Entity</code>.
     */
    public Texture getTexture(){
        return texture;
    }
}
