package game2.visuals.texture;

import java.awt.*;
import java.awt.image.BufferedImage;

public class StaticTexture extends ImageTexture {

    private final Rectangle bounds;

    public StaticTexture(BufferedImage image, int x, int y, int w, int h) {
        super(image);
        bounds = new Rectangle(x, y, w, h);
    }

    @Override
    public Rectangle getBounds() {
        return bounds;
    }
}
