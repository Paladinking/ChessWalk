package game2.visuals.texture;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class ImageTexture implements Texture {

    private int zIndex;

    protected BufferedImage image;

    protected ImageTexture(BufferedImage image, int z){
        this.image = image;
        this.zIndex = z;
    }
    @Override
    public void draw(Graphics2D g) {
        Rectangle r = getBounds();
        g.drawImage(image, r.x, r.y, r.width, r.height, null);
    }

    @Override
    public int getZ(){
        return zIndex;
    }
}
