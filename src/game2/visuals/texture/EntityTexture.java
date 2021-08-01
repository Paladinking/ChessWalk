package game2.visuals.texture;

import game2.entities.Entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class EntityTexture extends ImageTexture {

    private final Rectangle bounds;

    public EntityTexture(int x, int y, int width, int height, BufferedImage image){
        super(image);
        this.bounds = new Rectangle(x, y, width, height);
    }

    public void move(int dx, int dy){
        bounds.x += dx;
        bounds.y += dy;
    }

    @Override
    public Rectangle getBounds() {
        return bounds;
    }
}
