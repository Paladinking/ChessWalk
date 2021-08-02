package game2.visuals.texture;

import game2.enums.TextureState;

import java.awt.*;

public interface Texture {

    void draw(Graphics2D g);

    Rectangle getBounds();

    void setState(TextureState state);

    int getZ();
}