package game2.visuals.texture;

import game2.entities.Entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class EntityTexture extends ImageTexture {

    private final Entity entity;

    private int width, height;

    private final Rectangle bounds;

    private final Point pos;

    public EntityTexture(Entity entity, int width, int height, BufferedImage image){
        super(image);
        this.entity = entity;
        this.width = width;
        this.height = height;
        this.pos = entity.getPos();
        this.bounds = new Rectangle(pos.x, pos.y, width, height);

    }

    @Override
    public Rectangle getBounds() {
        bounds.x = pos.x;
        bounds.y = pos.y;
        return bounds;
    }
}
