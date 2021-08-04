package game2.visuals.texture;

import game2.enums.TextureState;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.EnumMap;
import java.util.Map;

public class  MultiTexture <T extends AbstractTexture> extends AbstractTexture {

    private final Rectangle bounds;

    private T currentTexture;

    protected final Map<TextureState, T> textures;

    public MultiTexture(T placeHolder){
        this.textures = new EnumMap<>(TextureState.class);
        this.bounds = new Rectangle(0, 0, 0, 0);
        this.currentTexture = placeHolder;
    }

    public void move(int dx, int dy) {
        bounds.x += dx;
        bounds.y += dy;
    }

    @Override
    public Rectangle getBounds() {
        return bounds;
    }

    public void addState(TextureState textureState, T texture){
        textures.put(textureState, texture);
    }

    public void setState(TextureState state) {
        if (!textures.containsKey(state)) return;
        Rectangle b = currentTexture.getBounds();
        bounds.x -= b.x;
        bounds.y -= b.y;
        this.currentTexture = textures.get(state);
        Rectangle b2 = currentTexture.getBounds();
        bounds.x += b2.x;
        bounds.y += b2.y;
        bounds.width = b2.width;
        bounds.height = b2.height;
    }

    @Override
    protected BufferedImage getImage() {
        return currentTexture.getImage();
    }

    @Override
    public int getZ() {
        return currentTexture.getZ();
    }

    @Override
    public void tick() {
        currentTexture.tick();
    }
}
