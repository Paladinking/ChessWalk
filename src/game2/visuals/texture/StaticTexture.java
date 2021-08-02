package game2.visuals.texture;

import game2.enums.TextureState;

import java.awt.*;
import java.awt.image.BufferedImage;

public class StaticTexture extends ImageTexture {

    private final Rectangle bounds;

    public StaticTexture(BufferedImage image, int z, int x, int y, int w, int h) {
        super(image, z);
        bounds = new Rectangle(x, y, w, h);
    }

    public StaticTexture(BufferedImage image, int x, int y, int w, int h){
        this(image, 1, x, y, w, h);
    }

    @Override
    public Rectangle getBounds() {
        return bounds;
    }

    @Override
    public void setState(TextureState state) {
        throw new UnsupportedOperationException("Static Textures do not have states.");
    }
}
