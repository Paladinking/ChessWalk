package game2.visuals.texture;

import game2.visuals.texture.Texture;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class ImageTexture implements Texture {

    private final BufferedImage image;

    protected ImageTexture(BufferedImage image){
        this.image = image;
    }
    @Override
    public void draw(Graphics2D g) {
        Rectangle r = getBounds();
        g.drawImage(image, r.x, r.y, r.width, r.height, null);
    }
}
