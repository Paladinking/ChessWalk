package game2.visuals.texture;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageTexture extends AbstractTexture {

    private final int zIndex;

    protected BufferedImage image;

    protected final Rectangle bounds;

    public ImageTexture(BufferedImage image, int z, int x, int y, int w, int h, int tileSize) {
        super(tileSize);
        this.image = image;
        this.zIndex = z;
        this.bounds = new Rectangle(x, y, w, h);
    }

    @Override
    protected BufferedImage getImage() {
        return image;
    }

    @Override
    public void move(int dx, int dy) {
        bounds.x += dx;
        bounds.y += dy;
    }

    @Override
    public void tick(){

    }


    @Override
    public int getZ(){
        return zIndex;
    }

    @Override
    public Rectangle getBounds(){
        return bounds;
    }

}
